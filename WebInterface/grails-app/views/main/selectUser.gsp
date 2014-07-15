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
<g:form class="form-horizontal" name="displayGraph" action="displayGraph" >
    <g:select name="user" from="${userList}"/>
    <button type="submit" class="btn">Select User</button>
</g:form>

</body>
</html>