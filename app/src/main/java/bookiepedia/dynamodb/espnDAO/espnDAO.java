package bookiepedia.dynamodb.espnDAO;

import bookiepedia.dynamodb.dataqualitycheck.DataQualityScanner;
import bookiepedia.dynamodb.models.Event;
import bookiepedia.dynamodb.models.Schedule;

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
import java.util.stream.Stream;


public class espnDAO {

    private static final LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC-05:00"));
    private static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final DateTimeFormatter mm_dd_yy = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final long RANGE_DAYS = 14;
    private static final String START_DATE = now.format(yyyyMMdd);
    private static final String END_DATE = now.plusDays(RANGE_DAYS).format(yyyyMMdd);
    private static final double THRESHOLD = 70;
    private static final String INVALID_ATTRIBUTE_REPLACER = "Unavailable";


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

        // League ID
        schedule.setLeagueId(
                Stream.of(espnResponse)
                    .map(response -> response.getJSONArray("leagues"))
                    .map(leagues -> leagues.getJSONObject(0))
                    .map(league -> league.optString("id", INVALID_ATTRIBUTE_REPLACER))
                    .findFirst()
                    .orElse(INVALID_ATTRIBUTE_REPLACER));
        // League Name
        schedule.setLeagueName(
                Stream.of(espnResponse)
                    .map(response -> response.getJSONArray("leagues"))
                    .map(leagues -> leagues.getJSONObject(0))
                    .map(league -> league.optString("abbreviation", INVALID_ATTRIBUTE_REPLACER))
                    .findFirst()
                    .orElse(INVALID_ATTRIBUTE_REPLACER));
        // Event ID List
        JSONArray events = espnResponse.getJSONArray("events");
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
                    // ID
                    e.setEventId(event.optString("id", INVALID_ATTRIBUTE_REPLACER));
                    // Event Name
                    e.setEventName(event.optString("name", INVALID_ATTRIBUTE_REPLACER));
                    // Event Name (Short)
                    e.setEventNameShort(event.optString("shortName", INVALID_ATTRIBUTE_REPLACER));
                    // Headline
                    e.setEventHeadline(event.getJSONArray("competitions")
                            .getJSONObject(0)
                            .getJSONArray("notes")
                            .getJSONObject(0)
                            .optString("headline", INVALID_ATTRIBUTE_REPLACER));
                    // League ID
                    e.setLeagueId(espnResponse.getJSONArray("leagues")
                            .getJSONObject(0)
                            .optString("id", INVALID_ATTRIBUTE_REPLACER));
                    // Event Date
                    e.setEventDate(event.optString("date", INVALID_ATTRIBUTE_REPLACER));
                    // Event Season
                    e.setEventSeasonId(event.getJSONObject("season")
                            .optString("type", INVALID_ATTRIBUTE_REPLACER));
                    // Home Team
                    e.setTeamHome(event.getJSONArray("competitions")
                            .getJSONObject(0)
                            .getJSONArray("competitors")
                            .getJSONObject(0)
                            .getJSONObject("team")
                            .optString("id", INVALID_ATTRIBUTE_REPLACER));
                    // Away Team
                    e.setTeamAway(event.getJSONArray("competitions")
                            .getJSONObject(0)
                            .getJSONArray("competitors")
                            .getJSONObject(1)
                            .getJSONObject("team")
                            .optString("id", INVALID_ATTRIBUTE_REPLACER));
                    // Event Status ID
                    e.setEventStatusId(event.getJSONObject("status")
                            .getJSONObject("type")
                            .optString("id", INVALID_ATTRIBUTE_REPLACER));
                    // Event Status
                    e.setEventStatus(e.getEventStatusId());
                    // IF (eventStatusId == 1) > "TBD"
                    // IF (eventStatusId == 3) > "Final"
                    // IF (eventStatusId == 2) > Period (Q or P) + clock + displayClock
                    // Winning Team
                    e.setTeamWinner(event.getJSONArray("competitions")
                            // IF (eventStatusID == 3) >
                            // IF (.getJSONObject(0) > .optString(winner) = true > .optString("id");
                            .getJSONObject(0)
                            .getJSONArray("competitors")
                            .getJSONObject(0)
                            .optString("id", INVALID_ATTRIBUTE_REPLACER));
                    // Home Team Score (Current or Final)
                    e.setScoreHome(event.getJSONArray("competitions")
                            .getJSONObject(0)
                            .getJSONArray("competitors")
                            // Need a way to select the home team - Using ID or homeAway?
                            .getJSONObject(0)
                            .optInt("score", -1));
                    // Away Team Score (Current or Final)
                    e.setScoreAway(event.getJSONArray("competitions")
                            .getJSONObject(0)
                            .getJSONArray("competitors")
                            // Need a way to select the home team - Using ID or homeAway?
                            .getJSONObject(1)
                            .optInt("score", -1));
                    // Total Score
                    e.setScoreTotal(e.getScoreHome() + e.getScoreAway());
                    // Links
                    JSONArray links = event.getJSONArray("links");
                    List<String> linksList = IntStream.range(0, links.length())
                            .mapToObj(links::getJSONObject)
                            .map(link -> link.optString("href", INVALID_ATTRIBUTE_REPLACER))
                            .collect(Collectors.toList());
                    e.setLinks(linksList);

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
}
