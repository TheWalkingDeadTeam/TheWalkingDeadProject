<!doctype html>
<html ng-app="app">
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular.js"></script>
    <script src="../../resources/js/studentListAngular.js"></script>
    <script src="http://vitalets.github.io/checklist-model/checklist-model.js"></script>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div ng-controller="StudentCtrl">
    <div>
        <div class="header">Students</div>
        <div ng-repeat="ch in students">
            <h3>==================</h3>

            <input type="checkbox" ng-model="ch.isActive" checklist-value="ch" ng-true-value="'1'" ng-false-value="'0'">
            <tt>box = {{ch.isActive}}</tt><br/>
            <h1>{{ch.name}}</h1>
            <h2>{{ch.email}}</h2>
            <p>{{ch.phone}}</p>
            <p>{{ch.address}}</p>


        </div>
    </div>

    <pre>{{ filter.students | json }}</pre>
</div>
</body>
</html>
