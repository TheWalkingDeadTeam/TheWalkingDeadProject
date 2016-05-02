<!doctype html>
<html ng-app="app">
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular.js"></script>
    <script src="../../resources/js/studentListAngular.js"></script>
    <script src="http://vitalets.github.io/checklist-model/checklist-model.js"></script>
    <link rel="stylesheet" type="text/css" href="/resources/css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/styles.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/registration.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.css"/>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div ng-controller="StudentCtrl">
    <form>
        <div class="input-group-addon"><i></i></div>
        <input type="text" class="form-control" placeholder="Search" ng-model="searchFiltr">
    </form>


    <button ng-click="activateStud()">
        Activate
    </button>
    <button ng-click="deactivateStud()">
        Deactivate
    </button>
    <button ng-click="rejectStud()">
        Reject
    </button>
    <table class="table table-bordered table-striped">

        <thead>
        <tr>
            <td>
                <input type="checkbox" ng-model="selectedAll" ng-click="checkAll()">
            </td>
            <td>
                <a href="#" ng-click="sortType = 'id'; sortReverse = !sortReverse">
                    Id
                    <span ng-show="sortType == 'id' && !sortReverse" class="fa fa-caret-down"></span>
                    <span ng-show="sortType == 'id' && sortReverse" class="fa fa-caret-up"></span>
                </a>
            </td>
            <td>
                <a href="#" ng-click="sortType = 'name'; sortReverse = !sortReverse">
                    Name
                    <span ng-show="sortType == 'name' && !sortReverse" class="fa fa-caret-down"></span>
                    <span ng-show="sortType == 'name' && sortReverse" class="fa fa-caret-up"></span>
                </a>
            </td>
            <td>
                <a href="#" ng-click="sortType = 'university'; sortReverse = !sortReverse">
                    University
                    <span ng-show="sortType == 'university' && !sortReverse" class="fa fa-caret-down"></span>
                    <span ng-show="sortType == 'university' && sortReverse" class="fa fa-caret-up"></span>
                </a>
            </td>
            <td>
                <a href="#" ng-click="sortType = 'devMark'; sortReverse = !sortReverse">
                    Dev Assesment
                    <span ng-show="sortType == 'devMark' && !sortReverse" class="fa fa-caret-down"></span>
                    <span ng-show="sortType == 'devMark' && sortReverse" class="fa fa-caret-up"></span>
                </a>
            </td>
            <td>
                <a href="#" ng-click="sortType = 'hrMark'; sortReverse = !sortReverse">
                    HR Assesment
                    <span ng-show="sortType == 'hrMark' && !sortReverse" class="fa fa-caret-down"></span>
                    <span ng-show="sortType == 'hrMark' && sortReverse" class="fa fa-caret-up"></span>
                </a>
            </td>
            <td>
                <a href="#" ng-click="sortType = 'color'; sortReverse = !sortReverse">
                    Color
                    <span ng-show="sortType == 'color' && !sortReverse" class="fa fa-caret-down"></span>
                    <span ng-show="sortType == 'color' && sortReverse" class="fa fa-caret-up"></span>
                </a>
            </td>
            <td>
                <a href="#" ng-click="sortType = 'color'; sortReverse = !sortReverse">
                    Description
                    <span ng-show="sortType == 'color' && !sortReverse" class="fa fa-caret-down"></span>
                    <span ng-show="sortType == 'color' && sortReverse" class="fa fa-caret-up"></span>
                </a>
            </td>
            <td>
                <a href="#" ng-click="sortType = 'isActive'; sortReverse = !sortReverse">
                    Active
                    <span ng-show="sortType == 'isActive' && !sortReverse" class="fa fa-caret-down"></span>
                    <span ng-show="sortType == 'isActive' && sortReverse" class="fa fa-caret-up"></span>
                </a>
            </td>
        </tr>
        </thead>

        <tbody>
        <tr ng-repeat="ch in students | orderBy:sortType:sortReverse | filter:searchFiltr">
            <td><input type="checkbox" checklist-model="dataStudents.studId" checklist-value="ch.id"></td>
            <td>{{ch.id}}</td>
            <td>{{ch.name}} {{ch.surname}}</td>
            <td>{{ch.university}}</td>
            <td>{{ch.devMark}}</td>
            <td>{{ch.hrMark}}</td>
            <td ng-style="{opacity:0.5,'background-color':'{{ch.color}}'}"></td>
            <td>{{ch.color == "red" ? "Reject" :
                  ch.color == "green" ? "On course" :
                  ch.color == "blue" ? "On job" : "Thinking"}}</td>
            <td>{{ch.isActive == 1 ? "Active" : "Inactive"}}</td>

        </tr>
        </tbody>
        <tt>multipleSelect = {{dataStudents.studId}}</tt>
    </table>


</div>
</body>
</html>
