/**
 * Created by Chnoch on 11.07.2014.
 */
package ch.chnoch.mt.baselinemodel

import com.tinkerpop.blueprints.Graph
import com.tinkerpop.blueprints.impls.tg.TinkerGraph
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory
import com.tinkerpop.gremlin.groovy.Gremlin

class BaselineModel {
    def graph
    def userId

    public BaselineModel(def user) {
        Gremlin.load()
        userId = user
        graph = new TinkerGraph()

    }

    def addVertex(def vertex) {
        if (!hasVertex(vertex)) {
            graph.addVertex(vertex)
        }
    }

    def getVertex(def vertex) {
        if (!hasVertex(vertex)) {
            return null
        }
        return graph.getVertex(vertex)
    }

    def addEdge(def timestamp, def fromVertexId, def toVertexId) {
        def fromVertex = getVertex(fromVertexId)
        def toVertex = getVertex(toVertexId)
        if (fromVertex != null && toVertex != null) {
            graph.addEdge(timestamp.toString(), fromVertex, toVertex, timestamp.toString())
        } else {
            println 'vertices not yet added'
        }
    }


    def hasVertex(def vertexId) {
        def vertex = graph.getVertex(vertexId)
        return  vertex != null
    }

    def printGraph() {
        println graph
    }
}
