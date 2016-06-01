$(document).ready(function () {

    $("#buttonSaveRoles").click(function (event) {
        event.preventDefault();
        var checkroles = [];
        $("input:checked").each(function () {
            checkroles.push({name: $(this).val()});
        });
        checkroles.push({name: $("#role").val()});
        $.ajax({
            type: 'post',
            url: '/account/changeroles',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({
                email: $("#userEmail").text(),
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