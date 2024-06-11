package bookiepedia.activities.requests;

public class FetchScheduleRequest {

    private final String id;

    private FetchScheduleRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "FetchScheduleRequest {ID = " + id + "}";
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public FetchScheduleRequest build() {
            return new FetchScheduleRequest(id);
        }
    }
}
