package ch.chnoch.mt.baselinemodel

/**
 * Created by Chnoch on 11.07.2014.
 */

class DataInitialization {
    def dataEntries
    public DataInitialization(dataEntries) {
        this.dataEntries = dataEntries
    }

    def start(def userId) {
        def userEntries = dataEntries.findAll{ it -> it.userId == userId}

        def model = new BaselineModel(userId)
        userEntries.sort { a, b ->
            a.timestampStart <=> b.timestampStart
        }
        
        userEntries.eachWithIndex{ entry, i ->
            model.addVertex(entry.stationId)
            if (i!=0) {
                def fromVertex = userEntries[i-1].stationId
                def toVertex = entry.stationId
                def label = entry.timestampStart
                model.addEdge(label, fromVertex, toVertex)
            }
        }

        model.printGraph()
    }
}
