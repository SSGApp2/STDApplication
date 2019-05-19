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
    socketSensor.setDeviceCode("XDK001");
    intervalFetchData = setInterval(function () {
        MainSensorCurrent = socketSensor.setCurrentData();
        $('.dpreal').each(function () {
            var code = $(this).attr('code');
            var value = '';
            if (MainSensorCurrent[code]) {
                value = MainSensorCurrent[code];

            }
            $(this).text(parseFloat(value).toFixed(3));
        });
        //realTime data
    }, _TIME_FETCH_DATA);

})