<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Alexander
  Date: 29.04.2016
  Time: 22:26
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
    <title>Create user</title>
    <link rel="icon" type="image/png" sizes="32x32" href="/resources/images/ico.png"/>

    <!-- Add to homescreen for Chrome on Android -->
    <meta name="mobile-web-app-capable" content="yes">

    <!-- Add to homescreen for Safari on iOS -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="Material Design Lite">
    <link rel="apple-touch-icon-precomposed" href="images/ios-desktop.png">
    <link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.css"/>


    <!-- Tile icon for Win8 (144x144 + tile color) -->
    <meta name="msapplication-TileImage" content="images/touch/ms-touch-icon-144x144-precomposed.png">
    <meta name="msapplication-TileColor" content="#3372DF">


    <!-- SEO: If your mobile URL is different from the desktop URL, add a canonical link to the desktop page https://developers.google.com/webmasters/smartphone-sites/feature-phones -->
    <!--
    <link rel="canonical" href="http://www.example.com/">
    -->

    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.cyan-light_blue.min.css">
    <link rel="stylesheet" href="/resources/css/styles.css">
    <link rel="stylesheet" href="/resources/css/notification/pnotify.custom.min.css">


</head>
<body>
<div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">
    <jsp:include page="admin-header.jsp"/>

    <main class="mdl-layout__content mdl-color--grey-100">

        <div class="container">
            <div class="reg registration">
                <div class="col-lg-6 col-md-8 col-sm-12 col-xs-12">
                    <div id="messageRegistration"></div>
                    <div>
                        <div class="row container-fluid reg-head">
                            <div style="margin-left: 30px;">
                                <h4 class="form-signin-heading"><spring:message code="locale.createUser"/></h4>
                            </div>
                        </div>
                        <form>
                            <div id="regform" class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
                                <input id="name" style="margin-bottom: 3px;" name="name" class="form-control"
                                       placeholder="<spring:message code="locale.name"/>" type="text" value="">
                                <div class="correct-name"></div>
                                <input id="surname" style="margin-bottom: 3px;" name="surname" class="form-control"
                                       placeholder="<spring:message code="locale.surname"/>" type="text"
                                       value="">
                                <div class="correct-surname"></div>
                                <input id="email" style="margin-bottom: 3px;" name="email" class="form-control"
                                       placeholder="<spring:message code="locale.email"/>" type="text"
                                       value="">
                                <div class="correct-email"></div>
                                <input id="password" style="margin-bottom: 3px;" name="password"
                                       class="form-control login-field  login-field-password" placeholder="<spring:message code="locale.password"/>"
                                       type="password"
                                       value="">
                                <div class="correct-password"></div>
                            </div>
                            <div id="roleAdmin" class="col-lg-4 col-md-8 col-sm-12 col-xs-12">
                                <label><spring:message code="locale.roles"/>: </label>
                                <label class="checkbox">
                                    <input type="checkbox" id="checkboxAdmin" value="ROLE_ADMIN"> Admin
                                </label>
                                <select id="role" class="form-control">
                                    <option value="" selected>-</option>
                                    <option value="ROLE_HR">HR</option>
                                    <option value="ROLE_DEV">DEV</option>
                                    <option value="ROLE_BA">BA</option>
                                </select>
                            </div>
                            <div class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
                                <button id="buttonRegistration" style="border-radius: 4px;    margin-top: 4px ;"
                                        class="btn btn-lg btn-primary btn-block mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">
                                    <spring:message code="locale.register"/>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <%--<a href="/logout" target="_blank" id="view-source"
               class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">Exit</a>--%>
        </div>
    </main>

</div>


</body>
<script src="https://code.getmdl.io/1.1.3/material.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/bootstrap/js/bootstrap.js"></script>
<script src="/resources/js/hideShowPassword.min.js"></script>
<script src="/resources/js/admin-create-user.js"></script>
<script src="/resources/js/createNewUserValid.js"></script>
<script src="/resources/js/adminmenu.js"></script>
<script src="/resources/js/notification/pnotify.custom.min.js"></script>
<script>
    $('#password').hideShowPassword(false, true);
</script>
</html>
