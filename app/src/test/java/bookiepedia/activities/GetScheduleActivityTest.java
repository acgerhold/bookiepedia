package bookiepedia.activities;

import bookiepedia.activities.requests.GetScheduleRequest;
import bookiepedia.activities.results.GetScheduleResult;
import bookiepedia.dynamodb.ScheduleDAO;
import bookiepedia.dynamodb.models.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import org.junit.jupiter.api.Test;

public class GetScheduleActivityTest {

        @Mock
        private ScheduleDAO scheduleDAO;

        private GetScheduleActivity getScheduleActivity;

        @BeforeEach
        public void setUp() {
            initMocks(this);
            getScheduleActivity = new GetScheduleActivity(scheduleDAO);
        }

        @Test
        public void handleRequest_savedScheduleFound_returnsScheduleModelInResult() {
            // GIVEN
            String expectedId = "expectedId";
            String expectedLeagueId = "expectedLeagueId";
            String expectedLeagueName = "expectedLeagueName";
            String expectedName = "expectedName";
            String expectedTimestamp = "expectedCustomerId";
            List<String> expectedEvents = List.of("event1", "event2", "event3");

            Schedule schedule = new Schedule();
            schedule.setScheduleId(expectedId);
            schedule.setLeagueId(expectedLeagueId);
            schedule.setLeagueName(expectedLeagueName);
            schedule.setTimestamp(expectedTimestamp);
            schedule.setEventIdList(expectedEvents);
            schedule.setScheduleName(expectedName);

            when(scheduleDAO.getSchedule(expectedId)).thenReturn(schedule);

            GetScheduleRequest request = GetScheduleRequest.builder()
                    .withId(expectedId)
                    .build();

            // WHEN
            GetScheduleResult result = getScheduleActivity.handleRequest(request);

            // THEN
            assertEquals(expectedId, result.getSchedule().getScheduleId());
            assertEquals(expectedLeagueId, result.getSchedule().getLeagueId());
            assertEquals(expectedLeagueName, result.getSchedule().getLeagueName());
            assertEquals(expectedName, result.getSchedule().getScheduleName());
            assertEquals(expectedTimestamp, result.getSchedule().getTimestamp());
            assertEquals(expectedEvents, result.getSchedule().getEventIdList());
        }

}
