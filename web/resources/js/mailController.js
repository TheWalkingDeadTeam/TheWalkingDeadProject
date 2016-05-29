'use strict';
/**
 * Created by Alexander Haliy on 05.05.2016.
 */

var mailer = angular.module('mailer', ['ui-notification']);

mailer.controller('MailController', ['$scope', 'MailService', '$http', 'Notification', function ($scope, MailService, $http, Notification) {

    $scope.list = [];

    $scope.submit = function () {
        var formData = {
            "contactStaff": $scope.contactStaff,
            "locations": $scope.location,
            "mailIdUser": $scope.mailIdUser,
            "mailIdStaff": $scope.mailIdStaff,
            "contactStudent": $scope.contactStudent,
            "courseType": $scope.courseType,
            "interviewTime": $scope.interviewTime
        };

        if ($scope.mailIdStaff && $scope.mailIdUser != null) {
            var response = $http.post('/admin/scheduler', formData);
            response.success(function (data, status, headers, config) {
                $scope.list.push(data);
                Notification.success({message: 'Scheduler  successfully started ', delay: 1000});
            });
            response.error(function (data, status, headers, config) {
                alert("Exception details: " + JSON.stringify({data: data}));
                Notification.error({message: 'Scheduler didn\'t start', delay: 2000});
            });
            $scope.list = [];
            $scope.myForm.$setPristine(true);
        } else {
            Notification.error({message: 'Radio button should be specified', delay: 1000});
        }
    };


    var self = this;
    self.mail = {id: null, bodyTemplate: '', headTemplate: ''};
    self.mails = [];


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


    self.updateMail = function (mail, id) {
        MailService.updateMail(mail, id)
            .then(
                self.fetchAllMails,
                function (errResponse) {
                    console.error('Error while updating Mail.');
                }
            );
        Notification.success({message: 'Mail \t "\ ' + mail.headTemplate + '"\ \t successfully updated', delay: 1000});

    };

    self.createMail = function (mail) {
        MailService.createMail(mail)
            .then(
                self.fetchAllMails,
                function (errResponse) {
                    console.error('Error while creating Mail.');
                }
            );
        Notification.success({message: 'Mail \t "\ ' + mail.headTemplate + '"\ \t successfully created', delay: 1000});
    };


    self.deleteMail = function (id) {
        MailService.deleteMail(id)
            .then(
                self.fetchAllMails,
                function (errResponse) {
                    console.error('Error while deleting Mail.');
                }
            );
        Notification.error({message: 'Mail with id: \t ' + id + ' \t successfully deleted', delay: 1000});

    };

    self.fetchAllMails();

    self.submit = function () {
        if (self.mail.id == null) {
            console.log('Saving New Mail', self.mail);
            self.createMail(self.mail);
        } else {
            self.updateMail(self.mail, self.mail.id);
            console.log('Mail updated with id ', self.mail.id);
        }
        self.reset();
    };

    self.edit = function (id) {
        console.log('id to be edited', id);
        for (var i = 0; i < self.mails.length; i++) {
            if (self.mails[i].id == id) {
                self.mail = angular.copy(self.mails[i]);
                break;
            }
        }
    };

    self.remove = function (id) {
        console.log('id to be deleted', id);
        if (self.mail.id === id) {//clean form if the mail to be deleted is shown there.
            self.reset();
        }
        self.deleteMail(id);
    };


    self.reset = function () {
        self.mail = {id: null, bodyTemplate: '', headTemplate: ''};
        $scope.myForm.$setPristine(); //reset Form
    };

}]);


mailer.factory('MailService', ['$http', '$q', function ($http, $q) {

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



