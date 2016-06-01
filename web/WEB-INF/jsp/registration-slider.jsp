<%--
  Created by IntelliJ IDEA.
  User: Neltarion
  Date: 29.05.2016
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Registration</title>

</head>
<body>
<div id="slideDown"  style="display: none;">
    <sec:authorize access="!isAuthenticated()">
        <form id="user" style="margin-left: auto; margin-right: auto;">
            <div id="messageRegistration"></div>
            <div class="row container-fluid reg-head">
                <div class="col-lg-6 col-md-8 col-sm-9 col-xs-9">
                    <h3 class="form-signin-heading"><spring:message code="locale.registration"/></h3>
                </div>
                <div class="col-lg-6 col-md-4 col-sm-3 col-xs-3 closeClass" style="padding-top: 13px; color: rgb(110, 150, 190);">
                    <i class="material-icons closeico"><span class="closebtn">keyboard_arrow_up</span></i>
                </div>
            </div>
            <input id="name" name="name" class="form-control" type="text" value="" placeholder=<spring:message code="locale.name"/>>
            <div class="correct-name"></div>
            <input id="surname" name="surname" class="form-control"  type="text" value="" placeholder=<spring:message code="locale.surname"/>>
            <div class="correct-surname"></div>
            <input id="email" name="email" class="form-control"  type="text" value="" placeholder="<spring:message code="locale.email"/>">
            <div class="correct-email"></div>
            <input id="password" name="password" class="form-control login-field  login-field-password" type="password" value="" placeholder=<spring:message code="locale.password"/>>
            <div class="correct-password"></div>
            <button id="buttonRegistration" class="btn btn-lg btn-primary btn-block" style="margin-top: 10px;"><spring:message code="locale.register"/></button>
        </form>
    </sec:authorize>
</div>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/js/test/bootstrap.min.js"></script>
<script src="/resources/js/registration.js"></script>
</body>
</html>
