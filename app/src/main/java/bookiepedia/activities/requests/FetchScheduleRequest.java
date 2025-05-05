package bookiepedia.activities.requests;

public class FetchScheduleRequest {

    private final String Id;
    private final String leagueName;
    private final String sportName;

    private FetchScheduleRequest(String Id, String leagueName, String sportName) {
        this.Id = Id;
        this.leagueName = leagueName;
        this.sportName = sportName;
    }

    public String getId() {
        return Id;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public String getSportName() {
        return sportName;
    }

    @Override
    public String toString() {
        return "FetchScheduleRequest {Id = " + Id + ", LeagueName = " + leagueName + ", SportName = " + sportName + "}";
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String Id;
        private String leagueName;
        private String sportName;

        public Builder withId(String Id) {
            this.Id = Id;
            return this;
        }

        public Builder withLeagueName(String leagueName) {
            this.leagueName = leagueName;
            return this;
        }

        public Builder withSportName(String sportName) {
            this.sportName = sportName;
            return this;
        }

        public FetchScheduleRequest build() {
            return new FetchScheduleRequest(Id, leagueName, sportName);
        }
    }
}
