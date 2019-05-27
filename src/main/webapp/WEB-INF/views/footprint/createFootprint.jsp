<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"/>
<style>
    #tbFootprit tr {
        cursor: pointer;
    }

    #tbFootprit tr:hover {
        background-color: rgba(0, 0, 0, 0.9);
    }

    form {
        display: inherit;
        width: 80%;
    }

    td.active{
        background-color: black;
    }
</style>
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

                <div class="col-lg-8 center">
                    <div class="input-group">
                        <form action="/api/common/upload" method="post" enctype="multipart/form-data">
                            <div class="custom-file">
                                <input id="imgInp" type="file" name="file" accept="image/*"
                                       aria-describedby="inputGroupFileAddon01">
                                <label class="custom-file-label text-left" for="imgInp">Choose file</label>

                            </div>
                            <div class="input-group-prepend">
                                <button id="btnUpload" style="margin-left: 10px" type="submit"
                                        class="btn btn-info btn-md ">Save
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
        </div>
    </div>


    <div class="row">
        <div class="col-lg-9">
            <div class="card-box">
                <div id="divDraggable">
                    <jsp:text/>
                </div>
                <div id="carouselExampleFade" class="carousel slide carousel-fade" data-interval="false"
                     data-ride="carousel">
                    <ol class="carousel-indicators">
                        <li data-target="#carouselExampleFade" data-slide-to="0" class="active"></li>
                    </ol>
                    <div id="carousel-list" class="carousel-inner" role="listbox">


                        <div class="carousel-item active">
                            <img  class="dropzone img-responsive"
                                 src="/resources/images/floorplan/exampleFloorplan.jpg"
                                 alt="First slide">
                        </div>
                    </div>
                    <a class="carousel-control-prev" href="#carouselExampleFade"  role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselExampleFade"   role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>

                <div class="center" style="margin-top: 10px">
                    <button id="btnSave" style="margin-right: 10px" type="button" class="btn btn-primary btn-md ">Save
                    </button>
                    <button id="btnDelete" style="margin-right: 10px" type="button" class="btn btn-danger btn-md ">
                        Delete
                    </button>
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
                    <tbody id="tbFootprint"></tbody>
                </table>
            </div>
        </div>

        <%--CSS--%>
        <spring:url value="${urls.getForLookupPath('/resources/styles/iot/plotDevice.css')}" var="plotdevice_css"/>
        <link rel="stylesheet" type="text/css" href="${plotdevice_css}"/>

        <spring:url value="${urls.getForLookupPath('/resources/scripts/util/CommonMessage.js')}" var="common_message"/>
        <script src="${common_message}" type="text/javascript"><!-- required for FF3 and Opera --></script>
        <%--Script--%>
        <%--<script src="https://cdn.jsdelivr.net/npm/interactjs/dist/interact.min.js"><!-- required for FF3 and Opera --></script>--%>
        <%--<script src="https://c4d6f7d727e094887e93-4ea74b676357550bd514a6a5b344c625.ssl.cf2.rackcdn.com/interact-1.1.1.min.js"><!-- required for FF3 and Opera --></script>--%>

        <%--<script src="http://malsup.github.io/jquery.form.js"><!-- required for FF3 and Opera --></script>--%>
        <script src="https://code.jquery.com/jquery-1.12.4.js"><!-- required for FF3 and Opera --></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"><!-- required for FF3 and Opera --></script>

        <spring:url value="${urls.getForLookupPath('/resources/scripts/iot/footprint_create.js')}" var="footprint_create"/>
        <script src="${footprint_create}" type="text/javascript"><!-- required for FF3 and Opera --></script>
    </div>
</div>
