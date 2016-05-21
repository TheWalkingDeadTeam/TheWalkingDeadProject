<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Alexnader
  Date: 17.05.2016
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


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
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/login">Home</a></li>
                    <li><a href="/information">Information</a></li>
                    <li><a href="/contacts">Contacts</a></li>
                    <sec:authorize access="hasRole('ROLE_STUDENT')">
                        <sec:authentication var="principal" property="principal"/>
                        <li><a href="/profile/${principal.id}">Profile</a></li>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                    <li><a href="/logout">Logout</a></li>
                    </sec:authorize>
                </ul>
            </div>
        </div>
    </nav>
</header>