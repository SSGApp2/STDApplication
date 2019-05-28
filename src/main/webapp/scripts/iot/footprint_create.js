const position_key = "position";
const machine_key = "machine-data";
var iotFootprint;
var footprint = {};
const DEFAULT_DEVICE_IMG = "/resources/images/cpu.png";
const DEFAULT_FLOOR_IMG = "/resources/images/floorplan/exampleFloorplan.jpg";
var IOT_FOOTPRINT = {};
const $fileNameField= $('[for="imgInp"]');
var $carousel = $('#carousel-list');
$(document).on({
    ajaxStart: function () {
        $('.dv-background').show();
    },
    ajaxStop: function () {
        $('.dv-background').hide();
    }
});

$(function () {
    footprint.renderFootprintList();
    $('form').validatr();
});

/***************************************************
 * Render List right bar
 * **************************************************/
footprint.renderFootprintList = function (foceId) {
    footprint.dataList = {};
    console.log('renderFootprintList');
    var $table = $('#tbFootprint');
    $table.empty();
    $('#txtFpName').val('');
    $fileNameField.text('');
    iotFootprint = null;
    $('.carousel-control-next,.carousel-control-prev').hide();

    AjaxUtil.get('/api/iotfootprints/findByOuth').success(function (data) {
        $.each(data, function (k, v) {
            footprint.dataList[v.id] = v;
            $table.append('<tr>' + '<td data-id="' + v.id + '" onclick="footprint.renderByFootprintId(\'' + v.id + '\')">' + v.name + '</td>' + '</tr>');
        });
        if (data.length == 0) {
            //default
            footprint.renderEmptyMachine();
            $carousel.append(' <div class="carousel-item active"> ' + '<img  class="dropzone img-responsive"  src="' + DEFAULT_FLOOR_IMG + '" >' + ' </div>');
            updateDropZone();
            rightEnable(3);
        } else {
            if(foceId){
                footprint.renderByFootprintId(foceId);
            }else{
                footprint.renderByFootprintId(data[0].id);
            }
            rightEnable();
            if(data.length>1){
                $('.carousel-control-next,.carousel-control-prev').show();
            }

        }
    });
}

/*************************************************
 *   renderByFootprintId In Used
 ***************************************************/
footprint.renderByFootprintId = function (id) {
    var v = footprint.dataList[id];
    $carousel.find('.carousel-item').remove();
    $fileNameField.text('');
    $('#tbFootprint').find('.active').removeClass('active');
    $('#tbFootprint').find('[data-id=\'' + id + '\']').addClass('active');
    var imgUrl = DEFAULT_FLOOR_IMG;
    if (v.picName) {
        imgUrl = '/api/common/image?fileName=' + v.picName + '&module=' + MODULE.FLOOR_PLAN;
    }
    $carousel.append(' <div class="carousel-item active"> ' + '<img  class="dropzone img-responsive"  src="' + imgUrl + '" >' + ' </div>');
    $carousel.find('.carousel-item').last().data('data-item', v);

    iotFootprint = v.id;
    $('#txtFpName').val(v.name);
    IOT_FOOTPRINT = v;

    footprint.renderEmptyMachine();
    updateDropZone();

    AjaxUtil.get('/api/iotfootprints/findByIotFootprint/' + id).success(function (data) {
        $.each(data, function (k, v) {
            $('#divDraggable').append(' <div class="ui-widget-content  draggable inZone dgOriginal">' +
                '<button class="btnRevert" onclick="revert(this)" style="float: right;">x</button>' +
                '<img class="img-responsive" src="' + DEFAULT_DEVICE_IMG + '"/>' +
                ' <p style="margin: 0px">' + v.iotMachine.iotDevice.deviceName + '</p>' +
                ' <p class="desc">' + v.iotMachine.macName + '</p>' +
                ' </div>');
            var element = $('#divDraggable').find('.draggable').last();
            element.data(machine_key, v.iotMachine);
            var pos = {
                x: v.posX,
                y: v.posY
            }
            element.data(position_key, pos);
            element.data(position_key + "_DEFAULT", pos);
            element.draggable({revert: 'invalid'});
            addTooltip(element);
            coordinates(element, v.posX, v.posY);
        });
        if (data.length > 0) {
            rightEnable();
        } else {
            rightDisabled();
        }
    });

}

footprint.renderEmptyMachine = function () {
    $('#divDraggable').empty();
    AjaxUtil.get('/api/iotmachines/findByNotInFootprintOuth','',false).success(function (data) {
        $.each(data, function (k, v) {
            $('#divDraggable').append(' <div class="ui-widget-content draggable empDevice">' +
                '<button class="btnRevert" onclick="revert(this)" style="float: right;display: none">x</button>' +
                '<img class="img-responsive" src="' + DEFAULT_DEVICE_IMG + '"/>' +
                ' <p style="margin: 0px">' + v.iotDevice.deviceName + '</p>' +
                ' <p class="desc">' + v.macName + '</p>' +
                ' </div>');
            $('#divDraggable').find('.draggable').last().data(machine_key, v);
        });
        $(".draggable").draggable({revert: 'invalid'});
        addTooltip($(".draggable"));
    });
}

function addTooltip(ele) {
    $(ele).mousemove(function (evt) {
        var deviceName = $(this).find('.desc').text();
        showTooltip(evt, deviceName);
    }).mouseout(function () {
        hideTooltip();
    });
}


var coordinates = function (element, per_x, per_y) {
    var width = parseFloat(per_x);
    var height = parseFloat(per_y);
    if (width < 0) width = 0;
    if (height < 0) height = 0;
    element.position({
        of: ".dropzone",
        my: "left top",
        at: "left+" + width + "%" + " top+" + height + "%",
        collision: "none none"

    });
}


function revert(ele) {
    const draggable = $(ele).closest('.draggable');
    draggable.animate({
        "left": 0,
        "top": 0
    });
    draggable.removeClass('inZone');
    draggable.data(position_key, '');
    $(ele).hide();
}

function revertAllEmpty() {
    const draggable = $('.empDevice');
    draggable.animate({
        "left": 0,
        "top": 0
    });
    draggable.removeClass('inZone');
    draggable.data(position_key, '');
    $('.empDevice').find('button').hide();
}



function updateDropZone() {
    $(".dropzone").droppable({
        drop: function (event, ui) {
            $(ui.draggable).find('button').show();

            const leftPosition = ui.offset.left - $(this).offset().left;
            const topPosition = ui.offset.top - $(this).offset().top;

            const targetW = event.target.clientWidth;
            const targetH = event.target.clientHeight;


            var x = (100 * parseFloat(leftPosition / parseFloat(targetW)));
            var y = (100 * parseFloat(topPosition / parseFloat(targetH)));

            if (x < 0) x = 0;
            if (y < 0) y = 0;

            console.log("X", x);
            console.log("Y", y);
            var position = {
                x: x,
                y: y
            }
            $(ui.draggable).addClass('inZone');
            $(ui.draggable).data(position_key, position);

        }
    });
}

function sortElement() {
    const last=$('#divDraggable div').not('.inZone').last();
    $('#divDraggable div.inZone').each(function () {
        $(this).insertAfter(last);
    });
}
