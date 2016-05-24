<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
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
    <link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.cyan-light_blue.min.css">
    <link rel="stylesheet" href="/resources/css/styles.css">
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

<div id="restrict_message"></div>



<div class="panel-group">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" href="#collapse1" >
                    Profile
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




<%--<sec:authorize access="@feedbackPermissions.isInterviewingPeriod()">--%>

<%--<div id="feedback">
    <form id="feedback_form">
        <div>
            <input type="number" id="feedback_score" max="100" min="1" align="centre" required/>

        </div>
        <div><textarea id="feedback_text" placeholder="Put your feedback here" cols="40" rows="10"
                       required></textarea>
        </div>
        <div>
            <select id="special_mark">
                <option disabled>Special mark</option>
                <option value="none" id="none">None</option>
                <option value="reject" id="reject">Reject</option>
                <option value="take on courses" id="take_on_courses">Take on courses</option>
                <option value="job offer" id="job_offer">Job offer</option>
            </select>
        </div>
        <div id="save_message"></div>
        <div>
            <button id="submitFeedback" type="submit" title="Submit">Submit</button>
        </div>
    </form>
</div>--%>


<div class="container">
    <div class="col-lg-6 col-md-8 col-sm-12 col-xs-12">
        <div id="save_message"></div>
        <div <%--id="feedback"--%>>
            <div class="row container-fluid reg-head">
                <div>
                    <h4 class="form-signin-heading">Feedback :</h4>
                </div>
            </div>
            <form id="feedback_form">
                <div id="regform" class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
                    <textarea rows="10" cols="10" id="feedback_text" style="margin-bottom: 3px;" class="form-control"
                              placeholder="Feedback" required></textarea>
                </div>
                <div  class="col-lg-4 col-md-8 col-sm-12 col-xs-12">
                    <label>Mark: </label>
                    <input id="feedback_score" style="margin-bottom: 3px;" class="form-control"
                           placeholder="1 .. 100" type="number"
                           max="100" min="1" align="centre" required>
                    <label>Special mark: </label>
                    <select id="special_mark" style="margin-bottom: 3px;" class="form-control">
                        <option value="none" id="none">None</option>
                        <option value="reject" id="reject">Reject</option>
                        <option value="take on courses" id="take_on_courses">Take on courses</option>
                        <option value="job offer" id="job_offer">Job offer</option>
                    </select>
                </div>
                <div class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
                    <button id="submitFeedback" style="border-radius: 4px;    margin-top: 4px ;"
                            class="btn btn-lg btn-primary btn-block mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">
                        Submit
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>


<%--</sec:authorize>--%>
<%--<sec:authorize access="@feedbackPermissions.isInterviewingPeriod()">--%>
    <div id="all_feedbacks">
        <div class="widget">
            <div class="widget-header clearfix">
                <h3><i class="icon ion-ios-browsers"></i> <span>
                    <p id="special_mark_display">Special mark: </p>


        </span></h3>
                <ul class="nav nav-tabs pull-right">
                    <li class="active"><a href="#tab1" data-toggle="tab"><i class="icon ion-gear-b"></i> Developer <span
                            id="dev_score" class="label label-info label-as-badge pull-left">55</span></a></li>
                    <li class=""><a href="#tab2" data-toggle="tab"><i class="icon ion-help-circled"></i> HR/BA <span
                            id="hr_score" class="label label-info label-as-badge pull-left">75</span></a></li>
                </ul>
            </div>

            <div class="widget-content tab-content">
                <div class="tab-pane fade active in" id="tab1">
                    <p id="dev_feedback">Dev feedback</p>
                </div>
                <div class="tab-pane fade" id="tab2">
                    <p id="hr_feedback">Hr feedback</p>
                </div>
            </div>
        </div>
    </div>

<%--
</sec:authorize>
--%>


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
