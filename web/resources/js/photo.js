/**
 * Created by Hlib on 01.05.2016.
 */
$(document).ready(function () {
    $("#photo_button").click(function () {
        event.preventDefault();
        var formData = new FormData();
        var photoData = $('input[type=file]')[0].files[0];
        formData.append('photo', photoData);
        $.ajax({
            url: '/uploadPhoto',
            type: 'post',
            data: formData,
            processData: false,
            contentType: false,
            dataType: 'json',
            success: function (response) {

                if (response.length) {
                    var errors_out = "";
                    for (var i in response) {
                        errors_out += response[i].errorMessage + "</br>"
                    }
                    $('#photoMessages')
                        .addClass('alert alert-danger')
                        .html(errors_out).fadeIn();
                }
                else {
                    $('#photoMessages').removeClass().empty();
                    $('#photoMessages').addClass('alert alert-success').html('Photo changed');
                    $("#photo_img").attr('src', '/getPhoto');
                    setTimeout(function () {
                        $("#photoMessages").fadeOut().empty();
                    }, 3000);
                }
            },
            /*           error: function(){
             $('#photoMessages')
             .addClass('alert alert-danger')
             .html('No file specified');
             }*/
            error: function (jqXHR, exception) {
                console.log(exception)
            }
        });
    });
});
