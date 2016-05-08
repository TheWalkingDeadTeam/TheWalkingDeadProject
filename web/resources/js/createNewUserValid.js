/**
 * Created by Neltarion on 08.05.2016.
 */

$('.form-control').bind('input', ValidateForm);

function ValidateForm() {
    var elem = $(this);
    var innerText = elem.val();
    var errorMsg = '';

    if (elem.is('#name')) {

        if (!/^([A-ZА-ЯЁ][a-zа-яё'-]+\s?)+$/.test(innerText)) {
            errorMsg = errorMsg + 'Incorrect name';
        } else if (/([^a-zA-Zа-яА-ЯёЁ'\s-])/.test(innerText)) {
            errorMsg = errorMsg + 'Incorrect name';
        } else if (!/^.{2,30}$/.test(innerText)) {
            errorMsg = errorMsg + 'Incorrect name';
        }
        ;

        $('.correct-name').text(errorMsg);
    } else if (elem.is('#email')) {

        if (!/^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$/.test(innerText)) {
            errorMsg = errorMsg + 'Incorrect email';
        }

        $('.correct-email').text(errorMsg);
    } else if (elem.is('#password')) {

        if (!/^.{6,20}$/.test(innerText)) {
            errorMsg = errorMsg + 'Incorrect symbols number';
        }

        $('.correct-password').text(errorMsg);
    } else if (elem.is('#surname')) {

        if (!/^([A-ZА-ЯЁ][a-zа-яё'-]+\s?)+$/.test(innerText)) {
            errorMsg = errorMsg + 'Need a capital letter;';
        }
        ;

        if (/([^a-zA-Zа-яА-ЯёЁ'\s-])/.test(innerText)) {
            errorMsg = errorMsg + 'Only letter\'s;';
        }
        ;

        if (!/^.{2,30}$/.test(innerText)) {
            errorMsg = errorMsg + 'Incorrect letter\'s number;';
        }
        ;
        $('.correct-surname').text(errorMsg);
    }
    ;
};