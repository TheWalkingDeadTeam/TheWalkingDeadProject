var app = angular.module('studentView', ['checklist-model', 'angularUtils.directives.dirPagination']);

app.controller('StudentCtrl', ["$http", "$scope", function ($http, $scope) {
    var vm = this;
    vm.users = []; //declare an empty array
    vm.pageno = 1; // initialize page no to 1
    vm.total_count = 0;
    vm.itemsPerPage = 10; //this could be a dynamic value from a drop down
    vm.selectUrl = "students/list/" + vm.itemsPerPage + "/" + vm.pageno;
    vm.order_by = null;
    $scope.sortReverse = false;

    vm.showSpin = function () {
        angular.element($(".cssload-thecube")).css('display', 'block');
        angular.element($("#tableUsers")).css('display', 'none');
        angular.element($("#pagination")).css('display', 'none');
    };
    vm.hideSpin = function () {
        angular.element($(".cssload-thecube")).css('display', 'none');
        angular.element($("#tableUsers")).css('display', 'table');
        angular.element($("#pagination")).css('display', 'block');
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
        if (vm.pattern == null) {
            $http.get("students/size").success(function (response) {
                vm.total_count = response;
            });
        } else {
            $http.get("students/size/" + vm.pattern).success(function (response) {
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
            vm.selectUrl = "students/list/" + vm.itemsPerPage + "/" + vm.pageno;
        } else {
            vm.selectUrl = "students/list/" + vm.itemsPerPage + "/" + vm.pageno + "/" + vm.order_by;
        }
        vm.getData();
    };

    $scope.checkAll = function () {
        if ($scope.selectedAll) {
            $scope.dataStudents.studId = vm.users.rows.map(function (item) {
                return item.userId;
            });
            $scope.selectdAll = true;
        }
        else {
            $scope.selectdAll = false;
            $scope.dataStudents.studId = [];
        }

    };

    $scope.sortType = function (type, revers) {
        vm.showSpin();
        vm.order_by = type;
        vm.selectUrl = "students/list/" + vm.itemsPerPage + "/" + vm.pageno + "/" + vm.order_by + "/" + revers;

        vm.getData();
        vm.getSize();
    };

    $scope.rejectStud = function () {
        vm.showSpin();
        var dataObj = {
            type: 'reject',
            values: $scope.dataStudents.studId
        };
        if ($scope.dataStudents.studId.length != 0) {
            var res = $http.post('students', dataObj);
            res.success(function (data, status, headers, config) {
                $scope.message = data;
                vm.getData();

            });
            res.error(function (data, status, headers, config) {
                alert("failure message: " + JSON.stringify({data: data}));
            });
        }
    };

    $scope.unrejectStud = function () {
        vm.showSpin();
        var dataObj = {
            type: 'unreject',
            values: $scope.dataStudents.studId
        };
        if ($scope.dataStudents.studId.length != 0) {
            var res = $http.post('students', dataObj);
            res.success(function (data, status, headers, config) {
                $scope.message = data;
                vm.getData();
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
            vm.selectUrl = "students/list/" + vm.itemsPerPage + "/" + vm.pageno;

        } else {
            if (vm.order_by === null) {
                vm.selectUrl = "students/search/" + vm.itemsPerPage + "/" + vm.pageno + "/" + pattern;
            } else {
                vm.selectUrl = "students/search/" + vm.itemsPerPage + "/" + vm.pageno + "/" + vm.order_by + "/" + pattern;
            }
        }
        vm.getData();
        vm.getSize();
    }
}]);

//
//
//
// var app = angular.module('studentView', ['checklist-model', 'angularUtils.directives.dirPagination']);
//
// app.controller('StudentCtrl', ["$http", "$scope", function ($http, $scope) {
//     var vm = this;
//     vm.users = []; //declare an empty array
//     vm.pageno = 1; // initialize page no to 1
//     vm.total_count = 0;
//     vm.itemsPerPage = 10; //this could be a dynamic value from a drop down
//     vm.selectUrl = "students/list/" + vm.itemsPerPage + "/" + vm.pageno;
//     vm.order_by = null;
//     vm.sortAsc = null;
//     vm.pattern = null;
//     $scope.sortReverse = false;
//     showSpin = function () {
//         angular.element($(".cssload-thecube")).css('display','block');
//         angular.element($("#tableUsers")).css('display','none');
//         angular.element($("#pagination")).css('display','none');
//     };
//     hideSpin = function () {
//         angular.element($(".cssload-thecube")).css('display','none');
//         angular.element($("#tableUsers")).css('display','table');
//         angular.element($("#pagination")).css('display','block');
//     };
//
//     vm.getData = function () {
//         showSpin();
//
//         vm.users = [];
//         // $http.get(vm.selectUrl).success(function (response) {
//         //     vm.users = response;
//         //     console.log(response);
//         // });
//         var response = $http.get(vm.selectUrl);
//         response.success(function (data, status, headers, config) {
//             // $scope.list.push(data);
//             console.log(data);
//         });
//         response.error(function (data, status, headers, config) {
//             console.log(data);
//             alert("Exception details: " + JSON.stringify({data: data}));
//         });
//         hideSpin();
//     };
//     vm.getSize = function () {
//         if(vm.pattern == null) {
//             $http.get("students/size").success(function (response) {
//                 vm.total_count = response;
//             });
//         }else{
//             $http.get("students/size/"+vm.pattern).success(function (response) {
//                 vm.total_count = response;
//             });
//         }
//         // elem.find('.modal-content').style.display = "none";
//
//     };
//
//     $scope.dataStudents = {
//         studId: []
//     };
//
//     $scope.columnsType = {
//         columns: []
//     };
//
//     vm.getData(); // Call the function to fetch initial data on page load.
//     // vm.getSize();
//     vm.setPageno = function (pageno) {
//         vm.pageno = pageno;
//         if (vm.order_by === null) {
//             vm.selectUrl = "students/list/" + vm.itemsPerPage + "/" + vm.pageno;
//         } else {
//             vm.selectUrl = "students/list/" + vm.itemsPerPage + "/" + vm.pageno + "/" + vm.order_by + "/" + vm.sortAsc;
//         }
//         vm.getData();
//         vm.getSize();
//     };
//
//     $scope.checkAll = function () {
//         if ($scope.selectedAll) {
//             $scope.dataStudents.studId = vm.users.map(function (item) {
//                 return item.id;
//             });
//             $scope.selectdAll = true;
//         }
//         else {
//             $scope.selectdAll = false;
//             $scope.dataStudents.studId = [];
//         }
//
//     };
//
//     $scope.sortType = function (type,revers) {
//         vm.order_by = type;
//         vm.sortAsc = revers;
//         vm.selectUrl = "students/list/" + vm.itemsPerPage + "/" + vm.pageno + "/" + vm.order_by + "/" + vm.sortAsc;
//         vm.getData();
//         vm.getSize();
//     };
//
//     $scope.rejectStud = function () {
//         vm.showSpin();
//         var dataObj = {
//             type: 'reject',
//             values: $scope.dataStudents.studId
//         };
//         if ($scope.dataStudents.studId.length != 0) {
//             var res = $http.post('students', dataObj);
//             res.success(function (data, status, headers, config) {
//                 $scope.message = data;
//                 vm.getData();
//
//             });
//             res.error(function (data, status, headers, config) {
//                 alert("failure message: " + JSON.stringify({data: data}));
//             });
//         }
//     };
//
//     $scope.unrejectStud = function () {
//         vm.showSpin();
//         var dataObj = {
//             type: 'unreject',
//             values: $scope.dataStudents.studId
//         };
//         if ($scope.dataStudents.studId.length != 0) {
//             var res = $http.post('students', dataObj);
//             res.success(function (data, status, headers, config) {
//                 $scope.message = data;
//                 vm.getData();
//             });
//             res.error(function (data, status, headers, config) {
//                 alert("failure message: " + JSON.stringify({data: data}));
//             });
//         }
//     };
//
//     $scope.searchFiltr = function (pattern) {
//         showSpin();
//         var dataObj = {
//             type: "search",
//             values: [$scope.searchFilt]
//         };
//         vm.pattern = pattern;
//         if (pattern == undefined || pattern == "" || pattern == null) {
//             vm.selectUrl = "students/list/" + vm.itemsPerPage + "/" + vm.pageno;
//
//         } else {
//             if (vm.order_by === null) {
//                 vm.selectUrl = "students/search/" + vm.itemsPerPage + "/" + vm.pageno + "/system_user_id/" + pattern;
//             } else {
//                 vm.selectUrl = "students/search/" + vm.itemsPerPage + "/" + vm.pageno + "/" + vm.order_by + "/" + pattern;
//             }
//         }
//         vm.getData();
//         vm.getSize();
//     }
// }]);
