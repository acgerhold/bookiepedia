package bookiepedia.dynamodb.bettinghistory;

import bookiepedia.dynamodb.EspnDAO.constants.EspnRequestConstants;
import bookiepedia.dynamodb.models.Bet;
import bookiepedia.dynamodb.models.bettinghistory.WeeklyHistory;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
public class WeeklyHistoryDAO {

    private final DynamoDBMapper dynamoDBMapper;

    @Inject
    public WeeklyHistoryDAO(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public void saveWeeklyHistory(WeeklyHistory weeklyHistory) {
        this.dynamoDBMapper.save(weeklyHistory);
    }

    public WeeklyHistory getWeeklyHistory(String weeklyHistoryId) {
        WeeklyHistory weeklyHistory = this.dynamoDBMapper.load(WeeklyHistory.class, weeklyHistoryId);
        if (weeklyHistory == null) {
            WeeklyHistory newWeeklyHistory = new WeeklyHistory();

            newWeeklyHistory.setWeeklyHistoryId("WH-" + EspnRequestConstants.CURRENT_WEEK_OF_MONTH);
            newWeeklyHistory.setDate(EspnRequestConstants.CURRENT_WEEK_OF_MONTH);

            List<Bet> weeklyBetList = new ArrayList<>();
            newWeeklyHistory.setWeeklyBetList(weeklyBetList);

            saveWeeklyHistory(newWeeklyHistory);
            return newWeeklyHistory;
        }
        return weeklyHistory;
    }

}
