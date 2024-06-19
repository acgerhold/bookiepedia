package bookiepedia.converters;

import bookiepedia.dynamodb.models.Bet;
import bookiepedia.dynamodb.models.Event;
import bookiepedia.dynamodb.models.Schedule;
import bookiepedia.models.BetModel;
import bookiepedia.models.EventModel;
import bookiepedia.models.ScheduleModel;

import java.util.ArrayList;
import java.util.List;

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
                .withTeamHomeColor(event.getTeamHomeColor())
                .withTeamAwayColor(event.getTeamAwayColor())
                .withTeamHomeColorAlt(event.getTeamHomeColorAlt())
                .withTeamAwayColorAlt(event.getTeamAwayColorAlt())
                .build();
    }

    public BetModel toBetModel(Bet bet) {
        return BetModel.builder()
                .withWeeklyHistoryId(bet.getWeeklyHistoryId())
                .withBetId(bet.getBetId())
                .withUserId(bet.getUserId())
                .withEventId(bet.getEventId())
                .withAmountWagered(bet.getAmountWagered())
                .withOdds(bet.getOdds())
                .withTeamBetOn(bet.getTeamBetOn())
                .withProjection(bet.getProjection())
                .withBettingMarket(bet.getBettingMarket())
                .withBookmakerId(bet.getBookmakerId())
                .withDatePlaced(bet.getDatePlaced())
                .withGainOrLoss(bet.getGainOrLoss())
                .withTeamHome(bet.getTeamHome())
                .withScoreHome(bet.getScoreHome())
                .withTeamHomeLogo(bet.getTeamHomeLogo())
                .withTeamAway(bet.getTeamAway())
                .withScoreAway(bet.getScoreAway())
                .withTeamAwayLogo(bet.getTeamAwayLogo())
                .withTeamWinner(bet.getTeamWinner())
                .withScoreTotal(bet.getScoreTotal())
                .withEventName(bet.getEventName())
                .withEventHeadline(bet.getEventHeadline())
                .withEventDate(bet.getEventDate())
                .withEventStatus(bet.getEventStatus())
                .build();
    }

    public List<BetModel> toBetModelList(List<Bet> betList) {
        List<BetModel> betModelList = new ArrayList<>();
        for (Bet bet : betList) {
            betModelList.add(toBetModel(bet));
        }
        return betModelList;
    }

}
