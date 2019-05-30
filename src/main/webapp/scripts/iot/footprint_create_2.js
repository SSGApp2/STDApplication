/***********************************************************************
 *               UPLOAD IMAGE
 *
 ***********************************************************************/

//Upload Image
$("#imgInp").change(function () {
    readURL(this);
});

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            footprint.renderEmptyMachine();
            iotFootprint=null;
            IOT_FOOTPRINT = {};
            //clear old Data
            $('#txtFpName').val('');
            $('.dropzone').removeClass('dropzone');
            $('.carousel-item.active').find('img').addClass('dropzone');
            $('#tbFootprint td').removeClass('active');
            // $('#blah').attr('src', e.target.result);
            $('.carousel-item').removeClass('active');
            $('.carousel-inner').append(' <div class="carousel-item active"> <img class="dropzone" src="' + e.target.result + '" alt=""> </div>');
            updateDropZone();
            rightEnable(3);
        }
        reader.readAsDataURL(input.files[0]);
        const fileName = input.files[0].name
        $fileNameField.text(fileName);
    }
}
