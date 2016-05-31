<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Alexander
  Date: 28.05.2016
  Time: 0:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Netcracker</title>
    <link rel="icon" type="image/png" sizes="32x32" href="/resources/images/ico.png"/>
    <%--<link rel="stylesheet" type="text/css" href="/resources/css/reset.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="/resources/css/styles.css"/>--%>
    <link rel="stylesheet" type="text/css" href="/resources/css/registration.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.css"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet"/>
    <script src='https://www.google.com/recaptcha/api.js'></script>
    <!-- core CSS -->
    <%--<link href="/resources/css/fonts" rel="stylesheet">--%>
    <link href="/resources/css/test/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/css/test/font-awesome.min.css" rel="stylesheet">
    <link href="/resources/css/test/animate.min.css" rel="stylesheet">
    <link href="/resources/css/test/owl.carousel.css" rel="stylesheet">
    <link href="/resources/css/test/owl.transitions.css" rel="stylesheet">
    <link href="/resources/css/test/prettyPhoto.css" rel="stylesheet">
    <link href="/resources/css/test/main.css" rel="stylesheet">
    <link href="/resources/css/test/responsive.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="/resources/js/test/html5shiv.js"></script>
    <script src="/resources/js/test/respond.min.js"></script>
    <![endif]-->
    <link rel="shortcut icon" href="/resources/images/test/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144"
          href="/resources/images/test/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114"
          href="/resources/images/test/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72"
          href="/resources/images/test/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="/resources/images/test/ico/apple-touch-icon-57-precomposed.png">
    <script src="http://code.jquery.com/jquery-latest.js"></script>

</head><!--/head-->

<style>
    .col-centered {
        float: none;
        margin: 0 auto;
    }
</style>

<body id="home" class="homepage">

<header id="header">
    <nav id="main-menu" class="navbar navbar-default navbar-fixed-top" role="banner">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" style="padding: 0px;" href="/"><img src="/resources/images/logo.png"
                                                                            style="width: 256px;height: 55px;"
                                                                            alt="logo"></a>
            </div>

            <div class="collapse navbar-collapse navbar-right">
                <a href="?lang=en"><img src="/resources/images/en.png"></a> | <a href="?lang=uk"><img src="/resources/images/ua.png"></a>
                <ul class="nav navbar-nav">
                    <li class="scroll active"><a href="#home"><spring:message code="locale.home"/></a></li>
                    <li class="scroll"><a href="#cta"><spring:message code="locale.login"/></a></li>
                    <li class="scroll"><a href="#about"><spring:message code="locale.info"/></a></li>
                    <li class="scroll"><a href="#get-in-touch"><spring:message code="locale.contacts"/></a></li>
                    <sec:authorize access="hasAnyRole('ROLE_HR','ROLE_DEV','ROLE_BA')">
                        <li><a href="/interviewee"><spring:message code="locale.interviewee"/></a></li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_STUDENT')">
                        <sec:authentication var="principal" property="principal"/>
                        <li><a href="/profile?${principal.id}"><spring:message code="locale.profile"/></a></li>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <li><a href="/logout"><spring:message code="locale.logout"/></a></li>
                    </sec:authorize>
                </ul>
            </div>
        </div><!--/.container-->
    </nav><!--/nav-->
</header><!--/header-->

<section id="main-slider">
    <div class="owl-carousel">
        <!--/.item-->
        <div class="item" style="background-image: url(/resources/images/test/slider/bg2.jpg);">
            <div class="slider-inner">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="carousel-content">
                                <h2>You are welcome to the official portal of the <span>Netcracker</span> Training
                                    Center! </h2>
                                <p>
                                    <spring:message code="locale.mission"/></p>
                                <a class="btn btn-primary btn-lg" href="#about"><spring:message code="locale.readMore"/></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--/.item-->
        <div class="item" style="background-image: url(/resources/images/bg1.jpg);">
            <div class="slider-inner">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="carousel-content">
                                <h2><span>Netcracker</span> focused on your most important step... the next one</h2>
                                <p><spring:message code="locale.complexSolutions"/></p>
                                <a class="btn btn-primary btn-lg" href="#about"><spring:message code="locale.readMore"/></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div><!--/.owl-carousel-->
</section><!--/#main-slider-->

