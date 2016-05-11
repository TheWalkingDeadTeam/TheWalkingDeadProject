<%--
  Created by IntelliJ IDEA.
  User: IGOR
  Date: 03.05.2016
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Course enrollment session settings</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="icon" type="image/png" sizes="32x32" href="/resources/images/ico.png"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/styles.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.css" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
<header class="header">
    <div class="container-fluid navbar headerTop">
        <a href="#"><img class="col-lg-4 col-md-4 col-sm-9 col-xs-12" src="/resources/images/logo.png" alt="logo"/></a>
    </div>
    <div class="col-lg-12 col-md-12 hidden-sm hidden-xs">
        <div class="container-fluid navbar-inner navigation">
            <a class="col-lg-4 col-md-4" href="#"><p>Home</p></a>
            <a class="col-lg-4 col-md-4" href="#"><p>Information</p></a>
            <a class="col-lg-4 col-md-4" href="#"><p>Contacts</p></a>
        </div>
    </div>
</header>
<body ng-app="myApp" ng-controller="FormController as ctrl">
    <form id="CESfields" ng-submit="ctrl.save()">
        <div>Year <input type="number" name="year" min="2016" max="2100" id="1" ng-model="ctrl.ces.year" ng-readonly="current" required/></div>
        <div>Quota <input type="number" name="quota" id="2" min="1" ng-model="ctrl.ces.quota" required/></div>
        <div>Start registration date <input type="date" name="startRegistrationDate" ng-model="ctrl.ces.startRegistrationDate" ng-readonly="current" id="3"required/></div>
        <div>End registration date <input type="date" name="endRegistrationDate" ng-model="ctrl.ces.endRegistrationDate" ng-readonly="current" id="4"required/></div>
        <div>Start interviewing date <input type="date" name="startInterviewingDate" ng-model="ctrl.ces.startInterviewingDate" ng-readonly="interviewBegan" id="5"/></div>
        <div>End interviewing date <input type="date" name="endInterviewingDate" ng-model="ctrl.ces.endInterviewingDate" ng-readonly="interviewBegan"  id="6"/></div>
        <div>Reminders <input type="number" name="reminders" id="7" ng-model="ctrl.ces.reminders" ng-readonly="current" required/></div>
        <div>Interview time for person <input type="number" name="interviewTimeForPerson" id="8" ng-model="ctrl.ces.interviewTimeForPerson" ng-readonly="interviewBegan"  required/></div>
        <div>Interview time for day <input type="number" name="interviewTimeForDay" id="9" ng-model="ctrl.ces.interviewTimeForDay" ng-readonly="interviewBegan"  required/></div>
        <input type="submit" value="Save" id="11">
    </form>
    <script src = "http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>

</body>

<footer class="footer container-fluid">
    <div class="footerLg container visible-md visible-lg">
        <img class="col-lg-3 col-lg-3 col-sm-3" src="/resources/images/logo-gray.png"/>
        <div class="col-lg-8 col-md-8 col-lg-offset-1 col-lg-offset-1 col-md-offset-1">
            <div class="footerLgText col-lg-3 col-md-3 col-lg-offset-1 col-md-offset-1">
                <p>Univercity Office Park III</p>
                <p>95 Sawyer Road</p>
                <p>Waltham, MA 02453 USA</p>
                <p>1-781-419-3300</p>
            </div>
            <div class="footerLgText col-lg-3 col-md-3 col-lg-offset-1 col-md-offset-1">
                <p>Facebook /NetcrackerTech</p>
                <p>Twitter @NetcrackerTech</p>
                <p>LikedIn /netcracker</p>
            </div>
            <div class="footerLgText col-lg-3 col-md-3 col-lg-offset-1 col-md-offset-1">
                <p>Privacy Policy</p>
                <p>Terms of Use</p>
                <p>Sitemap</p>
            </div>
        </div>
    </div>
    <div class="footerSm row visible-sm visible-xs">
        <img class="col-sm-5 visible-sm" src="/resources/images/logo-gray.png"/>
        <div class="footerSmText col-sm-7 col-xs-12">
            <div class="col-sm-8 col-xs-6">
                <a class="col-sm-6 col-xs-7" href="#"><p>Courses Info</p></a>
                <a class="col-sm-6 col-xs-7" href="#"><p>Contacts</p></a>
            </div>
            <div class="col-sm-4 col-xs-3 pull-right">
                <p>Privacy Policy</p>
                <p>Terms of Use</p>
                <p>Sitemap</p>
            </div>
        </div>
    </div>
</footer>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/js/ces.js" ></script>
</body>
</html>

