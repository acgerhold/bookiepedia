package bookiepedia.dynamodb.models.bettinghistory;

import bookiepedia.converters.BigDecimalConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@DynamoDBTable(tableName = "MonthlyHistory")
public class MonthlyHistory {

    private String monthlyHistoryId;
    private String userId;
    private String monthlyHistoryName;
    private String date;
    private List<String> weeklyHistoryIds;
    // MonthlyHistory will be a collection of a user's WeeklyHistory objects.
    // May eventually create YearlyHistory and/or OverallHistory to slice data further
    private Integer monthlyBetCount;
    // Total bets placed in year
    private BigDecimal monthlyAmountWagered;
    // Total amount wagered in year
    private BigDecimal monthlyGain;
    // Total $ gained in year
    private BigDecimal monthlyLoss;
    // Total $ lost in year
    private List<String> monthlyBetIdList;
    // List of all betIds placed in year
    private Map<String, BigDecimal> monthlySummary;
    // Map of betting markets to +/- of each bet in betIdList
    // When a betId is added to betIdList, retrieves the betting market & its gain or loss if available
    // Add each bet's gain/loss as a value entry for corresponding market
    // This will allow calculations like .size(), sum, average, etc on each entry to add to a user's overall summary

    // GETTERS

    @DynamoDBHashKey(attributeName = "monthlyHistoryId")
    public String getMonthlyHistoryId() {
        return monthlyHistoryId;
    }

    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    @DynamoDBAttribute(attributeName = "monthlyHistoryName")
    public String getMonthlyHistoryName() {
        return monthlyHistoryName;
    }

    @DynamoDBAttribute(attributeName = "getDate")
    public String getDate() {
        return date;
    }

    @DynamoDBAttribute(attributeName = "weeklyHistoryIds")
    public List<String> getWeeklyHistoryIds() {
        return weeklyHistoryIds;
    }

    @DynamoDBAttribute(attributeName = "monthlyBetCount")
    @DynamoDBTypeConverted(converter = BigDecimalConverter.class)
    public Integer getMonthlyBetCount() {
        return monthlyBetCount;
    }

    @DynamoDBAttribute(attributeName = "monthlyAmountWagered")
    @DynamoDBTypeConverted(converter = BigDecimalConverter.class)
    public BigDecimal getMonthlyAmountWagered() {
        return monthlyAmountWagered;
    }

    @DynamoDBAttribute(attributeName = "monthlyGain")
    @DynamoDBTypeConverted(converter = BigDecimalConverter.class)
    public BigDecimal getMonthlyGain() {
        return monthlyGain;
    }

    @DynamoDBAttribute(attributeName = "monthlyLoss")
    @DynamoDBTypeConverted(converter = BigDecimalConverter.class)
    public BigDecimal getMonthlyLoss() {
        return monthlyLoss;
    }

    @DynamoDBAttribute(attributeName = "monthlyBetIdList")
    @DynamoDBTypeConverted(converter = BigDecimalConverter.class)
    public List<String> getMonthlyBetIdList() {
        return monthlyBetIdList;
    }

    @DynamoDBAttribute(attributeName = "monthlySummary")
    @DynamoDBTypeConverted(converter = BigDecimalConverter.class)
    public Map<String, BigDecimal> getMonthlySummary() {
        return monthlySummary;
    }

// SETTERS

    public void setMonthlyHistoryId(String monthlyHistoryId) {
        this.monthlyHistoryId = monthlyHistoryId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setMonthlyHistoryName(String monthlyHistoryName) {
        this.monthlyHistoryName = monthlyHistoryName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWeeklyHistoryIds(List<String> weeklyHistoryIds) {
        this.weeklyHistoryIds = weeklyHistoryIds;
    }

    public void setMonthlyBetCount(Integer monthlyBetCount) {
        this.monthlyBetCount = monthlyBetCount;
    }

    public void setMonthlyAmountWagered(BigDecimal monthlyAmountWagered) {
        this.monthlyAmountWagered = monthlyAmountWagered;
    }

    public void setMonthlyGain(BigDecimal monthlyGain) {
        this.monthlyGain = monthlyGain;
    }

    public void setMonthlyLoss(BigDecimal monthlyLoss) {
        this.monthlyLoss = monthlyLoss;
    }

    public void setMonthlyBetIdList(List<String> monthlyBetIdList) {
        this.monthlyBetIdList = monthlyBetIdList;
    }

    public void setMonthlySummary(Map<String, BigDecimal> monthlySummary) {
        this.monthlySummary = monthlySummary;
    }
}
