<%--
  Created by IntelliJ IDEA.
  User: Neltarion
  Date: 31.05.2016
  Time: 0:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="A front-end template that helps you build fast, modern mobile web apps.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>ADMIN</title>

    <!-- Add to homescreen for Chrome on Android -->
    <meta name="mobile-web-app-capable" content="yes">


    <!-- Add to homescreen for Safari on iOS -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="Material Design Lite">
    <link rel="apple-touch-icon-precomposed" href="images/ios-desktop.png">

    <!-- Tile icon for Win8 (144x144 + tile color) -->
    <meta name="msapplication-TileImage" content="images/touch/ms-touch-icon-144x144-precomposed.png">
    <meta name="msapplication-TileColor" content="#3372DF">


    <!-- SEO: If your mobile URL is different from the desktop URL, add a canonical link to the desktop page https://developers.google.com/webmasters/smartphone-sites/feature-phones -->
    <!--
    <link rel="canonical" href="http://www.example.com/">
    -->

    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.cyan-light_blue.min.css">
    <link rel="stylesheet" href="/resources/css/styles.css">
    <link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="/resources/bootstrap/js/bootstrap.min.js" defer></script>
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
    </style>
</head>
<body>
<div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">
    <jsp:include page="admin-header.jsp"/>
    <main class="mdl-layout__content mdl-color--grey-100">
        <div class="mdl-grid demo-content">


            <%--<div class="demo-cards mdl-cell mdl-cell--4-col mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-grid mdl-grid--no-spacing">--%>
                <%--<div class="demo-updates mdl-card mdl-shadow--2dp mdl-cell mdl-cell--4-col mdl-cell--4-col-tablet mdl-cell--4-col-phone mdl-cell--12-col-desktop">--%>
                    <%--<div class="mdl-card__title mdl-card--expand mdl-color--teal-300">--%>
                        <%--<h2 class="mdl-card__title-text"> <i class="material-icons">date_range</i> Schedule planning</h2>--%>
                    <%--</div>--%>
                    <%--<div class="mdl-card__supporting-text mdl-color-text--grey-600">--%>
                        <%--You are able to create new Schedule--%>
                    <%--</div>--%>
                    <%--<div class="mdl-card__actions mdl-card--border">--%>
                        <%--<a href="/admin/scheduler" class="mdl-button mdl-js-button mdl-js-ripple-effect">Create New--%>
                            <%--Schedule</a>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="demo-separator mdl-cell--1-col"></div>--%>
            <%--</div>--%>

            <%--<sec:authorize access="hasRole('ROLE_ADMIN')">--%>
                <%--<div class="demo-cards mdl-cell mdl-cell--4-col mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-grid mdl-grid--no-spacing">--%>
                    <%--<div class="demo-updates mdl-card mdl-shadow--2dp mdl-cell mdl-cell--4-col mdl-cell--4-col-tablet mdl-cell--4-col-phone mdl-cell--12-col-desktop">--%>
                        <%--<div class="mdl-card__title mdl-card--expand mdl-color--teal-300">--%>
                            <%--<h2 class="mdl-card__title-text"><i class="material-icons">assignment_ind</i> Edit form template</h2>--%>
                        <%--</div>--%>
                        <%--<div class="mdl-card__supporting-text mdl-color-text--grey-600">--%>
                            <%--You are able to edit form template--%>
                        <%--</div>--%>
                        <%--<div class="mdl-card__actions mdl-card--border">--%>
                            <%--<a href="/admin/edit-form" class="mdl-button mdl-js-button mdl-js-ripple-effect">Edit Form--%>
                                <%--Template</a>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="demo-separator mdl-cell--1-col"></div>--%>
                <%--</div>--%>
            <%--</sec:authorize>--%>

            <%--<div class="demo-cards mdl-cell mdl-cell--4-col mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-grid mdl-grid--no-spacing">--%>
                <%--<div class="demo-updates mdl-card mdl-shadow--2dp mdl-cell mdl-cell--4-col mdl-cell--4-col-tablet mdl-cell--4-col-phone mdl-cell--12-col-desktop">--%>
                    <%--<div class="mdl-card__title mdl-card--expand mdl-color--teal-300">--%>
                        <%--<h2 class="mdl-card__title-text"><i class="material-icons">alarm</i> Registration period</h2>--%>
                    <%--</div>--%>
                    <%--<div class="mdl-card__supporting-text mdl-color-text--grey-600">--%>
                        <%--You are able to set course enroll session settings--%>
                    <%--</div>--%>
                    <%--<div class="mdl-card__actions mdl-card--border">--%>
                        <%--<a href="/admin/cessettings" class="mdl-button mdl-js-button mdl-js-ripple-effect">Set Course--%>
                            <%--Enroll Settings </a>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="demo-separator mdl-cell--1-col"></div>--%>
            <%--</div>--%>


            <%--<div class="demo-cards mdl-cell mdl-cell--4-col mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-grid mdl-grid--no-spacing">--%>
                <%--<div class="demo-updates mdl-card mdl-shadow--2dp mdl-cell mdl-cell--4-col mdl-cell--4-col-tablet mdl-cell--4-col-phone mdl-cell--12-col-desktop">--%>
                    <%--<div class="mdl-card__title mdl-card--expand mdl-color--teal-300">--%>
                        <%--<h2 class="mdl-card__title-text"><i class="material-icons">view_list</i> Application list</h2>--%>
                    <%--</div>--%>
                    <%--<div class="mdl-card__supporting-text mdl-color-text--grey-600">--%>
                        <%--You are able to view application list--%>
                    <%--</div>--%>
                    <%--<div class="mdl-card__actions mdl-card--border">--%>
                        <%--<a href="/admin/students" class="mdl-button mdl-js-button mdl-js-ripple-effect">View Application List</a>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="demo-separator mdl-cell--1-col"></div>--%>
            <%--</div>--%>

            <%--<div class="demo-cards mdl-cell mdl-cell--4-col mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-grid mdl-grid--no-spacing">--%>
                <%--<div class="demo-updates mdl-card mdl-shadow--2dp mdl-cell mdl-cell--4-col mdl-cell--4-col-tablet mdl-cell--4-col-phone mdl-cell--12-col-desktop">--%>
                    <%--<div class="mdl-card__title mdl-card--expand mdl-color--teal-300">--%>
                        <%--<h2 class="mdl-card__title-text"><i class="material-icons">supervisor_account</i> Interviewee list</h2>--%>
                    <%--</div>--%>
                    <%--<div class="mdl-card__supporting-text mdl-color-text--grey-600">--%>
                        <%--You are able to view interviewee list--%>
                    <%--</div>--%>
                    <%--<div class="mdl-card__actions mdl-card--border">--%>
                        <%--<a href="/admin/interviewee" class="mdl-button mdl-js-button mdl-js-ripple-effect">View Interviewee List</a>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="demo-separator mdl-cell--1-col"></div>--%>
            <%--</div>--%>

            <%--<div class="demo-cards mdl-cell mdl-cell--4-col mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-grid mdl-grid--no-spacing">--%>
                <%--<div class="demo-updates mdl-card mdl-shadow--2dp mdl-cell mdl-cell--4-col mdl-cell--4-col-tablet mdl-cell--4-col-phone mdl-cell--12-col-desktop">--%>
                    <%--<div class="mdl-card__title mdl-card--expand mdl-color--teal-300">--%>
                        <%--<h2 class="mdl-card__title-text"><i class="material-icons">supervisor_account</i> Interviewer List</h2>--%>
                    <%--</div>--%>
                    <%--<div class="mdl-card__supporting-text mdl-color-text--grey-600">--%>
                        <%--You are able to view interviewer List--%>
                    <%--</div>--%>
                    <%--<div class="mdl-card__actions mdl-card--border">--%>
                        <%--<a href="/admin/interviewers" class="mdl-button mdl-js-button mdl-js-ripple-effect">Interviewer List</a>--%>
                    <%--</div>--%>
                <%--</div>--%>

                <%--<div class="demo-separator mdl-cell--1-col"></div>--%>
            <%--</div>--%>
            <%--<sec:authorize access="hasRole('ROLE_ADMIN')">--%>
                <%--<div class="demo-cards mdl-cell mdl-cell--4-col mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-grid mdl-grid--no-spacing">--%>
                    <%--<div class="demo-updates mdl-card mdl-shadow--2dp mdl-cell mdl-cell--4-col mdl-cell--4-col-tablet mdl-cell--4-col-phone mdl-cell--12-col-desktop">--%>
                        <%--<div class="mdl-card__title mdl-card--expand mdl-color--teal-300">--%>
                            <%--<h2 class="mdl-card__title-text"><i class="material-icons">supervisor_account</i> User List</h2>--%>
                        <%--</div>--%>
                        <%--<div class="mdl-card__supporting-text mdl-color-text--grey-600">--%>
                            <%--You are able to view user List--%>
                        <%--</div>--%>
                        <%--<div class="mdl-card__actions mdl-card--border">--%>
                            <%--<a href="/admin/users" class="mdl-button mdl-js-button mdl-js-ripple-effect">User List</a>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="demo-separator mdl-cell--1-col"></div>--%>
                <%--</div>--%>
            <%--</sec:authorize>--%>

            <%--<sec:authorize access="hasRole('ROLE_ADMIN')">--%>
                <%--<div class="demo-cards mdl-cell mdl-cell--4-col mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-grid mdl-grid--no-spacing">--%>
                    <%--<div class="demo-updates mdl-card mdl-shadow--2dp mdl-cell mdl-cell--4-col mdl-cell--4-col-tablet mdl-cell--4-col-phone mdl-cell--12-col-desktop">--%>
                        <%--<div class="mdl-card__title mdl-card--expand mdl-color--teal-300">--%>
                            <%--<h2 class="mdl-card__title-text"><i class="material-icons">portrait</i> Create User</h2>--%>
                        <%--</div>--%>
                        <%--<div class="mdl-card__supporting-text mdl-color-text--grey-600">--%>
                            <%--You are able to create new Admin or Interviewer--%>
                        <%--</div>--%>
                        <%--<div class="mdl-card__actions mdl-card--border">--%>
                            <%--<a href="/admin/create-user" class="mdl-button mdl-js-button mdl-js-ripple-effect">Create--%>
                                <%--User</a>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="demo-separator mdl-cell--1-col"></div>--%>
                <%--</div>--%>
            <%--</sec:authorize>--%>


            <%--<div class="demo-cards mdl-cell mdl-cell--4-col mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-grid mdl-grid--no-spacing">--%>
                <%--<div class="demo-updates mdl-card mdl-shadow--2dp mdl-cell mdl-cell--4-col mdl-cell--4-col-tablet mdl-cell--4-col-phone mdl-cell--12-col-desktop">--%>
                    <%--<div class="mdl-card__title mdl-card--expand mdl-color--teal-300">--%>
                        <%--<h2 class="mdl-card__title-text"><i class="material-icons">contact_mail</i> Letters Template</h2>--%>
                    <%--</div>--%>
                    <%--<div class="mdl-card__supporting-text mdl-color-text--grey-600">--%>
                        <%--You are able to edit Letters Template here--%>
                    <%--</div>--%>
                    <%--<div class="mdl-card__actions mdl-card--border">--%>
                        <%--<a href="/admin/mail-template" class="mdl-button mdl-js-button mdl-js-ripple-effect">Letters--%>
                            <%--Template</a>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="demo-separator mdl-cell--1-col"></div>--%>
            <%--</div>--%>


            <%--<div class="demo-cards mdl-cell mdl-cell--4-col mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-grid mdl-grid--no-spacing">--%>
                <%--<div class="demo-updates mdl-card mdl-shadow--2dp mdl-cell mdl-cell--4-col mdl-cell--4-col-tablet mdl-cell--4-col-phone mdl-cell--12-col-desktop">--%>
                    <%--<div class="mdl-card__title mdl-card--expand mdl-color--teal-300">--%>
                        <%--<h2 class="mdl-card__title-text"><i class="material-icons">history</i> Session Enrollment History</h2>--%>
                    <%--</div>--%>
                    <%--<div class="mdl-card__supporting-text mdl-color-text--grey-600">--%>
                        <%--You are able to view session enrollment history--%>
                    <%--</div>--%>
                    <%--<div class="mdl-card__actions mdl-card--border">--%>
                        <%--<a href="/admin/enroll-session" class="mdl-button mdl-js-button mdl-js-ripple-effect">Session--%>
                            <%--Enrollment</a>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="demo-separator mdl-cell--1-col"></div>--%>
            <%--</div>--%>
            <%--<div class="demo-cards mdl-cell mdl-cell--4-col mdl-cell--8-col-tablet mdl-cell--4-col-phone mdl-grid mdl-grid--no-spacing">--%>
                <%--<div class="demo-updates mdl-card mdl-shadow--2dp mdl-cell mdl-cell--4-col mdl-cell--4-col-tablet mdl-cell--4-col-phone mdl-cell--12-col-desktop">--%>
                    <%--<div class="mdl-card__title mdl-card--expand mdl-color--teal-300">--%>
                        <%--<h2 class="mdl-card__title-text"><i class="material-icons">show_chart</i>Reports</h2>--%>
                    <%--</div>--%>
                    <%--<div class="mdl-card__supporting-text mdl-color-text--grey-600">--%>
                        <%--You are able to view and download reports--%>
                    <%--</div>--%>
                    <%--<div class="mdl-card__actions mdl-card--border">--%>
                        <%--<a href="/admin/report" class="mdl-button mdl-js-button mdl-js-ripple-effect">Reports</a>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="demo-separator mdl-cell--1-col"></div>--%>
            <%--</div>--%>
                <div class="text-center">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-12">
                                <h1 class="page-header">Course enroll session is ongoing!
                                    <small>So you can`t change registration form!</small>
                                </h1>
                                <img class="img-responsive" src="/resources/images/error-404.gif" style="margin: 0 auto;"/>
                            </div>
                        </div>
                    </div>
                </div>

        </div>
    </main>
</div>

<svg xmlns="http://www.w3.org/2000/svg" version="1.1"
     style="position: fixed; left: -1000px; height: -1000px;">
    <defs>
        <mask id="piemask" maskContentUnits="objectBoundingBox">
            <circle cx=0.5 cy=0.5 r=0.49 fill="white"/>
            <circle cx=0.5 cy=0.5 r=0.40 fill="black"/>
        </mask>
        <g id="piechart">
            <circle cx=0.5 cy=0.5 r=0.5/>
                <path d="M 0.5 0.5 0.5 0 A 0.5 0.5 0 0 1 0.95 0.28 z" stroke="none" fill="rgba(255, 255, 255, 0.75)"/>
        </g>
    </defs>
</svg>
<svg version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 500 250"
     style="position: fixed; left: -1000px; height: -1000px;">
    <defs>
        <g id="chart">
            <g id="Gridlines">
                <line fill="#888888" stroke="#888888" stroke-miterlimit="10" x1="0" y1="27.3" x2="468.3" y2="27.3"/>
                <line fill="#888888" stroke="#888888" stroke-miterlimit="10" x1="0" y1="66.7" x2="468.3" y2="66.7"/>
                <line fill="#888888" stroke="#888888" stroke-miterlimit="10" x1="0" y1="105.3" x2="468.3" y2="105.3"/>
                <line fill="#888888" stroke="#888888" stroke-miterlimit="10" x1="0" y1="144.7" x2="468.3" y2="144.7"/>
                <line fill="#888888" stroke="#888888" stroke-miterlimit="10" x1="0" y1="184.3" x2="468.3" y2="184.3"/>
            </g>
            <g id="Numbers">
                <text transform="matrix(1 0 0 1 485 29.3333)" fill="#888888" font-family="'Roboto'" font-size="9">500
                </text>
                <text transform="matrix(1 0 0 1 485 69)" fill="#888888" font-family="'Roboto'" font-size="9">400</text>
                <text transform="matrix(1 0 0 1 485 109.3333)" fill="#888888" font-family="'Roboto'" font-size="9">300
                </text>
                <text transform="matrix(1 0 0 1 485 149)" fill="#888888" font-family="'Roboto'" font-size="9">200</text>
                <text transform="matrix(1 0 0 1 485 188.3333)" fill="#888888" font-family="'Roboto'" font-size="9">100
                </text>
                <text transform="matrix(1 0 0 1 0 249.0003)" fill="#888888" font-family="'Roboto'" font-size="9">1
                </text>
                <text transform="matrix(1 0 0 1 78 249.0003)" fill="#888888" font-family="'Roboto'" font-size="9">2
                </text>
                <text transform="matrix(1 0 0 1 154.6667 249.0003)" fill="#888888" font-family="'Roboto'" font-size="9">
                    3
                </text>
                <text transform="matrix(1 0 0 1 232.1667 249.0003)" fill="#888888" font-family="'Roboto'" font-size="9">
                    4
                </text>
                <text transform="matrix(1 0 0 1 309 249.0003)" fill="#888888" font-family="'Roboto'" font-size="9">5
                </text>
                <text transform="matrix(1 0 0 1 386.6667 249.0003)" fill="#888888" font-family="'Roboto'" font-size="9">
                    6
                </text>
                <text transform="matrix(1 0 0 1 464.3333 249.0003)" fill="#888888" font-family="'Roboto'" font-size="9">
                    7
                </text>
            </g>
            <g id="Layer_5">
                <polygon opacity="0.36" stroke-miterlimit="10" points="0,223.3 48,138.5 154.7,169 211,88.5
              294.5,80.5 380,165.2 437,75.5 469.5,223.3 	"/>
            </g>
            <g id="Layer_4">
                <polygon stroke-miterlimit="10" points="469.3,222.7 1,222.7 48.7,166.7 155.7,188.3 212,132.7
              296.7,128 380.7,184.3 436.7,125 	"/>
            </g>
        </g>
    </defs>
</svg>
<script src="https://code.getmdl.io/1.1.3/material.min.js"></script>
</body>
<script src="/resources/js/logout.js"></script>
<script src="/resources/js/adminmenu.js"></script>

</html>


