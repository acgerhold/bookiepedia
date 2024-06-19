package bookiepedia.activities.requests;

public class GetBetsForHistoryRequest {

    private final String weeklyHistoryId;

    private GetBetsForHistoryRequest(String weeklyHistoryId) {
        this.weeklyHistoryId = weeklyHistoryId;
    }

    public String getWeeklyHistoryId() {
        return weeklyHistoryId;
    }

    @Override
    public String toString() {
        return "GetBetsForHistoryRequest{" +
                "weeklyHistoryId='" + weeklyHistoryId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String weeklyHistoryId;

        public GetBetsForHistoryRequest.Builder withWeeklyHistoryId(String weeklyHistoryId) {
            this.weeklyHistoryId = weeklyHistoryId;
            return this;
        }

        public GetBetsForHistoryRequest build() {
            return new GetBetsForHistoryRequest(weeklyHistoryId);
        }
    }

}
