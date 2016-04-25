/**
 * Created by Pavel on 25.04.2016.
 */
$(document).ready(function () {
    $("#loginButton").click(function () {
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
                window.location.href = response.redirect;

            },
            error: function (jqXHR, exception) {
                alert('Error')
            }
        });
    });
});