package bookiepedia.activities.requests;

public class FetchLeaguesRequest {
    
    private final String sportName;

    private FetchLeaguesRequest(String sportName) {
        this.sportName = sportName;
    }

    public String getSportName() {
        return sportName;
    }
    
    @Override
    public String toString() {
        return "FetchLeaguesRequest {sportName = " + sportName + "}";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String sportName;

        public Builder withSportName(String sportName) {
            this.sportName = sportName;
            return this;
        }

        public FetchLeaguesRequest build() {
            return new FetchLeaguesRequest(sportName);
        }
    }
}
