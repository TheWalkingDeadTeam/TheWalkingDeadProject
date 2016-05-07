<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places"></script>

    <title>Scheduler</title>
    <style>
        .minutes.ng-valid {
            background-color: #d7eec3;
        }

        .minutes.ng-dirty.ng-invalid-required {
            background-color: #ffd9cc;
        }

        .minutes.ng-dirty.ng-invalid-minlength {
            background-color: yellow;
        }

        .location.ng-valid {
            background-color: #d7eec3;
        }

        .location.ng-dirty.ng-invalid-required {
            background-color: #ffd9cc;
        }

        .location.ng-dirty.ng-invalid-email {
            background-color: yellow;
        }
        .contact.ng-valid {
            background-color: #d7eec3;
        }

        .contact.ng-dirty.ng-invalid-required {
            background-color: #ffd9cc;
        }

        .contact.ng-dirty.ng-invalid-email {
            background-color: yellow;
        }
        .courseType.ng-valid {
            background-color: #d7eec3;
        }

        .courseType.ng-dirty.ng-invalid-required {
            background-color: #ffd9cc;
        }

        .courseType.ng-dirty.ng-invalid-minlength {
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
                  data-ng-controller="MailController as ctrl" autocomplete="on">

                <%-- Interview time--%>
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="file">Minutes for student</label>
                        <div class="col-md-7">
                            <input type="number" data-ng-model="minutes" name="minutes"
                                   class="minutes form-control input-sm" placeholder="Enter minutes interval" min="0"
                                   max="60" required/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.minutes.$error.required">This is a required field</span>
                            </div>
                        </div>
                    </div>
                </div>

                <%--Contact Info--%>
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="file">Contact information</label>
                        <div class="col-md-7">
                            <input type="text" data-ng-model="contact" name="contact"
                                   class="contact form-control input-sm" placeholder="Enter contact information"
                                   ng-minlength="10" ng-maxlength="12"  required/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.contact.$error.required">This is a required field </span>
                                <span ng-show="myForm.contact.$error.minlength">Telephone should be at least 10 digits </span>
                                <span ng-show="myForm.contact.$error.maxlength">Telephone should be at least 12 digits </span>
                            </div>
                        </div>
                    </div>
                </div>

                <%--Course Type--%>
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="file">Course Type</label>
                        <div class="col-md-7">
                            <input type="text" data-ng-model="courseType" name="course"
                                   class="courseType form-control input-sm" placeholder="Enter course type information"
                                   ng-minlength="4"
                                   required/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.course.$error.required">This is a required field</span>
                                <span ng-show="myForm.course.$error.minlength">At least course number should be 4 symbols</span>
                            </div>
                        </div>
                    </div>
                </div>

                <%--Place interview--%>
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="file">Place</label>
                        <div class="col-md-7">
                            <input id="searchTextField" name="place" type="text" data-ng-model="location" size="50"
                                   class="location form-control input-sm"
                                   placeholder="Enter interview place" ng-minlength="3" required/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.place.$error.required">This is a required field</span>
                                <span ng-show="myForm.place.$error.minlength">Chars only allowed</span>
                            </div>
                        </div>
                    </div>
                </div>
                <%--Checkbox with mails here--%>
                <div class="panel panel-default" ?>
                    <!-- Default panel contents -->

                    <div class="panel-heading"><span class="lead">Student Mail Template</span></div>
                    <div class="tablecontainer">
                        <table class="table table-hover" class="ng-cloak">
                            <thead>
                            <tr>
                                <th><img src="/resources/images/checkbox.png" width="15" height="15"></th>
                                <th>Topic</th>
                                <th>Body</th>
                                <th width="20%"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-repeat="m in ctrl.mails">
                                <td><input type="radio" data-ng-model="$parent.mailIdUser" ng-value="{{m.id}}"></td>
                                <td><span ng-bind="m.bodyTemplate"></span></td>
                                <td><span ng-bind="m.headTemplate"></span></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <%--Personal Mail Template--%>
                    <div class="panel-heading"><span class="lead">Staff Mail Template</span></div>
                    <div class="tablecontainer">
                        <table class="table table-hover" class="ng-cloak">
                            <thead>
                            <tr>
                                <th><img src="/resources/images/checkbox.png" width="15" height="15"></th>
                                <th>Topic</th>
                                <th>Body</th>
                                <th width="20%"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-repeat="m in ctrl.mails">
                                <td><input type="radio" data-ng-model="$parent.mailIdStaff" ng-value="{{m.id}}"></td>
                                <td><span ng-bind="m.bodyTemplate"></span></td>
                                <td><span ng-bind="m.headTemplate"></span></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <%--Personal Mail Template--%>
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
<script>
    function initialize() {
        var input = document.getElementById('searchTextField');
        var autocomplete = new google.maps.places.Autocomplete(input);
    }
    google.maps.event.addDomListener(window, 'load', initialize);
</script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<script src="<c:url value='/resources/js/mail_controller.js' />"></script>
</body>
</html>