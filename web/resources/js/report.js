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
                    self.colspan = Object.keys(self.reportRows[0]).length;
                    if (self.reportRows[0][Object.keys(self.reportRows[0])[0]] != null  ) {
                        self.reportTemplateTable = false;
                        self.emptyTemplateTable = true;
                    } else {
                        self.reportTemplateTable = true;
                        self.emptyTemplateTable = false;
                    }
                }, function (errResponse) {
                    console.error('Error while fetching Currencies');
                    $window.location.href = '/report/error';
                }
            );
    };

    self.loading = false;
    var pId = $location.absUrl().split('?')[1];
    self.getReport(pId);
    console.info(pId);




}]);

reporterid.factory('ReportServiceId', ['$http', '$q' , '$window', function ($http, $q, $window) {

    return {

        getReport: function (id) {
            var reportUrl = '/reports/view/';
            if ($window.location.pathname == '/report/view/ces') {
                reportUrl = '/reports/view/ces/';
            }
            return $http.get(reportUrl + id)
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
