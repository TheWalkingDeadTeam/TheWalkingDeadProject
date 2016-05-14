/**
 * Created by Neltarion on 12.05.2016.
 */
// $(document).ready(function() {
//
//     $('input').blur(function() {
//
//         // check if the input has any value (if we've typed into it)
//         if ($(this).val())
//             $(this).addClass('used');
//         else
//             $(this).removeClass('used');
//     });
//
//
//
// });

var sendNewQuestForm = angular.module('sendForm', ['sendController']);

var sendConrtoller = angular.module('sendController', []);

sendConrtoller.controller('sendFr', ["$scope", "$http", function ($scope, $http) {
    var vm = this;

    vm.isShown = false;
    vm.isRequired = false;

    vm.newQuestion = {
        name: '',
        fieldTypeID: '',
        multipleChoice: '',
        orderNum: '',
        listTypeID: ''
    };

    $scope.change = function () {
        var flag = (vm.newQuestion.fieldTypeID == 4 || vm.newQuestion.fieldTypeID == 5 || vm.newQuestion.fieldTypeID == 6);
        if (flag == true) {
            if (vm.newQuestion.fieldTypeID == 5) {
                vm.newQuestion.multipleChoice = "true";
            } else {
                vm.newQuestion.multipleChoice = "false";
            }
            if (flag == true) {
                $('.newInputs').attr('required', 'true');
            } else {
                $('.newInputs').removeAttr('required');
            }
            vm.isShown = true;
        } else {
            $('.newInputs').removeAttr('required');
            vm.newQuestion.newOpt = '';
            vm.newQuestion.listTypeID = '';
            vm.newQuestion.multipleChoice = "false";
            vm.isShown = false;
        }

        
    };

    vm.save = function () {
        $http.post('/admin/edit-form/new-question', vm.newQuestion).success(function (responseData) {
            if (responseData.length) {
                var errors_out = "";
                for (var i in responseData) {
                    errors_out += responseData[i].errorMessage + "</br>"
                }
                $('#messageDiv')
                    .removeClass()
                    .empty()
                    .addClass('alert alert-danger')
                    .html(errors_out);
            } else {
                $scope.postSuccess = true;
                $('#messageDiv')
                    .removeClass()
                    .empty()
                    .addClass('alert alert-success')
                    .html('Saved successfully');
            }
        }).error(function () {
            $scope.postError = true;
        });
    }

    // $scope.items = [];
    //
    // $scope.add = function () {
    //     $scope.items.push({
    //         inlineChecked: false,
    //         question: '',
    //         questionPlaceholder: 'Option name',
    //         text: ''
    //     });
    // };
    
    $scope.items = [];
    // vm.newQuestion.newOpt = [];
    
    $scope.add = function() {
        var newItemNo = $scope.items.length+1;
        $scope.items.push({});
        $('.newInputs').attr('required', 'true');
        // $('.newInputs').attr('ng-model', 'newOpt');
    };

    $scope.removeChoice = function() {
        var lastItem = $scope.items.length-1;
        $scope.items.splice(lastItem);
    };

}]);



/*
 * Material Deesign Checkboxes non Polymer updated for use in bootstrap.
 * Tested and working in: IE9+, Chrome (Mobile + Desktop), Safari, Opera, Firefox.
 * @author Jason Mayes 2014, www.jasonmayes.com
 * @update Sergey Kupletsky 2014, www.design4net.ru
 */

var wskCheckbox = function () {
    var wskCheckboxes = [];
    var SPACE_KEY = 32;

    function addEventHandler(elem, eventType, handler) {
        if (elem.addEventListener) {
            elem.addEventListener(eventType, handler, false);
        } else if (elem.attachEvent) {
            elem.attachEvent('on' + eventType, handler);
        }
    }

    function clickHandler(e) {
        e.stopPropagation();
        if (this.className.indexOf('checked') < 0) {
            this.className += ' checked';
        } else {
            this.className = 'chk-span';
        }
    }

    function keyHandler(e) {
        e.stopPropagation();
        if (e.keyCode === SPACE_KEY) {
            clickHandler.call(this, e);
            // Also update the checkbox state.

            var cbox = document.getElementById(this.parentNode.getAttribute('for'));
            cbox.checked = !cbox.checked;
        }
    }

    function clickHandlerLabel(e) {
        var id = this.getAttribute('for');
        var i = wskCheckboxes.length;
        while (i--) {
            if (wskCheckboxes[i].id === id) {
                if (wskCheckboxes[i].checkbox.className.indexOf('checked') < 0) {
                    wskCheckboxes[i].checkbox.className += ' checked';
                } else {
                    wskCheckboxes[i].checkbox.className = 'chk-span';
                }
                break;
            }
        }
    }

    function findCheckBoxes() {
        var labels = document.getElementsByTagName('label');
        var i = labels.length;
        while (i--) {
            var posCheckbox = document.getElementById(labels[i].getAttribute('for'));
            if (posCheckbox !== null && posCheckbox.type === 'checkbox') {
                var text = labels[i].innerText;
                var span = document.createElement('span');
                span.className = 'chk-span';
                span.tabIndex = i;
                labels[i].insertBefore(span, labels[i].firstChild);
                addEventHandler(span, 'click', clickHandler);
                addEventHandler(span, 'keyup', keyHandler);
                addEventHandler(labels[i], 'click', clickHandlerLabel);
                wskCheckboxes.push({
                    'checkbox': span,
                    'id': labels[i].getAttribute('for')
                });
            }
        }
    }

    return {
        init: findCheckBoxes
    };
}();

wskCheckbox.init();