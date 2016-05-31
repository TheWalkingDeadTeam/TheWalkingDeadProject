<%--
  Created by IntelliJ IDEA.
  User: Alexander
  Date: 09.05.2016
  Time: 16:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html lang="en" ng-app="enrollView">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="A front-end template that helps you build fast, modern mobile web apps.">
    <title>Enrollment Session View</title>
    <meta name="mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="Material Design Lite">
    <link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.css"/>
    <meta name="msapplication-TileImage" content="images/touch/ms-touch-icon-144x144-precomposed.png">
    <meta name="msapplication-TileColor" content="#3372DF">
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en"/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons"/>
    <link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.cyan-light_blue.min.css"/>
    <link rel="stylesheet" href="/resources/css/styles.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/resources/css/roboto-style/roboto.css">
    <style>
        .panel{
            margin-left: 2%;
            margin-top: 2%;
        }
    </style>

</head>
<body class="ng-cloak">
<div clas="generic-container" ng-controller="enrollCtrl"
     class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">
    <jsp:include page="admin-header.jsp"/>
    <main class="mdl-layout__content mdl-color--grey-100">
        <div class="panel panel-default">
            <div class="panel-heading"><span class="lead"><spring:message code="locale.EnrollmentHistory"/></span></div>
            <div class="table-responsive">
                <table class="table table-hover table-bordered table-striped span11">
                    <thead>
                    <tr>
                        <td>#</td>

                        <td><spring:message code="locale.year"/><td>
                        <spring:message code="locale.startReg"/></td>
                        <td><spring:message code="locale.endReg"/></td>
                        <td><spring:message code="locale.startInt"/></td>
                        <td><spring:message code="locale.endInt"/></td>
                        <td><spring:message code="locale.quota"/></td>
                        <td><spring:message code="locale.reminders"/></td>
                        <td><spring:message code="locale.status"/> Id</td>
                        <td><spring:message code="locale.timePerson"/></td>
                        <td><spring:message code="locale.timeDay"/></td>
                        <td><spring:message code="locale.report"/></td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-hide="enrollCtrl.cesTable" ng-repeat="ch in session | orderBy:sortType:sortReverse | filter:searchFilt">
                        <td ng-init="index=$index + 1">{{index}}</td>
                        <td>{{ch.year}}</td>
                        <td>{{ch.startRegistrationDate}}</td>
                        <td>{{ch.endRegistrationDate}}</td>
                        <td>{{ch.startInterviewingDate}}</td>
                        <td>{{ch.endInterviewingDate}}</td>
                        <td>{{ch.quota}}</td>
                        <td>{{ch.reminders}}</td>
                        <td>{{ch.statusId}}</td>
                        <td>{{ch.interviewTimeForPerson}}</td>
                        <td>{{ch.interviewTimeForDay}}</td>
                        <td>
                            <a href="/report/view/ces?{{ch.id}}" target="_blank"><img
                                    src="/resources/images/view.png"
                                    alt="View" width="50" height="50"
                                    title="View"/></a>
                            <a href="/reports/download/ces/{{ch.id}}"><img src="/resources/images/excel.png"
                                                                           alt="Excel"
                                                                           width="50" height="50" title="Excel"/></a>
                        </td>
                    </tr>
                    <tr ng-hide="enrollCtrl.emptyTemplateTable != true">

                        <td colspan="12" class="vert-align col-xs-12 text-center"> <spring:message code="locale.emptyTable"/></td>
                    </tr>

                    </tbody>
                </table>
            </div>
        </div>
    </main>
</div>
<script src="https://code.getmdl.io/1.1.3/material.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/js/admin-create-user.js"></script>
<script src="/resources/bootstrap/js/bootstrap.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular-route.js"></script>
<script src="/resources/js/enrollment-session.js"></script>
<script src="http://vitalets.github.io/checklist-model/checklist-model.js"></script>
<script type="text/javascript" src="/resources/js/mdb.js"></script>
<script src="/resources/js/adminmenu.js"></script>
</body>
</html>
