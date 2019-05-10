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

    <div class="row">
        <div class="col-lg-9">
            <div class="card-box">
                <h4 class="header-title m-t-0">Latest Projects</h4>
                <p class="text-muted m-b-25 font-13">
                    Your awesome text goes here.
                </p>

                <div class="holder">

                    <div id="box-one" class="draggable">
                        <p> I am the first Box </p>
                    </div>
                    <div id="box-two" class="draggable">
                        <p> I am the second Box </p>
                    </div>
                    <br>

                    <img src="/resources/images/default-plan.png" id="dropzone" class="dropzone"/>
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
    </div>

    <%--CSS--%>
    <spring:url value="${urls.getForLookupPath('/resources/styles/iot/plotDevice.css')}" var="plotdevice_css"/>
    <link rel="stylesheet" type="text/css" href="${plotdevice_css}"/>
    <style>
        #tbFootprit tr {
            cursor: pointer;
        }
        #tbFootprit tr:hover {
            background-color: #0E1214;
        }
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
                dropzoneElement.classList.add('drop-target');
            },
            ondragleave: function (event) {
                // remove the drop feedback style
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


    </script>


</div>
