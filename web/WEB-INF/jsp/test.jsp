<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>AngularJS $http Example</title>
    <style>
        .mailhead.ng-valid {
            background-color: lightgreen;
        }
        .mailhead.ng-dirty.ng-invalid-required {
            background-color: red;
        }
        .mailhead.ng-dirty.ng-invalid-minlength {
            background-color: yellow;
        }
        .mailbody.ng-valid {
            background-color: lightgreen;
        }
        .mailbody.ng-dirty.ng-invalid-required {
            background-color: red;
        }
        .mailbody.ng-dirty.ng-invalid-email {
            background-color: yellow;
        }
    </style>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link href="<c:url value='/resources/css/app.css' />" rel="stylesheet"></link>
</head>
<body ng-app="myApp" class="ng-cloak">
<div class="generic-container" ng-controller="MailController as ctrl">
    <div class="panel panel-default">
        <div class="panel-heading"><span class="lead">Mail Template Form </span></div>
        <div class="formcontainer">
            <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
                <input type="hidden" ng-model="ctrl.mail.id" />
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="file">Mail Header</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="ctrl.mail.headTemplate" name="mhead" class="mailhead form-control input-sm" placeholder="Enter mail2 header" required ng-minlength="3"/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.mhead.$error.required">This is a required field</span>
                                <span ng-show="myForm.mhead.$error.minlength">Minimum length required is 3</span>
                                <span ng-show="myForm.mhead.$invalid">This field is invalid </span>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="file">Mail Body</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="ctrl.mail.bodyTemplate" class="form-control input-sm" placeholder="Enter mail body"/>
                        </div>
                    </div>
                </div>



                <div class="row">
                    <div class="form-actions floatRight">
                        <input type="submit"  value="{{!ctrl.mail.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
                        <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of Mails </span></div>
        <div class="tablecontainer">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Head</th>
                    <th>Body</th>
                    <th width="20%"></th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="m in ctrl.mails">
                    <td><span ng-bind="m.id"></span></td>
                    <td><span ng-bind="m.bodyTemplate"></span></td>
                    <td><span ng-bind="m.headTemplate"></span></td>
                    <td>
                        <button type="button" ng-click="ctrl.edit(m.id)" class="btn btn-success custom-width">Edit</button>  <button type="button" ng-click="ctrl.remove(m.id)" class="btn btn-danger custom-width">Remove</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<script src="<c:url value='/resources/js/app.js' />"></script>
<script src="<c:url value='/resources/js/mail_service.js' />"></script>
<script src="<c:url value='/resources/js/mail_controller.js' />"></script>
</body>
</html>