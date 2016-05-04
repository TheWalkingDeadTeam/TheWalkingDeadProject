/**
 * Created by Neltarion on 27.04.2016.
 */
$(document).ready(function () {

    $.ajax({
        method: 'GET',
        url: 'resources/js/json/myJSON.json'
    }).done(function (data) {
        console.log('loaded page');
        handleProfileResponse(data);
    })


    $('form').submit(function () {

        var responseData = {}

        $.ajax({
            url: "/profile",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(data)
        }).done(function (data) {
            console.log(data);
        });
    })

})

function handleProfileResponse(data) {
    if (data[0].name) {
        console.log('handling json');
        renderProfile(data);
    } else {
        // renderEmptyProfile(data);
    }
}

// function renderProfile(data) {
//     console.log('rendering profile');
//     for (var i in data) {
//         console.log('some shit');
//         for (var j in data[i]) {
//             if (typeof data[i][j] === 'object') {
//                 console.log('handling  values of ' + data[i][j] + ' field');
//             } else {
//                 switch (data[i][j].type) {
//                     case 'number':
//                         console.log('this is a number input');
//                         $('<div>').appendTo($('body'));
//                         $('<label>').attr({for: 'name'}).text(data[i][j].name).appendTo($('body'));
//                         $('<input>').attr({type: 'number', id: i, min: '0', max: '10'}).appendTo($('body'));
//                         $('</div>').appendTo($('body'));
//                 }
//             }
//         }
//     }
// }

function renderProfile(data) {
    console.log('rendering profile');
    data.forEach(function (item, i) {
        console.log('iterating over object № ' + i);
        typeSwitcher(item, i);
    })
}

function typeSwitcher(item, i) {
    if (item.multiple == false) {
        console.log('Single value');
        switch (item.type) {
            case 'number':
                console.log('this is a number => ' + item.type);
                $('<div id=\"block'+i+'\">').appendTo($('body'));
                $('<label>').attr({for: 'name'}).text(item.name + ' ').appendTo($('#block' + i));
                $('<input>').attr({type: 'number', id: item.id, min: '0', max: '10', value: item.value[0].value}).appendTo($('#block' + i));
                break;
            case 'radio':
                console.log('this is a radiobutton => ' + item.type);
                console.log('values count: ' + item.value.length);
                $('<div id=\"block'+i+'\">').appendTo($('body'));
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
    } else if (item.multiple == true) {
        console.log('Multiple values');
        switch (item.type) {
            case 'checkbox':
                console.log('this is a checkbox => ' + item.type);
                console.log('values count: ' + item.value.length);
                $('<div id=\"block'+i+'\">').appendTo($('body'));
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