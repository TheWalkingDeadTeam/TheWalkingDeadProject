/**
 * Created by Pavel on 24.05.2016.
 */

$(function() {
    var pgurl = location.pathname;
    if (pgurl == '/admin' || pgurl == '/' || pgurl == '/login') {
        $("#menu").addClass('active');
    } else {
        $('nav a[href^="' + pgurl + '"]').addClass('active');
    }

});
