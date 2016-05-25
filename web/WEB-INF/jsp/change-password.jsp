<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 04.05.2016
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<sec:authorize access="isAuthenticated()">
    <div class="" role="alert">

        <form>
            <input id="changePassword" name="password" style="margin-bottom: 5px;" class="form-control login-field  login-field-password" placeholder=<spring:message code="locale.password"/> type="password"
                   value="">
            <button id="buttonChangePassword" class="btn btn-lg btn-primary btn-block changebtn mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white"> <spring:message code="locale.changePassword"/></button>
        </form>
        <div id="messageCheckPassword"></div>
    </div>
</sec:authorize>