package bookiepedia.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;

@DynamoDBTable(tableName = "Event")
public class Event {

    private String eventId;
    // events > 0,1,2 ... > id (401571998)
    private String scheduleId;
    private String eventName;
    // events > 0,1,2 ... > name (Indiana Pacers at Boston Celtics)
    private String eventNameShort;
    // events > 0,1,2 ... > shortName (IND @ BOS)
    private String eventHeadline;
    // events > 0,1,2 ... > competitions > 0 > notes > 0 > headline (East Finals - Game 1)
    private String leagueId;
    // leagues > 0 > id (46)
    private String eventDate;
    // events > 0,1,2 ... > date (2024-05-22T00:00Z)
    private String eventSeasonId;
    // events > 0,1,2 ... > season > type (3 - postseason)
    private String teamHome;
    // event > 0,1,2 ... > competitions > 0 > competitors > 0,1 > homeAway
    // IF(homeAway = "home") > teamHome = event > 0,1,2 ... > competitions > 0 > competitors > 0,1 > id
    private String teamAway;
    // event > 0,1,2 ... > competitions > 0 > competitors > 0,1 > homeAway
    // IF(homeAway = "away") > teamHome = event > 0,1,2 ... > competitions > 0 > competitors > 0,1 > id
    private String eventStatusId;
    // events > 0,1,2 ... > status > type > id (1 - Scheduled, 2 - Current, 3 - Completed)
    private String eventStatus;
    // IF(eventStatusId == 1) { eventStatus = "TBD"; }
    // IF(eventStatusId == 2) { CASE periods:
    // 4 > eventStatus = "Q" + period + " - " + clock + " " + displayClock (Q3 - 2:30 0.7)
    // 3 > eventStatus = "P" + period + " - " + clock + " " + displayClock (P3 - 2:30 0.7)
    // }
    // IF(eventStatusId == 3) { eventStatus = "Final"; }
    // completed = events > 0,1,2 .. > status > type > completed
    // periods = events > 0,1,2 ... > competitions > 0 > format > regulation > periods
    // period = events > 0,1,2 ... > status > String.valueOf(period)
    // displayClock = events > 0,1,2 ... > status > displayClock
    // clock =  events > 0,1,2 ... > status > String.valueOf(clock)
    private String teamWinner;
    // IF(winner == true) { teamWinner = id }
    // winner = events > 0,1,2 ... > competitions > 0 > competitors > 0,1 > winner
    private Integer scoreHome;
    // IF(homeAway == "home") { scoreHome = score }
    // score = events > 0,1,2 ... > competitions > 0 > competitors > 0,1 > Integer.valueOf(score)
    private Integer scoreAway;
    // IF(homeAway == "away") { scoreAway = score }
    // score = events > 0,1,2 ... > competitions > 0 > competitors > 0,1 > Integer.valueOf(score)
    private Integer scoreTotal;
    // scoreTotal = scoreHome + scoreAway;
    //private Map<String, Map<String, Integer>> currentOdds;
    // API includes some current odds/projections from ESPN Bet:
    // odds/projections for totals, projections for spreads, neither for moneyline ??
    // Can use this as a placeholder before implementing The Odds API
    // May be able to blend the two API responses, similar format, ID's for bookmakers
    // Will later be refactored to an "Odds" object/model
    // TBD
    private List<String> links;
    // Can I embed these links instead of opening new tab?
    // events > 0,1,2 ... > links > 0 > href ("https://www.espn.com/nba…401671998/pacers-celtics")

    // GETTERS

    @DynamoDBAttribute(attributeName = "eventId")
    public String getEventId() {
        return eventId;
    }

     @DynamoDBHashKey(attributeName = "scheduleId")
     public String getScheduleId() {
        return scheduleId;
     }

    @DynamoDBAttribute(attributeName = "eventName")
    public String getEventName() {
        return eventName;
    }

    @DynamoDBAttribute(attributeName = "eventNameShort")
    public String getEventNameShort() {
        return eventNameShort;
    }

    @DynamoDBAttribute(attributeName = "eventHeadline")
    public String getEventHeadline() {
        return eventHeadline;
    }

    @DynamoDBAttribute(attributeName = "eventDate")
    public String getEventDate() {
        return eventDate;
    }

    @DynamoDBAttribute(attributeName = "eventSeasonId")
    public String getEventSeasonId() {
        return eventSeasonId;
    }

    @DynamoDBAttribute(attributeName = "leagueId")
    public String getLeagueId() {
        return leagueId;
    }

    @DynamoDBAttribute(attributeName = "teamHome")
    public String getTeamHome() {
        return teamHome;
    }

    @DynamoDBAttribute(attributeName = "teamAway")
    public String getTeamAway() {
        return teamAway;
    }

    @DynamoDBAttribute(attributeName = "eventStatusId")
    public String getEventStatusId() {
        return eventStatusId;
    }

    @DynamoDBAttribute(attributeName = "eventStatus")
    public String getEventStatus() {
        return eventStatus;
    }

    @DynamoDBAttribute(attributeName = "teamWinner")
    public String getTeamWinner() {
        return teamWinner;
    }

    @DynamoDBAttribute(attributeName = "scoreHome")
    public int getScoreHome() {
        return scoreHome;
    }

    @DynamoDBAttribute(attributeName = "scoreAway")
    public int getScoreAway() {
        return scoreAway;
    }

    @DynamoDBAttribute(attributeName = "scoreAway")
    public int getScoreTotal() {
        return scoreTotal;
    }

    //  public Map<String, Map<String, Integer>> getCurrentOdds() {
    //      return currentOdds;
    //  }

    @DynamoDBAttribute(attributeName = "links")
    public List<String> getLinks() {
        return links;
    }

    // SETTERS

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventNameShort(String eventNameShort) {
        this.eventNameShort = eventNameShort;
    }

    public void setEventHeadline(String eventHeadline) {
        this.eventHeadline = eventHeadline;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventSeasonId(String eventSeason) {
        this.eventSeasonId = eventSeason;
    }

    public void setTeamHome(String teamHome) {
        this.teamHome = teamHome;
    }

    public void setTeamAway(String teamAway) {
        this.teamAway = teamAway;
    }

    public void setEventStatusId(String eventStatusId) {
        this.eventStatusId = eventStatusId;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public void setTeamWinner(String teamWinner) {
        this.teamWinner = teamWinner;
    }

    public void setScoreHome(Integer scoreHome) {
        this.scoreHome = scoreHome;
    }

    public void setScoreAway(Integer scoreAway) {
        this.scoreAway = scoreAway;
    }

    public void setScoreTotal(Integer scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    //  public void setCurrentOdds(Map<String, Map<String, Integer>> currentOdds) {
    //      this.currentOdds = currentOdds;
    //  }

    public void setLinks(List<String> links) {
        this.links = links;
    }
}
