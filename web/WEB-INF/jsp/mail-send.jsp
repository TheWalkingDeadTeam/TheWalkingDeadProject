<%--
  Created by IntelliJ IDEA.
  User: Alexander
  Date: 14.05.2016
  Time: 1:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="studentView">
<head>
    <title>Mail</title>
    <!-- Compiled and minified JavaScript -->
</head>
<body ng-controller="StudentCtrl">

<md-dialog aria-label="Mango (Fruit)">
    <form>

        <md-toolbar>
            <div class="md-toolbar-tools">
                <h2>Mailer (Test)</h2>
                <span flex></span>
                <md-button class="md-icon-button" ng-click="cancel()">
                    <md-icon md-svg-src="img/icons/ic_close_24px.svg" aria-label="Close dialog"></md-icon>
                </md-button>
            </div>
        </md-toolbar>


        <md-dialog-content style="max-width:800px;max-height:810px; ">
            <md-tabs md-dynamic-height md-border-bottom>
                <md-tab label="one">
                    <md-content class="md-padding">
                        <h1 class="md-display-2">Users</h1>
                        
                            From jsp: {{dialogCtrl.parent.redirectArray.usersWithMail}};



                        <tr ng-repeat="u in dialogCtrl.redirectArray.usersWithMail">
                            <td><span ng-bind="u.id"></span></td>
                            <td><span ng-bind="u.email"></span></td>
                        </tr>

                    </md-content>
                </md-tab>
                <md-tab label="two">
                    <md-content class="md-padding">
                        <h1 class="md-display-2">Tab Two</h1>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla venenatis ante augue. Phasellus volutpat neque ac dui mattis vulputate. Etiam consequat aliquam cursus. In sodales pretium ultrices. Maecenas lectus est, sollicitudin consectetur felis nec, feugiat ultricies mi. Aliquam erat volutpat. Nam placerat, tortor in ultrices porttitor, orci enim rutrum enim, vel tempor sapien arcu a tellus. Vivamus convallis sodales ante varius gravida. Curabitur a purus vel augue ultrices ultricies id a nisl. Nullam malesuada consequat diam, a facilisis tortor volutpat et. Sed urna dolor, aliquet vitae posuere vulputate, euismod ac lorem. Sed felis risus, pulvinar at interdum quis, vehicula sed odio. Phasellus in enim venenatis, iaculis tortor eu, bibendum ante. Donec ac tellus dictum neque volutpat blandit. Praesent efficitur faucibus risus, ac auctor purus porttitor vitae. Phasellus ornare dui nec orci posuere, nec luctus mauris semper.</p>
                        <p>Morbi viverra, ante vel aliquet tincidunt, leo dolor pharetra quam, at semper massa orci nec magna. Donec posuere nec sapien sed laoreet. Etiam cursus nunc in condimentum facilisis. Etiam in tempor tortor. Vivamus faucibus egestas enim, at convallis diam pulvinar vel. Cras ac orci eget nisi maximus cursus. Nunc urna libero, viverra sit amet nisl at, hendrerit tempor turpis. Maecenas facilisis convallis mi vel tempor. Nullam vitae nunc leo. Cras sed nisl consectetur, rhoncus sapien sit amet, tempus sapien.</p>
                        <p>Integer turpis erat, porttitor vitae mi faucibus, laoreet interdum tellus. Curabitur posuere molestie dictum. Morbi eget congue risus, quis rhoncus quam. Suspendisse vitae hendrerit erat, at posuere mi. Cras eu fermentum nunc. Sed id ante eu orci commodo volutpat non ac est. Praesent ligula diam, congue eu enim scelerisque, finibus commodo lectus.</p>
                    </md-content>
                </md-tab>
                <md-tab label="three">
                    <md-content class="md-padding">
                        <h1 class="md-display-2">Tab Three</h1>
                        <p>Integer turpis erat, porttitor vitae mi faucibus, laoreet interdum tellus. Curabitur posuere molestie dictum. Morbi eget congue risus, quis rhoncus quam. Suspendisse vitae hendrerit erat, at posuere mi. Cras eu fermentum nunc. Sed id ante eu orci commodo volutpat non ac est. Praesent ligula diam, congue eu enim scelerisque, finibus commodo lectus.</p>
                    </md-content>
                </md-tab>
            </md-tabs>
        </md-dialog-content>
        <md-dialog-actions layout="row">
            <md-button href="http://en.wikipedia.org/wiki/Mango" target="_blank" md-autofocus>
                More on Wikipedia
            </md-button>
            <span flex></span>
            <md-button ng-click="answer('not useful')" >
                Not Useful
            </md-button>
            <md-button ng-click="answer('useful')" style="margin-right:20px;" >
                Useful
            </md-button>
        </md-dialog-actions>
    </form>
</md-dialog>

</body ng-controller="StudentCtrl">
<script src="/resources/js/studentListAngular.js" ></script>

</html>
