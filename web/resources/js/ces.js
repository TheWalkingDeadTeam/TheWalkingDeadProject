var app = angular.module('myApp', ['ui-notification']);
app.controller('FormController', ['$scope', 'MailService', '$http', 'Notification', function ($scope, MailService, $http, Notification) {


    $scope.backButton = function () {
        window.location = '/admin';
    };
    var self = this;
    self.mail = {id: null, bodyTemplate: '', headTemplate: ''};
    self.mails = [];

    this.ces = {
        "id": '',
        "year": '',
        "startRegistrationDate": '',
        "endRegistrationDate": '',
        "startInterviewingDate": '',
        "endInterviewingDate": '',
        "quota": '',
        "reminders": '',
        "interviewTimeForPerson": '',
        "interviewTimeForDay": '',
        "statusId": ''
    };
    $scope.current = false;

    self.fetchAllMails = function () {
        MailService.fetchAllMails()
            .then(
                function (d) {
                    self.mails = d;
                },
                function (errResponse) {
                    console.error('Error while fetching Currencies');
                }
            );
    };

    self.fetchAllMails();


    var getReq = function () {
        $http.get('/admin/cessettings').success(function (response) {
            if (response == '') {
                $scope.ctrl.ces.year = '';
                $scope.ctrl.ces.id = '';
                $scope.ctrl.ces.statusId = '';
                $scope.ctrl.ces.startRegistrationDate = '';
                $scope.ctrl.ces.endRegistrationDate = '';
                $scope.ctrl.ces.startInterviewingDate = '';
                $scope.ctrl.ces.endInterviewingDate = '';
                $scope.ctrl.ces.quota = '';
                $scope.ctrl.ces.reminders = '';
                $scope.ctrl.ces.interviewTimeForDay = '';
                $scope.ctrl.ces.interviewTimeForPerson = '';
                expect(element(by.css('[type="date"]')).getAttribute('readonly')).toBeFalsy();
                return;
            }
            if (1 == response.statusId) {
                $scope.current = false;
            } else if (response.statusId < 4) {
                $scope.current = true;
            } else if (response.statusId >= 4) {
                $scope.current = true;
            }
            $scope.ctrl.ces.year = response.year;
            $scope.ctrl.ces.id = response.id;
            $scope.ctrl.ces.statusId = response.statusId;
            $scope.ctrl.ces.startRegistrationDate = new Date(response.startRegistrationDate);
            $scope.ctrl.ces.endRegistrationDate = new Date(response.endRegistrationDate);
            $scope.ctrl.ces.startInterviewingDate = new Date(response.startInterviewingDate);
            $scope.ctrl.ces.endInterviewingDate = new Date(response.endInterviewingDate);
            $scope.ctrl.ces.quota = response.quota;
            $scope.ctrl.ces.reminders = response.reminders;
            $scope.ctrl.ces.interviewTimeForDay = response.interviewTimeForDay;
            $scope.ctrl.ces.interviewTimeForPerson = response.interviewTimeForPerson;
        }).error(function () {
            $scope.postError = true;
        });
    }
    getReq();

    this.save = function () {
        if (isNaN(new Date(this.ces.startRegistrationDate).getTime())) {  // d.valueOf() could also work
            var errors_out = "Start registration date is not valid";
            $('#errorsDiv')
                .removeClass()
                .empty()
                .addClass('alert alert-danger')
                .html(errors_out);
            return;
        }
        if (isNaN(new Date(this.ces.endRegistrationDate).getTime())) {  // d.valueOf() could also work
            var errors_out = "Start registration date is not valid";
            $('#errorsDiv')
                .removeClass()
                .empty()
                .addClass('alert alert-danger')
                .html(errors_out);
            return;
        }

        $http.post('/admin/cesPost', this.ces).success(function (responseData) {
            if (responseData.length) {
                var errors_out = "";
                for (var i in responseData) {
                    errors_out += responseData[i].errorMessage + "</br>"
                }
                $('#errorsDiv')
                    .removeClass()
                    .empty()
                    .addClass('alert alert-danger')
                    .html(errors_out).fadeIn();
            } else {
                $scope.postSuccess = true;
                $('#errorsDiv')
                    .removeClass()
                    .empty()
                    .addClass('alert alert-success')
                    .html('Session saved').fadeIn();
                setTimeout(function () {
                    $('#errorsDiv').fadeOut().empty();
                }, 3000);
            }
            $scope.message = responseData;
        }).error(function () {
            window.location.href = "/error"
        });
    };

    $scope.list = [];
    this.closeButton = function () {
        var formData = {
                "rejection": $scope.rejection,
                "work": $scope.work,
                "course": $scope.course
            };

        if ($scope.rejection && $scope.work && $scope.course != null) {
            // var response = $http.post('/admin/cesclose', formData);
            // response.success(function (data, status, headers, config) {
            //     $scope.list.push(data);
            //     Notification.success({message: 'CES was successfully closed ', delay: 1000});
            // });
            // response.error(function (data, status, headers, config) {
            //     alert("Exception details: " + JSON.stringify({data: data}));
            //     Notification.error({message: 'CES wasn\'t closed', delay: 2000});
            // });
            
            
            $http({
                method: "POST",
                url: '/admin/cesclose',
                data: formData
            }).then(function mySucces(response) {
                Notification.success({message: 'CES was successfully closed ', delay: 1000});
                getReq();
            }, function myError(response) {
                $scope.postError = true;
            });
        } else {
            Notification.error({message: 'All Radio buttons should be specified', delay: 1000});
        }
    }


}]);

/**
 * Fetching avaible mail template
 */
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
        }

    };
}]);

