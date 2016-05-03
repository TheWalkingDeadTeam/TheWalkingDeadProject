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
    <header class="demo-header mdl-layout__header mdl-color--grey-100 mdl-color-text--grey-600">
        <div class="mdl-layout__header-row">
            <span class="mdl-layout-title">Netcracker</span>
            <div class="mdl-layout-spacer"></div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable">
                <label class="mdl-button mdl-js-button mdl-button--icon" for="search">
                    <i class="material-icons">search</i>
                </label>
                <div class="mdl-textfield__expandable-holder">
                    <input class="mdl-textfield__input" type="text" id="search">
                    <label class="mdl-textfield__label" for="search">Enter your query...</label>
                </div>
            </div>
            <button class="mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--icon" id="hdrbtn">
                <i class="material-icons">more_vert</i>
            </button>
            <ul class="mdl-menu mdl-js-menu mdl-js-ripple-effect mdl-menu--bottom-right" for="hdrbtn">
                <li class="mdl-menu__item">About</li>
                <li class="mdl-menu__item">Contact</li>
                <li class="mdl-menu__item">Legal information</li>
            </ul>
        </div>
    </header>
    <div class="demo-drawer mdl-layout__drawer mdl-color--blue-grey-900 mdl-color-text--blue-grey-50">
        <header class="demo-drawer-header">
            <%--          <img src="/resources/images/dogvk.png" class="demo-avatar">--%>
            <div class="demo-avatar-dropdown">
                <span>hello@example.com</span>
                <div class="mdl-layout-spacer"></div>
                <button id="accbtn" class="mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--icon">
                    <i class="material-icons" role="presentation">arrow_drop_down</i>
                    <span class="visuallyhidden">Accounts</span>
                </button>
                <ul class="mdl-menu mdl-menu--bottom-right mdl-js-menu mdl-js-ripple-effect" for="accbtn">
                    <li class="mdl-menu__item">ion@gmail.com</li>
                    <li class="mdl-menu__item">info@example.com</li>
                    <li class="mdl-menu__item"><i class="material-icons">add</i>Add another account...</li>
                </ul>
            </div>
        </header>
        <nav class="demo-navigation mdl-navigation mdl-color--blue-grey-800">
            <a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons"
                                                       role="presentation">schedule</i>Schedule Planning</a>
            <a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons"
                                                       role="presentation">inbox</i>Form Template</a>
            <a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons"
                                                       role="presentation">local_offer</i>Registration Period</a>
            <a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons"
                                                       role="presentation">people</i>View Student List</a>
            <a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons"
                                                       role="presentation">people</i>Interviewer List</a>
            <a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons"
                                                       role="presentation">people</i>Create Interviewer</a>
            <a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons"
                                                       role="presentation">mail</i>Edit Letter Templates</a>
            <div class="mdl-layout-spacer"></div>
            <a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons"
                                                       role="presentation">help_outline</i><span class="visuallyhidden">Help</span></a>
        </nav>
    </div>


    <main class="mdl-layout__content mdl-color--grey-100">

        <div ng-controller="StudentCtrl">
            <form>
                <div class="input-group-addon"><i></i></div>
                <input type="text" class="form-control" placeholder="Search" ng-model="searchFiltr">
            </form>


            <button ng-click="activateStud()">
                Activate
            </button>
            <button ng-click="deactivateStud()">
                Deactivate
            </button>
            <button ng-click="rejectStud()">
                Reject
            </button>
            <button ng-click="saveChanges()">
                Save
            </button>
            <table class="table table-bordered table-striped">

                <thead>
                <tr>
                    <td>
                        <input type="checkbox" ng-model="selectedAll" ng-click="checkAll()">
                    </td>
                    <td>
                        <a href="#" ng-click="sortType = 'id'; sortReverse = !sortReverse">
                            Id
                            <span ng-show="sortType == 'id' && !sortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="sortType == 'id' && sortReverse" class="fa fa-caret-up"></span>
                        </a>
                    </td>
                    <td>
                        <a href="#" ng-click="sortType = 'name'; sortReverse = !sortReverse">
                            Name
                            <span ng-show="sortType == 'name' && !sortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="sortType == 'name' && sortReverse" class="fa fa-caret-up"></span>
                        </a>
                    </td>
                    <td>
                        <a href="#" ng-click="sortType = 'university'; sortReverse = !sortReverse">
                            University
                            <span ng-show="sortType == 'university' && !sortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="sortType == 'university' && sortReverse" class="fa fa-caret-up"></span>
                        </a>
                    </td>
                    <td>
                        <a href="#" ng-click="sortType = 'devMark'; sortReverse = !sortReverse">
                            Dev Assesment
                            <span ng-show="sortType == 'devMark' && !sortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="sortType == 'devMark' && sortReverse" class="fa fa-caret-up"></span>
                        </a>
                    </td>
                    <td>
                        <a href="#" ng-click="sortType = 'hrMark'; sortReverse = !sortReverse">
                            HR Assesment
                            <span ng-show="sortType == 'hrMark' && !sortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="sortType == 'hrMark' && sortReverse" class="fa fa-caret-up"></span>
                        </a>
                    </td>
                    <td>
                        <a href="#" ng-click="sortType = 'color'; sortReverse = !sortReverse">
                            Color
                            <span ng-show="sortType == 'color' && !sortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="sortType == 'color' && sortReverse" class="fa fa-caret-up"></span>
                        </a>
                    </td>
                    <td>
                        <a href="#" ng-click="sortType = 'isActive'; sortReverse = !sortReverse">
                            Active
                            <span ng-show="sortType == 'isActive' && !sortReverse" class="fa fa-caret-down"></span>
                            <span ng-show="sortType == 'isActive' && sortReverse" class="fa fa-caret-up"></span>
                        </a>
                    </td>
                </tr>
                </thead>

                <tbody>
                <tr ng-repeat="ch in students | orderBy:sortType:sortReverse | filter:searchFiltr">
                    <td><input type="checkbox" checklist-model="dataStudents.studId" checklist-value="ch.id"></td>
                    <td>{{ch.id}}</td>
                    <td><a href="/profile/{{ch.id}}" target="_blanks">{{ch.name}}</a> </td>
                    <td>{{ch.university}}</td>
                    <td>{{ch.devMark}}</td>
                    <td>{{ch.hrMark}}</td>
                    <td ng-style="{opacity:0.5,'background-color':'{{ch.color}}'}" title="
            {{ch.color == 'red' ? 'Reject' :
            ch.color == 'green' ? 'On course' :
            ch.color == 'blue' ? 'On job' : 'Thinking'}}"></td>
                    <td>{{ch.isActive == 1 ? "Active" : "Inactive"}}</td>

                </tr>
                </tbody>
                <tt>multipleSelect = {{students.length}}</tt>
            </table>


        </div>
    </main>
</div>


<script src="https://code.getmdl.io/1.1.3/material.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/js/admin-create-user.js"></script>
<script src="/resources/bootstrap/js/bootstrap.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular-route.js"></script>
<script src="../../resources/js/studentListAngular.js"></script>
<script src="http://vitalets.github.io/checklist-model/checklist-model.js"></script>
</body>

</html>
