package bookiepedia.models;

import java.util.List;
import java.util.Objects;

public class EventModel {

    private final String eventId;
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

    public EventModel(String eventId, String eventName, String eventNameShort, String eventHeadline,
                      String leagueId, String eventDate, String eventSeasonId, String teamHome, String teamAway,
                      String eventStatusId, String eventStatus, String teamWinner, Integer scoreHome,
                      Integer scoreAway, Integer scoreTotal, List<String> links) {
        this.eventId = eventId;
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
    }

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
                leagueId.equals(that.leagueId) &&
                eventDate.equals(that.eventDate) &&
                eventSeasonId.equals(that.eventSeasonId);
        // This may need to change for live updates on games
        // eventStatusId?
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, leagueId, eventDate, eventSeasonId);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String eventId;
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

        public Builder withEventId(String eventId) {
            this.eventId = eventId;
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

        public EventModel build() {
            return new EventModel(eventId, eventName, eventNameShort, eventHeadline, leagueId, eventDate,
                    eventSeasonId, teamHome, teamAway, eventStatusId, eventStatus, teamWinner, scoreHome, scoreAway,
                    scoreTotal, links);
        }
    }
}