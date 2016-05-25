/**
 * Created by Hlib on 09.05.2016.
 */
const div_form = '\
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">\
    <div id="save_message"></div>\
    <div>\
    <div class="row container-fluid reg-head">\
    <div>\
    <h4 class="form-signin-heading">Feedback :</h4>\
</div>\
</div>\
<form id="feedback_form">\
    <div id="regform" class="col-lg-8 col-md-8 col-sm-12 col-xs-12">\
    <textarea rows="10" cols="10" id="feedback_text" style="margin-bottom: 3px;" class="form-control"\
placeholder="Feedback" required></textarea>\
</div>\
<div  class="col-lg-4 col-md-4 col-sm-12 col-xs-12">\
<div class="form-group">\
    <label for="feedback_score">Mark: </label>\
<input id="feedback_score" style="margin: 0px;" class="form-control"\
placeholder="1 .. 100" type="number"\
max="100" min="1" required>\
</div>\
<div class="form-group">\
<label for="special_mark">Special mark: </label>\
<select id="special_mark" style="margin-bottom: 3px;" class="form-control">\
    <option value="none" id="none">None</option>\
    <option value="reject" id="reject">Reject</option>\
    <option value="take on courses" id="take_on_courses">Take on courses</option>\
<option value="job offer" id="job_offer">Job offer</option>\
</select>\
</div>\
</div>\
<div class="col-lg-8 col-md-8 col-sm-12 col-xs-12">\
    <button id="submitFeedback" style="border-radius: 4px;    margin-top: 4px ;"\
class="btn btn-lg btn-primary btn-block mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">\
    Submit\
    </button>\
    </div>\
    </form>\
    </div>\
    </div>\
 ';

const div_view = '    <div id="all_feedbacks">\
    <div class="widget">\
    <div class="widget-header clearfix">\
    <h3><i class="icon ion-ios-browsers"></i> <span>\
    <p id="special_mark_display">Special mark: </p>\
\
\
</span></h3>\
<ul class="nav nav-tabs pull-right">\
    <li class="active"><a href="#tab1" data-toggle="tab"><i class="icon ion-gear-b"></i> Developer <span\
id="dev_score" class="label label-info label-as-badge pull-left">55</span></a></li>\
<li class=""><a href="#tab2" data-toggle="tab"><i class="icon ion-help-circled"></i> HR/BA <span\
id="hr_score" class="label label-info label-as-badge pull-left">75</span></a></li>\
</ul>\
</div>\
\
<div class="widget-content tab-content">\
    <div class="tab-pane fade active in" id="tab1">\
    <p id="dev_feedback">Dev feedback</p>\
</div>\
<div class="tab-pane fade" id="tab2">\
    <p id="hr_feedback">Hr feedback</p>\
</div>\
</div>\
</div>\
</div>';
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
                            } else if (response.viewLevel == 'interviewing'){
                                if(response.applicationExists == true && response.intervieweeExists == true) {
                                    if (response.restricted == false) {
                                        $('#feedbacks').html(div_form);
                                        var feedback;
                                        if (response.interviewerRole == 'ROLE_DEV') {
                                            feedback = response.devFeedback;
                                        } else {
                                            feedback = response.hrFeedback;
                                        }
                                        $('#feedback_score').val(feedback.score);
                                        $('#feedback_text').val(feedback.comment);
                                        $('#special_mark').val(response.specialMark);
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
                            } else if (response.viewLevel == 'after'){
                                if(response.applicationExists == true && response.intervieweeExists == true) {
                                    $('#feedbacks').html(div_view);
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
