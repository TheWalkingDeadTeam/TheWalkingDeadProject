<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
<head>
  <title>Profile</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="icon" type="image/png" sizes="32x32" href="/resources/images/ico.png"/>
  <%--<link rel="stylesheet" type="text/css" href="/resources/css/reset.css"/>--%>
  <%--<link rel="stylesheet" type="text/css" href="/resources/css/styles.css"/>--%>
  <%--<link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.css"/>--%>
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

  <link rel="icon" type="image/png" sizes="32x32" href="/images/ico.png">
  <link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="/resources/css/style-profile.css" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="/resources/css/media-profile.css" rel="stylesheet">
  <script src="/resources/bootstrap/js/jquery-2.2.2.min.js" defer></script>
  <script src="/resources/bootstrap/js/bootstrap.min.js" defer></script>
  <%--<style type="text/css">--%>
  <%--/*<img src='images/logo.png' alt="Brand" class="header-img">*/--%>
  <%--/*<img src='images/error.gif' class="img-responsive profile-photo">*/--%>
  <%--/*<img class='img-responsive' src="images/logo-gray.png">*/--%>
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
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand brand-img" href="">
          <img src='/resources/images/logo.png' alt="Brand" class="header-img">
        </a>
      </div>
      <div id='collapsed-menu' class='navbar-collapse collapse'>
        <ul class="nav navbar-nav navbar-right">
          <li><a href="/login">Home</a></li>
          <li><a href="/information">Information</a></li>
          <li><a href="/contacts">Contacts</a></li>
          <li><a href="/logout">Logout</a></li>
        </ul>
      </div>
    </div>
  </nav>
</header>

<sec:authorize access="hasAnyRole('ROLE_BA','ROLE_HR','ROLE_DEV')">

  <div class="container smprofile">
    <div class="row">
      <div class="col-lg-2 col-md-2 col-sm-2 col-xs-4">
        <img id="photo_img" src="/getPhoto" alt="User's photo" width="100" height="100"
             onError="this.src='/resources/images/user-photo.png'" class="profile-photo">
      </div>
      <div class='col-lg-3 col-md-3 col-sm-3 col-xs-8'>
        <form id="photo_form" type=post enctype="multipart/form-data">
          <div id="photoMessages"></div>
          <label for='photo_input' class='file_upload'/>
          <input type="file" id="photo_input" name=" photo_input" accept="image/*">
          <button id="photo_button" type="submit">Upload</button>
        </form>
      </div>
      <div class=" col-lg-2 col-md-2 col-sm-2 col-xs-4 mainf">
        <h4>Name:</h4>
        <h4>Surname:</h4>
        <h4>E-mail:</h4>
      </div>
      <div class="col-lg-2 col-md-2 col-sm-2 col-xs-4">
        <span id="userName">Ivan</span>
        <span id="userSurname">Ivanovich</span>
        <span id="userEmail">ivanovivanovich@gmail.com</span>
      </div>
    </div>
  </div>
  </div>

  <div id="profile">
  </div>
  <br>
  <div id="save_message"></div>
  <div id="feedback">
    <form id="feedback_form">
      <input type="number" id = "feedback_score" max="100" min="0" align="centre"/>
      <textarea id="feedback_text" placeholder="Put your feedback here" cols="40" rows="10"></textarea>
      <button type="submit" title="Submit">Submit</button>
    </form>
  </div>


</sec:authorize>


<footer class="footer container-fluid">
  <div class="footerLg container visible-md visible-lg">
    <div class="col-lg-3 col-lg-3 col-sm-3"><img class='img-responsive' src="/resources/images/logo-gray.png"></div>

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
      <img src="/resources/images/logo-gray.png">
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

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/resources/js/profileForInterviewer.js"></script>
<script src="/resources/js/photo.js"></script>

</body>
</html>
