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
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Index Page</title>
    <!-- core CSS -->
    <link href="/resources/fonts" rel="stylesheet">
    <link href="/resources/css/test/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/css/test/font-awesome.min.css" rel="stylesheet">
    <link href="/resources/css/test/animate.min.css" rel="stylesheet">
    <link href="/resources/css/test/owl.carousel.css" rel="stylesheet">
    <link href="/resources/css/test/owl.transitions.css" rel="stylesheet">
    <link href="/resources/css/test/prettyPhoto.css.css" rel="stylesheet">
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

    <style>
        #img {
            display: block;
            position: relative;
            top: -540px;
            left: 45%;
            z-index: 105;
        }

        img {
            vertical-align: middle;
        }

        img {
            border: 0;
        }
    </style>

</head><!--/head-->

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
                <a class="navbar-brand" href="index.html"><img src="/resources/images/logo.png"
                                                               style="width: 210px;height: 58px;" alt="logo"></a>
            </div>

            <div class="collapse navbar-collapse navbar-right">
                <ul class="nav navbar-nav">
                    <li class="scroll active"><a href="#home">Home</a></li>
                    <li class="scroll"><a href="#about">About</a></li>
                    <li class="scroll"><a href="#get-in-touch">Contact</a></li>
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

                                <a class="btn btn-primary btn-lg" href="#">Registration</a>
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
                                <a class="btn btn-primary btn-lg" href="#">Read More</a>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div><!--/.owl-carousel-->
</section><!--/#main-slider-->


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
                    <h3>PRACTISE</h3>
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
                        <h3>Contact Info</h3>

                        <address>
                            <strong>Univercity Office Park III</strong><br>
                            95 Sawyer Road<br>
                            Waltham, MA 02453 USA<br>
                            <abbr title="Phone">P:</abbr> 1-781-419-3300
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
                &copy; 2016 Netcracker. Designed by <a target="_blank" href="http://shapebootstrap.net/"
                                                       title="Free Twitter Bootstrap WordPress Themes and HTML templates">ShapeBootstrap</a>
            </div>
            <div class="col-sm-6">
                <ul class="social-icons">
                    <li><a href="#"><i class="fa fa-facebook"></i></a></li>
                    <li><a href="#"><i class="fa fa-twitter"></i></a></li>
                    <li><a href="#"><i class="fa fa-google-plus"></i></a></li>
                    <li><a href="#"><i class="fa fa-pinterest"></i></a></li>
                    <li><a href="#"><i class="fa fa-dribbble"></i></a></li>
                    <li><a href="#"><i class="fa fa-behance"></i></a></li>
                    <li><a href="#"><i class="fa fa-flickr"></i></a></li>
                    <li><a href="#"><i class="fa fa-youtube"></i></a></li>
                    <li><a href="#"><i class="fa fa-linkedin"></i></a></li>
                    <li><a href="#"><i class="fa fa-github"></i></a></li>
                </ul>
            </div>
        </div>
    </div>
</footer><!--/#footer-->

<script src="/resources/js/test/jquery.js"></script>
<script src="/resources/js/test/bootstrap.min.js"></script>
<script src="http://maps.google.com/maps/api/js?sensor=true"></script>
<script src="/resources/js/test/owl.carousel.min.js"></script>
<script src="/resources/js/test/mousescroll.js"></script>
<script src="/resources/js/test/smoothscroll.js"></script>
<script src="/resources/js/test/jquery.prettyPhoto.js"></script>
<script src="/resources/js/test/jquery.isotope.min.js"></script>
<script src="/resources/js/test/jquery.inview.min.js"></script>
<script src="/resources/js/test/wow.min.js"></script>
<script src="/resources/js/test/main.js"></script>
</body>
</html>
