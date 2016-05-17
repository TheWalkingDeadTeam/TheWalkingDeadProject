<%--
  Created by IntelliJ IDEA.
  User: Alexander
  Date: 15.05.2016
  Time: 17:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Mail Admin</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link href="<c:url value='/resources/css/app.css' />" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/bootstrap.timepicker/0.2.6/css/bootstrap-timepicker.min.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="/resources/css/mdb.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link href="<c:url value='/resources/css/app.css' />" rel="stylesheet">
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-md-8">
            <div class="card hoverable">
                <div class="view overlay hm-white-slight z-depth-1">
                    <div class="mask waves-effect"></div>
                </div>
                <div class="card-content">

                    <form ng-submit="mail()" name="myForm" class="form-horizontal"
                          autocomplete="on">
                        <div class="row">
                            <%--<div class="form-actions floatRight">--%>
                                <%--<a href="#FoOpen" class="btn-floating btn-small waves-effect waves-light blue"--%>
                                   <%--data-toggle="collapse"><i class="material-icons">add_to_photos</i></a>--%>
                            <%--</div>--%>
                            <div class="form-group col-md-12">
                                <label class="col-md-2 control-lable"></label>
                                <div class="col-md-8">
                                    <input type="text" data-ng-model="mailHead" name="mailHead"
                                           class="contact form-control input-sm" placeholder="Enter Topic"
                                           ng-minlength="3" required/>
                                    <div class="has-error" ng-show="myForm.$dirty">
                                        <span ng-show="myForm.mailHead.$error.required">This is a required field </span>
                                        <span ng-show="myForm.mailHead.$error.minlength">Topic should be at least 3 symbols</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-md-12">
                                <label class="col-md-2 control-lable"></label>
                                <div class="col-md-8">
                                    <textarea type="text" data-ng-model="mailBody" name="mailBody"
                                              class="materialize-textarea" placeholder="Enter Body"
                                              ng-minlength="3" required> </textarea>
                                    <div class="has-error" ng-show="myForm.$dirty">
                                        <span ng-show="myForm.mailBody.$error.required">This is a required field </span>
                                        <span ng-show="myForm.mailBody.$error.minlength">Body should be at least 3 symbols</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-actions floatRight">
                                <input type="submit" id="mail" value="Send"
                                       class="btn btn-default waves-effect waves-light">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="js/mdb.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<script src="/resources/js/studentListAngular.js"></script>
</body>
</html>