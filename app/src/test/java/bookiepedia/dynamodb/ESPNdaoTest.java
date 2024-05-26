package bookiepedia.dynamodb;

import bookiepedia.dynamodb.dataqualitycheck.DataQualityScanner;
import bookiepedia.dynamodb.dataqualitycheck.exceptions.modelexceptions.ScheduleDataQualityException;
import bookiepedia.dynamodb.models.Schedule;
import bookiepedia.models.ScheduleModel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

//import static org.mockito.Mockito.*


public class ESPNdaoTest {

//    @Mock
    private DataQualityScanner dataQualityScanner;
    private ESPNdao espnDAO;

    @BeforeEach
    public void setUp() {
//        openMocks(this);
        espnDAO = new ESPNdao();
    }

    @Test
    public void printSchedule() throws Exception {
        // Schedule Example - Values printed to terminal

        // GIVEN
        JSONObject result = espnDAO.requestQuery();

        // WHEN
        String s = espnDAO.extractSchedule(result);

        // THEN
        ObjectMapper mapper = new ObjectMapper();
        Schedule schedule = mapper.readValue(s, Schedule.class);

        System.out.println("Schedule Name: " + schedule.getScheduleName());
        System.out.println("Schedule ID: " + schedule.getScheduleId());
        System.out.println("League: " + schedule.getLeagueName());
        System.out.println("League ID: " + schedule.getLeagueId());
        System.out.println("Timestamp: " + schedule.getTimestamp());
        System.out.println("Date Range: " + schedule.getDateRange());
        System.out.println("Events: ");
        for (String event : schedule.getEventIdList()) {
            System.out.println(event);
        }
    }

}
