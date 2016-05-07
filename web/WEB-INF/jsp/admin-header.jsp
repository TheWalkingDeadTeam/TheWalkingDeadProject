<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 04.05.2016
  Time: 1:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header class="demo-header mdl-layout__header mdl-color--grey-100 mdl-color-text--grey-600">
    <div class="mdl-layout__header-row">
        <span class="mdl-layout-title">Netcracker</span>
        <div class="mdl-layout-spacer"></div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable">
            <label class="mdl-button mdl-js-button mdl-button--icon" for="search">
                <i class="material-icons">search</i>
            </label>
            <div class="mdl-textfield__expandable-holder">
                <input ng-model="searchFilt" ng-click="searchFiltr()" class="mdl-textfield__input" type="text" id="search">
                <label class="mdl-textfield__label" for="search">Enter your query...</label>
            </div>
        </div>
        <button class="mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--icon" id="hdrbtn">
            <i class="material-icons">more_vert</i>
        </button>
        <ul class="mdl-menu mdl-js-menu mdl-js-ripple-effect mdl-menu--bottom-right" for="hdrbtn">
            <li class="mdl-menu__item">About</li>
            <li class="mdl-menu__item">Contact</li>
            <li class="mdl-menu__item">Legal information</li>
        </ul>
    </div>
</header>
<div class="demo-drawer mdl-layout__drawer mdl-color--blue-grey-900 mdl-color-text--blue-grey-50">
    <header class="demo-drawer-header">
        <%--          <img src="/resources/images/dogvk.png" class="demo-avatar">--%>
        <div class="demo-avatar-dropdown">
            <span>hello@example.com</span>
            <div class="mdl-layout-spacer"></div>
            <button id="accbtn" class="mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--icon">
                <i class="material-icons" role="presentation">arrow_drop_down</i>
                <span class="visuallyhidden">Accounts</span>
            </button>
            <ul class="mdl-menu mdl-menu--bottom-right mdl-js-menu mdl-js-ripple-effect" for="accbtn">
                <li class="mdl-menu__item">ion@gmail.com</li>
                <li class="mdl-menu__item">info@example.com</li>
                <li class="mdl-menu__item"><i class="material-icons">add</i>Add another account...</li>
            </ul>
        </div>
    </header>
    <nav class="demo-navigation mdl-navigation mdl-color--blue-grey-800">
        <a class="mdl-navigation__link" href=""><i class="mdl-color-text--blue-grey-400 material-icons"
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
        <div class="mdl-layout-spacer"></div>
        <a class="mdl-navigation__link" href="/admin"><i class="mdl-color-text--blue-grey-400 material-icons"
                                                         role="presentation">help_outline</i>Menu</a>
    </nav>
</div>


