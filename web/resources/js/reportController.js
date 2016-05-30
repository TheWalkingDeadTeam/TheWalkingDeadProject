'use strict';
/**
 * Created by Pavel on 11.05.2016.
 */

var reporter = angular.module('reporter', ['ui-notification']);

reporter.controller('ReportController', ['$scope', 'ReportService', '$http', 'Notification', function ($scope, ReportService, $http, Notification) {
    var self = this;
    self.report = {id: null, name: '', query: ''};
    self.reports = [];


    self.fetchReports = function () {
        ReportService.fetchReports()
            .then(
                function (d) {
                    self.reports = d;
                    if (self.reports.length != 0) {
                        self.reportsTemplateTable = false;
                        self.emptyTemplateTable = true;
                    } else {
                        self.reportsTemplateTable = true;
                        self.emptyTemplateTable = false;
                    }
                },
                function (errResponse) {
                    console.error('Error while fetching Currencies');
                }
            );
    };
    self.fetchReports();


    self.getReport = function (id) {
        ReportService.getReport(id);
    };

    self.createReport = function (report) {
        ReportService.createReport(report)
            .then(
                self.fetchReports,
                function (errResponse) {
                    console.error('Error while creating Report.');
                }
            );
    };

    self.updateReport = function (report, id) {
        ReportService.updateReport(report, id)
            .then(
                self.fetchReports(),
                function (errResponse) {
                    console.error('Error while updating Report.');
                }
            );
    };

    self.deleteReport = function (id) {
        ReportService.deleteReport(id)
            .then(
                self.fetchReports(),
                function (errResponse) {
                    console.error('Error while deleting Report.');
                }
            );
        Notification.info({message: 'Report ' + id + ' deleted', delay: 5000});
    };

    self.submit = function () {
        if (self.report.id == null) {
            console.log('Saving New Mail', self.report);
            self.createReport(self.report);
            Notification.success({message: 'New report ' + self.report.name + ' created', delay: 5000});

        } else {
            console.log('Report updated', self.report.name);
            self.updateReport(self.report, self.report.id);
            Notification.info({message: 'Report ' + self.report.name + ' updated', delay: 5000});

        }
        self.reset();
    };

    self.edit = function (id) {
        console.log('id to be edited', id);
        for (var i = 0; i < self.reports.length; i++) {
            if (self.reports[i].id == id) {
                self.report = angular.copy(self.reports[i]);
                break;
            }
        }
    };

    self.remove = function (id) {
        console.log('Report deleted', id);
        if (self.report.id === id) {//clean form if the report to be deleted is shown there.
            self.reset();
        }
        self.deleteReport(id);
    };


    self.reset = function () {
        self.report = {id: null, name: '', query: ''};
        $scope.myForm.$setPristine(); //reset Form
    };

}]);

reporter.factory('ReportService', ['$http', '$q', function ($http, $q) {

    return {

        fetchReports: function () {
            return $http.get('/reports')
                .then(
                    function (response) {
                        console.info(response.data);
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching reports');
                        return $q.reject(errResponse);
                    }
                );
        },
        getReport: function (id) {
            return $http.get('/reports/view/' + id)
                .then(
                    function (response) {
                        console.info(response.data);
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while get report ' + id);
                        return $q.reject(errResponse);
                    }
                );
        },
        createReport: function (report) {
            return $http.post('/reports/', report)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while creating report');
                        return $q.reject(errResponse);
                    }
                );
        },

        updateReport: function (report, id) {
            return $http.post('/reports/' + id, report)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while updating report');
                        return $q.reject(errResponse);
                    }
                );
        },

        deleteReport: function (id) {
            return $http.delete('/reports/' + id)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while deleting report');
                        return $q.reject(errResponse);
                    }
                );
        }
    };

}]);
