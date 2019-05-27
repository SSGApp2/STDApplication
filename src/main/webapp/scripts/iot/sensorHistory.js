var plotBands = [];
var normalValue;

//initial Date
$('[data-provide="datepicker"]').datepicker().datepicker("setDate", DateUtil.currentDate());

$(function () {
    AjaxUtil.get('/api/iotsensors/findByMachineCodeAndSensorCodeOth', {
        sensorCode: sensorCode
    }, false).success(function (iotsensorRange) {
        plotBands = showRangeConfig(iotsensorRange);
        normalValue = iotsensorRange.normalValue;
    });
    renderData();
});

$('#selectDateType').change(function () {
    const type = $(this).val();
    $('.divTo').hide();
    $('#divfrom').hide();
    $('#divWeek').hide();
    $('#divMonthfrom').hide();

    if (type == "Today") {
        $('#divfrom').show();
        $('#dateFrom').datepicker({dateFormat: 'yy-mm-dd'}); // format to show
        $('#dateFrom').datepicker('setDate', DateUtil.currentDate()); //setCurrentDate
        renderData();
    } else if (type == "Week") {
        $("#dateWeek").val('');
        $('#divWeek').show();
        $("#dateWeek").datepicker("show");
    } else if (type == "Month") {
        $("#dateWeek").val('');
        $('#divMonthfrom').show();
        $("#monthFrom").datepicker("show");
    }
    else if (type == "Custom") {
        $('.divTo').show();
        $('#divfrom').show();
        $("#dateFrom").datepicker("show");
    }
});



$("#dateFrom,#dateTo,#dateWeek").on('changeDate', function (ev) {
    renderData();
});
/*******************************************************************
 *                  DATE WEEK CUSTOM EVENT
 ********************************************************************/
$('#divWeek').click(function () {
    $("#dateWeek").datepicker("show");
});
$('#dateWeek').on('show', function (ev) {
    hilightRowWeek();
    setColorWeek();
});

$('#dateWeek').on('changeMonth', function (ev) {
    setTimeout(function () {
        hilightRowWeek();
    },50);

});
function setColorWeek() {
    $('.datepicker-days .next').click(function () {
        setTimeout(function () {
            hilightRowWeek();
        },50);
    });
    $('.datepicker-days .prev').click(function () {
        setTimeout(function () {
            hilightRowWeek();
        },50);
    });
}
function hilightRowWeek() {
    if($('#dateWeek').val()!=""){
        $('.datepicker-days .active').parent().find('td').addClass('active');
    }
    $(".datepicker-days tbody tr").hover(function () {
        $(this).css("background-color", "#808080")
    }).mouseout(function () {
        $(this).css("background-color", "")
    });
}

$('#dateWeek').on('hide', function (ev) {
    const type = $('#selectDateType').val();
    var dateSelect = $(this).datepicker("getDate");
    var firstDate = DateUtil.startDayOfWeek(dateSelect);
    var lastDate = DateUtil.lastDayOfWeek(dateSelect);
    $("#dateWeek").val(DateUtil.format(firstDate, 'DD/MM/YYYY', "TH") + " - " + DateUtil.format(lastDate, 'DD/MM/YYYY', "TH"));
});
/*******************************************************************
 *                  Month CUSTOM EVENT
 ********************************************************************/
$('#monthFrom').datepicker({
    startView: 1,
    minViewMode: 1,
    format: 'MM yyyy'
}).on('changeMonth', function (ev) {
    $(this).datepicker('hide');
    setTimeout(function () {
        renderData();
    });
});
$('#divMonthfrom').click(function () {
    $("#monthFrom").datepicker("show");
});



