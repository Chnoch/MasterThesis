/**
 * Created by Chnoch on 24.07.2014.
 */
var graphJSON;
var graph;
var graphId;
var width = 800;
var height = 600;
var renderer;

function createGraph(id, json, storeJSON) {
    graphId = id;
    graphJSON = json;

    graph = new Graph();

    for (var i = 0; i < graphJSON.vertices.length; i++) {
        var node = graphJSON.vertices[i];
        graph.addNode(node.id, {label: node.label});
    }

    var st = {
        directed: true,
        "label-style": {
            "font-size": 14
        }
    };

    for (var i = 0; i < graphJSON.edges.length; i++) {
        var edge = graphJSON.edges[i];
        st.label = edge.label;
        graph.addEdge(edge.inVertex.id, edge.outVertex.id, st);
    }

    /* layout the graph using the Spring layout implementation */
    var layouter = new Graph.Layout.Spring(graph);
    layouter.layout();
    /* draw the graph using the RaphaelJS draw implementation */
    renderer = new Graph.Renderer.Raphael(graphId, graph, width, height);
    renderer.draw();
}

function hideBottomVertices() {
    $.getJSON('/main/getBottomVertices', function (json) {
        $('#graph').empty();
        createGraph(graphId, json, false);
        $('#hide-vertices').addClass('hide');
        $('#show-vertices').removeClass('hide');
    });
}

function showBottomVertices() {
    $('#graph').empty();
    createGraph(graphId, graphJSON, false);
    $('#hide-vertices').removeClass('hide');
    $('#show-vertices').addClass('hide');
}