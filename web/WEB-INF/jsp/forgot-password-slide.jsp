<%--
  Created by IntelliJ IDEA.
  User: Neltarion
  Date: 29.05.2016
  Time: 23:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Forgot password</title>
</head>
<body>
<div id="slideForgotPass" class="recovery"  style="display: none;">
    <%--<div class="layout"></div>--%>
    <sec:authorize access="!isAuthenticated()">
        <form id="stupidUser" action="/passwordRecovery">
            <div id="passwordRecoveryMessage"></div>
            <div class="row container-fluid recovery-head">
                <div class="col-lg-8 col-md-8 col-sm-9 col-xs-9">
                    <h3 class="form-signin-heading"><spring:message code="locale.recoverPassword"/></h3>
                </div>
                <div class="col-lg-4 col-md-4 col-sm-3 col-xs-3 ">
                    <i class="material-icons closeico" style="padding-top: 13px; color: rgb(110, 150, 190); cursor: pointer;">
                        <span class="closePass">keyboard_arrow_up</span></i>
                </div>
            </div>
            <input id="userMail" name="email" class="form-control" type="text" value=""
                   placeholder="<spring:message code="locale.email"/>">
            <div class="correct-email"></div>
            <button id="buttonRecoverPassword" style="margin-top: 10px;" class="btn btn-lg btn-primary btn-block">
                <spring:message code="locale.sendRequest"/></button>
        </form>
    </sec:authorize>
</div>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/js/test/bootstrap.min.js"></script>
</body>
</html>
