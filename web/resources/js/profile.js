(function () {

    var requestData;
    var id = $(location).attr('search');

    $(document).ready(function () {
        $.ajax({
            type: 'get',
            /*  url: "/profile/" + id,*/
            url: "/resources/json/myJSON.json",
            dataType: 'json',
            contentType: "application/json",
            success: function (response) {
                if (response.fields.length) {
                    requestData = response;
                    enableSave();
                    $('#fields').on('change, input', enableSave);
                    response.fields.forEach(function (item, i) {
                        typeSwitcher(item, i, '#fields');
                    });
                    $('<div id="agreement">').appendTo('#fields');
                    $('#agreement').append('<label for="agree">' + "I agree to have my personal information been proceeded " + '</label>');
                    $('<input>').attr({id: "agree", type: "checkbox"}).appendTo('#agreement');
                    $('#agree').on('click', enableSave);
                }

            },
            error: function (jqXHR, exception) {
                window.location.href = "/error"
            }
        });

        $('#fields').submit(function () {
            event.preventDefault();
            $.ajax({
                method: 'POST',
                contentType: "application/json",
                url: 'profile',
                data: JSON.stringify(requestData)
            })
        });

        $('#buttonEnroll').bind('click', function () {
            event.preventDefault();
            $.ajax({
                method: 'GET',
                contentType: "application/json",
                url: '/profile/enroll'
            })
        });
    });


    function enableSave() {

        var empty = false;
        $('#fields > input, select, textarea, text').each(function () {
            if ($(this).val() == '') {
                empty = true;
            }
        });

        if (empty || !$('#agree').is(':checked') || !checkCheckboxes()) {
            $('#fieldsCheck').addClass('alert alert-danger').html('Please, fill each field and check Agree button');
            $('#save').attr('disabled', 'disabled');
        } else if (!empty && $('#agree').is(':checked') && checkCheckboxes()) {
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

        if (!item.multiple) {
            switch (item.type) {
                case 'number':
                    $('<div id=\"block' + i + '\">').appendTo($(divname));
                    $('<label>').attr({for: item.id}).text(item.name + ' ').appendTo($('#block' + i));
                    var attributes = {type: 'number', id: item.id, min: '0', max: '10', value: item.value[0].value};
                    attributes.required = "required";
                    $('<input>')
                        .attr(attributes)
                        .attr('ng-model', i)
                        .appendTo($('#block' + i))
                        .bind('input', function () {
                            requestData['fields'][$(this).attr('ng-model')].value[0].value = $(this).val();
                        });
                    break;

                case 'radio':
                    $('<div id=\"radioBlock' + i + '\">').appendTo($(divname));
                    $('<span>').text(item.name + ': ').appendTo($('#radioBlock' + i));
                    item.value.forEach(function (item_value, j) {
                        var isChecked = item_value.value ? 'checked' : '',
                            attributes = {type: 'radio', id: item_value.id, name: item.id};
                        attributes.required = "required";
                        if (isChecked) {
                            attributes.checked = isChecked;
                        }
                        $('<label>').attr({for: item_value.id}).text(' ' + item_value.name).appendTo($('#radioBlock' + i));
                        $('<input>')
                            .attr(attributes)
                            .attr('ng-model', i)
                            .appendTo($('#radioBlock' + i))
                            .on('change', function () {
                                var updatedRadios = requestData.fields[$(this).attr('ng-model')].value.map(function (item) {
                                    if (item.id == this.id) {
                                        item.value = true;
                                        return item;
                                    } else {
                                        item.value = false;
                                        return item;
                                    }
                                }, this);
                                enableSave()
                                requestData.fields[$(this).attr('ng-model')].value = updatedRadios;

                            });
                    })
                    break;

                case 'text':
                    var attributes = {type: 'text', id: item.id, value: item.value[0].value};
                    attributes.required = "required";
                    $('<div id=\"block' + i + '\">').appendTo($(divname));
                    $('<label>').attr({for: item.id}).text(item.name + ' ').appendTo($('#block' + i));
                    $('<input>')
                        .attr(attributes)
                        .attr('ng-model', i)
                        .appendTo($('#block' + i))
                        .bind('input', function () {
                            requestData['fields'][$(this).attr('ng-model')].value[0].value = $(this).val();
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
                        value: item.value[0].value
                    };
                    attributes.required = "required";
                    $('<div id=\"block' + i + '\">').appendTo($(divname));
                    $('<label>').attr({for: item.id}).text(item.name + ' ').appendTo($('#block' + i));
                    $('<input>')
                        .attr(attributes)
                        .attr('ng-model', i)
                        .appendTo($('#block' + i))
                        .bind('input', function () {
                            requestData['fields'][$(this).attr('ng-model')].value[0].value = $(this).val();
                        });
                    break;

                case 'select':
                    var attributes = {type: 'select', id: item.id};
                    attributes.required = "required";
                    $('<div id=\"block' + i + '\">').appendTo($(divname));
                    $('<span>').attr({for: item.id}).text(item.name + ' ').appendTo($('#block' + i));
                    $('<select>').attr(attributes).attr('ng-model', i).appendTo($('#block' + i));
                    $('#' + item.id).append('<option ' + 'disabled' + '>' + 'University' + '</option>');
                    item.value.forEach(function (item_value, j) {
                        var isSelected = item_value.value ? 'selected' : '';
                        $('#' + item.id)
                            .append('<option id="' + item_value.id + '" value="' + item_value.name + '" ' + isSelected + '>' + item_value.name + '</option>')
                            .on('change', function () {
                                var updatedSelect = requestData.fields[$(this).attr('ng-model')].value.map(function (item) {
                                    if (item.name == this.value) {
                                        item.value = true;
                                        return item;
                                    } else {
                                        item.value = false;
                                        return item;
                                    }
                                }, this);

                                requestData.fields[$(this).attr('ng-model')].value = updatedSelect;
                            });
                    })
                    break;

                case 'textarea':
                    var attributes = {id: item.id, cols: 40, rows: 4};
                    attributes.required = "required";
                    $('<div id=\"block' + i + '\">').appendTo($(divname));
                    $('<span>').attr({for: item.id}).text(item.name + ' ').appendTo($('#block' + i));
                    $('<textarea>')
                        .attr(attributes)
                        .attr('ng-model', i)
                        .appendTo($('#block' + i))
                        .bind('input', function () {
                            requestData['fields'][$(this).attr('ng-model')].value[0].value = $(this).val();
                        });
                    $('#' + item.id).val(item.value[0].value);
                    break;
            }
        } else if (item.multiple) {

            switch (item.type) {
                case 'checkbox':
                    $('<div id=\"checkBlock' + i + '\">').appendTo($(divname));
                    $('<span>').text(item.name + ': ').appendTo($('#checkBlock' + i));
                    item.value.forEach(function (item_value, j) {
                        var isChecked = item_value.value ? 'checked' : '',
                            attributes = {type: 'checkbox', id: item_value.id, value: item_value.value};
                        if (isChecked) {
                            attributes.checked = isChecked;
                        }
                        $('<label>').attr({for: item_value.id}).text(' ' + item_value.name).appendTo($('#checkBlock' + i));
                        $('<input>')
                            .attr(attributes)
                            .attr('ng-model', i)
                            .appendTo($('#checkBlock' + i))
                            .on('change', function () {
                                enableSave()
                                requestData.fields[$(this).attr('ng-model')].value[j].value = this.checked;
                            });
                    });
                    break;
            }
        }
    }
})();

