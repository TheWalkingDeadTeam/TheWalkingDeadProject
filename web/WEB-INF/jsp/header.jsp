<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Alexnader
  Date: 17.05.2016
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account Information</title>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/png" sizes="32x32" href="/resources/images/ico.png"/>
    <%--<link rel="stylesheet" type="text/css" href="/resources/css/reset.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="/resources/css/styles.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.css"/>--%>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <link rel="icon" type="image/png" sizes="32x32" href="/images/ico.png">
    <link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/resources/css/style-profile.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/resources/css/media-profile.css" rel="stylesheet">
    <script src="/resources/bootstrap/js/jquery-2.2.2.min.js" defer></script>
    <script src="/resources/bootstrap/js/bootstrap.min.js" defer></script>
    <%--<style type="text/css">--%>
    <%--/*<img src='images/logo.png' alt="Brand" class="header-img">*/--%>
    <%--/*<img src='images/error.gif' class="img-responsive profile-photo">*/--%>
    <%--/*<img class='img-responsive' src="images/logo-gray.png">*/--%>
</head>
<body>

<div class="intro deep-grey lighten-2 z-depth-1">
        <nav class="navbar navbar-default">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed button-header" data-toggle='collapse'
                            data-target='#collapsed-menu' aria-expanded="false">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand brand-img" href="">
                        <img src='/resources/images/logo.png' alt="Brand" class="header-img">
                    </a>
                </div>
                <div id='collapsed-menu' class='navbar-collapse collapse'>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="/login">Home</a></li>
                        <li><a href="/information">Information</a></li>
                        <li><a href="/contacts">Contacts</a></li>
                        <sec:authorize access="hasRole('ROLE_STUDENT')">
                            <li><a href="/account/profile">Profile</a></li>
                        </sec:authorize>
                        <li><a href="/logout">Logout</a></li>
                    </ul>
                </div>
        </nav>
</div>
<script src="/resources/js/changePassword.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/js/photo.js"></script>
<script src="/resources/js/hideShowPassword.min.js"></script>
<script>
    $('#changePassword').hideShowPassword(false, true);

</script>
</body>
</html>