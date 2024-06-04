package bookiepedia.models.assets;

import java.util.Objects;

public class LeagueModel {

    private final String leagueId;
    private final String leagueName;
    private final String seasonStatusId;
    private final String seasonStatus;
    private final String seasonYear;
    private final String leagueLogo;

    public LeagueModel(String leagueId, String leagueName, String seasonStatusId, String seasonStatus,
                       String seasonYear, String leagueLogo) {
        this.leagueId = leagueId;
        this.leagueName = leagueName;
        this.seasonStatusId = seasonStatusId;
        this.seasonStatus = seasonStatus;
        this.seasonYear = seasonYear;
        this.leagueLogo = leagueLogo;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public String getSeasonStatusId() {
        return seasonStatusId;
    }

    public String getSeasonStatus() {
        return seasonStatus;
    }

    public String getSeasonYear() {
        return seasonYear;
    }

    public String getLeagueLogo() {
        return leagueLogo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LeagueModel that = (LeagueModel) o;
        return leagueId.equals(that.leagueId) &&
                seasonStatusId.equals(that.seasonStatusId) &&
                seasonYear.equals(that.seasonYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leagueId, seasonStatusId, seasonYear);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String leagueId;
        private String leagueName;
        private String seasonStatusId;
        private String seasonStatus;
        private String seasonYear;
        private String leagueLogo;

        public Builder withLeagueId(String leagueId) {
            this.leagueId = leagueId;
            return this;
        }

        public Builder withLeagueName(String leagueName) {
            this.leagueName = leagueName;
            return this;
        }

        public Builder withSeasonStatusId(String seasonStatusId) {
            this.seasonStatusId = seasonStatusId;
            return this;
        }

        public Builder withSeasonStatus(String seasonStatus) {
            this.seasonStatus = seasonStatus;
            return this;
        }

        public Builder withSeasonYear(String seasonYear) {
            this.seasonYear = seasonYear;
            return this;
        }

        public Builder withLeagueLogo(String leagueLogo) {
            this.leagueLogo = leagueLogo;
            return this;
        }

        public LeagueModel build() {
            return new LeagueModel(leagueId, leagueName, seasonStatusId, seasonStatus, seasonYear, leagueLogo);
        }
    }
}
