package bookiepedia.models.assets;

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

    /**
     * Constructor for a 'Team' model.
     * @param leagueId - The ID of a league associated with a Team
     * @param teamId - An ID associated with a Team
     * @param teamName - The name of the Team
     * @param teamNameAbr - An abbreviated version of 'teamName'
     * @param teamLogo - External link to a Team's logo
     * @param teamColor - A Team's main color as a color code
     * @param teamAlternateColor - A Team's alternate color as a color code
     * @param teamLinks - External links to ESPN's roster, statistics, schedule, and home page for a Team
     */
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
