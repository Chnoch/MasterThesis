/**
 * Created by Chnoch on 17.06.2014.
 */
class DataEntry {
    private userId
    private timestampStart
    private timestampEnd
    private stationId

    public DataEntry(user, tsStart, tsEnd, station) {
        userId = user
        timestampStart = tsStart
        timestampEnd = tsEnd
        stationId = station
    }

    def getUserId() {
        return userId
    }

    def getTimestampStart() {
        return timestampStart
    }

    def getTimestampEnd() {
        return timestampEnd
    }

    def getStationId() {
        return stationId
    }

}
