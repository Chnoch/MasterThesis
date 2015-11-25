package ch.chnoch.mt.machinelearning.data.model

/**
 * Created by Chnoch on 27.02.2015.
 */
class ModelEntry {
    Date timestampStart
    Date timestampEnd

    int hourOfDay
    int minuteOfHour
    int dayOfWeek
    boolean weekday

    String previousStationId
    String userId
    String stationId

    private ModelEntry() {

    }

    public ModelEntry(start, end, user, station, hourOfDay, minuteOfHour, dayOfWeek, weekday) {
        this.timestampStart = start
        this.timestampEnd = end
        this.userId = user
        this.stationId = station
        this.hourOfDay = hourOfDay
        this.minuteOfHour = minuteOfHour
        this.dayOfWeek = dayOfWeek
        this.weekday = weekday
    }

    public String toString() {
        return 'user: ' + userId + ', timestampStart: ' + timestampStart.getTime() + ', stationId: ' + stationId + ', previousStationId: ' + previousStationId
    }
}
