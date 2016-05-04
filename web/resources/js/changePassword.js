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
                password: $('#changePassword').val()
            }),
            success: function (response) {
                if (response.length) {
                    var errors_out = "";
                    for (var i in response) {
                        errors_out += response[i].errorMessage + "</br>"
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
                    $('#messageCheckPassword')
                        .addClass('alert alert-success')
                        .html('Password changed successfully');
                        console.log('Console');
                   // window.location.href = window.location.href;
                }
            },
            error: function (jqXHR, exception) {
                window.location.href = "/error"
            }
        });
    });
});

