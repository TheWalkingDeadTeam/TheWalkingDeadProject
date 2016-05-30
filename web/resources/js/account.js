/**
 * Created by Hlib on 11.05.2016.
 */



$(document).ready(function () {
    var id = location.search.substr(1);
    $('#photo_img').attr('src', '/getPhoto/' + id);

    $.ajax({
        type: 'get',
        url: isNaN(id) ? '/getUser' : '/getUser/' + id,
        dataType: 'json',
        success: function (response) {
            $('#userName').text(response.name);
            $('#userSurname').text(response.surname);
            $('#userEmail').text(response.email);

            response.roles.forEach(function (item, i, arr) {
                $('#userRoles').text($('#userRoles').text() + ' ' + item.name);
            });
        },
        error: function (jqXHR, exception) {
            window.location.href = "/error"
        }
    })


    $("#enroll_button").click(function (event) {
        event.preventDefault();
        var arrid = [];
        arrid.push($("#userid").val());
        $.ajax({
            type: 'post',
            url: '/interviewer/enroll-ces-interviewer',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({
                values: arrid
            }),
            success: function (response) {
                if (response.length) {
                    var errors_out = "";
                    for (var i in response) {
                        errors_out += response[i].errorMessage + "</br>"
                    }
                    $('#enrollMessages')
                        .removeClass()
                        .empty()
                        .addClass('alert alert-danger')
                        .html(errors_out).fadeIn();
                } else {
                    $('#enrollMessages')
                        .removeClass()
                        .empty();
                    $('#enrollMessages')
                        .addClass('alert alert-success')
                        .html('Enrolled on interview')
                        .fadeIn();
                    setTimeout(function () {
                        $("#enrollMessages")
                            .empty()
                            .fadeOut();
                    }, 3000);
                }
            },
            error: function (jqXHR, exception) {
                window.location.href = "/error"
            }
        });
    });


})


$(document).ready(function () {
    $('.collapsible').collapsible();
});

$(document).ready(function () {
    $('.materialboxed').materialbox();
});
$(document).ready(function () {
    $('.materialboxed').materialbox();
});

$(document).ready(function () {
    $('.materialboxed').materialbox();
});
$(document).ready(function () {
    $('.materialboxed').materialbox();
});