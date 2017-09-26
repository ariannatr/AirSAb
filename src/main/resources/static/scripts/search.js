
$(document).ready(function(){
    //$("#secondarytext").hide();

    $("#searchnow").click(function(){
        $("#initialtext").hide();
        $("#secondarytext").show();
    });
    $("#backnow").click(function(){
        $("#secondarytext").hide();
        $("#initialtext").show();
    });
});