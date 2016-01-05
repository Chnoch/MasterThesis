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
    <h1>Stations</h1>
</div>

<div class="col-sm-12">
    <table id="datatable" class="table table-striped table-bordered">
        <thead>
        <tr role="row">
            <th class="sorting_disabled">Station ID</th>
            <th class="sorting_disabled">Station Name</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${stationList}" var="station" status="i">
            <tr role="row" class="${i % 2 == 0 ? 'even' : 'odd'}">
                <td>${station.stationId}</td>
                <td>${station.name}</td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>

</body>
</html>