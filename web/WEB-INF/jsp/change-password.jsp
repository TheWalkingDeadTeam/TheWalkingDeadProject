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