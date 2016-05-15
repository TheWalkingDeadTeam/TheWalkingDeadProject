var interView = angular.module('interView', ['checklist-model', 'angularUtils.directives.dirPagination']);

interView.controller('interCtrl', ["$http", "$scope", function ($http, $scope) {
    var vm = this;
    vm.users = []; //declare an empty array
    vm.pageno = 1; // initialize page no to 1
    vm.total_count = 0;
    vm.itemsPerPage = 10; //this could be a dynamic value from a drop down
    vm.selectUrl = "interviewers/list/" + vm.itemsPerPage + "/" + vm.pageno;
    vm.order_by = null;


    vm.getData = function () { // This would fetch the data on page change.
        //In practice this should be in a factory.
        vm.users = [];
        $http.get(vm.selectUrl).success(function (response) {
            vm.users = response;
            // vm.order_by = vm.header[0].id;
        });
        $http.get("interviewers/size").success(function (response) {
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
            vm.selectUrl = "interviewers/list/" + vm.itemsPerPage + "/" + vm.pageno;
        } else {
            vm.selectUrl = "interviewers/list/" + vm.itemsPerPage + "/" + vm.pageno + "/" + vm.order_by;
        }
        vm.getData();
    };

    $scope.checkAll = function () {
        if ($scope.selectedAll) {
            $scope.dataStudents.studId = vm.users.map(function (item) {
                return item.id;
            });
            $scope.selectdAll = true;
        }
        else {
            $scope.selectdAll = false;
            $scope.dataStudents.studId = [];
        }

    };

    $scope.sortType = function (type, asc) {
        vm.order_by = type;
        vm.selectUrl = "interviewers/list/" + vm.itemsPerPage + "/" + vm.pageno + "/" + vm.order_by + "/" + asc;
        vm.getData()
    };


    $scope.subscribeInterviewer = function () {
        var dataObj = {
            type: 'subscribe',
            values: $scope.dataStudents.studId
        };
        if ($scope.dataStudents.studId.length != 0) {
            var res = $http.post('interviewers', dataObj);
            res.success(function (data, status, headers, config) {
                $scope.message = data;
            });
            res.error(function (data, status, headers, config) {
                alert("failure message: " + JSON.stringify({data: data}));
            });
        }
    };

    $scope.unsubscribeInterviewer = function () {
        var dataObj = {
            type: 'unsubscribe',
            values: $scope.dataStudents.studId
        };
        if ($scope.dataStudents.studId.length != 0) {
            var res = $http.post('interviewers', dataObj);
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
            vm.selectUrl = "interviewers/list/" + vm.itemsPerPage + "/" + vm.pageno;

        }
        else {
            console.log("Privet2");
            if (vm.order_by === null) {
                vm.selectUrl = "interviewer/search/" + vm.itemsPerPage + "/" + vm.pageno + "/name/" + pattern;
            } else {
                vm.selectUrl = "interviewer/search/" + vm.itemsPerPage + "/" + vm.pageno + "/" + vm.order_by + "/" + pattern;
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