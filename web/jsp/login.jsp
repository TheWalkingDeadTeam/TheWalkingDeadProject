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
    <link rel="stylesheet" type="text/css" href="/resources/files/reset.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/files/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/files/styles.css"/>
</head>

<body id="document">

<header class="header">
    <div class="container-fluid navbar headerTop">
        <a href="#"><img class="col-lg-4 col-md-4 col-sm-9 col-xs-12" src="/resources/images/logo.png" alt="logo"/></a>
        <div class="headerBtn col-lg-2 col-md-2 col-sm-3 hidden-xs col-lg-offset-6 col-md-offset-6">
            <button class="btn col-sm-6 pull-right hidden-sm">Sign In</button>
            <button class="btnSm col-sm-6 pull-right visible-sm">Sign in</button>
            <p class="col-lg-10 col-md-10 col-sm-10 col-lg-offset-2 col-md-offset-2 col-sm-offset-2">Forgot
                password?</p>
        </div>
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
    <div class="reg col-lg-7 col-md-7 visible-lg visible-md">
        <sec:authorize access="!isAuthenticated()">
            <form:form action="/register" commandName="user">
                <c:if test="${param.register == 'success'}">
                    <div class="alert alert-success">
                        Registered successfully
                    </div>
                </c:if>
                <c:if test="${param.register == 'failed'}">
                    <div class="alert alert-danger">
                        Register failed
                    </div>
                </c:if>
                <c:if test="${param.register == 'exist'}">
                    <div class="alert alert-danger">
                        Such user already exists
                    </div>
                </c:if>
                <h2 class="form-signin-heading">Studet Registration</h2>
                <form:input path="name" cssClass="form-control" placeholder="Name"/>
                <form:input path="email" cssClass="form-control" placeholder="Email address"/>
                <form:password path="password" cssClass="form-control" placeholder="Password"/>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Register</button>
            </form:form>
        </sec:authorize>
    </div>


    <div class="inputBox col-lg-4 col-md-4 col-sm-12 col-xs-12">
        <sec:authorize access="!isAuthenticated()">
            <form action="/j_spring_security_check" method="post">
                <c:if test="${param.error != null}">
                    <div class="alert alert-danger">
                        Invalid username or password.
                    </div>
                </c:if>
                <c:if test="${param.logout != null}">
                    <div class="alert alert-success">
                        You have been logged out.
                    </div>
                </c:if>
                <h2 class="form-signin-heading">Please sign in</h2>
                <input type="text" class="form-control" name="j_username" placeholder="Email address" required>
                <input type="password" class="form-control" name="j_password" placeholder="Password" required>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            </form>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <div class="alert alert-info" role="alert">
                <p>Your login: <sec:authentication property="principal.username"/></p>
                <p><sec:authentication property="principal.authorities"/></p>
            </div>
            <p><a class="btn btn-lg btn-danger" href="<c:url value="/logout" />" role="button">Logout</a></p>
        </sec:authorize>
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
</html>