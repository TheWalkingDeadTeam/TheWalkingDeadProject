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
                    for (var i in response) {
                         new PNotify({
                            text: response[i].errorMessage,
                            type: 'error',
                            styling: 'fontawesome',
                            buttons: {
                                closer: false,
                                sticker: false
                            }
                        }).get().click(function() {
                             this.remove();
                         });
                    }


                    $('#j_password').val("");
                } else {
                    new PNotify({
                        text: 'User ' + $('#email').val() + ' registered successfully ',
                        type: 'success',
                        styling: 'fontawesome'
                    }).get().click(function() {
                        this.remove();
                    });;
                    $('#regform input')
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