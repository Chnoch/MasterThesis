package webinterface

import ch.chnoch.mt.dataparsing.Data
import grails.transaction.Transactional
import groovyx.net.http.HTTPBuilder

@Transactional
class StationService {

    def initializeStations(file) {
        def dataEntries = Data.getDataSetFromFile(file, false)
        def allStationIds = dataEntries.collect { it.stationId }
        def stationIds = allStationIds.toSet()
        def http = new HTTPBuilder('http://transport.opendata.ch/v1/stationboard')

        for (def i = 0; i < 100; i++) {
            def stationId = stationIds[i]
            http.get(query: [id: stationId]) { resp, json ->
                if (json.station) {
                    def station = new Station(id: stationId, name: json.station.name)
                    station.save()
                    println stationId + ': ' + json.station.name
                }
            }
        }
    }
}
