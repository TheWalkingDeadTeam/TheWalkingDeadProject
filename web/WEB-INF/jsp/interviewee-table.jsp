<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: creed
  Date: 24.05.16
  Time: 0:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en" ng-app="userView">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="A front-end template that helps you build fast, modern mobile web apps.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>Interviewee</title>

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
    <link rel="stylesheet" href="/resources/css/checkbox.css">

<style>
    p {
        line-height: 15px;
    }
</style>
</head>
<body>


<main ng-controller="IntervieweeCtrl as data"
      class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">

    <jsp:include page="header.jsp"/>

    <main class="mdl-color--grey-100">
        <div>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable">
                <label class="mdl-button mdl-js-button mdl-button--icon" for="search">
                    <i class="material-icons"><spring:message code="locale.search"/></i>
                </label>
                <div class="mdl-textfield__expandable-holder">
                    <form ng-submit = "searchFiltr(field)"><input  class="mdl-textfield__input" type="text" id="search" name="field" ng-model="field"></form>
                    <label class="mdl-textfield__label" for="search"><spring:message code="locale.enterQuery"/></label>
                </div>
            </div>
            <div class="cssload-thecube">
                <div class="cssload-cube cssload-c1"></div>
                <div class="cssload-cube cssload-c2"></div>
                <div class="cssload-cube cssload-c4"></div>
                <div class="cssload-cube cssload-c3"></div>
            </div>
        </div>
        <table  id="tableUsers" class="table table-striped table-hover table-bordered">
            <thead>
            <tr>
                <td>
                    <a ng-click="sortType('system_user_id',sortReverse); sortReverse = !sortReverse">
                        Id
                    </a>
                </td>
                <td>
                    <a ng-click="sortType('name',sortReverse); sortReverse = !sortReverse">
                        <spring:message code="locale.name"/>
                    </a>
                </td>
                <td>
                    <a ng-click="sortType('surname',sortReverse); sortReverse = !sortReverse">
                        <spring:message code="locale.surname"/>
                    </a>
                </td>
                <td>
                    <a ng-click="sortType('special_mark',sortReverse); sortReverse = !sortReverse">
                        <spring:message code="locale.specmark"/>
                    </a>
                </td>
                <td>
                    <a ng-click="sortType('dev_score',sortReverse); sortReverse = !sortReverse">
                        Dev <spring:message code="locale.score"/>
                    </a>
                </td>
                <td>
                    <a ng-click="sortType('hr_score',sortReverse); sortReverse = !sortReverse">
                        Hr <spring:message code="locale.score"/>
                    </a>
                </td>
                <td>
                    <a ng-click="sortType('special_mark',sortReverse); sortReverse = !sortReverse">
                        <spring:message code="locale.color"/>
                    </a>
                </td>
            </tr>
            </thead>
            <tbody>
            <tr ng-show="data.users.length <= 0">
                <td colspan="5" style="text-align:center;"><spring:message code="locale.plsWait"/>...</td>
            </tr>
            <tr dir-paginate="interviewee in data.users|itemsPerPage:data.itemsPerPage" total-items="data.total_count">
                <td><a href="/interviewer/feedback?{{interviewee.id}}" target="_blanks">{{interviewee.id}}</a></td>
                <td>{{interviewee.name}}</td>
                <td>{{interviewee.surname}}</td>
                <td>{{interviewee.special_mark}}</td>
                <td>{{interviewee.dev_score}}</td>
                <td>{{interviewee.hr_score}}</td>
                <td ng-style="{opacity:0.5,'background-color':'{{interviewee.color == '1' ? 'blue' :
                                interviewee.color == '2' ? 'green' :
                                interviewee.color == '4' ? 'yellow' : 'red'}}'}" title="
                              {{interviewee.color == '1' ? 'blue' :
                                interviewee.color == '2' ? 'green' :
                                interviewee.color == '4' ? 'yellow' : 'red'}}"></td>
            </tr>
            </tbody>
        </table>
        <div id="pagination">
            <dir-pagination-controls
                    max-size="8"
                    direction-links="true"
                    boundary-links="true"
                    on-page-change="data.setPageno(newPageNumber)">
            </dir-pagination-controls></div>

    </main>
    <jsp:include page="footer.jsp"/>
</main>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="https://code.getmdl.io/1.1.3/material.min.js"></script>
<script src="/resources/js/admin-create-user.js"></script>
<script src="/resources/bootstrap/js/bootstrap.js"></script>
<script src="/resources/js/checkboxScript.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular-route.js"></script>
<script src="/resources/js/interviewee-list.js"></script>
<script src="/resources/js/dirPagination.js"></script>
<script src="http://vitalets.github.io/checklist-model/checklist-model.js"></script>

</body>


</html>