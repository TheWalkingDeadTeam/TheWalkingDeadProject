/**
 * Created by creed on 01.05.16.
 */

var studentView = angular.module('interView', ['checklist-model', 'ngRoute', 'phonecatControllers']);

studentView.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.when('/interview', {
            templateUrl: 'admin-iter-view.jsp',
            controller: 'interCtrl'
        }).otherwise({
            redirectTo: 'error.jsp'
        });
    }]);

var phonecatControllers = angular.module('phonecatControllers', []);


phonecatControllers.controller("interCtrl", ["$scope", "$http","$rootElement", function ($scope, $http,$rootElement) {
    // $http.get('resources/json/studentsData.json').success(function (data) {
    //     $scope.interviewer = data;
    // });

    $http.get('interviewers/list').success(function (data) {
        $scope.interviewer = data;
    });


    $scope.sortType = 'id';
    $scope.sortReverse = false;
    $scope.searchFiltr = '';
    $scope.dataInterviewer = {
        interId: []
    };
    $scope.checkAll = function () {
        if ($scope.selectedAll) {
            $scope.dataInterviewer.interId = $scope.interviewer.map(function (item) {
                return item.id;
            });
            $scope.selectdAll = true;
        }
        else {
            $scope.selectdAll = false;
            $scope.dataInterviewer.interId = [];
        }

    };
    $scope.activateStud = function () {
        var dataObj = {
            type: 'activate',
            values: $scope.dataInterviewer.interId
        };
        if ($scope.dataInterviewer.interId.length != 0) {
            var res = $http.post('interview', dataObj);
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
            values: $scope.dataInterviewer.interId
        };
        if ($scope.dataInterviewer.interId.length != 0) {
            var res = $http.post('interview', dataObj);
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
        var res = $http.post('interview', dataObj);
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
            values:[$scope.searchFilt]
        };
        var res = $http.post('interview', dataObj);
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