/**
 * Created by Σταυρίνα on 27/9/2017.
 */
jQuery(document).ready(function () {
    jQuery('#username').on('input', function() {
        searchAjax();
    });
});
function searchAjax() {
    var username = jQuery('#username').val();
    jQuery.ajax({
        type: "POST",
        contentType: "application/json",
        url: "usernameCheck",
        data: JSON.stringify({"username": username}),
        dataType: 'json',
        timeout: 100000,
        success: function (data) {
            var mySpan = jQuery('#usernameError');
            if (data["code"] === "400") {
                //   mySpan.removeClass("hidden");
                mySpan.show();
                mySpan.html(data["msg"]);
            }
            else {
                mySpan.addClass("hidden");
                mySpan.html("");
            }
        },
        error: function (e) {
            console.log("ERROR: ", e);
            display(e);
        }

    });
}
