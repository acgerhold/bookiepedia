package bookiepedia.activities;

import bookiepedia.activities.requests.GetScheduleRequest;
import bookiepedia.activities.results.GetScheduleResult;
import bookiepedia.converters.ModelConverter;
import bookiepedia.dynamodb.ScheduleDAO;
import bookiepedia.dynamodb.models.Schedule;
import bookiepedia.models.ScheduleModel;

import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetScheduleActivity {

    private final Logger log = LogManager.getLogger(GetScheduleActivity.class);

    private final ScheduleDAO scheduleDAO;

    @Inject
    public GetScheduleActivity(ScheduleDAO scheduleDAO) {
        this.scheduleDAO = scheduleDAO;
    }

    public GetScheduleResult handleRequest(final GetScheduleRequest getScheduleRequest) {
        String requestedId = getScheduleRequest.getId();
        log.info("Received GetScheduleRequest " + getScheduleRequest);
        Schedule schedule = scheduleDAO.getSchedule(requestedId);
        ScheduleModel scheduleModel = new ModelConverter().toScheduleModel(schedule);

        return GetScheduleResult.builder()
                .withSchedule(scheduleModel)
                .build();
    }

}
