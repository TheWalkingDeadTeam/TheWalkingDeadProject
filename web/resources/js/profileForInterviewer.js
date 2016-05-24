/**
 * Created by Hlib on 09.05.2016.
 */

(function () {
    var requestData;
    var id = location.search.substr(1);
    $(document).ready(function () {
        $('#feedback').hide();
        $('#photo_img').attr('src','/getPhoto/'+id);
        $.ajax({
            type: 'get',
            url: "/profile/" + id,
            dataType: 'json',
            contentType: "application/json",
            success: function (response) {
                if (response.fields.length) {
                    requestData = response;
                    response.fields.forEach(function (item, i) {
                        typeSwitcher(item, i, '#profile');
                    });
                    $.ajax({
                        type: 'get',
                        url: 'getFeedback/' + id,
                        dataType: 'json',
                        contentType: "application/json",
                        success: function (response, textStatus, jqXHR) {
                            if (jqXHR.getResponseHeader('interviewee') == 'interviewee') {
                                if (jqXHR.getResponseHeader('restricted') == 'false') {
                                    $('#feedback').show();
                                    $('#feedback_score').val(response.feedback.score);
                                    $('#feedback_text').val(response.feedback.comment);
                                    $('#special_mark').val(response.specialMark);
                                } else {
                                    $('#restrict_message')
                                        .addClass('alert alert-danger')
                                        .html('Feedback for this student has already been written by another interviewer');
                                }
                            } else {
                                $('#restrict_message')
                                    .addClass('alert alert-danger')
                                    .html('This studen\'t wasn\'t interviewed');
                            }
                        },
                        error: function (jqXHR, exception) {
                            console.log(exception.toString());
                            window.location.href = "/error"
                        }
                    });
                } else {
                    $('#restrict_message')
                        .addClass('alert alert-danger')
                        .html('This user doesn\'t have the application');
                }
            },
            error: function (jqXHR, exception) {
                console.log(exception.toString());
                window.location.href = "/error"
            }
        });
    });

    $('#feedback_form').submit(function (event) {
            event.preventDefault();
            $.ajax({
                type: 'post',
                url: '/interviewer/feedback/' + id + '/save',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify({
                    feedback: {
                        score: $('#feedback_score').val(),
                        comment: $('#feedback_text').val()
                    },
                    specialMark: $('#special_mark').val()
                }),
                success: function (response){
                    if (response.length){
                        var errors_out = "";
                        for (var i in response) {
                            errors_out += response[i].errorMessage + "</br>"
                        }
                        $('#save_message')
                            .addClass('alert alert-danger')
                            .html(errors_out)
                            .fadeIn();
                    }
                    else {
                        $('#save_message')
                            .removeClass()
                            .addClass('alert alert-success')
                            .html('Successfully saved');
                        setTimeout(function() {
                            $("#save_message").fadeOut().empty();
                        }, 3000);
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
            $('<label>').attr({for: item.id}).text(item.fieldName + ':  ').appendTo($('#block' + i));
                switch (item.fieldType) {
                case 'number':
                case 'text':
                case 'textarea':
                case 'tel':
                    $('<span>').text(item.values[0].value+'').appendTo($('#block' + i));
                    break;
                case 'select':
                case 'checkbox':
                case 'radio':
                        var text = new Array;
                        item.values.forEach(function(item_value){
                            if (item_value.value == 'true'){
                                text.push(item_value.fieldValueName);
                            }
                        })
                        $('<span>').text(text.toString()).appendTo($('#block' + i));
                        break;
            }
    }
})();
