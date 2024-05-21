package bookiepedia.dynamodb.models.assets;

public class League {

    private String leagueId;
    // leagues > 0 > id
    private String leagueName;
    // leagues > 0 > abbreviation
    private String seasonStatusId;
    // leagues > 0 > season > type > id
    private String seasonStatus;
    // leagues > 0 > season > type > name
    private String seasonStartDate;
    // leagues > 0 > season > startDate
    private String seasonEndDate;
    // leagues > 0 > season > endDate
    private String leagueLogo;
    // leagues > 0 > logos > 1 > href


    // GETTERS

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

    public String getSeasonStartDate() {
        return seasonStartDate;
    }

    public String getSeasonEndDate() {
        return seasonEndDate;
    }

    public String getLeagueLogo() {
        return leagueLogo;
    }

    // SETTERS

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public void setSeasonStatusId(String seasonStatusId) {
        this.seasonStatusId = seasonStatusId;
    }

    public void setSeasonStatus(String seasonStatus) {
        this.seasonStatus = seasonStatus;
    }

    public void setSeasonStartDate(String seasonStartDate) {
        this.seasonStartDate = seasonStartDate;
    }

    public void setSeasonEndDate(String seasonEndDate) {
        this.seasonEndDate = seasonEndDate;
    }

    public void setLeagueLogo(String leagueLogo) {
        this.leagueLogo = leagueLogo;
    }
}
