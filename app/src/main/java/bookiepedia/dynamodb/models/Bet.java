package bookiepedia.dynamodb.models;

import java.math.BigDecimal;

public class Bet {

    private String betId;
    private String userId;
    private String eventId;
    private BigDecimal amountWagered;
    private BigDecimal odds;
    private String teamBetOn;
    // Tells which team user chose to bet on if spread or money line markets, blank for total\
    private BigDecimal projection;
    // Shows projection for spread or total markets (O 207.5, -2.5), blank for money line
    private String bettingMarket;
    private String bookmakerId;
    // May be able to pull projection, bettingMarket, and bookmakerId from Odds object for Event and not have as attr
    private String datePlaced;
    private BigDecimal gainOrLoss;

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

    public String getBetId() {
        return betId;
    }

    public String getUserId() {
        return userId;
    }

    public String getEventId() {
        return eventId;
    }

    public BigDecimal getAmountWagered() {
        return amountWagered;
    }

    public BigDecimal getOdds() {
        return odds;
    }

    public String getTeamBetOn() {
        return teamBetOn;
    }

    public BigDecimal getProjection() {
        return projection;
    }

    public String getBettingMarket() {
        return bettingMarket;
    }

    public String getBookmakerId() {
        return bookmakerId;
    }

    public String getDatePlaced() {
        return datePlaced;
    }

    public BigDecimal getGainOrLoss() {
        return gainOrLoss;
    }

    public String getTeamHome() {
        return teamHome;
    }

    public Integer getScoreHome() {
        return scoreHome;
    }

    public String getTeamHomeLogo() {
        return teamHomeLogo;
    }

    public String getTeamAway() {
        return teamAway;
    }

    public Integer getScoreAway() {
        return scoreAway;
    }

    public String getTeamAwayLogo() {
        return teamAwayLogo;
    }

    public String getTeamWinner() {
        return teamWinner;
    }

    public Integer getScoreTotal() {
        return scoreTotal;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventHeadline() {
        return eventHeadline;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    // SETTERS

    public void setBetId(String betId) {
        this.betId = betId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setAmountWagered(BigDecimal amountWagered) {
        this.amountWagered = amountWagered;
    }

    public void setOdds(BigDecimal odds) {
        this.odds = odds;
    }

    public void setTeamBetOn(String teamBetOn) {
        this.teamBetOn = teamBetOn;
    }

    public void setProjection(BigDecimal projection) {
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

    public void setGainOrLoss(BigDecimal gainOrLoss) {
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
