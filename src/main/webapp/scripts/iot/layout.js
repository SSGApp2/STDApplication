var tooltip = document.getElementById("tooltip");
const position_key = "position";
const machine_key = "machine-data";
const DEFAULT_DEVICE_IMG = "/resources/images/cpu.png";
const DEFAULT_FLOOR_IMG = "/resources/images/floorplan/exampleFloorplan.jpg";


$(window).on('load', function (e) {
    $('.carousel-control-next,.carousel-control-prev').hide();
    const dataLength=$('#carouselExampleFade .carousel-item').length;
    if (dataLength > 0) {
        $('#carouselExampleFade .carousel-item:eq(0)').addClass('active');
        renderByFootprintId();
        if(dataLength>1){
            $('.carousel-control-next,.carousel-control-prev').show();
        }
    }else{
        $('#carouselExampleFade .carousel-inner').append(' <div class="carousel-item active" >' +
            '<img class="dropzone img-responsive" src="'+DEFAULT_FLOOR_IMG+'"/>' +
            '</div>');
    }
});


$(window).on('resize', function(){
    $('.draggable').each(function () {
        var element=$(this);
        var pos=element.data(position_key);
        coordinates(element, pos.x, pos.y);
    });
});

function showTooltip(evt, deviceName) {
    var text = "<p><h5 class='text-success' >" + deviceName + "</h5></p>";
    text += "<p><span class='fa fa-battery-full logo text-success'/> <label>Good</label></p>";
    text += "<p><span class='fa fa-cog logo text-success'/> <label>Run</label></p>";
    text += "<p><span class='fa fa-line-chart logo text-success'/> <label>Normal</label></p>";

    tooltip.innerHTML = text;
    tooltip.style.display = "block";
    tooltip.style.left = evt.pageX + 0 + 'px';
    tooltip.style.top = evt.pageY + 20 + 'px';

}

function linkDevice(ele) {
    var data = $(ele).data(machine_key);
    console.log(data);
    window.location.href = '/dashboard/device/' + data.id;
}

function hideTooltip() {
    tooltip.style.display = "none";
}


function renderByFootprintId() {
    $('.draggable').remove();
    $('.dropzone').removeClass('dropzone');

    const element = $('#carouselExampleFade .carousel-item.active');
    element.find('img').addClass('dropzone');
    const id = element.attr('data-id');

    AjaxUtil.get('/api/iotfootprints/findByIotFootprint/' + id).success(function (data) {

        $.each(data, function (k, v) {
            $('#carouselExampleFade').append(' <div onclick="linkDevice(this)" class="ui-widget-content draggable inZone">' +
                '<img class="img-responsive" src="' + DEFAULT_DEVICE_IMG + '"/>' +
                ' <p class="deviceName" >' + v.iotMachine.iotDevice.deviceName + '</p>' +
                ' <p class="desc">' + v.iotMachine.macName + '</p>' +
                ' </div>');
            var element = $('#carouselExampleFade').find('.draggable').last();
            element.data(machine_key, v.iotMachine);
            element.data(position_key, {
                x: v.posX,
                y: v.posY
            });
            element.draggable({revert: 'invalid'});
            coordinates(element, v.posX, v.posY);
            element.draggable("disable");
        });

        $('.draggable').mousemove(function (evt) {
            var deviceName = $(this).find('.desc').text();
            showTooltip(evt, deviceName);
        }).mouseout(function () {
            hideTooltip();
        });

    });
}

var coordinates = function (element, per_x, per_y) {
    var width = parseFloat(per_x);
    var height = parseFloat(per_y);
    if (width < 0) width = 0;
    if (height < 0) height = 0;
    console.log(width, height)
    element.position({
        of: ".dropzone",
        my: "left top",
        at: "left+" + width + "%" + " top+" + height + "%",
        collision: "none none"

    });
}

$('#carouselExampleFade').on('slid.bs.carousel', function () {
    renderByFootprintId();
})
$('#carouselExampleFade').on('slide.bs.carousel', function () {
    $('.draggable').remove();
});