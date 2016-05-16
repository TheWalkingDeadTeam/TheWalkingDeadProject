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
    <title>Material Design Lite</title>

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

    <main class="mdl-layout__content mdl-color--grey-100">

        <div class="container">
            <div class="reg registration">
                <div class="col-lg-6 col-md-8 col-sm-9 col-xs-9">
                    <div id="messageRegistration"></div>
                    <div>
                        <div class="row container-fluid reg-head">
                            <div>
                                <h2 class="form-signin-heading">Interviewer Registration</h2>
                            </div>
                        </div>
                        <div id="regform" class="col-lg-11 col-md-8 col-sm-9 col-xs-9">
                            <input id="name" name="name" class="form-control" placeholder="Name" type="text" value="">
                            <div class="correct-"></div>
                            <input id="surname" name="surname" class="form-control" placeholder="Surname" type="text"
                                   value="">
                            <div class="correct-surname"></div>
                            <input id="email" name="email" class="form-control" placeholder="Email address" type="text"
                                   value="">
                            <div class="correct-email"></div>
                            <input id="password" name="password" class="form-control login-field  login-field-password" placeholder="Password"
                                   type="password"
                                   value="">
                            <div class="correct-password"></div>
                            <button id="buttonRegistration" class="btn btn-lg btn-primary btn-block">Register</button>
                        </div>
                        <div id="check" class="col-lg-1 col-md-4 col-sm-6 col-xs-6">
                            <label class="checkbox">
                                <input type="checkbox" id="checkbox_admin" value="ROLE_ADMIN" class="roles"> Admin
                            </label>
                            <label class="checkbox">
                                <input type="checkbox" id="checkbox_hr" value="ROLE_HR" class="roles"> HR
                            </label>
                            <label class="checkbox">
                                <input type="checkbox" id="checkbox_dev" value="ROLE_DEV" class="roles"> Developer
                            </label>
                            <label class="checkbox">
                                <input type="checkbox" id="checkbox_ba" value="ROLE_BA" class="roles"> BA
                            </label>
                        </div>
                        </form>
                    </div>
                </div>
            </div>
            <a href="/logout" target="_blank" id="view-source"
               class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">Exit</a>
    </div></main>

</div>



</body>
<script src="https://code.getmdl.io/1.1.3/material.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/bootstrap/js/bootstrap.js"></script>
<script src="/resources/js/hideShowPassword.min.js"></script>
<script src="/resources/js/admin-create-user.js"></script>
<script src="/resources/js/createNewUserValid.js"></script>
<script>
    $('#password').hideShowPassword(false, true);
</script>
</html>
