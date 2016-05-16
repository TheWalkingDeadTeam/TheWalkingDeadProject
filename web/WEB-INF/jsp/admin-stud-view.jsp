<%--
  Created by IntelliJ IDEA.
  User: Alexander
  Date: 29.04.2016
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en" ng-app="studentView">
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
    <link rel="stylesheet" href="/resources/css/checkbox.css">
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
    <link href="<c:url value='/resources/css/app.css' />" rel="stylesheet">
</head>
<body>
<main ng-controller="StudentCtrl as data"
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
            <button ng-click="rejectStud()"
                    class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">
                Reject
            </button>
            <button ng-click="unrejectStud()"
                    class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">
                Accept
            </button>
            <button ng-click="unrejectStud()"
                    class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">
                Accept
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
                <jsp:include page="custom-mail-template.jsp"/>
            </div>


            <%--<button ng-click="mail()"--%>
            <%--class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">--%>
            <%--Mail--%>
            <%--</button>--%>


            <div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable">
                <label class="mdl-button mdl-js-button mdl-button--icon" for="search">
                    <i class="material-icons">search</i>
                </label>
                <div class="mdl-textfield__expandable-holder">
                    <form ng-submit="searchFiltr(field)"><input class="mdl-textfield__input" type="text" id="search"
                                                                name="field" ng-model="field"></form>
                    <label class="mdl-textfield__label" for="search">Enter your query...</label>
                </div>
            </div>
            <%--<button ng-click="saveChanges()"--%>
            <%--class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">--%>
            <%--Save--%>
            <%--</button>--%>
            <%--<div class="checkbox-dropdown">--%>
            <%--Choose column--%>
            <%--&lt;%&ndash;<ul  class="checkbox-dropdown-list">&ndash;%&gt;--%>
            <%--<ul class="checkbox-dropdown-list">--%>
            <%--<li ng-repeat="(key,value) in data.users[0]">--%>
            <%--<label>--%>
            <%--<input type="checkbox" />{{key}}</label></li>--%>
            <%--</ul>--%>
            <%--&lt;%&ndash;</ul>&ndash;%&gt;--%>
            <%--</div>--%>

            <div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable">
                <label class="mdl-button mdl-js-button mdl-button--icon" for="search">
                    <i class="material-icons">search</i>
                </label>
                <div class="mdl-textfield__expandable-holder">
                    <form ng-submit = "searchFiltr(field)"><input  class="mdl-textfield__input" type="text" id="search" name="field" ng-model="field"></form>
                    <label class="mdl-textfield__label" for="search">Enter your query...</label>
                </div>
            </div>
        </div>
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <td>
                    <input type="checkbox" ng-model="selectedAll" ng-click="checkAll()">
                </td>
                <td>
                    <a href="#" ng-click="sortType = 'id'; sortReverse = !sortReverse; n">
                        #
                    </a>
                </td>
                <td>
                    <a href="#" ng-click="sortReverse = !sortReverse; sortType(0,sortReverse)">
                        Full Name
                    </a>
                </td>
                <%--<td>--%>
                <%--<a href="#" ng-click="sortType = 'university'; sortReverse = !sortReverse">--%>
                <%--University--%>
                <%--<span ng-show="sortType == 'university' && !sortReverse" class="fa fa-caret-down"></span>--%>
                <%--<span ng-show="sortType == 'university' && sortReverse" class="fa fa-caret-up"></span>--%>
                <%--</a>--%>
                <%--</td>--%>
                <%--<td>--%>
                <%--<a href="#" ng-click="sortType = 'devMark'; sortReverse = !sortReverse">--%>
                <%--Dev Assesment--%>
                <%--<span ng-show="sortType == 'devMark' && !sortReverse" class="fa fa-caret-down"></span>--%>
                <%--<span ng-show="sortType == 'devMark' && sortReverse" class="fa fa-caret-up"></span>--%>
                <%--</a>--%>
                <%--</td>--%>
                <%--<td>--%>
                <%--<a href="#" ng-click="sortType = 'hrMark'; sortReverse = !sortReverse">--%>
                <%--HR Assesment--%>
                <%--<span ng-show="sortType == 'hrMark' && !sortReverse" class="fa fa-caret-down"></span>--%>
                <%--<span ng-show="sortType == 'hrMark' && sortReverse" class="fa fa-caret-up"></span>--%>
                <%--</a>--%>
                <%--</td>--%>
                <td ng-repeat="head in data.header">
                    <a href="#" ng-click="sortReverse = !sortReverse; sortType(head.id,sortReverse)">
                        {{head.name}}
                    </a>
                </td>
                <td>
                    <a href="#" ng-click="sortTyprara='rejected'; sortReverse = !sortReverse">
                        Rejected
                    </a>
                </td>
            </tr>
            </thead>
            <tbody>
            <tr ng-show="data.users.length <= 0">
                <td colspan="5" style="text-align:center;">Please Wait</td>
            </tr>
            <tr dir-paginate="user in data.users|itemsPerPage:data.itemsPerPage" total-items="data.total_count">
                <td><input type="checkbox" checklist-model="dataStudents.studId" checklist-value="user.userId"></td>
                <td ng-init="index=$index + 1">{{index}}</td>
                <td><a href="/admin/students/{{user.userId}}" target="_blanks">{{user.name}}</a></td>

                <td ng-repeat="head in data.header">
                    {{user.fields[head.id]}}
                </td>
                <td ng-style="{opacity:0.5,'background-color':'{{user.rejected ? 'red' : 'green'}}'}">{{user.rejected}}</td>
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
<script src="/resources/js/checkboxScript.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular-route.js"></script>
<script src="/resources/js/studentListAngular.js"></script>
<script src="/resources/js/dirPagination.js"></script>
<script src="http://vitalets.github.io/checklist-model/checklist-model.js"></script>

</body>


</html>
