var $deviceCode;
var $dataDeviceCurrent;

$(function () {
    $('.touchspin').TouchSpin({
        min: -1000000000,
        // initval: 0
    });
})


$('#ddlDevice').change(function () {
    //Clear Filed
    clearField();

    if ($(this).val() == '') {
        $('#btnSave').parent().attr('disabled', true).removeClass("csave");
        $dataDeviceCurrent = [];
    } else {
        $('#btnSave').parent().attr('disabled', false).addClass("csave");
    }
    var deviceCode = $(this).find('option:selected').attr('code');
    var iotSensor = $('option[code="' + deviceCode + '"]').data();
    $deviceCode = deviceCode;
    var $ddlEle = $('#ddlSensor');
    $ddlEle.empty();
    $ddlEle.append('<option></option>');
    if (iotSensor) {
        $.each(iotSensor, function (k, v) {
            if (v.sensorCode) {
                $ddlEle.append('<option value="' + v.id + '" code="' + v.sensorCode + '" version="' + v.version + '" >' + v.sensorCode + '</option>');
            }
        });
        renderData();
        // renderSensorRateCombine(deviceCode);
    }
});

function clearField() {
    $('#divSensorRange').find('input,select').val('');
    $('#divSensorRange').find('[type="radio"]').prop('checked', false);
    $('[data-toggle="toggle"][type="checkbox"]').bootstrapToggle('off');
}


function renderData() {
    AjaxUtil.get('/api/iotsensors/findByDeviceCodeOth', {deviceCode: $deviceCode}).complete(function (xhr) {
        if (xhr.status == 200) {
            $dataDeviceCurrent = xhr.responseJSON;
            console.log($dataDeviceCurrent);
            $('#divSensorRange input[data-toggle="toggle"]').bootstrapToggle('on');
            var mapData = {};
            $.each($dataDeviceCurrent, function (k, v) {
                mapData[v.sensorCode] = v;
            });
            for (var i = 0; i < $('#divSettingRange .panel').length; i++) {

                var $ele = $('#divSettingRange .panel:eq(' + i + ')');
                const sensorCode = $ele.attr('sensor-code');
                var v = mapData[sensorCode];
                if (v != undefined) {
                    $ele.attr('idSensorWarning', v.id);
                    const key = $ele.attr('id');
                    $('#txtNomal' + key).val(v.normalValue);

                    if (v.iotSensorRange.valueType == "0") { //percent
                        $('[name="type1' + key + '"]:eq(0)').prop('checked', true); //percent
                    } else if (v.iotSensorRange.valueType == "1") {
                        $('[name="type1' + key + '"]:eq(1)').prop('checked', true); //amount
                    }

                    $('[name="type2' + key + '"][value="' + v.iotSensorRange.displayType + '"]').prop('checked', true); //percent


                    $('#txtWarning' + key).val(v.iotSensorRange.warningAmt);
                    $('#txtWarningAlert' + key).val(v.iotSensorRange.warningAlert);
                    $('#ddlAlertTimeWR' + key).val(v.iotSensorRange.warningUnit);

                    $('#txtDangerous' + key).val(v.iotSensorRange.dangerAmt);
                    $('#txtDangerAlert' + key).val(v.iotSensorRange.dangerAlert);
                    $('#ddlAlertTimeDG' + key).val(v.iotSensorRange.dangerUnit);

                    if (v.isActive == 'Y') {
                        $('#chkActive' + key).bootstrapToggle('on');
                    } else {
                        $('#chkActive' + key).bootstrapToggle('off');
                    }
                }


            }

        }
    });
}


function saveSensorRange() {
    var listAjaxSaveData = [];
    $.each($dataDeviceCurrent, function (k, v) {
        var IotSensor = {};
        var dataForm = getDataFontByIdSensor(v.id);
        IotSensor.id = v.id;
        IotSensor.isActive = dataForm.isActive;
        IotSensor.normalValue = dataForm.normalValue;
        IotSensor.iotSensorRange = dataForm;
        IotSensor.iotSensorRange.id = v.iotSensorRange.id;
        IotSensor.iotSensorRange.isActive = null;
        listAjaxSaveData.push(AjaxUtil.patch('/rest-api/iotSensors/' + v.id, JSON.stringify(IotSensor)));
    });
    // listAjaxSaveData.push(saveCombineDemo());
    if (listAjaxSaveData.length > 0) {
        AjaxUtil.list(listAjaxSaveData).done(function (x) {
            alert('Save Success');
        });
    }

}

function getDataFontByIdSensor(id) {
    var $ele = $('[idSensorWarning="' + id + '"]');
    const key = $ele.attr('id');
    var data = {
        normalValue: $('#txtNomal' + key).val(),
        warningAmt: $('#txtWarning' + key).val(),
        warningAlert: $('#txtWarningAlert' + key).val(),
        warningUnit: $('#ddlAlertTimeWR' + key + '').val(),

        dangerAmt: $('#txtDangerous' + key).val(),
        dangerAlert: $('#txtDangerAlert' + key).val(),
        dangerUnit: $('#ddlAlertTimeDG' + key + '').val(),

        displayType: $('[name="type2' + key + '"]:checked').val(),
        isActive: $('#chkActive' + key).prop('checked'),
    }
    if (data.isActive) {
        data.isActive = 'Y';
    } else {
        data.isActive = 'N';
    }
    const isPercent = $('[name="type1' + key + '"]:checked').val() == 0;
    if (isPercent) {
        data.valueType = "0";
    } else {
        data.valueType = "1";
    }
    return data;
}

//
//temp
$('#txtNomaltag1').change(function () {
    $('#lbSS1warningAvd').text($(this).val());
    $('#lbSS1dangerAvd').text($(this).val());
});

$('#txtNomaltag5').change(function () {
    $('#lbSS2warningAvd').text($(this).val());
    $('#lbSS2dangerAvd').text($(this).val());
});

