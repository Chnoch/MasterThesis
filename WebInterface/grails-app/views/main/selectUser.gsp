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
    <script type="text/javascript">
        $(function () {
            $('#datatable').DataTable({
                "order": [ 1, 'desc' ]
            });
        });
    </script>
</head>

<body>
<div class="col-sm-12 title">
    <h1>Select User</h1>
</div>

<div class="col-sm-12">
    <table id="datatable" class="table table-striped table-bordered">
        <thead>
        <tr role="row">
            <th class="sorting_disabled">User ID</th>
            <th class="sorting_disabled"># of Entries</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${userMap}" var="user" status="i">
            <tr role="row" class="${i % 2 == 0 ? 'even' : 'odd'}">
                <td><g:link action="displayGraph" id="${user.key}">${user.key}</g:link></td>
                <td>${user.value}</td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>
%{--<g:form class="form-horizontal" name="displayGraph" action="displayGraph">--}%
%{--<g:select name="user" from="${userList}"/>--}%
%{--<button type="submit" class="btn">Select User</button>--}%
%{--</g:form>--}%

</body>
</html>