var interView = angular.module('interView', ['checklist-model', 'angularUtils.directives.dirPagination']);

interView.controller('interCtrl', ["$http", "$scope", function ($http, $scope) {
    var vm = this;
    vm.users = []; //declare an empty array
    vm.pageno = 1; // initialize page no to 1
    vm.total_count = 0;
    vm.itemsPerPage = 10; //this could be a dynamic value from a drop down
    vm.selectUrl = "interviewers/list/" + vm.itemsPerPage + "/" + vm.pageno;
    vm.order_by = null;
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
        });

        vm.hideSpin();
    };
    vm.getSize = function () {
        if(vm.pattern == null) {
            $http.get("interviewers/size").success(function (response) {
                vm.total_count = response;
            });
        }else{
            $http.get("interviewers/size/"+vm.pattern).success(function (response) {
                vm.total_count = response;
            });
        }
        // elem.find('.modal-content').style.display = "none";

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
            vm.selectUrl = "interviewers/list/" + vm.itemsPerPage + "/" + vm.pageno;
        } else {
            vm.selectUrl = "interviewers/list/" + vm.itemsPerPage + "/" + vm.pageno + "/" + vm.order_by;
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

    $scope.sortType = function (type, asc) {
        vm.showSpin();
        vm.order_by = type;
        vm.selectUrl = "interviewers/list/" + vm.itemsPerPage + "/" + vm.pageno + "/" + vm.order_by + "/" + asc;
        vm.getData();
        vm.getSize();
    };


    $scope.subscribeInterviewer = function () {
        vm.showSpin();
        var dataObj = {
            type: 'subscribe',
            values: $scope.dataStudents.studId
        };
        if ($scope.dataStudents.studId.length != 0) {
            var res = $http.post('interviewers', dataObj);
            res.success(function (data, status, headers, config) {
                $scope.message = data;
                vm.getData();
                vm.getSize();
            });
            res.error(function (data, status, headers, config) {
                alert("failure message: " + JSON.stringify({data: data}));
            });
        }
    };

    $scope.unsubscribeInterviewer = function () {
        vm.showSpin();
        var dataObj = {
            type: 'unsubscribe',
            values: $scope.dataStudents.studId
        };
        if ($scope.dataStudents.studId.length != 0) {
            var res = $http.post('interviewers', dataObj);
            res.success(function (data, status, headers, config) {
                $scope.message = data;
                vm.getData();
                vm.getSize();
            });
            res.error(function (data, status, headers, config) {
                alert("failure message: " + JSON.stringify({data: data}));
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
        vm.getSize();

    }
}]);


it('should change state', function () {
    var value1 = element(by.binding('user.isActive'));

    expect(value1.getText()).toContain('1');

    element(by.model('user.isActive')).click();
    expect(isActive.getText()).toContain('0');

});