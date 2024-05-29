package bookiepedia.dynamodb;

import bookiepedia.dynamodb.dataqualitycheck.DataQualityScanner;
import bookiepedia.dynamodb.models.Event;
import bookiepedia.dynamodb.models.Schedule;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

//import static org.mockito.Mockito.*


public class ESPNdaoTest {

//    @Mock
    private DataQualityScanner dataQualityScanner;
    private ESPNdao espnDAO;
    private JSONObject result;

    @BeforeEach
    public void setUp() throws IOException {
//        openMocks(this);
        this.espnDAO = new ESPNdao();
        this.result = espnDAO.requestQuery();
    }

    @Test
    public void printSchedule() throws Exception {
        // Schedule Example - Values printed to terminal

        // GIVEN & WHEN - Extracting values from ESPN API response to create Schedule object
        String s = espnDAO.extractSchedule(result);

        // THEN - A Schedule object will be created without any null or invalid attributes
        ObjectMapper mapper = new ObjectMapper();
        Schedule schedule = mapper.readValue(s, Schedule.class);

        printScheduleAttributes(schedule);

        for (String event : schedule.getEventIdList()) {
            System.out.println(event);
        }
    }

    @Test
    public void printEventsInSchedule() throws Exception {
        // Events Example - Values printed to terminal

        // GIVEN & WHEN - Extracting values from ESPN API response to create an Event object for each event contained in request to ESPN API
//        List<String> eventListJson = espnDAO.extractEvents(result);

        // THEN - An Event object will be created for each Event without any null or invalid attributes
//        ObjectMapper mapper = new ObjectMapper();
//        List<Event> eventList = new ArrayList<>();
//        for (String s : eventListJson) {
//            Event event = mapper.readValue(s, Event.class);
//            printEventAttributes(event);
//            eventList.add(event);
//        }
    }



    // HELPERS
    public void printScheduleAttributes(Schedule schedule) {
        System.out.println("Schedule Name: " + schedule.getScheduleName());
        System.out.println("Schedule ID: " + schedule.getScheduleId());
        System.out.println("League: " + schedule.getLeagueName());
        System.out.println("League ID: " + schedule.getLeagueId());
        System.out.println("Timestamp: " + schedule.getTimestamp());
        System.out.println("Date Range: " + schedule.getDateRange());
        System.out.println("Events: ");
    }
    public void printEventAttributes(Event event) {
        System.out.println("ID: " + event.getEventId());
        System.out.println("Headline: " + event.getEventHeadline());
    }

}
