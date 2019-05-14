var $deviceCode;
var $dataDeviceCurrent = [];

$(function () {
    $('.touchspin').TouchSpin({
        min: -1000000000,
        max:10000000000
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
    var iotMachine = {
        id: $('#ddlDevice :selected').attr('data-id')
    }

    AjaxUtil.get('/rest-api/iotMachines/' + iotMachine.id + '/iotSensor').complete(function (xhr) {
        if (xhr.status == 200) {
            $dataDeviceCurrent = xhr.responseJSON.content;
            renderDataToForm();
        }
    });
}

function renderDataToForm() {

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

            if (v.valueType == "0") { //percent
                $('[name="type1' + key + '"]:eq(0)').prop('checked', true); //percent
            } else if (v.valueType == "1") {
                $('[name="type1' + key + '"]:eq(1)').prop('checked', true); //amount
            }

            $('[name="type2' + key + '"][value="' + v.displayType + '"]').prop('checked', true); //percent


            $('#txtWarning' + key).val(v.warningAmt);
            $('#txtWarningAlert' + key).val(v.warningAlert);
            $('#ddlAlertTimeWR' + key).val(v.warningUnit);

            $('#txtDangerous' + key).val(v.dangerAmt);
            $('#txtDangerAlert' + key).val(v.dangerAlert);
            $('#ddlAlertTimeDG' + key).val(v.dangerUnit);

            if (v.isActive == 'Y') {
                $('#chkActive' + key).bootstrapToggle('on');
            } else {
                $('#chkActive' + key).bootstrapToggle('off');
            }
        }


    }
}


function saveSensorRange() {
    //validate
    if($('#ddlDevice').val()=="")return false;


    var listAjaxSaveData = [];

    var mapData = {};
    $.each($dataDeviceCurrent, function (k, v) {
        mapData[v.sensorCode] = v;
    });

    var iotMachine = {
        id: $('#ddlDevice :selected').attr('data-id')
    }

    var arrSensor = [];
    for (var i = 0; i < $('#divSettingRange .panel').length; i++) {
        var $ele = $('#divSettingRange .panel:eq(' + i + ')');
        const sensorCode = $ele.attr('sensor-code');
        var v = mapData[sensorCode];
        var dataForm = getDataFontByElement($ele);
        var IotSensor = dataForm;
        var url = '/api/iotsensors/saveOrUpdate?id=' + iotMachine.id;
        if (v != undefined) {
            IotSensor.id = v.id;
            IotSensor.version=v.version;
            IotSensor.iotMachine=iotMachine;
        }
        arrSensor.push(IotSensor);
    }

    AjaxUtil.post(url, JSON.stringify(arrSensor)).success(function (data) {
        $dataDeviceCurrent=data;
        renderDataToForm();
    });


    // listAjaxSaveData.push(saveCombineDemo());
    // if (listAjaxSaveData.length > 0) {
    //     AjaxUtil.list(listAjaxSaveData).done(function (x) {
    //         alert('Save Success');
    //     });


}

function getDataFontByElement($ele) {
    // var $ele = $('[idSensorWarning="' + id + '"]');
    const key = $ele.attr('id');
    var data = {
        sensorCode: $ele.attr('sensor-code'),
        sensorName: $ele.attr('sensor-name'),

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

