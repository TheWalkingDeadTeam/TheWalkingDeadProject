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
            if(item.id == vm.item.id) {
                vm.itemModel.splice(vm.itemModel.indexOf(item), 1);
                return;
            }
        });
    }

    function onBlurHandler() {
        vm.itemModel.forEach( function (item) {
            if(item.id == vm.item.id) {
                item.value = vm.item.value;
                vm.itemModel.splice(vm.itemModel.indexOf(item), 1, {
                    id: item.id,
                    value: vm.itemValue
                });
                return;
            }
        });
    }

});
