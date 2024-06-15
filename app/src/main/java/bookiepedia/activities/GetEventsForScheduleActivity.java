package bookiepedia.activities;

import bookiepedia.activities.requests.GetEventsForScheduleRequest;
import bookiepedia.activities.results.GetEventsForScheduleResult;
import bookiepedia.converters.ModelConverter;
import bookiepedia.dynamodb.EventDAO;
import bookiepedia.dynamodb.models.Event;
import bookiepedia.models.EventModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GetEventsForScheduleActivity {

    private final Logger log = LogManager.getLogger(GetEventsForScheduleActivity.class);
    private final EventDAO eventDAO;

    @Inject
    public GetEventsForScheduleActivity(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public GetEventsForScheduleResult handleRequest(final GetEventsForScheduleRequest getEventsForScheduleRequest) {
        String requestedId = getEventsForScheduleRequest.getId();
        // Gets ID of request (aka the scheduleId)
        // BUT in the lambda, you use .withId(path.get("scheduleId"))
        log.info("Received GetEventsForScheduleRequest " + getEventsForScheduleRequest);

        List<Event> events = eventDAO.getEvents(requestedId);

        List<Event> eventsSorted = events.stream()
                .sorted(Comparator.comparing(Event::getEventDate))
                .collect(Collectors.toList());

        List<EventModel> eventModels = new ArrayList<>();
        for (Event event : eventsSorted) {
            eventModels.add(new ModelConverter().toEventModel(event));
        }

        return GetEventsForScheduleResult.builder()
                .withEventList(eventModels)
                .build();
    }
}
