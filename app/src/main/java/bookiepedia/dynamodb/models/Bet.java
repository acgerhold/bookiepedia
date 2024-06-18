package bookiepedia.dynamodb.models;

import bookiepedia.converters.BigDecimalConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.math.BigDecimal;

@DynamoDBTable(tableName = "Bet")
public class Bet {

    private String weeklyHistoryId;
    private String betId;
    private String userId;
    private String eventId;
    private Double amountWagered;
    private Double odds;
    private String teamBetOn;
    // Tells which team user chose to bet on if spread or money line markets, blank for total\
    private Double projection;
    // Shows projection for spread or total markets (O 207.5, -2.5), blank for money line
    private String bettingMarket;
    private String bookmakerId;
    // May be able to pull projection, bettingMarket, and bookmakerId from Odds object for Event and not have as attr
    private String datePlaced;
    private Double gainOrLoss;
    // May not need these as attributes, might be able to pull directly from Event using the EventId?
    private String teamHome;
    private Integer scoreHome;
    private String teamHomeLogo;
    private String teamAway;
    private Integer scoreAway;
    private String teamAwayLogo;
    private String teamWinner;
    private Integer scoreTotal;
    private String eventName;
    private String eventHeadline;
    private String eventDate;
    private String eventStatus;

    // GETTERS

    @DynamoDBHashKey(attributeName = "weeklyHistoryId")
    public String getWeeklyHistoryId() {
        return weeklyHistoryId;
    }

    @DynamoDBRangeKey(attributeName = "betId")
    public String getBetId() {
        return betId;
    }

    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    @DynamoDBAttribute(attributeName = "eventId")
    public String getEventId() {
        return eventId;
    }

    @DynamoDBAttribute(attributeName = "amountWagered")
    public Double getAmountWagered() {
        return amountWagered;
    }

    @DynamoDBAttribute(attributeName = "odds")
    public Double getOdds() {
        return odds;
    }

    @DynamoDBAttribute(attributeName = "teamBetOn")
    public String getTeamBetOn() {
        return teamBetOn;
    }

    @DynamoDBAttribute(attributeName = "projection")
    public Double getProjection() {
        return projection;
    }

    @DynamoDBAttribute(attributeName = "bettingMarket")
    public String getBettingMarket() {
        return bettingMarket;
    }

    @DynamoDBAttribute(attributeName = "bookmakerId")
    public String getBookmakerId() {
        return bookmakerId;
    }

    @DynamoDBAttribute(attributeName = "datePlaced")
    public String getDatePlaced() {
        return datePlaced;
    }

    @DynamoDBAttribute(attributeName = "gainOrLoss")
    public Double getGainOrLoss() {
        return gainOrLoss;
    }

    @DynamoDBAttribute(attributeName = "teamHome")
    public String getTeamHome() {
        return teamHome;
    }

    @DynamoDBAttribute(attributeName = "scoreHome")
    public Integer getScoreHome() {
        return scoreHome;
    }

    @DynamoDBAttribute(attributeName = "teamHomeLogo")
    public String getTeamHomeLogo() {
        return teamHomeLogo;
    }

    @DynamoDBAttribute(attributeName = "teamAway")
    public String getTeamAway() {
        return teamAway;
    }

    @DynamoDBAttribute(attributeName = "scoreAway")
    public Integer getScoreAway() {
        return scoreAway;
    }

    @DynamoDBAttribute(attributeName = "teamAwayLogo")
    public String getTeamAwayLogo() {
        return teamAwayLogo;
    }

    @DynamoDBAttribute(attributeName = "teamWinner")
    public String getTeamWinner() {
        return teamWinner;
    }

    @DynamoDBAttribute(attributeName = "scoreTotal")
    public Integer getScoreTotal() {
        return scoreTotal;
    }

    @DynamoDBAttribute(attributeName = "eventName")
    public String getEventName() {
        return eventName;
    }

    @DynamoDBAttribute(attributeName = "eventHeadline")
    public String getEventHeadline() {
        return eventHeadline;
    }

    @DynamoDBAttribute(attributeName = "eventDate")
    public String getEventDate() {
        return eventDate;
    }

    @DynamoDBAttribute(attributeName = "eventStatus")
    public String getEventStatus() {
        return eventStatus;
    }

    // SETTERS

    public void setWeeklyHistoryId(String weeklyHistoryId) {
        this.weeklyHistoryId = weeklyHistoryId;
    }

    public void setBetId(String betId) {
        this.betId = betId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setAmountWagered(Double amountWagered) {
        this.amountWagered = amountWagered;
    }

    public void setOdds(Double odds) {
        this.odds = odds;
    }

    public void setTeamBetOn(String teamBetOn) {
        this.teamBetOn = teamBetOn;
    }

    public void setProjection(Double projection) {
        this.projection = projection;
    }

    public void setBettingMarket(String bettingMarket) {
        this.bettingMarket = bettingMarket;
    }

    public void setBookmakerId(String bookmakerId) {
        this.bookmakerId = bookmakerId;
    }

    public void setDatePlaced(String datePlaced) {
        this.datePlaced = datePlaced;
    }

    public void setGainOrLoss(Double gainOrLoss) {
        this.gainOrLoss = gainOrLoss;
    }

    public void setTeamHome(String teamHome) {
        this.teamHome = teamHome;
    }

    public void setScoreHome(Integer scoreHome) {
        this.scoreHome = scoreHome;
    }

    public void setTeamHomeLogo(String teamHomeLogo) {
        this.teamHomeLogo = teamHomeLogo;
    }

    public void setTeamAway(String teamAway) {
        this.teamAway = teamAway;
    }

    public void setScoreAway(Integer scoreAway) {
        this.scoreAway = scoreAway;
    }

    public void setTeamAwayLogo(String teamAwayLogo) {
        this.teamAwayLogo = teamAwayLogo;
    }

    public void setTeamWinner(String teamWinner) {
        this.teamWinner = teamWinner;
    }

    public void setScoreTotal(Integer scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventHeadline(String eventHeadline) {
        this.eventHeadline = eventHeadline;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }
}
