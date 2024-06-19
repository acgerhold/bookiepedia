package bookiepedia.activities;

import bookiepedia.activities.requests.GetBetsForHistoryRequest;
import bookiepedia.activities.results.GetBetsForHistoryResult;
import bookiepedia.converters.ModelConverter;
import bookiepedia.dynamodb.BetDAO;
import bookiepedia.dynamodb.models.Bet;
import bookiepedia.models.BetModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class GetBetsForHistoryActivity {

    private final Logger log = LogManager.getLogger(GetBetsForHistoryActivity.class);

    private final BetDAO betDAO;

    @Inject
    public GetBetsForHistoryActivity(BetDAO betDAO) {
        this.betDAO = betDAO;
    }

    public GetBetsForHistoryResult handleRequest(final GetBetsForHistoryRequest getBetsForHistoryRequest) {
        String requestedId = getBetsForHistoryRequest.getWeeklyHistoryId();

        log.info("Received GetBetsForHistoryRequest " + getBetsForHistoryRequest);

        List<Bet> betList = betDAO.getBetsFromHistory(requestedId);

        List<Bet> betsSorted = betList.stream()
                .sorted(Comparator.comparing(Bet::getDatePlaced))
                .collect(Collectors.toList());

        List<BetModel> betModels = new ArrayList<>();
        for (Bet bet : betsSorted) {
            betModels.add(new ModelConverter().toBetModel(bet));
        }

        return GetBetsForHistoryResult.builder()
                .withBetList(betModels)
                .build();
    }
}
