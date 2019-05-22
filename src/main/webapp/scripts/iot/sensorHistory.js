var deviceCode = "XDK001";
var sensorCode = "temp";

//
$('[data-provide="datepicker"]').datepicker().datepicker("setDate", new Date());
$("#dateFrom,#dateTo").on('changeDate', function (ev) {
    getData();
});


function getData() {
    var data = {
        deviceCode: deviceCode,
        sensorCode: sensorCode,
        dateFrom: DateUtil.format($("#dateFrom").datepicker("getDate"), 'DD/MM/YYYY'),
        dateTo: DateUtil.format($("#dateTo").datepicker("getDate"), 'DD/MM/YYYY'),
    }
    console.log(data);
    AjaxUtil.get('/api/mainsensorviews/findByCriteria', data).success(function (data) {
        console.log(data.length);
        // Create a timer
        var start = +new Date();
        // Create the chart
        Highcharts.stockChart('container', {
            chart: {
                events: {
                    load: function () {
                        if (!window.TestController) {
                            this.setTitle(null, {
                                text: 'Built chart in ' + (new Date() - start) + 'ms'
                            });
                        }
                    }
                },
                zoomType: 'x',
                height: '40%'
            }, credits: {
                enabled: false
            },
            rangeSelector: {
                buttons: [ {
                    count: 15,
                    type: 'minute',
                    text: '15 min'
                }, {
                    count: 30,
                    type: 'minute',
                    text: '30 min'
                }, {
                    count: 60,
                    type: 'minute',
                    text: '1 hr'
                }, {
                    count: 360,
                    type: 'minute',
                    text: '6 hr'
                }, {
                    count: 1440,
                    type: 'minute',
                    text: '1 Day'
                }
                , {
                    type: 'all',
                    text: 'All'
                }],
                selected: 4,
            },

            yAxis: {
                type: 'datetime',
                title: {
                    text: 'Temperature (°C)'
                },

            },

            xAxis: {
                // events: {
                //     afterSetExtremes: afterSetExtremes
                // },
                minRange: 3600 * 100 // one hour
            },

            title: {
                text: 'Hourly temperatures in Vik i Sogn, Norway, 2009-2017'
            },

            subtitle: {
                text: 'Built chart in ...' // dummy text to reserve space for dynamic subtitle
            },
            time: {
                useUTC: false
            },
            series: [{
                turboThreshold: 20000,
                name: 'Temperature',
                data: data,
                tooltip: {
                    valueDecimals: 1,
                    valueSuffix: '°C'
                }
            }]

        });
    });
}

// $.getJSON('https://cdn.jsdelivr.net/gh/highcharts/highcharts@v7.0.0/samples/data/large-dataset.json', function (data) {
