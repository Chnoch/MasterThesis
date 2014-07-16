<%--
  Created by IntelliJ IDEA.
  User: Chnoch
  Date: 14.07.2014
  Time: 15:55
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:javascript src="Curry-1.0.1.js"></g:javascript>
    <g:javascript src="seedrandom.js"></g:javascript>
    <g:javascript src="raphael-min.js"></g:javascript>
    <g:javascript src="dracula_algorithms.js"></g:javascript>
    <g:javascript src="dracula_graffle.js"></g:javascript>
    <g:javascript src="dracula_graph.js"></g:javascript>
</head>

<body>
<div class="user">
    <h1>${user}</h1>
</div>
<div class="graph-info">
    <div>Number of Nodes: ${graph.vertices.size()}</div>
    <div>Number of Edges: ${graph.edges.size()}</div>
</div>
<div id="graph"></div>

<script type="text/javascript">
    $(function () {
        var width = 800;
        var height = 600;

        var g = new Graph();

        /* add a simple node */
        <g:each in="${graph.vertices}" var="node" status="i">
        g.addNode("${node.id}");
        </g:each>

        var st;
        <g:each in="${graph.edges}" var="edge">
        st ={directed: true, label: "${edge.getProperty("count")}",
            "label-style": {
                "font-size": 14
            }
        };
        g.addEdge("${edge.inVertex.id}", "${edge.outVertex.id}", st);
        </g:each>

        /* layout the graph using the Spring layout implementation */
        var layouter = new Graph.Layout.Spring(g);

        /* draw the graph using the RaphaelJS draw implementation */
        var renderer = new Graph.Renderer.Raphael('graph', g, width, height);
    });
</script>

</body>
</html>