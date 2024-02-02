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
            <h4 style="text-align: center">Patient Summary</h4>
        </div>


        <div class="row">

            <div class="line">

                <div class="col-md-12 mb-5">
                    <div class="row">
                        <div class="col mr-2 pl-0">
                            <div class="widget">
                                <div class="widget-content with-shadow">
                                    <div class="widget-icon">
                                        <i class="flaticon-upload purple"></i>
                                    </div>

                                    <div class="widget-block" style="margin-top: -10px;">
                                        <h4 class="widget-title purple" style="font-size: 0.9rem;">
                                            Total Enrolled
                                        </h4>
                                        <span class="widget-stat purple " id="totalPatients">
                                            ...
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col mr-2">
                            <div class="widget">
                                <div class="widget-content with-shadow">
                                    <div class="widget-icon">
                                        <i class="text-dark-50 flaticon-users-1 orange"></i>
                                    </div>
                                    <h4 class="widget-title orange">
                                        Patients On ART
                                    </h4>
                                    <span class="widget-stat orange" id="totalARTPatients">
                                        ...
                                    </span>
                                </div>
                            </div>
                        </div>

                        <div class="col mr-2">
                            <div class="widget">
                                <div class="widget-content with-shadow">
                                    <div class="widget-icon">
                                        <i class="flaticon2-hourglass-1 teal"></i>
                                    </div>
                                    <h4 class="widget-title teal">
                                        HTS
                                    </h4>
                                    <span class="widget-stat teal" id="totalHTSPatients">
                                        ...
                                    </span>
                                </div>
                            </div>
                        </div>

                        <div class="col mr-2">
                            <div class="widget">
                                <div class="widget-content with-shadow">
                                    <div class="widget-icon">
                                        <i class="flaticon2-list-3 blue-dark"></i>
                                    </div>
                                    <h4 class="widget-title blue-dark">
                                        Recency
                                    </h4>
                                    <span class="widget-stat blue-dark" id="totalRecencyPatients">
                                        ...
                                    </span>
                                </div>
                            </div>
                        </div>

                        <div class="col mr-2 pr-0">
                            <div class="widget">
                                <div class="widget-content with-shadow">
                                    <div class="widget-icon">
                                        <i class="text-dark-50 flaticon2-user-outline-symbol green-dark"
                                           style="color: #03051c;"></i>
                                    </div>
                                    <h4 class="widget-title green-dark" style="color: #03051c;">
                                        PBS Captured
                                    </h4>
                                    <span class="widget-stat green-dark" id="totalPBS" style="color: #03051c;">
                                        ...
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-3 pt-3">
                            <div class="form-group ">
                                <label>Start Date</label>
                                <input placeholder="Select date" type="date" id="from" class="form-control">
                            </div>
                        </div>

                        <div class="col-md-3 pt-3">
                            <div class="form-group">
                                <label for="example">End Date</label>
                                <input placeholder="Select date" type="date" id="to" class="form-control">
                            </div>
                        </div>

                        <div class="col-md-4 pt-3">
                            <div class="form-group">
                                <label>Program Area</label>
                                <select id="issueType" class="form-control">
                                    <option value="" selected="selected">- Select -</option>
                                    <option value="getPatientWithARVRefillIssues">Patient with arv refill issues</option>
                                    <option value="getPatientDrugPickupButNORegimen">Patient with drug Pickup but no regimen</option>
                                    <option value="getPatientNoState">Patients with no state</option>
                                    <option value="getPatientNoLGA">Patient with no lga</option>
                                </select>
                            </div>
                        </div>

                        <div class="col-md-2">
                            <button type="button" onclick="getIssuesList()" class="submit btn btn-primary btn-block"
                                    style="margin-top: 46px;">Search</button>
                        </div>
                    </div>
                </div>


                <div class="col-md-12">
                    <div class="row">
                        <div class="box white-background with-shadow">

                            <div class="box-content">
                                <table class="table table-striped table-hover" id="uploadDataTable">
                                    <thead class="table-dark">

                                    <tr>
                                        <th title="IP" rowspan="2" style="width: 350px;background-color: #343a40;
                                        color: #fff;">Patient Name</th>
                                        <th title="Batch" rowspan="2" style="width: 200px; background-color: #343a40;
                                        color: #fff;">Identifier</th>
                                        <th title="Basic Info" colspan="2"
                                            class="center-text shaded-table">Encounter Info</th>
                                        <th title="Click each row to get the Summary" rowspan="2" class="wp-75">
                                        </th>
                                    </tr>
                                    <tr class="center-text shaded-table">
                                        <th title="Art Start Date" style="width: 491px;background-color: #343a40;
                                        color: #fff;">Encounter Type</th>
                                        <th title=">PBS(Yes/No)" style="width: 300px; background-color: #343a40;
                                        color: #fff;">Encounter date</th>

                                    </tr>
                                    </thead>
                                </table>

                            </div>
                        </div>
                    </div>
                </div>
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
