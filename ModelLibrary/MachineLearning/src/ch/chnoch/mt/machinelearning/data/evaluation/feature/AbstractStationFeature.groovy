package ch.chnoch.mt.machinelearning.data.evaluation.feature

/**
 * Created by Chnoch on 05.01.2016.
 */
class AbstractStationFeature extends AbstractFeature {
    public AbstractStationFeature() {}

    protected AbstractStationFeature(attr, prop) {
        super(attr, prop)
    }

    protected getStationIds(entries) {
        // Create list to hold nominal values "first", "second", "third"
        def stationValues = new HashSet<String>()

        entries.each { it ->
            stationValues.add(it.stationId)
        }

        stationValues.add('null')

        return stationValues.toList()
    }
}
