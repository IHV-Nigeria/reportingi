<!-- Tab content -->
<div id="dashboard" class="tabcontent">

    <style>
    table,
    tr,
    td {
        border: none;
    }
    .snapchat-icon {
        color: green;
    }


    </style>

    <div class="container-fluid bg-light ">
        <div class="row text-center title">
            <h4 style="text-align: center">Report Dashboard</h4>
        </div>

        
        
        
        <div style="display: flex; justify-content: space-between; flex-wrap: wrap;">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
              
              <div style="background-color: white; border-radius: 0.5rem; padding: 1.5rem 2.5rem; flex: 1; margin-right: 20px;">
                <div style="text-align: left;">
                  <h2 style="font-size: 1.5rem; font-weight: 500; color: black; margin-bottom: 0.5rem; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
                    Total Enrolled
                  </h2><i class="flaticon-users-1 black" style="font-size: 25px;"></i>
                  <p style="font-size: 0.875rem; color: #6B7280; margin-top: 0.5rem; display: none">
                    Add description if necessary
                  </p>
                  <p>
                    <span id="totalPatients" style="font-size: 2rem; font-weight: 300; color: #000000;">
                      25
                    </span>
                    <span style="font-size: 1rem; font-weight: 500; color: #6B7280;">
                    </span>
                  </p>
                </div>
              </div>

              <div style="background-color: white; border-radius: 0.5rem; padding: 1.5rem 2.5rem; flex: 1; margin-right: 20px;">
                <div style="text-align: left;">
                  <h2 style="font-size: 1.5rem; font-weight: 500; color: teal; margin-bottom: 0.5rem; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
                    Total HTS
                  </h2><i class="flaticon2-hourglass-1 teal" style="font-size: 25px;"></i>
                  <p style="font-size: 0.875rem; color: #6B7280; margin-top: 0.5rem; display: none">
                    Add description if necessary
                  </p>
                  <p>
                    <span id="totalHTSPatients" style="font-size: 2rem; font-weight: 300; color: #000000;">
                      25
                    </span>
                    <span style="font-size: 1rem; font-weight: 500; color: #6B7280;">
                    </span>
                  </p>
                </div>
              </div>

              <div style="background-color: white; border-radius: 0.5rem; padding: 1.5rem 2.5rem; flex: 1; margin-right: 20px;">
                <div style="text-align: left;">
                  <h2 style="font-size: 1.5rem; font-weight: 500; color: purple; margin-bottom: 0.5rem; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
                    Total Recency
                  </h2><i class="flaticon-upload purple" style="font-size: 25px;"></i>
                  <p style="font-size: 0.875rem; color: #6B7280; margin-top: 0.5rem; display: none">
                    Add description if necessary
                  </p>
                  <p>
                    <span id="totalRecencyPatients" style="font-size: 2rem; font-weight: 300; color: #000000;">
                      -
                    </span>
                    <span style="font-size: 1rem; font-weight: 500; color: #6B7280;">
                    </span>
                  </p>
                </div>
              </div>

              <div style="background-color: white; border-radius: 0.5rem; padding: 1.5rem 2.5rem; flex: 1; margin-right: 20px;">
                <div style="text-align: left;">
                  <h2 style="font-size: 1.5rem; font-weight: 500; color: orange; margin-bottom: 0.5rem; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
                    Total PBS
                  </h2><i class="flaticon2-list-3 orange" style="font-size: 25px;"></i>
                  <p style="font-size: 0.875rem; color: #6B7280; margin-top: 0.5rem; display: none">
                    Add description if necessary
                  </p>
                  <p>
                    <span id="totalPBS" style="font-size: 2rem; font-weight: 300; color: #000000;">
                      -
                    </span>
                    <span style="font-size: 1rem; font-weight: 500; color: #6B7280;">
                    </span>
                  </p>
                </div>
              </div>

              <div style="background-color: #f1f1f1; border: 2px solid white; cursor: pointer; border-radius: 0.5rem; padding: 1.5rem 2.5rem; flex: 1; margin-right: 20px;">
                <div style="text-align: left;">
                  <h2 style="font-size: 1.5rem; font-weight: 500; color: purple; margin-bottom: 0.5rem; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
                    Download ART Linelist
                  </h2><i class="flaticon-upload purple" style="font-size: 25px;"></i>
                  <p style="font-size: 0.875rem; color: #6B7280; margin-top: 0.5rem; display: none">
                    Add description if necessary
                  </p>
                  <p>
                    <span id="LL" style="font-size: 2rem; font-weight: 300; color: #000000;">
                      Processing...
                    </span>
                    <span style="font-size: 1rem; font-weight: 500; color: #6B7280;">
                    </span>
                  </p>
                </div>
              </div>

            </div>
        </div>






