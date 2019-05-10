$('#btnAddMachine').click(function () {
    $('#iotmachinemodel').modal('show');
    $('#titlemodalMachine').text('Add Machine');
    $('#machineName').val("");
});

$('#btnSaveMachine').click(function () {
    var machineName = $('#machineName').val();
});

$('.btnEditMachine').click(function () {
   var idMachine = $(this).data("idmachine");
   var machineName = $('.row-machine[data-idmachine='+idMachine+']').find('td:eq(1)').text();
    $('#iotmachinemodel').modal('show');
    $('#titlemodalMachine').text('Edit Machine');
    $('#machineName').val(machineName);
});

$('.btnDeleteMachine').click(function () {
    var idMachine = $(this).data("idmachine");
    var machineName = $('.row-machine[data-idmachine='+idMachine+']').find('td:eq(1)').text();
    swal({
            title: "Are you sure?",
            text: "Delete "+machineName+"!",
            type: "warning",
            showCancelButton: true,
            confirmButtonClass: "btn-danger",
            confirmButtonText: "Yes, delete it!",
            closeOnConfirm: false
        },
        function(){
            swal("Deleted!", machineName+"", "success");
        });
    // AjaxUtil.delete('/rest-api/iotMachines/1').complete(function (xhr) {
    //     console.log(xhr.responseText)
    // });
    $.ajax({
        url: "/rest-api/iotMachines",
        type: 'GET',
        success: function(result) {
            console.log(result);
        }
    });
});


