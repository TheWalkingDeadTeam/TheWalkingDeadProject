<%--
  Created by IntelliJ IDEA.
  User: Neltarion
  Date: 12.05.2016
  Time: 20:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en" ng-app="sendForm">
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
            <div style="margin-left: 20px;"><h4>Please, enter new question properties</h4></div>
            <div style="margin: 20px;">
                <%--<form id="newQuestionForm">--%>

                    <%--<div class="group">--%>
                        <%--<input type="text" required>--%>
                        <%--<span class="highlight"></span>--%>
                        <%--<span class="bar"></span>--%>
                        <%--<label>Field name</label>--%>
                    <%--</div>--%>

                    <%--<div class="group">--%>
                        <%--<select type="select" required>--%>
                        <%--<option disabled>true\false</option>--%>
                        <%--<option value="true">True</option>--%>
                        <%--<option value="false">False</option>--%>
                        <%--</select>--%>
                        <%--<span class="highlight"></span>--%>
                        <%--<span class="bar"></span>--%>
                        <%--<label>Multiple choice</label>--%>
                    <%--</div>--%>

                    <%--<div class="group">--%>
                        <%--<input type="text" required>--%>
                        <%--<span class="highlight"></span>--%>
                        <%--<span class="bar"></span>--%>
                        <%--<label>Order number</label>--%>
                    <%--</div>--%>

                    <%--<div class="group">--%>
                        <%--<input type="text" required>--%>
                        <%--<span class="highlight"></span>--%>
                        <%--<span class="bar"></span>--%>
                        <%--<label>Field type</label>--%>
                    <%--</div>--%>
                    <%--<div>--%>
                        <%--<button type="submit" for="newQuestionForm">Save</button>--%>
                        <%--<button type="button" onclick="back()" >Back</button>--%>
                    <%--</div>--%>
                <%--</form>--%>
                    <form role="form" ng-submit="sef.save()">
                        <div class="form-group">
                            <label for="fieldName">Field name</label>
                            <input type="text" ng-model="sef.newQuestion.name" class="form-control" id="fieldName" placeholder="Enter field name" required>
                        </div>
                        <label for="fieldType">Field type</label>
                        <select id="fieldType" class="form-control" ng-model="sef.newQuestion.fieldTypeID" required>
                            <option ng-selected="true" value="" disabled>Type</option>
                            <option value="1">Number</option>
                            <option value="2">Text</option>
                            <option value="3">Textarea</option>
                            <option value="4">Select</option>
                            <option value="5">Checkbox</option>
                            <option value="6">Radio</option>
                            <option value="7">Tel</option>
                            <option value="8">Date</option>
                        </select>
                        <label for="multiple">Multiple choice</label>
                        <select id="multiple" class="form-control" ng-model="sef.newQuestion.multipleChoice" required>
                            <option ng-selected="true" value="" disabled>True/False</option>
                            <option value="true">True</option>
                            <option value="false">False</option>
                        </select>
                        <div class="form-group">
                            <label for="orderNum">Order number</label>
                            <input type="number" min="1" max="50" class="form-control" ng-model="sef.newQuestion.orderNum" id="orderNum" placeholder="Enter order number" required>
                        </div>
                        <div class="form-group">
                            <label for="listType">ListTypeId</label>
                            <input type="number" min="1" max="50" class="form-control" ng-model="sef.newQuestion.listTypeID" id="listType" placeholder="Enter listtypeid">
                        </div>
                        <div id="messageDiv"></div>
                        <button type="submit">Save</button>
                    </form>
                    <button onclick="back()" style="margin-top: 10px;">Back</button>
            </div>
        </div>
    </main>
</div>


<script src="https://code.getmdl.io/1.1.3/material.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular.min.js"></script>
<script src="/resources/js/new-question.js"></script>
<script>function back() {
    window.location.href = "/admin/edit-form";
}</script>
<script src="/resources/bootstrap/js/bootstrap.js"></script>
</body>

</html>
