var machineSetting = {};
var update_create_status = 0;
$('#btnAddMachine').click(function () {
    update_create_status = 0;
    $('#iotmachinemodel').modal('show');
    $('#titlemodalMachine').text('Add Machine');
    $('#machineName').val("");
    //machineSetting.FindDeviceNotMachine();
});

$('#btnSaveMachine').click(function () {
    var machineName = $('#machineName').val();
    var data = {
        "macName": machineName,
        "macCode": "",
        "id": $('#deviceName').val()
    };
    if(update_create_status == 0) {
        AjaxUtil.post('/api/iotmachines/createIotMachine', JSON.stringify(data)).complete(function (xhr) {
            console.log(xhr.status);
            location.reload();
        });
    }else{
        $.ajax({
            url: "/api/iotmachines/" + $('#machineName').data("idmachine"),
            type: 'PUT',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            complete: function(result) {
                console.log(result.status);
                location.reload();
            }
        });
    }
});

$('.btnEditMachine').click(function () {
    update_create_status = 1;
   var idMachine = $(this).data("idmachine");
   var machineName = $('.row-machine[data-idmachine='+idMachine+']').find('td:eq(0)').text();
   var deviceName = $('.row-machine[data-idmachine='+idMachine+']').find('td:eq(1)').text();
   var deviceID = $('.row-machine[data-idmachine='+idMachine+']').data("iddevice");
    $('#iotmachinemodel').modal('show');
    $('#deviceName').val(deviceID);
    $('#titlemodalMachine').text('Edit Machine');
    $('#machineName').val(machineName);
    $('#machineName').data("idmachine", idMachine);
    // machineSetting.FindDeviceNotMachine('EDIT',deviceID);
});

$('.btnDeleteMachine').click(function () {
    var idMachine = $(this).data("idmachine");
    var machineName = $('.row-machine[data-idmachine='+idMachine+']').find('td:eq(1)').text();
   AjaxUtil.delete('/rest-api/iotMachines/'+idMachine).complete(function (xhr) {
       console.log(xhr.status);
       if(xhr.status == 204) {
                        $('.row-machine[data-idmachine=' + idMachine + ']').remove();
                    }
   });
});

machineSetting.FindDeviceNotMachine = function (mode,id) {
    $('#deviceName').find("option").each(function () { $(this).show();});
    if(mode == 'EDIT') {
        $('#deviceName').find("option").each(function () {
            var idDeviceFromSelect = $(this).val();
            if ($(this).data("isused") == 'N' || id == idDeviceFromSelect) {
                $(this).show();
            } else {
                $(this).hide();
            }
        });
    }else{
        var count = 0;
        $('#deviceName').val(0);
        $('#deviceName').find("option").each(function () {
            if ($(this).data("isused") == 'N') {
                $(this).show();
                count++;
                if(count==1) {
                    $('#deviceName').val($(this).val());
                }
            } else {
                $(this).hide();
            }
        });
    }
}


