$(document).ready(function () {
    
    $("#buttonRegistration").click(function () {
        var checkroles = [];
        $("input:checked").each(function () {
            checkroles.push({name: $(this).val()});
        });
        checkroles.push({name: $("#role").val()});
        event.preventDefault();
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
                    // response.errors.forEach(item, i);
                    for (var i in response) {
                        errors_out += response[i].errorMessage + "</br>"
                    }
                    $('#messageRegistration')
                        .removeClass()
                        .empty();
                    $('#messageRegistration')
                        .addClass('alert alert-danger')
                        .html(errors_out);
                    $('#j_password').val("");
                } else {
                    $('#messageRegistration')
                        .removeClass()
                        .empty()
                        .addClass('alert alert-success')
                        .html('Registered successfully');
                    $('#regform')
                        .val("");
                    $("#check").prop("checked", false);
                    $("input").filter(".roles").prop("checked", false);
                }
            },
            error: function (jqXHR, exception) {
                window.location.href = "/error"
            }
        });
    });
});