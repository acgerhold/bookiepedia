package bookiepedia.activities.requests;

public class GetBetsForHistoryRequest {

    private final String id;

    private GetBetsForHistoryRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "GetBetsForHistoryRequest{" +
                "id='" + id + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;

        public GetBetsForHistoryRequest.Builder withId(String id) {
            this.id = id;
            return this;
        }

        public GetBetsForHistoryRequest build() {
            return new GetBetsForHistoryRequest(id);
        }
    }

}
