var intervalFetchData;
const _TIME_FETCH_DATA = 1 * 700; //s * ms
const _MAX_DATA_TIME = 1 * 60;// min * sec
const _TICK_INTERVAL = 0.1 * 60 * 1000 // min * sec * ms
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
    socketSensor.setDeviceCode(deviceCode);

    intervalFetchData = setInterval(function () {
        MainSensorCurrent = socketSensor.setCurrentData();
        var current_datetime = (new Date(MainSensorCurrent['dateTime']));
        var formatted_date = current_datetime.getDate() + "-" + (current_datetime.getMonth() + 1) + "-" + current_datetime.getFullYear() + " " + current_datetime.getHours() + ":" + current_datetime.getMinutes() + ":" + current_datetime.getSeconds();
        $('#txtStatus').text('Status : ' + SOCKET_DISPLAY_STATUS);
        $('#txtLastUpdate').text('Update : ' + formatted_date);
    }, _TIME_FETCH_DATA);


    $('.chartOne').each(function (ele) {
        var id = $(this).attr('id');
        var sensorCode = $(this).attr('code');
        initialHighChart(id, sensorCode);
    });
})

var IntervalStatus = {};

function setAlertTime(sensorCode, status) {
    // var $div2blink = $(".divtoBlink"); // Save reference, only look this item up once, then save
    var $div2blink = $('.chartOne[code="' + sensorCode + '"]').closest(".divtoBlink");
    $div2blink.removeClass(function (index, className) {
        return (className.match(/(^|\s)badge-\S+/g) || []).join(' ');
    });
    var toggleClass;
    if (status == "danger") {
        toggleClass = "badge-danger";
    } else if (status == "warning") {
        toggleClass = "badge-warning";
    }
    $div2blink.addClass(toggleClass);
    // IntervalStatus[sensorCode] = setInterval(function () {
    //     $div2blink.toggleClass(toggleClass);
    // }, 1000);


}

var chartData = [];

function initialHighChart(element, sensorCode) {
    Highcharts.chart(element, {
        chart: {
            type: 'spline',
            animation: Highcharts.svg, // don't animate in old IE
            marginRight: 10,
            events: {
                load: function () {
                    // set up the updating of the chart each second
                    var series = this.series[0];

                    setInterval(function () {
                        //realTime data
                        var x = (new Date(MainSensorCurrent['dateTime'])).getTime(), // current time
                            y = parseFloat(MainSensorCurrent[sensorCode]);

                        var data = [x, y];

                        var len = series.data.length - 1;
                        var lastData = [null, null];
                        if (series.data.length > 0) {
                            lastData = [series.data[len].x, series.data[len].y];
                        }
                        if (data[0] == lastData[0] && data[1] == lastData[1]) {
                        } else {
                            var shift = series.data.length > _MAX_DATA_TIME;
                            series.addPoint(data, true, shift);

                            var status = MainSensorCurrent[sensorCode + "Status"];
                            setAlertTime(sensorCode, status);
                        }


                    }, _TIME_FETCH_DATA);
                }
            }
        },
        credits: {
            enabled: false
        },
        time: {
            useUTC: false
        },
        plotOptions: {
            series: {
                dataLabels: {
                    enabled: true,
                    formatter: function () {
                        var isLast = false;
                        if (this.point.x === this.series.data[this.series.data.length - 1].x && this.point.y === this.series.data[this.series.data.length - 1].y) isLast = true;
                        return isLast ? parseFloat(this.y).toFixed(3) : '';
                    }
                }
            }
        },
        title: {
            text: null
        },
        xAxis: {
            type: 'datetime',
            showLastLabel: true,
            endOnTick: true
        },
        yAxis: {
            title: {
                text: 'Value'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }],
            showLastLabel: true,
        },
        tooltip: {
            headerFormat: '<b>{series.name}</b><br/>',
            pointFormat: '{point.x:%Y-%m-%d %H:%M:%S}<br/>{point.y:.3f}'
        },
        legend: {
            enabled: false
        },
        exporting: {
            enabled: false
        },
        series: [{data: []}]
    });
}