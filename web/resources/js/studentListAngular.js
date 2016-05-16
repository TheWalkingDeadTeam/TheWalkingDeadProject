var app = angular.module('studentView', ['checklist-model', 'angularUtils.directives.dirPagination','ui-notification']);

app.factory('MailService', ['$http', '$q', function ($http, $q) {

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


app.controller('StudentCtrl', ["$http", "$scope", 'MailService','Notification', function ($http, $scope, MailService,Notification) {
    var vm = this;
    vm.users = []; //declare an empty array
    vm.pageno = 1; // initialize page no to 1
    vm.total_count = 0;
    vm.itemsPerPage = 10; //this could be a dynamic value from a drop down
    vm.selectUrl = "students/list/" + vm.itemsPerPage + "/" + vm.pageno;
    vm.order_by = null;
    $scope.sortReverse = false;



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

        if(dataObj.values.length != 0){
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

    $scope.sortType = function (type, revers) {

        vm.order_by = type;
        vm.selectUrl = "students/list/" + vm.itemsPerPage + "/" + vm.pageno + "/" + vm.order_by + "/" + revers;

        vm.getData()
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
        var dataObj = {
            type: "search",
            values: [$scope.searchFilt]
        };
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

    }
}]);


it('should change state', function () {
    var value1 = element(by.binding('user.isActive'));

    expect(value1.getText()).toContain('1');

    element(by.model('user.isActive')).click();
    expect(isActive.getText()).toContain('0');

});