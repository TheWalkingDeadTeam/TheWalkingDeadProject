<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>AngularJS $http Example</title>
    <style>
        .minutes.ng-valid {
            background-color: lightgreen;
        }

        .minutes.ng-dirty.ng-invalid-required {
            background-color: #ff9777;
        }

        .minutes.ng-dirty.ng-invalid-minlength {
            background-color: yellow;
        }

        .location.ng-valid {
            background-color: lightgreen;
        }

        .location.ng-dirty.ng-invalid-required {
            background-color: #ff9777;
        }

        .location.ng-dirty.ng-invalid-email {
            background-color: yellow;
        }
    </style>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link href="<c:url value='/resources/css/app.css' />" rel="stylesheet"></link>
</head>
<body ng-app="mailer" class="ng-cloack">
<div class="generic-container">
    <div class="panel panel-default">
        <div class="panel-heading"><span class="lead">Scheduler paramaters </span></div>
        <div class="formcontainer">
            <form ng-submit="submit()" name="myForm" class="form-horizontal"
                  data-ng-controller="SchedulerController">

                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="file">Minutes for student</label>
                        <div class="col-md-7">
                            <input type="number" data-ng-model="minutes" name="minutes"
                                   class="minutes form-control input-sm" placeholder="Enter minutes interval" min="0"
                                   max="60" required/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.minutes.$error.required">This is a required field</span>
                                <span ng-show="myForm.minutes.$error.minlength">Minutes only allowed</span>
                                <span ng-show="myForm.minutes.$invalid">This field is invalid </span>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="file">Place</label>
                        <div class="col-md-7">
                            <input type="text" data-ng-model="location"  name="location" class="location form-control input-sm"
                                   placeholder="Enter interview place"  ng-pattern-restrict  ng-minlength="3" required/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.location.$error.required">This is a required field</span>
                                <span ng-show="myForm.location.$error.minlength">Chars only allowed</span>
                                <span ng-show="myForm.location.$invalid">This field is invalid </span>
                            </div>
                        </div>
                    </div>
                </div>



                <%--Checkbox with mails here--%>
                <div class="panel panel-default" ? >
                    <!-- Default panel contents -->
                    <div class="panel-heading"><span class="lead">List of Mails </span></div>
                    <div class="tablecontainer">
                        <table class="table table-hover" ng-controller="MailController as ctrl" class="ng-cloak">
                            <thead>
                            <tr>
                                <th> <img src="/resources/images/checkbox.png" width="15" height="15"> </th>
                                <th>Topic</th>
                                <th>Body</th>
                                <th width="20%"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-repeat="m in ctrl.mails">
                                <td><input type="radio"  data-ng-module="mailId" value="{{m.id}}"></td>
                                <%--<td><span ng-bind="m.id"></span></td>--%>
                                <td><span ng-bind="m.bodyTemplate"></span></td>
                                <td><span ng-bind="m.headTemplate"></span></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>


                <%--Checkbox with mails here--%>




                <div class="row">
                    <div class="form-actions floatRight">
                        <input type="submit" id="submit" value="Submit" class="btn btn-primary btn-sm"
                               ng-disabled="myForm.$invalid">
                        <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm"
                                ng-disabled="myForm.$pristine">Reset Form
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<script src="<c:url value='/resources/js/mail_controller.js' />"></script>
</body>
</html>