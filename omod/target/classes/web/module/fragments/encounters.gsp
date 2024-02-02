<%

    def options;
    options = context.encounterService.allEncounterTypes

%>
<div id="encounters" class="tabcontent">

    <div class="col-md-12">
        <div class="row">
            <div class="col-md-6 pt-3">
                <div class="form-group">
                    <label>Patient Identifier</label>
                    <input placeholder="Enter identifier" type="text" id="identifier" class="form-control">
                </div>
            </div>

            <div class="col-md-6 pt-3">
                <div class="form-group">
                    <label>Program Area</label>
                    <select id="encounterType" class="form-control">
                        <option value="" selected="selected">- Select -</option>
                        <% options.each { %>
                        <option value="${it.encounterTypeId}" selected="selected">${it.name}</option>
                        <% } %>

                    </select>
                </div>
            </div>

            <div class="col-md-3 pt-3">
                <div class="form-group">
                    <label>NDR Visit ID</label>
                    <input placeholder="Enter NDR visit ID" type="text" id="ndrVisitId" class="form-control">
                </div>
            </div>

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

            <div class="col-md-2">
                <button type="button" onclick="getEncounters()" class="submit btn btn-primary btn-block"
                        style="margin-top: 46px;">Search</button>
            </div>
        </div>
    </div>

    <div class="col-md-12">
        <table class="table table-striped table-hover" id="encounterDataTable">
            <thead class="table-dark">

            <tr>
                <th title="IP" rowspan="2" style="width: 350px;background-color: #343a40;
                color: #fff;">Patient Name</th>
                <th title="Batch" rowspan="2" style="width: 200px;">Identifier</th>
                <th title="Basic Info" colspan="2"
                    class="center-text shaded-table">Encounter Info</th>
                <th title="Click each row to get the Summary" rowspan="2" class="wp-75">
                </th>
            </tr>
            <tr class="center-text shaded-table">
                <th title="Art Start Date" style="width: 491px;">Encounter Type</th>
                <th title=">PBS(Yes/No)" style="width: 300px;">Encounter date</th>

            </tr>
            </thead>
        </table>
    </div>

</div>

<script>

    function getEncounters() {

        if (jqq('#encounterType').val() == "") {
            alert("Please select an issue type")
            return false;
        }
        jqq('#encounterDataTable').dataTable().fnClearTable();
        jqq('#encounterDataTable').dataTable().fnDestroy();

        jqq('#encounterDataTable').DataTable({
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
                    to: jqq('#encounters  #to').val(),
                    from: jqq('#encounters  #from').val(),
                    encounterTypeId: jqq('#encounterType').val(),
                    identifier: jqq('#identifier').val(),
                    ndrVisitDate: jqq('#ndrVisitId').val()
                },
                "url": '${ ui.actionLink("nmrsreports", "encounters", "getEncounters") }',
                "dataSrc": function (json) {
                    console.log(json)
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
                        return '<a href="/openmrs/htmlformentryui/htmlform/editHtmlFormWithStandardUi.page?patientId=' + full.patientId + '&encounterId=' + full.encounterId + '&' +
                            '" class="btn btn-tiny btn-info btn-block" target="_blank">' +
                            '<i class="flaticon2-information"></i> Details</a>';
                    }
                }
            ]
        });
    }
</script>



