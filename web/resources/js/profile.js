    (function () {

        var requestData;

        $(document).ready(function () {

            $.ajax({
                type: 'get',
                url: 'resources/json/ces.json' /*"/profile"*/,
                dataType: 'json',
                contentType: "application/json",
                success: function (response) {
                    if (response.fields.length) {
                        requestData = response;
                        $('#fields').on('change, input', enableSave);
                        response.fields.forEach(function (item, i) {
                            typeSwitcher(item, i, '#fields');
                        });
                        $('#fields').append('<button id="save" type=\"submit\" form=\"fields\" value=\"Submit\" disabled="disabled">' + 'Save' + '</button>');
                    }

                },
                error: function (jqXHR, exception) {
                    window.location.href = "/error"
                }
            });

            $('#fields').submit(function () {
                event.preventDefault();
                $.ajax({
                    method: 'POST',
                    contentType: "application/json",
                    url: 'profile',
                    data: JSON.stringify(requestData)
                })
            });
        });
    })
    
