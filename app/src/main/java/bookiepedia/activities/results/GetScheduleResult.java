package bookiepedia.activities.results;

import bookiepedia.models.ScheduleModel;

public class GetScheduleResult {

    private final ScheduleModel schedule;

    private GetScheduleResult(ScheduleModel schedule) {
        this.schedule = schedule;
    }

    public ScheduleModel getSchedule() {
        return schedule;
    }

    @Override
    public String toString() {
        return "GetScheduleResult{schedule=" + schedule + "}";
    }

    //CHECKSTYLE:OFF:BUILDER
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ScheduleModel schedule;

        public Builder withSchedule(ScheduleModel schedule) {
            this.schedule = schedule;
            return this;
        }

        public GetScheduleResult build() {
            return new GetScheduleResult(schedule);
        }
    }

}
