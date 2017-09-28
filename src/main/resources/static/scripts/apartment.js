/**
 * Created by Σταυρίνα on 27/9/2017.
 */
function ask(){
    $("#qform").show();
}
function post(){
    $("#qform").hide();
}

function change(){
    $("#yourtext1").hide();
    $("#yourtext2").show();
}
function back(){
    $("#yourtext2").hide();
    $("#yourtext1").show();
}

$('#search_country').change(function() {
    $('#country').val($('#search_country').val());
});

$('#search_town').change(function() {
    $('#town').val($('#search_town').val());
});

$('#search_area').change(function() {
    $('#area').val($('#search_area').val());
});

$('#search_address').change(function() {
    $('#address').val($('#search_address').val());
});

$('#search_address_number').change(function() {
    $('#address_number').val($('#search_address_number').val());
});

