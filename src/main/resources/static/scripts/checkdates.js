/**
 * Created by Σταυρίνα on 30/9/2017.
 */

$(document).ready(function () {
    $('#tokoumpi').click(function () {


        var start = $("#datepicker1").datepicker( 'getDate' );
        var final = $("#datepicker2").datepicker( 'getDate' );
       // alert(start);
       /* var fields1 = start.split('-');
        var fields2 =final.split('-');

        a = new Date(fields1[0],fields1[1],fields1[2]);
        b = new Date(fields2[0],fields2[1],fields2[2]);

        alert(fields1[2]);

        c=new Date(start);
        d=new Date(final);
        */
        if(start> final) {
            $('#datesError').show();
            return false;
        }


    });
});
