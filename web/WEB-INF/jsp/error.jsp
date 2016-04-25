<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 18.04.2016
  Time: 20:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" type="text/css" href="/resources/files/reset.css" />
    <link rel="stylesheet" type="text/css" href="/resources/files/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="/resources/files/styles.css" />
</head>
<body>
<div class="text-center">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">You catch the error,
                    <small>or error catch you...</small>
                </h1>
            </div>

        </div>
        <sec:authorize access="isAuthenticated()">
            <p><sec:authentication property="principal.authorities"/></p>
        </sec:authorize>
        <img src="/resources/images/error.gif"/>
    </div>
</div>
</body>
</html>
