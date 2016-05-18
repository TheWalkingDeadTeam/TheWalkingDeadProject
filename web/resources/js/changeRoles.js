/**
 * Created by Max Morozov on 15.05.2016.
 */

$(document).ready(function(){
    var id = location.search.substr(1);
    $.ajax({
        type: 'get',
        url: isNaN(id) ? '/getUser' : '/getUser/'+id,
        dataType: 'json',
        success: function(response){
            $('#userName').text(response.name);
            $('#userSurname').text(response.surname);
            $('#userEmail').text(response.email);
        },
        error: function (jqXHR, exception) {
            window.location.href = "/error"
        }
    })
})

$(document).ready(function () {

    $("#save_roles").click(function () {
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