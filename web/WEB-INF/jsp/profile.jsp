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

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Netcracker</title>
    <link rel="icon" type="image/png" sizes="32x32" href="/resources/images/ico.png"/>
    <link rel="stylesheet" type="text/css" href="/resources/files/reset.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/files/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/files/styles.css"/>
</head>
<body>
<h1>PROFILE</h1>
<sec:authorize access="isAuthenticated()">
    <div>
        <form id="profileForm">
            <div><label>Name <input name="name" type="text" maxlength="50"></label></div>
            <div><label>Surname <input name="surname" type="text" maxlength="50"></label></div>
            <div><label>Phone <input name="tel" type="tel"></label></div>
            <div><label>Email <input name="email" type="email" maxlength="100"></label></div>
            <div><label>University <select id="university" name="university">
                <option value="" disabled selected>University:</option>
            </select></label></div>
            <div><label>Faculty <input name="faculty" type="text" maxlength="50"></label></div>
            <div><label>Department <input name="department" type="text" maxlength="100"></label></div>
            <div><label>Specialty <input name="specialty" type="text" maxlength="100"></label></div>
            <div><label>Graduation year <input name="graduationyear" type="date" ></label></div>
            <div><label>Course: <select id="course" name="course">
                <option value="" disabled selected>Course:</option>
            </select></label></div>
            <div><label>Knowledge of Programming languages:
                <ul id="programingLangList">
                        <%--<li><label>Name<input name="" type="number"></label></li> --%>
                </ul>
            </label></div>
            <div><label>Other knowledge:
                <ul id="knowledgeList">
                        <%--<li><label>Name<input name="" type="number"></label></li> --%>
                </ul>
            </label></div>
            <div>
                <ul id="multiplyQuestions">
                        <%--<li><label>Phrase<input name="" type="number"></label></li> --%>
                </ul>
            </div>
            <div>
                <ul id="singleQuestions">
                        <%--<li><label>Phrase<input name="" type="number"></label></li> --%>
                </ul>
            </div>
            <div><label>Additional languages <input name="additionalLang" type="text" maxlength="100"></label></div>
            <div><label>Average mark during study in university <input name="averageMark" type="number" min="0" max="5"></label></div>
            <div><label>Portfolio <input name="portfolio" type="textarea" maxlength="1000"></label></div>
            <div><label>Why should we take you? <input name="whyYou" type="textarea" maxlength="1000"></label></div>
            <div><label>Participation in foreign student programs <input name="foreignPrograms" type="textarea" maxlength="1000"></label></div>
            <div><label>Your hobbies <input name="hobbies" type="textarea" maxlength="1000"></label></div>
            <div>
                <ul id="singleQuestions2">
                        <%--<li><label>Phrase<input name="" type="number"></label></li> --%>
                </ul>
            </div>
            <div><label>I agree to have my personal information proceeded <input name="personalAgree" type="checkbox"></label></div>

            <button type="submit">SAVE</button>
        </form>
    </div>
</sec:authorize>
<script src="resources/js/vendor/jquery-2.2.3.min.js"></script>
<script src="resources/js/profile.js"></script>
</body>
</html>
