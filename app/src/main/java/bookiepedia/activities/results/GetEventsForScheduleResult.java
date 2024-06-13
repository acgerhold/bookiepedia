package bookiepedia.activities.results;

import bookiepedia.models.EventModel;

import java.util.List;

public class GetEventsForScheduleResult {

    private final List<EventModel> eventList;

    private GetEventsForScheduleResult(List<EventModel> eventList) {
        this.eventList = eventList;
    }

    public List<EventModel> getEventList() {
        return eventList;
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private List<EventModel> eventList;

        public Builder withEventList(List<EventModel> eventList) {
            this.eventList = eventList;
            return this;
        }

        public GetEventsForScheduleResult build() {
            return new GetEventsForScheduleResult(eventList);
        }
    }
}
