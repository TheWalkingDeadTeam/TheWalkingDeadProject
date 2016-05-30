<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
                    <img src='/resources/images/logo.png' alt="Brand" class="header-img">
                </a>
            </div>
            <div id='collapsed-menu' class='navbar-collapse collapse'>
                <a href="?lang=en"><img src="/resources/images/en.png"></a> | <a href="?lang=uk"><img src="/resources/images/ua.png"></a>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/login">
                        <sec:authorize access="hasRole('ROLE_STUDENT') or !isAuthenticated()">
                            <spring:message code="locale.home"/>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            Admin Panel
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_HR')">
                            Hr Panel
                        </sec:authorize>
                    </a></li>
                    <sec:authorize access="hasAnyRole('ROLE_HR','ROLE_DEV','ROLE_BA')">
                    <li><a href="/interviewee"><spring:message code="locale.interviewee"/></a></li>
                    </sec:authorize>
                    <%--<li><a href="/information"><spring:message code="locale.info"/></a></li>--%>
                    <%--<li><a href="/contacts"><spring:message code="locale.contacts"/></a></li>--%>
                    <sec:authorize access="hasRole('ROLE_STUDENT')">
                        <sec:authentication var="principal" property="principal"/>
                        <li><a href="/profile?${principal.id}"><spring:message code="locale.profile"/></a></li>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                    <li><a href="/logout"><spring:message code="locale.logout"/></a></li>
                    </sec:authorize>
                </ul>
            </div>
        </div>
    </nav>
</header>