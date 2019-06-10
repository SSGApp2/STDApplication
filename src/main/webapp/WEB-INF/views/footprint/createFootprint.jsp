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

    td.active {
        background-color: black;
    }

    #tooltip {
        background: #1b1e21;
        border: 1px solid black;
        border-radius: 5px;
        padding: 5px;
        color: white;
        z-index: 1000;
    }

    .text-success {
        color: white !important;
    }

    .validatr-message {
        z-index: 1000
    }
</style>
<div class="container-fluid">


    <!-- Page-Title -->

    <%--<div class="row">--%>
    <%--<div class="col-sm-12">--%>
    <%--<div class="page-title-box">--%>
    <%--<h4 class="page-title">Footprint Setting</h4>--%>
    <%--<ol class="breadcrumb float-right">--%>
    <%--<li class="breadcrumb-item"><a href="#">Home</a></li>--%>
    <%--<li class="breadcrumb-item active">Footprint Setting</li>--%>
    <%--</ol>--%>
    <%--<div class="clearfix"></div>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</div>--%>

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
                                <label class="custom-file-label text-left" for="imgInp" style="overflow: hidden">Choose file</label>
                            </div>
                        </form>
                        <button id="btnNew" style="margin-left: 10px" class="btn btn-success btn-md ">New</button>
                    </div>
                </div>

            </div>
        </div>
    </div>


    <div class="row">
        <div class="col-lg-9">
            <div class="card-box">


                <div class="container">
                    <h3>Device</h3>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div id="divDraggable">
                                <jsp:text/>
                            </div>
                        </div>
                    </div>
                </div>


                <form id="save_form" style="width: 100%" action="./">
                    <div style="display:flex; flex-direction: row; justify-content: center; align-items: center">
                        <label for="txtFpName" class="col-sm-2 col-form-label">Foorprint Name<span
                                style="color: red">*</span></label>
                        <div class="col-sm-4">
                            <input id="txtFpName" class="form-control" type="text" required/>
                        </div>
                    </div>
                </form>


                <div id="myCarousel" class="carousel slide" data-interval="false">
                    <div id="carousel-list" class="carousel-inner" role="listbox">
                        <%--<div class="carousel-item" >--%>
                        <%--<img class="img-responsive" src="/resources/images/floorplan/exampleFloorplan.jpg">--%>
                        <%--</div>--%>
                        <jsp:text/>
                    </div>
                    <a class="carousel-control-prev" href="#" onclick="carouselPrev()" role="button"
                       data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#" onclick="carouselNext()" role="button"
                       data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
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

        <div id="tooltip" display="none" style="position: absolute; display: none;">
            <jsp:text/>
        </div>

        <%--Modal--%>
        <div class="modal fade" id="modalNewItem" role="dialog">
            <div class="modal-dialog">
                <div style="background-color: #1d7fc1;" class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Create Footprint</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body">
                        <jsp:text/>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary" id="btnSaveMachine">Save</button>
                        <button type="button" class="btn btn-danger btncancel" data-dismiss="modal">cancel</button>
                    </div>

                </div>
            </div>
        </div>


        <%--CSS--%>
        <spring:url value="${urls.getForLookupPath('/resources/styles/iot/plotDevice.css')}" var="plotdevice_css"/>
        <link rel="stylesheet" type="text/css" href="${plotdevice_css}"/>



        <%--<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"><!-- required for FF3 and Opera --></script>--%>
        <spring:url value="/resources/scripts/util/validatr.min.js" var="utilformdatachangevalidation"/>
        <spring:url value="/resources/scripts/util/jquery.ui.touch-punch.min.js" var="touch_punch_js"/>
        <spring:url value="${urls.getForLookupPath('/resources/scripts/util/CommonMessage.js')}" var="common_message"/>
        <script src="${common_message}" type="text/javascript"><!-- required for FF3 and Opera --></script>
        <script src="${utilformdatachangevalidation}" type="text/javascript"><!-- required for FF3 and Opera --></script>


        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"><!-- required for FF3 and Opera --></script>
        <%--<script src="http://code.jquery.com/ui/1.8.21/jquery-ui.min.js"><!-- required for FF3 and Opera --></script>--%>
        <script src="${touch_punch_js}" type="text/javascript"><!-- required for FF3 and Opera --></script>

        <spring:url value="${urls.getForLookupPath('/resources/scripts/iot/footprint_create.js')}"
                    var="footprint_create"/>
        <spring:url value="${urls.getForLookupPath('/resources/scripts/iot/footprint_create_1.js')}"
                    var="footprint_create_1"/>
        <spring:url value="${urls.getForLookupPath('/resources/scripts/iot/footprint_create_2.js')}"
                    var="footprint_create_2"/>
        <spring:url value="${urls.getForLookupPath('/resources/scripts/iot/footprint_create_3.js')}"
                    var="footprint_create_3"/>
        <script src="${footprint_create}" type="text/javascript"><!-- required for FF3 and Opera --></script>
        <script src="${footprint_create_1}" type="text/javascript"><!-- required for FF3 and Opera --></script>
        <script src="${footprint_create_2}" type="text/javascript"><!-- required for FF3 and Opera --></script>
        <script src="${footprint_create_3}" type="text/javascript"><!-- required for FF3 and Opera --></script>
    </div>


</div>
