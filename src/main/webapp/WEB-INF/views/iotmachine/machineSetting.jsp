<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<spring:url value="/resources/scripts/iot/machineSetting.js" var="machineSetting"/>

<div class="container-fluid">
    <div class="card-box">
        <h4 class="header-title m-t-0">Machine Setting</h4>
        <button title="Add" style="margin-bottom: 6px" class="btn btn-info" id="btnAddMachine" data-toggle="modal" data-target="#iotmachinemodel">Add</button>
        <div class="table-responsive">
            <table class="table mb-0">
                <thead>
                <tr>
                    <th>Machine Name</th>
                    <th>Device Name</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${iotMachine}" var="iotmachine">
                <tr class="row-machine" data-idmachine="${iotmachine.id}" data-iddevice="${iotmachine.iotDevice.id}">
                    <td><c:out value="${iotmachine.macName}"/></td>
                    <td><c:out value="${iotmachine.iotDevice.deviceName}"/></td>
                    <td><span class="badge badge-warning waves-effect btnEditMachine" data-idmachine="${iotmachine.id}" title="Edit">Edit</span></td>
                    <td><span class="badge badge-danger waves-effect btnDeleteMachine" data-idmachine="${iotmachine.id}" title="Delete">Delete</span></td>
                </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="modal fade" id="iotmachinemodel" role="dialog">
    <div class="modal-dialog">
        <div style="background-color: #36404a;" class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="titlemodalMachine">Add Machine</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="machineName">Machine Name:<span style="color: red">*</span></label>
                    <input type="text" class="form-control" id="machineName" data-idmachine="">
                </div>
                <div class="form-group">
                    <label for="deviceName">Device Name:</label>
                    <select class="input-large form-control" id="deviceName">
                        <option value="0">-- select device --</option>
                        <c:forEach items="${iotDevice}" var="iotdevice">
                            <option value="${iotdevice.id}" data-isused="${iotdevice.isUsed}"><c:out value="${iotdevice.deviceName}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal" id="btnSaveMachine">Save</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal">cancel</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="${machineSetting}"></script>