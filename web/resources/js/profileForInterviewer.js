/**
 * Created by Hlib on 09.05.2016.
 */

(function () {
    var requestData;
    var id = location.search.substr(1);
    $(document).ready(function () {
        $.ajax({
            type: 'get',
            url: "/profile/" + id,
            // url: "/resources/json/myJSON.json",
            dataType: 'json',
            contentType: "application/json",
            success: function (response) {
                if (response.fields.length) {
                    requestData = response;
                    response.fields.forEach(function (item, i) {
                        typeSwitcher(item, i, '#profile');
                    });
                }
            },
            error: function (jqXHR, exception) {
                console.log(exception.toString());
                window.location.href = "/error"
            }
        });

    });

    $('#feedback').submit(function (event) {
            event.preventDefault();
            $.ajax({
                type: 'post',
                url: '/interviewer/feedback/' + id + '/save',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify({
                    score: $('#feedback_score').val(),
                    comment: $('#feedback_text').val()
                }),
                success: function (response){
                    if (response.length){
                        var errors_out = "";
                        for (var i in response) {
                            errors_out += response[i].errorMessage + "</br>"
                        }
                        $('#save_message')
                            .addClass('alert alert-danger')
                            .html(errors_out);
                    }
                },
                error: function (jqXHR, exception) {
                    console.log(exception.toString());
                    window.location.href = "/error"
                }
            });
        }
    )

    function typeSwitcher(item, i, divname){
            $('<div id=\"block' + i + '\">').appendTo($(divname));
            $('<label>').attr({for: item.id}).text(item.fieldName + '\t ').appendTo($('#block' + i));
                switch (item.fieldType) {
                case 'number':
                case 'text':
                case 'textarea':
                case 'tel':
                    $('<label>').text(item.values[0].value+'').appendTo($('#block' + i));
                    break;
                case 'select':
                case 'checkbox':
                case 'radio':
                        var text = ',';
                        item.values.forEach(function(item_value){
                            if (item_value.value == 'true'){
                                text = text + ',' + item_value.fieldValueName + ',';
                            }
                        })
                        $('<label>').text(text).appendTo($('#block' + i));
                        break;
            }
    }
})();
