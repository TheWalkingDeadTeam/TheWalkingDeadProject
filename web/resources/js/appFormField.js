/**
 * Created by Neltarion on 12.05.2016.
 */

var formView = angular.module('formView', ['viewController', 'checklist-model']);

var viewController = angular.module('viewController', []);

var id = location.search.substr(1);
// var id = getParameter(id);
viewController.controller('viewCtrl', ["$scope", "$http", function ($scope, $http) {
    
    $http.get('/admin/edit-form/appformfield/' + id).success( function (response) {
        $scope.fieldOptions = response;
    });


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
    
}]);

viewController.controller('questionInfo', ["$scope", "$http", function ($scope, $http) {

    $http.get('/admin/edit-form/appformfield/' + id).success( function (response) {
        $scope.field = response;
    });

}]);


function getParameter(paramName) {
    var searchString = window.location.search.substring(1),
        i, val, params = searchString.split("&");

    for (i=0;i<params.length;i++) {
        val = params[i].split("=");
        if (val[0] == paramName) {
            return val[1];
        }
    }
    return null;
}