package bookiepedia.activities.results;

import bookiepedia.models.BetModel;

import java.util.ArrayList;
import java.util.List;

public class AddBetToHistoryResult {

    private final List<BetModel> betList;

    private AddBetToHistoryResult(List<BetModel> betList) {
        this.betList = betList;
    }

    public List<BetModel> getBetList() {
        return betList;
    }

    @Override
    public String toString() {
        return "AddBetToHistoryResult{" +
                "betList=" + betList +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<BetModel> betList;

        public Builder withBetList(List<BetModel> betList) {
            this.betList = new ArrayList<>(betList);
            return this;
        }

        public AddBetToHistoryResult build() {
            return new AddBetToHistoryResult(betList);
        }
    }
}
