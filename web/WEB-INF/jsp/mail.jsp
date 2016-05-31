<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<html>
<head>
    <title>Mail Page</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="/resources/css/notification/angular-ui-notification.min.css">
    <link href="/resources/css/app.css" rel="stylesheet"/>
    <link href="/resources/css/mdb.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="/resources/css/roboto-font/roboto.css">

</head>
<body ng-app="mailer" class="ng-cloak">
<div class="generic-container" ng-controller="MailController as ctrl">
    <div class="row">
        <div class="col-md-12">
            <div class="card hoverable">
                <div class="view overlay hm-white-slight z-depth-1">
                    <div class="mask waves-effect"></div>
                </div>
                <div class="card-content">

                    <script>
                        $(function () {
                            new PNotify({
                                title: 'Before filling templates!',
                                text: '1)Place \'$\' before $name, $surname, $location, $contact, $coursetype, $place. and $googleMaps for scheduler.' +
                                'All this parameters will be substituted.' +
                                '2)$name, $surname for registration & CES close templates',
                                hide: false
                            });
                        });
                    </script>

                    <h5 align="center"> <spring:message code="locale.mailTemp"/></h5>
                    <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
                        <input type="hidden" ng-model="ctrl.mail.id"/>
                        <div class="row">
                            <div class="md-form">
                                <label class="col-md-2 control-lable" for="file"></label>
                                <div class="col-md-7">
                                    <input type="text" ng-model="ctrl.mail.headTemplate" name="mhead"
                                           class="md-textarea"
                                           placeholder="<spring:message code="locale.mailTopic"/>" required
                                           ng-minlength="3"/>
                                    <div class="has-error" ng-show="myForm.$dirty">
                                        <span ng-show="myForm.mhead.$error.required"><spring:message code="locale.requiredField"/></span>
                                        <span ng-show="myForm.mhead.$error.minlength"><spring:message code="locale.minLength"/> 3</span>
                                        <span ng-show="myForm.mhead.$invalid"><spring:message code="locale.invalidField"/></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="md-form">
                                <label class="col-md-2 control-lable" for="file"></label>
                                <div class="col-md-7">
                                                    <textarea rows="5" cols="5" type="text"
                                                              ng-model="ctrl.mail.bodyTemplate"
                                                              class="materialize-textarea"
                                                              placeholder="<spring:message code="locale.enterBody"/>">
                                                    </textarea>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-actions floatRight">
                                <input type="submit" value="{{!ctrl.mail.id ? '<spring:message code="locale.add"/>' : '<spring:message code="locale.update"/>'}}"
                                       class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
                                <button type="button" ng-click="ctrl.reset()"
                                        class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">
                                    <spring:message code="locale.reset"/>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <div class="card hoverable">
                <div class="card-content">
                    <div class="panel panel-default">
                        <h5 align="center"><spring:message code="locale.listOfMails"/></h5>

                        <div class="table-responsive tablecontainer">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th><spring:message code="locale.topic"/></th>
                                    <th><spring:message code="locale.body"/></th>
                                    <th width="20%"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr ng-hide="ctrl.mailsTemplateTable"  ng-repeat="m in ctrl.mails">
                                    <td class="vert-align"><span ng-bind="m.id"></span></td>
                                    <td class="vert-align"><span ng-bind="m.headTemplate"></span></td>
                                    <td class="vert-align"><span ng-bind="m.bodyTemplate"></span></td>
                                    <td>
                                        <button type="button" ng-click="ctrl.edit(m.id)"
                                                class="btn btn-success btn-sm"><spring:message code="locale.edit"/>
                                        </button>
                                        <button type="button" ng-click="ctrl.remove(m.id)"
                                                class="btn btn-danger btn-sm"> <spring:message code="locale.remove"/>
                                        </button>
                                    </td>
                                </tr>
                                <tr ng-hide="ctrl.emptyTemplateTable == true">
                                    <td colspan="4"  class="vert-align col-xs-12 text-center"> <spring:message code="locale.emptyTable"/></td>
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
<script src="/resources/js/notification/angular-ui-notification.min.js"></script>
<script src="/resources/js/mailController.js"></script>
</body>
</html>