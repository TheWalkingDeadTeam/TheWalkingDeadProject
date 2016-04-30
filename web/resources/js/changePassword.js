/**
 * Created by Alexander on 30.04.2016.
 */


$(document).ready(function () {
    
    $("#buttonChangePassword").click(function () {
        event.preventDefault();
        $.ajax({
            type: 'post',
            url: '/changePassword',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({
                password: $('#changePassword').val(),
            }),
            success: function (response) {
                if (response.errors.length) {
                    var errors_out = "";
                    for (var i in response.errors) {
                        errors_out += response.errors[i].errorMessage + "</br>"
                    }
                    $('#messageCheckPassword')
                        .removeClass()
                        .empty();
                    $('#messageCheckPassword')
                        .addClass('alert alert-danger')
                        .html(errors_out);
                    $('#password').val("");
                } else {
                    $('#messageCheckPassword')
                        .removeClass()
                        .empty();
                    $('#messageRegistration')
                        .addClass('alert alert-success')
                        .html('Password changed successfully');

                    window.location.href = window.location.href;
                }
            },
            error: function (jqXHR, exception) {
                window.location.href = "/error"
            }
        });
    });
});

