package bookiepedia.activities.results;

import java.util.List;

import bookiepedia.models.assets.LeagueModel;

public class FetchLeaguesResult {
    
    private final String message;
    private final List<LeagueModel> leagueModels;

    private FetchLeaguesResult(String message, List<LeagueModel> leagueModels) {
        this.message = message;
        this.leagueModels = leagueModels;
    }

    public String getMessage() {
        return message;
    }

    public List<LeagueModel> getLeagueModels() {
        return leagueModels;
    }

    @Override
    public String toString() {
        return "FetchScheduleResult {Message = " + message + "}";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String message;
        private List<LeagueModel> leagueModels;

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }
        
        public Builder withLeagueModels(List<LeagueModel> leagueModels) {
            this.leagueModels = leagueModels;
            return this;
        }

        public FetchLeaguesResult build() {
            return new FetchLeaguesResult(message, leagueModels);
        }
    }
}