<div class="horizontal-line" style="border-top: 1px solid #CCCCCC; width: 100%; margin: 10px 0;"></div>
            




        <div style="display: flex; justify-content: space-between; flex-wrap: wrap;">
            <div style="width: 50%; margin-bottom: 20px;">
              <figure class="highcharts-figure">
                <div id="containeras"></div>
                <p class="highcharts-description" style="align-items: center">
                  <strong></strong>
                </p>
              </figure>
            </div>
            
            <div style="width: 50%; margin-bottom: 20px;">
              <figure class="highcharts-figure">
                <div id="containercolIIT"></div>
                <p class="highcharts-description" style="align-items: center">
                  <strong></strong>
                </p>
              </figure>
            </div>
            
            
            
            
         

<div class="horizontal-line" style="border-top: 1px solid #CCCCCC; width: 100%; margin: 10px 0;"></div>
         
            
            
            
            
            
            <div style="width: 50%; margin-bottom: 20px;">
              <figure class="highcharts-figure">
                <div id="containercol"></div>
                <p class="highcharts-description" style="align-items: center">
                  <strong></strong>
                </p>
              </figure>
            </div>
            
            <div style="width: 50%; margin-bottom: 20px;">
              <figure class="highcharts-figure">
                <div id="containerPBSTrend"></div>
                <p class="highcharts-description" style="align-items: center">
                  <strong></strong>
                </p>
              </figure>
            </div>
            


<div class="horizontal-line" style="border-top: 1px solid #CCCCCC; width: 100%; margin: 10px 0;"></div>
            


            <div style="width: 50%; margin-bottom: 20px;">
              <figure class="highcharts-figure">
                <div id="containereaca"></div>
                <p class="highcharts-description" style="align-items: center">
                  <strong></strong>
                </p>
              </figure>
            </div>
            
            <div style="width: 50%; margin-bottom: 20px;">
              <figure class="highcharts-figure">
                <div id="containereacb"></div>
                <p class="highcharts-description" style="align-items: center">
                  <strong></strong>
                </p>
              </figure>
            </div>



<div class="horizontal-line" style="border-top: 1px solid #CCCCCC; width: 100%; margin: 10px 0;"></div>



            <div style="width: 50%; margin-bottom: 20px;">
              <figure class="highcharts-figure">
                <div id="containereacc"></div>
                <p class="highcharts-description" style="align-items: center">
                  <strong></strong>
                </p>
              </figure>
            </div>
            
            <div style="width: 50%; margin-bottom: 20px;">
              <figure class="highcharts-figure">
                <div id="containereacd"></div>
                <p class="highcharts-description" style="align-items: center">
                  <strong></strong>
                </p>
              </figure>
            </div>

            <div style="width: 67%; margin-bottom: 20px; display: none">
              <figure class="highcharts-figure">
                <div id="containercom"></div>
                <p class="highcharts-description" style="align-items: center">
                  <strong></strong>
                </p>
              </figure>
            </div>

            <div style="width: 50%; margin-bottom: 20px;">
              <figure class="highcharts-figure">
                <div id="containerpi"></div>
                <p class="highcharts-description" style="align-items: center">
                  <strong></strong>
                </p>
              </figure>
            </div>

            <div style="width: 30%; margin-bottom: 20px; display: none">
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
