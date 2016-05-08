<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
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
    <title>Contacts</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/png" sizes="32x32" href="/resources/images/ico.png"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="icon" type="image/png" sizes="32x32" href="/images/ico.png">
    <link rel="stylesheet" type="text/css" href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="resources/css/style-profile.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="resources/css/media-profile.css" rel="stylesheet">
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
                        <li><a href="/profile/{id}">Profile</a></li>
                    </sec:authorize>
                    <li><a href="/logout">Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>
</header>

<div class="headquarters-container bord-bottom">
    <div class="container headquarters-info">
        <div class="row">
            <div class="col-xs-6 col-sm-6 col-md-6">
                <div class="row">
                    <div class="col-md-12">
                        <div class="headline-one"><h1>Headquarters</h1></div>
                        <div class="standard-copy">University Office Park III</div>
                        <div class="standard-copy">95 Sawyer Road Waltham, MA 02453</div>
                        <div class="standard-copy">United States of America</div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="standard-copy"><h1>Phone numbers</h1></div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-4 col-sm-4 col-md-6">
                        <div class="standard-copy">Phone</div>
                    </div>
                    <div class="col-xs-8 col-sm-8 col-md-6">
                        <div class="standard-copy text-right">1-781-419-3300</div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-4 col-sm-4 col-md-6">
                        <div class="standard-copy">Toll Free</div>
                    </div>
                    <div class="col-xs-8 col-sm-8 col-md-6">
                        <div class="standard-copy text-right">1-800-477-5785</div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-4 col-sm-4 col-md-6">
                        <div class="standard-copy">Fax</div>
                    </div>
                    <div class="col-xs-8 col-sm-8 col-md-6">
                        <div class="standard-copy text-right">1-781-419-3301</div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-4 col-sm-4 col-md-4">
                        <div class="standard-copy">Customer Support</div>
                    </div>
                    <div class="col-xs-8 col-sm-8 col-md-8">
                        <div class="standard-copy text-right">1-781-419-3388, 1-844-855-3355</div>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-6 col-md-6"><img class="map-location" src="http://www.netcracker.com/assets/img/map-office-location.png" alt=""></div>
        </div>
    </div>
</div>

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
