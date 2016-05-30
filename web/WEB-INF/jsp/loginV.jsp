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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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


<jsp:include page="header.jsp"/>

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
        </div>
    </div>
</div>


<jsp:include page="footer.jsp"/>

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