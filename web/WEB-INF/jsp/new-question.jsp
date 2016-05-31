<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Neltarion
  Date: 12.05.2016
  Time: 20:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en" ng-app="sendFormModule">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="A front-end template that helps you build fast, modern mobile web apps.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>Add new question</title>

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
    <link rel="stylesheet" href="/resources/css/addquestion.css">

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
<div ng-controller="sendFr as sef"
     class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">
    <jsp:include page="admin-header.jsp"/>


    <main class="mdl-layout__content mdl-color--grey-100">

        <div>
            <div style="margin-left: 20px;"><h4><spring:message code="locale.plsEnterNewQuest"/></h4></div>
            <div style="margin: 20px;">
                <form role="form" ng-submit="sef.save()">
                    <div class="form-group">
                        <label for="fieldName"><spring:message code="locale.fieldName"/></label>
                        <input type="text" ng-model="sef.newQuestion.name" class="form-control" id="fieldName"
                               placeholder="Enter field name" required>
                    </div>
                    <label for="fieldType"><spring:message code="locale.type"/></label>
                    <select id="fieldType" class="form-control" style="margin-bottom: 10px;" ng-change="change()"
                            ng-model="sef.newQuestion.fieldTypeID" required>
                        <option ng-selected="true" value="" disabled><spring:message code="locale.type"/></option>
                        <option value="1">Number</option>
                        <option value="2">Text</option>
                        <option value="3">Textarea</option>
                        <option value="4">Select</option>
                        <option value="5">Checkbox</option>
                        <option value="6">Radio</option>
                        <option value="7">Tel</option>
                        <%--<option value="8">Date</option>--%>
                    </select>

                    <div class="form-group" ng-show="sef.isShown">
                        <label for="fieldName">Options group name</label>
                        <input type="text" ng-model="sef.newQuestion.listTypeName" class="form-control newInputs"
                               id="optionGroupName"
                               placeholder="Enter options group name">
                    </div>

                    <div id="newOptions">
                        <div ng-repeat="item in sef.newQuestion.inputOptionsFields" ng-show="sef.isShown">
                            <input-field item='item' item-model="sef.newQuestion.inputOptionsFields"></input-field>
                        </div>
                    </div>
                    <div id="messageDiv"></div>
                    <button type="submit"
                            class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">
                        <spring:message code="locale.save"/>
                    </button>
                    <button ng-click="sef.add()" ng-show="sef.isShown" type="button"
                            class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">
                        New Item
                    </button>
                </form>
                <button ng-click="back()"
                        class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white"
                        style="margin-top: 10px;"><spring:message code="locale.back"/>
                </button>
            </div>
        </div>
    </main>
</div>


<script src="https://code.getmdl.io/1.1.3/material.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular.min.js"></script>
<script src="/resources/js/new-question.js"></script>
<script src="/resources/js/InputFieldComponent/InputFieldComponent.js"></script>
<script src="/resources/js/angular-drag-and-drop-lists.js"></script>
<script src="/resources/bootstrap/js/bootstrap.js"></script>
</body>

</html>
