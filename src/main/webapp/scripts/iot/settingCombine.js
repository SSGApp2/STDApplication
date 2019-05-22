$('#btnAddSensor').click(function () {
    var id = $('#ddlMachine').val();
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
    var key = new Date().getTime();
    var $ddlSensor = 'ddlSensor_' + key;
    var $ddlCondition = 'ddlCondition_' + key;
    var $txtRate = 'txtRate_' + key;
    var $txtRate1 = 'txtRate1_' + key;
    var $txtRate2 = 'txtRate2_' + key;
    var $formRate = 'formRate_' + key;
    var $optradio1 = 'optradio1_' + key;
    var $optradio2 = 'optradio2_' + key;
    var $normalid = 'normalid_' + key;

    var template = $('#templateSensor')[0].innerHTML
        .replace('ddlSensor', $ddlSensor)
        .replace('ddlCondition', $ddlCondition)
        .replace('txtRate', $txtRate)
        .replace('txtRate1', $txtRate1)
        .replace('txtRate2', $txtRate2)
        .replace('optradio1', $optradio1)
        .replace('optradio2', $optradio1)
        .replace('optradio3', $optradio2)
        .replace('optradio4', $optradio2)
        .replace('optradio5', $optradio2)
        .replace('formRate', $formRate)
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
        var datasensorNormal = $(this).find('option:selected').data("normalvalue");
        $('span#normalid_'+keyid).text(datasensorNormal);
        $('input#txtRate_'+keyid).data("normalvalue",datasensorNormal);
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

function calNomal(e) {
    // console.log(e.id.split('_')[1]);
    var keyID = e.id.split('_')[1];
    var thisValue = e.value;
    $('#formRate_'+keyID).find('input.rate1').val()
    var normalValue = $('#ddlSensor_'+ keyID).find('option:selected').data("normalvalue");
    $('#formRate_'+keyID).find('input.rate1').val(parseFloat(normalValue) - parseFloat(thisValue));
    $('#formRate_'+keyID).find('input.rate2').val(parseFloat(normalValue) + parseFloat(thisValue));
    console.log(normalValue+'  '+thisValue);
    // $('input#txtRate_'+keyid).text(datasensorNamol);
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

$('#btnSaveCombine').click(function () {
    const deviceID = $('#inputDevices').data("iddevice");
    var iotSensorCombineDetail = [];
    var countID = 0;
    $('#divSensorAdd .templateSensor').each(function (k, v) {
        var data = {
            iotSensor: {id:$(v).find('select.sensor').val()},
            valueType:  $(v).find('input.value-type[type="radio"]:checked').data("idtype"),
            amount: $(v).find('input.rate').val(),
            displayType: $(v).find('input.display-type[type="radio"]:checked').data("displaytype"),
            iotSensorCombine:{id:countID}
        };
        iotSensorCombineDetail.push(data);
        // console.log(v)
        countID++;
    });

    if (iotSensorCombineDetail.length == 0) {
        MessageUtil.alertWarning("Please add sensor");
        return false;
    }

    var data = {
        alertMessage: $('#txtMessage').val(),
        repeatUnit:$('#ddlRepeatUnit').val(),
        repeatAlert:$('#txtRepeat').val(),
        iotSensorCombineDetails: iotSensorCombineDetail
    };

    AjaxUtil.post('/saveIotSensorCombine', JSON.stringify(data)).complete(function (xhr) {
        // console.log(xhr);
    });

});

$(function () {
    $('#deviceCombine').val($('#ddlMachine').find('option:selected').data("devicename"));
    $('#deviceCombine').data("iddevice",$('#ddlMachine').find('option:selected').data("iddevice"));
});

$('#ddlMachine').change(function () {
    $('#deviceCombine').val($('#ddlMachine').find('option:selected').data("devicename"));
    $('#deviceCombine').data("iddevice", $('#ddlMachine').find('option:selected').data("iddevice"));
});
