$(function () {
    //upload file
    var bar = $('.bar');
    var percent = $('.percent');
    var status = $('#status');

    // LoadData();

    $('form').ajaxForm({
        beforeSend: function () {
            status.empty();
            var percentVal = '0%';
            bar.width(percentVal);
            percent.html(percentVal);
        },
        uploadProgress: function (event, position, total, percentComplete) {
            var percentVal = percentComplete + '%';
            bar.width(percentVal);
            percent.html(percentVal);
        },
        complete: function (xhr) {
            status.html(xhr.responseText);
        }
    });

    var startPos = {x: 0, y: 0};
     //ready
    interact('.draggable')
        .draggable({
            // enable inertial throwing
            inertia: true,
            // keep the element within the area of it's parent
            restrict: {
                // restriction: "parent",
                endOnly: true,
                elementRect: {top: 0, left: 0, bottom: 1, right: 1}
            },
            revert:"invalid",
            // enable autoScroll
            autoScroll: true,
            // call this function on every dragmove event
            onmove: dragMoveListener,

        });
//
//
 // enable draggables to be dropped into this
    interact('.dropzone').dropzone({
        // Require a 50% element overlap for a drop to be possible
        overlap: 0.50,

        // listen for drop related events:

        ondropactivate: function (event) {
            // add active dropzone feedback
            event.target.classList.add('drop-active');
        },
        ondragenter: function (event) {
            var draggableElement = event.relatedTarget,
                dropzoneElement = event.target;

            // feedback the possibility of a drop
            event.relatedTarget.textContent = 'Dropped target';
            dropzoneElement.classList.add('drop-target');
        },
        ondragleave: function (event) {
            // remove the drop feedback style
            event.relatedTarget.textContent = 'Dropped out target';
            event.target.classList.remove('drop-target');


        },
        ondrop: function (event) {
            event.relatedTarget.textContent = 'Dropped';
            var dropzonePostion = $(event.target).position(),
                elementPosition = $(event.relatedTarget).position(),
                x = elementPosition.left - dropzonePostion.left,
                y = elementPosition.top - dropzonePostion.top;

            console.log('x',x,'y',y);
        },
        ondropdeactivate: function (event) {
            // remove active dropzone feedback
            event.target.classList.remove('drop-active');
            event.target.classList.remove('drop-target');
        }
    });


})



function dragMoveListener(event) {
    var target = event.target,
        // keep the dragged position in the data-x/data-y attributes
        x = (parseFloat(target.getAttribute('data-x')) || 0) + event.dx,
        y = (parseFloat(target.getAttribute('data-y')) || 0) + event.dy;

    // translate the element
    target.style.webkitTransform =
        target.style.transform =
            'translate(' + x + 'px, ' + y + 'px)';

    // update the posiion attributes
    target.setAttribute('data-x', x);
    target.setAttribute('data-y', y);
}


function LoadData() {
    $('.draggable').remove();
    AjaxUtil.get('/api/iotmachines/findByNotInFootprintOuth').success(function (data) {
        console.log(data);
        $.each(data, function (k, v) {
            $('#carouselExampleFade').before('<img class="draggable">' +
                '<p>'+v.macName+'</p>' +
                '</img>');
        });

    });

    const $tbFootprint = $('#tbFootprint').empty();
    AjaxUtil.get('/api/iotfootprints/findByOuth').success(function (data) {
        $.each(data, function (k, v) {
            $tbFootprint.append('<tr> ' +
                '<td>v.name</td>' +
                '</tr>');
        });
        //add Active
        $('#tbFootprint tr:eq(0)').addClass('bg-success text-white');
    });
}



//Upload Image
$("#imgInp").change(function () {
    readURL(this);
});

function readURL(input) {

    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            // $('#blah').attr('src', e.target.result);
            $('.carousel-item').removeClass('active');
            var length = $('.carousel-indicators li').length;
            $('.carousel-indicators').append('<li data-target="#carouselExampleFade" data-slide-to="' + length + '"></li>');
            $('.carousel-inner').append(' <div class="carousel-item active"> <img  src="' + e.target.result + '" alt=""> </div>');
        }

        reader.readAsDataURL(input.files[0]);
    }
}

