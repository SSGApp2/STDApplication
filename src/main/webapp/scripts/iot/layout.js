
var tooltip = document.getElementById("tooltip");

$(window).on('load', function (e) {
    blueprintSVG = $('#blueprint')[0].getSVGDocument();

    $(blueprintSVG).find("[id^='XDK']").click(function(e){
        // DEVICE_NAME = this.id;
        // window.location.href = '/dashboard/device/' + DEVICE_NAME;
        // window.location.href = '/dashboard/device';

    });

    $(blueprintSVG).find("[id^='XDK']").mousemove(function(evt){
        var obj = this;
        showTooltip(evt, obj.id);
    });



    // $(blueprintSVG).find("[id^='XDK']").mouseout(function(){
    // 	var obj = this;
    // 	hideTooltip();
    // });

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
    window.location.href = '/dashboard/device';
}

function hideTooltip() {
    console.log('Hide');
    tooltip.style.display = "none";
}
