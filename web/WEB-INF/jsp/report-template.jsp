<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 14.05.2016
  Time: 1:52
  To change this template use File | Settings | File Templates.
--%>
<!--Created by Alexander Haliy on 05.05.2016. !-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places"></script>
    <title>Report Template</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link href="/resources/css/scheduler-styles.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/bootstrap.timepicker/0.2.6/css/bootstrap-timepicker.min.css">
    <link rel="stylesheet" href="/resources/css/notification/angular-ui-notification.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script type="text/javascript" src="/resources/js/bootstrap-timepicker.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <link href="/resources/css/app.css" rel="stylesheet"/>
    <link href="/resources/css/mdb.min.css" rel="stylesheet"/>
    <link href="/resources/css/roboto-font/roboto.css" rel="stylesheet"/>

</head>
<body ng-app="reporter">
<div class="generic-container" ng-controller="ReportController as rc">
    <div class="row">
        <div class="col-md-12">
            <div class="card hoverable">
                <div class="view overlay hm-white-slight z-depth-1">
                    <div class="mask waves-effect"></div>
                </div>
                <div class="card-content">
                    <h5 align="center"><spring:message code="locale.reportTemp"/></h5>
                    <form ng-submit="rc.submit()" name="myForm" class="form-horizontal">
                        <input type="hidden" ng-model="rc.report.id"/>
                        <div class="row">
                            <div class="form-group col-md-12">
                                <label class="col-md-2 control-lable" for="file"></label>
                                <div class="col-md-7">
                                    <input type="text" ng-model="rc.report.name" class="md-textarea"
                                           placeholder="<spring:message code="locale.enterReportName"/>" required ng-minlength="3" name="rname"/>
                                    <div class="has-error" ng-show="myForm.$dirty">
                                        <span ng-show="myForm.rname.$error.required"><spring:message code="locale.requiredField"/></span>
                                        <span ng-show="myForm.rname.$error.minlength"><spring:message code="locale.minLength"/> 3</span>
                                        <span ng-show="myForm.rname.$invalid"><spring:message code="locale.invalidField"/></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-md-12">
                                <div class="md-form">
                                    <label class="col-md-2 control-lable" for="file"></label>
                                    <div class="col-md-7">
                                        <textarea type="text"
                                                  class="materialize-textarea" ng-model="rc.report.query"
                                                  placeholder="<spring:message code="locale.enterQuery"/>" name="rquery"
                                                  required>
                                        </textarea>
                                        <div class="has-error" ng-show="myForm.$dirty">
                                            <span ng-show="myForm.rquery.$error.required"><spring:message code="locale.requiredField"/></span>
                                            <span ng-show="myForm.rquery.$invalid"><spring:message code="locale.invalidField"/></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-actions floatRight">
                                <input type="submit" value="{{!rc.report.id ? '<spring:message code="locale.add"/>' : '<spring:message code="locale.update"/>'}}"
                                       class="btn btn-primary btn-sm"
                                       ng-disabled="myForm.$invalid">
                                <button type="button" ng-click="rc.reset()" class="btn btn-warning btn-sm"><spring:message code="locale.reset"/>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <%-- Report View--%>
            <div class="card hoverable">
                <div class="card-content">
                    <div class="panel panel-default">
                        <h5 align="center"><spring:message code="locale.reportTemp"/></h5>
                        <div class="table-responsive tablecontainer">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <%--                    <th><img src="/resources/images/checkbox.png" width="15" height="15"></th>--%>
                                    <th>#</th>
                                    <th><spring:message code="locale.name"/></th>
                                    <th><spring:message code="locale.query"/></th>
                                    <th width="20%"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr ng-hide="rc.reportsTemplateTable" ng-repeat="r in rc.reports">
                                    <%--                    <td><input
                                                                type="checkbox" &lt;%&ndash;data-ng-model="$parent.mailIdUser" ng-value="{{r.id}}"&ndash;%&gt;>
                                                        </td>--%>
                                    <td class="vert-align" ng-init="index=$index + 1">{{index}}</td>
                                    <td class="vert-align">{{r.name}}</td>
                                    <td class="vert-align">{{r.query}}</td>
                                    <td class="vert-align">
                                        <a href="" ng-click="rc.edit(r.id)"><img src="/resources/images/edit.png"
                                                                                 alt="Edit"
                                                                                 width="50" height="50"
                                                                                 title="Edit"/></a>
                                        <a href="/report/view?{{r.id}}" target="_blank"><img
                                                src="/resources/images/view.png"
                                                alt="View" width="50" height="50"
                                                title="View"/></a>
                                        <a href="/reports/download/{{r.id}}"><img src="/resources/images/excel.png"
                                                                                  alt="Excel"
                                                                                  width="50" height="50" title="Excel"/></a>
                                        <a href="" ng-click="rc.remove(r.id)"><img src="/resources/images/delete.png"
                                                                                   alt="Delete" width="50" height="50"
                                                                                   title="Delete"/></a>
                                    </td>
                                </tr>
                                <tr ng-hide="rc.emptyTemplateTable == true">
                                    <td colspan="4"  class="vert-align text-center"><spring:message code="locale.emptyTable"/></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
<script src="/resources/js/reportController.js"></script>
<script src="/resources/js/notification/angular-ui-notification.min.js"></script>
</body>
</html>