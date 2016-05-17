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
    <script src="/resources/js/reportid.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
    <link href="<c:url value='/resources/css/app.css' />" rel="stylesheet"/>


</head>

<style>
    #mydiv {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        z-index: 1000;
        background-color: white;
        opacity: .8;
    }

    .loader {
        position: absolute;
        left: 50%;
        top: 50%;
        margin-left: -32px; /* -1 * image width / 2 */
        margin-top: -32px; /* -1 * image height / 2 */
        display: block;
    }

    .table-fixed thead {
        width: 97%;
    }
    .table-fixed tbody {
        height: 230px;
        overflow-y: auto;
        width: 100%;
    }
    .table-fixed thead, .table-fixed tbody, .table-fixed tr, .table-fixed td, .table-fixed th {
        display: block;
    }
    .table-fixed tbody td, .table-fixed thead > tr> th {
        float: left;
        border-bottom-width: 0;
    }
</style>
<body ng-app="reporterid" ng-controller="ReportControllerId as rc">
<div id="mydiv" ng-hide="rc.loading">
    <img src="/resources/images/load.gif" class="loader"/>
</div>
<div class="container">
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4>
                    Fixed Header Scrolling Table
                </h4>
            </div>
            <table class="table table-responsive header-fixed ">
                <thead>
                <tr>
                    <th class="col-xs-1" ng-repeat="(key, val) in rc.reports[0]">{{key}}</th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="r in rc.reports">
                    <td class="col-xs-1 filterable-cell" ng-repeat="(key,val) in r">{{val}}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>


</html>
