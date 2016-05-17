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
    <title>Contacts</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/png" sizes="32x32" href="/resources/images/ico.png"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="icon" type="image/png" sizes="32x32" href="/images/ico.png">
    <link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/resources/css/style-profile.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/resources/css/media-profile.css" rel="stylesheet">
    <script src="/resources/bootstrap/js/jquery-2.2.2.min.js" defer></script>
    <script src="/resources/bootstrap/js/bootstrap.min.js" defer></script>
    <script src="http://maps.google.com/maps/api/js?sensor=false"></script>
    <script src="/resources/js/google-api.js"></script>
</head>
<body>

<jsp:include page="header.jsp"/>

<div class="headquarters-container bord-bottom">
    <div class="container headquarters-info">
        <div class="row">
            <div class="col-xs-6 col-sm-6 col-md-6">
                <div class="row">
                    <div class="col-md-12">
                        <div class="headline-one"><h1>Headquarters</h1></div>
                        <div class="standard-copy">University Office Park III</div>
                        <div class="standard-copy">95 Sawyer Road Waltham, MA 02453</div>
                        <div class="standard-copy">United States of America</div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="standard-copy"><h1>Phone numbers</h1></div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-4 col-sm-4 col-md-6">
                        <div class="standard-copy">Phone</div>
                    </div>
                    <div class="col-xs-8 col-sm-8 col-md-6">
                        <div class="standard-copy text-right">1-781-419-3300</div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-4 col-sm-4 col-md-6">
                        <div class="standard-copy">Toll Free</div>
                    </div>
                    <div class="col-xs-8 col-sm-8 col-md-6">
                        <div class="standard-copy text-right">1-800-477-5785</div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-4 col-sm-4 col-md-6">
                        <div class="standard-copy">Fax</div>
                    </div>
                    <div class="col-xs-8 col-sm-8 col-md-6">
                        <div class="standard-copy text-right">1-781-419-3301</div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-4 col-sm-4 col-md-4">
                        <div class="standard-copy">Customer Support</div>
                    </div>
                    <div class="col-xs-8 col-sm-8 col-md-8">
                        <div class="standard-copy text-right">1-781-419-3388, 1-844-855-3355</div>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 col-sm-6 col-md-6"> <div id="map-container" class="card-panel hoverable wow fadeInUp" style="height: 300px"></div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
