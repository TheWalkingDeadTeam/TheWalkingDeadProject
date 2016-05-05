/**
 * Created by creed on 01.05.16.
 */

var studentView = angular.module('studentView', ['checklist-model', 'ngRoute', 'phonecatControllers']);

studentView.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.when('/students', {
            templateUrl: 'admin-stud-view.jsp',
            controller: 'StudentCtrl'
        }).otherwise({
            redirectTo: 'error.jsp'
        });
    }]);

var phonecatControllers = angular.module('phonecatControllers', []);


phonecatControllers.controller("StudentCtrl", ["$scope", "$http", "$rootElement", function ($scope, $http, $rootElement) {
    // $http.get('resources/json/studentsData.json').success(function (data) {
    //     $scope.interviewer = data;
    // });

    $http.get('students/list').success(function (data) {
        $scope.students = data;
    });


    $scope.sortType = 'id';
    $scope.sortReverse = false;
    $scope.searchFiltr = '';
    $scope.dataStudents = {
        studId: []
    };
    $scope.checkAll = function () {
        if ($scope.selectedAll) {
            $scope.dataStudents.studId = $scope.students.map(function (item) {
                return item.id;
            });
            $scope.selectdAll = true;
        }
        else {
            $scope.selectdAll = false;
            $scope.dataStudents.studId = [];
        }

    };
    $scope.activateStud = function () {
        var dataObj = {
            type: 'activate',
            values: $scope.dataStudents.studId
        };
        if ($scope.dataStudents.studId.length != 0) {
            var res = $http.post('students', dataObj);
            res.success(function (data, status, headers, config) {
                $scope.message = data;
            });
            res.error(function (data, status, headers, config) {
                alert("failure message: " + JSON.stringify({data: data}));
            });
        }
    };
    $scope.deactivateStud = function () {
        var dataObj = {
            type: 'deactivate',
            values: $scope.dataStudents.studId
        };
        if ($scope.dataStudents.studId.length != 0) {
            var res = $http.post('students', dataObj);
            res.success(function (data, status, headers, config) {
                $scope.message = data;
            });
            res.error(function (data, status, headers, config) {
                alert("failure message: " + JSON.stringify({data: data}));
            });
        }
    };
    $scope.rejectStud = function () {
        var dataObj = {
            type: 'reject',
            values: $scope.dataStudents.studId
        };
        if ($scope.dataStudents.studId.length != 0) {
            var res = $http.post('students', dataObj);
            res.success(function (data, status, headers, config) {
                $scope.message = data;
            });
            res.error(function (data, status, headers, config) {
                alert("failure message: " + JSON.stringify({data: data}));
            });
        }
    };
    $scope.saveChanges = function () {
        var dataObj = {
            type: "save",
            values: []
        };
        var res = $http.post('students', dataObj);
        res.success(function (data, status, headers, config) {
            $scope.message = data;
        });
        res.error(function (data, status, headers, config) {
            alert("failure message: " + JSON.stringify({data: data}));
        });
    }

    $scope.searchFiltr = function () {
        var dataObj = {
            type: "search",
            values: [$scope.searchFilt]
        };
        var res = $http.post('students', dataObj);
        res.success(function (data, status, headers, config) {
            $scope.message = data;
        });
        res.error(function (data, status, headers, config) {
            alert("failure message: " + JSON.stringify({data: data}));
        });
    }
}]);


it('should change state', function () {
    var value1 = element(by.binding('h.isActive'));

    expect(value1.getText()).toContain('1');

    element(by.model('h.isActive')).click();
    expect(isActive.getText()).toContain('0');

});