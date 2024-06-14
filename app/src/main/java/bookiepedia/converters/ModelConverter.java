package bookiepedia.converters;

import bookiepedia.dynamodb.models.Event;
import bookiepedia.dynamodb.models.Schedule;
import bookiepedia.models.EventModel;
import bookiepedia.models.ScheduleModel;

public class ModelConverter {

    public ScheduleModel toScheduleModel(Schedule schedule) {
        return ScheduleModel.builder()
                .withScheduleId(schedule.getScheduleId())
                .withLeagueId(schedule.getLeagueId())
                .withLeagueName(schedule.getLeagueName())
                .withTimestamp(schedule.getTimestamp())
                .withEventIdList(schedule.getEventIdList())
                .withScheduleName(schedule.getScheduleName())
                .build();
    }

    public EventModel toEventModel(Event event) {
        return EventModel.builder()
                .withEventId(event.getEventId())
                .withScheduleId(event.getScheduleId())
                .withEventName(event.getEventName())
                .withEventNameShort(event.getEventNameShort())
                .withEventHeadline(event.getEventHeadline())
                .withLeagueId(event.getLeagueId())
                .withEventDate(event.getEventDate())
                .withEventSeasonId(event.getEventSeasonId())
                .withTeamHome(event.getTeamHome())
                .withTeamAway(event.getTeamAway())
                .withEventStatusId(event.getEventStatusId())
                .withEventStatus(event.getEventStatus())
                .withTeamWinner(event.getTeamWinner())
                .withScoreHome(event.getScoreHome())
                .withScoreAway(event.getScoreAway())
                .withScoreTotal(event.getScoreTotal())
                .withLinks(event.getLinks())
                .withTeamHomeLogo(event.getTeamHomeLogo())
                .withTeamAwayLogo(event.getTeamAwayLogo())
                .build();
    }

}
