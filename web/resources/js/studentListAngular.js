/**
 * Created by creed on 01.05.16.
 */

var studentView = angular.module('studentView', ['ngMaterial', 'ngMessages', 'material.svgAssetsCache', 'checklist-model', 'angularUtils.directives.dirPagination']);

studentView.factory('UserMailService', ['$http', '$q', function ($http, $q) {
    return {
        fetchUserSendMail: function (users) {
            return $http.post('/admin/users-mail-id', users)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching users to send email');
                        return $q.reject(errResponse);
                    }
                );
        }
    };
}]);

studentView.controller('StudentCtrl', ["$http", "$scope", "$mdDialog", "$mdMedia", 'UserMailService', function ($http, $scope, $mdDialog, $mdMedia, UserMailService) {
    var vm = this;
    vm.users = []; //declare an empty array
    vm.pageno = 1; // initialize page no to 1
    vm.total_count = 0;
    vm.itemsPerPage = 10; //this could be a dynamic value from a drop down
    vm.selectUrl = "students/list/" + vm.itemsPerPage + "/" + vm.pageno;
    vm.order_by = null;
    $scope.sortReverse = false;

    $scope.dataStudents = {
        studId: []
    };

    $scope.redirectArray = {
        usersWithMail: []
    }

    $scope.nameTest = "helllo from scope";
    //Alexander Haliy script


    vm.users_by_mail = [];
    vm.user_name = {id: null, email: ''};
    $scope.status = '  ';
    $scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

    $scope.showCustom = function (event) {
        
        // console.info(dataObj.values.length);

        vm.fetchUserSendMail = function (users) {
            UserMailService.fetchUserSendMail(users)
                .then(
                    function (d) {
                        $scope.redirectArray.usersWithMail = d;
                    },
                    function (errResponse) {
                        console.error('Error while fetching Currencies');
                    }
                );
        };

        var dataObj = {
            values: $scope.dataStudents.studId
        };

        if ($scope.dataStudents.studId.length != 0) {


            vm.fetchUserSendMail(dataObj);


            $mdDialog.show({
                clickOutsideToClose: true,
                controller: function ($mdDialog) {
                    this.parent = $scope;
                    $scope.closeDialog = function () {
                        $mdDialog.hide();
                    }
                },
                controllerAs: 'dialogCtrl',
                preserveScope: true,
                targetEvent: event,
                fullscreen: $scope.customFullscreen,
                templateUrl: '/admin/mail-personal',
                parent: angular.element(document.querySelector('#dialogContainer')),
            });
        }
    };

    $scope.$watch(function () {
        return $mdMedia('xs') || $mdMedia('sm');
    }, function (wantsFullScreen) {
        $scope.customFullscreen = (wantsFullScreen === true);
    });

    //ALEXANDER CODE ENDS HERE


    vm.getData = function () { // This would fetch the data on page change.
        //In practice this should be in a factory.
        vm.users = [];
        // "students/list/"+vm.itemsPerPage+"/"+pageno
        $http.get(vm.selectUrl).success(function (response) {
            vm.header = response.header;
            vm.users = response.rows;
            // vm.order_by = vm.header[0].id;
        });
        $http.get("students/size").success(function (response) {
            vm.total_count = response.size;
        });
    };


    $scope.columnsType = {
        columns: []
    };

    vm.getData(); // Call the function to fetch initial data on page load.

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

    $scope.sortType = function (type) {
        // vm.users = [];
        vm.order_by = type;
        vm.selectUrl = "students/list/" + vm.itemsPerPage + "/" + vm.pageno + "/" + vm.order_by;
        // // "students/list/"+vm.itemsPerPage+"/"+pageno
        // $http.get("students/list/" + vm.itemsPerPage + "/" + vm.pageno + "/" + type).success(function (response) {
        //     vm.header = response.header;
        //     vm.users = response.rows;
        // });
        // $http.get("students/size").success(function (response) {
        //     vm.total_count = response.size;
        // });
        vm.getData()
    };


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

    $scope.searchFiltr = function () {
        var dataObj = {
            type: "search",
            values: [$scope.searchFilt]
        };
        var res = $http.get('students/search', dataObj);
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


