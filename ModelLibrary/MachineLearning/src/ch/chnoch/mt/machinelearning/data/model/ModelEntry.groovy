package ch.chnoch.mt.machinelearning.data.model

/**
 * Created by Chnoch on 27.02.2015.
 */
class ModelEntry {
    Date timestampStart

    int hourOfDay
    int minuteOfHour
    int dayOfWeek
    boolean weekday

    String previousStationId
    String userId
    String stationId

    // This is the main thing to figure out
    String nextStationId

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
}
