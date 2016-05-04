/**
 * Created by Alexander on 05.05.2016.
 */

var mailScheduler = angular.module('mailScheduler', []);
mailScheduler.controller('SchedulerController', ['$scope', '$http', function ($scope, $http) {

    $scope.list = [];

    $scope.submit = function () {
        var formData = {
            "minutes": $scope.minutes,
            "location": $scope.location,
        };

        var response = $http.post('/schedule', formData);
        response.success(function (data, status, headers, config) {
            $scope.list.push(data);
        });
        response.error(function (data, status, headers, config) {
            alert("Exception details: " + JSON.stringify({data: data}));
        });
        $scope.list = [];
    };
}]);