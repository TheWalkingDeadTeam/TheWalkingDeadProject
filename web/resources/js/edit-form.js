/**
 * Created by Neltarion on 12.05.2016.
 */

var editFormView = angular.module('editFormView', ['tableController', 'checklist-model']);

var tableController = angular.module('tableController', []);

tableController.controller('tableCtrl', ["$scope", "$http", function ($scope, $http) {

    $http.get('/admin/edit-form').success( function (response) {
        $scope.fields = response;
    })

    $scope.dataFields = {
        fieldId: []
    };

    $scope.checkAll = function () {
        if ($scope.selectedAll) {
            $scope.dataFields.fieldId = $scope.fields.map(function (item) {
                return item.id;
            });
            $scope.selectdAll = true;
        }
        else {
            $scope.selectdAll = false;
            $scope.dataFields.fieldId = [];
        }

    };
    
    $scope.addQuestion = function () {
        window.location.href = "/admin/edit-form/new-question";
    }

}]);