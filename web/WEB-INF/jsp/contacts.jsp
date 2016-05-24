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
    <link rel="stylesheet" type="text/css" href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="resources/css/style-profile.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="resources/css/media-profile.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/resources/css/style-contacts.css">
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="resources/bootstrap/js/bootstrap.min.js" defer></script>
</head>
<body>

<jsp:include page="header.jsp"/>

<div>
    <div>
        <div class="row">
            <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                <div class="row">
                    <div>
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                            <h4 style="margin-left: 15px;">Headquarters</h4>
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">University Office Park III</div>
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">95 Sawyer Road Waltham, MA 02453</div>
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">United States of America</div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                            <h4>Courses</h4>
                            <div>Patrice Lumumba Street, 4/6B, Kyiv</div>
                            <div>Ukraine</div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div>
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                            <div>
                                <h4 style="margin-left: 15px;">Phone numbers</h4>
                                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                    <div class="names">Phone</div>
                                    <div class="num">1-781-419-3300</div>
                                </div>
                                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                    <div class="names">Toll Free</div>
                                    <div class="num">1-800-477-5785</div>
                                </div>
                                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                    <div class="names">Fax</div>
                                    <div class="num">1-781-419-3301</div>
                                </div>
                                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                    <div class="names">Customer Support</div>
                                    <div class="num">1-844-855-3355</div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                            <div>
                                <h4>Courses numbers</h4>
                                <div><a href="tel:+380442388727">044 238 8727</a></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                <div id="map-container" class="z-depth-1" style="height: 200px"></div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>
<script src="http://maps.google.com/maps/api/js"></script>
<script src="/resources/js/google-maps/google-maps.js"></script>
</body>
</html>
