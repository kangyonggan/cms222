$(function () {
    var $modal = $('.modal');
    var $form = $modal.find("form");
    var $btn = $modal.find("button[data-type=submit]");

    $form.validate({
        rules: {
            value: {
                required: true,
                rangelength: [1, 256]
            }
        },
        submitHandler: function (form, event) {
            event.preventDefault();
            $btn.button('loading');
            formSubmit($(form), $btn, function () {
                $modal.modal('hide');
                var params = $("#form").serializeForm();
                $('#table').bootstrapTable("refresh", {query: params});
            });
        }
    });
});