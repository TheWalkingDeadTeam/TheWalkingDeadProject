/**
 * Created by Pavel on 25.04.2016.
 */
$(document).ready(function () {
    $("#buttonSignIn").click(function () {
        event.preventDefault();
        $.ajax({
            type: 'post',
            url: '/security_check',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({
                email: $('#j_username').val(),
                password: $('#j_password').val(),
            }),
            success: function (response) {
                if (response.length) {
                    var errors_out = "";
                    for (var i in response) {
                        errors_out += response[i].errorMessage + "</br>"
                    }
                    $('#messageRegistration')
                        .removeClass()
                        .empty();
                    $('#messageSignIn')
                        .addClass('alert alert-danger')
                        .html(errors_out);
                    $('#j_password').val("");
                } else {
                    window.location.href = window.location.href;
                }
            },
            error: function (jqXHR, exception) {
                window.location.href = "/error"
            }
        });
    });
});