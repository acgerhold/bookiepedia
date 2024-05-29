package bookiepedia.dynamodb;

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


public class ESPNdao {

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
        // Specify date ranges with '?dates=YYYYMMDD-YYYYMMDD'
        // No parameters returns schedule for current day
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
                    .orElse(INVALID_ATTRIBUTE_REPLACER)
        );
        // League Name
        schedule.setLeagueName(
                Stream.of(espnResponse)
                    .map(response -> response.getJSONArray("leagues"))
                    .map(leagues -> leagues.getJSONObject(0))
                    .map(league -> league.optString("abbreviation", INVALID_ATTRIBUTE_REPLACER))
                    .findFirst()
                    .orElse(INVALID_ATTRIBUTE_REPLACER)
        );
        // Event ID List
        JSONArray events = espnResponse.getJSONArray("events");
        schedule.setEventIdList(
                IntStream.range(0, events.length())
                        .mapToObj(events::getJSONObject)
                        .map(obj -> obj.getString("id"))
                        .collect(Collectors.toList())
        );
        // Schedule ID (ex. S46-05252024)
        schedule.setScheduleId(String.format("%s-%s-%s",
                schedule.getLeagueId(), START_DATE, END_DATE)
        );
        // Schedule Name
        schedule.setScheduleName(String.format("%s Events: %s - %s",
                schedule.getLeagueName(), START_DATE, END_DATE)
        );
        // Schedule Date Range
        schedule.setDateRange(String.format("%s-%s",
                START_DATE, END_DATE)
        );
        // Schedule Timestamp
        schedule.setTimestamp(
                now.toString()
        );

        ObjectMapper mapper = new ObjectMapper();

        String scheduleJson = mapper.writeValueAsString(schedule);

        DataQualityScanner dataQualityScanner = new DataQualityScanner(scheduleJson, THRESHOLD);
        dataQualityScanner.scan();

        return scheduleJson;
    }

    public List<String> extractEvents(JSONObject espnResponse) {

        JSONArray events = espnResponse.getJSONArray("events");
        List<JSONObject> eventListObjects = IntStream.range(0, events.length())
                .mapToObj(events::getJSONObject)
                .collect(Collectors.toList());

        ObjectMapper mapper = new ObjectMapper();

        List<String> eventList = new ArrayList<>();
        eventListObjects
                .forEach(event -> {
                    Event e = new Event();
                    e.setEventId(event.getJSONArray("competitions")
                            .getJSONObject(0)
                            .optString("id", INVALID_ATTRIBUTE_REPLACER)
                    );
                    e.setEventName(event.getJSONArray("competitions")
                            .getJSONObject(0)
                            .optString("id", INVALID_ATTRIBUTE_REPLACER)
                    );
                    e.setEventHeadline(event.getJSONArray("competitions")
                            .getJSONObject(0)
                            .optString("name", INVALID_ATTRIBUTE_REPLACER)
                    );

                    try {
                        String eventJson = mapper.writeValueAsString(e);
                        // Need to add every other attr above or will get NPE
                        // Think about reducing number of attr for events
                        eventList.add(eventJson);
                    } catch (JsonProcessingException jpe) {
                        throw new RuntimeException(jpe);
                    }
        });

        // Event ID *
//        event.setEventId(
//                Stream.of(espnResponse)
//                    .map(response -> response.getJSONArray("events"))
//                    .map(events -> events.getJSONObject(0))
//                    .map(e -> e.getString("id"))
//                    .findFirst()
//                    .get()
//        );
//        // Event Name *
//        event.setEventId((Stream.of(espnResponse)
//                .map(r -> r.getJSONArray("events"))
//                .map(es -> es.getJSONObject(0)))
//                .map(e -> e.getString("name"))
//                .findFirst()
//                .get()
//        );
//        // Event Name (Short) *
//        event.setEventId((Stream.of(espnResponse)
//                .map(r -> r.getJSONArray("events"))
//                .map(es -> es.getJSONObject(0)))
//                .map(e -> e.getString("shortName"))
//                .findFirst()
//                .get()
//        );
//        // Event Headline *
//        event.setEventHeadline(String.valueOf((Stream.of(espnResponse)
//                .map(r -> r.getJSONArray("events"))
//                .map(es -> es.getJSONObject(0)))
//                .map(e -> e.getJSONArray("competitions"))
//                .map(cs -> cs.getJSONArray(0))
//                //.map(c -> c.getJSONObject())
//
//                .findFirst()
//                .get())
//        );

        return eventList;
    }
}
