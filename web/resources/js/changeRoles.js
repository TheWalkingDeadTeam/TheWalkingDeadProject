/**
 * Created by Max Morozov on 15.05.2016.
 */

$(document).ready(function () {
    $("#save_roles").click(function (event) {
        var checkroles = [];
        $("input:checked").each(function () {
            checkroles.push({name: $(this).val()});
        });
        checkroles.push({name: $("#role").val()});
        event.preventDefault();
        $.ajax({
            type: 'post',
            url: '/changeroles',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({
                email: $("#userEmail").val(), //"kirkorov@gmail.com",
                roles: checkroles
            }),
            success: function (response) {
                if (response.length) {
                } else {
                    $('#messageChangeRoles')
                        .addClass('alert alert-success')
                        .html('Roles changed successfully');
                }
            },
            error: function (jqXHR, exception) {
                window.location.href = "/error"
            }
        });
    });
});