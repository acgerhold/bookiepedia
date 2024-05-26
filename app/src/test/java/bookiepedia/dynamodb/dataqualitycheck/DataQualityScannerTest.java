package bookiepedia.dynamodb.dataqualitycheck;

import bookiepedia.dynamodb.ESPNdao;
import bookiepedia.dynamodb.dataqualitycheck.exceptions.DataQualityException;
import bookiepedia.models.ScheduleModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class DataQualityScannerTest {

    @Mock
    private ESPNdao espnDAO;
    private DataQualityScanner dataQualityScanner;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }


    @Test
    public void extractSchedule_lowDataQuality_throwsScheduleDataQualityException() throws JsonProcessingException {

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
}
