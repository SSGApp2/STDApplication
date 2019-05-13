var machineSetting = {};
$('#btnAddMachine').click(function () {
    $('#iotmachinemodel').modal('show');
    $('#titlemodalMachine').text('Add Machine');
    $('#machineName').val("");
    machineSetting.FindDeviceNotMachine();
});

$('#btnSaveMachine').click(function () {
    var machineName = $('#machineName').val();
    var data = {
        "iotDevice": {
            "id": 4
        },
        "macName": "Machine005"
    };
    console.log(data);
    AjaxUtil.post('/rest-api/iotMachines',JSON.stringify(data));
});

$('.btnEditMachine').click(function () {
   var idMachine = $(this).data("idmachine");
   var machineName = $('.row-machine[data-idmachine='+idMachine+']').find('td:eq(1)').text();
   var deviceName = $('.row-machine[data-idmachine='+idMachine+']').find('td:eq(2)').text();
   var deviceID = $('.row-machine[data-idmachine='+idMachine+']').data("iddevice");
    $('#iotmachinemodel').modal('show');
    $('#deviceName').val(deviceID);
    $('#titlemodalMachine').text('Edit Machine');
    $('#machineName').val(machineName);
    machineSetting.FindDeviceNotMachine('EDIT',deviceID);
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


