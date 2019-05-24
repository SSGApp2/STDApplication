$("#j_username,#j_password").keypress(function(event){
    if ((event.charCode >= 48 && event.charCode <= 57) || // 0-9
        (event.charCode >= 65 && event.charCode <= 90) || // A-Z
        (event.charCode >= 97 && event.charCode <= 122)){ // a-z
        return true;
    }else{
        event.preventDefault();
        return false;
    }
});

$(function () {
    if (document.URL.indexOf('error') > 0) {
        $('#msg').html('Username or Password Incorrect')
    } else {
        $('#msg').html('')
    }
})