package bookiepedia.dynamodb.EspnDAO;

import bookiepedia.dynamodb.EspnDAO.constants.EspnRequestConstants;

import bookiepedia.dynamodb.dataqualitycheck.DataQualityScanner;
import bookiepedia.dynamodb.models.Event;
import bookiepedia.dynamodb.models.Schedule;
import bookiepedia.dynamodb.models.assets.League;
import bookiepedia.dynamodb.models.assets.Team;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static bookiepedia.dynamodb.EspnDAO.constants.EspnRequestConstants.CENTRAL_ZONE;

public class EspnDAO {

    private final DynamoDBMapper dynamoDbMapper;
    private static final double THRESHOLD = 70;
    private static final String INVALID_STRING_REPLACER = "Unavailable";

    @Inject
    public EspnDAO(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDbMapper = dynamoDBMapper;
    }

    /**
     * Sends an HTTP request to the ESPN API.
     * One request contains all necessary data to form Schedule, Event, Team, and League objects.
     * @return A response from the ESPN API as a JSONObject
     * @throws IOException if error contacting the ESPN API
     */
    public JSONObject requestQuery(String url) throws IOException {

        // Calling the API
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();

        // Prints request URL and response code
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode + "\n");

        // Use BufferedReader to build JSON out of InputStream
        BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        // Close connection
        br.close();

