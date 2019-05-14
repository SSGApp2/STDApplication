var intervalFetchData;
const _TIME_FETCH_DATA = 1 * 1000; //s * ms
var MainSensorCurrent = {};

$(document).on({
    ajaxStart: function () {
        $('.dv-background').hide();
    },
    ajaxStop: function () {
        $('.dv-background').hide();
    }
});


$(function () {
    intervalFetchData = setInterval(function () {
        AjaxUtil.get('/mainsensors/deviceCode', {deviceCode: 'XDK002'}).success(function (json) {
            MainSensorCurrent = json;
            $('.dpreal').each(function () {
                var code = $(this).attr('code');
                var value='';
                if (MainSensorCurrent[code]) {
                    value = MainSensorCurrent[code];

                }
                $(this).text(value);
            });

        });
        //realTime data
    }, _TIME_FETCH_DATA);

})
;