package bookiepedia.models;

import java.math.BigDecimal;
import java.util.Objects;

public class BetModel {

    private final String weeklyHistoryId;
    private final String betId;
    private final String userId;
    private final String eventId;
    private final Double amountWagered;
    private final Double odds;
    private final String teamBetOn;
    private final Double projection;
    private final String bettingMarket;
    private final String bookmakerId;
    private final String datePlaced;
    private final Double gainOrLoss;
    private final String teamHome;
    private final Integer scoreHome;
    private final String teamHomeLogo;
    private final String teamAway;
    private final Integer scoreAway;
    private final String teamAwayLogo;
    private final String teamWinner;
    private final Integer scoreTotal;
    private final String eventName;
    private final String eventHeadline;
    private final String eventDate;
    private final String eventStatus;

    /**
     * Constructor for a Bet model.
     * @param weeklyHistoryId - The ID associated with a weekly history
     * @param betId - The ID of the bet
     * @param userId - The ID of the user
     * @param eventId - The ID of the event
     * @param amountWagered - The amount wagered
     * @param odds - The odds of the bet
     * @param teamBetOn - The team bet on
     * @param projection - The projection of the bet
     * @param bettingMarket - The betting market
     * @param bookmakerId - The ID of the bookmaker
     * @param datePlaced - The date the bet was placed
     * @param gainOrLoss - The gain or loss from the bet
     * @param teamHome - The home team
     * @param scoreHome - The home team score
     * @param teamHomeLogo - The home team logo
     * @param teamAway - The away team
     * @param scoreAway - The away team score
     * @param teamAwayLogo - The away team logo
     * @param teamWinner - The winning team
     * @param scoreTotal - The total score
     * @param eventName - The name of the event
     * @param eventHeadline - The headline of the event
     * @param eventDate - The date of the event
     * @param eventStatus - The status of the event
     */
    public BetModel(String weeklyHistoryId, String betId, String userId, String eventId, Double amountWagered,
                    Double odds, String teamBetOn, Double projection, String bettingMarket,
                    String bookmakerId, String datePlaced, Double gainOrLoss, String teamHome, Integer scoreHome,
                    String teamHomeLogo, String teamAway, Integer scoreAway, String teamAwayLogo, String teamWinner,
                    Integer scoreTotal, String eventName, String eventHeadline, String eventDate, String eventStatus) {
        this.weeklyHistoryId = weeklyHistoryId;
        this.betId = betId;
        this.userId = userId;
        this.eventId = eventId;
        this.amountWagered = amountWagered;
        this.odds = odds;
        this.teamBetOn = teamBetOn;
        this.projection = projection;
        this.bettingMarket = bettingMarket;
        this.bookmakerId = bookmakerId;
        this.datePlaced = datePlaced;
        this.gainOrLoss = gainOrLoss;
        this.teamHome = teamHome;
        this.scoreHome = scoreHome;
        this.teamHomeLogo = teamHomeLogo;
        this.teamAway = teamAway;
        this.scoreAway = scoreAway;
        this.teamAwayLogo = teamAwayLogo;
        this.teamWinner = teamWinner;
        this.scoreTotal = scoreTotal;
        this.eventName = eventName;
        this.eventHeadline = eventHeadline;
        this.eventDate = eventDate;
        this.eventStatus = eventStatus;
    }

    public String getWeeklyHistoryId() {
        return weeklyHistoryId;
    }

    public String getBetId() {
        return betId;
    }

    public String getUserId() {
        return userId;
    }

    public String getEventId() {
        return eventId;
    }

    public Double getAmountWagered() {
        return amountWagered;
    }

    public Double getOdds() {
        return odds;
    }

    public String getTeamBetOn() {
        return teamBetOn;
    }

    public Double getProjection() {
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

    public Double getGainOrLoss() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BetModel betModel = (BetModel) o;
        return weeklyHistoryId.equals(betModel.weeklyHistoryId) &&
                betId.equals(betModel.betId) &&
                userId.equals(betModel.userId) &&
                eventId.equals(betModel.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weeklyHistoryId, betId, userId, eventId);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String weeklyHistoryId;
        private String betId;
        private String userId;
        private String eventId;
        private Double amountWagered;
        private Double odds;
        private String teamBetOn;
        private Double projection;
        private String bettingMarket;
        private String bookmakerId;
        private String datePlaced;
        private Double gainOrLoss;
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

        public Builder withWeeklyHistoryId(String weeklyHistoryId) {
            this.weeklyHistoryId = weeklyHistoryId;
            return this;
        }

        public Builder withBetId(String betId) {
            this.betId = betId;
            return this;
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder withAmountWagered(Double amountWagered) {
            this.amountWagered = amountWagered;
            return this;
        }

        public Builder withOdds(Double odds) {
            this.odds = odds;
            return this;
        }

        public Builder withTeamBetOn(String teamBetOn) {
            this.teamBetOn = teamBetOn;
            return this;
        }

        public Builder withProjection(Double projection) {
            this.projection = projection;
            return this;
        }

        public Builder withBettingMarket(String bettingMarket) {
            this.bettingMarket = bettingMarket;
            return this;
        }

        public Builder withBookmakerId(String bookmakerId) {
            this.bookmakerId = bookmakerId;
            return this;
        }

        public Builder withDatePlaced(String datePlaced) {
            this.datePlaced = datePlaced;
            return this;
        }

        public Builder withGainOrLoss(Double gainOrLoss) {
            this.gainOrLoss = gainOrLoss;
            return this;
        }

        public Builder withTeamHome(String teamHome) {
            this.teamHome = teamHome;
            return this;
        }

        public Builder withScoreHome(Integer scoreHome) {
            this.scoreHome = scoreHome;
            return this;
        }

        public Builder withTeamHomeLogo(String teamHomeLogo) {
            this.teamHomeLogo = teamHomeLogo;
            return this;
        }

        public Builder withTeamAway(String teamAway) {
            this.teamAway = teamAway;
            return this;
        }

        public Builder withScoreAway(Integer scoreAway) {
            this.scoreAway = scoreAway;
            return this;
        }

        public Builder withTeamAwayLogo(String teamAwayLogo) {
            this.teamAwayLogo = teamAwayLogo;
            return this;
        }

        public Builder withTeamWinner(String teamWinner) {
            this.teamWinner = teamWinner;
            return this;
        }

        public Builder withScoreTotal(Integer scoreTotal) {
            this.scoreTotal = scoreTotal;
            return this;
        }

        public Builder withEventName(String eventName) {
            this.eventName = eventName;
            return this;
        }

        public Builder withEventHeadline(String eventHeadline) {
            this.eventHeadline = eventHeadline;
            return this;
        }

        public Builder withEventDate(String eventDate) {
            this.eventDate = eventDate;
            return this;
        }

        public Builder withEventStatus(String eventStatus) {
            this.eventStatus = eventStatus;
            return this;
        }

        public BetModel build() {
            return new BetModel(weeklyHistoryId, betId, userId, eventId, amountWagered, odds, teamBetOn, projection,
                    bettingMarket, bookmakerId, datePlaced, gainOrLoss, teamHome, scoreHome, teamHomeLogo, teamAway,
                    scoreAway, teamAwayLogo, teamWinner, scoreTotal, eventName, eventHeadline, eventDate, eventStatus);
        }
    }
}
