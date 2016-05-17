<%--
  Created by IntelliJ IDEA.
  User: Alexander
  Date: 13.05.2016
  Time: 21:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html ng-app="plunker">

<head>
    <meta charset="utf-8" />
    <title>AngularJS Plunker</title>
    <script>document.write('<base href="' + document.location + '" />');</script>
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/ng-dialog/0.1.6/ng-dialog.min.css" />
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/ng-dialog/0.1.6/ng-dialog-theme-plain.min.css" />

    <script data-require="angular.js@1.2.x" src="https://code.angularjs.org/1.2.21/angular.js" data-semver="1.2.21"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/ng-dialog/0.1.6/ng-dialog.min.js"></script>
    <script src="/resources/js/temp_test.js"></script>
</head>

<body ng-controller="MainCtrl">
<button ng-click="openDialog($event)">Open Me!</button>
</body>

</html>