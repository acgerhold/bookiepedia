package bookiepedia.dynamodb;

import bookiepedia.dynamodb.models.Schedule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

public class ESPNdaoTest {

    @Test
    public void scheduleDataQuality() throws Exception {

        ESPNdao espn = new ESPNdao();
        JSONObject result = espn.requestQuery();

        ObjectMapper mapper = new ObjectMapper();

        String str = espn.extractSchedule(result);
        Schedule s = mapper.readValue(str, Schedule.class);

        System.out.println("Schedule Name: " + s.getScheduleName());
        System.out.println("Schedule ID: " + s.getScheduleId());
        System.out.println("League: " + s.getLeagueName() + s.getLeagueId());
        System.out.println("Timestamp: " + s.getTimestamp());
        System.out.println("Events: ");
        for (String event : s.getEventIdList()) {
            System.out.println(event);
        }
    }
}
