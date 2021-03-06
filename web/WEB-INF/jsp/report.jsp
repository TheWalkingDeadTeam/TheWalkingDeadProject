<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    <link rel="icon" type="image/png" sizes="32x32" href="/resources/images/ico.png"/>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
    <script src="/resources/js/report.js"></script>
    <link href="/resources/css/report.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
    <link href=/resources/css/app.css" rel="stylesheet"/>


</head>


<body ng-app="reporterid" ng-controller="ReportControllerId as rc">
<div id="loaderdiv" ng-hide="rc.loading">
    <img src="/resources/images/load.gif" class="loader"/>
</div>


<div class="col-lg-18 col-md-12 col-sm-12 col-xs-12">
    <h4><spring:message code="locale.reportName"/> : <span ng-bind="rc.report.name"></span> </h4>

        <table class="table table-responsive table-bordered table-striped">
            <thead>
            <tr>
                <th class="col-xs-1" ng-repeat="(key, val) in rc.reportRows[0]">{{key}}</th>
            </tr>
            </thead>
            <tbody>
            <tr  ng-hide="rc.reportTemplateTable" ng-repeat="r in rc.reportRows">
                <td class="col-xs-1" ng-repeat="(key,val) in r">{{val}}</td>
            </tr>
            <tr ng-hide="rc.emptyTemplateTable == true">
                <td colspan="{{rc.colspan}}"  class="vert-align text-center">Empty table</td>
            </tr>
            </tbody>
        </table>
</div>



</body>


</html>
