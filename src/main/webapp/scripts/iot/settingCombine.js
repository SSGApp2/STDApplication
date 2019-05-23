var sensorOfMachine;
var create_or_update = 0;
var tempDeleteCombineDetail = [];

$('#btnAddSensor').click(function () {
    create_or_update = 0;
    var id = $('#ddlMachine').val();
            var seq = $('#divSensorAdd').find('.templateSensor').length + 1;
            if(seq < 7) {
                var key = createCriteriaTemplate(sensorOfMachine, 'ADD');
                var $ddlSensor = $('#ddlSensor_' + key);
                $ddlSensor.focus();
                if(seq == 6){
                    $('#addsensor_div').hide();
                }
            }

});



function getSensorByMachineID(id) {
    AjaxUtil.get('/api/iotsensors/findIotSensorByMachineID?id='+id).complete(function (xhr) {
        if (xhr.status = 200) {
            sensorOfMachine = xhr.responseJSON;
        }
    });
}

function createCriteriaTemplate(datasensor,mode,datadetail) {
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
    var $templateSensorID = 'templateSensorID_' + key;
    var $btnDeleteCombine = 'btnDeleteCombine_' + key;

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
        .replace('TemplateSensorID', $templateSensorID)
        .replace('btnDeleteCombine', $templateSensorID)
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

    if(mode == 'EDIT'){
            $('#templateSensorID_'+key).data("combinedetail", datadetail.combineDetailID);
            $('#ddlSensor_'+key).val(datadetail.sensorID);
            $('span#normalid_'+key).text(datadetail.normalValue);
            $('input[type="radio"][data-idtype="'+datadetail.valueType+'"][name="optradio1_'+key+'"]').attr('checked','checked');
            $('input[type="radio"][data-displaytype="'+datadetail.displayType+'"][name="optradio2_'+key+'"]').attr('checked','checked');
            $('#txtRate_'+key).val(datadetail.amount);

        var normalValue = datadetail.normalValue;
        if(normalValue != '') {
            calBothPositiveNegatine(key, datadetail.displayType, datadetail.valueType, parseFloat(normalValue), parseFloat(datadetail.amount));
        }
    }

    return key;

}

function calBothPositiveNegatine(keys,e1,e2,normal,rate) {
    if(e1 == 'A'){
        if(parseInt(e2) == 1){
            var ans = calAmount(normal,rate);
            setRateCal(keys,ans[0],ans[1]);
        }else {
            var anss = calPercent(normal,rate);
            var ans = calAmount(normal,anss);
            setRateCal(keys,ans[0],ans[1]);
    }
    }else if(e1 == 'P'){
        if(parseInt(e2) == 1){
            var ans = calAmount(normal,rate);
            setRateCal(keys,normal,ans[1]);
        }else {
            var anss = calPercent(normal,rate);
            var ans = calAmount(normal,anss);
            setRateCal(keys,normal,ans[1]);
        }
    }else if(e1 == 'N'){
        if(parseInt(e2) == 1){
            var ans = calAmount(normal,rate);
            setRateCal(keys,ans[0],normal);
        }else {
            var anss = calPercent(normal,rate);
            var ans = calAmount(normal,anss);
            setRateCal(keys,ans[0],normal);
        }
    }
}

function calPercent(a,b) {
    return (a/100) * b;
}
function calAmount(a,b) {
    return [a-b,a+b];
}

function calNomal(e) {
    var keyID = e.id.split('_')[1];
    var thisValue = e.value;
    var option1 =   $('input[type="radio"][name="optradio1_'+keyID+'"]:checked').data("idtype");
    var option2 =   $('input[type="radio"][name="optradio2_'+keyID+'"]:checked').data("displaytype");
    var normalValue = $('#ddlSensor_'+ keyID).find('option:selected').data("normalvalue");
    if(normalValue != '' && thisValue != '') {
        calBothPositiveNegatine(keyID, option2, option1, parseFloat(normalValue), parseFloat(thisValue));
    }
}

function setRateCal(key,r1,r2) {
    $('#formRate_'+key).find('input.rate1').val(r1);
    $('#formRate_'+key).find('input.rate2').val(r2);
}

function removeSensorItem(ele) {
    $('#divSensorAdd').hide();
    var key = ele.id.split('_')[1];
    if(create_or_update == 1){
       var idCombineDetail = $('#templateSensorID_'+key).data("combinedetail");
        tempDeleteCombineDetail.push(idCombineDetail);
        // console.log(key);
        // console.log(tempDeleteCombineDetail);
    }
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

    var seq = $('#divSensorAdd').find('.templateSensor').length + 1;
    if(seq <= 6) {
        $('#addsensor_div').show();

    }
}