function renderData() {
    const type = $('#selectDateType').val();
    var dateFrom, dateTo;
    if (type == "Today") {
        dateFrom = DateUtil.format($("#dateFrom").datepicker("getDate"), 'DD/MM/YYYY');
        dateTo = dateFrom;
    } else if (type == "Week") {
        var dateSelect = $("#dateFrom").datepicker("getDate");
        var firstDate = DateUtil.startDayOfWeek(dateSelect);
        var lastDate = DateUtil.lastDayOfWeek(dateSelect);
        dateFrom = DateUtil.format(firstDate, 'DD/MM/YYYY');
        dateTo = DateUtil.format(lastDate, 'DD/MM/YYYY');
    } else if (type == "Month") {
        var dateSelect = $('#monthFrom').datepicker("getDate");
        var firstDate = DateUtil.startDayOfMonth(dateSelect);
        var lastDate = DateUtil.lastDayOfMonth(dateSelect);
        dateFrom = DateUtil.format(firstDate, 'DD/MM/YYYY');
        dateTo = DateUtil.format(lastDate, 'DD/MM/YYYY');
    } else if (type == "Custom") {
        dateFrom = DateUtil.format($("#dateFrom").datepicker("getDate"), 'DD/MM/YYYY');
        dateTo = DateUtil.format($("#dateTo").datepicker("getDate"), 'DD/MM/YYYY');
    }

    var data = {
        deviceCode: deviceCode,
        sensorCode: sensorCode,
        dateFrom: dateFrom,
        dateTo: dateTo,
    }
    console.log(data);
    AjaxUtil.get('/api/mainsensorviews/findByCriteria', data).success(function (data) {
        data=JSON.parse(data);
        console.log(data.length);
        // Create a timer
        var start = +new Date();
        // Create the chart
        Highcharts.setOptions({lang: {noData: "Data Not Found!"}})

        Highcharts.stockChart('container', {
            chart: {
                events: {
                    load: function () {
                        if (!window.TestController) {
                            // this.setTitle(null, {
                            //     text: 'Built chart in ' + (new Date() - start) + 'ms'
                            // });
                        }
                    }
                },

                zoomType: 'x',
                height: '40%',
                // margin: [0, 0, 0, 0],
            },
            mapNavigation: {
                enableMouseWheelZoom: true
            },
            credits: {
                enabled: false
            },
            rangeSelector: {
                enabled: data.length>0,
                selected: 5,
                buttons: [{
                    type: 'minute',
                    count: 60,
                    text: '1h'
                }, {
                    type: 'day',
                    count: 1,
                    text: '1d'
                }, {
                    type: 'week',
                    count: 1,
                    text: '1w'
                }, {
                    type: 'month',
                    count: 1,
                    text: '1m'
                },
                    //     {
                    //     type: 'year',
                    //     count: 1,
                    //     text: '1y'
                    // },
                    {
                        type: 'all',
                        text: 'All'
                    }],
                labelStyle: {
                    // display: 'none'
                },
                // inputDateFormat: '%Y-%m-%d',
                // inputEditDateFormat: '%Y-%m-%d'
            },
            exporting: {
                enabled: false
            },
            yAxis: {
                plotBands: plotBands,
                plotLines: [{
                    value: normalValue,
                    color: 'black',
                    dashStyle: 'shortdash',
                    width: 2,
                    label: {
                        text: "Normal " + titleHeader + " Value"
                    }
                }],
                title: {
                    // text: 'Temperature (°C)'
                    text: titleHeader + " Value"
                },
            },
            xAxis: {
                type: 'datetime',
            },

            title: {
                text: titleHeader
            },
            time: {
                useUTC: false
            },
            series: [{
                turboThreshold: 20000,
                boostThreshold: 20000,
                name: 'Temperature',
                data: data,
                tooltip: {
                    valueDecimals: 1,
                    valueSuffix: '°C'
                }
            }]
        });
        // hide range selector elements
        // $(".highcharts-input-group, .highcharts-range-selector-buttons").toArray().forEach(function (item, i) {
        //     if (i !== 0) { // omit the main navigator
        //         item.style.display = "none";
        //     }
        // });
    });
}

function showRangeConfig(iotsensorRange) {
    const maxChart = 99999999;
    var plotBands = [];

    var dangerAmt = iotsensorRange.dangerAmt;
    var warningAmt = iotsensorRange.warningAmt;
    if (iotsensorRange.valueType == "0") { //percent
        dangerAmt = iotsensorRange.dangerAmt / 100.0 * iotsensorRange.normalValue;
        warningAmt = iotsensorRange.warningAmt / 100.0 * iotsensorRange.normalValue;
    }
    if (dangerAmt == null) {
        dangerAmt = maxChart;
    }
    if (iotsensorRange.displayType == "N") {
        if (warningAmt) {
            plotBands.push({
                to: iotsensorRange.normalValue - dangerAmt,  //Warning -
                from: iotsensorRange.normalValue - warningAmt,
                color: 'rgba(254,255,78, .5)'
            })
        }
        if (dangerAmt) {
            plotBands.push({
                to: iotsensorRange.normalValue - dangerAmt, //Danger -
                from: 0,
                color: 'rgba(213,68,68, .5)'
            })
        }
    } else if (iotsensorRange.displayType == "P") {
        if (warningAmt) {
            plotBands.push({
                from: iotsensorRange.normalValue + warningAmt, //Warning +
                to: iotsensorRange.normalValue + dangerAmt,
                color: 'rgba(254,255,78, .5)'
            })
        }
        if (dangerAmt) {
            plotBands.push({
                from: iotsensorRange.normalValue + dangerAmt, //Danger +
                to: maxChart,
                color: 'rgba(213,68,68, .5)'
            });
        }
    } else {
        if (warningAmt) {
            plotBands.push({
                from: iotsensorRange.normalValue + warningAmt, //Warning +
                to: iotsensorRange.normalValue + dangerAmt,
                color: 'rgba(254,255,78, .5)'
            })
        }
        if (dangerAmt) {
            plotBands.push({
                from: iotsensorRange.normalValue + dangerAmt, //Danger +
                to: maxChart,
                color: 'rgba(213,68,68, .5)'
            });
        }
        if (warningAmt) {
            plotBands.push({
                to: iotsensorRange.normalValue - dangerAmt,  //Warning -
                from: iotsensorRange.normalValue - warningAmt,
                color: 'rgba(254,255,78, .5)'
            })
        }
        if (dangerAmt) {
            plotBands.push({
                to: iotsensorRange.normalValue - dangerAmt, //Danger -
                from: 0,
                color: 'rgba(213,68,68, .5)'
            })
        }
    }
    return plotBands;
}
