package bookiepedia.dynamodb;

import bookiepedia.dynamodb.dataqualitycheck.DataQualityScanner;
import bookiepedia.dynamodb.espnDAO.espnDAO;
import bookiepedia.dynamodb.models.Event;
import bookiepedia.dynamodb.models.Schedule;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

//import static org.mockito.Mockito.*


public class ESPNdaoTest {

//    @Mock
    private DataQualityScanner dataQualityScanner;
    private static bookiepedia.dynamodb.espnDAO.espnDAO espnDAO;
    private static JSONObject result;

    @BeforeAll
    public static void setUp() throws IOException {
//        openMocks(this);
        espnDAO = new espnDAO();
        result = espnDAO.requestQuery();
    }

    @Test
    public void printSchedule() throws Exception {
        // Schedule Example - Values printed to terminal

        // GIVEN & WHEN - Extracting values from ESPN API response to create Schedule object
        String s = espnDAO.extractSchedule(result);

        // THEN - A Schedule object will be created without any null or invalid attributes
        ObjectMapper mapper = new ObjectMapper();
        Schedule schedule = mapper.readValue(s, Schedule.class);

        System.out.println("-");
        printScheduleAttributes(schedule);
        System.out.println("-");
    }

    @Test
    public void printEventsInSchedule() throws Exception {
        // Events Example - Values printed to terminal

        // GIVEN & WHEN - Extracting values from ESPN API response to create an Event object for each event contained in request to ESPN API
        List<String> eventListJson = espnDAO.extractEvents(result);

        // THEN - An Event object will be created for each Event without any null or invalid attributes
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Events:\n-");
        for (int i = 0; i < eventListJson.size(); i++) {
            System.out.println("(" + (i + 1) + "/" + eventListJson.size() + ")");
            Event event = mapper.readValue(eventListJson.get(i), Event.class);
            printEventAttributes(event);
        }
        System.out.println("-");
    }

    // HELPERS
    public void printScheduleAttributes(Schedule schedule) {
        System.out.println("Schedule    : " + schedule.getScheduleName());
        System.out.println("Schedule ID : " + schedule.getScheduleId());
        System.out.println("League      : " + schedule.getLeagueName());
        System.out.println("League ID   : " + schedule.getLeagueId());
        System.out.println("Timestamp   : " + schedule.getTimestamp());
        System.out.println("Date Range  : " + schedule.getDateRange());
        System.out.println("Events      : " + schedule.getEventIdList());
    }
    public void printEventAttributes(Event event) {
        System.out.println("Event           : " + event.getEventName());
        System.out.println("ID              : " + event.getEventId());
        System.out.println("Headline        : " + event.getEventHeadline());
        System.out.println("League ID       : " + event.getLeagueId());
        System.out.println("Event (short)   : " + event.getEventNameShort());
        System.out.println("Event Date      : " + event.getEventDate());
        System.out.println("Event Season ID : " + event.getEventSeasonId());
        System.out.println("Home Team ID    : " + event.getTeamHome());
        System.out.println("Score           : " + event.getScoreHome());
        System.out.println("Away Team ID    : " + event.getTeamAway());
        System.out.println("Score           : " + event.getScoreAway());
        System.out.println("Total Score     : " + event.getScoreTotal());
        System.out.println("Winning Team ID : " + event.getTeamWinner());
        System.out.println("Links           : " + event.getLinks() + "\n");
    }

}
