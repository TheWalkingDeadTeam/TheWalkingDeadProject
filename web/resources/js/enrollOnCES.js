$(document).ready(function () {
    $("#enroll_button").click(function () {
        event.preventDefault();
        var enrolledInterviewer = [];
        enrolledInterviewer.push(this.id);
        $.ajax({
            url: '/enroll',
            type: 'post',
            data: JSON.stringify(enrolledInterviewer),
            processData: false,
            contentType: false,
            dataType: 'json',
            success: function (response) {
                if (response.length) {
                    var errors_out = "";
                    for (var i in response) {
                        errors_out += response[i].errorMessage + "</br>"
                    }
                    $('#enrollMessages')
                        .addClass('alert alert-danger')
                        .html(errors_out);
                }
                else {
                    $('#enrollMessages').removeClass();
                }
            },
            error: function (jqXHR, exception) {
                console.log(exception)
            }
        });
    });
});

