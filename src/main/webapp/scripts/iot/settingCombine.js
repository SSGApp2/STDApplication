$('#btnAddSensor').click(function () {
    var id = $('#ddlMachine').val();
    // AjaxIotSensor.findByMachineID($('#ddlMachine').val()).complete(function (xhr) {
    //     if (xhr.status = 200) {
    //         var data = xhr.responseJSON;
    //         var seq = $('#divSensorAdd').find('.templateSensor').length + 1;
    //         if(data.length != 0 && seq < 7) {
    //             var key = createCriteriaTemplate(data);
    //             var $ddlSensor = $('#ddlSensor' + key);
    //             $ddlSensor.focus();
    //         }
    //     }
    // });

    AjaxUtil.get('/api/iotsensors/findIotSensorByMachineID?id='+id).complete(function (xhr) {
        if (xhr.status = 200) {
            var data = xhr.responseJSON;
            var seq = $('#divSensorAdd').find('.templateSensor').length + 1;
            if(data.length != 0 && seq < 7) {
                var key = createCriteriaTemplate(data);
                var $ddlSensor = $('#ddlSensor' + key);
                $ddlSensor.focus();
            }
        }});
});

function createCriteriaTemplate(datasensor) {
    $('#divSensorAdd').show();
    var seq = $('#divSensorAdd').find('.templateSensor').length + 1;
    var key = seq;
    var $ddlSensor = 'ddlSensor_' + key;
    var $ddlCondition = 'ddlCondition_' + key;
    var $txtRate = 'txtRate_' + key;
    var $normalid = 'normalid_' + key;

    var template = $('#templateSensor')[0].innerHTML
        .replace('ddlSensor', $ddlSensor)
        .replace('ddlCondition', $ddlCondition)
        .replace('txtRate', $txtRate)
        .replace('seq', seq)
        .replace('normalid', $normalid);
    $('#divSensorAdd').append(template);


    var deviceCode = $('#ddlDevice').find('option:selected').attr('code');
    var iotSensor = $('option[code="' + deviceCode + '"]').data();
    $ddlSensor = $('#' + $ddlSensor);
    $ddlSensor.empty();
    $ddlSensor.append('<option></option>');
    $('select#ddlSensor_'+key).change(function () {
        var keyid = $(this).attr("id").split('_')[1];
        var datasensorNamol = $(this).find('option:selected').data("normalvalue");
        $('span#normalid_'+keyid).text(datasensorNamol);
    });
    $.each(datasensor,function (k, v) {
        $ddlSensor.append('<option  value="' + v.id + '" code="' + v.sensorCode + '" version="' + v.version + '"data-normalvalue="'+ v.normalValue +'" >' + v.sensorName + '</option>');
    });
    if (seq > 0) {
        $('#ddlDevice').attr('disabled', true);
    } else {
        $('#ddlDevice').attr('disabled', false);
    }

    return key;

}

function removeSensorItem(ele) {
    $('#divSensorAdd').hide();

    $(ele).closest('.templateSensor').parent().empty();
    $('#ddlDevice').attr('disabled', false);
    $('#divSensorAdd .panel-title').each(function (i, ele) {
        $('#ddlDevice').attr('disabled', true);
        $('#divSensorAdd').show();
        $(ele).text(i + 1);

    });

    $('div.templateID').each(function (i, ele) {
        if($(this).children().length==0){
            $(this).remove();
        }
    });
}

$(function () {
    $('#deviceCombine').val($('#ddlMachine').find('option:selected').data("devicename"));
    $('#deviceCombine').data("iddevice",$('#ddlMachine').find('option:selected').data("iddevice"));
});

$('#ddlMachine').change(function () {
    $('#deviceCombine').val($('#ddlMachine').find('option:selected').data("devicename"));
    $('#deviceCombine').data("iddevice", $('#ddlMachine').find('option:selected').data("iddevice"));
});
