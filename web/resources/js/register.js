/**
 * Created by Pavel on 25.04.2016.
 */
$(document).ready(function () {
    $("#buttonRegistration").click(function () {
        event.preventDefault();
        $.ajax({
            type: 'post',
            url: '/register',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({
                name: $('#name').val(),
                email: $('#email').val(),
                password: $('#password').val()
            }),
            success: function (response) {
                if (response.errors.length) {
                    var errors_out = "";
                    for (var i in response.errors) {
                        errors_out += response.errors[i].errorMessage + "</br>"
                    }
                    $('#messageRegistration')
                        .addClass('alert alert-danger')
                        .html(errors_out);
                    $('#messageSignIn')
                        .removeClass()
                        .empty();
                } else {
                    $('#messageRegistration')
                        .removeClass('alert alert-danger')
                        .addClass('alert alert-success')
                        .html("Registered successfully");
                    $('#messageSignIn')
                        .removeClass()
                        .empty();
                    $('#name').val("");
                    $('#email').val("");
                    $('#password').val("");
                }

            },
            error: function (jqXHR, exception) {
                window.location.href = "/error"
            }
        });
    });
});