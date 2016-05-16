<%--
  Created by IntelliJ IDEA.
  User: Alexander
  Date: 29.04.2016
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en" ng-app="interView">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="A front-end template that helps you build fast, modern mobile web apps.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>Material Design Lite</title>

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
    <link rel="stylesheet" href="/resources/css/notification/angular-ui-notification.min.css">
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
<main ng-controller="interCtrl as data"
      class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">
    <jsp:include page="admin-header.jsp"/>


    <main class="mdl-layout__content mdl-color--grey-100">
        <div>
            <button ng-click="activateStud()"
                    class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">
                Activate
            </button>
            <button ng-click="deactivateStud()"
                    class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">
                Deactivate
            </button>


            <a href="#FooOne"
               class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white"
               data-toggle="collapse">Mail</a>

            <a href="#FooTwo"
               class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white"
               data-toggle="collapse">Mail With Template</a>

            <div id="FooOne" class="collapse">
                <jsp:include page="custom-mail.jsp"/>
            </div>


            <div id="FooTwo" class="collapse">
                <jsp:include page="custom-mail-template-interviewers.jsp"/>
            </div>



        </div>
        <table class="table table-bordered table-striped" style="{margin-top: 200px}">

            <thead>
            <tr>
                <td>
                    <input type="checkbox" ng-model="selectedAll" ng-click="checkAll()">
                </td>
                <td>
                    <a href="#" ng-click="order_by = 'id'; sortReverse = !sortReverse; sortType(order_by)">
                        #
                    </a>
                </td>
                <td>
                    <a href="#" ng-click="order_by = 'name'; sortReverse = !sortReverse; sortType(order_by)">
                        Name
                    </a>
                </td>
                <td>
                    <a href="#" ng-click="order_by = 'university'; sortReverse = !sortReverse; sortType(order_by)">
                        Surname
                    </a>
                </td>
                <td>
                    <a href="#" ng-click="order_by = 'email'; sortReverse = !sortReverse; sortType(order_by)">
                        Email
                    </a>
                </td>
                <td>
                    <a href="#" ng-click="order_by = 'role'; sortReverse = !sortReverse; sortType(order_by)">
                        Role
                    </a>
                </td>
                <td>
                    <a href="#" ng-click="order_by = 'participation'; sortReverse = !sortReverse; sortType(order_by)">
                        Participation
                    </a>
                </td>
            </tr>
            </thead>
            <tbody>
            <tr ng-show="data.users.length <= 0">
                <td colspan="5" style="text-align:center;">Bratiska POGODI</td>
            </tr>
            <tr dir-paginate="user in data.users|itemsPerPage:data.itemsPerPage" total-items="data.total_count">
                <td><input type="checkbox" checklist-model="dataStudents.studId" checklist-value="user.id">
                </td>
                <td ng-init="index=$index + 1">{{index}}</td>
                <td>{{user.name}}</td>
                <td>{{user.surname}}</td>
                <td>{{user.email}}</td>
                <td>{{user.role}}</td>
                <td>{{user.participation}}</td>
            </tr>
            </tbody>
        </table>

        <dir-pagination-controls
                max-size="8"
                direction-links="true"
                boundary-links="true"
                on-page-change="data.setPageno(newPageNumber)">
        </dir-pagination-controls>


    </main>
</main>

<script src="/resources/js/notification/angular-ui-notification.min.js"></script>
<script src="https://code.getmdl.io/1.1.3/material.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/js/admin-create-user.js"></script>
<script src="/resources/bootstrap/js/bootstrap.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular-route.js"></script>
<script src="../../resources/js/interviewListAngular.js"></script>
<script src="../../resources/js/dirPagination.js"></script>
<script src="http://vitalets.github.io/checklist-model/checklist-model.js"></script>
</body>
</html>