<section id="cta" class="wow fadeIn">
    <div class="container">

        <div class="row ">
            <div class="col-lg-5 col-md-5 col-xs-12 col-sm-12 col-centered">
                <sec:authorize access="!isAuthenticated()">
                    <form>
                        <div id="messageSignIn"></div>
                        <h2 class="form-signin-heading"><spring:message code="locale.pleaseSignIn"/></h2>
                        <input id="j_username" type="text" class="form-control" name="j_username"
                               placeholder=
                                   "<spring:message code="locale.email"/>" required>
                        <input id="j_password" type="password" class="form-control login-field  login-field-password"
                               name="j_password" placeholder=
                                   <spring:message code="locale.password"/>
                                       required>
                        <button id="buttonSignIn" style="margin-top: 3px;"
                                class="btn btn-lg btn-primary btn-block signbtn" type="submit"><spring:message
                                code="locale.signin"/>
                        </button>
                        <a href="#slideDown" type="button" style="margin-top: 3px;"
                           class="btn btn-lg btn-primary btn-block regbut" >
                            <spring:message code="locale.registration"/></a>
                        <a href="#slideForgotPass" class="passRec"><spring:message code="locale.forgotPassword"/></a>
                    </form>
                </sec:authorize>


            </div>
        </div>

        <div class="col-lg-5 col-md-7 col-sm-12 col-xs-12 col-centered">
            <jsp:include page="registration-slider.jsp"/>
        </div>

        <div class="col-lg-5 col-md-7 col-sm-12 col-xs-12 col-centered">
            <jsp:include page="forgot-password-slide.jsp"/>
        </div>

        <div class="row">
            <div class="inputBox col-lg-4 col-md-4 col-sm-12 col-xs-12">
                <sec:authorize access="isAuthenticated()">
                    <div class="alert alert-info" role="alert">
                        <div id="messageCheckPassword"></div>
                        <form>
                            <input id="changePassword" name="password" class="form-control" type="password" value=""
                                   placeholder=<spring:message code="locale.password"/>>
                            <button id="buttonChangePassword" class="btn btn-lg btn-primary btn-block changebtn">
                                <spring:message code="locale.changePassword"/>
                            </button>
                        </form>
                    </div>
                </sec:authorize>
            </div>
        </div>
    </div>
</section><!--/#cta-->

