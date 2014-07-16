package ch.chnoch.mt.baselinemodel

/**
 * Created by Chnoch on 11.07.2014.
 */

class DataTraining {
    def userId, trainingSet

    public DataTraining(userId, trainingSet) {
        this.userId = userId
        this.trainingSet = trainingSet
    }

    def createModel() {

        def model = new BaselineModel(userId)
        trainingSet.eachWithIndex { entry, i ->
            model.addVertex(entry.stationId)
            if (i != 0) {
                def fromVertex = trainingSet[i - 1].stationId
                def toVertex = entry.stationId
                def label = entry.timestampStart
                model.addEdge(label, fromVertex, toVertex)
            }
        }

        return model
    }
}
