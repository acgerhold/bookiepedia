package bookiepedia.dynamodb.models.assets;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;

@DynamoDBTable(tableName = "Team")
public class Team {

    private String teamId;
    // events > 0/1 > competitions > competitors > 0/1 > team > id
    private String leagueId;
    // leagues > 0 > id
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

    @DynamoDBHashKey(attributeName = "teamId")
    public String getTeamId() {
        return teamId;
    }

    @DynamoDBAttribute(attributeName = "leagueId")
    public String getLeagueId() {
        return leagueId;
    }

    @DynamoDBAttribute(attributeName = "teamName")
    public String getTeamName() {
        return teamName;
    }

    @DynamoDBAttribute(attributeName = "teamNameAbr")
    public String getTeamNameAbr() {
        return teamNameAbr;
    }

    @DynamoDBAttribute(attributeName = "teamLogo")
    public String getTeamLogo() {
        return teamLogo;
    }

    @DynamoDBAttribute(attributeName = "teamColor")
    public String getTeamColor() {
        return teamColor;
    }

    @DynamoDBAttribute(attributeName = "teamAlternateColor")
    public String getTeamAlternateColor() {
        return teamAlternateColor;
    }

    @DynamoDBAttribute(attributeName = "teamLinks")
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
