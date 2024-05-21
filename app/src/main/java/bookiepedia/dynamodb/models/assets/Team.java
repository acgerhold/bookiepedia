package bookiepedia.dynamodb.models.assets;

public class Team {

    private String leagueId;
    // leagues > 0 > id
    private String teamId;
    // events > 0/1 > competitions > competitors > 0/1 > team > id
    private String teamName;
    // events > 0/1 > competitions > competitors > 0/1 > team > displayName
    private String teamNameAbr;
    // events > 0/1 > competitions > competitors > 0/1 > team > abbreviation
    private String teamLogo;
    // events > 0/1 > competitions > competitors > 0/1 > team > logo
    private String teamColor;
    // events > 0/1 > competitions > competitors > 0/1 > team > abbreviation
    private String teamRosterLink;
    // events > 0/1 > competitions > competitors > 0/1 > team > links > 1 > href
    private String teamScheduleLink;
    // events > 0/1 > competitions > competitors > 0/1 > team > links > 3 > href


    // GETTERS

    public String getLeagueId() {
        return leagueId;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamNameAbr() {
        return teamNameAbr;
    }

    public String getTeamLogo() {
        return teamLogo;
    }

    public String getTeamColor() {
        return teamColor;
    }

    public String getTeamRosterLink() {
        return teamRosterLink;
    }

    public String getTeamScheduleLink() {
        return teamScheduleLink;
    }

    // SETTERS

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTeamNameAbr(String teamNameAbr) {
        this.teamNameAbr = teamNameAbr;
    }

    public void setTeamLogo(String teamLogo) {
        this.teamLogo = teamLogo;
    }

    public void setTeamColor(String teamColor) {
        this.teamColor = teamColor;
    }

    public void setTeamRosterLink(String teamRosterLink) {
        this.teamRosterLink = teamRosterLink;
    }

    public void setTeamScheduleLink(String teamScheduleLink) {
        this.teamScheduleLink = teamScheduleLink;
    }
}
