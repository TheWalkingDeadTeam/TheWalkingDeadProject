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
    <title>Interviewers</title>

    <!-- Add to homescreen for Chrome on Android -->
    <meta name="mobile-web-app-capable" content="yes">
    <link rel="icon" sizes="192x192" href="images/android-desktop.png">

    <!-- Add to homescreen for Safari on iOS -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="Material Design Lite">
    <link rel="apple-touch-icon-precomposed" href="images/ios-desktop.png">
    <link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" href="/resources/css/notification/angular-ui-notification.min.css">


    <!-- Tile icon for Win8 (144x144 + tile color) -->
    <meta name="msapplication-TileImage" content="images/touch/ms-touch-icon-144x144-precomposed.png">
    <meta name="msapplication-TileColor" content="#3372DF">

    <link rel="icon" type="image/png" sizes="32x32" href="/resources/images/ico.png"/>


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
<main ng-controller="InterCtrl as data"
      class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">
    <jsp:include page="admin-header.jsp"/>


    <main class="mdl-layout__content mdl-color--grey-100">
        <div>
            <button ng-click="subscribeInterviewer()"
                    class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">
                Subscribe for CES
            </button>
            <button ng-click="unsubscribeInterviewer()"
                    class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">
                Unsubscribe from CES
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


            <div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable">
                <label class="mdl-button mdl-js-button mdl-button--icon" for="search">
                    <i class="material-icons">search</i>
                </label>
                <div class="mdl-textfield__expandable-holder">
                    <form ng-submit = "searchFiltr(field)"><input  class="mdl-textfield__input" type="text" id="search" name="field" ng-model="field"></form>
                    <label class="mdl-textfield__label" for="search">Enter your query...</label>
                </div>
            </div>
            <div class="cssload-thecube">
                <div class="cssload-cube cssload-c1"></div>
                <div class="cssload-cube cssload-c2"></div>
                <div class="cssload-cube cssload-c4"></div>
                <div class="cssload-cube cssload-c3"></div>
            </div>
        </div>
        <div class="table-responsive">
        <table id="tableUsers" class="table table-bordered table-striped" style="{margin-top: 200px}">

            <thead>
            <tr>
                <td>
                    <input type="checkbox" ng-model="selectedAll" ng-click="checkAll()">
                </td>
                <td>
                    <a ng-click="sortReverse = !sortReverse; sortType('system_user_id',sortReverse); ">
                        id
                    </a>
                </td>
                <td>
                    <a ng-click="sortReverse = !sortReverse; sortType('name',sortReverse); ">
                        Name
                    </a>
                </td>
                <td>
                    <a ng-click="sortReverse = !sortReverse; sortType('surname',sortReverse); ">
                        Surname
                    </a>
                </td>
                <td>
                    <a ng-click="sortReverse = !sortReverse; sortType('email',sortReverse);">
                        Email
                    </a>
                </td>
                <td>
                    <a ng-click="sortReverse = !sortReverse; sortType('role',sortReverse); ">
                        Role
                    </a>
                </td>
                <td>
                    <a>
                        Participation
                    </a>
                </td>
            </tr>
            </thead>
            <tbody>
            <tr ng-show="data.users.length <= 0">
                <td colspan="5" style="text-align:center;">No data</td>
            </tr>
            <tr dir-paginate="user in data.users|itemsPerPage:data.itemsPerPage" total-items="data.total_count">
                <td><input type="checkbox" checklist-model="dataStudents.studId" checklist-value="user.id">
                </td>
                <%--<td ng-init="index=$index + 1">{{index}}</td>--%>
                <td><a href="/account?{{user.id}}" target="_blanks">{{user.id}}</a></td>
                <td>{{user.name}}</td>
                <td>{{user.surname}}</td>
                <td>{{user.email}}</td>
                     <td>{{user.role}}</td>
                <td ng-style="{opacity:0.8,'background-color':'{{user.participation ? 'green' : 'red'}}'}"
                title="{{user.participation}}">

                </td>
            </tr>
            </tbody>
        </table>
            </div>
        <div id="pagination">
        <dir-pagination-controls
                max-size="8"
                direction-links="true"
                boundary-links="true"
                on-page-change="data.setPageno(newPageNumber)">
        </dir-pagination-controls>
        </div>

    </main>
</main>


<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular-route.js"></script>
<script src="/resources/js/notification/angular-ui-notification.min.js"></script>
<script src="https://code.getmdl.io/1.1.3/material.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/js/notification/angular-ui-notification.min.js"></script>
<script src="/resources/bootstrap/js/bootstrap.js"></script>
<script src="/resources/js/interview-list.js"></script>
<script src="/resources/js/dirPagination.js"></script>
<script src="http://vitalets.github.io/checklist-model/checklist-model.js"></script>
<script src="/resources/js/adminmenu.js"></script>

</body>

</html>
