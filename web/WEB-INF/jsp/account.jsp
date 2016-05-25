<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/png" sizes="32x32" href="/resources/images/ico.png"/>
    <%--<link rel="stylesheet" type="text/css" href="/resources/css/reset.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="/resources/css/styles.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.css"/>--%>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="icon" type="image/png" sizes="32x32" href="/images/ico.png">
    <link rel="stylesheet" type="text/css" href="/resources/css/style-profile.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/resources/css/media-profile.css" rel="stylesheet">
    <link href="http://netdna.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href='/resources/bootstrap/css/bootstrap.css' rel='stylesheet'/>
    <link href='/resources/css/rotating-card.css' rel='stylesheet'/>
    <script src="/resources/bootstrap/js/jquery-2.2.2.min.js" defer></script>
    <script src="/resources/bootstrap/js/bootstrap.min.js" defer></script>
    <link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/jasny-bootstrap.css">
    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport'/>
    <link rel="stylesheet" href="/resources/css/style-account.css">
    <style>
        .card .stats:last-child {
            border-left: 0px solid #EEEEEE;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<sec:authorize access="isAuthenticated()">
    <div class="card-container manual-flip">
        <div class="card">
            <div class="front">
                <div class="cover">
                    <img src="/resources/images/background-2.jpg"/>
                </div>
                <div class="user">
                    <img id="photo_img" src="" alt="User's photo" width="100" height="120"
                         onError="this.src='/resources/images/user-photo.png'" class="img-circle">
                </div>
                <div class="content">
                    <div class="main">
                        <h3 id="userName" class="name"></h3>
                        <p id="userSurname" class="profession"></p>
                        <h5 id="userRoles" class="profession"></h5>
                        <p id="userEmail" class="profession"></p>
                    </div>
                    <div class="footer" style="margin-bottom: 20px">
                        <button class="btn btn-simple" onclick="rotateCard(this)">
                            <i class="fa fa-mail-forward"></i> <spring:message code="locale.options"/>
                        </button>
                    </div>
                </div>
            </div> <!-- end front panel -->
            <div class="back">

                <div class="content">
                    <div class="main">

                        <div align="center">
                            <jsp:include page="changePhoto.jsp"/>
                        </div>

                        <div>
                            <div class="stats col-lg-4 col-md-4 col-sm-5 col-xs-5">
                            <h5><spring:message code="locale.changePassword"/></h5>
                                <jsp:include page="change-password.jsp"/>
                            </div>
                            <div class="stats col-lg-4 col-md-4 col-sm-2 col-xs-2">
                                <sec:authorize access="!hasRole('ROLE_STUDENT')">
                                    <h5><spring:message code="locale.enrollment"/></h5>
                                    <sec:authentication var="principal" property="principal"/>
                                    <input id="userid" type="hidden" value=" ${principal.id}"/>
                                    <div id="enrollMessages"></div>
                                    <button id="enroll_button" href="/interviewer/enroll-ces-interviewer"
                                            class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white"
                                            type="submit"><spring:message code="locale.enroll"/></button>
                                </sec:authorize>
                            </div>
                            <div class="stats col-lg-4 col-md-4 col-sm-5 col-xs-5">

                                <jsp:include page="change-roles.jsp"/>
                            </div>
                        </div>
                    </div>
                </div>
                <button class="btn btn-simple" rel="tooltip" title="Flip Card" onclick="rotateCard(this)">
                    <i class="fa fa-reply"></i> <spring:message code="locale.back"/>
                </button>
            </div> <!-- end back panel -->
        </div> <!-- end card -->
    </div>
    <!-- end card-container -->
    </div>
    </div>

</sec:authorize>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="/resources/bootstrap/js/jasny-bootstrap.js"></script>
<jsp:include page="footer.jsp"/>
<script src="/resources/js/changePassword.js"></script>
<script src="/resources/js/photo.js"></script>
<script src="/resources/js/account.js"></script>
<script src="/resources/js/hideShowPassword.min.js"></script>
<%--<script src="/resources/js/changeRoles.js"></script>--%>


<script type="text/javascript">
    $().ready(function () {
        $('[rel="tooltip"]').tooltip();

    });

    function rotateCard(btn) {
        var $card = $(btn).closest('.card-container');
        console.log($card);
        if ($card.hasClass('hover')) {
            $card.removeClass('hover');
        } else {
            $card.addClass('hover');
        }
    }
</script>
</body>
</html>
