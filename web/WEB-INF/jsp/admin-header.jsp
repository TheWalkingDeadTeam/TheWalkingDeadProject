<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 04.05.2016
  Time: 1:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<header class="demo-header mdl-layout__header mdl-color--grey-100 mdl-color-text--grey-600">
    <div class="mdl-layout__header-row">
        <%--   <span class="mdl-layout-title">Netcracker</span>--%>
        <a class="navbar-brand brand-img" href="">
            <img src='/resources/images/logo.png' alt="Logo" class="header-img">
        </a>
        <div class="mdl-layout-spacer"></div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable">
            <%--            <label class="mdl-button mdl-js-button mdl-button--icon" for="search">
                            <i class="material-icons">search</i>
                        </label>--%>
            <%--            <div class="mdl-textfield__expandable-holder">
                            <form ng-submit="searchFiltr()"><input class="mdl-textfield__input" type="text" id="search"></form>
                            <label class="mdl-textfield__label" for="search">Enter your query...</label>
                        </div>
                    </div>
                    <button class="mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--icon" id="hdrbtn">
                        <i class="material-icons">more_vert</i>
                    </button>
                    <ul class="dropdown-content" for="hdrbtn">
                        <li class="mdl-menu__item">About</li>
                        <li class="mdl-menu__item">Contact</li>
                        <li class="mdl-menu__item">Legal information</li>
                    </ul>--%>
        </div>
    </div>
</header>
<div class="demo-drawer mdl-layout__drawer mdl-color--blue-grey-900 mdl-color-text--blue-grey-50">
    <header class="demo-drawer-header">
        <%--
                <img width="100" height="100" src="/resources/images/user-photo.png" class="img-circle  ">
        --%>
        <div class="demo-avatar-dropdown">
            <sec:authentication var="principal" property="principal"/>
            <div class="user-panel">
                <div class="pull-left image">
                    <img width="100" height="100" src="/resources/images/rainbow.jpg" class="img-circle"
                         alt="User Image">
                </div>
                <div class="pull-left">
                    <p>
                        <sec:authorize access="isAuthenticated()">
                            ${principal.username}
                        </sec:authorize>
                    </p>
                    <a href="/account">
                        <i class="material-icons" role="presentation">account_circle</i></a>
                    <a href="/logout">
                        <i class="material-icons" role="presentation">power_settings_new</i></a>
                </div>
            </div>
            <%--
                        <div class="mdl-layout-spacer">${principal.username}
            --%>
            <%--               <button id="accbtn" class="mdl-button  mdl-js-b utton mdl-js-ripple-effect mdl-button--icon">
                               <i class="material-icons" role="presentation">arrow_drop_down</i>
                               <span class="visuallyhidden">Accounts</span>
                           </button>
                       </div>
                       <ul class="mdl-menu mdl-menu--bottom-right mdl-js-menu mdl-js-ripple-effect" for="accbtn">
                           <li class="mdl-menu__item">ion@gmail.com</li>
                           <li class="mdl-menu__item">info@example.com</li>
                       </ul>--%>
        </div>
    </header>
    <nav class="demo-navigation mdl-navigation mdl-color--blue-grey-800">
        <div class="mdl-layout-spacer">
            <a class="mdl-navigation__link" href="/admin"><i class="mdl-color-text--blue-grey-400 material-icons"
                                                             role="presentation">help_outline</i>Menu</a>
        </div>
        <a class="mdl-navigation__link" href="/admin/scheduler"><i class="mdl-color-text--blue-grey-400 material-icons"
                                                                   role="presentation">schedule</i>Schedule Planning</a>
        <a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons"
                                                   role="presentation">inbox</i>Form Template</a>
        <a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons"
                                                   role="presentation">local_offer</i>Registration Period</a>
        <a class="mdl-navigation__link" href="/admin/students"><i class="mdl-color-text--blue-grey-400 material-icons"
                                                                  role="presentation">people</i>Student List</a>
        <a class="mdl-navigation__link" href="/admin/interviewers"><i
                class="mdl-color-text--blue-grey-400 material-icons"
                role="presentation">people</i>Interviewer List</a>
        <a class="mdl-navigation__link" href="/admin/create"><i class="mdl-color-text--blue-grey-400 material-icons"
                                                                role="presentation">people</i>Create Interviewer</a>
        <a class="mdl-navigation__link" href="/admin/mail-template"><i
                class="mdl-color-text--blue-grey-400 material-icons"
                role="presentation">mail</i>Edit Letter Templates</a>
        <a class="mdl-navigation__link" href="/admin/enroll-session"><i
                class="mdl-color-text--blue-grey-400 material-icons"
                role="presentation">schedule</i>Enrollment History</a>
        <a class="mdl-navigation__link" href="/admin/report"><i
                class="mdl-color-text--blue-grey-400 material-icons"
                role="presentation">show_chart</i>Statistic & Reports</a>
    </nav>
</div>


