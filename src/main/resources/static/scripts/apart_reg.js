/**
 * Created by Arianna on 29/9/2017.
 */

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