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
<g:form role="form" class="form-horizontal" action="selectUser" method="post" enctype="multipart/form-data">
    <div class="form-group">
        <label for="inputEmail3" class="col-sm-2 control-label">Email</label>
        <div class="col-sm-10">
            <input type="email" class="form-control" id="inputEmail3" placeholder="Email">
        </div>
    </div>
    <div class="form-group">
        <label for="inputEmail3" class="col-sm-2 control-label">Or upload file: </label>
        <div class="col-sm-10">
    <input type="file" name="data" />
        </div>
    </div>
    <button type="submit" class="btn btn-default">Submit File</button>
</g:form>
</body>
</html>