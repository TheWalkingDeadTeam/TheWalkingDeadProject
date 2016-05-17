var sendFormModule = angular.module('sendFormModule');

sendFormModule.component('inputField', {
    bindings: {
        itemModel: '=',
        item: '<'
    },
    controller: 'InputFieldController',
    controllerAs: 'inputFieldCtrl',
    templateUrl: '/resources/js/InputFieldComponent/input-field.html'
});

sendFormModule.controller('InputFieldController', function ($scope) {
    var vm = this;
    
    vm.$onInit = init;
    vm.onBlurHandler = onBlurHandler;
    vm.removeItem = removeItem;
    
    function init() {
        vm.itemValue = vm.item.value;
    }

    function removeItem() {
        vm.itemModel.forEach( function (item) {
            if(item.opt_id == vm.item.opt_id) {
                vm.itemModel.splice(vm.itemModel.indexOf(item), 1);
                return;
            }
        });
    }

    function onBlurHandler() {
        vm.itemModel.forEach( function (item) {
            if(item.opt_id == vm.item.opt_id) {
                item.value = vm.item.value;
                vm.itemModel.splice(vm.itemModel.indexOf(item), 1, {
                    opt_id: item.opt_id,
                    value: vm.itemValue
                });
                return;
            }
        });
    }

});
