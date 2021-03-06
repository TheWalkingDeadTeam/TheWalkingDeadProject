<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Alexander
  Date: 24.05.2016
  Time: 20:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CES MAIL</title>
    <link rel="stylesheet" href="/resources/css/roboto-font/roboto.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/bootstrap.timepicker/0.2.6/css/bootstrap-timepicker.min.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="/resources/css/mdb.min.css">
    <link rel="stylesheet" href="/resources/css/app.css">
    <link rel="stylesheet" href="/resources/css/radio-butoon/build.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <style>
        .generic-container {
            /*width: 97%;*/
            /*margin-left: 6%;*/
            padding-right: 30px;
        }
    </style>

</head>
<body>
<div class="generic-container col-lg-12 col-md-12 col-sm-12 col-xs-12">
    <div class="row">
        <div>
            <div class="card hoverable">
                <div class="view overlay hm-white-slight z-depth-1">
                    <div class="mask waves-effect"></div>
                </div>


                <form ng-submit="templateSend()" name="myForm" class="form-horizontal" autocomplete="on">

                    <div class="card-content">
                        <div class="panel panel-default">
                            <h5 align="center"><spring:message code="locale.reject"/> <spring:message code="locale.mailTemp"/></h5>
                            <div class="table-responsive tablecontainer">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th><img src="/resources/images/checkbox.png" width="15" height="15"></th>
                                        <th><spring:message code="locale.topic"/></th>
                                        <th width="20%"></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr ng-repeat="m in ctrl.mails">
                                        <td class="vert-align">
                                            <div class="radio radio-success">
                                                <input type="radio" id="singleRadio1" data-ng-model="$parent.rejection"
                                                       ng-value={{m.id}} name="radioSingle1">
                                                <label></label>
                                            </div>
                                        </td>
                                        <td class="vert-align"><span ng-bind="m.headTemplate"></span></td>
                                        <td class="vert-align">
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>


                    <div class="card-content">
                        <div class="panel panel-default">
                            <h5 align="center"><spring:message code="locale.jobOffer"/> <spring:message code="locale.mailTemp"/></h5>
                            <div class="table-responsive tablecontainer">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th><img src="/resources/images/checkbox.png" width="15" height="15"></th>
                                        <th><spring:message code="locale.topic"/></th>
                                        <%--<th>Body</th>--%>
                                        <th width="20%"></th>

                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr ng-repeat="m in ctrl.mails">
                                        <td class="vert-align">
                                            <div class="radio radio-success">
                                                <input type="radio" id="singleRadio2" data-ng-model="$parent.work"
                                                       ng-value={{m.id}} name="radioSingle2">
                                                <label></label>
                                            </div>
                                        </td>
                                        <td class="vert-align"><span ng-bind="m.headTemplate"></span></td>
                                        <td class="vert-align">
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <div class="card-content">
                        <div class="panel panel-default">
                            <h5 align="center"><spring:message code="locale.takeOnCourses"/> <spring:message code="locale.mailTemp"/></h5>
                            <div class="table-responsive tablecontainer">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th><img src="/resources/images/checkbox.png" width="15" height="15"></th>
                                        <th><spring:message code="locale.topic"/></th>
                                        <th width="20%"></th>

                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr ng-repeat="m in ctrl.mails">
                                        <td class="vert-align">
                                            <div class="radio radio-success">
                                                <input type="radio" id="singleRadio3" data-ng-model="$parent.course"
                                                       ng-value={{m.id}} name="radioSingle3">
                                                <label></label>
                                            </div>
                                        </td>
                                        <td class="vert-align"><span ng-bind="m.headTemplate"></span></td>
                                        <td class="vert-align">
                                    </tr>
                                    </tbody>
                                </table>
                            </div>

                            <button ng-click="ctrl.closeButton()"
                                    class="floatRight btn btn-default waves-effect waves-light"
                                    style="margin-top: 3%;margin-bottom: 4%">
                                <spring:message code="locale.close"/>
                            </button>

                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


</div>
</div>

<script type="text/javascript">
    function changeState(el) {
        if (el.readOnly) el.checked = el.readOnly = false;
        else if (!el.checked) el.readOnly = el.indeterminate = true;
    }
</script>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
</body>
</html>
