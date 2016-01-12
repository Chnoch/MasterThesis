package ch.chnoch.mt.machinelearning.data.evaluation.feature

/**
 * Created by Chnoch on 05.01.2016.
 */
class AbstractStationFeature extends AbstractFeature {

    private static final int TOP = 10

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

    protected getTopStationIds(entries) {
        def stationMap = new HashMap<>()
        entries.each { it ->
            if (stationMap.containsKey(it.stationId)) {
                stationMap.put(it.stationId, 1)
            } else {
                stationMap.put(it.stationId, stationMap.get(it.stationId) + 1)
            }
        }

        stationMap.sort {a, b -> a.value <=> b.value}

        def stationList = [];

        stationMap.iterator().eachWithIndex{key, value, index ->
            if (index < TOP) {
                stationList.add(key)
            }
        }

        stationList.add('null')
        return stationList
    }
}
