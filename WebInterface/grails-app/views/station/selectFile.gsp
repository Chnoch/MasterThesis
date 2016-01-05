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
</head>

<body>
<div class="col-sm-12 title">
    <h1>Select File</h1>
</div>

<div class="col-sm-12">
    <g:form role="form" action="updateStations" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label>Upload file to initialize Stations:</label>
            <input type="file" name="data"/>
        </div>
        <button type="submit" class="btn btn-info">Submit</button>
    </g:form>
</div>
</body>
</html>