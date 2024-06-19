package bookiepedia.activities.results;

import bookiepedia.models.BetModel;

import java.util.ArrayList;
import java.util.List;

public class RemoveBetFromHistoryResult {
    private final List<BetModel> betModelList;

    private RemoveBetFromHistoryResult(List<BetModel> betModelList) {
        this.betModelList = betModelList;
    }

    public List<BetModel> getBetList() {
        return new ArrayList<>(betModelList);
    }

    @Override
    public String toString() {
        return "RemoveBetFromHistoryResult{" +
                "weeklyHistory=" + betModelList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<BetModel> betModelList;

        public Builder withBetList(List<BetModel> betModelList) {
            this.betModelList = new ArrayList<>(betModelList);
            return this;
        }

        public RemoveBetFromHistoryResult build() {
            return new RemoveBetFromHistoryResult(betModelList);
        }
    }
}
