/**
 * Created by Alexander on 30.04.2016.
 */
$(document).ready(function () {
    $('#changePassword').hideShowPassword;


    function ValidateChangeForm() {
        var elem = $('#changePassword');
        var innerText = elem.val();
        var errorMsg = '';
        if (!/^.{6,32}$/.test(innerText)) {
            errorMsg = errorMsg + 'Password should have from 6 to 32 symbols';
        }

        $('.correct-password').text(errorMsg);
        errorMsg = '';
    }

    $('.form-control').bind('input', ValidateChangeForm);

    $("#buttonChangePassword").click(function (event) {
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
                    $('#changePassword').val("");
                } else {
                    $('#messageCheckPassword')
                        .removeClass()
                        .empty();
                    $('#messageCheckPassword')
                        .addClass('alert alert-success')
                        .html('Password changed successfully');
                    $('#changePassword').val("");
                    setTimeout(function() {
                        $("#messageCheckPassword").fadeOut().empty();
                    }, 3000);
                }
            },
            error: function (jqXHR, exception) {
                window.location.href = "/error"
            }
        });
    });
});

