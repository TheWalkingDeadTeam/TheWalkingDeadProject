$(document).ready(function () {

    $("#buttonRegistration").click(function (event) {
        event.preventDefault();
        var checkroles = [];
        if ($("#checkboxAdmin").prop('checked')) {
            checkroles.push({name: $("#checkboxAdmin").val()});
        }
        if ($("#role").val() != '') {
            checkroles.push({name: $("#role").val()});
        }
        $.ajax({
            type: 'post',
            url: '/register',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({
                name: $('#name').val(),
                surname: $('#surname').val(),
                email: $('#email').val(),
                password: $('#password').val(),
                roles: checkroles
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
                    $('#messageRegistration')
                        .addClass('alert alert-danger')
                        .html(errors_out)
                        .fadeIn();
                    $('#j_password').val("");
                } else {
                    $('#messageRegistration')
                        .removeClass()
                        .empty()
                        .addClass('alert alert-success')
                        .html('Registered successfully')
                        .fadeIn();
                    $('#regform input')
                        .val("");
                    $("#check").prop("checked", false);
                    $("input").filter(".roles").prop("checked", false);
                    setTimeout(function() {
                        $("#messageRegistration").fadeOut().empty();
                    }, 3000);
                }
            },
            error: function (jqXHR, exception) {
                window.location.href = "/error"
            }
        });
    });
});