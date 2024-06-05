package bookiepedia.dynamodb.models.bettinghistory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class YearlyHistory {

    private String yearlyHistoryId;
    private String userId;
    private String yearlyHistoryName;
    private String date;
    private List<String> weeklyHistoryIds;
    // YearlyHistory will be a collection of a user's WeeklyHistory objects.
    // May eventually create MonthlyHistory and/or OverallHistory to slice data further
    private Integer yearlyBetCount;
    // Total bets placed in year
    private BigDecimal yearlyAmountWagered;
    // Total amount wagered in year
    private BigDecimal yearlyGain;
    // Total $ gained in year
    private BigDecimal yearlyLoss;
    // Total $ lost in year
    private List<String> yearlyBetIdList;
    // List of all betIds placed in year
    private Map<String, BigDecimal> yearlySummary;
    // Map of betting markets to +/- of each bet in betIdList
    // When a betId is added to betIdList, retrieves the betting market & its gain or loss if available
    // Add each bet's gain/loss as a value entry for corresponding market
    // This will allow calculations like .size(), sum, average, etc on each entry to add to a user's overall summary

    // GETTERS

    public String getYearlyHistoryId() {
        return yearlyHistoryId;
    }

    public String getUserId() {
        return userId;
    }

    public String getYearlyHistoryName() {
        return yearlyHistoryName;
    }

    public String getDate() {
        return date;
    }

    public List<String> getWeeklyHistoryIds() {
        return weeklyHistoryIds;
    }

    public Integer getYearlyBetCount() {
        return yearlyBetCount;
    }

    public BigDecimal getYearlyAmountWagered() {
        return yearlyAmountWagered;
    }

    public BigDecimal getYearlyGain() {
        return yearlyGain;
    }

    public BigDecimal getYearlyLoss() {
        return yearlyLoss;
    }

    public List<String> getYearlyBetIdList() {
        return yearlyBetIdList;
    }

    public Map<String, BigDecimal> getYearlySummary() {
        return yearlySummary;
    }

    // SETTERS

    public void setYearlyHistoryId(String yearlyHistoryId) {
        this.yearlyHistoryId = yearlyHistoryId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setYearlyHistoryName(String yearlyHistoryName) {
        this.yearlyHistoryName = yearlyHistoryName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWeeklyHistoryIds(List<String> weeklyHistoryIds) {
        this.weeklyHistoryIds = weeklyHistoryIds;
    }

    public void setYearlyBetCount(Integer yearlyBetCount) {
        this.yearlyBetCount = yearlyBetCount;
    }

    public void setYearlyAmountWagered(BigDecimal yearlyAmountWagered) {
        this.yearlyAmountWagered = yearlyAmountWagered;
    }

    public void setYearlyGain(BigDecimal yearlyGain) {
        this.yearlyGain = yearlyGain;
    }

    public void setYearlyLoss(BigDecimal yearlyLoss) {
        this.yearlyLoss = yearlyLoss;
    }

    public void setYearlyBetIdList(List<String> yearlyBetIdList) {
        this.yearlyBetIdList = yearlyBetIdList;
    }

    public void setYearlySummary(Map<String, BigDecimal> yearlySummary) {
        this.yearlySummary = yearlySummary;
    }
}
