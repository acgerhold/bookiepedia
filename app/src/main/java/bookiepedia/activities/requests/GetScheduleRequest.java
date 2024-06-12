package bookiepedia.activities.requests;

public class GetScheduleRequest {

    private final String id;

    private GetScheduleRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "GetScheduleRequest{" +
                "id='" + id + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;

        public GetScheduleRequest.Builder withId(String id) {
            this.id = id;
            return this;
        }

        public GetScheduleRequest build() {
            return new GetScheduleRequest(id);
        }
    }

}
