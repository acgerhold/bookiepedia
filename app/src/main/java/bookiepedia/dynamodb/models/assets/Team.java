package bookiepedia.dynamodb.models.assets;

import java.util.List;

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
    private String teamAlternateColor;
    private List<String> teamLinks;


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

    public String getTeamAlternateColor() {
        return teamAlternateColor;
    }

    public List<String> getTeamLinks() {
        return teamLinks;
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

    public void setTeamAlternateColor(String teamAlternateColor) {
        this.teamAlternateColor = teamAlternateColor;
    }

    public void setTeamLinks(List<String> teamLinks) {
        this.teamLinks = teamLinks;
    }
}
