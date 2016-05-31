<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 04.05.2016
  Time: 1:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<header class="demo-header mdl-layout__header mdl-color--grey-100 mdl-color-text--grey-600">
    <div class="mdl-layout__header-row">
        <%--   <span class="mdl-layout-title">Netcracker</span>--%>
        <a class="navbar-brand brand-img" href="">
            <img src='/resources/images/logo.png' alt="Logo" class="header-img">
        </a>
            <a href="?lang=en"><img src="/resources/images/en.png"></a> | <a href="?lang=uk"><img src="/resources/images/ua.png"></a>
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
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="padding-left: 0px; padding-right: 0px;">
                    <p style="margin-right: 7px; float: right; margin-top: 0px; margin-bottom: 0px; margin-left: 0px; padding-left: 0px; padding-right: 0px;">
                        <sec:authorize access="isAuthenticated()">
                            ${principal.username}
                        </sec:authorize>
                    </p>
                </div>
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="padding-left: 0px; padding-right: 0px;">
                    <div class=" image col-lg-6 col-md-6 col-sm-6 col-xs-6" style="display: inline-block; padding-left: 0px; padding-right: 0px;">
                        <%--                    <img width="100" height="100" src="/resources/images/rainbow.jpg" class="img-circle"
                                                 alt="User Image">--%>
                        <img id="photo_img" src="/getPhoto" alt="User's photo" width="100" height="100"
                             onError="this.src='/resources/images/rainbow.jpg'" class="profile-photo img-circle">
                    </div>
                    <div style="float: right; margin-top: 5px; padding-left: 0px; padding-right: 0px;" class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                        <a href="/account" style="">
                            <i class="material-icons" role="presentation" style="font-size: 40px;">account_circle</i></a>
                        <a href="/logout">
                            <i class="material-icons" role="presentation" style="font-size: 40px;">power_settings_new</i></a>
                    </div>
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
            <a id="menu" class="mdl-navigation__link" href="/admin"><i class="mdl-color-text--blue-grey-400 material-icons"
                                                             role="presentation">view_module</i><spring:message code="locale.Menu"/></a>
        </div>
        <a class="mdl-navigation__link" href="/admin/scheduler"><i class="mdl-color-text--blue-grey-400 material-icons"
                                                                   role="presentation">date_range</i><spring:message code="locale.shedulePlanning"/></a>

        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <a class="mdl-navigation__link" href="/admin/edit-form"><i
                    class="mdl-color-text--blue-grey-400 material-icons"
                    role="presentation">assignment_ind</i><spring:message code="locale.formTemplate"/></a>
        </sec:authorize>
        <a class="mdl-navigation__link" href="/admin/cessettings"><i
                class="mdl-color-text--blue-grey-400 material-icons"
                role="presentation">alarm</i><spring:message code="locale.registrationPeriod"/></a>
        <a class="mdl-navigation__link" href="/admin/students"><i class="mdl-color-text--blue-grey-400 material-icons"
                                                                  role="presentation">supervisor_account</i><spring:message code="locale.appList"/></a>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <a class="mdl-navigation__link" href="/admin/users"><i class="mdl-color-text--blue-grey-400 material-icons"
                                                                   role="presentation">supervisor_account</i><spring:message code="locale.userList"/></a>
        </sec:authorize>
        <a class="mdl-navigation__link" href="/admin/interviewee"><i
                class="mdl-color-text--blue-grey-400 material-icons"
                role="presentation">supervisor_account</i><spring:message code="locale.IntervieweeList"/></a>
        <a class="mdl-navigation__link" href="/admin/interviewers"><i
                class="mdl-color-text--blue-grey-400 material-icons"
                role="presentation">supervisor_account</i><spring:message code="locale.InterviewerList"/></a>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <a class="mdl-navigation__link" href="/admin/create-user"><i class="mdl-color-text--blue-grey-400 material-icons"
                                                                    role="presentation">portrait</i><spring:message code="locale.createUser"/></a>
        </sec:authorize>
        <a class="mdl-navigation__link" href="/admin/mail-template"><i
                class="mdl-color-text--blue-grey-400 material-icons"
                role="presentation">contact_mail</i><spring:message code="locale.editMailTemplates"/></a>
        <a class="mdl-navigation__link" href="/admin/enroll-session"><i
                class="mdl-color-text--blue-grey-400 material-icons"
                role="presentation">history</i><spring:message code="locale.EnrollmentHistory"/></a>
        <a class="mdl-navigation__link" href="/admin/report"><i
                class="mdl-color-text--blue-grey-400 material-icons"
                role="presentation">show_chart</i><spring:message code="locale.statistics"/></a>
    </nav>
</div>


