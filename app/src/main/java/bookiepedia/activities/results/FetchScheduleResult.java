package bookiepedia.activities.results;

public class FetchScheduleResult {

    private final String message;

    private FetchScheduleResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "FetchScheduleResult {Message = " + message + "}";
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String message;

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public FetchScheduleResult build() {
            return new FetchScheduleResult(message);
        }
    }
}
