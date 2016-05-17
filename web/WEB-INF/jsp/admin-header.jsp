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
        </div>
    </div>
</header>
<div class="demo-drawer mdl-layout__drawer mdl-color--blue-grey-900 mdl-color-text--blue-grey-50">
    <header class="demo-drawer-header">
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
        </div>
    </header>
    <nav class="demo-navigation mdl-navigation mdl-color--blue-grey-800">
        <div class="mdl-layout-spacer">
            <a class="mdl-navigation__link" href="/admin"><i class="mdl-color-text--blue-grey-400 material-icons"
                                                             role="presentation">help_outline</i>Menu</a>
        </div>
        <a class="mdl-navigation__link" href="/admin/scheduler"><i class="mdl-color-text--blue-grey-400 material-icons"
                                                                   role="presentation">schedule</i>Schedule Planning</a>
        <a class="mdl-navigation__link" href="/admin/edit-form"><i class="mdl-color-text--blue-grey-400 material-icons"
                                                                   role="presentation">inbox</i>Form Template</a>
        <a class="mdl-navigation__link" href="/admin/cessettings"><i
                class="mdl-color-text--blue-grey-400 material-icons"
                role="presentation">local_offer</i>Registration Period</a>
        <a class="mdl-navigation__link" href="/admin/students"><i class="mdl-color-text--blue-grey-400 material-icons"
                                                                  role="presentation">people</i>Student List</a>
        <a class="mdl-navigation__link" href="/admin/users"><i class="mdl-color-text--blue-grey-400 material-icons"
                                                               role="presentation">people</i>Users List</a>
        <a class="mdl-navigation__link" href="/admin/interviewee"><i
                class="mdl-color-text--blue-grey-400 material-icons"
                role="presentation">people</i>Interviewee List</a>
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


