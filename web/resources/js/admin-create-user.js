

    $("#buttonRegistration").click(function () {
        var checkroles = [];
        $("input:checked").each(function() {
            checkroles.push( {name: $(this).val()});    //toDO look after
        });
        event.preventDefault();
        $.ajax({
            type: 'post',
            url: '/admin/register',
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
 /*                   response.forEach(item)*/
                }
            },
            error: function (jqXHR, exception) {
                window.location.href = "/error"
            }
        });
    });


    function fillError(item) {
        //toDo
    }