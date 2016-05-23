'use strict';
/**
 * Created by Pavel on 11.05.2016.
 */

var reporterid = angular.module('reporterid', []);

reporterid.controller('ReportControllerId', ['$scope', 'ReportServiceId', '$http', '$location', '$window', function ($scope, ReportServiceId, $http, $location, $window) {
    var self = this;
    self.report = {id: null, name: '', query: ''};
    self.reportRows = [];


    self.getReport = function (id) {
        ReportServiceId.getReport(id)
            .then(
                function (d) {
                    self.loading = true;
                    self.report = d.report;
                    self.reportRows = d.reportRows;
                }, function (errResponse) {
                    console.error('Error while fetching Currencies');
                    $window.location.href = '/error';
                }
            );
    };

    self.loading = false;
    var pId = $location.absUrl().split('?')[1];
    self.getReport(pId);
    console.info(pId);




}]);

reporterid.factory('ReportServiceId', ['$http', '$q', function ($http, $q) {

    return {

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
        }
    };

}]);
