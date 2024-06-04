package bookiepedia.models;

import java.util.List;

public class TeamModel {

    private final String leagueId;
    private final String teamId;
    private final String teamName;
    private final String teamNameAbr;
    private final String teamLogo;
    private final String teamColor;
    private final String teamAlternateColor;
    private final List<String> teamLinks;

    public TeamModel(String leagueId, String teamId, String teamName, String teamNameAbr, String teamLogo,
                     String teamColor, String teamAlternateColor, List<String> teamLinks) {
        this.leagueId = leagueId;
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamNameAbr = teamNameAbr;
        this.teamLogo = teamLogo;
        this.teamColor = teamColor;
        this.teamAlternateColor = teamAlternateColor;
        this.teamLinks = teamLinks;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TeamModel that = (TeamModel) o;
        return teamId.equals(that.teamId) &&
                leagueId.equals(that.leagueId) &&
                teamName.equals(that.teamName);
    }
}
