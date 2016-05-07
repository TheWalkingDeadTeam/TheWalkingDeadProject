<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Neltarion
  Date: 04.05.2016
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account Information</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="icon" type="image/png" sizes="32x32" href="/resources/images/ico.png"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
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
                </button>
                <a class="navbar-brand brand-img" href="">
                    <img src='resources/images/logo.png' alt="Brand" class="header-img">
                </a>
            </div>
            <div id='collapsed-menu' class='navbar-collapse collapse'>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="">Home</a></li>
                    <li><a href="">Information</a></li>
                    <sec:authorize access="hasRole('ROLE_STUDENT')">
                        <li><a href="/profile">Profile</a></li>
                    </sec:authorize>
                    <li><a href="/logout">Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>
</header>

<form id="accountForm">
    <div class="container-fluid  smprofile">
        <div class="row">
            <div class=" col-lg-3 col-md-4 col-sm-4 col-xs-12 ">
                <img id="photo_img" src="/getPhoto" alt="User's photo" width="100" height="100"
                     onError="this.src='/resources/images/user-photo.png'" class="profile-photo">
                <form id="photo_form" type=post enctype="multipart/form-data">
                    <div id="photoMessages"></div>
                    Photo to upload: <input type="file" id="photo_input" name=" photo_input" accept="image/*"><br/>
                    <button id="photo_button" type="submit">Upload</button>
                </form>
            </div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-4">
                <h4>Name:</h4>
                <h4>Surname:</h4>
                <h4>E-mail:</h4>
            </div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-3">
                <span>Ivan</span>
                <span>Ivanovich</span>
                <span>ivanovivanovich@gmail.com</span>

            </div>
            <div class="col-lg-6 col-md-4 col-xs-3">
                <jsp:include page="change-password.jsp"/>
            </div>
        </div>
    </div>
</form>


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
<script src="/resources/js/changePassword.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/js/photo.js"></script>
</body>
</html>
