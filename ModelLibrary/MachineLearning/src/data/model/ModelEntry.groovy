package data.model

/**
 * Created by Chnoch on 27.02.2015.
 */
class ModelEntry {
    def timestampStart, timestampEnd, userId, stationId

    private ModelEntry() {

    }

    public ModelEntry(start, end, user, station) {
        timestampStart = start
        timestampEnd = end
        userId = user
        stationId = station
    }


}
