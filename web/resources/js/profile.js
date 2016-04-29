/**
 * Created by Pavel on 28.04.2016.
 */

/**
 * Created by Pavel on 25.04.2016.
 */
$(document).ready(function () {
    // $("#buttonRegistration").click(function () {
    //     event.preventDefault();
        $.ajax({
            type: 'get',
            url: 'resources/json/myJSON.json' /*"/profile"*/,
            dataType: 'json',
            contentType: "application/json",
            success: function (response) {
                if (response.fields.length) {
                    response.fields.forEach(function (item, i) {
                        console.log('iterating over object № ' + i);
                        typeSwitcher(item, i, '#fields');
                    })
                }

            },
            error: function (jqXHR, exception) {
                window.location.href = "/error"
            }
        });
    // });
});

function typeSwitcher(item, i, divname) {
    if (!item.multiple) {
        console.log('Single value');
        switch (item.type) {
            case 'number':
                console.log('this is a number => ' + item.type);
                $('<div id=\"block'+i+'\">').appendTo($(divname));
                $('<label>').attr({for: 'name'}).text(item.name + ' ').appendTo($('#block' + i));
                $('<input>').attr({type: 'number', id: item.id, min: '0', max: '10', value: item.value[0].value}).appendTo($('#block' + i));
                break;
            case 'radio':
                console.log('this is a radiobutton => ' + item.type);
                console.log('values count: ' + item.value.length);
                $('<div id=\"block'+i+'\">').appendTo($(divname));
                $('<span>').text(item.name + ': ').appendTo($('#block' + i));
                item.value.forEach ( function (item_value, j) {
                    var isChecked = item_value.value ? 'checked' : '',
                        attribures = {type: 'radio', id: item_value.id, value: item_value.value/*what value?*/};
                    if (isChecked) {
                        attribures.checked = isChecked;
                    }
                    $('<label>').attr({for: item_value.id}).text(' ' + item_value.name).appendTo($('#block' + i));
                    $('<input>').attr(attribures).appendTo($('#block' + i));
                })
                break;
            case 'text':
                console.log('text input here');
        }
    } else if (item.multiple) {
        console.log('Multiple values');
        switch (item.type) {
            case 'checkbox':
                console.log('this is a checkbox => ' + item.type);
                console.log('values count: ' + item.value.length);
                $('<div id=\"block'+i+'\">').appendTo($(divname));
                $('<span>').text(item.name + ': ').appendTo($('#block' + i));
                item.value.forEach ( function (item_value, j) {
                    var isChecked = item_value.value ? 'checked' : '',
                        attribures = {type: 'checkbox', id: item_value.id, value: item_value.value/*what value?*/};
                    if (isChecked) {
                        attribures.checked = isChecked;
                    }
                    $('<label>').attr({for: item_value.id}).text(' ' + item_value.name).appendTo($('#block' + i));
                    $('<input>').attr(attribures).appendTo($('#block' + i));
                })
                break;
        }
    }
}