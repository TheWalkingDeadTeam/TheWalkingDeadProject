'use strict';


App.controller('MailController', ['$scope', 'MailService', function($scope, MailService) {
    var self = this;
    self.mail={id:null,bodyTemplate:'',headTemplate:''};
    self.mails=[];

    self.fetchAllMails = function(){
        MailService.fetchAllMails()
            .then(
                function(d) {
                    self.mails = d;
                },
                function(errResponse){
                    console.error('Error while fetching Currencies');
                }
            );
    };

    self.createMail = function(mail){
        MailService.createMail(mail)
            .then(
                self.fetchAllMails,
                function(errResponse){
                    console.error('Error while creating Mail.');
                }
            );
    };

    self.updateMail = function(mail, id){
        MailService.updateMail(mail, id)
        MailService.updateMail(mail, id)
            .then(
                self.fetchAllMails,
                function(errResponse){
                    console.error('Error while updating Mail.');
                }
            );
    };

    self.deleteMail= function(id){
        MailService.deleteMail(id)
            .then(
                self.fetchAllMails,
                function(errResponse){
                    console.error('Error while deleting Mail.');
                }
            );
    };

    self.fetchAllMails();

    self.submit = function() {
        if(self.mail.id==null){
            console.log('Saving New Mail', self.mail);
            self.createMail(self.mail);
        }else{
            self.updateMail(self.mail, self.mail.id);
            console.log('Mail updated with id ', self.mail.id);
        }
        self.reset();
    };

    self.edit = function(id){
        console.log('id to be edited', id);
        for(var i = 0; i < self.mails.length; i++){
            if(self.mails[i].id == id) {
                self.mail = angular.copy(self.mails[i]);
                break;
            }
        }
    };

    self.remove = function(id){
        console.log('id to be deleted', id);
        if(self.mail.id === id) {//clean form if the mail to be deleted is shown there.
            self.reset();
        }
        self.deleteMail(id);
    };


    self.reset = function(){
        self.mail={id:null,bodyTemplate:'',headTemplate:''};
        $scope.myForm.$setPristine(); //reset Form
    };

}]);
