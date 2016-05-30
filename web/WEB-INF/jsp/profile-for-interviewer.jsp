<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
    <title>Profile For Interviewer</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/png" sizes="32x32" href="/resources/images/ico.png"/>
    <%--<link rel="stylesheet" type="text/css" href="/resources/css/reset.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="/resources/css/styles.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.css"/>--%>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <link rel="icon" type="image/png" sizes="32x32" href="/images/ico.png">
    <link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/resources/css/style-profile.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/resources/css/media-profile.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/resources/css/inter/inter-feedback.css" rel="stylesheet">
    <script src="/resources/bootstrap/js/jquery-2.2.2.min.js" defer></script>
    <script src="/resources/bootstrap/js/bootstrap.min.js" defer></script>
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="/resources/css/style-interv-acc.css">
    <%--<link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.cyan-light_blue.min.css">--%>
    <%--<link rel="stylesheet" href="/resources/css/styles.css">--%>
    <%--<style type="text/css">--%>
    <%--/*<img src='images/logo.png' alt="Brand" class="header-img">*/--%>
    <%--/*<img src='images/error.gif' class="img-responsive profile-photo">*/--%>
    <%--/*<img class='img-responsive' src="images/logo-gray.png">*/--%>
</head>
<style>
    .panel-title a {
        display: block;
        padding: 10px 15px;
        margin: -10px -15px;
    }

</style>

<jsp:include page="header.jsp"/>

<div class="container smprofile">
    <div class="row">
        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
            <img id="photo_img" src="/getPhoto" alt="User's photo" width="100" height="100"
                 onError="this.src='/resources/images/user-photo.png'" class="profile-photo">
        </div>
        <div class=" col-lg-4 col-md-5 col-sm-5 col-xs-5 mainf">
            <h4><spring:message code="locale.name"/>:</h4>
            <h4><spring:message code="locale.surname"/>:</h4>
            <h4><spring:message code="locale.email"/>:</h4>
        </div>
        <div class="col-lg-5 col-md-4 col-sm-4 col-xs-4 userDetails">
            <span id="userName"></span>
            <span id="userSurname"></span>
            <span id="userEmail"></span>
        </div>
    </div>
    <div class="panel-group">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" href="#collapse1" id="studForm">
                        <spring:message code="locale.profile"/> <i class="material-icons">keyboard_arrow_down</i>
                    </a>
                </h4>
            </div>
            <div id="collapse1" class="panel-collapse collapse">
                <div class="panel-body">
                    <div id="profile"></div>
                </div>
                <div class="panel-footer">

                </div>
            </div>
        </div>
    </div>

<div id="restrict_message" style="margin-top: 7px;"></div>
    <div id="feedbacks" style="margin-bottom: 10px;"></div>






</div>
<jsp:include page="footer.jsp"/>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/js/profileForInterviewer.js"></script>
<script src="/resources/js/photo.js"></script>
<script src="/resources/js/account.js"></script>

</body>
</html>
