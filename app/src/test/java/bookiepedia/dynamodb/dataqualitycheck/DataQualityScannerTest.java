package bookiepedia.dynamodb.dataqualitycheck;

import bookiepedia.exceptions.DataQualityException;
import bookiepedia.models.ScheduleModel;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class DataQualityScannerTest {

    @Mock
    private bookiepedia.dynamodb.espnDAO.espnDAO espnDAO;
    private DataQualityScanner dataQualityScanner;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }


    @Test
    public void extractSchedule_lowDataQuality_throwsScheduleDataQualityException() {

        // GIVEN - A Schedule JSONObject with 71% data quality and 75% threshold
        double threshold = 75.00;
        ScheduleModel scheduleModel = ScheduleModel.builder()
                .withScheduleId("schedule")
                .withLeagueId("123")
                .withLeagueName("league")
                .withTimestamp("01012024")
                .withEventIdList(List.of("game123"))
                .withDateRange("Unavailable")
                .withScheduleName("Unavailable")
                .build();
        JSONObject scheduleObject = new JSONObject(scheduleModel);

        // WHEN - Calling extractSchedule() and scanning result for data quality
        when(espnDAO.extractSchedule(scheduleObject)).thenReturn(String.valueOf(scheduleObject));

        String schedule = espnDAO.extractSchedule(scheduleObject);
        dataQualityScanner = new DataQualityScanner(schedule, threshold);

        // THEN - A ScheduleDataQualityException will be thrown
        // * Can't use ScheduleDataQualityException because ESPNdao is being mocked;
        //   StackWalker can't trace the class' calling method
        assertThrows(DataQualityException.class, () -> dataQualityScanner.scan());
    }

    @Test
    public void extractSchedule_highDataQuality_returnsScheduleJson() {

        // GIVEN - A Schedule JSONObject with 100% data quality and 75% threshold
        double threshold = 75.00;
        ScheduleModel scheduleModel = ScheduleModel.builder()
                .withScheduleId("1")
                .withLeagueId("123")
                .withLeagueName("league")
                .withTimestamp("01012024")
                .withEventIdList(List.of("game123"))
                .withDateRange("01012024-02012024")
                .withScheduleName("schedule123")
                .build();
        JSONObject scheduleObject = new JSONObject(scheduleModel);

        // WHEN - Calling extractSchedule() and scanning result for data quality
        when(espnDAO.extractSchedule(scheduleObject)).thenReturn(String.valueOf(scheduleObject));

        String schedule = espnDAO.extractSchedule(scheduleObject);
        dataQualityScanner = new DataQualityScanner(schedule, threshold);
        dataQualityScanner.scan();

        // THEN - The schedule will have 100% data quality and no invalid attributes
        assertEquals(100.0, dataQualityScanner.getQualityPercentage());
    }

    @Test
    public void extractEvents_lowDataQuality_throwsEventDataQualityException() {

    }

    @Test
    public void extractEvents_highDataQuality_returnsListOfEventJSONs() {

    }

    @Test
    public void extractTeams_lowDataQuality_throwsTeamDataQualityException() {

    }

    @Test
    public void extractTeams_highDataQuality_returnsTeamJSON() {

    }

    @Test
    public void extractLeagues_lowDataQuality_throwsLeagueDataQualityException() {

    }

    @Test
    public void extractLeagues_highDataQuality_returnsLeagueJSON() {

    }
}
