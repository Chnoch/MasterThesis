package ch.chnoch.mt.webinterface

import grails.converters.JSON
import grails.transaction.Transactional
import ch.chnoch.mt.dataparsing.*
import ch.chnoch.mt.baselinemodel.*
import net.sf.json.JSONArray
import net.sf.json.JSONObject

@Transactional
class ModelService {

    def createModel(file, user) {
        def dataEntries = Data.getFullCleanedDataSetForUserFromFile(file, user)
        def dataTraining = new DataTraining(user, dataEntries)
        return dataTraining.createModel()
    }

    def getUsersFromFile(file) {
        def users = sortByImportance(Data.getDataSetFromFile(file, true))
        return users
    }

    def createJSONFromGraph(graph, removeBottomNodes) {
        def json = new JSONObject()
        def verticesJSON = new JSONArray()
        def bottomNodes = []
        graph.vertices.each { node ->
            if (removeBottomNodes && isVertexInBottomPart(node)) {
                bottomNodes.add(node)
            } else {
                def vertexJSON = new JSONObject()
                vertexJSON.put('id', node.id)
                vertexJSON.put('label', node.getProperty('name'))
                verticesJSON.add(vertexJSON)
            }
        }
        json.put('vertices', verticesJSON)
        def edgesJSON = new JSONArray()
        graph.edges.each { edge ->
            if (!(bottomNodes.contains(edge.inVertex) || bottomNodes.contains(edge.outVertex))) {
                def edgeJSON = new JSONObject()
                def inVertex = new JSONObject()
                def outVertex = new JSONObject()
                inVertex.put('id', edge.inVertex.id)
                outVertex.put('id', edge.outVertex.id)
                edgeJSON.put('inVertex', inVertex)
                edgeJSON.put('outVertex', outVertex)
                edgeJSON.put('label', '"' + edge.getProperty('count') + '"')

                edgesJSON.add(edgeJSON)
            }
        }
        json.put('edges', edgesJSON)
        return json
    }

    def isVertexInBottomPart(vertex) {
        def highCount = false
        vertex.inEdges.each { edge ->
            def count = edge.value[0].getProperty('count')
            if (count > 1) {
                highCount = true
            }
        }
        vertex.outEdges.each { edge ->
            def count = edge.value[0].getProperty('count')
            if (count > 1) {
                highCount = true
            }
        }

        return !(highCount || vertex.inEdges.size() > 1 || vertex.outEdges.size() > 1)
    }

    def getBottomVerticesFromGraphAsJSON(graph) {
        def json = new JSONArray()
        graph.vertices.each { vertex ->
            def highCount = false
            vertex.inEdges.each { edge ->
                def count = edge.value[0].getProperty('count')
                if (count > 1) {
                    highCount = true
                }
            }
            vertex.outEdges.each { edge ->
                def count = edge.value[0].getProperty('count')
                if (count > 1) {
                    highCount = true
                }
            }

            if (!(highCount && vertex.inEdges.size() > 1 && vertex.outEdges.size() > 1)) {
                json.add(vertex.id)
            }
        }

        return json
    }

    private sortByImportance(dataEntries) {
        def userMap = [:]

        dataEntries.each { it ->
            if (userMap.containsKey(it.userId)) {
                userMap[it.userId] += 1
            } else {
                userMap[it.userId] = 1
            }
        }

        userMap = userMap.sort { -it.value }
        return userMap
    }
}
