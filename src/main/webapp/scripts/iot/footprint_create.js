const position_key = "position";
const machine_key = "machine-data";
var iotFootprint=3;
$(document).on({
    ajaxStart: function () {
        $('.dv-background').show();
    },
    ajaxStop: function () {
        $('.dv-background').hide();
    }
});


$(function () {
    //
    renderEmptyMachine();
    renderByFootprintId(iotFootprint);
    renderFootprintList();

    $(".dropzone").droppable({
        drop: function (event, ui) {
            $(ui.draggable).find('button').show();

            const leftPosition = ui.offset.left - $(this).offset().left;
            const topPosition = ui.offset.top - $(this).offset().top;

            const targetW = event.target.clientWidth;
            const targetH = event.target.clientHeight;
            var x = (100 * parseFloat(leftPosition / parseFloat(targetW)));
            var y = (100 * parseFloat(topPosition / parseFloat(targetH)));
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
});

function renderFootprintList() {
    var $table=$('#tbFootprint');
    $table.empty();
    AjaxUtil.get('/api/iotfootprints/findByOuth').success(function (data) {
        $.each(data,function (k,v) {
            $table.append('<tr>' +
                '<td>'+v.name+'</td>' +
                '</tr>');
            $table.find('td:eq(0)').addClass('active');
        })
    });
}

function renderByFootprintId(id) {
    AjaxUtil.get('/api/iotfootprints/findByIotFootprint/'+id).success(function (data) {

        $.each(data, function (k, v) {
            $('#divDraggable').append(' <div class="ui-widget-content draggable inZone">' +
                '<button class="btnRevert" onclick="revert(this)" style="float: right;">x</button>' +
                ' <p style="margin: 0px">' + v.iotMachine.iotDevice.deviceName + '</p>' +
                ' <p>' + v.iotMachine.macName + '</p>' +
                ' </div>');
            var element = $('#divDraggable').find('.draggable').last();
            element.data(machine_key, v.iotMachine);
            element.data(position_key,  {
                x: v.posX,
                y: v.posY
            });
            element.draggable({revert: 'invalid'});
            coordinates(element, v.posX, v.posY);
        });

    });
}

function renderEmptyMachine() {
    AjaxUtil.get('/api/iotmachines/findByNotInFootprintOuth').success(function (data) {
        $.each(data, function (k, v) {
            $('#divDraggable').append(' <div class="ui-widget-content draggable">' +
                '<button class="btnRevert" onclick="revert(this)" style="float: right;display: none">x</button>' +
                ' <p style="margin: 0px">' + v.iotDevice.deviceName + '</p>' +
                ' <p>' + v.macName + '</p>' +
                ' </div>');
            $('#divDraggable').find('.draggable').last().data(machine_key, v);
        });
        $(".draggable").draggable({revert: 'invalid'});
    });
}

var coordinates = function (element, per_x, per_y) {

    const width = $('.dropzone').width() / 100 * parseFloat(per_x);
    const height = $('.dropzone').height() / 100 * parseFloat(per_y);
    console.log(width,height)
    element.position({
        my: "left+" + width + " top+" + height,
        at: "left top",
        of: ".dropzone"
    });
}


function revert(ele) {
    const draggable = $(ele).closest('.draggable');

    draggable.animate({
        "left":0,
        "top": 0
    });
    draggable.removeClass('inZone');
    draggable.data(position_key, '');
    $(ele).hide();
}


$('#btnSave').click(function () {

    var machine = [];
    $('.draggable.inZone').each(function () {
        const data = $(this).data(machine_key);
        const position = $(this).data(position_key);
        var dataMachine = {
            machine: data.id,
            posX: position.x,
            posY: position.y,
        }
        machine.push(dataMachine);
    });
    var data = {
        picturePath: "default.jpg",
        name: "template",
        machine: machine,
        footPrint:iotFootprint
        // ouCode:session.ouCode
    }
    AjaxUtil.post('/api/iotfootprints', JSON.stringify(data)).success(function (data) {
        console.log(data);
    });
});

$('#btnRevert').click(function () {
    $('#divDraggable').empty();
    renderEmptyMachine();
    renderByFootprintId(iotFootprint);
});
