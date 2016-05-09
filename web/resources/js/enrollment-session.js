/**
 * Created by Alexander on 09.05.2016.
 */

var enrollView = angular.module('enrollView', ['checklist-model', 'ngRoute', 'sessionController']);

enrollView.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.when('/enroll-session', {
            templateUrl: 'admin-iter-view.jsp',
            controller: 'enrollCtrl'
        }).otherwise({
            redirectTo: 'error.jsp'
        });
    }]);

var sessionController = angular.module('sessionController', []);


sessionController.controller("enrollCtrl", ["$scope", "$http", "$rootElement", function ($scope, $http, $rootElement) {
    // $http.get('resources/json/studentsData.json').success(function (data) {
    //     $scope.interviewer = data;
    // });

    $http.get('/CES/').success(function (data) {
        $scope.session = data;
    });


    $scope.sortType = 'id';
    $scope.sortReverse = false;
    $scope.searchFiltr = '';


    // $scope.searchFilter = function () {
    //     var dataObj = {
    //         type: "search",
    //         values: [$scope.searchFilt]
    //     };
    //     var res = $http.post('interview', dataObj);
    //     res.success(function (data, status, headers, config) {
    //         $scope.message = data;
    //     });
    //     res.error(function (data, status, headers, config) {
    //         alert("failure message: " + JSON.stringify({data: data}));
    //     });
    // }
}]);


// it('should change state', function () {
//     var value1 = element(by.binding('h.isActive'));
//
//     expect(value1.getText()).toContain('1');
//
//     element(by.model('h.isActive')).click();
//     expect(isActive.getText()).toContain('0');

// });