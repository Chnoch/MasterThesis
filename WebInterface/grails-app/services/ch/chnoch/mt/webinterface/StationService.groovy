package ch.chnoch.mt.webinterface

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
        def failedIds = []
//        def count = 0
        stationIds.each { stationId ->
//            if (count > 10) {
//                return
//            } else {
//                count++
//            }
            try {
                queryStation(http, stationId)
            } catch (def e) {
                sleep(1000)
                println 'Exception for id: ' + stationId
                println e
                failedIds.add(stationId)
            }
        }

        sleep(1000)

        failedIds.each { stationId ->
            try {
                queryStation(http, stationId)
            } catch (def e) {
                sleep(1000)
                println 'Failed Exception for id: ' + stationId
                println e
            }
        }
    }

    private queryStation(http, stationId) {
        if (!Station.findByStationId(stationId)) {
            http.get(query: [id: stationId]) { resp, json ->
                if (json.station) {
                    def station = new Station(stationId: stationId, name: json.station.name)
                    station.save()
                    println stationId + ': ' + json.station.name
                }
            }
        }
    }

    def getStationName(id) {
        if (!id) {
            return id + ' name'
        }

        def station = Station.findByStationId(id)

        if (!station) {
            return id + ' name'
        }

        return station.name
    }
}
