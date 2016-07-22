package ch.chnoch.mt.machinelearning.data.model

/**
 * Created by Chnoch on 27.02.2015.
 */
public class ModelEntry {

    private String userId

    private Date timestampStart

    private int hourOfDay
    private int minuteOfHour
    private int dayOfWeek
    private Boolean weekday

    private String stationId
    private String previousStationId

    // This is the main thing to predict
    private String nextStationId

    private ModelEntry() {}

    public ModelEntry(start, user, station, hourOfDay, minuteOfHour, dayOfWeek, weekday) {
        this.timestampStart = start
        this.userId = user
        this.stationId = station
        this.hourOfDay = hourOfDay
        this.minuteOfHour = minuteOfHour
        this.dayOfWeek = dayOfWeek
        this.weekday = weekday
    }

    public String toString() {
        return 'user: ' + userId + ', timestampStart: ' + timestampStart.getTime() + ', stationId: ' + stationId + ', previousStationId: ' + previousStationId + ', nextStationId: ' + nextStationId
    }

    public getElement(element) {
        switch (element) {
            case 'stationId':
                return stationId;
            case 'hourOfDay':
                return hourOfDay;
            case 'minuteOfHour':
                return minuteOfHour;
            case 'dayOfWeek':
                return dayOfWeek;
            case 'weekday':
                return weekday.toString();
            case 'previousStationId':
                return previousStationId;
            case 'nextStationId':
                return nextStationId;
        }
    }

    public Date getTimestampStart() {
        return timestampStart
    }

    public void setTimestampStart(Date timestampStart) {
        this.timestampStart = timestampStart
    }

    public int getHourOfDay() {
        return hourOfDay
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay
    }

    public int getMinuteOfHour() {
        return minuteOfHour
    }

    public void setMinuteOfHour(int minuteOfHour) {
        this.minuteOfHour = minuteOfHour
    }

    public int getDayOfWeek() {
        return dayOfWeek
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek
    }

    public boolean getWeekday() {
        return weekday
    }

    public void setWeekday(boolean weekday) {
        this.weekday = weekday
    }

    public String getPreviousStationId() {
        return previousStationId
    }

    public void setPreviousStationId(String previousStationId) {
        this.previousStationId = previousStationId
    }

    public String getUserId() {
        return userId
    }

    public void setUserId(String userId) {
        this.userId = userId
    }

    public String getStationId() {
        return stationId
    }

    public void setStationId(String stationId) {
        this.stationId = stationId
    }

    public String getNextStationId() {
        return nextStationId
    }

    public void setNextStationId(String nextStationId) {
        this.nextStationId = nextStationId
    }

}
