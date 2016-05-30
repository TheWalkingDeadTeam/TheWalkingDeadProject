/**
 * Created by Neltarion on 12.05.2016.
 */

var editFormView = angular.module('editFormView', ['tableController', 'checklist-model', 'as.sortable']);

var tableController = angular.module('tableController', []);

tableController.controller('tableCtrl', ["$scope", "$http", function ($scope, $http) {

    $scope.isShown = false;

    $http({
        method: 'GET',
        url: '/admin/edit-form',
        headers: {Accept: 'application/json'}
    }).success(function (response) {
        $scope.fields = response;
    });

    $scope.dataFields = {
        fieldId: []
    };

    $scope.sortableOptions = {
        orderChanged: function (event) {
            $scope.isShown = true;
        }

    };

    $scope.chooseType = function (typeID) {
        if (typeID == 1) {
            return "Number";
        }
        if (typeID == 2) {
            return "Text";
        }
        if (typeID == 3) {
            return "Textarea";
        }
        if (typeID == 4) {
            return "Select";
        }
        if (typeID == 5) {
            return "Checkbox";
        }
        if (typeID == 6) {
            return "Radiobutton";
        }
        if (typeID == 7) {
            return "Tel";
        }
        if (typeID == 8) {
            return "Date";
        }
    };

    $scope.savePosition = function () {
        var dataObject = {
            fields: $scope.fields
        }
        if ($scope.fields.length != 0) {
            var res = $http.post('/admin/edit-form/save-position', dataObject);
            res.success(function (responseData) {
                if (responseData.length) {
                    var errors_out = "";
                    for (var i in responseData) {
                        errors_out += responseData[i].errorMessage + "</br>"
                    }
                    $('#errorsDiv')
                        .removeClass()
                        .empty()
                        .addClass('alert alert-danger')
                        .html(errors_out);
                } else {
                    $('#errorsDiv')
                        .removeClass()
                        .empty()
                        .addClass('alert alert-success')
                        .html('Position saved successfully');
                }
            });
        }
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
    };

    $scope.deleteQuestion = function () {
        var dataObject = {
            id: $scope.dataFields.fieldId
        }
        if ($scope.dataFields.fieldId.length != 0) {
            var res = $http.post('/admin/edit-form', dataObject);
            res.success(function (responseData, status, headers, config) {
                if (responseData.length) {
                    var errors_out = "";
                    for (var i in responseData) {
                        errors_out += responseData[i].errorMessage + "</br>"
                    }
                    $('#errorsDiv')
                        .removeClass()
                        .empty()
                        .addClass('alert alert-danger')
                        .html(errors_out);
                } else {
                    $scope.postSuccess = true;
                    $('#errorsDiv')
                        .removeClass()
                        .empty()
                        .addClass('alert alert-success')
                        .html('Deleted successfully');
                    window.location.href = "/admin/edit-form";
                }
                $scope.message = responseData;
            });
            res.error(function (data, status, headers, config) {
                alert("failure message: " + JSON.stringify({data: data}));
            });
        }
    }

}]);