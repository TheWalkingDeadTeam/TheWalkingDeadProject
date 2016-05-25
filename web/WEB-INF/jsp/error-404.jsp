<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 13.05.2016
  Time: 15:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error 404</title>
    <link rel="icon" type="image/png" sizes="32x32" href="/resources/images/ico.png"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/styles.css"/>
</head>
<body>
<div class="text-center">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">You caught 404 error,
                    <small>The requested URL [${requestScope['javax.servlet.forward.request_uri']}] was not found in this server.</small>
                </h1>
                <img class="img-responsive" src="/resources/images/error-404.gif" style="margin: 0 auto;"/>
            </div>
        </div>
    </div>
</div>
</body>
</html>
