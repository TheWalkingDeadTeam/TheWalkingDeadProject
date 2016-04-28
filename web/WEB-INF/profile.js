$(document).ready(function () {
    $('form').submit(formSubmitHandler);

    getDataForProfile();
});

function getDataForProfile() {
    $.ajax ({
        method: 'GET',
        url: 'resources/js/json/getProfile.json'
        // url: 'resources/js/json/getEmptyProfile.json'
    })
    .done( function(data) {
        handleProfileResponse(data);
    });
}

function formSubmitHandler(e) {
    e.preventDefault();

    var requestData = {
        programingLanguages: [],
        multiplyQuestions: {},
        singleQuestions: {},
        name: $('input[name=name]').val(),
        surname: $('input[name=surname]').val(),
        phoneNumber: $('input[name=tel]').val(),
        email: $('input[name=email]').val(),
        university: $('#university').val(),
        course: $('#course').val()
    };

    var programingLangList = $('#programingLangList');
    var multiplyQuestions = $('#multiplyQuestions');
    var singleQuestions = $('#singleQuestions');

    programingLangList.find('input').each(function (i,item) {
        requestData.programingLanguages.push({
            id: $(item).attr('name'),
            assesment: $(item).val()
        });
    });

    singleQuestions.find('input:checked').each(function (i,item) {
        requestData.singleQuestions[$(item).attr('name')] = $(item).val();
    });

    multiplyQuestions.find('input:checked').each(function (i,item) {
        if( requestData.multiplyQuestions[$(item).attr('name')]) {
            requestData.multiplyQuestions[$(item).attr('name')].push($(item).val())
        } else {
            requestData.multiplyQuestions[$(item).attr('name')] = [$(item).val()];
        }
    });

    $.ajax ({
            method: 'POST',
            contentType: "application/json",
            url: 'profile',
            data: JSON.stringify(requestData)
        })
        .done( function(data) {
            console.log(data);
        });
}

function handleProfileResponse(data) {
    if(data.name) {
        renderProfile(data);
    } else {
        renderEmptyProfile(data);
    }
}
function renderEmptyProfile(data) {
    var programingLangList = $('#programingLangList');
    var universitySelect = $('#universitySelect');
    var course = $('#course');


    appendOptionToSelect(universitySelect, data.university);
    appendOptionToSelect(course, data.course);
    appendItemsToList(programingLangList, data.programingLanguages);
}

function renderProfile(data) {
    var name = $('input[name=name]');
    var surname= $('input[name=surname]');
    var tel= $('input[name=tel]');
    var email= $('input[name=email]');
    var programingLangList = $('#programingLangList');
    var multiplyQuestions = $('#multiplyQuestions');
    var singleQuestions = $('#singleQuestions');
    var universitySelect = $('#university');
    var course = $('#course');


    name.val(data.name);
    surname.val(data.surname);
    tel.val(data.phoneNumber);
    email.val(data.email);

    appendOptionToSelect(universitySelect, data.university);
    appendOptionToSelect(course, data.course);
    appendItemsToList(programingLangList, data.programingLanguages);
    appendMultipluItemsToList(multiplyQuestions, data.multiplyQuestions);
    appendSingleItemsToList(singleQuestions, data.singleQuestions);
}

function appendOptionToSelect(element,options) {
    options.values.forEach( function (item) {
        var isSelected =  options.selected === item  ? 'selected' : '';

        $(element).append($('<option '+ isSelected +'>' + item + '</option>'));
    })
}

function appendItemsToList(element,options) {
    options.forEach( function (item) {
        $(element).append($('<li><label>' + item.name + '<input name="' + item.id + '" type="number" min="0" max="10" value="' + item.assesment + '"></label></li>'))
    })
}

function appendMultipluItemsToList(element,options) {
    options.forEach( function (item) {
        var questionsItem =  $('<li><span>' + item.title + '</span></li>');

        item.allValues.forEach( function (qItem) {
            var isCheked =  ~item.checkedValues.indexOf(qItem) ? 'checked' : '';
            var questionItem =  $('<span>' + qItem +': <input type="checkbox" name="' + item.questionId +'" value="' + qItem + '"' + isCheked +'></span> ');
            questionsItem.append(questionItem);
        });
        $(element).append(questionsItem);
    })
}

function appendSingleItemsToList(element,options) {
    options.forEach( function (item) {
        var questionsItem =  $('<li><span>' + item.title + '</span></li>');

        item.allValues.forEach( function (qItem) {
            var isCheked =  item.selectedValue === qItem ? 'checked' : '';
            var questionItem =  $('<span>' + qItem +': <input type="radio" name="' + item.questionId +'" value="' + qItem + '"' + isCheked +'></span> ');
            questionsItem.append(questionItem);
        });
        $(element).append(questionsItem);
    })
}