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
    var self = this;
    $http.get('/CES/').success(function (data) {
        $scope.session = data;
        if (data.length != 0) {
            self.cesTable = false;
            self.emptyTemplateTable = true;
        } else {
            self.cesTable = true;
            self.emptyTemplateTable = false;
        }
    });
    
    $scope.sortType = 'id';
    $scope.sortReverse = false;
    $scope.searchFiltr = '';
    
}]);


