var iotFootprint=3;
var tooltip = document.getElementById("tooltip");
const position_key = "position";
const machine_key = "machine-data";

$(window).on('load', function (e) {

    renderByFootprintId(iotFootprint);

});

var DEVICE_SELECT;
function showTooltip(evt, deviceName) {

    var text = "<p><h4>"+deviceName+"</h4></p>";
    text += "<p><span class='fa fa-battery-full logo text-success'/> <label>Good</label></p>";
    text += "<p><span class='fa fa-cog logo text-success'/> <label>Run</label></p>";
    text += "<p><span class='fa fa-line-chart logo text-success'/> <label>Normal</label></p>";

    DEVICE_SELECT=deviceName;

    tooltip.innerHTML = text;
    tooltip.style.display = "block";
    tooltip.style.left = evt.pageX + 35 + 'px';
    tooltip.style.top = evt.pageY + 50 + 'px';

}

function linkDevice(ele) {
    var data=$(ele).data(machine_key);
    console.log(data);
    window.location.href = '/dashboard/device/'+data.id;
}

function hideTooltip() {
    console.log('Hide');
    tooltip.style.display = "none";
}


function renderByFootprintId(id) {
    AjaxUtil.get('/api/iotfootprints/findByIotFootprint/'+id).success(function (data) {

        $.each(data, function (k, v) {
            $('#carouselExampleFade').append(' <div onclick="linkDevice(this)" class="ui-widget-content draggable inZone">' +
                ' <p style="margin: 0px">' + v.iotMachine.iotDevice.deviceName + '</p>' +
                ' <p>' + v.iotMachine.macName + '</p>' +
                ' </div>');
            var element = $('#carouselExampleFade').find('.draggable').last();
            element.data(machine_key, v.iotMachine);
            element.data(position_key,  {
                x: v.posX,
                y: v.posY
            });
            element.draggable({revert: 'invalid'});
            coordinates(element, v.posX, v.posY);
            element.draggable( "disable" )
        });
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