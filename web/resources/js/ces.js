var app = angular.module('myApp', []);
app.controller('FormController', ['$scope', '$http', function ($scope, $http) {



    $scope.backButton = function() {
        window.location = '/admin';
    };

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
    $scope.interviewBegan = false;

    var getReq = function() {
        $http.get('/admin/cessettings').success(function (response) {
            console.log(response)
            if (response == '') {
                $scope.ctrl.ces.year = '';
                $scope.ctrl.ces.id = '';
                $scope.ctrl.ces.statusId = '';
                $scope.ctrl.ces.startRegistrationDate = new Date(response.startRegistrationDate);
                $scope.ctrl.ces.endRegistrationDate = '';
                $scope.ctrl.ces.startInterviewingDate = '';
                $scope.ctrl.ces.endInterviewingDate = '';
                $scope.ctrl.ces.quota = '';
                $scope.ctrl.ces.reminders = '';
                $scope.ctrl.ces.interviewTimeForDay = '';
                $scope.ctrl.ces.interviewTimeForPerson = '';
                $scope.current = false;
                $scope.interviewBegan = false;
                return;
            }
            if (1 == response.statusId) {
                $scope.current = false;
                $scope.interviewBegan = false;
            } else if (response.statusId < 4) {
                $scope.current = true;
                $scope.interviewBegan = false;
            } else if (response.statusId >= 4) {
                $scope.current = true;
                $scope.interviewBegan = true;
            }
            $scope.ctrl.ces.year = response.year;
            $scope.ctrl.ces.id = response.id;
            $scope.ctrl.ces.statusId = response.statusId;
            $scope.ctrl.ces.startRegistrationDate = new Date(response.startRegistrationDate);
            $scope.ctrl.ces.endRegistrationDate = new Date(response.endRegistrationDate);
            if (response.startInterviewingDate != null) {
                $scope.ctrl.ces.startInterviewingDate = new Date(response.startInterviewingDate);
            }
            if (response.endInterviewingDate != null){
                $scope.ctrl.ces.endInterviewingDate = new Date(response.endInterviewingDate);
            }
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
        if ( isNaN( new Date(this.ces.startRegistrationDate).getTime() ) ) {  // d.valueOf() could also work
            var errors_out = "Start registration date is not valid";
            $('#errorsDiv')
                .removeClass()
                .empty()
                .addClass('alert alert-danger')
                .html(errors_out);
            return;
        }
        if ( isNaN( new Date(this.ces.endRegistrationDate).getTime() ) ) {  // d.valueOf() could also work
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
                        .html(errors_out);
                } else {
                    $scope.postSuccess = true;
                    $('#errorsDiv')
                        .removeClass()
                        .empty()
                        .addClass('alert alert-success')
                        .html('Session saved');
                }
                $scope.message = responseData;
            }).error(function () {
            window.location.href = "/error"
        });
    };
    this.closeButton = function(){
        $http({
            method : "POST",
            url : '/admin/cesclose'
        }).then(function mySucces(response) {
            if (confirm("Session will be removed") == true) {
                getReq();
            }

        }, function myError(response) {
            $scope.postError = true;
        });
    }
}]);

