<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container-fluid">
    <!-- Page-Title -->

    <div class="row">
        <div class="col-sm-12">
            <div class="page-title-box">
                <h4 class="page-title">Footprint Setting</h4>
                <ol class="breadcrumb float-right">
                    <li class="breadcrumb-item"><a href="#">Home</a></li>
                    <li class="breadcrumb-item active">Footprint Setting</li>
                </ol>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>

    <%--Upload File--%>
    <div class="row">
        <div class="col-lg-12 ">
            <div class="card-box">

                <div class="col-lg-6 center">
                    <div class="input-group">
                        <div class="custom-file">
                            <input id="imgInp" type="file" name="pic" accept="image/*"
                                   aria-describedby="inputGroupFileAddon01">
                            <label class="custom-file-label text-left" for="imgInp">Choose file</label>
                        </div>
                        <div class="input-group-prepend">
                            <button id="btnUpload" style="margin-left: 10px" type="button" class="btn btn-info btn-md ">Save</button>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>


    <div class="row">
        <div class="col-lg-9">
            <div class="card-box">
                <div>
                    <div id="box-one" class="draggable">
                        <p> Machine 1 </p>
                    </div>
                    <div id="box-two" class="draggable">
                        <p> Machine 2 </p>
                    </div>

                    <div id="carouselExampleFade" class="carousel slide carousel-fade" data-interval="false"
                         data-ride="carousel">
                        <ol class="carousel-indicators">
                            <li data-target="#carouselExampleFade" data-slide-to="0" class="active"></li>
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
                <div class="center">
                    <button id="btnSave" style="margin-right: 10px" type="button" class="btn btn-primary btn-md ">Save</button>
                    <button id="btnDelete" style="margin-right: 10px" type="button" class="btn btn-danger btn-md ">Delete</button>
                    <button id="btnRevert" type="button" class="btn btn-info btn-md ">Revert</button>
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
            #tbFootprit tr{
                cursor: pointer;
            }
            #tbFootprit tr:hover {
                background-color: rgba(0, 0, 0, 0.9);
            }
        </style>
        <%--Script--%>
        <script src="https://cdn.jsdelivr.net/npm/interactjs/dist/interact.min.js"><!-- required for FF3 and Opera --></script>

        <spring:url value="${urls.getForLookupPath('/resources/scripts/iot/footprint_create.js')}"
                    var="footprint_create"/>
        <script src="${footprint_create}" type="text/javascript"><!-- required for FF3 and Opera --></script>
    </div>
</div>
