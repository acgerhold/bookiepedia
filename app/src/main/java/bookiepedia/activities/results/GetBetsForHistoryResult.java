package bookiepedia.activities.results;

import bookiepedia.dynamodb.models.Bet;
import bookiepedia.models.BetModel;

import java.util.List;

public class GetBetsForHistoryResult {

    private final List<BetModel> betList;

    private GetBetsForHistoryResult(List<BetModel> betList) {
        this.betList = betList;
    }

    public List<BetModel> getBetList() {
        return betList;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private List<BetModel> betList;

        public Builder withBetList(List<BetModel> betList) {
            this.betList = betList;
            return this;
        }

        public GetBetsForHistoryResult build() {
            return new GetBetsForHistoryResult(betList);
        }
    }
}
