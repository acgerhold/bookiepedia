package bookiepedia.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;

@DynamoDBTable(tableName = "Schedule")
public class Schedule {

    private String scheduleId;
    // leagueId + "-" + timestamp (46-2024-05-21)
    private String leagueId;
    // leagues > 0 > id
    private String leagueName;
    // leagues > 0 > abbreviation
    private String timestamp;
    // day > date
    private List<String> eventIdList;
    // events > 0,1,2 ... > id
    private String scheduleName;
    // leagueName + " upcoming events " + dateRange;

    // GETTERS

    @DynamoDBAttribute(attributeName = "scheduleId")
    public String getScheduleId() {
        return scheduleId;
    }

    @DynamoDBHashKey(attributeName = "leagueId")
    public String getLeagueId() {
        return leagueId;
    }

    @DynamoDBAttribute(attributeName = "leagueName")
    public String getLeagueName() {
        return leagueName;
    }

    @DynamoDBRangeKey(attributeName = "timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @DynamoDBAttribute(attributeName = "eventIdList")
    public List<String> getEventIdList() {
        return eventIdList;
    }

    @DynamoDBAttribute(attributeName = "scheduleName")
    public String getScheduleName() {
        return scheduleName;
    }

    // SETTERS

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setEventIdList(List<String> eventIdList) {
        this.eventIdList = eventIdList;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }
}
