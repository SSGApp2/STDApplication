/***********************************************************************
 *             CRUD Button Event
 *
 ***********************************************************************/
$('#btnNew').click(function () {
    $('#tbFootprint').find('.active').removeClass('active');
    $('#txtFpName').val('');
    $carousel.find('.carousel-item').remove();
    $carousel.append(' <div class="carousel-item active"> ' + '<img  class="dropzone img-responsive"  src="' + DEFAULT_FLOOR_IMG + '" >' + ' </div>');
    updateDropZone();
    footprint.renderEmptyMachine();
    IOT_FOOTPRINT = {};
    iotFootprint = null;
    $fileNameField.text('');
    $("#imgInp").val('');
    rightEnable(3);

});

$(function () {
    $('#btnSave').click(function () {
        var name = $('#txtFpName').val().trim();
        if (name == "") {
            $('#save_form').submit();
            return false;
        }
        var machine = [];
        $('.draggable.inZone').each(function () {
            const data = $(this).data(machine_key);
            const position = $(this).data(position_key);
            var dataMachine = {
                machine: data.id,
                posX: position.x,
                posY: position.y,
            }
            machine.push(dataMachine);
        });
        if (machine.length == 0) {
            MessageUtil.alertWarning('Please Select Device');
            return false;
        }
        var data = {
            name: name,
            machine: machine,
            footPrint: iotFootprint
            // ouCode:session.ouCode
        }
        var formData = new FormData();
        if ($fileNameField.text() == "") {
            formData.append("file", null);
        } else {
            formData.append("file", imgInp.files[0]);
        }
        formData.append("json", JSON.stringify(data));
        AjaxUtil.postWithFile('/api/iotfootprints', formData).complete(function (xhr) {
            if (xhr.status == 200) {
                var data = xhr.responseJSON;
                footprint.renderFootprintList(data.id);
                MessageUtil.alert('Save successfully.');
            }
        });
    });

    $('#btnRevert').click(function () {
        revertAllEmpty();
        $('.dgOriginal').each(function () {
            $(this).find('button').hide();
            $(this).addClass('inZone');
            var element = $(this);
            var posDefault = element.data(position_key + "_DEFAULT");//get def pos
            element.data(position_key, posDefault);
            coordinates(element, posDefault.x, posDefault.y);
        });
    });

    $('#btnDelete').click(function () {
        MessageUtil.confirm('Delete ' + IOT_FOOTPRINT.name, function () {
            AjaxUtil.delete('/rest-api/iotFootprints/' + IOT_FOOTPRINT.id).success(function () {
                $carousel.find('.carousel-item').remove();
                footprint.renderFootprintList();
            });
        })

    });
})

$("#save_form").submit(function (e) {
    e.preventDefault();
})