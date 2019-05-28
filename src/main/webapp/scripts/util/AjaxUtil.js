function AjaxUtil() {
}

var pattern = {
    async: true,
    cache: false,
    data: '',
    contentType: "application/json; charset=utf-8",
    headers: {Accept: "application/json"},
    error: function (xhr) {
        alert('error!');
        console.log(xhr);
    }
    //,
    // beforeSend: function () {
    //     $('.dv-background').show();
    // },
    // complete: function() { $('.dv-background').hide(); }
};

/**
 * fire ajax request to server type post
 *
 * @param strUrl        (string)    url connect to server
 * @param jsonData      (json)      data send to server
 * @param bAsync        (boolean)   asynchronous type
 * @param eBackdrop     (element)   put backdrop (loader) over
 */
$(function () {
    $(document).on({
        ajaxStart: function () {
            $('.dv-background').show();
        },
        ajaxStop: function () {
            $('.dv-background').hide();
        }
    });
});



AjaxUtil.post = function (strUrl, jsonData, bAsync, eBackdrop) {

    var options = {
        type: "POST",
        url: session['context'] + strUrl,
        data: jsonData,
        async: bAsync
    };

    var settings = $.extend({}, pattern, options);
    return $.ajax(settings);
};

AjaxUtil.postWithFile = function (strUrl, jsonData, bAsync, eBackdrop) {

    var options = {
        type: "POST",
        url: session['context'] + strUrl,
        data: jsonData,
        processData: false,
        contentType: false,
        dataType: "json",
        async: bAsync,
        error: function (xhr) {
            console.log(xhr);
        }
    };
    var settings = $.extend({}, pattern, options);
    return $.ajax(settings);
};

AjaxUtil.patch = function (strUrl, jsonData, bAsync, eBackdrop) {

    var options = {
        type: "PATCH",
        url: session['context'] + strUrl,
        data: jsonData,
        async: bAsync
    };

    var settings = $.extend({}, pattern, options);
    return $.ajax(settings);
};

AjaxUtil.get = function (strUrl, jsonData, bAsync) {
    var options = {
        type: 'GET',
        url: session['context'] + strUrl,
        data: jsonData,
        async: bAsync
    };
    var settings = $.extend({}, pattern, options);
    return $.ajax(settings);
};

AjaxUtil.delete = function (strUrl, jsonData, bAsync, eBackdrop) {

    var options = {
        type: "DELETE",
        url: session['context'] + strUrl,
        data: jsonData,
        async: bAsync
    };

    var settings = $.extend({}, pattern, options);
    return $.ajax(settings);
};

AjaxUtil.list = function (listAjaxSaveData) {
    return $.when.apply(null, listAjaxSaveData);
}