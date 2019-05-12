

var $deviceCode;
var $dataDeviceCurrent;

$(function () {
    $('.touchspin').TouchSpin({
        min: -1000000000,
        initval: 0
    });
})


$('#ddlDevice').change(function () {
    if ($(this).val() == '') {
        $('#btnSave').attr('disabled', true)
    } else {
        $('#btnSave').attr('disabled', false)
    }
    var deviceCode = $(this).find('option:selected').attr('code');
    var iotSensor = $('option[code="' + deviceCode + '"]').data();
    $deviceCode = deviceCode;
    var $ddlEle = $('#ddlSensor');
    $ddlEle.empty();
    $ddlEle.append('<option></option>');
    $.each(iotSensor, function (k, v) {
        if (v.sensorCode) {
            $ddlEle.append('<option value="' + v.id + '" code="' + v.sensorCode + '" version="' + v.version + '" >' + v.sensorCode + '</option>');
        }
    });
    renderData();
    // renderSensorRateCombine(deviceCode);

});


function renderData() {
    AjaxUtil.get('/api/iotsensors/findByDeviceCodeOth',{deviceCode:$deviceCode}).complete(function (xhr) {
        if (xhr.status == 200) {
            $dataDeviceCurrent = xhr.responseJSON;
            console.log($dataDeviceCurrent );
            $('#divSensorRange input[data-toggle="toggle"]').bootstrapToggle('on');
            var mapData = {};
            $.each($dataDeviceCurrent, function (k, v) {
                mapData[v.sensorCode] = v;
            });
            for (var i = 0; i < $('#divSettingRange .panel').length; i++) {

                var $ele = $('#divSettingRange .panel:eq(' + i + ')');
                const sensorCode = $ele.attr('sensor-code');
                var v = mapData[sensorCode];
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

                if (v.isActive) {
                    $('#chkActive' + key).bootstrapToggle('on');
                } else {
                    $('#chkActive' + key).bootstrapToggle('off');
                }

            }

        }
    });
}

$('#btnSave').click(function () {
    var listAjaxSaveData = [];
    $.each($dataDeviceCurrent, function (k, v) {
        var data = getDataFontByIdSensor(v.id);
        listAjaxSaveData.push(AjaxIotSensorRange.patchData(v.id, JSON.stringify(data)));
    });

    listAjaxSaveData.push(saveCombineDemo());

    $.when(null, listAjaxSaveData).then(function () {
        // do something when all requests are done.
        MessageUtil.alert('Save Success');
    });
});

function getDataFontByIdSensor(id) {
    var $ele = $('[idSensorWarning="' + id + '"]');
    const key = $ele.attr('id');
    var data = {
        normalVal: $('#txtNomal' + key).val(),
        warningPercent: $('#txtWarning' + key).val(),
        warningAmt: $('#txtWarning' + key).val(),
        warningAlert: $('#txtWarningAlert' + key).val(),
        dangerPercent: $('#txtDangerous' + key).val(),
        dangerAmt: $('#txtDangerous' + key).val(),
        dangerAlert: $('#txtDangerAlert' + key).val(),
        displayType: $('[name="type2' + key + '"]:checked').val(),
        warningUnit: $('#ddlAlertTimeWR' + key + '').val(),
        dangerUnit: $('#ddlAlertTimeDG' + key + '').val(),
        isActive: $('#chkActive' + key).prop('checked'),
    }
    const isPercent = $('[name="type1' + key + '"]:checked').val() == 0;
    if (isPercent) {
        data.warningAmt = null;
        data.dangerAmt = null;
        data.valueType="0";
    } else {
        data.warningPercent = null;
        data.dangerPercent = null;
        data.valueType="1";
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

