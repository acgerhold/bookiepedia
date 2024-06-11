package bookiepedia.activities;

import bookiepedia.activities.requests.GetScheduleRequest;
import bookiepedia.activities.results.GetScheduleResult;
import bookiepedia.converters.ModelConverter;
import bookiepedia.dynamodb.ScheduleDAO;
import bookiepedia.dynamodb.models.Schedule;
import bookiepedia.models.ScheduleModel;

import javax.inject.Inject;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class GetScheduleActivity {

    private final ScheduleDAO scheduleDAO;
    // private final Logger log = LogManager.getLogger();

    @Inject
    public GetScheduleActivity(ScheduleDAO scheduleDAO) {
        this.scheduleDAO = scheduleDAO;
    }

    public GetScheduleResult handleRequest(final GetScheduleRequest getScheduleRequest) {
        String requestedId = getScheduleRequest.getId();
        Schedule schedule = scheduleDAO.getSchedule(requestedId);
        ScheduleModel scheduleModel = new ModelConverter().toScheduleModel(schedule);

        return GetScheduleResult.builder()
                .withSchedule(scheduleModel)
                .build();
    }

}
