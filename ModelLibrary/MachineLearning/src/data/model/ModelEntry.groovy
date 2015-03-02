package data.model

/**
 * Created by Chnoch on 27.02.2015.
 */
class ModelEntry {
    Date timestampStart
    Date timestampEnd
    String userId
    String stationId

    private ModelEntry() {

    }

    public ModelEntry(start, end, user, station) {
        timestampStart = start
        timestampEnd = end
        userId = user
        stationId = station
    }


}
