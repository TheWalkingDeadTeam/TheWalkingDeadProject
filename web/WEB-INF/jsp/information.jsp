<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%--
  Created by IntelliJ IDEA.
  User: Neltarion
  Date: 05.05.2016
  Time: 16:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Info about us</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/png" sizes="32x32" href="/resources/images/ico.png"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="icon" type="image/png" sizes="32x32" href="/images/ico.png">
    <link rel="stylesheet" type="text/css" href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="resources/css/style-profile.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="resources/css/media-profile.css" rel="stylesheet">
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
                    <sec:authorize access="hasRole('ROLE_STUDENT')">
                        <li><a href="/account/profile">Profile</a></li>
                    </sec:authorize>
                    <li><a href="/logout">Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>
</header>

<div id="workspace" style="margin: 20px;">



    <h3>
        Компания NetCracker Technology является мировым лидером в области создания и внедрения комплексных решений для провайдеров услуг связи
    </h3>





    <h3>
        <br/>Твои возможности:
    </h3>
    <ul>
        <li>Ты уже сейчас сможешь учиться в университете и параллельно осваивать современные востребованные технологии</li>
        <li>Получишь первый опыт командной работы на учебных проектах, очень близких к реальным, с помощью наших опытных разработчиков</li>
        <li>Есть возможность получить первую работу задолго до окончания университета, не волнуясь при этом за учебу</li>
        <li>Сможешь строить успешную карьеру в бурно развивающейся отрасли (IT/Телеком), участвовать в разработке и внедрении сверхсложной сверхгибкой системы, востребованной <a href="http://www.netcracker.com/en/customers-folder/customers.html">сотнями крупнейших мировых компаний</a></li>
        <li>Научишься принимать сложные технические и управленческие решения</li>
        <li>Получишь неоценимый опыт бизнес-коммуникаций, работая непосредственно с заказчиком, летая в командировки по всему миру</li>
    </ul>

    <h3>
        <br/>7 главных аргументов в пользу учебы и работы в NetCracker:
    </h3>
    <ol>
        <li>
            <b>NetCracker – компания с мировым именем</b>
        </li>

        <li>
            <b>Лидер отрасли.</b> О нас пишут крупнейшие издания, такие как
            <b><i>Forbes, Mobile Europe, Connect World, Vanillaplus, European communication</i></b>.
        </li>

        <li>
            <b>Глобальная экспертиза в области телеком</b>
        </li>

        <li>
            <b>Инновационные решения</b>
        </li>
        <li>
            <b>Клиенты NetCracker</b>
            <ul>
                <li>Многомиллионные операторы связи на 5 континентах</li>
                <li>Компании из списка Fortune1000</li>
                <li>Флагманы телекомрынка</li>
            </ul>
        </li>
        <li>
            <b>NetCracker – профессиональное и личностное развитие</b><br/>
            <ul>
                <li>Курсы английского языка в офисе</li>
                <li>Soft Skills – тренинги</li>
                <li>Технические тренинги и семинары</li>
                <li>Учебные центры NetCracker для студентов при ведущих технических  ВУЗах Украины</li>
            </ul>
        </li>

        <li>
            <b>NetCracker – уникальная корпоративная культура</b><br/>
            <ul>
                <li>Открытый диалог с руководством любого уровня. Отсутствие бюрократии и строгого дресс-кода</li>
                <li>Гибкий график работы для всех, включая студентов</li>
                <li>Корпоративные мероприятия, спорт</li>
                <li>Социальное обеспечение</li>
                <li>Социальные проекты</li>
            </ul>
        </li>
    </ol>



    <p style="color: #888; font-size: 10pt;">
        <br>
        С нами можно связаться:<br>
        По организационным вопросам — <a href="mailto:KievCareers@NetCracker.com">KievCareers@NetCracker.com</a><br>
        По проблемам или вопросам по работе сайта — <a href="mailto:webmaster.nnc@gmail.com">webmaster.nnc@gmail.com</a>
    </p>

</div>
</div>






<footer class="footer container-fluid">
    <div class="footerLg container visible-md visible-lg">
        <div class="col-lg-3 col-lg-3 col-sm-3"><img class='img-responsive' src="resources/images/logo-gray.png"></div>

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
        <div class="col-sm-5 visible-sm" >
            <img src="resources/images/logo-gray.png">
        </div>
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
</body>
</html>
