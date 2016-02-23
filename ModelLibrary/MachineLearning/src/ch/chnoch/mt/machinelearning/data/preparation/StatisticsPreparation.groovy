package ch.chnoch.mt.machinelearning.data.preparation

import ch.chnoch.mt.machinelearning.data.model.Model

/**
 * Prepares every user in a way that only the top 5 stations are taken into account
 */
public class StatisticsPreparation {
    private static final int MAX_AMOUNT_OF_STATIONS = 10

    public static prepareStations(Model model) {
        model.getPreparedUsers().each { user ->
            Map<String, Integer> stations = [:]
            user.getPreparedEntries().each { entry ->
                def count = stations.get(entry.stationId);
                if (count == null) {
                    stations.put(entry.stationId, 1)
                } else {
                    stations.put(entry.stationId, ++count)
                }
            }

            stations = stations.sort { -it.value }
            List<String> finalStations = []
            List<String> tempStations = stations.keySet().toList()
            if (tempStations.size() > MAX_AMOUNT_OF_STATIONS) {
                tempStations[0..(MAX_AMOUNT_OF_STATIONS - 1)].each { finalStations << it }
            } else {
                finalStations = tempStations
            }

            finalStations.add('null')

            user.getPreparedEntries().each { entry ->
                if (!finalStations.contains(entry.nextStationId) || entry.nextStationId == null) {
                    entry.nextStationId = 'null';
                }
            }

            user.setAvailableNextStations(finalStations)
        }
    }
}
