<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!--Created by Alexander Haliy on 05.05.2016. !-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<html>
<head>
    <title>Scheduler</title>
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places"></script>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script type="text/javascript" src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/js/datapicker/bootstrap-datetimepicker.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/bootstrap.timepicker/0.2.6/css/bootstrap-timepicker.min.css"/>
    <link rel="stylesheet" href="/resources/css/mdb.min.css"/>
    <link rel="stylesheet" href="/resources/css/notification/angular-ui-notification.min.css"/>
    <link rel="stylesheet" href="/resources/css/app.css"/>
    <link rel="stylesheet" href="/resources/css/datepicker/bootstrap-datetimepicker.css"/>
    <link rel="stylesheet" href="/resources/css/roboto-font/roboto.css"/>
</head>
<body ng-app="mailer" class="ng-cloack">
<div class="generic-container" ng-controller="MailController as ctrl">
    <div class="panel panel-default">
        <h5 align="center"><spring:message code="locale.shedulerParam"/></h5>
        <div class="formcontainer">
            <form ng-submit="submit()" name="myForm" class="form-horizontal"
                  autocomplete="on">

                <%-- Interview Start Date --%>
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="file"><spring:message code="locale.startTime"/></label>
                        <div class="col-md-7">
                            <div class="input-append  date form_datetime">
                                <input class="input-sm" type="text" value="" data-ng-model="interviewTime"
                                       placeholder="<spring:message code="locale.provideInterviewTime"/>" name="nterviewTime" readonly>
                                <span class="add-on"><i class="icon-th"></i></span>
                            </div>
                        </div>
                    </div>
                </div>

                <%-- Interview contact info--%>
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="file"><spring:message code="locale.intContact"/></label>
                        <div class="col-md-7">
                            <input type="text" data-ng-model="contactStaff" name="contactStaff"
                                   class="contact form-control input-sm" placeholder="<spring:message code="locale.enterContactInfo"/>"
                                   ng-minlength="3" required/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.contactStaff.$error.required"><spring:message code="locale.requiredField"/> </span>
                                <span ng-show="myForm.contactStaff.$error.minlength"><spring:message code="locale.minLength"/> 3 </span>
                            </div>
                        </div>
                    </div>
                </div>
                <%--Student Contact Info--%>
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="file"><spring:message code="locale.studentContact"/></label>
                        <div class="col-md-7">
                            <input type="text"
                                   data-ng-model="contactStudent"
                                   name="contactStudent"
                                   class="studContact form-control input-sm"
                                   placeholder="<spring:message code="locale.enterContactInfo"/>"
                                   ng-minlength="3"
                                   required/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.contactStudent.$error.required"><spring:message code="locale.requiredField"/> </span>
                                <span ng-show="myForm.contactStudent.$error.minlength"><spring:message code="locale.minLength"/> 3</span>
                            </div>
                        </div>
                    </div>
                </div>
                <%--Course Type--%>
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="file"><spring:message code="locale.courseType"/></label>
                        <div class="col-md-7">
                            <input type="text" data-ng-model="courseType" name="course"
                                   class="courseType form-control input-sm"
                                   placeholder="<spring:message code="locale.enterCourseType"/>"
                                   ng-minlength="4"
                                   required/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.course.$error.required"><spring:message code="locale.requiredField"/></span>
                                <span ng-show="myForm.course.$error.minlength"><spring:message code="locale.minLength"/> 4 </span>
                            </div>
                        </div>
                    </div>
                </div>
                <%--Place interview--%>
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="file"><spring:message code="locale.location"/></label>
                        <div class="col-md-7">
                            <input id="searchTextField" name="place" type="text" data-ng-model="location" size="50"
                                   class="location form-control input-sm"
                                   placeholder="<spring:message code="locale.enterIntLocation"/>" ng-minlength="3" required/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.place.$error.required"><spring:message code="locale.requiredField"/></span>
                                <span ng-show="myForm.place.$error.minlength"><spring:message code="locale.minLength"/> 3</span>
                            </div>
                        </div>
                    </div>
                </div>
                <%--Student Mail View--%>

                <div class="panel panel-default">
                    <h5 align="center"><spring:message code="locale.studentMailTemp"/></h5>
                    <div class="table-responsive tablecontainer">
                        <table class="table" class="ng-cloak">
                            <thead>
                            <tr>
                                <th><img src="/resources/images/checkbox.png" width="15" height="15"></th>
                                <th><spring:message code="locale.topic"/></th>
                                <th><spring:message code="locale.body"/></th>
                                <th width="20%"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-hide="ctrl.mailsTemplateTable" ng-repeat="m in ctrl.mails">
                                <td class="vert-align"><input type="radio" data-ng-model="$parent.mailIdUser"
                                                              ng-value="{{m.id}}"></td>
                                <td class="vert-align"><span ng-bind="m.headTemplate"></span></td>
                                <td class="vert-align"><span ng-bind="m.bodyTemplate"></span></td>

                            </tr>
                            <tr ng-hide="ctrl.emptyTemplateTable == true">
                                <td colspan="4"  class="vert-align col-xs-12 text-center"><spring:message code="locale.emptyTable"/></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="panel panel-default">
                    <%--Staff Mail Template--%>
                    <h5 align="center"><spring:message code="locale.interviewerMailTemp"/></h5>
                    <div class="table-responsive tablecontainer">
                        <table class="table" class="ng-cloak">
                            <thead>
                            <tr>
                                <th><img src="/resources/images/checkbox.png" width="15" height="15"></th>
                                <th><spring:message code="locale.topic"/></th>
                                <th><spring:message code="locale.body"/></th>
                                <th width="20%"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-hide="ctrl.mailsTemplateTable" ng-repeat="m in ctrl.mails">
                                <td class="vert-align"><input type="radio" data-ng-model="$parent.mailIdStaff"
                                                              ng-value="{{m.id}}">
                                </td>
                                <td class="vert-align"><span ng-bind="m.headTemplate"></span></td>
                                <td class="vert-align"><span ng-bind="m.bodyTemplate"></span></td>
                            </tr>
                            <tr ng-hide="ctrl.emptyTemplateTable == true">
                                <td colspan="4"  class="vert-align col-xs-12 text-center"><spring:message code="locale.emptyTable"/></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

                <%--Buttons--%>
                <div class="row">
                    <div class="form-actions floatRight">
                        <input type="submit" id="submit" value="<spring:message code="locale.submit"/>" class="btn btn-primary btn-sm"
                               ng-disabled="myForm.$invalid">
                        <button type="reset" class="btn btn-warning btn-sm">
                            <spring:message code="locale.reset"/>
                        </button>
                        <a href="/admin/mail-template">
                            <button type="button" class="btn btn-success btn-sm"><spring:message code="locale.mailTemp"/>
                            </button>
                        </a>
                    </div>
                </div>
            </form>
        </div>
    </div>


</div>
<script src="/resources/js/google-api.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<script src="/resources/js/notification/angular-ui-notification.min.js"></script>
<script src="/resources/js/mailController.js"></script>
</body>
</html>