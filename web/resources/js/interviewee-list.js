/**
 * Created by creed on 15.05.16.
 */
var app = angular.module('userView', ['checklist-model', 'angularUtils.directives.dirPagination']);

app.controller('IntervieweeCtrl', ["$http", "$scope", function ($http, $scope) {
    var vm = this;
    vm.users = []; //declare an empty array
    vm.pageno = 1; // initialize page no to 1
    vm.total_count = 0;
    vm.itemsPerPage = 10; //this could be a dynamic value from a drop down
    vm.selectUrl = "interviewee/list/" + vm.itemsPerPage + "/" + vm.pageno;
    vm.order_by = null;
    vm.pattern = null;
    $scope.sortReverse = false;
    
    vm.showSpin = function () {
        angular.element($(".cssload-thecube")).css('display','block');
        angular.element($("#tableUsers")).css('display','none');
        angular.element($("#pagination")).css('display','none');
    };
    vm.hideSpin = function () {
        angular.element($(".cssload-thecube")).css('display','none');
        angular.element($("#tableUsers")).css('display','table');
        angular.element($("#pagination")).css('display','block');
    };

    vm.getData = function () {
        vm.showSpin();

        vm.users = [];
        $http.get(vm.selectUrl).success(function (response) {
            vm.users = response;
        }).error(function (errResponse) {
            console.error('Error while get data');
            alert('Error while get data');
        });

        vm.hideSpin();
    };

    vm.getSize = function () {

        if(vm.pattern == null) {
            $http.get("interviewee/size").success(function (response) {
                vm.total_count = response;
            }).error(function (errResponse) {
                console.error('Error while get size');
                alert('Error while get size');
            });
        }else{
            $http.get("interviewee/size/"+vm.pattern).success(function (response) {
                vm.total_count = response;
            }).error(function (errResponse) {
                console.error('Error while get size');
                alert('Error while get size');
            });
        }

    };

    $scope.dataStudents = {
        studId: []
    };

    $scope.columnsType = {
        columns: []
    };

    vm.getData(); // Call the function to fetch initial data on page load.
    vm.getSize();
    vm.setPageno = function (pageno) {
        vm.pageno = pageno;
        if (vm.order_by === null) {
            vm.selectUrl = "interviewee/list/" + vm.itemsPerPage + "/" + vm.pageno;
        } else {
            vm.selectUrl = "interviewee/list/" + vm.itemsPerPage + "/" + vm.pageno + "/" + vm.order_by + "/" + $scope.sortReverse;
        }
        vm.getData();
        vm.getSize();
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

    $scope.sortType = function (type) {
        vm.showSpin();
        vm.order_by = type;
        vm.selectUrl = "interviewee/list/" + vm.itemsPerPage + "/" + vm.pageno + "/" + vm.order_by + "/" + $scope.sortReverse;
        vm.getData();
        vm.getSize();
    };

    $scope.activateUser = function () {
        
        var dataObj = {
            type: 'activate',
            values: $scope.dataStudents.studId
        };
        if ($scope.dataStudents.studId.length != 0) {
            vm.showSpin();
            var res = $http.post('users', dataObj);
            res.success(function (data, status, headers, config) {
                $scope.message = data;
                vm.getData();
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
            vm.showSpin();
            var res = $http.post('users', dataObj);
            res.success(function (data, status, headers, config) {
                $scope.message = data;
                vm.getData();
            });
            res.error(function (data) {
                alert("failure message: ");
            });
        }
    };

    $scope.searchFiltr = function (pattern) {
        vm.showSpin();
        var dataObj = {
            type: "search",
            values: [$scope.searchFilt]
        };
        vm.pattern = pattern;
        if (pattern == undefined || pattern == "" || pattern == null) {
            vm.selectUrl = "interviewee/list/" + vm.itemsPerPage + "/" + vm.pageno;

        } else {
            vm.pattern = pattern;
            if (vm.order_by === null) {
                vm.selectUrl = "interviewee/search/" + vm.itemsPerPage + "/" + vm.pageno + "/system_user_id/" + pattern;
            } else {
                vm.selectUrl = "interviewee/search/" + vm.itemsPerPage + "/" + vm.pageno + "/" + vm.order_by + "/" + pattern;
            }
        }
        vm.getData();
        vm.getSize();
    }
}]);