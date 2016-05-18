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
        <div id="messageCheckPassword"></div>
        <form>
            <input id="changePassword" name="password" class="form-control login-field  login-field-password" placeholder="Password" type="password"
                   value="">
            <button id="buttonChangePassword" class="btn btn-lg btn-primary btn-block changebtn">ChangePassword</button>
        </form>
    </div>
</sec:authorize>