<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 09.05.2016
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html ng-app="reporter">

<head>
    <link data-require="bootstrap-css@3.2.0" data-semver="3.2.0" rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
    <script data-require="ui-bootstrap@0.11.0" data-semver="0.11.0" src="http://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.11.0.min.js"></script>
    <script data-require="lodash.js@3.10.0" data-semver="3.10.0" src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/3.10.0/lodash.js"></script>
<%--
    <script src="/resources/js/script.js"></script>
--%>
    <script src="/resources/js/report.js"></script>
<%--
    <script src="/resources/js/smart-table.js"></script>
--%>
</head>

<body ng-controller="ReportController">
<table >
    HELLOU FROME INDIA
    <thead>

    <tr ng-repeat="d in ReportController.reports">
        <th>{{d.name}}</th>
    </tr>
    </thead>
    <tbody>
<%--    <tr ng-repeat="row in displayed">
        <td>{{row.firstName}}</td>
        <td>{{row.lastName | uppercase}}</td>
        <td>{{row.nationality}}</td>
        <td>{{row.education}}</td>
    </tr>--%>
    </tbody>
    <tfoot></tfoot>
</table>
</body>

</html>
