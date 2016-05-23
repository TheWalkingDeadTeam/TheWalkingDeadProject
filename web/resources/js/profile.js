(function () {
    var requestData;
    var id = location.search.substr(1);
    $('#photo_img').attr('src', '/getPhoto/' + id );
    var isAgree = false;

    $(document).ready(function () {
        $.ajax({
            type: 'get',
            url: "/profile/" + id,
            dataType: 'json',
            contentType: "application/json",
            success: function (response) {
                if (response.fields.length) {
                    requestData = response;
                    enableSave();
                    $('#fields').on('change, input', enableSave);
                    response.fields.forEach(function (item, i) {
                        typeSwitcher(item, i, '#fields');
                        if (item.fieldName == 'Phone number' && item.values[0].value) {
                            isAgree = true;
                        }
                    });
                    $('#agree').on('click', enableSave);
                    checkAgreement();
                    enableSave();
                }

            },
            error: function (jqXHR, exception) {
                window.location.href = "/error"
            }
        });

        $('#fields').submit(function (event) {
            event.preventDefault();
            $.ajax({
                method: 'POST',
                contentType: "application/json",
                url: '/profile',
                data: JSON.stringify(requestData),
                success: function () {
                    $('#fieldsCheck').removeClass().empty();
                    $('#fieldsCheck').addClass('alert alert-success').html('Profile saved successfully').fadeIn();
                    setTimeout(function() {
                        $("#fieldsCheck").fadeOut().empty();
                    }, 3000);
                }
            })
        });

        $('#buttonEnroll').bind('click', function (event) {
            event.preventDefault();
            $.ajax({
                method: 'GET',
                contentType: "application/json",
                url: '/profile/enroll',
                success: function (response) {
                    if (response.length) {
                        $('#fieldsCheck').removeClass().empty();
                        var errorMsg = '';
                        for (var i in response) {
                            errorMsg += response[i].errorMessage + "</br>";
                        }
                        $('#fieldsCheck').addClass('alert alert-danger').html(errorMsg).fadeIn();
                    } else {
                        $('#fieldsCheck').removeClass().empty();
                        $('#fieldsCheck').addClass('alert alert-success').html('You have successfully enrolled on current courses!').fadeIn();
                        setTimeout(function() {
                            $("#fieldsCheck").fadeOut().empty();
                        }, 3000);
                    }
                }
            })
        });
    });


    function checkAgreement() {
        if (isAgree) {
            $('#agree').prop("checked", true);
        } else {
            $('#agree').prop("checked", false);
        }
    }

    function enableSave() {

        var empty = false;
        $('#fields > input, select, textarea, text').each(function () {
            if ($(this).val() == '') {
                empty = true;
            }
        });

        if (empty || !$('#agree').is(':checked') || !checkCheckboxes() || !checkRadio()) {
            $('#fieldsCheck').addClass('alert alert-danger').html('Please, fill each field and check Agree button').fadeIn();
            $('#save').attr('disabled', 'disabled');
        } else if (!empty && $('#agree').is(':checked') && checkCheckboxes() && checkRadio()) {
            $('#fieldsCheck').removeClass().empty();
            $('#save').prop('disabled', false);
        }

    }

    function checkRadio() {

        var radioGroup = $("[id^=radioBlock]").find('input');

        for (var i = 0; i < radioGroup.length; i++) {
            if (radioGroup[i].checked) {
                return true;
            }
        }
        return false;
    }

    function checkCheckboxes() {

        var group = $("[id^=checkBlock]").find('input');

        for (var i = 0; i < group.length; i++) {
            if (group[i].checked) {
                return true;
            }
        }
        return false;
    }

    function typeSwitcher(item, i, divname) {

        if (!item.multipleChoice) {
            switch (item.fieldType) {
                case 'number':
                    $('<div id=\"block' + i + '\">').appendTo($(divname));
                    $('<label>').attr({for: item.id}).text(item.fieldName + ' ').appendTo($('#block' + i));
                    var attributes = {type: 'number', id: item.id, min: '0', max: '10', value: item.values[0].value};
                    attributes.required = "required";
                    $('<input>')
                        .attr(attributes)
                        .attr('ng-model', i)
                        .appendTo($('#block' + i))
                        .bind('input', function () {
                            requestData['fields'][$(this).attr('ng-model')].values[0].value = $(this).val();
                        });
                    break;

                case 'radio':
                    $('<div id=\"radioBlock' + i + '\">').appendTo($(divname));
                    $('<span>').text(item.fieldName + ': ').appendTo($('#radioBlock' + i));
                    item.values.forEach(function (item_value, j) {
                        var rand = _getRandomInt();
                        var isChecked = (item_value.value == "true") ? 'checked' : '',
                            attributes = {type: 'radio', id: item_value.id + rand, name: item.id};
                        attributes.required = "required";
                        if (isChecked) {
                            attributes.checked = isChecked;
                        }
                        $('<label>').attr({for: item_value.id + rand}).text(' ' + item_value.fieldValueName).appendTo($('#radioBlock' + i));
                        $('<input>')
                            .attr(attributes)
                            .attr('ng-model', i)
                            .appendTo($('#radioBlock' + i))
                            .on('change', function () {
                                var updatedRadios = requestData.fields[$(this).attr('ng-model')].values.map(function (item) {
                                    if (item.id == this.id) {
                                        item.value = true;
                                        return item;
                                    } else {
                                        item.value = false;
                                        return item;
                                    }
                                }, this);
                                enableSave();
                                requestData.fields[$(this).attr('ng-model')].values = updatedRadios;

                            });
                    })
                    break;

                case 'text':
                    var attributes = {type: 'text', id: item.id, value: item.values[0].value};
                    attributes.required = "required";
                    $('<div id=\"block' + i + '\">').appendTo($(divname));
                    $('<label>').attr({for: item.id}).text(item.fieldName + ' ').appendTo($('#block' + i));
                    $('<input>')
                        .attr(attributes)
                        .attr('ng-model', i)
                        .appendTo($('#block' + i))
                        .bind('input', function () {
                            requestData['fields'][$(this).attr('ng-model')].values[0].value = $(this).val();
                        });
                    break;
                case 'tel':
                    var pattern = "^\\+380\\d{9}$";
                    var attributes = {
                        type: 'tel',
                        id: item.id,
                        max: 13,
                        min: 10,
                        pattern: pattern,
                        placeholder: "+380662281488",
                        value: item.values[0].value
                    };
                    attributes.required = "required";
                    $('<div id=\"block' + i + '\">').appendTo($(divname));
                    $('<label>').attr({for: item.id}).text(item.fieldName + ' ').appendTo($('#block' + i));
                    $('<input>')
                        .attr(attributes)
                        .attr('ng-model', i)
                        .appendTo($('#block' + i))
                        .bind('input', function () {
                            requestData['fields'][$(this).attr('ng-model')].values[0].value = $(this).val();
                        });
                    break;

                case 'select':

                    var attributes = {type: 'select', id: 'select' + item.id};
                    attributes.required = "required";
                    $('<div id=\"block' + i + '\">').appendTo($(divname));
                    $('<span>').attr({for: item.id}).text(item.fieldName + ' ').appendTo($('#block' + i));
                    $('<select>').attr(attributes).attr('ng-model', i).appendTo($('#block' + i));
                    $('#select' + item.id).append('<option ' + 'disabled' + '>' + 'University' + '</option>');
                    item.values.forEach(function (item_value, j) {
                        var rand = _getRandomInt();
                        var isSelected = item_value.value == "true";
                        $('#select' + item.id)
                            .append('<option id="' + item_value.id + rand + '" value="' + item_value.fieldValueName + '">' + item_value.fieldValueName + '</option>');
                        if (isSelected) {
                            $("#select" + item.id + " option[value='" + item_value.fieldValueName + "']").prop('selected', true);
                        }
                    });
                    $('#select' + item.id).on('change', function () {
                        var updatedSelect = requestData.fields[$(this).attr('ng-model')].values.map(function (item) {
                            allah = $(this).attr('ng-model');
                            if (item.fieldValueName == this.value) {
                                item.value = 'true';
                                return item;
                            } else {
                                item.value = 'false';
                                return item;
                            }
                        }, this);

                        requestData.fields[$(this).attr('ng-model')].values = updatedSelect;
                    });
                    break;

                case 'textarea':
                    var attributes = {id: 'textarea' + item.id, cols: 40, rows: 4};
                    attributes.required = "required";
                    $('<div id=\"block' + i + '\">').appendTo($(divname));
                    $('<span>').attr({for: item.id}).text(item.fieldName + ' ').appendTo($('#block' + i));
                    $('<textarea>')
                        .attr(attributes)
                        .attr('ng-model', i)
                        .appendTo($('#block' + i))
                        .bind('input', function () {
                            requestData['fields'][$(this).attr('ng-model')].values[0].value = $(this).val();
                        });
                    $('#textarea' + item.id).val(item.values[0].value);
                    break;
            }
        } else if (item.multipleChoice) {

            switch (item.fieldType) {
                case 'checkbox':
                    $('<div id=\"checkBlock' + i + '\">').appendTo($(divname));
                    $('<span>').text(item.fieldName + ': ').appendTo($('#checkBlock' + i));
                    item.values.forEach(function (item_value, j) {
                        var rand = _getRandomInt();
                        var isChecked = (item_value.value == "true") ? 'checked' : '',
                            attributes = {type: 'checkbox', id: item_value.id + rand, value: item_value.value};
                        if (isChecked) {
                            attributes.checked = isChecked;
                        }
                        $('<label>').attr({for: item_value.id + rand}).text(' ' + item_value.fieldValueName).appendTo($('#checkBlock' + i));
                        $('<input>')
                            .attr(attributes)
                            .attr('ng-model', i)
                            .appendTo($('#checkBlock' + i))
                            .on('change', function () {
                                enableSave();
                                requestData.fields[$(this).attr('ng-model')].values[j].value = this.checked;
                            });
                    });
                    break;
            }
        }
    }

    function _getRandomInt() {
        return Math.round(1 + Math.random() * (998)) + Math.round(1 + Math.random() * (998));
    }
})();

