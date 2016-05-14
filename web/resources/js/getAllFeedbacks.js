/**
 * Created by Hlib on 14.05.2016.
 */

    $(document).ready(function(){
        var id = location.search.substr(1);
        $.ajax({
            type: 'get',
            url: 'getallfeedbacks/' + id,
            dataType: 'json',
            contentType: 'application/json',
            success: function(response){
                var devFeedback = response[0].feedback;
                var specialMark = response[0].specialMark;
                var hrFeedback = response[1].feedback;
                $('#dev_feedback').append('<div>'+devFeedback.comment+'</div>');
                $('#dev_score').append('<div>'+devFeedback.score+'</div>');
                $('#hr_feedback').append('<div>'+hrFeedback.comment+'</div>');
                $('#hr_score').append('<div>'+hrFeedback.score+'</div>');
                $('#special_mark_display').append('<div>'+specialMark+'</div>');
            },
            error: function (jqXHR, exception) {
                console.log(exception.toString());
            }
        })
    });
