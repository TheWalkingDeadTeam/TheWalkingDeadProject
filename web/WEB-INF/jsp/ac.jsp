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
    <link href="http://netdna.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href='/resources/bootstrap/css/bootstrap.css' rel='stylesheet'/>
    <link href='/resources/css/rotating-card.css' rel='stylesheet'/>
    <script src="https://google-code-prettify.googlecode.com/svn/loader/run_prettify.js"></script>
    <script src="/resources/bootstrap/js/jquery-2.2.2.min.js" defer></script>
    <script src="/resources/bootstrap/js/bootstrap.min.js" defer></script>
    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport'/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.6/css/materialize.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.6/js/materialize.min.js"></script>


    <%--<style type="text/css">--%>
    <%--/*<img src='images/logo.png' alt="Brand" class="header-img">*/--%>
    <%--/*<img src='images/error.gif' class="img-responsive profile-photo">*/--%>
    <%--/*<img class='img-responsive' src="images/logo-gray.png">*/--%>
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
                    <img src='/resources/images/logo.png' alt="Brand" class="header-img">
                </a>
            </div>
            <div id='collapsed-menu' class='navbar-collapse collapse'>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/login">Home</a></li>
                    <li><a href="/information">Information</a></li>
                    <li><a href="/contacts">Contacts</a></li>
                    <sec:authorize access="hasRole('ROLE_STUDENT')">
                        <li><a href="/account/profile">Profile</a></li>
                    </sec:authorize>
                    <li><a href="/logout">Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>
</header>


<sec:authorize access="isAuthenticated()">
    <div class="card-container manual-flip">
        <div class="card">
            <div class="front">
                <div class="cover">
                    <img src="/resources/images/background-2.jpg"/>
                </div>
                <div class="user">
                    <img id="photo_img" src="/getPhoto" alt="User's photo" width="100" height="120"
                         onError="this.src='/resources/images/user-photo.png'" class="img-circle">
                        <%--<img class="img-circle" src="images/rotating_card_profile2.png"/>--%>
                </div>
                <div class="content">
                    <div class="main">
                        <h3 id="userName" class="name"></h3>
                        <p id="userSurname" class="profession"></p>
                        <p id="userEmail" class="profession"></p>


                        <p class="text-center">"Lamborghini Mercy <br>Your chick she so thirsty <br>I'm in that
                            two seat Lambo"</p>
                    </div>
                    <div class="footer" style="margin-bottom: 20px">
                        <button class="btn btn-simple" onclick="rotateCard(this)">
                            <i class="fa fa-mail-forward"></i> Options
                        </button>
                    </div>
                </div>
            </div> <!-- end front panel -->
            <div class="back">
                <div class="header">
                    <h5 class="motto">"To be or not to be, this is my awesome motto!"</h5>
                </div>
                <div class="content">
                    <div class="main">
                        <h5 class="text-center">Job Description</h5>
                        <p class="text-center">Web design, Adobe Photoshop, HTML5, CSS3, Corel and many
                            others...</p>
                        <div class="stats-container">
                            <div class="stats">
                                <h5>Upload Image</h5>


                                <form id="photo_form" type=post enctype="multipart/form-data">
                                    <div id="photoMessages"></div>
                                    <div class="file-field input-field">
                                        <div class="btn">
                                            <span>File</span>
                                            <input type="file" id="photo_input" name="photo_input" accept="image/*">
                                        </div>
                                        <div class="file-path-wrapper">
                                            <input class="file-path validate" type="text">
                                        </div>
                                        <div >
                                            <button class="btn" id="photo_button" type="submit">Upload</button>
                                        </div>
                                    </div>
                                </form>


                                    <%--<form id="photo_form" type=post enctype="multipart/form-data">--%>
                                    <%--<div id="photoMessages"></div>--%>
                                    <%--Photo to upload: <input type="file" id="photo_input" name=" photo_input"--%>
                                    <%--accept="image/*"><br/>--%>
                                    <%--<button id="photo_button" type="submit">Upload</button>--%>
                                    <%--</form>--%>


                            </div>
                            </div>
                            <div class="stats">
                                <h5>Change Password</h5>

                                <sec:authorize access="isAuthenticated()">
                                    <div class="" role="alert">
                                        <div id="messageCheckPassword"></div>
                                        <form>
                                            <input id="changePassword" name="password" class="form-control" placeholder="Password" type="password"
                                                   value="">
                                            <button id="buttonChangePassword" class="btn  btn-primary btn-block changebtn">Change Password</button>
                                        </form>
                                    </div>
                                </sec:authorize>

                            </div>
                            <div class="stats">
                                <h5>Enrollment</h5>
                                <sec:authorize access="!hasRole('ROLE_STUDENT')">
                                    <div id="enrollMessages"></div>
                                    <button class="btn" id="enroll_button" type="submit">Enroll</button>
                                </sec:authorize>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="footer">
                    <button class="btn btn-simple" rel="tooltip" title="Flip Card" onclick="rotateCard(this)">
                        <i class="fa fa-reply"></i> Back
                    </button>
                    <div class="social-links text-center">
                        <a href="http://creative-tim.com" class="facebook"><i class="fa fa-facebook fa-fw"></i></a>
                        <a href="http://creative-tim.com" class="google"><i class="fa fa-google-plus fa-fw"></i></a>
                        <a href="http://creative-tim.com" class="twitter"><i
                                class="fa fa-twitter fa-fw"></i></a>
                    </div>
                </div>
            </div> <!-- end back panel -->
        </div> <!-- end card -->
    </div>
    <!-- end card-container -->
    </div>
    <!-- end col sm 3 -->
    <!-- <div class="col-sm-1"></div> -->
    </div>

</sec:authorize>

<footer class="footer container-fluid">
    <div class="footerLg container visible-md visible-lg">
        <div class="col-lg-3 col-lg-3 col-sm-3"><img class='img-responsive' src="/resources/images/logo-gray.png"></div>

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
        <img class="col-sm-5 visible-sm" src="/resources/images/logo-gray.png">
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
<script src="/resources/js/account.js"></script>
<script src="http://code.jquery.com/jquery-latest.js" type="text/javascript"></script>
<script src="/resources/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script>
    $('#changePassword').hideShowPassword(false, true);

</script>

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
