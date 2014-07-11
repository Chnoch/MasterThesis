/**
 * Created by Chnoch on 11.07.2014.
 */
package ch.chnoch.mt.baselinemodel

import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.Graph
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.tg.TinkerGraph
import com.tinkerpop.blueprints.impls.tg.TinkerGraphFactory
import com.tinkerpop.blueprints.oupls.jung.GraphJung
import com.tinkerpop.gremlin.groovy.Gremlin
import edu.uci.ics.jung.algorithms.layout.CircleLayout
import edu.uci.ics.jung.graph.event.GraphEvent
import edu.uci.ics.jung.visualization.BasicVisualizationServer
import edu.uci.ics.jung.visualization.renderers.Renderer
import org.apache.commons.collections15.Transformer

import javax.swing.JFrame
import java.awt.Dimension

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
        def g = new GraphJung(graph);
        def layout = new CircleLayout<Vertex, Edge>(g);
        layout.setSize(new Dimension(300, 300));
        def viz = new BasicVisualizationServer<Vertex, Edge>(layout);
        viz.setPreferredSize(new Dimension(350, 350));

        def vertexLabelTransformer = new Transformer<Vertex, String>() {
            public String transform(Vertex vertex) {
                return (String) vertex.getProperty("name");
            }
        };

        def edgeLabelTransformer = new Transformer<Edge, String>() {
            public String transform(Edge edge) {
                return edge.getLabel();
            }
        };

        viz.getRenderContext().setEdgeLabelTransformer(edgeLabelTransformer);
        viz.getRenderContext().setVertexLabelTransformer(vertexLabelTransformer);

        def frame = new JFrame("TinkerPop");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(viz);
        frame.pack();
        frame.setVisible(true);
    }
}