<section id="about">
    <div class="container">

        <div class="section-header">
            <h2 class="section-title text-center wow fadeInDown"><spring:message code="locale.welcome"/></h2>
            <p class="text-center wow fadeInDown">

            <ul>
                <li><spring:message code="locale.pos1"/>
                </li>
                <li><spring:message code="locale.pos2"/>
                </li>
                <li><spring:message code="locale.pos3"/>
                </li>
                <li><spring:message code="locale.pos4"/>
                </li>
                <li><spring:message code="locale.pos5"/>
                </li>
            </ul>
            </p>
        </div>


        <section id="features">
            <div class="container">
                <div class="section-header">
                    <h2 class="section-title text-center wow fadeInDown"><spring:message code="locale.awesome"/></h2>
                </div>
                <div class="row">
                    <div class="col-lg-4 wow fadeInLeft">
                        <img class="img-responsive" src="/resources/images/partnership-stream.png" alt="">
                    </div>
                    <div class="col-sm-6">
                        <div class="media service-box wow fadeInRight">
                            <div class="pull-left">
                                <i class="fa fa-line-chart"></i>
                            </div>
                            <div class="media-body">
                                <h4 class="media-heading">UX design</h4>
                                <p>Backed by some of the biggest names in the industry, Firefox OS is an open platform
                                    that fosters greater</p>
                            </div>
                        </div>

                        <div class="media service-box wow fadeInRight">
                            <div class="pull-left">
                                <i class="fa fa-cubes"></i>
                            </div>
                            <div class="media-body">
                                <h4 class="media-heading">UI design</h4>
                                <p>Backed by some of the biggest names in the industry, Firefox OS is an open platform
                                    that fosters greater</p>
                            </div>
                        </div>

                        <div class="media service-box wow fadeInRight">
                            <div class="pull-left">
                                <i class="fa fa-pie-chart"></i>
                            </div>
                            <div class="media-body">
                                <h4 class="media-heading">SEO Services</h4>
                                <p>Backed by some of the biggest names in the industry, Firefox OS is an open platform
                                    that fosters greater</p>
                            </div>
                        </div>

                        <div class="media service-box wow fadeInRight">
                            <div class="pull-left">
                                <i class="fa fa-pie-chart"></i>
                            </div>
                            <div class="media-body">
                                <h4 class="media-heading">SEO Services</h4>
                                <p>Backed by some of the biggest names in the industry, Firefox OS is an open platform
                                    that fosters greater</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>


        <div class="section-header">
            <h2 class="section-title text-center wow fadeInDown"><spring:message code="locale.trainingProgram"/></h2>
            <p class="text-center wow fadeInDown">
            <ul>
                <li><spring:message code="locale.oop"/></li>
                <li><spring:message code="locale.databases"/></li>
                <li><spring:message code="locale.javaEE"/></li>
                <li><spring:message code="locale.constructionMethods"/></li>
                <li><spring:message code="locale.master"/></li>
                <li><spring:message code="locale.curators"/></li>
            </ul>
            </p>
        </div>

        <div class="row">
            <div class="col-sm-6 wow fadeInLeft">
                <h3 class="column-title">Focused forward</h3>
                <!-- 16:9 aspect ratio -->
                <div class="embed-responsive embed-responsive-16by9">
                    <iframe src="http://embed.wistia.com/deliveries/67c278bea45da3e1984cd01c4cecf4ca4b9a3467/file.mp4?autoPlay=true"
                            allowtransparency="true" frameborder="0" scrolling="no" class="wistia_embed"
                            name="wistia_embed"
                            id="video" allowfullscreen mozallowfullscreen webkitallowfullscreen oallowfullscreen
                            msallowfullscreen width="640" height="360">
                    </iframe>

                </div>
            </div>

            <div class="col-sm-6 wow fadeInRight">
                <h3 class="column-title"> <spring:message code="locale.arguments"/></h3>
                <p>

                <ul>
                    <li> <spring:message code="locale.arg1"/></li>
                    <li>  <spring:message code="locale.arg2"/>
                    </li>
                    <li> <spring:message code="locale.arg3"/></li>
                    <li> <spring:message code="locale.arg4"/></li>
                    <li> <spring:message code="locale.arg5"/></li>
                    <ul>
                        <li> <spring:message code="locale.arg51"/></li>
                        <li> <spring:message code="locale.arg52"/></li>
                        <li> <spring:message code="locale.arg53"/></li>
                    </ul>
                    <li> <spring:message code="locale.arg6"/></li>
                    <ul>
                        <li> <spring:message code="locale.arg61"/></li>
                        <li> <spring:message code="locale.arg62"/></li>
                        <li> <spring:message code="locale.arg63"/></li>
                        <li> <spring:message code="locale.arg64"/></li>
                    </ul>
                    <li> <spring:message code="locale.arg7"/></li>
                    <ul>
                        <li> <spring:message code="locale.arg71"/>
                        </li>
                        <li> <spring:message code="locale.arg72"/></li>
                        <li> <spring:message code="locale.arg73"/></li>
                        <li> <spring:message code="locale.arg74"/></li>
                        <li> <spring:message code="locale.arg75"/></li>
                    </ul>
                </ul>
            </div>
        </div>
    </div>
</section><!--/#about-->

<section id="work-process">
    <div class="container">
        <div class="section-header">
            <h2 class="section-title text-center wow fadeInDown"> <spring:message code="locale.path"/></h2>
            <p class="text-center wow fadeInDown"> <spring:message code="locale.jedi"/>
            </p>
        </div>

        <div class="row text-center">
            <div class="col-md-2 col-md-4 col-xs-6">
                <div class="wow fadeInUp" data-wow-duration="400ms" data-wow-delay="0ms">
                    <div class="icon-circle">
                        <span>1</span>
                        <i class="fa fa-file-text fa-2x"></i>
                    </div>
                    <h3>CV</h3>
                </div>
            </div>
            <div class="col-md-2 col-md-4 col-xs-6">
                <div class="wow fadeInUp" data-wow-duration="400ms" data-wow-delay="100ms">
                    <div class="icon-circle">
                        <span>2</span>
                        <i class="fa fa-bullhorn fa-2x"></i>
                    </div>
                    <h3>INTERVIEW</h3>
                </div>
            </div>
            <div class="col-md-2 col-md-4 col-xs-6">
                <div class="wow fadeInUp" data-wow-duration="400ms" data-wow-delay="200ms">
                    <div class="icon-circle">
                        <span>3</span>
                        <i class="fa fa-graduation-cap fa-2x"></i>
                    </div>
                    <h3>COURSES</h3>
                </div>
            </div>
            <div class="col-md-2 col-md-4 col-xs-6">
                <div class="wow fadeInUp" data-wow-duration="400ms" data-wow-delay="300ms">
                    <div class="icon-circle">
                        <span>4</span>
                        <i class="fa fa-users fa-2x"></i>
                    </div>
                    <h3>PRACTICE</h3>
                </div>
            </div>
            <div class="col-md-2 col-md-4 col-xs-6">
                <div class="wow fadeInUp" data-wow-duration="400ms" data-wow-delay="400ms">
                    <div class="icon-circle">
                        <span>5</span>
                        <i class="fa fa-cubes fa-2x"></i>
                    </div>
                    <h3>PROJECT</h3>
                </div>
            </div>
            <div class="col-md-2 col-md-4 col-xs-6">
                <div class="wow fadeInUp" data-wow-duration="400ms" data-wow-delay="500ms">
                    <div class="icon-circle">
                        <span>6</span>
                        <i class="fa fa-space-shuttle fa-2x"></i>
                    </div>
                    <h3>JOB</h3>
                </div>
            </div>
        </div>
    </div>
