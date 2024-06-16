package bookiepedia.models;

import java.util.List;
import java.util.Objects;

public class EventModel {

    private final String eventId;
    private final String scheduleId;
    private final String eventName;
    private final String eventNameShort;
    private final String eventHeadline;
    private final String leagueId;
    private final String eventDate;
    private final String eventSeasonId;
    private final String teamHome;
    private final String teamAway;
    private final String eventStatusId;
    private final String eventStatus;
    private final String teamWinner;
    private final Integer scoreHome;
    private final Integer scoreAway;
    private final Integer scoreTotal;
    private final List<String> links;
    private final String teamHomeLogo;
    private final String teamAwayLogo;
    private final String teamHomeColor;
    private final String teamAwayColor;
    private final String teamHomeColorAlt;
    private final String teamAwayColorAlt;

    /**
     * Constructor for an Event model.
     * @param eventId - An ID associated with an Event
     * @param scheduleId - The ID of a Schedule object an Event is contained in
     * @param eventName - The name of an Event
     * @param eventNameShort - An abbreviated version of eventName
     * @param eventHeadline - The headline of an Event
     * @param leagueId - The ID of a league associated with an Event
     * @param eventDate - The date of the Event
     * @param eventSeasonId - An ID representing pre-season, regular season, or post-season
     * @param teamHome - The home team of the Event
     * @param teamAway - The away team of the Event
     * @param eventStatusId - An ID representing if an Event is scheduled, in progress, or complete
     * @param eventStatus - "Scheduled", "In Progress", or "Complete"
     * @param teamWinner - The winning team of the event
     * @param scoreHome - The score of 'teamHome'
     * @param scoreAway - The score of 'teamAway'
     * @param scoreTotal - The combined score of 'teamHome' and 'teamAway'
     * @param links - External links to ESPN's game cast, box score, highlights, play-by-play, and recap
     */
    public EventModel(String eventId, String scheduleId, String eventName, String eventNameShort, String eventHeadline,
                      String leagueId, String eventDate, String eventSeasonId, String teamHome, String teamAway,
                      String eventStatusId, String eventStatus, String teamWinner, Integer scoreHome,
                      Integer scoreAway, Integer scoreTotal, List<String> links, String teamHomeLogo,
                      String teamAwayLogo, String teamHomeColor, String teamAwayColor, String teamHomeColorAlt,
                      String teamAwayColorAlt) {
        this.eventId = eventId;
        this.scheduleId = scheduleId;
        this.eventName = eventName;
        this.eventNameShort = eventNameShort;
        this.eventHeadline = eventHeadline;
        this.leagueId = leagueId;
        this.eventDate = eventDate;
        this.eventSeasonId = eventSeasonId;
        this.teamHome = teamHome;
        this.teamAway = teamAway;
        this.eventStatusId = eventStatusId;
        this.eventStatus = eventStatus;
        this.teamWinner = teamWinner;
        this.scoreHome = scoreHome;
        this.scoreAway = scoreAway;
        this.scoreTotal = scoreTotal;
        this.links = links;
        this.teamHomeLogo = teamHomeLogo;
        this.teamAwayLogo = teamAwayLogo;
        this.teamHomeColor = teamHomeColor;
        this.teamAwayColor = teamAwayColor;
        this.teamHomeColorAlt = teamHomeColorAlt;
        this.teamAwayColorAlt = teamAwayColorAlt;
    }

