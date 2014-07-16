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

<div class="col-sm-5">
    <g:form role="form" action="selectUser" method="post">
        <g:hiddenField name="defaultFile" value="true"/>
        <div class="form-group">
            <label>Select default file: learning_data.csv</label><br/>
            Data from 2013-12-28 until 2014-06-12
        </div>
        <button type="submit" class="btn btn-primary">Select default file</button>
    </g:form>
</div>

<div class="col-sm-2 form-divider">
    - or -
</div>

<div class="col-sm-5">
    <g:form role="form" action="selectUser" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label>Upload file:</label>
            <input type="file" name="data"/>
        </div>
        <button type="submit" class="btn btn-info">Submit</button>
    </g:form>
</div>
</body>
</html>