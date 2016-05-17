/**
 * Created by Neltarion on 12.05.2016.
 */

var sendFormModule = angular.module('sendFormModule', []);

sendFormModule.controller('sendFr', ["$scope", "$http", function ($scope, $http) {
    var vm = this;

    vm.isShown = false;
    vm.isRequired = false;

    vm.newQuestion = {
        name: '',
        fieldTypeID: '',
        multipleChoice: '',
        orderNum: '',
        listTypeID: '',
        listTypeName: '',
        inputOptionsFields: []
    };

    $scope.back = function () {
        window.location.href = "/admin/edit-form";
    }

    $scope.change = function () {
        var flag = (vm.newQuestion.fieldTypeID == 4 || vm.newQuestion.fieldTypeID == 5 || vm.newQuestion.fieldTypeID == 6);
        vm.newQuestion.inputOptionsFields = [];
        
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
            vm.newQuestion.listTypeID = '';
            vm.newQuestion.listTypeName='';
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
    };

    vm.add = function(event) {
        var item = {
            value: '',
            opt_id: _getRandomInt()
        };

        vm.newQuestion.inputOptionsFields.push(item);
        
        $('.newInputs').attr('required', 'true');
    };
    
    function _getRandomInt() {
        return Math.round(1 + Math.random() * (9998)) + Math.round(1 + Math.random() * (9998));
    }

}]);


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