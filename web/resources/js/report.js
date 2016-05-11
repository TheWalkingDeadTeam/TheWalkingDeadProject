/**
 * Created by Pavel on 11.05.2016.
 */

var reporter = angular.module('reporter',[]);

reporter.controller('ReportController', ['$scope', 'ReportService','$http', function($scope, ReportService,$http) {
    var self = this;
    self.reports=[];


    self.fetchReports = function(){
        ReportService.fetchReports()
            .then(
                function(d) {
                    self.reports = d;
                },
                function(errResponse){
                    console.error('Error while fetching Currencies');
                }
            );
    };
}]);

reporter.factory('ReportService', ['$http', '$q', function($http, $q){

    return {

        fetchReports: function() {
            return $http.get('/reports/view/1')
                .then(
                    function(response){
                        console.info(response.data);
                        return response.data;
                    },
                    function(errResponse){
                        console.error('Error while fetching reports');
                        return $q.reject(errResponse);
                    }
                );
        }
    };

}]);
