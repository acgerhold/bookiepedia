package bookiepedia.dynamodb.models.bettinghistory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class WeeklyHistory {

    private String weeklyHistoryId;
    private String userId;
    private String weeklyHistoryName;
    private String date;
    // Starting by organizing histories by week number/month/year
    // Ex. 1-05-2024, 2-05-2024, ..., 1-06-2024, 2-05, 2024 ...
    private Integer weeklyBetCount;
    // Total bets placed in week
    private BigDecimal weeklyAmountWagered;
    // Total amount wagered in week
    private BigDecimal weeklyGain;
    // Total $ gained in week
    private BigDecimal weeklyLoss;
    // Total $ lost in week
    private List<String> weeklyBetIdList;
    // List of all betIds placed in week
    private Map<String, BigDecimal> weeklySummary;
    // Map of betting markets to +/- of each bet in betIdList
    // When a betId is added to betIdList, retrieves the betting market & its gain or loss if available
    // Add each bet's gain/loss as a value entry for corresponding market
    // This will allow calculations like .size(), sum, average, etc on each entry to add to a user's overall summary

    // GETTERS

    public String getWeeklyHistoryId() {
        return weeklyHistoryId;
    }

    public String getUserId() {
        return userId;
    }

    public String getWeeklyHistoryName() {
        return weeklyHistoryName;
    }

    public String getDate() {
        return date;
    }

    public Integer getWeeklyBetCount() {
        return weeklyBetCount;
    }

    public BigDecimal getWeeklyAmountWagered() {
        return weeklyAmountWagered;
    }

    public BigDecimal getWeeklyGain() {
        return weeklyGain;
    }

    public BigDecimal getWeeklyLoss() {
        return weeklyLoss;
    }

    public List<String> getWeeklyBetIdList() {
        return weeklyBetIdList;
    }

    public Map<String, BigDecimal> getWeeklySummary() {
        return weeklySummary;
    }

    // SETTERS


    public void setWeeklyHistoryId(String weeklyHistoryId) {
        this.weeklyHistoryId = weeklyHistoryId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setWeeklyHistoryName(String weeklyHistoryName) {
        this.weeklyHistoryName = weeklyHistoryName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWeeklyBetCount(Integer weeklyBetCount) {
        this.weeklyBetCount = weeklyBetCount;
    }

    public void setWeeklyAmountWagered(BigDecimal weeklyAmountWagered) {
        this.weeklyAmountWagered = weeklyAmountWagered;
    }

    public void setWeeklyGain(BigDecimal weeklyGain) {
        this.weeklyGain = weeklyGain;
    }

    public void setWeeklyLoss(BigDecimal weeklyLoss) {
        this.weeklyLoss = weeklyLoss;
    }

    public void setWeeklyBetIdList(List<String> weeklyBetIdList) {
        this.weeklyBetIdList = weeklyBetIdList;
    }

    public void setWeeklySummary(Map<String, BigDecimal> weeklySummary) {
        this.weeklySummary = weeklySummary;
    }
}
