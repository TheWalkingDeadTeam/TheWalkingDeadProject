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
    <script src="/resources/bootstrap/js/jquery-2.2.2.min.js" defer></script>
    <script src="/resources/bootstrap/js/bootstrap.min.js" defer></script>
    <%--<style type="text/css">--%>
    <%--/*<img src='images/logo.png' alt="Brand" class="header-img">*/--%>
    <%--/*<img src='images/error.gif' class="img-responsive profile-photo">*/--%>
    <%--/*<img class='img-responsive' src="images/logo-gray.png">*/--%>
</head>
<jsp:include page="header.jsp"/>

    <div class="container smprofile">
        <div class="row">
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-4">
                <img id="photo_img" alt="User's photo" width="100" height="100"
                     onError="this.src='/resources/images/user-photo.png'" class="profile-photo">
            </div>
            <div class='col-lg-3 col-md-3 col-sm-3 col-xs-8'>
                <form id="photo_form" type=post enctype="multipart/form-data">
                    <div id="photoMessages"></div>
                    <label for='photo_input' class='file_upload'/>
                    <input type="file" id="photo_input" name=" photo_input" accept="image/*">
                    <button id="photo_button" type="submit"><spring:message code="locale.upload"/></button>
                </form>
            </div>
            <div class=" col-lg-2 col-md-2 col-sm-2 col-xs-4 mainf">
                <h4><spring:message code="locale.name"/>:</h4>
                <h4><spring:message code="locale.surname"/>:</h4>
                <h4><spring:message code="locale.email"/>:</h4>
            </div>
            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-4">
                <span id="userName"></span>
                <span id="userSurname"></span>
                <span id="userEmail"></span>
            </div>
        </div>
    </div>
    </div>

    <div id="profile">
    </div>
    <br>

    <%--<sec:authorize access="@feedbackPermissions.isInterviewingPeriod()">--%>

    <div id="restrict_message"></div>
    <div id="feedback">
        <form id="feedback_form">
            <div>
                <input type="number" id="feedback_score" max="100" min="1" align="centre" required/>

            </div>
            <div><textarea id="feedback_text" placeholder=<spring:message code="locale.putFeedback"/> cols="40" rows="10"
                           required></textarea>
            </div>
            <div>
                <select id = "special_mark">
                    <option disabled><spring:message code="locale.specmark"/></option>
                    <option value="none" id = "none"><spring:message code="locale.none"/></option>
                    <option value="reject" id = "reject"><spring:message code="locale.reject"/></option>
                    <option value="take on courses" id="take_on_courses"><spring:message code="locale.takeOnCourses"/></option>
                    <option value="job offer" id="job_offer"><spring:message code="locale.jobOffer"/></option>
                </select>
            </div>
            <div id="save_message"></div>
            <div>
                <button id="submitFeedback" type="submit" title="Submit"><spring:message code="locale.submit"/></button>
            </div>
        </form>
    </div>
    <%--</sec:authorize>--%>

    <sec:authorize access="@feedbackPermissions.isInterviewingPeriod()">
        <div id="getall_message"></div>
        <div id = "all_feedbacks">
            <p id = "dev_feedback">Dev's <spring:message code="locale.feedback"/><br></p>
            <p id = "dev_score">Dev's <spring:message code="locale.score"/>: <br></p>
            <p id = "hr_feedback">HR's <spring:message code="locale.feedback"/><br></p>
            <p id = "hr_score">HR's <spring:message code="locale.score"/>: <br></p>
            <p id = "special_mark_display"><spring:message code="locale.specmark"/>: </p>
        </div>

    </sec:authorize>


<jsp:include page="footer.jsp"/>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/js/profileForInterviewer.js"></script>
<script src="/resources/js/photo.js"></script>
<script src="/resources/js/account.js"></script>
<sec:authorize access="@feedbackPermissions.isInterviewingPeriod()">
<script src="/resources/js/getAllFeedbacks.js"></script>
</sec:authorize>

</body>
</html>
