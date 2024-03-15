<!-- Tab content -->
<div id="dashboard" class="tabcontent">

    <style>
    table,
    tr,
    td {
        border: none;
    }
    </style>

    <div class="container-fluid bg-light ">
        <div class="row text-center title">
            <h4 style="text-align: center">Report Dashboard</h4>
        </div>

        
        
        
        <div style="display: flex; justify-content: space-between; flex-wrap: wrap;">
            <div style="width: 32%; margin-bottom: 20px;">
              <figure class="highcharts-figure">
                <div id="containeras"></div>
                <p class="highcharts-description" style="align-items: center">
                  <strong></strong>
                </p>
              </figure>
            </div>
            
            <div style="width: 32%; margin-bottom: 20px;">
              <figure class="highcharts-figure">
                <div id="containercol"></div>
                <p class="highcharts-description" style="align-items: center">
                  <strong></strong>
                </p>
              </figure>
            </div>

            <div style="width: 32%; margin-bottom: 20px;">
              <figure class="highcharts-figure">
                <div id="containerrr"></div>
                <p class="highcharts-description" style="align-items: center">
                  <strong></strong>
                </p>
              </figure>
            </div>

            <div style="width: 67%; margin-bottom: 20px;">
              <figure class="highcharts-figure">
                <div id="containercom"></div>
                <p class="highcharts-description" style="align-items: center">
                  <strong></strong>
                </p>
              </figure>
            </div>

            <div style="width: 32%; margin-bottom: 20px;">
              <figure class="highcharts-figure">
                <div id="containerpi"></div>
                <p class="highcharts-description" style="align-items: center">
                  <strong></strong>
                </p>
              </figure>
            </div>

            <div style="width: 30%; margin-bottom: 20px;">
              <figure class="highcharts-figure">
                <div id="containerpbs"></div>
                <p class="highcharts-description" style="align-items: center">
                  <strong></strong>
                </p>
              </figure>
            </div>
          </div>

          
          
          
        
    </div>
</div>
<script>
    jqq = jQuery;
    getDashboardData();

    function getDashboardData() {

        jqq.ajax({
            data: {
                'id': 1
            },
            type: 'GET',
            dataType: "json",
            url: "${ ui.actionLink("nmrsreports", "reportslist", "getPatientsList") }",
            cache: false
        }).done(function (json) {
            var rest = JSON.parse(json);
            console.log(rest)
            jqq('#totalPatients').html(rest.totalPatients)
            jqq('#totalARTPatients').html(rest.totalPatientsOnART)
            jqq('#totalHTSPatients').html(rest.totalHtsPatients)
            jqq('#totalRecencyPatients').html(rest.totalRecencyPatients)
            jqq('#totalPBS').html(rest.totalPBS)
        });
    }


    function getIssuesList() {
        if (jqq('#issueType').val() == "") {
            alert("Please select an issue type")
            return false;
        }
        jqq('#uploadDataTable').dataTable().fnClearTable();
        jqq('#uploadDataTable').dataTable().fnDestroy();

        jqq('#uploadDataTable').DataTable({
            //"info": false,
            "processing": true,
            "serverSide": true,
            //"pagingType": "full_numbers",
            "pageLength": 10,
            "searching": true,
            "autoWidth": false,
            // "bLengthChange": false,
            "retrieve": true,
            "ordering": false,
            "orderClasses": false,
            "lengthMenu": [10, 50, 100],
            "language": {
                "searchPlaceholder": "Type to filter results",
                //"processing": "Fetching data from the NDR Database <i class='fa fa-spinner fa-spin'></i>"
            },
            buttons: [
                {extend: 'excel', text: 'Save as Excel', className: 'btn'},
                {extend: 'pageLength', className: 'custom-select'}
            ],

            "ajax": {
                "type": 'POST',
                dataType: "json",
                data: {
                    to: jqq('#dashboard  #to').val(),
                    from: jqq('#dashboard  #from').val(),
                    issueType: jqq('#issueType').val()
                },
                "url": '${ ui.actionLink("nmrsreports", "reportslist", "getPatientWithIssues") }',
                "dataSrc": function (json) {
                    return json.data;
                }
                // dataSrc:"data"
            },
            "columns": [
                {
                    "data": "Program Enrollled To",
                    "render": function (data, type, full, meta) {
                        return '<div><span class="light-text"><span class="flaticon2-architecture-and-city"></span>&nbsp; &nbsp;</span>' + full.patientName + '</div>';
                    }
                },
                {
                    "data": "originalFileName",
                    "render": function (data, type, full, meta) {
                        return '<div><span class="light-text"><span class="flaticon2-list"></span>&nbsp; &nbsp; </span><b>' + full.identifier + '</b></div>';

                    }
                },
                {"data": "encounterType", className: 'right-text shaded-table vertical-center'},
                {
                    "data": "encounterDate",
                    "defaultContent": " - ",
                    className: 'right-text shaded-table vertical-center'
                },
                {
                    "data": "",
                    "render": function (data, type, full, meta) {
                        // if (full.status !== 'Uploaded')
                        if (jqq('#issueType').val() == "getPatientNoState" || jqq('#issueType').val() == "getPatientNoLGA") {
                            return '<a href="/openmrs/admin/patients/patient.form?patientId=' + full.patientId + '' +
                                '" class="btn btn-tiny btn-info btn-block" target="_blank">' +
                                '<i class="flaticon2-information"></i> Details</a>';
                        } else {
                            return '<a href="/openmrs/htmlformentryui/htmlform/editHtmlFormWithStandardUi.page?patientId=' + full.patientId + '&encounterId=' + full.encounterId + '&' +
                                '" class="btn btn-tiny btn-info btn-block" target="_blank">' +
                                '<i class="flaticon2-information"></i> Details</a>';
                        }


                    }
                }
            ]
        });

    }


</script>
