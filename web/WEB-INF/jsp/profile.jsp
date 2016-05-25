<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
<head>
    <title>Profile</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/png" sizes="32x32" href="/resources/images/ico.png"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <link rel="icon" type="image/png" sizes="32x32" href="/images/ico.png">
    <link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/resources/css/style-profile.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/resources/css/media-profile.css" rel="stylesheet">
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
                    <li><a href="/logout">Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>
</header>

<sec:authentication var="principal" property="principal"/>
<sec:authorize access="isAuthenticated()">

    <div class="container smprofile">
        <div class="row">
            <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
                <img id="photo_img" src="/getPhoto" alt="User's photo" width="100" height="100"
                     onError="this.src='/resources/images/user-photo.png'" class="profile-photo">
            </div>
            <%--<div class='col-lg-3 col-md-3 col-sm-3 col-xs-8'>--%>
                <%--<form id="photo_form" type=post enctype="multipart/form-data">--%>
                    <%--<div id="photoMessages"></div>--%>
                    <%--<label for='photo_input' class='file_upload'/>--%>
                    <%--<input type="file" id="photo_input" name=" photo_input" accept="image/*">--%>
                    <%--<button id="photo_button" type="submit">Upload</button>--%>
                <%--</form>--%>
            <%--</div>--%>
            <div class=" col-lg-3 col-md-3 col-sm-3 col-xs-3 mainf">
                <h4>Name:</h4>
                <h4>Surname:</h4>
                <h4>E-mail:</h4>
            </div>
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 userDetails">
                <span id="userName"></span>
                <span id="userSurname"></span>
                <span id="userEmail"></span>
            </div>
        </div>
    </div>
    </div>

    <form id="fields">

    </form>
    <sec:authorize access="hasRole('ROLE_STUDENT')">
        <div id="agreement">
            <label for="agree">I agree to have my personal information been proceeded</label>
            <input id="agree" type="checkbox"/>
        </div>
        <div id="fieldsCheck"></div>
        <div id="profileButtons">
            <button id="save" type="submit" form="fields" value="Submit" disabled="disabled">Save</button>
                <%--<button id="buttonEnroll" type="submit" value="Enroll" href="/enroll">Enroll</button>--%>
        </div>
    </sec:authorize>


</sec:authorize>


<jsp:include page="footer.jsp"/>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/js/profile.js"></script>
<script src="/resources/js/account.js"></script>
<script src="/resources/js/photo.js"></script>

</body>
</html>