</section><!--/#work-process-->


<section id="get-in-touch">
    <div class="container">
        <div class="section-header">
            <h2 class="section-title text-center wow fadeInDown"> <spring:message code="locale.getInTouch"/></h2>
            <p class="text-center wow fadeInDown"> <spring:message code="locale.canFind"/> <br>  <spring:message code="locale.glad"/></p>
        </div>
    </div>
</section><!--/#get-in-touch-->


<section id="contact">
    <div id="google-map" style="height:650px" data-latitude="50.420467" data-longitude="30.529028"></div>
    <div class="container-wrapper">
        <div class="container">
            <div class="row">
                <div class="col-sm-4 col-sm-offset-8">
                    <div class="contact-form">
                        <h3>Headquarters</h3>

                        <address>
                            <strong>Univercity Office Park III</strong><br>
                            95 Sawyer Road<br>
                            Waltham, MA 02453 USA<br>
                            <abbr title="Phone">P:</abbr> 1-781-419-3300
                        </address>

                    </div>

                    <div class="contact-form">
                        <h3>Kyiv office</h3>

                        <address>
                            вулиця Патріса Лумумби,4/6В<br>
                            Київ, 33848 UA<br>
                            <abbr title="Phone">P:</abbr> <a href="tel:+380442388727">044 238-8727</a>
                        </address>

                    </div>


                </div>
            </div>
        </div>
    </div>
</section><!--/#bottom-->

<footer id="footer">
    <div class="container">
        <div class="row">
            <div class="col-sm-6">
                &copy; 2016 Netcracker. Designed by <a target="_blank" href="http://nc-training.tk/"
                                                       title="Walking Dead Team">Walking Dead Team</a>
            </div>
            <div class="col-sm-6">
                <ul class="social-icons">
                    <li><a href="https://www.facebook.com/NetCrackerTech/"><i class="fa fa-facebook"></i></a></li>
                    <li><a href="https://twitter.com/netcrackertech"><i class="fa fa-twitter"></i></a></li>
                    <li><a href="https://plus.google.com/112244362035655933650/about"><i class="fa fa-google-plus"></i></a>
                    </li>
                    <li><a href="https://www.flickr.com/photos/tags/netcracker/"><i class="fa fa-flickr"></i></a></li>
                    <li><a href="https://www.youtube.com/user/SmartRevenue"><i class="fa fa-youtube"></i></a></li>
                    <li><a href="https://ru.linkedin.com/company/netcracker"><i class="fa fa-linkedin"></i></a></li>
                    <li><a href="https://github.com/TheWalkingDeadTeam/TheWalkingDeadProject"><i
                            class="fa fa-github"></i></a></li>
                </ul>
            </div>
        </div>
    </div>
</footer><!--/#footer-->

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/js/test/bootstrap.min.js"></script>
<script src="/resources/js/registration-slide.js"></script>
<script src="http://maps.google.com/maps/api/js?sensor=true"></script>
<script src="/resources/js/test/owl.carousel.min.js"></script>
<script src="/resources/js/test/mousescroll.js"></script>
<script src="/resources/js/test/smoothscroll.js"></script>
<script src="/resources/js/test/jquery.prettyPhoto.js"></script>
<script src="/resources/js/test/jquery.isotope.min.js"></script>
<script src="/resources/js/test/jquery.inview.min.js"></script>
<script src="/resources/js/test/wow.min.js"></script>
<script src="/resources/js/test/main.js"></script>

<script src="/resources/js/changePassword.js"></script>
<script src="/resources/js/login.js"></script>
<script src="/resources/js/logout.js"></script>
<%--<script src="/resources/bootstrap/js/bootstrap.js"></script>--%>
<script src="/resources/js/registration.js"></script>
<script src="/resources/js/passwordRecovery.js"></script>
<script src="/resources/js/hideShowPassword.min.js"></script>
</body>
</html>
