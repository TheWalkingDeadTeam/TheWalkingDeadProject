/**
 * Created by Neltarion on 12.05.2016.
 */

var formView = angular.module('formView', ['viewController', 'checklist-model']);

var viewController = angular.module('viewController', []);

var listType_id = location.search.substr(1);
var id = listType_id.split('/')[1];

viewController.controller('viewCtrl', ["$scope", "$http", function ($scope, $http) {
    var vm = this;
    vm.isShown = false;
    
    $http.get('/admin/edit-form/appformfield/' + listType_id).success(function (response) {
        $scope.fieldOptions = response;
        if (response[0].id) {
            vm.isShown = true;
        }
    });


    $scope.back = function () {
        window.location.href = "/admin/edit-form";
    }

    $scope.dataOptions = {
        optionId: []
    };

    $scope.checkAll = function () {
        if ($scope.selectedAll) {
            $scope.dataOptions.optionId = $scope.fieldOptions.map(function (item) {
                return item.id;
            });
            $scope.selectdAll = true;
        }
        else {
            $scope.selectdAll = false;
            $scope.dataOptions.optionId = [];
        }

    };

    // $scope.deleteOption = function () {
    //     var dataObject = {
    //         id: $scope.dataOptions.optionId
    //     }
    //     if ($scope.dataOptions.optionId.length != 0) {
    //         var res = $http.post('/admin/edit-form/delete-option', dataObject);
    //         res.success(function (responseData) {
    //             if (responseData.length) {
    //                 var errors_out = "";
    //                 for (var i in responseData) {
    //                     errors_out += responseData[i].errorMessage + "</br>"
    //                 }
    //                 $('#errorsDiv')
    //                     .removeClass()
    //                     .empty()
    //                     .addClass('alert alert-danger')
    //                     .html(errors_out);
    //             } else {
    //                 $scope.postSuccess = true;
    //                 $('#errorsDiv')
    //                     .removeClass()
    //                     .empty()
    //                     .addClass('alert alert-success')
    //                     .html('Deleted successfully');
    //                 // window.location.href = '/admin/edit-form/appformfield?' + id;
    //             }
    //             $scope.message = responseData;
    //         });
    //         res.error(function (data) {
    //             alert("failure message: " + JSON.stringify({data: data}));
    //         });
    //     }
    // }

}]);

viewController.controller('questionInfo', ["$scope", "$http", function ($scope, $http) {

    $http.get('/admin/edit-form/appformfield/get-field/' + id).success(function (response) {
        $scope.field = response;
    });

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
    
}]);


function getParameter(paramName) {
    var searchString = window.location.search.substring(1),
        i, val, params = searchString.split("&");

    for (i = 0; i < params.length; i++) {
        val = params[i].split("=");
        if (val[0] == paramName) {
            return val[1];
        }
    }
    return null;
}