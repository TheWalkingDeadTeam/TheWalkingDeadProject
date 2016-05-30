<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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


<jsp:include page="header.jsp"/>

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
            <div class=" col-lg-4 col-md-5 col-sm-5 col-xs-5 mainf">
                <h4><spring:message code="locale.name"/>:</h4>
                <h4><spring:message code="locale.surname"/>:</h4>
                <h4><spring:message code="locale.email"/>:</h4>
            </div>
            <div class="col-lg-5 col-md-4 col-sm-4 col-xs-4" style="margin-top: 7px;">
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
            <label for="agree"><spring:message code="locale.agreement"/></label>
            <input id="agree" type="checkbox"/>
        </div>
        <div id="fieldsCheck"></div>
        <div id="profileButtons">
            <button id="save" type="submit" form="fields" value="Submit" disabled="disabled"><spring:message
                    code="locale.save"/></button>
                <%--<button id="buttonEnroll" type="submit" value="Enroll" href="/enroll">Enroll</button>--%>
        </div>
    </sec:authorize>

    <div style="display: block; margin: 10px auto; max-width: 700px;">
        <button id="pdf" type="submit" form="fields" value="Submit" onclick="download();">Download PDF</button>
    </div>

</sec:authorize>


<jsp:include page="footer.jsp"/>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/js/profile.js"></script>
<script src="/resources/js/account.js"></script>
<script src="/resources/js/photo.js"></script>
<script src="/resources/js/pdfstructure.js"></script>
<script src='/resources/js/pdfmake.min.js'></script>
<script src='/resources/js/vfs_fonts.js'></script>


</body>
</html>
