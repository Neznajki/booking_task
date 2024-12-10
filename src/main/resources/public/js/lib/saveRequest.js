var SaveRequest = function () {
    var self = this;
    var errorContainer = $("#errorContainer");
    var successContainer = $("#successContainer");

    self.submit = function(e) {
        console.info("submitted form");
        e.preventDefault(); // avoid to execute the actual submit of the form.

        var form = $(this);
        $.ajax({
            type: "POST",
            url: form.attr('action'),
            data: form.serialize(), // serializes the form's elements.
            success: function(data)
            {
                successContainer.text(data.responseJSON.successMessage);
            },
            error: function (data) {
                if (data.responseJSON && data.responseJSON.errorMessage) {
                    console.info(errorContainer, data.responseJSON.errorMessage);
                    errorContainer.text(data.responseJSON.errorMessage);
                    return
                }

                console.log('An error occurred.');
                console.log(data);
            },
        });
    };
}