<%--
  Created by IntelliJ IDEA.
  User: IGOR
  Date: 16.05.2016
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="A front-end template that helps you build fast, modern mobile web apps.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>Settings</title>

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

</head>
<body>
<div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">
    <jsp:include page="admin-header.jsp"/>

    <main ng-app="myApp" ng-controller="FormController as ctrl" class="mdl-layout__content mdl-color--grey-100">

        <div class="container">
            <div class="reg registration">
                <div class="col-lg-6 col-md-8 col-sm-9 col-xs-9">
                    <div id="messageRegistration"></div>
                    <div>
                        <div class="row container-fluid reg-head">
                            <div id="sessionSettings">
                                <h4 class="form-signin-heading">Session settings</h4>
                            </div>
                        </div>
                        <div name="myForm" id="CESfields" ng-submit="ctrl.save()"
                             class="col-lg-11 col-md-8 col-sm-9 col-xs-9">
                            <div><span class="myTextInfo">Year</span><input type="number" name="year"
                                                                            id="1" class="form-control"
                                                                            ng-model="ctrl.ces.year"
                                                                            ng-readonly="current" required/></div>
                            <div class="correct-year"></div>
                            <div><span class="myTextInfo">Quota</span><input type="number" name="quota" id="quota"
                                                                             class="form-control"
                                                                             ng-model="ctrl.ces.quota"
                                                                             required/></div>
                            <div class="correct-quota"></div>
                            <div><span class="myTextInfo">Start registration date</span><input type="date"
                                                                                               name="startRegistrationDate"
                                                                                               class="form-control"
                                                                                               ng-model="ctrl.ces.startRegistrationDate"
                                                                                               ng-readonly="current"
                                                                                               placeholder="yyyy-MM-dd"
                                                                                               id="3" required></div>
                            <div class="correct-date"></div>
                            <div><span class="myTextInfo">End registration date</span><input type="date"
                                                                                             name="endRegistrationDate"
                                                                                             class="form-control"
                                                                                             ng-model="ctrl.ces.endRegistrationDate"
                                                                                             ng-readonly="current"
                                                                                             placeholder="yyyy-MM-dd"
                                                                                             id="4" required/></div>
                            <div class="correct-date"></div>
                            <div><span class="myTextInfo">Start interviewing date</span><input type="date"
                                                                                               name="startInterviewingDate"
                                                                                               class="form-control"
                                                                                               ng-model="ctrl.ces.startInterviewingDate"
                                                                                               placeholder="yyyy-MM-dd"
                                                                                               ng-readonly="interviewBegan"
                                                                                               id="5"/></div>
                            <div class="correct-date"></div>
                            <div><span class="myTextInfo">End interviewing date</span><input type="date"
                                                                                             name="endInterviewingDate"
                                                                                             class="form-control"
                                                                                             ng-model="ctrl.ces.endInterviewingDate"
                                                                                             placeholder="yyyy-MM-dd"
                                                                                             ng-readonly=true id="6"/>
                            </div>
                            <div class="correct-date"></div>
                            <div><span class="myTextInfo">Reminders </span><input type="number" name="reminders" id="7"
                                                                                  ng-model="ctrl.ces.reminders"
                                                                                  class="form-control"
                                                                                  ng-readonly="current" required/></div>
                            <div><span class="myTextInfo">Interview time for person </span><input type="number"
                                                                                                  name="interviewTimeForPerson"
                                                                                                  id="8"
                                                                                                  class="form-control"
                                                                                                  ng-model="ctrl.ces.interviewTimeForPerson"
                                                                                                  ng-readonly="interviewBegan"
                                                                                                  required/></div>
                            <div class="correct-int"></div>
                            <div>Interview time for day <input type="number" name="interviewTimeForDay" id="9"
                                                               class="form-control"
                                                               ng-model="ctrl.ces.interviewTimeForDay"
                                                               ng-readonly="interviewBegan" required/></div>
                            <div class="correct-int"></div>
                            <%--<div>Status: <input type="text" name="interviewTimeForDay" id="10"--%>
                                                               <%--class="form-control"--%>
                                                               <%--ng-model="ctrl.ces.status"--%>
                                                               <%--ng-readonly=true required/></div>--%>
                            <input type="submit" ng-click="ctrl.save()" value="Save" style="margin-top: 5px;"
                                   class="btn btn-lg btn-primary btn-block mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white formControllingButt">
                            <%--<button ng-click="backButton()" class="btn btn-lg btn-primary btn-block"> Back </button>--%>
                            <button ng-click="ctrl.closeButton()"
                                    class="btn btn-lg btn-primary btn-block mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white formControllingButt">
                                Close session
                            </button>
                            <div id="errorsDiv" style="margin-top: 10px;"></div>
                        </div>
                        </form>
                    </div>
                </div>
            </div>
            <%--<a href="/logout" target="_blank" id="view-source"--%>
            <%--class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">Exit</a>--%>
        </div>
    </main>

</div>
</body>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js"></script>

<script>
    $(function () {
        if (!Modernizr.inputtypes.date) {
            // If not native HTML5 support, fallback to jQuery datePicker
            $('input[type=date]').datepicker({
                        // Consistent format with the HTML5 picker
                        dateFormat: 'yy-mm-dd'
                    },
                    // Localization
                    $.datepicker.regional['it']
            );
        }
    });
</script>
<script src="/resources/js/ces.js"></script>
<script src="https://code.getmdl.io/1.1.3/material.min.js"></script>
<script src="/resources/bootstrap/js/bootstrap.js"></script>
</html>
