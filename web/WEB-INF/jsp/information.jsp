<%--
  Created by IntelliJ IDEA.
  User: Neltarion
  Date: 05.05.2016
  Time: 16:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Info about us</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="icon" type="image/png" sizes="32x32" href="/resources/images/ico.png"/>
    <link rel="icon" type="image/png" sizes="32x32" href="/images/ico.png">
    <link rel="stylesheet" type="text/css" href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="resources/css/style.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="resources/css/media.css" rel="stylesheet">
    <script src="resources/bootstrap/js/jquery-2.2.2.min.js" defer></script>
    <script src="resources/bootstrap/js/bootstrap.min.js" defer></script>
</head>
<body>
<header>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed button-header" data-toggle='collapse'
                        data-target='#collapsed-menu' aria-expanded="false">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
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
                    <li><a href="/logout">Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>
</header>

<footer class="footer container-fluid">
    <div class="footerLg container visible-md visible-lg">
        <div class="col-lg-3 col-lg-3 col-sm-3"><img class='img-responsive' src="resources/images/logo-gray.png"></div>

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
        <img class="col-sm-5 visible-sm" src="resources/images/logo-gray.png">
        <div class="footerSmText col-sm-7 col-xs-12">
            <div class="col-sm-8 col-xs-6">
                <a class="col-sm-6 col-xs-7" href="http://localhost:8080/profile#"><p>Courses Info</p></a>
                <a class="col-sm-6 col-xs-7" href="http://localhost:8080/profile#"><p>Contacts</p></a>
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