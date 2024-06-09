package bookiepedia.dynamodb.models.assets;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "League")
public class League {

    private String leagueId;
    // leagues > 0 > id
    private String leagueName;
    // leagues > 0 > abbreviation
    private String seasonStatusId;
    // leagues > 0 > season > type > id
    private String seasonStatus;
    // leagues > 0 > season > type > name
    private String seasonYear;
    // leagues > 0 > season > year
    private String leagueLogo;
    // leagues > 0 > logos > 1 > href

    // GETTERS

    @DynamoDBHashKey(attributeName = "leagueId")
    public String getLeagueId() {
        return leagueId;
    }

    @DynamoDBAttribute(attributeName = "leagueName")
    public String getLeagueName() {
        return leagueName;
    }

    @DynamoDBAttribute(attributeName = "seasonStatusId")
    public String getSeasonStatusId() {
        return seasonStatusId;
    }

    @DynamoDBAttribute(attributeName = "seasonStatus")
    public String getSeasonStatus() {
        return seasonStatus;
    }

    @DynamoDBAttribute(attributeName = "seasonYear")
    public String getSeasonYear() {
        return seasonYear;
    }

    @DynamoDBAttribute(attributeName = "leagueLogo")
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

    public void setSeasonYear(String seasonYear) {
        this.seasonYear = seasonYear;
    }

    public void setLeagueLogo(String leagueLogo) {
        this.leagueLogo = leagueLogo;
    }
}
