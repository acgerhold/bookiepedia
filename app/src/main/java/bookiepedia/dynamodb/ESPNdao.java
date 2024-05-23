package bookiepedia.dynamodb;

import bookiepedia.dynamodb.models.Schedule;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ESPNdao {

    public JSONObject requestQuery() throws Exception {

        // GET request for NBA schedule
        String url = "https://site.api.espn.com/apis/site/v2/sports/basketball/nba/scoreboard";

        // Calling the API
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();

        // Prints response URL and response code
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        // Convert response to
        BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        // Close connection
        br.close();

        // Return JSONObject as string
        return new JSONObject(response.toString());
    }

    public Schedule extractSchedule(JSONObject espnResponse) {
        Schedule schedule = new Schedule();

        // League ID
        schedule.setLeagueId(Stream.of(espnResponse)
                .map(response -> response.getJSONArray("leagues"))
                .map(leagues -> leagues.getJSONObject(0))
                .map(league -> league.getString("id"))
                .findFirst()
                .get()
        );
        // League Name
        schedule.setLeagueName(Stream.of(espnResponse)
                .map(response -> response.getJSONArray("leagues"))
                .map(leagues -> leagues.getJSONObject(0))
                .map(league -> league.getString("abbreviation"))
                .findFirst()
                .get()
        );
        // Timestamp
        schedule.setTimestamp(Stream.of(espnResponse)
                .map(response -> response.getJSONObject("day"))
                .map(league -> league.getString("date"))
                .findFirst()
                .get()
        );
        // Schedule ID
        schedule.setScheduleId(schedule.getLeagueId() + "-" + schedule.getTimestamp());
        // Event ID List
        schedule.setEventIdList((Stream.of(espnResponse)
                .map(response -> response.getJSONArray("events"))
                .map(events -> events.getJSONObject(0)))
                // How will I adjust this for multiple games?
                // Couldn't figure out how to iterate through the JSONArray, events, using a stream.
                // Have to be accessing a JSONObject first (JSONObject(0)) to call getString(key)
                .map(event -> event.getString("id"))
                .collect(Collectors.toList())
        );

        return schedule;
    }
}
