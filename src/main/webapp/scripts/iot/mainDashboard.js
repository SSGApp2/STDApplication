var intervalFetchData;
const _TIME_FETCH_DATA = 1 * 500; //s * ms
const _MAX_DATA_TIME = 1 * 60;// min * sec
const _TICK_INTERVAL = 0.5 * 60 * 1000 // min * sec * ms
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
        $('#txtStatus').text('Status : ' + SOCKET_DISPLAY_STATUS );
        $('#txtLastUpdate').text('Update : ' + formatted_date );
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
                        series.addPoint(data, true, true);

                        var status = MainSensorCurrent[sensorCode + "Status"];
                        // clearInterval(IntervalStatus[sensorCode]);
                        // if (status) {
                        setAlertTime(sensorCode, status);
                        // }
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
                marker: {
                    enabled: false //remove dot in line
                }
            }
        },
        title: {
            text: null
        },
        xAxis: {
            type: "datetime",
            tickInterval: _TICK_INTERVAL,
            showLastLabel: true
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

        },
        tooltip: {
            headerFormat: '<b>{series.name}</b><br/>',
            pointFormat: '{point.x:%Y-%m-%d %H:%M:%S}<br/>{point.y:.2f}'
        },
        legend: {
            enabled: false
        },
        exporting: {
            enabled: false
        },
        series: [{
            name: 'Random data',
            data: (function () {
                //initial data
                // generate an array of random data
                var data = [],
                    time = (new Date()).getTime(),
                    i;

                for (i = -(_MAX_DATA_TIME); i <= 0; i += 1) {
                    data.push({
                        x: time + i * 1000,
                        y: Math.random()
                    });
                }

                return data;
            }())
        }]
    });
}