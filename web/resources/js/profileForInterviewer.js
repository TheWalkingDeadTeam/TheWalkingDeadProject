/**
 * Created by Hlib on 09.05.2016.
 */

(function () {
    var requestData;
    var id = location.search.substr(1);
    $(document).ready(function () {
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
                        success: function (response) {
                            if (response.viewLevel == 'restricted'){
                                $('#feedbacks')
                                    .addClass('alert alert-danger')
                                    .html('It\'s too early to leave feedbacks');
                            } else if (response.viewLevel == 'interviewing period'){
                                if(response.applicationExists == true && response.intervieweeExists == true) {
                                    if (response.restricted == false) {
                                        $('#feedbacks').html(
                                            '\
                                            <div id="restrict_message"></div>\
                                            <div id="feedback">\
                                                <form id="feedback_form">\
                                                <div>\
                                                    <input type="number" id="feedback_score" max="100" min="1" align="centre" required/>\
                                                </div>\
                                                <div>\
                                                    <textarea id="feedback_text" placeholder="Put your feedback here" cols="40" rows="10"\
                                                    required></textarea>\
                                                </div>\
                                                <div>\
                                                    <select id = "special_mark">\
                                                        <option disabled>Special mark</option>\
                                                        <option value="none" id = "none">None</option>\
                                                        <option value="reject" id = "reject">Reject</option>\
                                                        <option value="take on courses" id="take_on_courses">Take on courses</option>\
                                                        <option value="job offer" id="job_offer">Job offer</option>\
                                                    </select>\
                                                </div>\
                                                <div id="save_message"></div>\
                                                <div>\
                                                    <button id="submitFeedback" type="submit" title="Submit">Submit</button>\
                                                </div>\
                                                </form>\
                                            </div>\
                                        ');
                                        var feedback;
                                        if (response.interviewerRole == 'ROLE_DEV') {
                                            feedback = response.devFeedback;
                                        } else {
                                            feedback = response.hrFeedback;
                                        }
                                        $('#feedback_score').val(feedback.score);
                                        $('#feedback_text').val(feedback.comment);
                                        $('#special_mark').val(response.specialMark);
                                    } else {
                                        $('#feedbacks')
                                            .addClass('alert alert-danger')
                                            .html('Feedback for this student has already been written by another interviewer');
                                    }
                                } else {
                                    $('#feedbacks')
                                        .addClass('alert alert-danger')
                                        .html('This studen\'t wasn\'t interviewed');
                                }
                            } else if (response.viewLevel = 'after interviewing period'){
                                if(response.applicationExists == true && response.intervieweeExists == true) {
                                    $('#feedbacks').html('\
                                    <div id="getall_message"></div>\
                                    <div id = "all_feedbacks">\
                                        <p id = "dev_feedback">Dev\'s feedback<br></p>\
                                        <p id = "dev_score">Dev\'s score: <br></p>\
                                        <p id = "hr_feedback">HR\'s feedback<br></p>\
                                        <p id = "hr_score">HR\'s score: <br></p>\
                                        <p id = "special_mark_display">Special mark: </p>\
                                    </div>');
                                } else {
                                    $('#feedbacks')
                                        .addClass('alert alert-danger')
                                        .html('This studen\'t wasn\'t interviewed');
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
                            .html(errors_out);
                    }
                    else {
                        $('#save_message')
                            .removeClass()
                            .addClass('alert alert-success')
                            .html('Successfully saved');
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
                    $('<label>').text(item.values[0].value+'').appendTo($('#block' + i));
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
                        $('<label>').text(text.toString()).appendTo($('#block' + i));
                        break;
            }
    }
})();
