var app = angular.module('userView', ['checklist-model', 'angularUtils.directives.dirPagination']);

app.controller('UserCtrl', ["$http", "$scope", function ($http, $scope) {
    var vm = this;
    vm.users = []; //declare an empty array
    vm.pageno = 1; // initialize page no to 1
    vm.total_count = 0;
    vm.itemsPerPage = 10; //this could be a dynamic value from a drop down
    vm.selectUrl = "users/list/" + vm.itemsPerPage + "/" + vm.pageno;
    vm.order_by = null;
    $scope.sortReverse = false;


    vm.getData = function () {
        vm.users = [];
        $http.get(vm.selectUrl).success(function (response) {
            vm.users = response;
        });
        $http.get("users/size").success(function (response) {
            vm.total_count = response;
        });
    };

    $scope.dataStudents = {
        studId: []
    };

    $scope.columnsType = {
        columns: []
    };

    vm.getData(); // Call the function to fetch initial data on page load.

    vm.setPageno = function (pageno) {
        vm.pageno = pageno;
        if (vm.order_by === null) {
            vm.selectUrl = "users/list/" + vm.itemsPerPage + "/" + vm.pageno;
        } else {
            vm.selectUrl = "users/list/" + vm.itemsPerPage + "/" + vm.pageno + "/" + vm.order_by;
        }
        vm.getData();
    };

    $scope.checkAll = function () {
        if ($scope.selectedAll) {
            $scope.dataStudents.studId = vm.users.map(function (item) {
                return item.userId;
            });
            $scope.selectdAll = true;
        }
        else {
            $scope.selectdAll = false;
            $scope.dataStudents.studId = [];
        }

    };

    $scope.sortType = function (type,revers) {
        vm.order_by = type;
        vm.selectUrl = "users/list/" + vm.itemsPerPage + "/" + vm.pageno + "/" + vm.order_by + "/" + revers;
        vm.getData()
    };

    $scope.activateUser = function () {
        var dataObj = {
            type: 'activate',
            values: $scope.dataStudents.studId
        };
        if ($scope.dataStudents.studId.length != 0) {
            var res = $http.post('users', dataObj);
            res.success(function (data, status, headers, config) {
                $scope.message = data;
            });
            res.error(function (data, status, headers, config) {
                alert("failure message: " + JSON.stringify({data: data}));
            });
        }
    };

    $scope.deactivateUser = function () {
        var dataObj = {
            type: 'deactivate',
            values: $scope.dataStudents.studId
        };
        if ($scope.dataStudents.studId.length != 0) {
            var res = $http.post('users', dataObj);
            res.success(function (data, status, headers, config) {
                $scope.message = data;
            });
            res.error(function (data, status, headers, config) {
                alert("failure message: " + JSON.stringify({data: data}));
            });
        }
    };

    $scope.searchFiltr = function (pattern) {
        var dataObj = {
            type: "search",
            values: [$scope.searchFilt]
        };
        if (pattern == undefined || pattern == "" || pattern == null) {
            vm.selectUrl = "users/list/" + vm.itemsPerPage + "/" + vm.pageno;

        } else {
            if (vm.order_by === null) {
                vm.selectUrl = "users/search/" + vm.itemsPerPage + "/" + vm.pageno + "/" + pattern;
            } else {
                vm.selectUrl = "users/search/" + vm.itemsPerPage + "/" + vm.pageno + "/" + vm.order_by + "/" + pattern;
            }
        }
        vm.getData();
    }
}]);


it('should change state', function () {
    var value1 = element(by.binding('user.isActive'));

    expect(value1.getText()).toContain('1');

    element(by.model('user.isActive')).click();
    expect(isActive.getText()).toContain('0');

});