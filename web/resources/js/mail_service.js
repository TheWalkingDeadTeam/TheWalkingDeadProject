/**
 * Alexander
 * 03.05.2016
 */

'use strict';

App.factory('MailService', ['$http', '$q', function($http, $q){

    return {

        fetchAllMails: function() {
            return $http.get('/mails/')
                .then(
                    function(response){
                        return response.data;
                    },
                    function(errResponse){
                        console.error('Error while fetching mail');
                        return $q.reject(errResponse);
                    }
                );
        },

        createMail: function(mail){
            return $http.post('http://localhost:8080/mails/', mail)
                .then(
                    function(response){
                        return response.data;
                    },
                    function(errResponse){
                        console.error('Error while creating mail');
                        return $q.reject(errResponse);
                    }
                );
        },

        updateMail: function(mail, id){
            return $http.post('http://localhost:8080/mails/'+id, mail)
                .then(
                    function(response){
                        return response.data;
                    },
                    function(errResponse){
                        console.error('Error while updating mail');
                        return $q.reject(errResponse);
                    }
                );
        },

        deleteMail: function(id){
            return $http.delete('http://localhost:8080/mails/'+id)
                .then(
                    function(response){
                        return response.data;
                    },
                    function(errResponse){
                        console.error('Error while deleting mail');
                        return $q.reject(errResponse);
                    }
                );
        }

    };

}]);
