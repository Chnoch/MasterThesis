package ch.chnoch.mt.machinelearning.data.preparation

/**
 * Created by Chnoch on 14.12.2015.
 */
class StatisticsPreparation {

    public static prepareStations(model) {

        model.getUsers().each { user ->
            def stations = [:]
            model.getEntriesForUser(user).each { entry ->
                def count = stations.get(entry.stationId);
                if (count == null) {
                    stations.put(entry.stationId, 1)
                } else {
                    stations.put(entry.stationId, ++count)
                }
            }

            stations = stations.sort { -it.value }
            def finalStations = []
            def tempStations = stations.keySet().toList()
            if (tempStations.size() > 5) {
                tempStations[0..4].each { finalStations << it}
            } else {
                finalStations = tempStations
            }

            finalStations.add('null')

            model.getEntriesForUser(user).each { entry ->
                if (!finalStations.contains(entry.nextStationId) || entry.nextStationId == null) {
                    entry.nextStationId = 'null';
                }
            }

            model.setAvailableStationsForUser(user, finalStations)
        }
    }
}
