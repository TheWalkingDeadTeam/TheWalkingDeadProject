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
                <ul class="nav navbar-nav">
                    <li class="scroll active"><a href="#home">Home</a></li>
                    <li class="scroll"><a href="#cta">Login</a></li>
                    <li class="scroll"><a href="#about">About</a></li>
                    <li class="scroll"><a href="#get-in-touch">Contact</a></li>
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
                                    The mission of our Training Center is effective education of young specialists for
                                    future employment in Netcracker Systems. </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--/.item-->
        <div class="item" style="background-image: url(/resources/images/test/slider/bg1.jpg);">
            <div class="slider-inner">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="carousel-content">
                                <h2><span>Netcracker</span> focused on your most important step... the next one</h2>
                                <p>Компания NetCracker Technology является мировым лидером в области создания и
                                    внедрения комплексных решений для провайдеров услуг связи</p>
                                <a class="btn btn-primary btn-lg" href="#about">Read More</a>

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
        <%--<div class="reg registration">--%>
        <%--<div class="layout"></div>--%>
        <%--<sec:authorize access="!isAuthenticated()">--%>
        <%--<form id="user">--%>
        <%--<div id="messageRegistration"></div>--%>
        <%--<div class="row container-fluid reg-head">--%>
        <%--<div class="col-lg-6 col-md-8 col-sm-9 col-xs-9">--%>
        <%--<h2 class="form-signin-heading"><spring:message code="locale.registration"/></h2>--%>
        <%--</div>--%>
        <%--<div class="col-lg-6 col-md-4 col-sm-3 col-xs-3 ">--%>
        <%--<i class="material-icons closeico"><span class="closebtn">highlight_off</span></i>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<input id="name" name="name" class="form-control" placeholder=<spring:message code="locale.name"/> type="text" value="">--%>
        <%--<div class="correct-name"></div>--%>
        <%--<input id="surname" name="surname" class="form-control" placeholder=<spring:message code="locale.surname"/> type="text" value="">--%>
        <%--<div class="correct-surname"></div>--%>
        <%--<input id="email" name="email" class="form-control" placeholder=<spring:message code="locale.email"/> type="text" value="">--%>
        <%--<div class="correct-email"></div>--%>
        <%--<input id="password" name="password" class="form-control login-field  login-field-password" placeholder=<spring:message code="locale.password"/> type="password"--%>
        <%--value="">--%>
        <%--<div class="correct-password"></div>--%>
        <%--<div class="g-recaptcha" data-sitekey="6LdZ1R8TAAAAAMwVjN-N-oTtZR51Li8QmKoSYEiF"></div>--%>
        <%--<button id="buttonRegistration" class="btn btn-lg btn-primary btn-block"><spring:message code="locale.register"/></button>--%>

        <%--</form>--%>
        <%--</sec:authorize>--%>
        <%--</div>--%>

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
                        <%--<a href="#slideForgotPass" type="button" style="display: none;" type="button" id="recpass"--%>
                           <%--class="btn btn-lg btn-primary btn-block recoverybtn"><spring:message code="locale.forgotPassword"/>--%>
                        <%--</a>--%>
                        <%--<label for="recpass"><spring:message code="locale.forgotPassword"/></label>--%>
                        <a href="#slideForgotPass" class="passRec"><spring:message code="locale.forgotPassword"/></a>
                    </form>
                </sec:authorize>


            </div>
        </div>

        <div id="" class="col-lg-5 col-md-7 col-sm-12 col-xs-12 col-centered">
            <jsp:include page="registration-slider.jsp"/>
        </div>

        <div id="" class="col-lg-5 col-md-7 col-sm-12 col-xs-12 col-centered"">
            <jsp:include page="forgot-password-slide.jsp"/>
        </div>

        <%--<div class="recovery registration">--%>
            <%--<div class="layout"></div>--%>
            <%--<sec:authorize access="!isAuthenticated()">--%>
                <%--<form id="stupidUser" action="/passwordRecovery">--%>
                    <%--<div id="passwordRecoveryMessage"></div>--%>
                    <%--<div class="row container-fluid recovery-head">--%>
                        <%--<div class="col-lg-6 col-md-8 col-sm-9 col-xs-9">--%>
                            <%--<h2 class="form-signin-heading"><spring:message code="locale.recoverPassword"/></h2>--%>
                        <%--</div>--%>
                        <%--<div class="col-lg-6 col-md-4 col-sm-3 col-xs-3 ">--%>
                            <%--<i class="material-icons closeico"><span class="closebtn">clear</span></i>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<input id="userEmail" name="email" class="form-control" placeholder=--%>
                        <%--<spring:message code="locale.email"/> type="text"--%>
                           <%--value="">--%>
                    <%--<div class="correct-email"></div>--%>
                    <%--<button id="buttonRecoverPassword" class="btn btn-lg btn-primary btn-block"><spring:message--%>
                            <%--code="locale.sendRequest"/></button>--%>
                <%--</form>--%>
            <%--</sec:authorize>--%>
        <%--</div>--%>

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
            <h2 class="section-title text-center wow fadeInDown">Welcome to Netcracker training portal</h2>
            <p class="text-center wow fadeInDown">

            <ul>
                <li>Ты уже сейчас сможешь учиться в университете и параллельно осваивать современные востребованные
                    технологии
                </li>
                <li>Получишь первый опыт командной работы на учебных проектах, очень близких к реальным, с помощью наших
                    опытных разработчиков
                </li>
                <li> Есть возможность получить первую работу задолго до окончания университета, не волнуясь при этом за
                    учебу
                </li>
                <li> Сможешь строить успешную карьеру в бурно развивающейся отрасли (IT/Телеком), участвовать в
                    разработке и внедрении сверхсложной сверхгибкой системы, востребованной сотнями крупнейших мировых
                    компаний
                </li>
                <li> Научишься принимать сложные технические и управленческие решения Получишь неоценимый опыт
                    бизнес-коммуникаций, работая непосредственно с заказчиком, летая в командировки по всему миру
                </li>
            </ul>
            </p>
        </div>


        <section id="features">
            <div class="container">
                <div class="section-header">
                    <h2 class="section-title text-center wow fadeInDown">Awesome Features</h2>
                    <p class="text-center wow fadeInDown">Вы можете выбрать одно из следующих направлений
                        <br>в своем нелог</p>
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
            <h2 class="section-title text-center wow fadeInDown">The training program</h2>
            <p class="text-center wow fadeInDown">
            <ul>
                <li>объектно-ориентированное программирование на Java</li>
                <li>проектирование реляционных баз данных в Oracle</li>
                <li>разработка на Java EE, технологии построения распределенных систем</li>
                <li>методы построения, технологии и протоколы современных сетей связи</li>
                <li>мастер-классы от сотрудников NetCracker</li>
                <li>учебные проекты под руководством кураторов</li>
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
                <h3 class="column-title">7 главных аргументов в пользу учебы и работы в NetCracker</h3>
                <p>

                <ul>
                    <li>NetCracker – компания с мировым именем</li>
                    <li>Лидер отрасли. О нас пишут крупнейшие издания, такие как Forbes, Mobile Europe, Connect World,
                        Vanillaplus, European communication.
                    </li>
                    <li>Глобальная экспертиза в области телеком</li>
                    <li>Инновационные решения</li>
                    <li>Клиенты NetCracker</li>
                    <ul>
                        <li>Многомиллионные операторы связи на 5 континентах</li>
                        <li>Компании из списка Fortune1000</li>
                        <li>Флагманы телекомрынка</li>
                    </ul>
                    <li>NetCracker – профессиональное и личностное развитие</li>
                    <ul>
                        <li>Курсы английского языка в офисе</li>
                        <li>Soft Skills – тренинги</li>
                        <li>Технические тренинги и семинары</li>
                        <li>Учебные центры NetCracker для студентов при ведущих технических ВУЗах Украины</li>
                    </ul>
                    <li>NetCracker – уникальная корпоративная культура</li>
                    <ul>
                        <li>Открытый диалог с руководством любого уровня. Отсутствие бюрократии и строгого дресс-кода
                        </li>
                        <li>Гибкий график работы для всех, включая студентов</li>
                        <li>Корпоративные мероприятия, спорт</li>
                        <li>Социальное обеспечение</li>
                        <li>Социальные проекты</li>
                    </ul>
                </ul>
            </div>
        </div>
    </div>
</section><!--/#about-->

<section id="work-process">
    <div class="container">
        <div class="section-header">
            <h2 class="section-title text-center wow fadeInDown">YOUR PATH</h2>
            <p class="text-center wow fadeInDown">Jedi way that you should pass to become part<br> of Netcracker team
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
            <h2 class="section-title text-center wow fadeInDown">Get in Touch</h2>
            <p class="text-center wow fadeInDown"> Below you can find contact information <br> We will be glad to answer
                all your questions</p>
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
