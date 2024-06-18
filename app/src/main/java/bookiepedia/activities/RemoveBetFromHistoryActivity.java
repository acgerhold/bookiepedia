package bookiepedia.activities;

import bookiepedia.activities.requests.RemoveBetFromHistoryRequest;
import bookiepedia.activities.results.RemoveBetFromHistoryResult;
import bookiepedia.converters.ModelConverter;
import bookiepedia.dynamodb.BetDAO;
import bookiepedia.dynamodb.bettinghistory.WeeklyHistoryDAO;
import bookiepedia.dynamodb.models.Bet;
import bookiepedia.dynamodb.models.bettinghistory.WeeklyHistory;
import bookiepedia.models.BetModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class RemoveBetFromHistoryActivity {

    private final Logger log = LogManager.getLogger();
    private final WeeklyHistoryDAO weeklyHistoryDAO;
    private final BetDAO betDAO;

    @Inject
    public RemoveBetFromHistoryActivity(WeeklyHistoryDAO weeklyHistoryDAO, BetDAO betDAO) {
        this.weeklyHistoryDAO = weeklyHistoryDAO;
        this.betDAO = betDAO;
    }

    public RemoveBetFromHistoryResult handleRequest(final RemoveBetFromHistoryRequest removeBetFromHistoryRequest) {
        log.info("Received RemoveBetFromHistoryRequest {} ", removeBetFromHistoryRequest);

        WeeklyHistory weeklyHistory = weeklyHistoryDAO.getWeeklyHistory(removeBetFromHistoryRequest.getId());

        Bet betToRemove = betDAO.getBet(removeBetFromHistoryRequest.getBetId());

        List<Bet> betList = weeklyHistory.getWeeklyBetList();
        betList.remove(betToRemove);

        weeklyHistory.setWeeklyBetList(betList);
        weeklyHistoryDAO.saveWeeklyHistory(weeklyHistory);

        List<BetModel> betModels = new ModelConverter().toBetModelList(weeklyHistory.getWeeklyBetList());

        return RemoveBetFromHistoryResult.builder()
                .withBetList(betModels)
                .build();
    }
}
