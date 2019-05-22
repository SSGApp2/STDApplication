function DateUtil() {

}

DateUtil.format = function (date, format) {
    return dateFns.format(
        date,
        format.toUpperCase()
        // new Date(2014, 1, 11),
        // 'MM/DD/YYYY'
    );
}