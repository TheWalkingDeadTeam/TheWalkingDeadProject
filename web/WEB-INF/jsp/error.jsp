<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 18.04.2016
  Time: 20:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
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
                <h1 class="page-header">You caught the error,
                    <small>or error caught you...</small>
                </h1>
                <img class="img-responsive" src="/resources/images/error.gif" style="margin: 0 auto;"/>
                <h1 class="page-header"><a href="/">Back to sweat home</a></h1>
                <small>${error == null ? " " : error}</small>
            </div>

        </div>

    </div>
</div>
</body>
</html>
