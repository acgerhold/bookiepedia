package bookiepedia.activities.requests;

import java.util.List;

public class GetEventsForScheduleRequest {

    private final String id;

    private GetEventsForScheduleRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "GetEventsForScheduleRequest{" +
                "id='" + id + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;

        public GetEventsForScheduleRequest.Builder withId(String id) {
            this.id = id;
            return this;
        }

        public GetEventsForScheduleRequest build() {
            return new GetEventsForScheduleRequest(id);
        }
    }
}
