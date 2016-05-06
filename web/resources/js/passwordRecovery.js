$(document).ready(function () {
    // popup-------------------------------------------------------------------------------popup
    $('.recoverybtn').bind('click', function () {
        $('.recovery').fadeIn(500); //openpopup
        openValidate();
    });

    $('.closebtn').bind('click', function () {
        $('.recovery').fadeOut(300); //closebutton
    });

    $('.layout').bind('click', function () {
        $('.recovery').fadeOut(300); //layoutclose
    });
    //popup
    // -------------------------------------------------------------------------------validation
    $('.form-control').bind('input', ValidateRecoveryForm);
    // binding


    function ValidateRecoveryForm() {
        var elem = $(this);
        var innerText = elem.val();
        var errorMsg = '';

        if (elem.is('#userEmail')) {
            if (!/^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$/.test(innerText)) {
                errorMsg = errorMsg + 'Incorrect email';
            }
            $('.correct-email').text(errorMsg);
        }
        ;
        buttonEnable();
    };
    // oninputvalidation

    function openValidate() {
        var errorMsg = '';

        var elem = $('#userEmail');
        var innerText = elem.val();

        if (!/^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$/.test(innerText)) {
            errorMsg = errorMsg + 'Incorrect email';
        }
        $('.correct-email').text(errorMsg);
        buttonEnable();
    };
    // start validation
    function buttonEnable() {
        if ($('.correct-email').text() == '') {
            $('#stupidUser button').prop('disabled', false);
        } else {
            $('#stupidUser button').prop('disabled', true);
        }
    };
    // ------------------------------------------validation

    $("#buttonRecoverPassword").click(function () {
        event.preventDefault();
        $.ajax({
            type: 'post',
            url: '/passwordRecovery',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({
                email: $('#email').val()
            }),
            success: function (response) {
                if (response.errors.length) {
                    response.errors.forEach(item);
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

});