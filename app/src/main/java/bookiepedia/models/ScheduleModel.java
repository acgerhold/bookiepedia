package bookiepedia.models;

import java.util.List;
import java.util.Objects;

public class ScheduleModel {

    private final String scheduleId;
    private final String leagueId;
    private final String leagueName;
    private final String timestamp;
    private final List<String> eventIdList;
    private final String dateRange;
    private final String scheduleName;

    private ScheduleModel(String scheduleId, String leagueId, String leagueName, String timestamp,
                          List<String> eventIdList, String dateRange, String scheduleName) {
        this.scheduleId = scheduleId;
        this.leagueId = leagueId;
        this.leagueName = leagueName;
        this.timestamp = timestamp;
        this.eventIdList = eventIdList;
        this.dateRange = dateRange;
        this.scheduleName = scheduleName;
    }

    // GETTERS

    public String getScheduleId() {
        return scheduleId;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public List<String> getEventIdList() {
        return eventIdList;
    }

    public String getDateRange() {
        return dateRange;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScheduleModel that = (ScheduleModel) o;
        return scheduleId.equals(that.scheduleId) &&
                timestamp.equals(that.timestamp) &&
                eventIdList.equals(that.eventIdList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, timestamp, eventIdList);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String scheduleId;
        private String leagueId;
        private String leagueName;
        private String timestamp;
        private List<String> eventIdList;
        private String dateRange;
        private String scheduleName;

        public Builder withScheduleId(String scheduleId) {
            this.scheduleId = scheduleId;
            return this;
        }

        public Builder withLeagueId(String leagueId) {
            this.leagueId = leagueId;
            return this;
        }

        public Builder withLeagueName(String leagueName) {
            this.leagueName = leagueName;
            return this;
        }

        public Builder withTimestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder withEventIdList(List<String> eventIdList) {
            this.eventIdList = eventIdList;
            return this;
        }

        public Builder withDateRange(String dateRange) {
            this.dateRange = dateRange;
            return this;
        }

        public Builder withScheduleName(String scheduleName) {
            this.scheduleName = scheduleName;
            return this;
        }

        public ScheduleModel build() {
            return new ScheduleModel(scheduleId, leagueId, leagueName, timestamp,
                    eventIdList, dateRange, scheduleName);
        }
    }
}