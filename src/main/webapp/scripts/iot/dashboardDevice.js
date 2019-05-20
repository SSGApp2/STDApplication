var intervalFetchData;
const _TIME_FETCH_DATA = 1 * 500; //s * ms
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
            var status = MainSensorCurrent[code + "Status"];
            $(this).text(parseFloat(value).toFixed(3));
            $(this).removeClass(function (index, className) {
                return (className.match(/(^|\s)badge-\S+/g) || []).join(' ');
            });
            if(status=="danger"){
                $(this).addClass('badge-danger');
            }else if(status=="warning"){
                $(this).addClass('badge-warning');
            }

        });
        //realTime data
    }, _TIME_FETCH_DATA);

})