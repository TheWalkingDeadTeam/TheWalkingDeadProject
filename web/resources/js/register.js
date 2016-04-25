/**
 * Created by Pavel on 25.04.2016.
 */
$(document).ready(function () {
    $("#registerButton").click(function () {
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
                window.location.href = response.redirect;

            }
        });
    });
});