        // Convert
        return new JSONObject(response.toString());
    }

    /**
     * Extracts data necessary to create a 'Schedule' object.
     * @param espnResponse - The JSONObject returned by requestQuery()
     * @return A JSON of a 'Schedule' object represented as a String
     */
    public String extractSchedule(JSONObject espnResponse) {

        Schedule schedule = new Schedule();

        JSONArray leagues = espnResponse.getJSONArray("leagues");
        JSONArray events = espnResponse.getJSONArray("events");

        // League ID
        schedule.setLeagueId(leagues.optJSONObject(0).optString("id", INVALID_STRING_REPLACER));
        if (dynamoDbMapper.load(League.class, schedule.getLeagueId()) == null) {
            extractLeague(leagues.optJSONObject(0));
        }
        // Schedule ID
        schedule.setScheduleId(String.format("%s-%s",
                schedule.getLeagueId(), EspnRequestConstants.START_DATE));
        // League Name
        schedule.setLeagueName(leagues.optJSONObject(0).optString("abbreviation", INVALID_STRING_REPLACER));
        // Event ID List
        schedule.setEventIdList(
                IntStream.range(0, events.length())
                        .mapToObj(events::getJSONObject)
                        .map(obj -> obj.getString("id"))
                        .collect(Collectors.toList()));
        // Schedule Name
        schedule.setScheduleName(String.format("%s Events: %s",
                schedule.getLeagueName(), EspnRequestConstants.START_DATE));
        // Schedule Timestamp
        schedule.setTimestamp(EspnRequestConstants.NOW.format(EspnRequestConstants.yyyy_MM_dd));

        ObjectMapper mapper = new ObjectMapper();
        String scheduleJson;

        try {
            // Create JSON of new Schedule object
            scheduleJson = mapper.writeValueAsString(schedule);

            // Scan Schedule JSON's data quality
            DataQualityScanner dataQualityScanner = new DataQualityScanner(scheduleJson, THRESHOLD);
            dataQualityScanner.scan();
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException(jpe);
        }

        return scheduleJson;
    }

    /**
     * Extracts data necessary to create an 'Event' object.
     * @param espnResponse - The JSONObject returned by requestQuery()
     * @return A JSON of an 'Event' object represented as a String
     */
    public List<String> extractEvents(JSONObject espnResponse) {

        // Extract the league ID to be used in setting an event's scheduleId
        String leagueId = espnResponse.getJSONArray("leagues")
                .optJSONObject(0)
                .optString("id", INVALID_STRING_REPLACER);

        // Extract the "events" JSONArray from the response and adding each JSONObject (event) to a list
        JSONArray eventsJson = espnResponse.getJSONArray("events");
        List<JSONObject> eventJsonList = IntStream.range(0, eventsJson.length())
                .mapToObj(eventsJson::getJSONObject)
                .collect(Collectors.toList());

        List<List<JSONObject>> eventJsonListSplit = splitEvents(eventJsonList, 20);
        // Splitting the list of events into chunks, 7 days of MLB events returns 100 events in the result.
        // Calling fetchSchedule with all 100 events makes the request time out before finishing.
        // Splitting into chunks 5 chunks of 20 still causes an issue, but retrying fetchSchedule() will...
        // Eventually load remaining events into DynamoDB.
        // Placeholder for threading

        ObjectMapper mapper = new ObjectMapper();

        // List that will contain each created Event's JSON and their data quality scores
        List<String> eventList = new ArrayList<>();
        List<Double> dataQualityScores = new ArrayList<>();
        // Stream the list of Event JSONObjects to extract data for Event object attributes
        List<JSONObject> firstChunk = eventJsonListSplit.get(0);
        // for (List<JSONObject> chunk : eventJsonListSplit) {
            firstChunk.forEach(event -> {

                    Event e = new Event();

                    // Organize different collections of data within response
                    JSONObject competition = event.getJSONArray("competitions").getJSONObject(0);
                    JSONArray competitors = competition.getJSONArray("competitors");
                    JSONObject homeTeam = competitors.getJSONObject(0);
                    JSONObject awayTeam = competitors.getJSONObject(1);
                    JSONObject status = event.getJSONObject("status");

                    // ID
                    e.setEventId(event.optString("id", INVALID_STRING_REPLACER));
                    // Schedule ID
                    e.setScheduleId(String.format("%s-%s",
                            leagueId, EspnRequestConstants.START_DATE));
                    // Event Name
                    e.setEventName(event.optString("name", INVALID_STRING_REPLACER));
                    // Event Name (Short)
                    e.setEventNameShort(event.optString("shortName", INVALID_STRING_REPLACER));
                    // Headline
                    if (!competition.getJSONArray("notes").isEmpty()) {
                        e.setEventHeadline(competition.getJSONArray("notes")
                                .getJSONObject(0)
                                .optString("headline", INVALID_STRING_REPLACER));
                    } else {
                        e.setEventHeadline("-");
                    }
                    // League ID
                    e.setLeagueId(espnResponse.getJSONArray("leagues")
                            .getJSONObject(0)
                            .optString("id", INVALID_STRING_REPLACER));
                    // Event Date
                    String date = event.optString("date", INVALID_STRING_REPLACER);
                    ZonedDateTime dateInCentral = ZonedDateTime.parse(date, EspnRequestConstants.yyyy_MM_DD_T_HH_MM_X)
                                    .withZoneSameInstant(EspnRequestConstants.CENTRAL_ZONE);
                    e.setEventDate(dateInCentral.format(EspnRequestConstants.yyyy_MM_DD_T_HH_MM_X));
                    // Event Season
                    e.setEventSeasonId(event.getJSONObject("season")
                            .optString("type", INVALID_STRING_REPLACER));
                    // Home Team
                    e.setTeamHome(homeTeam.getJSONObject("team")
                            .optString("id", INVALID_STRING_REPLACER));
                    // Add Team info to DynamoDB if Team doesnt exist yet
                    if (dynamoDbMapper.load(Team.class, e.getTeamHome(), e.getLeagueId()) == null) {
                        extractTeam(homeTeam.getJSONObject("team"), e.getLeagueId());
                    }
                    // Away Team
                    e.setTeamAway(awayTeam.getJSONObject("team")
                            .optString("id", INVALID_STRING_REPLACER));
                    if (dynamoDbMapper.load(Team.class, e.getTeamAway(), e.getLeagueId()) == null) {
                        extractTeam(awayTeam.getJSONObject("team"), e.getLeagueId());
                    }
                    // Event Status ID
                    e.setEventStatusId(status.getJSONObject("type")
                            .optString("id", INVALID_STRING_REPLACER));
                    // Event Status
                    e.setEventStatus(status.getJSONObject("type")
                            .optString("shortDetail", INVALID_STRING_REPLACER));
                    // Home Team Score (Current or Final)
                    e.setScoreHome(homeTeam.optInt("score", -1));
                    // Away Team Score (Current or Final)
                    e.setScoreAway(awayTeam.optInt("score", -1));
                    // Total Score
                    e.setScoreTotal(e.getScoreHome() + e.getScoreAway());
                    // Links
                    JSONArray links = event.getJSONArray("links");
                    List<String> linksList = IntStream.range(0, links.length())
                            .mapToObj(links::getJSONObject)
                            .map(link -> link.optString("href", INVALID_STRING_REPLACER))
                            .collect(Collectors.toList());
                    e.setLinks(linksList);
                    // Winning Team
                    if (e.getEventStatusId().equals("3")) {
                        e.setTeamWinner(homeTeam.optBoolean("winner") ?
                                homeTeam.optString("id", INVALID_STRING_REPLACER) :
                                awayTeam.optString("id", INVALID_STRING_REPLACER));
                    } else {
                        e.setTeamWinner("-1");
                    }
                    // Team Home Logo & Colors
                    Team teamHome = dynamoDbMapper.load(Team.class, leagueId, e.getTeamHome());
                    e.setTeamHomeLogo(teamHome.getTeamLogo());
                    e.setTeamHomeColor(teamHome.getTeamColor());
                    e.setTeamHomeColorAlt(teamHome.getTeamAlternateColor());
                    // Team Away Logo & Colors
                    Team teamAway = dynamoDbMapper.load(Team.class, leagueId, e.getTeamAway());
                    e.setTeamAwayLogo(teamAway.getTeamLogo());
                    e.setTeamAwayColor(teamAway.getTeamColor());
                    e.setTeamAwayColorAlt(teamAway.getTeamAlternateColor());

                    try {

                        // Create JSON of new Event object
                        String eventJson = mapper.writeValueAsString(e);

                        // Scan each Event JSON's data quality
                        DataQualityScanner dataQualityScanner = new DataQualityScanner(eventJson, THRESHOLD);
                        System.out.print("(" + e.getEventId() + ") ");
                        dataQualityScanner.scan();

                        // Add Event JSON to list of Event JSONs
                        // Add Event's data quality score to list of each Event's score to calculate average
                        eventList.add(eventJson);
                        dataQualityScores.add(dataQualityScanner.getQualityPercentage());

                    } catch (JsonProcessingException jpe) {
                        throw new RuntimeException(jpe);
                    }
            });
        // }

        // Take average of each Event's data quality score and print result
        // * May include data quality score as an attribute for each object
        double avgDataQualityScore = dataQualityScores.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
        System.out.println("\nAverage - " + avgDataQualityScore + "%");

        return eventList;
    }

    public static List<List<JSONObject>> splitEvents(List<JSONObject> eventJsonList, int chunkSize) {
        List<List<JSONObject>> eventJsonListChunks = new ArrayList<>();

        for (int i = 0; i < eventJsonList.size(); i += chunkSize) {
            eventJsonListChunks.add(eventJsonList.subList(i, Math.min(eventJsonList.size(), i + chunkSize)));
        }

        return eventJsonListChunks;
    }

    /**
     * Extracts data necessary to create a 'Team' object.
     * This method is called only if a 'teamId' is not present in DynamoDB
     * @param team - A subsection of the JSONObject returned by requestQuery()
     * @param leagueId - The 'leagueId' for a given 'Event'
     * @return A JSON of a 'Schedule' object represented as a String
     */
    public void extractTeam(JSONObject team, String leagueId) {
        // This will likely end up as a void method and be called within extractEvents() @Ln156
        // Takes extra parameter 'leagueId' that will be available in extractEvents()

        Team t = new Team();

        // League ID
        t.setLeagueId(leagueId);
        // Team ID
        t.setTeamId(team.optString("id", INVALID_STRING_REPLACER));
        // Team Name
        t.setTeamName(team.optString("displayName", INVALID_STRING_REPLACER));
        // Team Name Abbreviated
        t.setTeamNameAbr(team.optString("abbreviation", INVALID_STRING_REPLACER));
        // Team Logo
        t.setTeamLogo(team.optString("logo", INVALID_STRING_REPLACER));
        // Team Color Code
        t.setTeamColor(team.optString("color", INVALID_STRING_REPLACER));
        // Team Alternate Color Code
        t.setTeamAlternateColor(team.optString("alternateColor", INVALID_STRING_REPLACER));
        // Team Links
        JSONArray links = team.getJSONArray("links");
        List<String> linksList = IntStream.range(0, links.length())
                .mapToObj(links::getJSONObject)
                .map(link -> link.optString("href", INVALID_STRING_REPLACER))
                .collect(Collectors.toList());
        t.setTeamLinks(linksList);

        dynamoDbMapper.save(t);

        ObjectMapper mapper = new ObjectMapper();
        String teamJson;

        try {
            // Create JSON of new Team object
            // * Temporary, only for testing
            teamJson = mapper.writeValueAsString(t);

            // Scan for data quality
            DataQualityScanner dataQualityScanner = new DataQualityScanner(teamJson, THRESHOLD);
            dataQualityScanner.scan();
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException(jpe);
        }
    }

    /**
     * Extracts data necessary to create a 'League' object.
     * This method is called only if a 'leagueId' is not present in DynamoDB
     * @param league - A subsection of the JSONObject returned by requestQuery()
     * @return A JSON of a 'Schedule' object represented as a String
     */
    public void extractLeague(JSONObject league) {
        // This method will also probably end up as a void method that gets called in extractSchedule()

        League l = new League();

        JSONObject season = league.getJSONObject("season");

        // League ID
        l.setLeagueId(league.optString("id", INVALID_STRING_REPLACER));
        // League Name
        l.setLeagueName(league.optString("abbreviation", INVALID_STRING_REPLACER));
        // Season Status ID
        l.setSeasonStatusId(season.getJSONObject("type").optString("id", INVALID_STRING_REPLACER));
        // Season Status
        l.setSeasonStatus(season.getJSONObject("type").optString("name", INVALID_STRING_REPLACER));
        // Season Year
        l.setSeasonYear(season.optString("year", INVALID_STRING_REPLACER));
        // League Logo
        l.setLeagueLogo(league.getJSONArray("logos").getJSONObject(1).optString("href", INVALID_STRING_REPLACER));

        dynamoDbMapper.save(l);

        ObjectMapper mapper = new ObjectMapper();
        String leagueJson;

        try {
            // Create JSON of new League object
            leagueJson = mapper.writeValueAsString(l);

            // Scan for data quality
            DataQualityScanner dataQualityScanner = new DataQualityScanner(leagueJson, THRESHOLD);
            dataQualityScanner.scan();
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException(jpe);
        }
    }

}
