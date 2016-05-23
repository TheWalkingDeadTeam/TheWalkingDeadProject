/**
 * Created by Hlib on 11.05.2016.
 */



$(document).ready(function(){
    var id = location.search.substr(1);
    $('#photo_img').attr('src', '/getPhoto/' + id );

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



$(document).ready(function(){
    $('.collapsible').collapsible();
});

$(document).ready(function(){
    $('.materialboxed').materialbox();
});
$(document).ready(function(){
    $('.materialboxed').materialbox();
});

$(document).ready(function(){
    $('.materialboxed').materialbox();
});
$(document).ready(function(){
    $('.materialboxed').materialbox();
});