    public String getEventId() {
        return eventId;
    }
    public String getScheduleId() {
        return scheduleId;
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

    public String getLeagueId() {
        return leagueId;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventSeasonId() {
        return eventSeasonId;
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

    public Integer getScoreHome() {
        return scoreHome;
    }

    public Integer getScoreAway() {
        return scoreAway;
    }

    public Integer getScoreTotal() {
        return scoreTotal;
    }

    public List<String> getLinks() {
        return links;
    }

    public String getTeamHomeLogo() {
        return teamHomeLogo;
    }

    public String getTeamAwayLogo() {
        return teamAwayLogo;
    }

    public String getTeamHomeColor() {
        return teamHomeColor;
    }

    public String getTeamAwayColor() {
        return teamAwayColor;
    }

    public String getTeamHomeColorAlt() {
        return teamHomeColorAlt;
    }

    public String getTeamAwayColorAlt() {
        return teamAwayColorAlt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventModel that = (EventModel) o;
        return eventId.equals(that.eventId) &&
                scheduleId.equals(that.scheduleId) &&
                leagueId.equals(that.leagueId) &&
                eventDate.equals(that.eventDate) &&
                eventSeasonId.equals(that.eventSeasonId);
        // This may need to change for live updates on games
        // eventStatusId?
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, scheduleId, leagueId, eventDate, eventSeasonId);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String eventId;
        private String scheduleId;
        private String eventName;
        private String eventNameShort;
        private String eventHeadline;
        private String leagueId;
        private String eventDate;
        private String eventSeasonId;
        private String teamHome;
        private String teamAway;
        private String eventStatusId;
        private String eventStatus;
        private String teamWinner;
        private Integer scoreHome;
        private Integer scoreAway;
        private Integer scoreTotal;
        private List<String> links;
        private String teamHomelogo;
        private String teamAwayLogo;
        private String teamHomeColor;
        private String teamAwayColor;
        private String teamHomeColorAlt;
        private String teamAwayColorAlt;

        public Builder withEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder withScheduleId(String scheduleId) {
            this.scheduleId = scheduleId;
            return this;
        }

        public Builder withEventName(String eventName) {
            this.eventName = eventName;
            return this;
        }

        public Builder withEventNameShort(String eventNameShort) {
            this.eventNameShort = eventNameShort;
            return this;
        }

        public Builder withEventHeadline(String eventHeadline) {
            this.eventHeadline = eventHeadline;
            return this;
        }

        public Builder withLeagueId(String leagueId) {
            this.leagueId = leagueId;
            return this;
        }

        public Builder withEventDate(String eventDate) {
            this.eventDate = eventDate;
            return this;
        }

        public Builder withEventSeasonId(String eventSeasonId) {
            this.eventSeasonId = eventSeasonId;
            return this;
        }

        public Builder withTeamHome(String teamHome) {
            this.teamHome = teamHome;
            return this;
        }

        public Builder withTeamAway(String teamAway) {
            this.teamAway = teamAway;
            return this;
        }

        public Builder withEventStatusId(String eventStatusId) {
            this.eventStatusId = eventStatusId;
            return this;
        }

        public Builder withEventStatus(String eventStatus) {
            this.eventStatus = eventStatus;
            return this;
        }

        public Builder withTeamWinner(String teamWinner) {
            this.teamWinner = teamWinner;
            return this;
        }

        public Builder withScoreHome(Integer scoreHome) {
            this.scoreHome = scoreHome;
            return this;
        }

        public Builder withScoreAway(Integer scoreAway) {
            this.scoreAway = scoreAway;
            return this;
        }

        public Builder withScoreTotal(Integer scoreTotal) {
            this.scoreTotal = scoreTotal;
            return this;
        }

        public Builder withLinks(List<String> links) {
            this.links = links;
            return this;
        }

        public Builder withTeamHomeLogo(String teamHomeLogo) {
            this.teamHomelogo = teamHomeLogo;
            return this;
        }

        public Builder withTeamAwayLogo(String teamAwayLogo) {
            this.teamAwayLogo = teamAwayLogo;
            return this;
        }

        public Builder withTeamHomeColor(String teamHomeColor) {
            this.teamHomeColor = teamHomeColor;
            return this;
        }

        public Builder withTeamAwayColor(String teamAwayColor) {
            this.teamAwayColor = teamAwayColor;
            return this;
        }

        public Builder withTeamHomeColorAlt(String teamHomeColorAlt) {
            this.teamHomeColorAlt = teamHomeColorAlt;
            return this;
        }

        public Builder withTeamAwayColorAlt(String teamAwayColorAlt) {
            this.teamAwayColorAlt = teamAwayColorAlt;
            return this;
        }

        public EventModel build() {
            return new EventModel(eventId, scheduleId, eventName, eventNameShort, eventHeadline, leagueId, eventDate,
                    eventSeasonId, teamHome, teamAway, eventStatusId, eventStatus, teamWinner, scoreHome, scoreAway,
                    scoreTotal, links, teamHomelogo, teamAwayLogo, teamHomeColor, teamAwayColor, teamHomeColorAlt,
                    teamAwayColorAlt);
        }
    }
}
