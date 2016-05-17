<%--
  Created by IntelliJ IDEA.
  User: Alexander
  Date: 09.05.2016
  Time: 16:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en" ng-app="enrollView">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="A front-end template that helps you build fast, modern mobile web apps.">
    <title>Enrollment Session View</title>
    <!-- Add to homescreen for Chrome on Android -->
    <meta name="mobile-web-app-capable" content="yes">
    <link rel="icon" sizes="192x192" href="images/android-desktop.png">
    <!-- Add to homescreen for Safari on iOS -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="Material Design Lite">
    <link rel="apple-touch-icon-precomposed" href="images/ios-desktop.png">
    <link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.css"/>


    <!-- Tile icon for Win8 (144x144 + tile color) -->
    <meta name="msapplication-TileImage" content="images/touch/ms-touch-icon-144x144-precomposed.png">
    <meta name="msapplication-TileColor" content="#3372DF">

    <link rel="shortcut icon" href="images/favicon.png">

    <!-- SEO: If your mobile URL is different from the desktop URL, add a canonical link to the desktop page https://developers.google.com/webmasters/smartphone-sites/feature-phones -->
    <!--
    <link rel="canonical" href="http://www.example.com/">
    -->

    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.cyan-light_blue.min.css">
    <link rel="stylesheet" href="/resources/css/styles.css">
    <style>
        #view-source {
            position: fixed;
            display: block;
            right: 0;
            bottom: 0;
            margin-right: 40px;
            margin-bottom: 40px;
            z-index: 900;
        }
        td {
            text-align: center;
            vertical-align: middle;
        }

    </style>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link href="<c:url value='/resources/css/app.css' />" rel="stylesheet"/>
</head>
<body class="ng-cloak">
<div clas="generic-container" ng-controller="enrollCtrl"
     class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">
    <jsp:include page="admin-header.jsp"/>
    <main class="mdl-layout__content mdl-color--grey-100">
        <div class="panel panel-default">
        <div class="container">
            <div class="panel-heading"><span class="lead">Enrollment Session History </span></div>
            <div class="table-responsive">
            <table class="table  table table-hover table-bordered table-striped span11">
                <thead>
                <tr>
                    <td>
                        <a href="#" ng-click="sortType = 'id'; sortReverse = !sortReverse">
                            #
                            <span ng-show="sortType == 'id' && !sortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="sortType == 'id' && sortReverse" class="fa fa-caret-up"></span>
                        </a>
                    </td>

                    <td>
                        <a href="#" ng-click="sortType = 'year'; sortReverse = !sortReverse">
                            Year
                            <span ng-show="sortType == 'year' && !sortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="sortType == 'year' && sortReverse" class="fa fa-caret-up"></span>
                        </a>
                    </td>

                    <td>
                        <a href="#" ng-click="sortType = 'startRegistrationDate'; sortReverse = !sortReverse">
                            Start Registration Date
                            <span ng-show="sortType == 'startRegistrationDate' && !sortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="sortType == 'startRegistrationDate' && sortReverse" class="fa fa-caret-up"></span>
                        </a>
                    </td>

                    <td>
                        <a href="#" ng-click="sortType = 'endRegistrationDate'; sortReverse = !sortReverse">
                            End Registration Date
                            <span ng-show="sortType == 'endRegistrationDate' && !sortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="sortType == 'endRegistrationDate' && sortReverse" class="fa fa-caret-up"></span>
                        </a>
                    </td>

                    <td>
                        <a href="#" ng-click="sortType = 'startInterviewingDate'; sortReverse = !sortReverse">
                            Start Interview Date
                            <span ng-show="sortType == 'startInterviewingDate' && !sortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="sortType == 'startInterviewingDate' && sortReverse" class="fa fa-caret-up"></span>
                        </a>
                    </td>

                    <td>
                        <a href="#" ng-click="sortType = 'endInterviewingDate'; sortReverse = !sortReverse">
                            End Interview Date
                            <span ng-show="sortType == 'endInterviewingDate' && !sortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="sortType == 'endInterviewingDate' && sortReverse" class="fa fa-caret-up"></span>
                        </a>
                    </td>

                    <td>
                        <a href="#" ng-click="sortType = 'quota'; sortReverse = !sortReverse">
                            Quota
                            <span ng-show="sortType == 'quota' && !sortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="sortType == 'quota' && sortReverse" class="fa fa-caret-up"></span>
                        </a>
                    </td>

                    <td>
                        <a href="#" ng-click="sortType = 'reminders'; sortReverse = !sortReverse">
                            Reminders
                            <span ng-show="sortType == 'reminders' && !sortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="sortType == 'reminders' && sortReverse" class="fa fa-caret-up"></span>
                        </a>
                    </td>

                    <td>
                        <a href="#" ng-click="sortType = 'statusId'; sortReverse = !sortReverse">
                            Status Id
                            <span ng-show="sortType == 'statusId' && !sortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="sortType == 'statusId' && sortReverse" class="fa fa-caret-up"></span>
                        </a>
                    </td>

                    <td>
                        <a href="#" ng-click="sortType = 'interviewTimeForPerson'; sortReverse = !sortReverse">
                            Interview Time For Person
                            <span ng-show="sortType == 'interviewTimeForPerson' && !sortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="sortType == 'interviewTimeForPerson' && sortReverse" class="fa fa-caret-up"></span>
                        </a>
                    </td>
                    <td>
                        <a href="#" ng-click="sortType = 'interviewTimeForDay'; sortReverse = !sortReverse">
                            Interview Time For Day
                            <span ng-show="sortType == 'interviewTimeForDay' && !sortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="sortType == 'interviewTimeForDay' && sortReverse" class="fa fa-caret-up"></span>
                        </a>
                    </td>
                </tr>
                </thead>
                <tbody valign="center">
                <tr ng-repeat="ch in session | orderBy:sortType:sortReverse | filter:searchFilt">
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
                </tr>
                </tbody>
            </table>
        </div>
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
</body>

</html>
