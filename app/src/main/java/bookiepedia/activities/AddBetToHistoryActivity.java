package bookiepedia.activities;

import bookiepedia.activities.requests.AddBetToHistoryRequest;
import bookiepedia.activities.results.AddBetToHistoryResult;
import bookiepedia.converters.ModelConverter;
import bookiepedia.dynamodb.BetDAO;
import bookiepedia.dynamodb.EspnDAO.constants.EspnRequestConstants;
import bookiepedia.dynamodb.bettinghistory.WeeklyHistoryDAO;
import bookiepedia.dynamodb.models.Bet;
import bookiepedia.dynamodb.models.bettinghistory.WeeklyHistory;
import bookiepedia.models.BetModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class AddBetToHistoryActivity {
    private final Logger log = LogManager.getLogger();
    private final BetDAO betDAO;
    private final WeeklyHistoryDAO weeklyHistoryDAO;

    @Inject
    public AddBetToHistoryActivity(BetDAO betDAO, WeeklyHistoryDAO weeklyHistoryDAO) {
        this.betDAO = betDAO;
        this.weeklyHistoryDAO = weeklyHistoryDAO;
    }

    public AddBetToHistoryResult handleRequest(final AddBetToHistoryRequest addBetToHistoryRequest) {
        log.info("Received AddBetToHistoryRequest {} ", addBetToHistoryRequest);

        WeeklyHistory weeklyHistory = weeklyHistoryDAO.getWeeklyHistory(addBetToHistoryRequest.getWeeklyHistoryId());

        Bet betToAdd = new Bet();

        betToAdd.setWeeklyHistoryId(weeklyHistory.getWeeklyHistoryId());
        // Set this to the WeeklyHistory ID that is returned from getWeeklyHistory() instead of the request
        // This makes sure the ID is not set to a nonexistent WeeklyHistory from the request
        // Uses the new ID generated from getWeeklyHistory() instead if WeeklyHistory doesn't exist

        betToAdd.setBetId(addBetToHistoryRequest.getBetId());
        betToAdd.setUserId(addBetToHistoryRequest.getUserId());
        betToAdd.setEventId(addBetToHistoryRequest.getEventId());
        betToAdd.setAmountWagered(addBetToHistoryRequest.getAmountWagered());
        betToAdd.setOdds(addBetToHistoryRequest.getOdds());
        betToAdd.setTeamBetOn(addBetToHistoryRequest.getTeamBetOn());
        betToAdd.setProjection(addBetToHistoryRequest.getProjection());
        betToAdd.setBettingMarket(addBetToHistoryRequest.getBettingMarket());
        betToAdd.setBookmakerId(addBetToHistoryRequest.getBookmakerId());

        betToAdd.setDatePlaced(String.valueOf(EspnRequestConstants.NOW));
        // Just set this automatically to the time of the request

        betToAdd.setGainOrLoss(addBetToHistoryRequest.getGainOrLoss());
        betToAdd.setTeamHome(addBetToHistoryRequest.getTeamHome());
        betToAdd.setScoreHome(addBetToHistoryRequest.getScoreHome());
        betToAdd.setTeamHomeLogo(addBetToHistoryRequest.getTeamHomeLogo());
        betToAdd.setTeamAway(addBetToHistoryRequest.getTeamAway());
        betToAdd.setScoreAway(addBetToHistoryRequest.getScoreAway());
        betToAdd.setTeamAwayLogo(addBetToHistoryRequest.getTeamAwayLogo());
        betToAdd.setTeamWinner(addBetToHistoryRequest.getTeamWinner());
        betToAdd.setScoreTotal(addBetToHistoryRequest.getScoreTotal());
        betToAdd.setEventName(addBetToHistoryRequest.getEventName());
        betToAdd.setEventHeadline(addBetToHistoryRequest.getEventHeadline());
        betToAdd.setEventDate(addBetToHistoryRequest.getEventDate());
        betToAdd.setEventStatus(addBetToHistoryRequest.getEventStatus());

        betDAO.saveBet(betToAdd);

        List<Bet> betList = weeklyHistory.getWeeklyBetList();
        betList.add(betToAdd);

        weeklyHistory.setWeeklyBetList(betList);
        weeklyHistoryDAO.saveWeeklyHistory(weeklyHistory);

        List<BetModel> betModels = new ModelConverter().toBetModelList(betList);
        return AddBetToHistoryResult.builder()
                .withBetList(betModels)
                .build();
    }
}
