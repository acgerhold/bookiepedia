package bookiepedia.dynamodb;

import bookiepedia.dynamodb.models.Schedule;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class ESPNdaoTest {

    @Test
    public void testingBufferedReaderStream() throws Exception {
        ESPNdao espn = new ESPNdao();
        JSONObject result = espn.requestQuery();

        Schedule s = espn.extractSchedule(result);

        System.out.println("Schedule Name: " + s.getScheduleName());
        System.out.println("Schedule ID: " + s.getScheduleId());
        System.out.println("League: " + s.getLeagueName() + s.getLeagueId());
        System.out.println("Timestamp: " + s.getTimestamp());
        System.out.println("Events: ");
        for (String str : s.getEventIdList()) {
            System.out.println(str);
        }

    }
}
