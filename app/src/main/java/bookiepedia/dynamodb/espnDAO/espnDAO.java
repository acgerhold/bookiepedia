package bookiepedia.dynamodb.espnDAO;

import bookiepedia.dynamodb.dataqualitycheck.DataQualityScanner;
import bookiepedia.dynamodb.models.Event;
import bookiepedia.dynamodb.models.Schedule;

import bookiepedia.dynamodb.models.assets.Team;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class espnDAO {

    private static final LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC-05:00"));
    private static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final DateTimeFormatter mm_dd_yy = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final long RANGE_DAYS = 10;
    private static final String START_DATE = now.format(yyyyMMdd);
    private static final String END_DATE = now.plusDays(RANGE_DAYS).format(yyyyMMdd);
    private static final double THRESHOLD = 70;
    private static final String INVALID_STRING_REPLACER = "Unavailable";


    public JSONObject requestQuery() throws IOException {

        // GET request for NBA schedule
        // Specify league with /sports/{sport}/{leagueId}/scoreboard
        // Specify date ranges with '?dates=YYYYMMDD-YYYYMMDD'
        // No date parameters returns schedule for current day
        String url = String.format("https://site.api.espn.com/apis/site/v2/sports/basketball/nba/scoreboard?dates=%s-%s",
                START_DATE, END_DATE);

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

    // Decide how to handle the JsonProcessingExceptions
    public String extractSchedule(JSONObject espnResponse) throws JsonProcessingException {

        Schedule schedule = new Schedule();

        JSONArray leagues = espnResponse.getJSONArray("leagues");
        JSONArray events = espnResponse.getJSONArray("events");

        // League ID
        schedule.setLeagueId(leagues.optJSONObject(0).optString("id", INVALID_STRING_REPLACER));
        // League Name
        schedule.setLeagueName(leagues.optJSONObject(0).optString("abbreviation", INVALID_STRING_REPLACER));
        // Event ID List
        schedule.setEventIdList(
                IntStream.range(0, events.length())
                        .mapToObj(events::getJSONObject)
                        .map(obj -> obj.getString("id"))
                        .collect(Collectors.toList()));
        // Schedule ID
        schedule.setScheduleId(String.format("%s-%s-%s",
                schedule.getLeagueId(), START_DATE, END_DATE));
        // Schedule Name
        schedule.setScheduleName(String.format("%s Events: %s - %s",
                schedule.getLeagueName(), START_DATE, END_DATE));
        // Schedule Date Range
        schedule.setDateRange(String.format("%s-%s",
                START_DATE, END_DATE));
        // Schedule Timestamp
        schedule.setTimestamp(
                now.toString());

        ObjectMapper mapper = new ObjectMapper();

        // Create JSON of new Schedule object
        String scheduleJson = mapper.writeValueAsString(schedule);

        // Scan Schedule JSON's data quality
        DataQualityScanner dataQualityScanner = new DataQualityScanner(scheduleJson, THRESHOLD);
        dataQualityScanner.scan();

        return scheduleJson;
    }

    public List<String> extractEvents(JSONObject espnResponse) {

        // Start by extracting the "events" JSONArray from the response and adding each JSONObject (event) to a list
        JSONArray eventsJson = espnResponse.getJSONArray("events");
        List<JSONObject> eventJsonList = IntStream.range(0, eventsJson.length())
                .mapToObj(eventsJson::getJSONObject)
                .collect(Collectors.toList());

        ObjectMapper mapper = new ObjectMapper();

        // Lists that will contain each created Event's JSON and their data quality scores
        List<String> eventList = new ArrayList<>();
        List<Double> dataQualityScores = new ArrayList<>();
        // Stream the list of Event JSONObjects to extract data for Event object attributes
        eventJsonList
                .forEach(event -> {

                    Event e = new Event();

                    JSONObject competition = event.getJSONArray("competitions").getJSONObject(0);
                    JSONArray competitors = competition.getJSONArray("competitors");
                    JSONObject homeTeam = competitors.getJSONObject(0);
                    JSONObject awayTeam = competitors.getJSONObject(1);
                    JSONObject status = event.getJSONObject("status");

                    // ID
                    e.setEventId(event.optString("id", INVALID_STRING_REPLACER));
                    // Event Name
                    e.setEventName(event.optString("name", INVALID_STRING_REPLACER));
                    // Event Name (Short)
                    e.setEventNameShort(event.optString("shortName", INVALID_STRING_REPLACER));
                    // Headline
                    e.setEventHeadline(competition.getJSONArray("notes").getJSONObject(0).optString("headline", INVALID_STRING_REPLACER));
                    // League ID
                    e.setLeagueId(espnResponse.getJSONArray("leagues").getJSONObject(0).optString("id", INVALID_STRING_REPLACER));
                    // Event Date
                    e.setEventDate(event.optString("date", INVALID_STRING_REPLACER));
                    // Event Season
                    e.setEventSeasonId(event.getJSONObject("season").optString("type", INVALID_STRING_REPLACER));
                    // Home Team
                    e.setTeamHome(homeTeam.getJSONObject("team").optString("id", INVALID_STRING_REPLACER));

//                    if (dynamoDbMapper.load(e.getTeamHome() == null)) {
//                        extractTeams(homeTeam.getJSONObject("team"), e.getLeagueId());
//                    }

                    // Away Team
                    e.setTeamAway(awayTeam.getJSONObject("team").optString("id", INVALID_STRING_REPLACER));

//                    if (dynamoDbMapper.load(e.getTeamHome() == null)) {
//                        extractTeams(homeTeam.getJSONObject("team"), e.getLeagueId());
//                    }

                    // Event Status ID
                    e.setEventStatusId(status.getJSONObject("type").optString("id", INVALID_STRING_REPLACER));
                    // Event Status
                    switch (e.getEventStatusId()) {
                        case "1":
                            e.setEventStatus("TBD");
                            break;
                        case "2":
                            e.setEventStatus("Final");
                            break;
                        case "3":
                            e.setEventStatus("P" + status.optString("period") + " " + status.optString("clock"));
                            break;
                    }
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

        // Take average of each Event's data quality score and print result
        // * May include data quality score as an attribute for each object
        double avgDataQualityScore = dataQualityScores.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
        System.out.println("\nAverage - " + avgDataQualityScore + "%");

        return eventList;
    }

    public String extractTeam(JSONObject team, String leagueId) throws JsonProcessingException {

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

        ObjectMapper mapper = new ObjectMapper();

        // Create JSON of new Team object
        // * Temporary, only for testing
        String teamJson = mapper.writeValueAsString(t);

        DataQualityScanner dataQualityScanner = new DataQualityScanner(teamJson, THRESHOLD);
        dataQualityScanner.scan();

        // * Temporary
        return teamJson;
    }

}
