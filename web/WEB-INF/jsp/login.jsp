<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 18.04.2016
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Netcracker</title>
    <link rel="icon" type="image/png" sizes="32x32" href="/resources/images/ico.png"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/styles.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/registration.css" />
    <link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.css" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>

<body id="document">

<header class="header">
    <div class="container-fluid navbar headerTop">
        <a href="#"><img class="col-lg-4 col-md-4 col-sm-9 col-xs-12" src="/resources/images/logo.png" alt="logo"/></a>

    </div>
    <div class="col-lg-12 col-md-12 hidden-sm hidden-xs">
        <div class="container-fluid navbar-inner navigation">
            <a class="col-lg-4 col-md-4" href="#"><p>Home</p></a>
            <a class="col-lg-4 col-md-4" href="#"><p>Information</p></a>
            <a class="col-lg-4 col-md-4" href="#"><p>Contacts</p></a>
        </div>
    </div>
    <div class="signIn visible-xs">
        <div calss="container-fluid">
            <a class="col-xs-4 pull-left" href="#"><p>Forgot password?</p></a>
            <a class="col-xs-4 pull-right" href="#"><p class="pull-right">Sign in</p></a>
        </div>
    </div>
</header>


<div class="content container">
    <div class="reg registration">
        <div class="layout"></div>
        <sec:authorize access="!isAuthenticated()">
            <form id="user">
                <div id="messageRegistration"></div>
                <div class="row container-fluid reg-head">
                    <div class="col-lg-6 col-md-8 col-sm-9 col-xs-9">
                        <h2 class="form-signin-heading">Student Registration</h2>
                    </div>
                    <div class="col-lg-6 col-md-4 col-sm-3 col-xs-3 ">
                        <i class="material-icons closeico"><span class="closebtn">highlight_off</span></i>
                    </div>
                </div>
                <input id="name" name="name" class="form-control" placeholder="Name" type="text" value="">
                <div class="correct-name"></div>
                <input id="surname" name="surname" class="form-control" placeholder="Surname" type="text" value="">
                <div class="correct-surname"></div>
                <input id="email" name="email" class="form-control" placeholder="Email address" type="text" value="">
                <div class="correct-email"></div>
                <input id="password" name="password" class="form-control" placeholder="Password" type="password" value="">
                <div class="correct-password"></div>
                <button id="buttonRegistration" class="btn btn-lg btn-primary btn-block">Register</button>
            </form>
        </sec:authorize>
    </div>

    <div class="row">


        <div class="inputBox col-lg-4 col-md-4 col-sm-12 col-xs-12">
            <sec:authorize access="!isAuthenticated()">
                <form>
                    <div id="messageSignIn"></div>
                    <h2 class="form-signin-heading">Please sign in</h2>
                    <input id="j_username" type="text" class="form-control" name="j_username" placeholder="Email address" required>
                    <input id="j_password" type="password" class="form-control" name="j_password" placeholder="Password" required>
                    <button id="buttonSignIn" class="btn btn-lg btn-primary btn-block signbtn" type="submit">Sign in</button>
                    <button type="button" class="btn btn-lg btn-primary btn-block regbut">Registration</button>
                </form>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <div class="alert alert-info" role="alert">
                    <p>Your login: <sec:authentication property="principal.username"/></p>
                    <p><sec:authentication property="principal.authorities"/></p>
                </div>
                <p><a id="buttonLogout" class="btn btn-lg btn-danger" href="/logout" role="button">Logout</a></p>
                <p>
                <form id = "photoUpload" method="post" action="uploadPhoto" enctype="multipart/form-data">
                    Photo to upload: <input type="file" name="photo" accept="image/jpeg"><br />
                    <input type="submit" value="Upload"/>
                </form>
                </p>
            </sec:authorize>
        </div>
        <div class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
            <img class='img-responsive' src='/resources/images/main.jpg'>
        </div>
    </div>
</div>


<footer class="footer container-fluid">
    <div class="footerLg container visible-md visible-lg">
        <img class="col-lg-3 col-lg-3 col-sm-3" src="/resources/images/logo-gray.png"/>
        <div class="col-lg-8 col-md-8 col-lg-offset-1 col-lg-offset-1 col-md-offset-1">
            <div class="footerLgText col-lg-3 col-md-3 col-lg-offset-1 col-md-offset-1">
                <p>Univercity Office Park III</p>
                <p>95 Sawyer Road</p>
                <p>Waltham, MA 02453 USA</p>
                <p>1-781-419-3300</p>
            </div>
            <div class="footerLgText col-lg-3 col-md-3 col-lg-offset-1 col-md-offset-1">
                <p>Facebook /NetcrackerTech</p>
                <p>Twitter @NetcrackerTech</p>
                <p>LikedIn /netcracker</p>
            </div>
            <div class="footerLgText col-lg-3 col-md-3 col-lg-offset-1 col-md-offset-1">
                <p>Privacy Policy</p>
                <p>Terms of Use</p>
                <p>Sitemap</p>
            </div>
        </div>
    </div>
    <div class="footerSm row visible-sm visible-xs">
        <img class="col-sm-5 visible-sm" src="/resources/images/logo-gray.png"/>
        <div class="footerSmText col-sm-7 col-xs-12">
            <div class="col-sm-8 col-xs-6">
                <a class="col-sm-6 col-xs-7" href="#"><p>Courses Info</p></a>
                <a class="col-sm-6 col-xs-7" href="#"><p>Contacts</p></a>
            </div>
            <div class="col-sm-4 col-xs-3 pull-right">
                <p>Privacy Policy</p>
                <p>Terms of Use</p>
                <p>Sitemap</p>
            </div>
        </div>
    </div>
</footer>

</body>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/js/login.js"></script>
<script src="/resources/js/logout.js"></script>
<script src="/resources/bootstrap/js/bootstrap.js"></script>
<script src="/resources/js/registration.js"></script>
</html>