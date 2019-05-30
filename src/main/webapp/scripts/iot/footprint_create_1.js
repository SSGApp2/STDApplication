
const tooltip = document.getElementById("tooltip");
/**************************************************************************
 *                     Carousel
 **************************************************************************/
$('#myCarousel').on('slid.bs.carousel', function () {
    console.log('after');
});
$('#myCarousel').on('slide.bs.carousel', function (e) {
    console.log('before');
    $(e.relatedTarget).find('img').each(function() {
        var $this = $(this);
        var src = $this.data('lazy-load-src');
        if (typeof src !== "undefined" && src != "") {
            $this.attr('src', src)
            $this.data('lazy-load-src', '');  // clean up
        }
    });
});

function carouselNext() {
    var element = $('#tbFootprint').find('.active').parent().next();
    if(element.length==0){
        element= $('#tbFootprint').find('tr').first();
    }
    var id = element.find('td').attr('data-id');
    footprint.renderByFootprintId(id);
}

function carouselPrev() {
    var element = $('#tbFootprint').find('.active').parent().prev();
    if(element.length==0){
        element= $('#tbFootprint').find('tr').last();
    }
    var id = element.find('td').attr('data-id');
    footprint.renderByFootprintId(id);
}


function hideTooltip() {
    tooltip.style.display = "none";
}

function showTooltip(evt, deviceName) {
    var text = "<p><h5 class='text-success' >" + deviceName + "</h5></p>";
    // text += "<p><span class='fa fa-battery-full logo text-success'/> <label>Good</label></p>";
    // text += "<p><span class='fa fa-cog logo text-success'/> <label>Run</label></p>";
    // text += "<p><span class='fa fa-line-chart logo text-success'/> <label>Normal</label></p>";
    tooltip.innerHTML = text;
    tooltip.style.display = "block";
    tooltip.style.left = evt.pageX + 0 + 'px';
    tooltip.style.top = evt.pageY + 20 + 'px';

}