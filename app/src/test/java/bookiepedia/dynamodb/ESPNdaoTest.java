package bookiepedia.dynamodb;

import bookiepedia.dynamodb.dataqualitycheck.DataQualityScanner;
import bookiepedia.dynamodb.espnDAO.espnDAO;
import bookiepedia.dynamodb.models.Event;
import bookiepedia.dynamodb.models.Schedule;

import bookiepedia.dynamodb.models.assets.League;
import bookiepedia.dynamodb.models.assets.Team;
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
    public void extractTeams_teamNotInDynamoDB_savesNewTeamObject() {
        // Test that a new Team object is created if the Team ID is not found in DynamoDB
    }

    @Test
    public void extractTeams_teamInDynamoDB_methodNotCalled() {
        // Test that there are no interactions with extractTeams() if the Team ID is found in DynamoDB
    }

    @Test
    public void extractTeams_creatingTeamObject_perfectDataQuality() {

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
    public void printEvents() throws Exception {
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

    @Test
    public void printTeams() throws Exception {
        // Team Example - Values printed to terminal

        // GIVEN & WHEN - Extracting values from ESPN API response to create a Team object not found in DynamoDB
        JSONObject event = result.getJSONArray("events").getJSONObject(0);
        JSONObject team = event.getJSONArray("competitions")
                .getJSONObject(0)
                .getJSONArray("competitors")
                .getJSONObject(0)
                .getJSONObject("team");
        // * Temporary, will probably call extractTeam() within extractEvents() to simplify

        // THEN - A Team object will be created for both teams that will be stored in DynamoDB
        ObjectMapper mapper = new ObjectMapper();

        String teamJson = espnDAO.extractTeam(team, "46");
        Team t = mapper.readValue(teamJson, Team.class);

        System.out.println("Team(s)");
        printTeamAttributes(t);
    }

    @Test
    public void printLeagues() throws Exception {
        // Team Example - Values printed to terminal

        // GIVEN & WHEN - Extracting values from ESPN API response to create a Team object not found in DynamoDB
        JSONObject league = result.getJSONArray("leagues").getJSONObject(0);

        // * Temporary, will probably call extractTeam() within extractEvents() to simplify

        // THEN - A Team object will be created for both teams that will be stored in DynamoDB
        ObjectMapper mapper = new ObjectMapper();

        String leagueJson = espnDAO.extractLeague(league);
        League l = mapper.readValue(leagueJson, League.class);

        System.out.println("League(s)");
        printLeagueAttributes(l);
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
    public void printTeamAttributes(Team team) {
        System.out.println("League ID       : " + team.getLeagueId());
        System.out.println("Team ID         : " + team.getTeamId());
        System.out.println("Team Name       : " + team.getTeamName());
        System.out.println("Team Name Abr   : " + team.getTeamNameAbr());
        System.out.println("Team Logo URL   : " + team.getTeamLogo());
        System.out.println("Team Color      : " + team.getTeamColor());
        System.out.println("Alternate Color : " + team.getTeamAlternateColor());
        System.out.println("Team Links      : " + team.getTeamLinks() + "\n");
    }

    public void printLeagueAttributes(League league) {
        System.out.println("League ID        : " + league.getLeagueId());
        System.out.println("League Name      : " + league.getLeagueName());
        System.out.println("Season Status ID : " + league.getSeasonStatusId());
        System.out.println("Season Status    : " + league.getSeasonStatus());
        System.out.println("Season Year      : " + league.getSeasonYear());
        System.out.println("Season Logo      : " + league.getLeagueLogo() + "\n");
    }

}
