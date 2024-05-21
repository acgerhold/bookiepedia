package bookiepedia.dynamodb.models;

import java.util.List;

public class Schedule {

    private String scheduleId;
    // leagueId + "-" + timestamp (46-2024-05-21)
    private String leagueId;
    // leagues > 0 > id
    private String leagueName;
    // leagues > 0 > abbreviation
    private String timestamp;
    // day > date
    private List<Event> eventList;
    // events > 0,1,2 ... > id
    private String dateRange;
    // MIN(event.date : eventlist) + "-" + MAX(event.date : eventlist)
    private String scheduleName;
    // leagueName + " upcoming events " + dateRange;


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

    public List<Event> getEventList() {
        return eventList;
    }

    public String getDateRange() {
        return dateRange;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    // SETTERS

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }
}
