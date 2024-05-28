package bookiepedia.dynamodb;

import bookiepedia.dynamodb.dataqualitycheck.DataQualityScanner;
import bookiepedia.dynamodb.models.Event;
import bookiepedia.dynamodb.models.Schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ESPNdao {

    private final LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC-05:00"));
    private final DateTimeFormatter mmddyy = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    private static final String NULL_ATTRIBUTE_REPLACER = "Unavailable";
    private DataQualityScanner dataQualityScanner;

    public JSONObject requestQuery() throws Exception {

        String startDate = "20240523";
        String endDate = "20240526";

        // GET request for NBA schedule
        // Specify date ranges with '?dates=YYYYMMDD-YYYYMMDD'
        // No parameters returns schedule for current day
        String url = String.format("https://site.api.espn.com/apis/site/v2/sports/basketball/nba/scoreboard?dates=%s-%s",
                startDate, endDate);

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

    public String extractSchedule(JSONObject espnResponse) throws JsonProcessingException {

        Schedule schedule = new Schedule();

        // League ID
        schedule.setLeagueId(Stream.of(espnResponse)
                .map(response -> response.getJSONArray("leagues"))
                .map(leagues -> leagues.getJSONObject(0))
                .map(league -> league.getString("id"))
                .findFirst()
                .get()
//                .orElse(NULL_ATTRIBUTE_REPLACER)
        );
        // League Name
        schedule.setLeagueName(Stream.of(espnResponse)
                .map(response -> response.getJSONArray("leagues"))
                .map(leagues -> leagues.getJSONObject(0))
                .map(league -> league.getString("abbreviation"))
                .findFirst()
                .get()
//                .orElse(NULL_ATTRIBUTE_REPLACER)
        );
        // Timestamp
        schedule.setTimestamp(now.toString());
        // Schedule ID (ex. S46-05252024)
        schedule.setScheduleId(schedule.getLeagueId() + "-" + now.format(mmddyy));
        // Event ID List
        // jsonarray(events) > jsonobject(0,1,2 ...) > getString("id")
        List<String> eventIds = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        //AtomicInteger counter = new AtomicInteger(0);
//        Stream.of(espnResponse)
//                .map(response -> response.getJSONArray("events"))
//                .forEach(events -> {
//                    JSONObject obj = events.getJSONObject(counter.getAndIncrement());
//                    list2.add(obj.getString("id"));
//                });
        AtomicInteger counter = new AtomicInteger(0);
        Stream.of(espnResponse)
                .map(response -> response.getJSONArray("events"))
                .forEach(events -> {
                    int index = counter.getAndIncrement();
                    if (index < events.length()) {
                        JSONObject obj = events.getJSONObject(index);
                        list2.add(obj.getString("id"));
                    }
                });

        System.out.println(list2);

        JSONArray list = new JSONArray(espnResponse.getJSONArray("events"));
        System.out.println("size :" + list.length());
        for (int i = 0; i < list.length(); i++) {
            JSONObject obj = list.getJSONObject(i);
            eventIds.add(obj.getString("id"));
        }
        schedule.setEventIdList(eventIds);
        // Schedule Name
        schedule.setScheduleName(NULL_ATTRIBUTE_REPLACER);
        // Schedule Date Range
        schedule.setDateRange(NULL_ATTRIBUTE_REPLACER);

        ObjectMapper mapper = new ObjectMapper();

        String scheduleJson = mapper.writeValueAsString(schedule);

        dataQualityScanner = new DataQualityScanner(scheduleJson, 70);
        dataQualityScanner.scan();

        return scheduleJson;
    }

    public String extractEvents(JSONObject espnResponse) throws JsonProcessingException {

        Event event = new Event();

        // Event ID *
        event.setEventId((Stream.of(espnResponse)
                .map(r -> r.getJSONArray("events"))
                .map(es -> es.getJSONObject(0)))
                .map(e -> e.getString("id"))
                .findFirst()
                .get()
        );
        // Event Name *
        event.setEventId((Stream.of(espnResponse)
                .map(r -> r.getJSONArray("events"))
                .map(es -> es.getJSONObject(0)))
                .map(e -> e.getString("name"))
                .findFirst()
                .get()
        );
        // Event Name (Short) *
        event.setEventId((Stream.of(espnResponse)
                .map(r -> r.getJSONArray("events"))
                .map(es -> es.getJSONObject(0)))
                .map(e -> e.getString("shortName"))
                .findFirst()
                .get()
        );
        // Event Headline *
        event.setEventHeadline(String.valueOf((Stream.of(espnResponse)
                .map(r -> r.getJSONArray("events"))
                .map(es -> es.getJSONObject(0)))
                .map(e -> e.getJSONArray("competitions"))
                .map(cs -> cs.getJSONArray(0))
                //.map(c -> c.getJSONObject())

                .findFirst()
                .get())
        );

        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(event);
    }
}
