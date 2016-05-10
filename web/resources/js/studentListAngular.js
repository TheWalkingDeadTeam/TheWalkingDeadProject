/**
 * Created by creed on 01.05.16.
 */
//
// var studentView = angular.module('studentView', ['checklist-model', 'ngRoute', 'phonecatControllers']);
//
// studentView.config(['$routeProvider',
//     function ($routeProvider) {
//         $routeProvider.when('/students', {
//             templateUrl: 'admin-stud-view.jsp',
//             controller: 'StudentCtrl'
//         }).otherwise({
//             redirectTo: 'error.jsp'
//         });
//     }]);
//
// var phonecatControllers = angular.module('phonecatControllers', []);
//
//
// phonecatControllers.controller("StudentCtrl", [ "$http",  function ($http) {
//     // $http.get('resources/json/studentsData.json').success(function (data) {
//     //     $scope.interviewer = data;
//     // });
//
//     // $http.get('students/list').success(function (data) {
//     //     $scope.students = data;
//     // });
//     //
//
//         var vm = this;
//         vm.users = []; //declare an empty array
//         vm.pageno = 1; // initialize page no to 1
//         vm.total_count = 0;
//         vm.itemsPerPage = 10; //this could be a dynamic value from a drop down
//
//         vm.getData = function(pageno){ // This would fetch the data on page change.
//             //In practice this should be in a factory.
//             vm.users = [];
//             $http.get("students/list").success(function(response){
//                 vm.users = response.data;  //ajax request to fetch data into vm.data
//                 vm.total_count = response.total_count;
//             });
//         };
//         vm.getData(vm.pageno); // Call the function to fetch initial data on page load.
//
//
//     // var vm = this;
//     // vm.dataStudents = {
//     //     studId: []
//     // };
//     // vm.students = []; //declare an empty array
//     // vm.pageno = 1; // initialize page no to 1
//     // vm.total_count = 10;
//     // vm.itemsPerPage = 10; //this could be a dynamic value from a drop down
//     // vm.getData = function(pageno){ // This would fetch the data on page change.
//     //     //In practice this should be in a factory.
//     //     $http.get("students/list").success(function(response){
//     //         //ajax request to fetch data into vm.data
//     //         vm.students = response.data;  // data to be displayed on current page.
//     //         console.log("+++++++++++");
//     //         console.log(vm.students);
//     //         vm.total_count = response.total_count; // total data count.
//     //         console.log("+++++++++++");
//     //         console.log(vm.total_count);
//     //     });
//     // };
//     // vm.getData(vm.pageno);
//     // console.log("+++++++++++");
//     // console.log(vm.students);
//
//
//     // $scope.itemsPerPages = 10;
//     // $scope.total_count = 0;
//     // $scope.sortType = 'id';
//     // $scope.sortReverse = false;
//     // $scope.searchFiltr = '';
//
//     // $scope.checkAll = function () {
//     //     if ($scope.selectedAll) {
//     //         $scope.dataStudents.studId = $scope.students.map(function (item) {
//     //             return item.id;
//     //         });
//     //         $scope.selectdAll = true;
//     //     }
//     //     else {
//     //         $scope.selectdAll = false;
//     //         $scope.dataStudents.studId = [];
//     //     }
//     //
//     // };
//     // $scope.activateStud = function () {
//     //     var dataObj = {
//     //         type: 'activate',
//     //         values: $scope.dataStudents.studId
//     //     };
//     //     if ($scope.dataStudents.studId.length != 0) {
//     //         $http.post('students', dataObj)
//     //             .success(function (data, status, headers, config) {
//     //             $scope.message = data;
//     //         })
//     //             .error(function (data, status, headers, config) {
//     //             // alert("failure message: " + JSON.stringify({data: data}));
//     //             // alert($scope.message = data);
//     //         });
//     //     }
//     // };
//     // $scope.deactivateStud = function () {
//     //     var dataObj = {
//     //         type: 'deactivate',
//     //         values: $scope.dataStudents.studId
//     //     };
//     //     if ($scope.dataStudents.studId.length != 0) {
//     //         var res = $http.post('students', dataObj);
//     //         res.success(function (data, status, headers, config) {
//     //             $scope.message = data;
//     //         });
//     //         res.error(function (data, status, headers, config) {
//     //             alert("failure message: " + JSON.stringify({data: data}));
//     //         });
//     //     }
//     // };
//     // $scope.rejectStud = function () {
//     //     var dataObj = {
//     //         type: 'reject',
//     //         values: $scope.dataStudents.studId
//     //     };
//     //     if ($scope.dataStudents.studId.length != 0) {
//     //         var res = $http.post('students', dataObj);
//     //         res.success(function (data, status, headers, config) {
//     //             $scope.message = data;
//     //         });
//     //         res.error(function (data, status, headers, config) {
//     //             alert("failure message: " + JSON.stringify({data: data}));
//     //         });
//     //     }
//     // };
//     // $scope.saveChanges = function () {
//     //     var dataObj = {
//     //         type: "save",
//     //         values: []
//     //     };
//     //     var res = $http.post('students', dataObj);
//     //     res.success(function (data, status, headers, config) {
//     //         $scope.message = data;
//     //     });
//     //     res.error(function (data, status, headers, config) {
//     //         alert("failure message: " + JSON.stringify({data: data}));
//     //     });
//     // }
//     //
//     // $scope.searchFiltr = function () {
//     //     var dataObj = {
//     //         type: "search",
//     //         values: [$scope.searchFilt]
//     //     };
//     //     var res = $http.post('students', dataObj);
//     //     res.success(function (data, status, headers, config) {
//     //         $scope.message = data;
//     //     });
//     //     res.error(function (data, status, headers, config) {
//     //         alert("failure message: " + JSON.stringify({data: data}));
//     //     });
//     // }
// }]);

