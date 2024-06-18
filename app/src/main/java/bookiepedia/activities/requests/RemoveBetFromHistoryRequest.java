package bookiepedia.activities.requests;

public class RemoveBetFromHistoryRequest {

    private final String id;

    private final String betId;

    private RemoveBetFromHistoryRequest(String id, String betId) {
        this.id = id;
        this.betId = betId;
    }

    public String getId() {
        return id;
    }

    public String getBetId() {
        return betId;
    }

    @Override
    public String toString() {
        return "RemoveBetFromHistoryRequest{" +
                "id='" + id + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private String betId;

        public RemoveBetFromHistoryRequest.Builder withId(String id) {
            this.id = id;
            return this;
        }

        public RemoveBetFromHistoryRequest.Builder withBetId(String betId) {
            this.betId = betId;
            return this;
        }

        public RemoveBetFromHistoryRequest build() {
            return new RemoveBetFromHistoryRequest(id, betId);
        }
    }
}
