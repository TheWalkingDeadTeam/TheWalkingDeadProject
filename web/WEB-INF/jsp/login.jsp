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
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>




<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Netcracker</title>
    <link rel="icon" type="image/png" sizes="32x32" href="/resources/images/ico.png"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/styles.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/registration.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.css"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet"/>
    <script src='https://www.google.com/recaptcha/api.js'></script>
</head>

<body id="document">

<header>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed button-header" data-toggle='collapse'
                        data-target='#collapsed-menu' aria-expanded="false">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand brand-img" href="">
                    <img src='resources/images/logo.png' alt="Brand" class="header-img">
                </a>
            </div>
            <div id='collapsed-menu' class='navbar-collapse collapse'>
                <a href="?lang=en">English</a>                |
                <a href="?lang=uk">Українська</a>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/login"><spring:message code="locale.home"/></a></li>
                    <li><a href="/information"><spring:message code="locale.info"/></a></li>
                    <li><a href="/contacts"><spring:message code="locale.contacts"/></a></li>
                </ul>
            </div>
        </div>
    </nav>
</header>


<div class="content container">
    <div class="reg registration">
        <div class="layout"></div>
        <sec:authorize access="!isAuthenticated()">
            <form id="user">
                <div id="messageRegistration"></div>
                <div class="row container-fluid reg-head">
                    <div class="col-lg-6 col-md-8 col-sm-9 col-xs-9">
                        <h2 class="form-signin-heading"><spring:message code="locale.registration"/></h2>
                    </div>
                    <div class="col-lg-6 col-md-4 col-sm-3 col-xs-3 ">
                        <i class="material-icons closeico"><span class="closebtn">highlight_off</span></i>
                    </div>
                </div>
                <input id="name" name="name" class="form-control" placeholder=<spring:message code="locale.name"/> type="text" value="">
                <div class="correct-name"></div>
                <input id="surname" name="surname" class="form-control" placeholder=<spring:message code="locale.surname"/> type="text" value="">
                <div class="correct-surname"></div>
                <input id="email" name="email" class="form-control" placeholder=<spring:message code="locale.email"/> type="text" value="">
                <div class="correct-email"></div>
                <input id="password" name="password" class="form-control login-field  login-field-password" placeholder=<spring:message code="locale.password"/> type="password"
                       value="">
                <div class="correct-password"></div>
                <div class="g-recaptcha" data-sitekey="6LdZ1R8TAAAAAMwVjN-N-oTtZR51Li8QmKoSYEiF"></div>
                <button id="buttonRegistration" class="btn btn-lg btn-primary btn-block"><spring:message code="locale.register"/></button>

            </form>
        </sec:authorize>
    </div>

    <div class="recovery registration">
        <div class="layout"></div>
        <sec:authorize access="!isAuthenticated()">
            <form id="stupidUser" action="/passwordRecovery">
                <div id="passwordRecoveryMessage"></div>
                <div class="row container-fluid recovery-head">
                    <div class="col-lg-6 col-md-8 col-sm-9 col-xs-9">
                        <h2 class="form-signin-heading"><spring:message code="locale.recoverPassword"/></h2>
                    </div>
                    <div class="col-lg-6 col-md-4 col-sm-3 col-xs-3 ">
                        <i class="material-icons closeico"><span class="closebtn">clear</span></i>
                    </div>
                </div>
                <input id="userEmail" name="email" class="form-control" placeholder=<spring:message code="locale.email"/> type="text"
                       value="">
                <div class="correct-email"></div>
                <button id="buttonRecoverPassword" class="btn btn-lg btn-primary btn-block"><spring:message code="locale.sendRequest"/></button>
            </form>
        </sec:authorize>
    </div>

    <div class="row">


        <div class="inputBox col-lg-4 col-md-4 col-sm-12 col-xs-12">


            <sec:authorize access="isAuthenticated()">
                <div class="alert alert-info" role="alert">
                    <div id="messageCheckPassword"></div>
                    <form>
                        <input id="changePassword" name="password" class="form-control" placeholder=<spring:message code="locale.password"/>
                               type="password"
                               value="">
                        <button id="buttonChangePassword" class="btn btn-lg btn-primary btn-block changebtn">
                            <spring:message code="locale.changePassword"/>
                        </button>
                    </form>
                </div>
            </sec:authorize>


           <sec:authorize access="!isAuthenticated()">
                <form>
                    <div id="messageSignIn"></div>
                    <h2 class="form-signin-heading"><spring:message code="locale.pleaseSignIn"/></h2>
                    <input id="j_username" type="text" class="form-control" name="j_username"
                           placeholder=<spring:message code="locale.email"/> required>
                    <input id="j_password" type="password" class="form-control login-field  login-field-password" name="j_password" placeholder=<spring:message code="locale.password"/>
                           required>
                    <button id="buttonSignIn" class="btn btn-lg btn-primary btn-block signbtn" type="submit"><spring:message code="locale.signin"/>
                    </button>
                    <button type="button" class="btn btn-lg btn-primary btn-block regbut"><spring:message code="locale.registration"/></button>
                    <button style="display: none;" type="button" id="recpass"
                            class="btn btn-lg btn-primary btn-block recoverybtn">Forgot password
                    </button>
                    <label for="recpass"><spring:message code="locale.forgotPassword"/></label>
                </form>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <div class="alert alert-info" role="alert">
                    <p>Your login: <sec:authentication property="principal.username"/></p>
                    <p><sec:authentication property="principal.authorities"/></p>
                    <p><img id="photo_img" src="/getPhoto" alt="User's photo" width="100" height="100"
                            onError="this.src='/resources/images/user-photo.png'"/></p>
                </div>
&lt;%&ndash;
                <p><a id="buttonLogout" class="btn btn-lg btn-danger" href="/logout" role="button"><spring:message code="locale.logout"/></a></p>

            </sec:authorize>

        </div>
        <div id="logoDiv" class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
            <div id="thisDiv">
                <img id="mainLogo" class='img-responsive' src='/resources/images/main.jpg'>
            </div>
        </div>
    </div>
</div>


<footer class="footer container-fluid">
    <div class="footerLg container visible-md visible-lg">
        <div class="col-lg-3 col-lg-3 col-sm-3"><img class='img-responsive' src="resources/images/logo-gray.png"></div>

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
        <div class="col-sm-5 visible-sm" >
            <img src="resources/images/logo-gray.png">
        </div>
        <div class="footerSmText col-sm-7 col-xs-12">
            <div class="col-sm-8 col-xs-6">
                <a class="col-sm-6 col-xs-7" href="http://localhost:8080/profile#"><p>Courses Info</p></a>
                <a class="col-sm-6 col-xs-7" href="http://localhost:8080/profile#"><p>Contacts</p></a>
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
<script src="/resources/js/changePassword.js"></script>
<script src="/resources/js/login.js"></script>
<script src="/resources/js/logout.js"></script>
<script src="/resources/bootstrap/js/bootstrap.js"></script>
<script src="/resources/js/registration.js"></script>
<script src="/resources/js/passwordRecovery.js"></script>
<script src="/resources/js/hideShowPassword.min.js"></script>
</html>