<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    <title>Students</title>

    <link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.css"/>


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

            <button ng-click="rejectStud()"
                    class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">
                <spring:message code="locale.reject"/>
            </button>
            <button ng-click="unrejectStud()"
                    class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">
                <spring:message code="locale.accept"/>
            </button>

            <a href="#FooOne"
               class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white"
               data-toggle="collapse"><spring:message code="locale.mail"/></a>

            <a href="#FooTwo"
               class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white"
               data-toggle="collapse"><spring:message code="locale.mailWithTemplate"/></a>

            <a href="#FooThree"
               class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white"
               data-toggle="collapse"><spring:message code="locale.settings"/></a>


            <div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable">
                <label class="mdl-button mdl-js-button mdl-button--icon" for="search" style="bottom: 30px;">
                    <i class="material-icons">search</i>
                </label>
                <div class="mdl-textfield__expandable-holder" style="padding: 0px">
                    <form ng-submit="searchFiltr(field)">
                        <input class="mdl-textfield__input" type="text" id="search" style="border-bottom: 1px solid #9e9e9e;
                        box-shadow: none"
                                                                name="field" ng-model="field"></form>
                    <%--<label class="mdl-textfield__label" for="search" style="border-bottom: none;--%>
                        <%--box-shadow: none"--%>
                    <%--><spring:message code="locale.enterQuery"/>...</label>--%>
                </div>
            </div>
            <div>

            </div>
            <div id="FooOne" class="collapse">
                <jsp:include page="custom-mail.jsp"/>
            </div>
            <div class="cssload-thecube">
                <div class="cssload-cube cssload-c1"></div>
                <div class="cssload-cube cssload-c2"></div>
                <div class="cssload-cube cssload-c4"></div>
                <div class="cssload-cube cssload-c3"></div>
            </div>


            <div id="FooTwo" class="collapse">
                <jsp:include page="custom-mail-template-interviewers.jsp"/>
            </div>

            <div id="FooThree" class="collapse">
                <%--<jsp:include page="custom-mail.jsp"/>--%>
                <div class="container">
                    <div class="row">
                        <div class="col-md-8">
                            <div class="card hoverable">
                                <div class="view overlay hm-white-slight z-depth-1">
                                    <div class="mask waves-effect"></div>
                                </div>

                                <div class="card-content">
                                    <div class="panel panel-default">
                                        <form ng-submit="setSize(count)" id="settingsForm">
                                            <select name="selectUsersCount" ng-model="count">
                                                <option disabled value=""><spring:message code="locale.chooseCount"/></option>
                                                <option selected value="10">10</option>
                                                <option value="25">25</option>
                                                <option value="50">50</option>
                                            </select>
                                            <div>
                                                <div ng-repeat="head in data.users.header">
                                                    <label><input type="checkbox" checklist-model="headerStud.head"
                                                           checklist-value="head">
                                                    <p>{{head.name}}</p>
                                                    </label>
                                                </div>
                                            </div>

                                            <div class="form-actions floatRight">
                                                <input type="submit" id="mail" value="<spring:message code="locale.save"/>"
                                                       class="btn btn-default waves-effect waves-light">
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <div class="table-responsive">
        <table id="tableUsers" class="table table-striped table-hover table-bordered">
            <thead>
            <tr>
                <td>
                    <input type="checkbox" ng-model="selectedAll" ng-click="checkAll()">
                </td>
                <td>
                    <a ng-click="sortReverse = !sortReverse; sortType(0,sortReverse)">
                        <spring:message code="locale.fullName"/>
                    </a>
                </td>
                <td ng-repeat="head in headerStud.head">
                    <a ng-click="sortReverse = !sortReverse; sortType(head.id,sortReverse)">
                        {{head.name}}
                    </a>
                </td>
                <td>
                    <a ng-click="sortReverse = !sortReverse; sortType('2147483647',sortReverse);">
                        <spring:message code="locale.status"/>
                    </a>
                </td>
            </tr>
            </thead>
            <tbody>
            <tr ng-show="data.users.rows.length <= 0">
                <td colspan="5" style="text-align:center;"><spring:message code="locale.emptyTable"/></td>
            </tr>
            <tr dir-paginate="user in data.users.rows|itemsPerPage:data.itemsPerPage" total-items="data.total_count">
                <td><input type="checkbox" checklist-model="dataStudents.studId" checklist-value="user.userId"></td>
                <td><a href="/profile?{{user.userId}}" target="_blanks">{{user.name}}</a></td>
                <td ng-repeat="head in headerStud.head">
                    {{user.fields[head.id]}}
                </td>
                <td ng-style="{opacity:0.8,'background-color':'{{user.rejected ? 'red' : 'green'}}'}"
                    title="{{user.rejected}}">
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
<script src="/resources/js/admin-create-user.js"></script>
<script src="/resources/bootstrap/js/bootstrap.js"></script>
<script src="/resources/js/checkboxScript.js"></script>
<script src="/resources/js/student-list.js"></script>
<script src="/resources/js/dirPagination.js"></script>
<script src="http://vitalets.github.io/checklist-model/checklist-model.js"></script>
<script src="/resources/js/adminmenu.js"></script>
</body>


</html>
