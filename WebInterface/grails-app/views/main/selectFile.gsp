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
<g:form class="form-horizontal" name="fileUpload" action="selectUser" method="post" enctype="multipart/form-data">
    <input type="file" name="data" />
    <button type="submit" class="btn">Submit File</button>
</g:form>
</body>
</html>