var app = angular.module('studentView', ['checklist-model', 'angularUtils.directives.dirPagination']);

app.controller('StudentCtrl', ["$http", "$scope", function ($http, $scope) {
    var vm = this;
    vm.users = []; //declare an empty array
    vm.pageno = 1; // initialize page no to 1
    vm.total_count = 0;
    vm.itemsPerPage = 10; //this could be a dynamic value from a drop down
    $scope.sortType = 'id';
    $scope.sortReverse = false;



    vm.getData = function (pageno) { // This would fetch the data on page change.
        //In practice this should be in a factory.
        vm.users = [];
        // "students/list/"+vm.itemsPerPage+"/"+pageno
        $http.get("students/list/" + vm.itemsPerPage + "/" + pageno + "/" +$scope.sortType + "/" +$scope.sortReverse).success(function (response) {
            vm.users = response;
        });
        $http.get("students/size").success(function (response) {
            vm.total_count = response.size;
        });
    };

    // $scope.sortBy() = function (pageno) {
    //     var dataObj = {
    //         sortBy: $scope.sortType,
    //         sortOrder: $scope.sortReverse
    // },
    //     $http.post('stodents/sort', dataObj)
    //         .success(function (data) {
    //
    //         })
    // }
    //
    $scope.dataStudents = {
        studId: []
    };
    
    $scope.columnsType = {
        columns: []
    };
    
    vm.getData(vm.pageno); // Call the function to fetch initial data on page load.

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

    }



    $scope.activateStud = function () {
        var dataObj = {
            type: 'activate',
            values: $scope.dataStudents.studId
        };
        if ($scope.dataStudents.studId.length != 0) {
            $http.post('students', dataObj)
                .success(function (data, status, headers, config) {
                    $scope.message = data;
                })
                .error(function (data, status, headers, config) {
                    // alert("failure message: " + JSON.stringify({data: data}));
                    // alert($scope.message = data);
                });
        }
    };
    $scope.deactivateStud = function () {
        var dataObj = {
            type: 'deactivate',
            values: $scope.dataStudents.studId
        };
        if ($scope.dataStudents.studId.length != 0) {
            var res = $http.post('students', dataObj);
            res.success(function (data, status, headers, config) {
                $scope.message = data;
            });
            res.error(function (data, status, headers, config) {
                alert("failure message: " + JSON.stringify({data: data}));
            });
        }
    };
    $scope.rejectStud = function () {
        var dataObj = {
            type: 'reject',
            values: $scope.dataStudents.studId
        };
        if ($scope.dataStudents.studId.length != 0) {
            var res = $http.post('students', dataObj);
            res.success(function (data, status, headers, config) {
                $scope.message = data;
            });
            res.error(function (data, status, headers, config) {
                alert("failure message: " + JSON.stringify({data: data}));
            });
        }
    };
    // $scope.saveChanges = function () {
    //     var dataObj = {
    //         type: "save",
    //         values: []
    //     };
    //     var res = $http.post('students', dataObj);
    //     res.success(function (data, status, headers, config) {
    //         $scope.message = data;
    //     });
    //     res.error(function (data, status, headers, config) {
    //         alert("failure message: " + JSON.stringify({data: data}));
    //     });
    // }

    $scope.searchFiltr = function () {
        var dataObj = {
            type: "search",
            values: [$scope.searchFilt]
        };
        var res = $http.post('students', dataObj);
        res.success(function (data, status, headers, config) {
            $scope.message = data;
        });
        res.error(function (data, status, headers, config) {
            alert("failure message: " + JSON.stringify({data: data}));
        });
    }
}]);


it('should change state', function () {
    var value1 = element(by.binding('user.isActive'));

    expect(value1.getText()).toContain('1');

    element(by.model('user.isActive')).click();
    expect(isActive.getText()).toContain('0');

});