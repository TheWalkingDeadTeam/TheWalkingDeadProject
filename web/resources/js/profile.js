$(document).ready(function () {
    $('form').submit(formSubmitHandler);

    getDataForProfile();
});

function getDataForProfile() {
    $.ajax({
            method: 'GET',
            url: 'resources/js/json/getProfile.json'
            // url: 'resources/js/json/getEmptyProfile.json'
        })
        .done(function (data) {
            handleProfileResponse(data);
        });
}

function formSubmitHandler(e) {
    e.preventDefault();

    var requestData = {
        programingLanguages: [],
        knowledge: [],
        multiplyQuestions: {},
        singleQuestions: {},
        singleQuestions2: {},
        name: $('input[name=name]').val(),
        surname: $('input[name=surname]').val(),
        phoneNumber: $('input[name=tel]').val(),
        mail: $('input[name=email]').val(),
        university: $('#university').val(),
        faculty: $('input[name=faculty]').val(),
        department: $('input[name=department]').val(),
        specialty: $('input[name=specialty]').val(),
        course: $('#course').val(),
        averageMark: $('input[name=averageMark]').val(),
        portfolio: $('input[name=portfolio]').val(),
        additionalLang: $('input[name=additionalLang]').val(),
        whyYou: $('input[name=whyYou]').val(),
        foreignPrograms: $('input[name=foreignPrograms]').val(),
        hobbies: $('input[name=hobbies]').val(),
        personalAgree: $('input[name=personalAgree]').val()
    };

    var programingLangList = $('#programingLangList');
    var knowledgeList = $('#knowledgeList');
    var multiplyQuestions = $('#multiplyQuestions');
    var singleQuestions = $('#singleQuestions');
    var singleQuestions2 = $('#singleQuestions2');


    programingLangList.find('input').each(function (i, item) {
        requestData.programingLanguages.push({
            id: $(item).attr('name'),
            assessment: $(item).val()
        });
    });
    knowledgeList.find('input').each(function (i, item) {
        requestData.knowledge.push({
            id: $(item).attr('name'),
            assessment: $(item).val()
        });
    });

    singleQuestions.find('input:checked').each(function (i, item) {
        requestData.singleQuestions[$(item).attr('name')] = $(item).val();
    });
    singleQuestions2.find('input:checked').each(function (i, item) {
        requestData.singleQuestions2[$(item).attr('name')] = $(item).val();
    });

    multiplyQuestions.find('input:checked').each(function (i, item) {
        if (requestData.multiplyQuestions[$(item).attr('name')]) {
            requestData.multiplyQuestions[$(item).attr('name')].push($(item).val())
        } else {
            requestData.multiplyQuestions[$(item).attr('name')] = [$(item).val()];
        }
    });

    $.ajax({
            method: 'POST',
            contentType: "application/json",
            url: 'profile',
            data: JSON.stringify(requestData)
        })
        .done(function (data) {
            console.log(data);
        });
}

function handleProfileResponse(data) {
    if (data.name) {
        renderProfile(data);
    } else {
        renderEmptyProfile(data);
    }
}
function renderEmptyProfile(data) {
    var programingLangList = $('#programingLangList');
    var universitySelect = $('#universitySelect');
    var course = $('#course');
    var knowledgeList = $('#knowledgeList');

    appendOptionToSelect(universitySelect, data.university);
    appendOptionToSelect(course, data.course);
    appendItemsToList(programingLangList, data.programingLanguages);
    appendItemsToList(knowledgeList, data.knowledge);
}

function renderProfile(data) {
    var name = $('input[name=name]');
    var surname = $('input[name=surname]');
    var tel = $('input[name=tel]');
    var email = $('input[name=email]');
    var programingLangList = $('#programingLangList');
    var knowledgeList = $('#knowledgeList');
    var multiplyQuestions = $('#multiplyQuestions');
    var singleQuestions = $('#singleQuestions');
    var singleQuestions2 = $('#singleQuestions2');
    var universitySelect = $('#university');
    var faculty = $('input[name=faculty]');
    var department = $('input[name=department]');
    var specialty = $('input[name=specialty]');
    var course = $('#course');
    var averageMark = $('input[name=averageMark]');
    var portfolio = $('input[name=portfolio]');
    var additionalLang = $('input[name=additionalLang]');
    var whyYou = $('input[name=whyYou]');
    var foreignPrograms = $('input[name=foreignPrograms]');
    var hobbies = $('input[name=hobbies]');
    var personalAgree = $('input[name=personalAgree]');

    name.val(data.name);
    surname.val(data.surname);
    tel.val(data.phoneNumber);
    email.val(data.mail);
    faculty.val(data.faculty);
    department.val(data.department);
    specialty.val(data.specialty);
    averageMark.val(data.averageMark);
    portfolio.val(data.portfolio);
    additionalLang.val(data.additionalLang);
    whyYou.val(data.whyYou);
    foreignPrograms.val(data.foreignPrograms);
    hobbies.val(data.hobbies);
    personalAgree.val(data.personalAgree);

    appendOptionToSelect(universitySelect, data.university);
    appendOptionToSelect(course, data.course);
    appendItemsToList(programingLangList, data.programingLanguages);
    appendItemsToList(knowledgeList, data.knowledge);
    appendMultiplyItemsToList(multiplyQuestions, data.multiplyQuestions);
    appendSingleItemsToList(singleQuestions, data.singleQuestions);
    appendSingleItemsToList(singleQuestions2, data.singleQuestions2);
}

function appendOptionToSelect(element, options) {
    options.values.forEach(function (item) {
        var isSelected = options.selected === item ? 'selected' : '';

        $(element).append($('<option ' + isSelected + '>' + item + '</option>'));
    })
}

function appendItemsToList(element, options) {
    options.forEach(function (item) {
        $(element).append($('<li><label>' + item.name + ' <input name="' + item.id + '" type="number" min="0" max="10" value="' + item.assessment + '"></label></li>'))
    })
}

function appendMultiplyItemsToList(element, options) {
    options.forEach(function (item) {
        var questionsItem = $('<li><span>' + item.title + '</span></li>');

        item.allValues.forEach(function (qItem) {
            var isCheked = ~item.checkedValues.indexOf(qItem) ? 'checked' : '';
            var questionItem = $('<span> ' + qItem + ': <input type="checkbox" name="' + item.questionId + '" value="' + qItem + '"' + isCheked + '></span> ');
            questionsItem.append(questionItem);
        });
        $(element).append(questionsItem);
    })
}

function appendSingleItemsToList(element, options) {
    options.forEach(function (item) {
        var questionsItem = $('<li><span>' + item.title + '</span></li>');

        item.allValues.forEach(function (qItem) {
            var isChecked = item.selectedValue === qItem ? 'checked' : '';
            var questionItem = $('<span> ' + qItem + ': <input type="radio" name="' + item.questionId + '" value="' + qItem + '"' + isChecked + '></span> ');
            questionsItem.append(questionItem);
        });
        $(element).append(questionsItem);
    })
}