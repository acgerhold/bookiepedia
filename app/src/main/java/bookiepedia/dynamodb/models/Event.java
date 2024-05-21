package bookiepedia.dynamodb.models;

import java.util.Map;

public class Event {

    private String eventId;
    // events > 0,1,2 ... > id (401571998)
    private String eventName;
    // events > 0,1,2 ... > name (Indiana Pacers at Boston Celtics)
    private String eventNameShort;
    // events > 0,1,2 ... > shortName (IND @ BOS)
    private String eventHeadline;
    // events > 0,1,2 ... > competitions > 0 > notes > 0 > headline (East Finals - Game 1)
    private String leagueId;
    // leagues > 0 > id (46)
    private String eventDate;
    // events > 0,1,2 ... > date (2024-05-22T00:00Z)
    private String eventDateDetail;
    // events > 0,1,2 ... > competitions > 0 > status > type > detail (Tue, May 21st at 8:00 PM EDT)
    private String eventDateDetailShort;
    // events > 0,1,2 ... > competitions > 0 > status > type > shortDetail (5/21 - 8:00 PM EDT)
    private String eventSeason;
    // events > 0,1,2 ... > season > type (3 - postseason)
    private String teamHome;
    // event > 0,1,2 ... > competitions > 0 > competitors > 0,1 > homeAway
    // IF(homeAway = "home") > teamHome = event > 0,1,2 ... > competitions > 0 > competitors > 0,1 > id
    private String teamAway;
    // event > 0,1,2 ... > competitions > 0 > competitors > 0,1 > homeAway
    // IF(homeAway = "away") > teamHome = event > 0,1,2 ... > competitions > 0 > competitors > 0,1 > id
    private String eventStatusId;
    // events > 0,1,2 ... > status > type > id (1 - Scheduled, 2 - Current, 3 - Completed)
    private String eventStatus;
    // IF(eventStatusId == 1) { eventStatus = "TBD"; }
    // IF(eventStatusId == 2) { CASE periods:
    // 4 > eventStatus = "Q" + period + " - " + clock + " " + displayClock (Q3 - 2:30 0.7)
    // 3 > eventStatus = "P" + period + " - " + clock + " " + displayClock (P3 - 2:30 0.7)
    // }
    // IF(eventStatusId == 3) { eventStatus = "Final"; }
    // completed = events > 0,1,2 .. > status > type > completed
    // periods = events > 0,1,2 ... > competitions > 0 > format > regulation > periods
    // period = events > 0,1,2 ... > status > String.valueOf(period)
    // displayClock = events > 0,1,2 ... > status > displayClock
    // clock =  events > 0,1,2 ... > status > String.valueOf(clock)
    private String teamWinner;
    // TBD
    private Integer scoreHome;
    // IF(homeAway == "home") { scoreHome = score }
    // score = events > 0,1,2 ... > competitions > 0 > competitors > 0,1 > Integer.valueOf(score)
    private Integer scoreAway;
    // IF(homeAway == "away") { scoreAway = score }
    // score = events > 0,1,2 ... > competitions > 0 > competitors > 0,1 > Integer.valueOf(score)
    private Integer scoreTotal;
    // scoreTotal = scoreHome + scoreAway;
    private Map<String, Map<String, Integer>> currentOdds;
    // API includes some current odds/projections from ESPN Bet:
    // odds/projections for totals, projections for spreads, neither for moneyline ??
    // Can use this as a placeholder before implementing The Odds API
    // May be able to blend the two API responses, similar format, ID's for bookmakers
    // Will later be refactored to an "Odds" object/model
    // TBD
    private String gamecastLink;
    // Can I embed these links instead of opening new tab?
    // events > 0,1,2 ... > links > 0 > href ("https://www.espn.com/nba…401671998/pacers-celtics")

    // GETTERS

    public String getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventNameShort() {
        return eventNameShort;
    }

    public String getEventHeadline() {
        return eventHeadline;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventDateDetail() {
        return eventDateDetail;
    }

    public String getEventDateDetailShort() {
        return eventDateDetailShort;
    }

    public String getEventSeason() {
        return eventSeason;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public String getTeamHome() {
        return teamHome;
    }

    public String getTeamAway() {
        return teamAway;
    }

    public String getEventStatusId() {
        return eventStatusId;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public String getTeamWinner() {
        return teamWinner;
    }

    public int getScoreHome() {
        return scoreHome;
    }

    public int getScoreAway() {
        return scoreAway;
    }

    public int getScoreTotal() {
        return scoreTotal;
    }

    public Map<String, Map<String, Integer>> getCurrentOdds() {
        return currentOdds;
    }

    public String getGamecastLink() {
        return gamecastLink;
    }

    // SETTERS

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventNameShort(String eventNameShort) {
        this.eventNameShort = eventNameShort;
    }

    public void setEventHeadline(String eventHeadline) {
        this.eventHeadline = eventHeadline;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventDateDetail(String eventDateDetail) {
        this.eventDateDetail = eventDateDetail;
    }

    public void setEventDateDetailShort(String eventDateDetailShort) {
        this.eventDateDetailShort = eventDateDetailShort;
    }

    public void setEventSeason(String eventSeason) {
        this.eventSeason = eventSeason;
    }

    public void setTeamHome(String teamHome) {
        this.teamHome = teamHome;
    }

    public void setTeamAway(String teamAway) {
        this.teamAway = teamAway;
    }

    public void setEventStatusId(String eventStatusId) {
        this.eventStatusId = eventStatusId;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public void setTeamWinner(String teamWinner) {
        this.teamWinner = teamWinner;
    }

    public void setScoreHome(Integer scoreHome) {
        this.scoreHome = scoreHome;
    }

    public void setScoreAway(Integer scoreAway) {
        this.scoreAway = scoreAway;
    }

    public void setScoreTotal(Integer scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public void setCurrentOdds(Map<String, Map<String, Integer>> currentOdds) {
        this.currentOdds = currentOdds;
    }

    public void setGamecastLink(String gamecastLink) {
        this.gamecastLink = gamecastLink;
    }
}
