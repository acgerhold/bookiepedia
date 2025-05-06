package bookiepedia.activities.requests;

public class FetchScheduleRequest {

    private final String id;
    private final String leagueName;
    private final String sportName;

    private FetchScheduleRequest(String id, String leagueName, String sportName) {
        this.id = id;
        this.leagueName = leagueName;
        this.sportName = sportName;
    }

    public String getId() {
        return id;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public String getSportName() {
        return sportName;
    }

    @Override
    public String toString() {
        return "FetchScheduleRequest {ID = " + id + ", leagueName = " + leagueName + ", sportName = " + sportName + "}";
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private String leagueName;
        private String sportName;

        public Builder withId(String id) {
            this.id = id;
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
            return new FetchScheduleRequest(id, leagueName, sportName);
        }
    }
}
