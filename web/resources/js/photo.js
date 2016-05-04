/**
 * Created by Hlib on 01.05.2016.
 */
$(document).ready(function () {
    $("#photo_button").click(function () {
        event.preventDefault();
        var formData = new FormData();
        var photoData = $('input[type=file]')[0].files[0];
        formData.append('photo',photoData);
        $.ajax({
            url: '/uploadPhoto',
            type: 'post',
            data: formData,
            processData: false,
            contentType: false,
            dataType: 'json',
            success: function(response){

                if(response.errors.length){
                    var errors_out = "";
                    for (var i in response.errors) {
                        errors_out += response.errors[i].errorMessage + "</br>"
                    }
                    $('#photoMessages')
                        .addClass('alert alert-danger')
                        .html(errors_out);
                }
                else{
                    $('#photoMessages').removeClass();
                    $("#photo_img").attr('src','/getPhoto');
                }
            },
            error: function(){
                $('#photoMessages')
                    .addClass('alert alert-danger')
                    .html('No file specified');
            }
        });
    });
});
