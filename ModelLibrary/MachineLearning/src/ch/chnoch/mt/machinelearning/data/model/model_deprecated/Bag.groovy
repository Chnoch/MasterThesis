package ch.chnoch.mt.machinelearning.data.model.model_deprecated

/**
 * Created by Chnoch on 12.03.2015.
 */
class Bag {

    Date timestampStartBag
    Date timestampEndBag
    String userId
    String stationIdStart
    String stationIdEnd

    private Bag() {

    }

    public Bag(start, end, user, stationStart, stationEnd) {
        timestampStartBag = start
        timestampEndBag = end
        userId = user
        stationIdStart = stationStart
        stationIdEnd = stationEnd
    }
}
