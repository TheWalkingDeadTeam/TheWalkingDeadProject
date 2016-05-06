/**
 * Created by Hlib on 06.05.2016.
 */

$(document).ready(function(){
    $.ajax({
        type: 'get',
        url: '/getUser',
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