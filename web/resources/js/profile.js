(function () {

    var requestData;

    $(document).ready(function () {

        $.ajax({
            type: 'get',
            url: 'resources/json/myJSON.json' /*"/profile"*/,
            dataType: 'json',
            contentType: "application/json",
            success: function (response) {
                if (response.fields.length) {
                    requestData = response;
                    response.fields.forEach(function (item, i) {
                        typeSwitcher(item, i, '#fields');
                    });
                    $('#fields').append('<button type=\"submit\" form=\"fields\" value=\"Submit\">' + 'Save' + '</button>');
                }

            },
            error: function (jqXHR, exception) {
                window.location.href = "/error"
            }
        });

        $('#fields').submit(function () {
            event.preventDefault();
            $.ajax ({
                    method: 'POST',
                    contentType: "application/json",
                    url: 'profile',
                    data: JSON.stringify(requestData)
                })
                .done( function(data) {
                    console.log(data);
                })
        });
    });

    function typeSwitcher(item, i, divname) {
        if (!item.multiple) {
            switch (item.type) {
                case 'number':
                    $('<div id=\"block'+i+'\">').appendTo($(divname));
                    $('<label>').attr({for: item.id}).text(item.name + ' ').appendTo($('#block' + i));
                    var attributes = {type: 'number', id: item.id, min: '0', max: '10', value: item.value[0].value};
                    $('<input>')
                        .attr(attributes)
                        .attr('ng-model', i )
                        .appendTo($('#block' + i))
                        .bind('input', function(){
                            requestData['fields'][$(this).attr('ng-model')].value[0].value = $(this).val();
                        });
                    break;
                case 'radio':
                    $('<div id=\"block'+i+'\">').appendTo($(divname));
                    $('<span>').text(item.name + ': ').appendTo($('#block' + i));
                    item.value.forEach ( function (item_value, j) {
                        var isChecked = item_value.value ? 'checked' : '',
                            attribures = {type: 'radio', id: item_value.id, name: item.id};
                        if (isChecked) {
                            attribures.checked = isChecked;
                        }
                        $('<label>').attr({for: item_value.id}).text(' ' + item_value.name).appendTo($('#block' + i));
                        $('<input>')
                            .attr(attribures)
                            .attr('ng-model', i)
                            .appendTo($('#block' + i))
                            .on('change', function () {
                                var updatedRadios = requestData.fields[$(this).attr('ng-model')].value.map(function (item) {
                                    if(item.id == this.id) {
                                        item.value = true;
                                        return item;
                                    } else {
                                        item.value = false;
                                        return item;
                                    }
                                }, this);

                                requestData.fields[$(this).attr('ng-model')].value = updatedRadios;

                            });
                    })
                    break;
                case 'text':
                    $('<div id=\"block'+i+'\">').appendTo($(divname));
                    $('<label>').attr({for: item.id}).text(item.name + ' ').appendTo($('#block' + i));
                    $('<input>')
                        .attr({type: 'text', id: item.id, value: item.value[0].value})
                        .attr('ng-model', i)
                        .appendTo($('#block' + i))
                        .bind('input', function(){
                            requestData['fields'][$(this).attr('ng-model')].value[0].value = $(this).val();
                        });
                    break;
                case 'tel':
                    $('<div id=\"block'+i+'\">').appendTo($(divname));
                    $('<label>').attr({for: item.id}).text(item.name + ' ').appendTo($('#block' + i));
                    $('<input>')
                        .attr({type: 'tel', id: item.id, value: item.value[0].value})
                        .attr('ng-model', i)
                        .appendTo($('#block' + i))
                        .bind('input', function(){
                            requestData['fields'][$(this).attr('ng-model')].value[0].value = $(this).val();
                        });
                    break;
                case 'select':
                    $('<div id=\"block'+i+'\">').appendTo($(divname));
                    $('<span>').attr({for: item.id}).text(item.name + ' ').appendTo($('#block' + i));
                    $('<select>').attr({type: 'select', id: item.id}).attr('ng-model', i).appendTo($('#block' + i));
                    $('#' + item.id).append('<option ' + 'disabled' + '>' + 'University' + '</option>');
                    item.value.forEach( function (item_value, j) {
                        var isSelected = item_value.value ? 'selected' : '';
                        $('#' + item.id)
                            .append('<option id="' + item_value.id + '" value="' + item_value.name + '" ' + isSelected +'>' + item_value.name + '</option>')
                            .on('change', function () {
                                var updatedSelect = requestData.fields[$(this).attr('ng-model')].value.map(function (item) {
                                    if(item.name == this.value) {
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
                    $('<div id=\"block'+i+'\">').appendTo($(divname));
                    $('<span>').attr({for: item.id}).text(item.name + ' ').appendTo($('#block' + i));
                    $('<textarea>')
                        .attr({id: item.id, cols: 40, rows: 4})
                        .attr('ng-model', i)
                        .appendTo($('#block' + i))
                        .bind('input', function(){
                            requestData['fields'][$(this).attr('ng-model')].value[0].value = $(this).val();
                        });
                    $('#' + item.id).val(item.value[0].value);
                    break;
            }
        } else if (item.multiple) {
            switch (item.type) {
                case 'checkbox':
                    $('<div id=\"block'+i+'\">').appendTo($(divname));
                    $('<span>').text(item.name + ': ').appendTo($('#block' + i));
                    item.value.forEach ( function (item_value, j) {
                        var isChecked = item_value.value ? 'checked' : '',
                            attribures = {type: 'checkbox', id: item_value.id, value: item_value.value/*what value?*/};
                        if (isChecked) {
                            attribures.checked = isChecked;
                        }
                        $('<label>').attr({for: item_value.id}).text(' ' + item_value.name).appendTo($('#block' + i));
                        $('<input>')
                            .attr(attribures)
                            .attr('ng-model', i)
                            .appendTo($('#block' + i))
                            .on('change', function(){
                                requestData.fields[$(this).attr('ng-model')].value[j].value = this.checked;
                            });
                    });
                    break;
            }
        }
    }
})();

