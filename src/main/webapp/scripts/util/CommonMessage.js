var MessageUtil = {};

MessageUtil.alert = function (message) {
    // bootbox.alert({
    //     size: "small",
    //     message: message
    // })

    Swal.fire({
        type: 'success',
        title: message,
        showConfirmButton: false,
        timer: 1500
    })
}

MessageUtil.alertWarning = function (message) {
    Swal.fire({
        type: 'warning',
        title: message,
        showConfirmButton: false,
        timer: 1500
    })
}

MessageUtil.confirm = function (message, funcCallBackOk,funcCallBackNo) {

    Swal.fire({
        title: 'Are you sure?',
        text: message,
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
    }).then((result) => {
        if (result.value) {
            if (result) {
                if (isFunction(funcCallBackOk)) {
                    funcCallBackOk();
                }
            }
        }else{
            if (isFunction(funcCallBackNo)) {
                funcCallBackNo();
            }
        }
    });
}

function isFunction(functionToCheck) {
    if (typeof functionToCheck === "function") {
        return true;
    }
    return false;
}

MessageUtil.notify=function(msg){
    $.notify({
        title: "Welcome:",
        message: "This plugin has been provided to you by Robert McIntosh aka mouse0270"
    });

}

MessageUtil.notify=function(msg){
    MessageUtil.commonNotify(msg);
}
MessageUtil.notifySuccess=function(msg){
    MessageUtil.commonNotify(msg,'success');
}
MessageUtil.notifyDanger=function(msg){
    MessageUtil.commonNotify(msg,'danger');
}
MessageUtil.notifyWarning=function(msg){
    MessageUtil.commonNotify(msg,'warning');
}

MessageUtil.commonNotify=function (msg,type) {
    $.notifyClose();
    $.notify({
        message: msg
    },{
        type: type
    });
}

