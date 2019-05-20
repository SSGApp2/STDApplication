var $deviceCode;
var $dataDeviceCurrent = [];

$(function () {
    $('.touchspin').TouchSpin({
        min: -1000000000,
        decimals: 2,
        step: 0.01,
        boostat: 5,
        maxboostedstep: 10,
        max: 10000000000
        // initval: 0
    });
})

function updateLabel() {
    $('.inpWarning').change();
}

$('.txtNomal,.valueType,.displayType').change(function () {
    updateLabel();
})

$('.inpWarning').change(function () {
    var nomalValue = $(this).closest('.settingHead').find('.txtNomal').val();
    var current = $(this).val();
    const valueType = $(this).closest('.settingHead').find('.valueType:checked').val(); //0 percent - 1 amount
    const displayType = $(this).closest('.settingHead').find('.displayType:checked').val();
    nomalValue = parseFloat(nomalValue);
    current = parseFloat(current);
    if (valueType == "1") {//amount
        var valPost = nomalValue + current;
        var valNeg = nomalValue - current;

    } else if (valueType == "0") {//percent
        var valPost = nomalValue + (nomalValue / 100.0 * current);
        var valNeg = nomalValue - (nomalValue / 100.0 * current);
    }
    $(this).closest('.warningForm').find('.display-val').text('');
    if (valPost && valNeg) {
        valPost = valPost.toFixed(2);
        valNeg = valNeg.toFixed(2);
        var text = "";
        if (displayType == "A") {
            text = "<=" + valNeg + " || >=" + valPost;
        } else if (displayType == "P") {
            text = ">=" + valPost;
        } else if (displayType == "N") {
            text = "<=" + valNeg;
        }
        $(this).closest('.warningForm').find('.display-val').text(text);

    }
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
    $('#divSensorRange').find('select').val('');
    $('#divSensorRange').find('[type="number"]').val('');
    $('#divSensorRange').find('[type="text"]').val('');
    $('#divSensorRange').find('[type="radio"]').prop('checked', false);
    $('[data-toggle="toggle"][type="checkbox"]').bootstrapToggle('off');
    updateLabel();
}

function renderData() {
    var iotMachine = {
        id: $('#ddlDevice :selected').attr('data-id')
    }

    AjaxUtil.get('/rest-api/iotMachines/' + iotMachine.id + '/iotSensor').complete(function (xhr) {
        if (xhr.status == 200) {
            $dataDeviceCurrent = xhr.responseJSON.content;
            renderDataToForm();
            updateLabel();
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
    if ($('#ddlDevice').val() == "") return false;


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
            IotSensor.version = v.version;
            IotSensor.iotMachine = iotMachine;
        }
        arrSensor.push(IotSensor);
    }

    AjaxUtil.post(url, JSON.stringify(arrSensor)).success(function (data) {
        $dataDeviceCurrent = data;
        renderDataToForm();
        MessageUtil.alert("Save Success");
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

