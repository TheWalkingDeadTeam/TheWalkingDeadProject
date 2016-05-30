/**
 * Created by Hlib on 09.05.2016.
 */
(function () {
    var requestData;
    var id = location.search.substr(1);
    $(document).ready(function () {
        $('#photo_img').attr('src', '/getPhoto/' + id);
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
                        success: function (response) {
                            if (response.viewLevel == 'restricted') {
                                $('#feedbacks')
                                    .addClass('alert alert-danger')
                                    .html('It\'s too early to leave feedbacks')
                                    .fadeIn();
                            } else if (response.viewLevel == 'interviewing') {
                                if (response.applicationExists == true && response.intervieweeExists == true) {
                                    if (response.restricted == false) {
                                        //$('#feedbacks').html(div_form);
                                        $('#feedbacks').load('/interviewer/leave-feedback',function(){
                                            var feedback;
                                            if (response.interviewerRole == 'ROLE_DEV') {
                                                feedback = response.devFeedback;
                                            } else {
                                                feedback = response.hrFeedback;
                                            }
                                            if (feedback != null) {
                                                $('#feedback_score').val(feedback.score);
                                                $('#feedback_text').val(feedback.comment);
                                                $('#special_mark').val(response.specialMark).change();
                                            }
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
                                                        success: function (response) {
                                                            if (response.length) {
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
                                                                    .html('Successfully saved')
                                                                    .fadeIn();
                                                                setTimeout(function () {
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
                                        });

                                    } else {
                                        $('#feedbacks')
                                            .addClass('alert alert-danger')
                                            .html('Feedback for this student has already been written by another interviewer')
                                            .fadeIn();
                                    }
                                } else {
                                    $('#feedbacks')
                                        .addClass('alert alert-danger')
                                        .html('This studen\'t wasn\'t interviewed')
                                        .fadeIn();
                                }
                            } else if (response.viewLevel == 'after') {
                                if (response.applicationExists == true && response.intervieweeExists == true) {
                                    //$('#feedbacks').html(div_view);
                                    $('#feedbacks').load('/interviewer/view-feedback',function( response, status, xhr ){
                                        $('#special_mark_display').append(response.specialMark);
                                        $('#dev_score').append(response.devFeedback.score);
                                        $('#hr_score').append(response.hrFeedback.score);
                                        $('#dev_feedback').append(response.devFeedback.comment);
                                        $('#hr_feedback').append(response.hrFeedback.comment);
                                    });
                                } else {
                                    $('#feedbacks')
                                        .addClass('alert alert-danger')
                                        .html('This studen\'t wasn\'t interviewed')
                                        .fadeIn();
                                }
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
                        .html('This user doesn\'t have the application')
                        .fadeIn();
                }
            },
            error: function (jqXHR, exception) {
                console.log(exception.toString());
                window.location.href = "/error"
            }
        });
    });


    function typeSwitcher(item, i, divname) {
        $('<div id=\"block' + i + '\">').appendTo($(divname));
        $('<label>').attr({for: item.id}).text(item.fieldName + ':  ').appendTo($('#block' + i));
        switch (item.fieldType) {
            case 'number':
            case 'text':
            case 'textarea':
            case 'tel':
                $('<span>').text(item.values[0].value + '').appendTo($('#block' + i));
                break;
            case 'select':
            case 'checkbox':
            case 'radio':
                var text = new Array;
                item.values.forEach(function (item_value) {
                    if (item_value.value == 'true') {
                        text.push(item_value.fieldValueName);
                    }
                })
                $('<span>').text(text.toString()).appendTo($('#block' + i));
                break;
        }
    }
})();
