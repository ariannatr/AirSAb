/**
 * Created by Σταυρίνα on 27/9/2017.
 */
$(document).ready(function () {
    $('#tokoumpi').click(function() {
        checked = $("input[type=checkbox]:checked").length;

        if(!checked) {
            //alert("You must check at least one checkbox.");
            $('#checkboxError').show();
            return false;
        }

    });
});