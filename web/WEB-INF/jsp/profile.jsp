<%--
  Created by IntelliJ IDEA.
  User: Neltarion
  Date: 23.04.2016
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile page</title>
    <link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.min.css"/>
</head>
<body>
<h1>PROFILE</h1>
<sec:authorize access="isAuthenticated()">
    <div>
        <form id="profileForm">
            <div><label>Name<input name="name" type="text"></label></div>
            <div><label>SurName<input name="surname" type="text"></label></div>
            <div><label>Email<input name="email" type="email"></label></div>
            <div><label>Phone<input name="tel" type="tel"></label></div>
            <div>University<select id="university" name="university">
                <option value="" disabled selected>University:</option>
            </select></div>
            <div>Course:<select id="course" name="course">
                <option value="" disabled selected>Course:</option>
            </select></div>
            <div>Languages:
                <ul id="programingLangList">
                        <%--<li><label>Name<input name="" type="number"></label></li> --%>
                </ul>
            </div>
            <div>Qs:
                <ul id="multiplyQuestions">
                        <%--<li><label>Phrase<input name="" type="number"></label></li> --%>
                </ul>
            </div>
            <div>S Qs:
                <ul id="singleQuestions">
                        <%--<li><label>Phrase<input name="" type="number"></label></li> --%>
                </ul>
            </div>
            <button type="submit">SAVE</button>
        </form>
    </div>
</sec:authorize>
<script src="resources/js/vendor/jquery-2.2.3.min.js"></script>
<script src="resources/js/profile.js"></script>
</body>
</html>
