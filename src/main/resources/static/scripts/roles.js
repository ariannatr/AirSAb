
var typ=0;
function validate(){
    var remember = document.getElementById('ownertype');
    if(remember.checked)
        typ=typ+1 ;
    else
        typ=typ-1;
}
function validate2(){
    var remember2 = document.getElementById('rentertype');
    if(remember2.checked)
        typ=typ+2 ;
    else
        typ=typ-2;
}

function complete(){
    document.getElementById('type').value=typ;
}