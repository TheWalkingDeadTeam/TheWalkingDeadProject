var studentView = angular.module('studentView', ['checklist-model', 'angularUtils.directives.dirPagination', 'ui-notification']);
studentView.factory('MailService', ['$http', '$q', function ($http, $q) {

    return {

        fetchAllMails: function () {
            return $http.get('/mails/')
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching mail');
                        return $q.reject(errResponse);
                    }
                );
        },

        createMail: function (mail) {
            return $http.post('/mails/', mail)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while creating mail');
                        return $q.reject(errResponse);
                    }
                );
        },

        updateMail: function (mail, id) {
            return $http.post('/mails/' + id, mail)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while updating mail');
                        return $q.reject(errResponse);
                    }
                );
        },

        deleteMail: function (id) {
            return $http.delete('/mails/' + id)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while deleting mail');
                        return $q.reject(errResponse);
                    }
                );
        }

    };

}]);


studentView.controller('StudentCtrl', ["$http", "$scope", 'MailService', 'Notification', function ($http, $scope, MailService, Notification) {
    var vm = this;
    vm.users = [];
    vm.pageno = 1;
    vm.total_count = 0;
    vm.itemsPerPage = 10;
    vm.selectUrl = "students/list/" + vm.itemsPerPage + "/" + vm.pageno;
    vm.order_by = null;
    vm.sortReverse = false;
    vm.flagReload = true;

    /////////////Alexander///////////////////////////////////////////////
    vm.mails = [];
    vm.mail = {id: null, bodyTemplate: ' ', headTemplate: ' '};
    vm.checkNull = true;

    $scope.list = [];
    $scope.mail = function () {
        var dataObj = {
            values: $scope.dataStudents.studId
        };
        if (dataObj.values.length != 0) {
            var formData = {
                "usersId": dataObj.values,
                "headTemplate": $scope.mailHead,
                "bodyTemplate": $scope.mailBody
            };
            var response = $http.post('/admin/users-mail-id', formData);
            response.success(function (data, status, headers, config) {
                $scope.list.push(data);
            });
            response.error(function (data, status, headers, config) {
                alert("Exception details: " + JSON.stringify({data: data}));
            });
            Notification.success({message: 'Mail successful sent', delay: 1000});
            $scope.list = [];
        } else {
            Notification.error({message: 'You should choose users', delay: 2000});
        }
    };

    $scope.templateSend = function () {

        var dataObj = {
            values: $scope.dataStudents.studId
        };

        if (dataObj.values.length != 0) {
            var formData = {
                "usersId": dataObj.values,
                "mailIdUser": $scope.mailIdUser,
            };
            var response = $http.post('/admin/users-mail-id', formData);
            response.success(function (data, status, headers, config) {
                $scope.list.push(data);
            });
            response.error(function (data, status, headers, config) {
                alert("Exception details: " + JSON.stringify({data: data}));
            });
            Notification.success({message: 'Mail successful sent', delay: 1000});
            $scope.list = [];
        } else {
            Notification.error({message: 'You should choose users', delay: 2000});
        }
    };

    vm.fetchAllMails = function () {
        MailService.fetchAllMails()
            .then(
                function (d) {
                    vm.mails = d;
                },
                function (errResponse) {
                    console.error('Error while fetching Currencies');
                }
            );
    };

    vm.createMail = function (mail) {
        MailService.createMail(mail)
            .then(
                vm.fetchAllMails,
                function (errResponse) {
                    console.error('Error while creating Mail.');
                }
            );
    };

    vm.updateMail = function (mail, id) {
        MailService.updateMail(mail, id)
            .then(
                vm.fetchAllMails,
                function (errResponse) {
                    console.error('Error while updating Mail.');
                }
            );
    };

    vm.deleteMail = function (id) {
        MailService.deleteMail(id)
            .then(
                vm.fetchAllMails,
                function (errResponse) {
                    console.error('Error while deleting Mail.');
                }
            );
    };

    vm.fetchAllMails();

    ///////////////////////////////ALEXANDER////////////////////////


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
            if (vm.flagReload) {
                $scope.headerStud.head = vm.users.header.map(function (item) {
                    return item;
                });
                vm.flagReload = false
            }
        });


        vm.hideSpin();
    };
    $scope.setSize = function (size) {
        if (size == undefined) {
            return
        }
        vm.itemsPerPage = size;
        vm.getData();
        vm.getSize();
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

    $scope.headerStud = {
        head: []
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

    $scope.sortType = function (type,revers) {
        vm.showSpin();
        vm.order_by = type;
        vm.sortReverse = revers;
        vm.selectUrl = "students/list/" + vm.itemsPerPage + "/" + vm.pageno + "/" + vm.order_by + "/" + vm.sortReverse;
        vm.getData();
        vm.getSize();
    };

    $scope.rejectStud = function () {

        var dataObj = {
            type: 'reject',
            values: $scope.dataStudents.studId
        };
        if ($scope.dataStudents.studId.length != 0) {
            vm.showSpin();
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

        var dataObj = {
            type: 'unreject',
            values: $scope.dataStudents.studId
        };
        if ($scope.dataStudents.studId.length != 0) {
            vm.showSpin();
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