$('#form_save_combine').submit(function(e) {
    e.preventDefault();
    const deviceID = $('#inputDevices').data("iddevice");
    var iotSensorCombineDetail = [];
    var countID = 0;
    $('#divSensorAdd .templateSensor').each(function (k, v) {
        var idCombineDetail = $(v).data("combinedetail");

       var data = {
            id: (idCombineDetail != '')?idCombineDetail:0,
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
if(create_or_update == 0) {
    AjaxUtil.post('/saveIotSensorCombine', JSON.stringify(data)).complete(function (xhr) {
        // console.log(xhr);
        findSensorComdineDetailAll();
    });
    }else{
    AjaxUtil.post('/updateSensorCombine?id='+ 103, JSON.stringify(data)).complete(function (xhr) {
       if(xhr.status == 200){
           console.log(xhr);
           if(tempDeleteCombineDetail.length != 0){
           deleteCombineDetail();
           tempDeleteCombineDetail = [];
           }
       }
    });
    }
});

function deleteCombineDetail() {
    var data = {dataid:tempDeleteCombineDetail};
    AjaxUtil.post('/deleteSensorCombineDetail', JSON.stringify(data)).complete(function (xhr) {
        if(xhr.status == 200){
            console.log(xhr);
        }
    });
}

function findSensorComdineDetailAll() {
    AjaxUtil.get('/sensorcombinedetailall').complete(function (xhr) {
        // console.log(JSON.parse(xhr.responseText));
        var data = JSON.parse(xhr.responseText);
        if(data != null) {
            setTableCombineDetail(data);
        }
    });
}

function groupBy(data) {
    var groups = {};
    var myArray = data;
    for (var i = 0; i < myArray.length; i++) {
        var groupName = data[i].combineID;
        if (!groups[groupName]) {
            groups[groupName] = [];
        }
        groups[groupName].push(myArray[i].sensorName);
    }
    myArray = [];
    for (var groupName in groups) {
        myArray.push({combineID: groupName, sensorName: groups[groupName]});
    }
    // console.log(myArray);
    return myArray;
}

function setTableCombineDetail(data) {
    $('#dataGrid').empty();
    var sensorGroupBy = groupBy(data);
    var iotSersor = [];
    var combineID = [];
    var tabletHtml = '<tr>';
    var countCol = 0;
   for(var i = 0; i < sensorGroupBy.length; i++){
       tabletHtml += '<tr>';
       for(var j = 0; j<sensorGroupBy[i].sensorName.length; j++){
           countCol++;
           tabletHtml += '<td>'+sensorGroupBy[i].sensorName[j]+'</td>';
           if(j==5){
               tabletHtml += '<td><span style="margin: 2px;" class="badge badge-warning waves-effect btnEditCombine" data-idcombine="'+sensorGroupBy[i].combineID+'" title="Edit">Edit</span>'
                   + '<span <span style="margin: 2px;" class="badge badge-danger waves-effect btnDeleteCombine" data-idcombine="'+sensorGroupBy[i].combineID+'" title="Delete">Delete</span></td>';
               countCol=0;
           }
       }
       if(countCol<5 && countCol != 0){
            for(var x=countCol; x < 6; x++){
                tabletHtml += '<td></td>';
                if(x==5){
                    tabletHtml += '<td><span <span style="margin: 2px;" class="badge badge-warning waves-effect btnEditCombine" data-idcombine="'+sensorGroupBy[i].combineID+'" title="Edit">Edit</span>'
                    + '<span <span style="margin: 2px;" class="badge badge-danger waves-effect btnDeleteCombine" data-idcombine="'+sensorGroupBy[i].combineID+'" title="Delete">Delete</span></td>';
                    countCol=0;
                }
            }
       }
       tabletHtml += '</tr>';
   }

    $('#dataGrid').append(tabletHtml);

   $('.btnEditCombine').click(function () {
       editCombineSetting($(this));
   });

   $('.btnDeleteCombine').click(function () {
       var idCombine = $(this).data("idcombine");
       MessageUtil.confirm('',function () {
       AjaxUtil.post('/deletesettingcombine?id='+idCombine).complete(function (xhr) {
           //console.log(xhr);
           if(xhr.status == 200) {
               location.reload();
           }
       });
   });
   });
}

function editCombineSetting(e) {
    create_or_update = 1;
    var idCombine = e.data("idcombine");
    var idMachine = $('#ddlMachine').val();
    var data;
    AjaxUtil.get('/api/iotsensors/findIotSensorByMachineID?id='+idMachine).complete(function (xhr) {
        if (xhr.status = 200) {
            sensorOfMachine = xhr.responseJSON;
            AjaxUtil.get('/finddetailallbyid?id='+idCombine).complete(function (xhrx) {
                if(xhrx.status==200){
                    data = JSON.parse(xhrx.responseText);
                    setForTemplateEdit(data, sensorOfMachine);
                }
            });
        }
    });


}

function setForTemplateEdit(datadetail, datasensor) {

    if(datadetail != null && datadetail.length != 0) {
        $('#ddlMachine').val(datadetail[0].machineID);
        $('#txtRepeat').val(datadetail[0].repeatAlert);
        $('#ddlRepeatUnit').val(datadetail[0].repeatUnit);
        $('#txtMessage').val(datadetail[0].alertMessage);
        for (var i = 0; i < datadetail.length; i++) {
            // console.log(datadetail[i]);
            createCriteriaTemplate(datasensor, 'EDIT', datadetail[i]);
        }
    }

}

$(function () {
    $('#deviceCombine').val($('#ddlMachine').find('option:selected').data("devicename"));
    $('#deviceCombine').data("iddevice",$('#ddlMachine').find('option:selected').data("iddevice"));
    findSensorComdineDetailAll();
   var idMachine = $('#ddlMachine').val();
    sensorOfMachine = getSensorByMachineID(idMachine);
});

$('#ddlMachine').change(function () {
    $('#deviceCombine').val($('#ddlMachine').find('option:selected').data("devicename"));
    $('#deviceCombine').data("iddevice", $('#ddlMachine').find('option:selected').data("iddevice"));
    var idMachine = $('#ddlMachine').val();
    sensorOfMachine = getSensorByMachineID(idMachine);
});
