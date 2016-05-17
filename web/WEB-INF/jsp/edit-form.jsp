<%--
  Created by IntelliJ IDEA.
  User: Neltarion
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en" ng-app="editFormView">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="A front-end template that helps you build fast, modern mobile web apps.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>Material Design Lite</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/ng-sortable.min.css">

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
<div ng-controller="tableCtrl as tc"
     class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">
    <jsp:include page="admin-header.jsp"/>


    <main class="mdl-layout__content mdl-color--grey-100">

        <div style="margin: 20px;">
            <button style="margin-bottom: 15px;" ng-click="addQuestion()"
                    class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">
                Add question
            </button>
            <button style="margin-bottom: 15px;" ng-click="deleteQuestion()"
                    class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">
                Delete question
            </button>

            <button style="margin-bottom: 15px;" ng-click="savePosition()"
                    class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">
                Save position
            </button>

            <table class="table table-bordered table-striped" style="{margin-top: 200px}">

                <thead>
                <td>
                    <input type="checkbox" ng-model="selectedAll" ng-click="checkAll()">
                </td>
                <%--<td>Id</td>--%>
                <td>Type</td>
                <td>
                    Field name
                </td>
                </thead>

                <tbody as-sortable="sortableOptions" ng-model="fields">
                <tr ng-repeat="ch in fields" as-sortable-item>
                    <td as-sortable-item-handle><input type="checkbox" checklist-model="dataFields.fieldId"
                                                       checklist-value="ch.id"></td>
                    <%--<td as-sortable-item-handle>{{ch.id}}</td>--%>
                    <td as-sortable-item-handle>{{chooseType(ch.fieldTypeID)}}</td>
                    <td as-sortable-item-handle><a href="/admin/edit-form/appformfield?{{ch.listTypeID}}/{{ch.id}}"
                                                   target="_blanks">{{ch.name}}</a></td>
                </tr>
                </tbody>
            </table>
            <div id="errorsDiv"></div>
        </div>
    </main>
</div>
<script src="https://code.getmdl.io/1.1.3/material.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular.min.js"></script>
<script src="/resources/bootstrap/js/bootstrap.js"></script>
<script src="/resources/js/edit-form.js"></script>
<script src="/resources/js/ng-sortable.min.js"></script>
<script src="http://vitalets.github.io/checklist-model/checklist-model.js"></script>
</body>

</html>
