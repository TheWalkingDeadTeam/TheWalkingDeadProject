<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 09.05.2016
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
    <script src="/resources/js/report.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
    <link href="<c:url value='/resources/css/app.css' />" rel="stylesheet"/>


</head>

<body ng-app="reporter">
<div class="table-responsive">
    <table ng-controller="ReportController as rc" class="table">
        <thead>
        <tr>
            <th ng-repeat="(key, val) in rc.reports[0]">{{key}}</th>
        </tr>
        </thead>
        <tbody>
        <tr ng-repeat="r in rc.reports">
            <td ng-repeat="(key,val) in r">{{val}}</td>
        </tr>
        </tbody>
    </table>
</div>
</body>

</html>
