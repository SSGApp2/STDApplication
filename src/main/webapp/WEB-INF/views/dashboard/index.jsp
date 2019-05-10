<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container-fluid">
    <!-- Page-Title -->

    <div class="row">
        <div class="col-sm-12">
            <div class="page-title-box">
                <h4 class="page-title">Welcome !</h4>
                <ol class="breadcrumb float-right">
                    <li class="breadcrumb-item"><a href="#">Minton</a></li>
                    <li class="breadcrumb-item active">Dashboard</li>
                </ol>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>

    <%--Upload File--%>
    <div class="row">
        <div class="col-lg-12 ">
            <div class="card-box  bg-dark ">
                <label>Upload Image</label>


            </div>

        </div>
    </div>

    <div class="row">
        <div class="col-lg-9">
            <div class="card-box">
                <div class="holder">

                    <div id="box-one" class="draggable">
                        <p> Machine 1 </p>
                    </div>
                    <div id="box-two" class="draggable">
                        <p> Machine 2 </p>
                    </div>


                    <p class="text-muted m-b-30 font-13">Add <code>.carousel-fade</code> to your carousel to animate
                        slides
                        with a fade transition instead of a slide.</p>

                    <div id="carouselExampleFade" class="carousel slide carousel-fade" data-interval="false"
                         data-ride="carousel">
                        <ol class="carousel-indicators">
                            <li data-target="#carouselExampleFade" data-slide-to="0" class="active"></li>
                            <%--<li data-target="#carouselExampleFade" data-slide-to="1" class=""></li>--%>
                            <%--<li data-target="#carouselExampleFade" data-slide-to="2" class="active"></li>--%>
                        </ol>
                        <div class="carousel-inner " role="listbox">
                            <div class="carousel-item active">
                                <img class="d-block img-fluid dropzone "
                                     src="https://coderthemes.com/minton/dark/assets/images/small/img1.jpg"
                                     alt="First slide">
                                <div class="carousel-caption d-none d-md-block">
                                    <h3 class="text-white">First slide label</h3>
                                    <p>Nulla vitae elit libero, a pharetra augue mollis interdum.</p>
                                </div>
                            </div>
                            <%--<div class="carousel-item">--%>
                            <%--<img class="d-block img-fluid"--%>
                            <%--src="https://coderthemes.com/minton/dark/assets/images/small/img2.jpg"--%>
                            <%--alt="Second slide">--%>
                            <%--<div class="carousel-caption d-none d-md-block">--%>
                            <%--<h3 class="text-white">Second slide label</h3>--%>
                            <%--<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>--%>
                            <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="carousel-item ">--%>
                            <%--<img class="d-block img-fluid"--%>
                            <%--src="https://coderthemes.com/minton/dark/assets/images/small/img2.jpg"--%>
                            <%--alt="Third slide">--%>
                            <%--<div class="carousel-caption d-none d-md-block">--%>
                            <%--<h3 class="text-white">Third slide label</h3>--%>
                            <%--<p>Praesent commodo cursus magna, vel scelerisque nisl consectetur.</p>--%>
                            <%--</div>--%>
                            <%--</div>--%>
                        </div>
                        <a class="carousel-control-prev" href="#carouselExampleFade" role="button" data-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="carousel-control-next" href="#carouselExampleFade" role="button" data-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-lg-3">
            <div class="card-box">
                <h4 class="m-t-0 header-title">Footprint List</h4>
                <table id="tbFootprit" class="table table-borderless">
                    <thead class="thead-dark">
                    </thead>
                    <tbody>
                    <tr class="bg-success text-white">
                        <td>Plant1</td>
                    </tr>
                    <tr>
                        <td>Plant2</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <%--CSS--%>
        <spring:url value="${urls.getForLookupPath('/resources/styles/iot/plotDevice.css')}" var="plotdevice_css"/>
        <link rel="stylesheet" type="text/css" href="${plotdevice_css}"/>
        <style>


        </style>
        <%--Script--%>
        <script src="https://cdn.jsdelivr.net/npm/interactjs/dist/interact.min.js"><!-- required for FF3 and Opera --></script>


        <script type="text/javascript">

            interact('.draggable')
                .draggable({
                    // enable inertial throwing
                    inertia: true,
                    // keep the element within the area of it's parent
                    restrict: {
                        restriction: "parent",
                        endOnly: true,
                        elementRect: {top: 0, left: 0, bottom: 1, right: 1}
                    },
                    // enable autoScroll
                    autoScroll: true,
                    // call this function on every dragmove event
                    onmove: dragMoveListener,
                });

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
                },
                ondropdeactivate: function (event) {
                    // remove active dropzone feedback
                    event.target.classList.remove('drop-active');
                    event.target.classList.remove('drop-target');


                }
            });


            // $(document).on('change', '.btn-file :file', function() {
            //     var input = $(this),
            //         label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
            //     input.trigger('fileselect', [label]);
            // });
            //
            // $('.btn-file :file').on('fileselect', function(event, label) {
            //
            //     var input = $(this).parents('.input-group').find(':text'),
            //         log = label;
            //
            //     if( input.length ) {
            //         input.val(log);
            //     } else {
            //         if( log ) alert(log);
            //     }
            //
            // });
            // function readURL(input) {
            //     if (input.files && input.files[0]) {
            //         var reader = new FileReader();
            //
            //         reader.onload = function (e) {
            //             $('#img-upload').attr('src', e.target.result);
            //         }
            //
            //         reader.readAsDataURL(input.files[0]);
            //     }
            // }
            //
            // $("#imgInp").change(function(){
            //     readURL(this);
            // });
            AjaxUtil.delete('/rest-api/iotDevices/1').complete(function (xhr) {
                console.log(xhr.responseText)
            })
        </script>


    </div>
</div>
