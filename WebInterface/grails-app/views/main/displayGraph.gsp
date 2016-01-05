<%--
  Created by IntelliJ IDEA.
  User: Chnoch
  Date: 14.07.2014
  Time: 15:55
--%>
<%@ page import="ch.chnoch.mt.webinterface.StationService" %>
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
    <g:javascript src="main.js"></g:javascript>
</head>

<body>
<div class="col-sm-12 title">
    <h1 class="pull-left">User ${user}</h1>
    <div class="pull-right select-button">
        <g:link action="selectUser" class="btn btn-primary">
            Select different User
        </g:link>
    </div>
    <div class="pull-right select-button">
        <a onclick="javascript:hideBottomVertices();" id="hide-vertices" class="btn btn-primary">
            Hide Unimportant Vertices
        </a>
        <a onclick="javascript:showBottomVertices();" id="show-vertices" class="btn btn-primary hide">
            Show Unimportant Vertices
        </a>
    </div>
</div>
<div class="col-sm-12 graph-info">
    <div>Number of Nodes: ${verticesSize}</div>
    <div>Number of Edges: ${edgesSize}</div>
</div>
<div id="graph"></div>


<script type="text/javascript">
    $(function () {
        var graphJSON = JSON.parse('${raw(graph)}');
        createGraph('graph', graphJSON, true);
    });
</script>

</body>
</html>