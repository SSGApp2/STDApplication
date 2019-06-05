/*
* dateFns v1.3.0 https://date-fns.org/v1.3.0/docs
* */

function DateUtil() {

}

DateUtil.currentDate = function () {
    return new Date();
}
DateUtil.parse=function (date) {
    return dateFns.parse(date);
}

DateUtil.format = function (date, format,locale) {
    if (locale != undefined) {
        if(locale.toUpperCase()=="TH"){
            date=  dateFns.addYears(date,543);
        }
    }
    return dateFns.format(
        date,
        format.toUpperCase()
        // new Date(2014, 1, 11),
        // 'MM/DD/YYYY'
    );
}


//Day
DateUtil.startDayOfWeek = function (date) {
    return dateFns.startOfWeek(date)
}
DateUtil.lastDayOfWeek = function (date) {
    return dateFns.lastDayOfWeek(date)
}

//Month
DateUtil.startDayOfMonth = function (date) {
    return dateFns.startOfMonth(date)
}
DateUtil.lastDayOfMonth= function (date) {
    return dateFns.lastDayOfMonth(date)
}
