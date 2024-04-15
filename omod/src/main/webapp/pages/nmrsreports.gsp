<% ui.decorateWith("appui", "standardEmrPage") %>


<%=ui.resourceLinks()%>



<% ui.includeCss("nmrsreports", "fontawesome/css/all.css") %>
<% ui.includeCss("nmrsreports", "flaticon/flaticon.css") %>
<% ui.includeCss("nmrsreports", "flaticon2/flaticon.css") %>
<% ui.includeCss("nmrsreports", "bootstrap.css") %>
<% ui.includeCss("nmrsreports", "all.css") %>
<% ui.includeCss("nmrsreports", "site.css") %>
<% ui.includeCss("nmrsreports", "jquery.dataTables.min.css") %>
<% ui.includeCss("nmrsreports", "buttons.dataTables.min.css") %>
<% ui.includeCss("nmrsreports", "custom.css") %>
<% ui.includeCss("nmrsreports", "css/css.css") %>

<% ui.includeJavascript("nmrsreports", "jquery.js") %>
<% ui.includeJavascript("nmrsreports", "bootstrap.bundle.js") %>
<% ui.includeJavascript("nmrsreports", "site.js") %>
<% ui.includeJavascript("nmrsreports", "jquery.dataTables.min.js") %>
<% ui.includeJavascript("nmrsreports", "jquery-ui.js") %>
<% ui.includeJavascript("nmrsreports", "myAjax.js") %>
<% ui.includeJavascript("nmrsreports", "core.js") %>
<% ui.includeJavascript("nmrsreports", "charts.js") %>
<% ui.includeJavascript("nmrsreports", "sunburst.js") %>
<% ui.includeJavascript("nmrsreports", "highcharts.js") %>
<% //ui.includeJavascript("nmrsreports", "highstock.js") %>
<% //ui.includeJavascript("nmrsreports", "map.js") %>
<% //ui.includeJavascript("nmrsreports", "drilldown.js") %>
<% //ui.includeJavascript("nmrsreports", "data.js") %>
<% //ui.includeJavascript("nmrsreports", "js/highcharts-more.js") %>
<% ui.includeJavascript("nmrsreports", "js/accessibility.js") %>
<% ui.includeJavascript("nmrsreports", "js/exporting.js") %>
<% //ui.includeJavascript("nmrsreports", "offline-exporting.js") %>
<% ui.includeJavascript("nmrsreports", "js/export-data.js") %>
<% ui.includeJavascript("nmrsreports", "js/series-label.js") %>



<style>
.dataTables_length {
    width: 50%;
    float: right;
    text-align: left !important;
}
</style>

<!-- Tab links -->
<div class="tab">

    <button class="tablinks" onclick="openTab(event, 'dashboard')"
            id="defaultOpen">Reports Dashboardd</button>
    <button class="tablinks" onclick="openTab(event, 'reports')">Report Line Listings</button>
    <button class="tablinks" onclick="openTab(event, 'encounters')">Search for Encounter</button>

</div>


${ui.includeFragment("nmrsreports", "dashboard_1")}
${ui.includeFragment("nmrsreports", "encounters")}
${ui.includeFragment("nmrsreports", "reportslist")}



<script>
const counts = {
  CurrentARTStatusActive: 0,
  BiometricCapturedYes: 0,
  BiometricCapturedNo: 0,
  ValidCaptureYes: 0,
  ValidCaptureNo: 0,
  RecaptureCounts: {},
  currentViralLoad: {
    Unsuppressed: [],
    LLV: []
  },
  PBSTrend: {
    Active: [{
      "date": "",
      pids:[] 
    }],
    BiometricCaptured: [{
      "date": "",
      pids:[] 
    }],
  }
};
    
    getCv19Data();
    function getCv19Data(){

          myAjax({}, '${ ui.actionLink("nmrsreports", "ARTParams", "getAllARTParamsData") }').then(function(response){
            AllARTParamsData = JSON.parse(response);
            console.log("back in nmrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
            console.log(AllARTParamsData);
            console.log("back in nmrss sommmmmmmmmmmmmmmmmmmmmmeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            // Initialize the counts object
            

            // Iterate through the array and count the occurrences
            for (const obj of AllARTParamsData) {
              if (obj.CurrentARTStatus === "Active") {
                counts.CurrentARTStatusActive++;
              }
              if (obj.BiometricCaptured === "Yes") {
                counts.BiometricCapturedYes++;
              }
              if (obj.BiometricCaptured === "No") {
                counts.BiometricCapturedNo++;
              }
              if (obj.ValidCapture === "Yes") {
                counts.ValidCaptureYes++;
              }
              if (obj.ValidCapture === "No") {
                counts.ValidCaptureNo++;
              }
              const recaptureCount = obj.RecaptureCount;
                if (recaptureCount !== null && recaptureCount !== undefined) {
                    if (counts.RecaptureCounts[recaptureCount]) {
                    counts.RecaptureCounts[recaptureCount]++;
                    } else {
                    counts.RecaptureCounts[recaptureCount] = 1;
                    }
                } else {
                    if (counts.RecaptureCounts[null]) {
                        if(obj.ARTStartDate != "" && obj.ARTStartDate == obj.LastPickupDate){
                            //console.log("ARTStartDate")
                            //console.log(obj.ARTStartDate)
                            //console.log("LastPickupDate")
                            //console.log(obj.LastPickupDate)
                            //console.log("currentDate")
                            const currentDate = new Date();
                            //console.log(currentDate)
                            //console.log("DaysOfARVRefil")
                            //console.log(obj.DaysOfARVRefil)




                            
                            const dateToSubtract = new Date(obj.ARTStartDate);
                            const differenceInMilliseconds = currentDate - dateToSubtract;
                            const differenceInDays = differenceInMilliseconds / (1000 * 60 * 60 * 24);
                            const initARVDays = obj.DaysOfARVRefil;
                            if(differenceInDays>initARVDays){
                            //console.log("eligible");
                            counts.RecaptureCounts[null]++;
                            }
                            //console.log("differenceInDays");
                            //console.log(differenceInDays);






                            
                        }
                    } else {
                        if(obj.ARTStartDate != "" && obj.ARTStartDate == obj.LastPickupDate){
                            //console.log("ARTStartDate")
                            //console.log(obj.ARTStartDate)
                            //console.log("LastPickupDate")
                            //console.log(obj.LastPickupDate)
                            //console.log("currentDate")
                            const currentDate = new Date();
                            //console.log(currentDate)
                            //console.log("DaysOfARVRefil")
                            //console.log(obj.DaysOfARVRefil)




                            
                            const dateToSubtract = new Date(obj.ARTStartDate);
                            const differenceInMilliseconds = currentDate - dateToSubtract;
                            const differenceInDays = differenceInMilliseconds / (1000 * 60 * 60 * 24);
                            const initARVDays = obj.DaysOfARVRefil;
                            if(differenceInDays>initARVDays){
                            //console.log("eligible");
                            counts.RecaptureCounts[null] = 1;
                            }
                            //console.log("differenceInDays");
                            //console.log(differenceInDays);






                            
                        }
                        
                    }
                }

                if (obj.CurrentViralLoad_c_ml !== null && obj.CurrentViralLoad_c_ml !== undefined) {
                const currentViralLoad = parseInt(obj.CurrentViralLoad_c_ml);
                    if (currentViralLoad >= 1000) {
                    counts.currentViralLoad.Unsuppressed.push(obj.pid);
                    } else if (currentViralLoad >= 50 && currentViralLoad < 1000) {
                    counts.currentViralLoad.LLV.push(obj.pid);
                    }
                } else {/*undefined*/}
            }
            console.log("log here");
            console.log(counts.BiometricCapturedYes);
            console.log("log here again");
            console.log(counts);
            const dataValues = [
                counts.CurrentARTStatusActive,
                counts.BiometricCapturedNo,
                counts.RecaptureCounts[null],
                counts.ValidCaptureNo
            ];
            console.log("datavalues");
            console.log(dataValues);


            const analysisOfRecaptures = counts.RecaptureCounts;
            const analysisOfRecapturesData = Object.values(counts.RecaptureCounts);
            console.log("analysisOfRecaptures");
            console.log(analysisOfRecaptures);
            console.log("analysisOfRecapturesData");
            console.log(analysisOfRecapturesData);

            const Unsuppressed = counts.currentViralLoad.Unsuppressed.length;
            const LLV = counts.currentViralLoad.LLV.length;
            console.log("currentViralLoad");
            console.log(Unsuppressed);
            console.log(LLV);

            
            
            
            
            
            
            function groupPidsByCondition(data, counts) {
                const currentDate = new Date();
                const currentMonth = currentDate.getMonth() + 1; 
                const currentYear = currentDate.getFullYear();
              
                let startMonth, startYear;
                if (currentMonth >= 10) {
                  // If the current month is October or later, start from October of the current year
                  startMonth = 10;
                  startYear = currentYear;
                } else {
                  // If the current month is January to September, start from October of the previous year
                  startMonth = 10;
                  startYear = currentYear - 1;
                }
              
                // Group the values based on the conditions and store them in PBSTrend
                data.forEach((item) => {
                    const { CurrentARTStatus, BiometricCaptured, pid, LastPickupDate } = item;

                    const pickupMonth = new Date(LastPickupDate).getMonth() + 1; // Adding 1 because months are zero-based
                    const pickupYear = new Date(LastPickupDate).getFullYear();

                    if (pickupYear > startYear || (pickupYear === startYear && pickupMonth >= startMonth)) {
                      if (CurrentARTStatus === "Active") {
                        const pickupDate = new Date(LastPickupDate).toLocaleString("default", {
                          month: "long",
                          year: "numeric",
                        });
                        const existingDateIndex = counts.PBSTrend.Active.findIndex((group) => group.date === pickupDate);
                        if (existingDateIndex !== -1) {
                          counts.PBSTrend.Active[existingDateIndex].pids.push(pid);
                        } else {
                          counts.PBSTrend.Active.push({ date: pickupDate, pids: [pid] });
                        }


                          if (BiometricCaptured === "Yes") {
                            const pickupDate = new Date(LastPickupDate).toLocaleString("default", {
                              month: "long",
                              year: "numeric",
                            });
                            const existingDateIndex = counts.PBSTrend.BiometricCaptured.findIndex((group) => group.date === pickupDate);
                            if (existingDateIndex !== -1) {
                              counts.PBSTrend.BiometricCaptured[existingDateIndex].pids.push(pid);
                            } else {
                              counts.PBSTrend.BiometricCaptured.push({ date: pickupDate, pids: [pid] });
                            }
                          }
                      }
                    }
                  });
              
                // The values are now stored in PBSTrend.Active and PBSTrend.BiometricCaptured based on the conditions
              }
              
              groupPidsByCondition(AllARTParamsData, counts);
              
              //output
              console.log(counts.PBSTrend.Active); // Array of pids where CurrentARTStatus is "Active"
              console.log(counts.PBSTrend.BiometricCaptured); // Array of pids where BiometricCaptured is "Yes"
              
              
              const countsPBSTrendActive = counts.PBSTrend.Active.map(item => ({
                date: item.date,
                pidlength: item.pids.length
              })).filter(item => item.date !== ""); // Remove the first empty item
              console.log("countsPBSTrendActive");
              console.log(countsPBSTrendActive);
              
              const countsPBSTrendBiometricCaptured = counts.PBSTrend.BiometricCaptured.map(item => ({
                date: item.date,
                pidlength: item.pids.length
              })).filter(item => item.date !== ""); // Remove the first empty item
              console.log("countsPBSTrendBiometricCaptured");
              console.log(countsPBSTrendBiometricCaptured);
              
              
              
              
              for (let i = countsPBSTrendActive.length - 2; i >= 0; i--) {
                    countsPBSTrendActive[i].pidlength += countsPBSTrendActive[i + 1].pidlength;
                }

                console.log(countsPBSTrendActive);
              
              
                console.log("converted to function")
                function calculateCumulativePidlength(input) {
                    const countsPBSTrendActive = input.map(item => ({
                      date: item.date,
                      pidlength: item.pids.length
                    })).filter(item => item.date !== "");

                    for (let i = countsPBSTrendActive.length - 2; i >= 0; i--) {
                      countsPBSTrendActive[i].pidlength += countsPBSTrendActive[i + 1].pidlength;
                    }

                    return countsPBSTrendActive;
                }
                const cumulativePidlength_resultActive = calculateCumulativePidlength(counts.PBSTrend.Active);
                console.log(cumulativePidlength_resultActive);
                
                const cumulativePidlength_resultPBSYes = calculateCumulativePidlength(counts.PBSTrend.BiometricCaptured);
                console.log(cumulativePidlength_resultPBSYes);
                

              
                //data for the PBS Basetcapture trend
                
                const cumulativePidlength_result = [];

                for (let i = 0; i < cumulativePidlength_resultActive.length; i++) {
                  const a = cumulativePidlength_resultActive[i].pidlength;
                  const b = cumulativePidlength_resultPBSYes[i].pidlength;
                  const result = ((b / a)*100).toFixed(0);
                  cumulativePidlength_result.push({
                    date: cumulativePidlength_resultActive[i].date,
                    result: result
                  });
                }

                console.log(cumulativePidlength_result);
                
                const monthNames = cumulativePidlength_result.map(item => item.date.split(' ')[0].slice(0, 3));
                const resultValues = cumulativePidlength_result.map(item => parseFloat(item.result));

                monthNames.reverse();
                resultValues.reverse();

                
                console.log(monthNames);
                console.log(resultValues);




            // Modify the series data with dynamically supplied values
            const chart2 = Highcharts.chart('containercolIIT', {
                chart: {
                    type: 'column'
                },
                title: {
                    text: 'Potential IIT',
                    align: 'center'
                },
                xAxis: {
                    categories: ['Potential IIT (1 Week)', 'Potential IIT (1 Month)'],
                    crosshair: true,
                    accessibility: {
                        description: 'Countries'
                    }
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: 'Number of TX_Curr with PBS Capture'
                    }
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.y}</b>' // Update the tooltip format
                },
                plotOptions: {
                    column: {
                        dataLabels: {
                            enabled: false,
                            format: '<b>{point.y}</b>', // Update the data label format
                            style: {
                                fontSize: '1.2em'
                            }
                        }
                    }
                },
                series: [
                    {
                        name: 'Potential IIT',
                        data: [12, 23]
                    }
                ]
            });




            // Modify the series data with dynamically supplied values
            const chart = Highcharts.chart('containercol', {
                chart: {
                    type: 'column'
                },
                title: {
                    text: 'PBS Gap Analysis',
                    align: 'center'
                },
                xAxis: {
                    categories: ['TX_Curr', 'No Baseline PBS', 'No Recapture', 'Invalid Capture'],
                    crosshair: true,
                    accessibility: {
                        description: 'Countries'
                    }
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: 'Number of TX_Curr with PBS Capture'
                    }
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.y}</b>' // Update the tooltip format
                },
                plotOptions: {
                    column: {
                        dataLabels: {
                            enabled: true,
                            format: '<b>{point.y}</b>', // Update the data label format
                            style: {
                                fontSize: '1.2em'
                            }
                        }
                    }
                },
                series: [
                    {
                        name: 'PBS Capture',
                        data: dataValues
                    }
                ]
            });





            //for Line with data labels
            Highcharts.chart('containerPBSTrend', {
                chart: {
                    type: 'line'
                },
                title: {
                    text: 'PBS Base Capture Trend'
                },
                xAxis: {
                    categories: monthNames,
                    title: {
                        text: 'Current COP Year' // Add the desired title here
                    }
                },
                yAxis: {
                    title: {
                        text: '% Trend'
                    }
                },
                plotOptions: {
                    line: {
                        dataLabels: {
                            enabled: true,
                            formatter: function() {
                                return this.y + '%'; // Add the percent sign to the data label
                            }
                        },
                        enableMouseTracking: false
                    }
                },
                series: [{
                    name: '',
                    data: resultValues
                }]
            });
            

            /*
            //RecaptureCounts analysis chart
            const categories = Object.keys(analysisOfRecaptures).map(key => key!=="null" ? 'Count ' + parseInt(key) : 'No Recapture');
            console.log(categories)

            Highcharts.chart('containerrr', {
                chart: {
                    type: 'column' // Set the chart type to column
                },
                title: {
                    text: 'Recapture Analysis',
                    align: 'center'
                },
                xAxis: {
                    categories: categories // Set the categories for the x-axis
                },
                yAxis: {
                    title: {
                        text: 'Count' // Set the title for the y-axis
                    }
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.y}</b>' // Update the tooltip format
                },
                plotOptions: {
                    column: {
                        dataLabels: {
                            enabled: true,
                            format: '<b>{point.y}</b>', // Update the data label format
                            style: {
                                fontSize: '1.2em'
                            }
                        }
                    }
                },
                series: [{
                    name: 'Count',
                    data: analysisOfRecapturesData // Update the data values
                }]
            });
            */


            const tx_curr = counts.CurrentARTStatusActive;
            Highcharts.chart('containereaca', {
                chart: {
                    type: 'column' // Set the chart type to column
                },
                title: {
                    text: 'Eligibility for EAC',
                    align: 'center'
                },
                xAxis: {
                    categories: ['TX_Curr', 'Unsuppressed', 'LLV'] // Set the categories for the x-axis
                },
                yAxis: {
                    title: {
                        text: 'Count' // Set the title for the y-axis
                    }
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.y}</b>' // Update the tooltip format
                },
                plotOptions: {
                    column: {
                        dataLabels: {
                            enabled: true,
                            format: '<b>{point.y}</b>', // Update the data label format
                            style: {
                                fontSize: '1.2em'
                            }
                        }
                    }
                },
                series: [{
                    name: 'Count',
                    data: [tx_curr, Unsuppressed, LLV] // Update the data values
                }]
            });


            const eligibleForEAC = Unsuppressed+LLV;
            const notCommencedEAC = eligibleForEAC-23;
            Highcharts.chart('containereacb', {
                chart: {
                    type: 'column' // Set the chart type to column
                },
                title: {
                    text: 'EAC Commencement',
                    align: 'center'
                },
                xAxis: {
                    categories: ['Eligible for EAC', 'Not Commenced EAC', 'Commenced EAC', 'EAC > 3 Months'] // Set the categories for the x-axis
                },
                yAxis: {
                    title: {
                        text: 'Count' // Set the title for the y-axis
                    }
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.y}</b>' // Update the tooltip format
                },
                plotOptions: {
                    column: {
                        dataLabels: {
                            enabled: true,
                            format: '<b>{point.y}</b>', // Update the data label format
                            style: {
                                fontSize: '1.2em'
                            }
                        }
                    }
                },
                series: [{
                    name: 'Count',
                    data: [eligibleForEAC, notCommencedEAC, 23, 20] // Update the data values
                }]
            });


            Highcharts.chart('containereacc', {
                chart: {
                    type: 'column' // Set the chart type to column
                },
                title: {
                    text: 'EAC Sessions',
                    align: 'center'
                },
                xAxis: {
                    categories: ['EAC > 3 Months', '1 EAC', '2 EACs', '3 EACs and above'] // Set the categories for the x-axis
                },
                yAxis: {
                    title: {
                        text: 'Count' // Set the title for the y-axis
                    }
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.y}</b>' // Update the tooltip format
                },
                plotOptions: {
                    column: {
                        dataLabels: {
                            enabled: false,
                            format: '<b>{point.y}</b>', // Update the data label format
                            style: {
                                fontSize: '1.2em'
                            }
                        }
                    }
                },
                series: [{
                    name: 'Count',
                    data: [20, 12, 3, 5] // Update the data values
                }]
            });

            Highcharts.chart('containereacd', {
                chart: {
                    type: 'column' // Set the chart type to column
                },
                title: {
                    text: 'Post EAC Viral Load Results',
                    align: 'center'
                },
                xAxis: {
                    categories: ['3 EACs and above', 'VL (<50)', 'VL (50 - 999)', 'VL (>=1000'] // Set the categories for the x-axis
                },
                yAxis: {
                    title: {
                        text: 'Count' // Set the title for the y-axis
                    }
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.y}</b>' // Update the tooltip format
                },
                plotOptions: {
                    column: {
                        dataLabels: {
                            enabled: false,
                            format: '<b>{point.y}</b>', // Update the data label format
                            style: {
                                fontSize: '1.2em'
                            }
                        }
                    }
                },
                series: [{
                    name: 'Count',
                    data: [5, 2, 1, 2] // Update the data values
                }]
            });
            
            

          })
            
       } 
   
    // Get the element with id="defaultOpen" and click on it
    document.getElementById("defaultOpen").click();

    function openTab(evt, tabName) {
        // Declare all variables
        var i, tabcontent, tablinks;

        // Get all elements with class="tabcontent" and hide them
        tabcontent = document.getElementsByClassName("tabcontent");
        for (i = 0; i < tabcontent.length; i++) {
            tabcontent[i].style.display = "none";
        }

        // Get all elements with class="tablinks" and remove the class "active"
        tablinks = document.getElementsByClassName("tablinks");
        for (i = 0; i < tablinks.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" active", "");
        }

        // Show the current tab, and add an "active" class to the button that opened the tab
        document.getElementById(tabName).style.display = "block";
        evt.currentTarget.className += " active";
    }
    
    
    
    //for Line with data labels
    Highcharts.chart('containeras', {
        chart: {
            type: 'line'
        },
        title: {
            text: 'Retention Chart'
        },
        xAxis: {
            categories: ['Oct', 'Nov', 'Dec', 'Jan', 'Feb', 'Mar']
        },
        yAxis: {
            title: {
                text: 'Retention in Treatment'
            }
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: false
                },
                enableMouseTracking: false
            }
        },
        series: [{
            name: 'TX_Curr',
            data: [16.0, 18.2, 23.1, 27.9, 32.2, 36.4]
        }, {
            name: 'IIT',
            data: [-2.9, -3.6, -0.6, 4.8, 10.2, 14.5]
        }]
    });
    
    
    
    
    


    



    
    
    // for Pie
    // Radialize the colors
    Highcharts.setOptions({
        colors: Highcharts.map(Highcharts.getOptions().colors, function (color) {
            return {
                radialGradient: {
                    cx: 0.5,
                    cy: 0.3,
                    r: 0.7
                },
                stops: [
                    [0, color],
                    [1, Highcharts.color(color).brighten(-0.3).get('rgb')] // darken
                ]
            };
        })
    });



    
    //for combo
        Highcharts.chart('containercom', {
        chart: {
            zoomType: 'xy'
        },
        title: {
            text: 'Treatment Switch',
            align: 'left'
        },
        xAxis: [{
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
            crosshair: true
        }],
        yAxis: [{ // Primary yAxis
            labels: {
                format: '{value}°C',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            },
            title: {
                text: 'Treatment Line',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            }
        }, { // Secondary yAxis
            title: {
                text: 'Precipitation',
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            },
            labels: {
                format: '{value} mm',
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            },
            opposite: true
        }],
        tooltip: {
            shared: true
        },
        legend: {
            align: 'left',
            x: 80,
            verticalAlign: 'top',
            y: 60,
            floating: true,
            backgroundColor:
                Highcharts.defaultOptions.legend.backgroundColor || // theme
                'rgba(255,255,255,0.25)'
        },
        series: [{
            name: 'Precipitation',
            type: 'column',
            yAxis: 1,
            data: [27.6, 28.8, 21.7, 34.1, 29.0, 28.4, 45.6, 51.7, 39.0,
                60.0, 28.6, 32.1],
            tooltip: {
                valueSuffix: ' mm'
            }

        }, {
            name: 'TX_Line',
            type: 'spline',
            data: [-13.6, -14.9, -5.8, -0.7, 3.1, 13.0, 14.5, 10.8, 5.8,
                -0.7, -11.0, -16.4],
            tooltip: {
                valueSuffix: '°C'
            }
        }]
    });
    



    // Build the chart
    Highcharts.chart('containerpi', {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: 'CTD Tracking',
            align: 'center'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        accessibility: {
            point: {
                valueSuffix: '%'
            }
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false,
                    format: '<span style="font-size: 1.2em"><b>{point.name}</b></span><br>' +
                        '<span style="opacity: 0.6">{point.percentage:.1f} %</span>',
                    connectorColor: 'rgba(128,128,128,0.5)'
                }
            }
        },
        series: [{
            name: 'Share',
            data: [
                { name: 'Clients Being Tracked', y: 938899 },
                { name: 'Clients without Outcome', y: 1229600 }
            ]
        }]
    });
    

   

    //for stacked pbs
    Highcharts.chart('containerpbs', {
        chart: {
            type: 'column'
        },
        title: {
            text: 'PBS Capture',
            align: 'center'
        },
        xAxis: {
            categories: ['TX_Curr', 'Recaptures', 'Baseline PBS', 'Valid PBS']
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Percent'
            }
        },
        tooltip: {
            pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b> ({point.percentage:.0f}%)<br/>',
            shared: true
        },
        plotOptions: {
            column: {
                stacking: 'percent',
                dataLabels: {
                    enabled: true,
                    format: '{point.percentage:.0f}%'
                }
            }
        },
        series: [{
            name: 'Road',
            data: [706, 390, 207, 290]
        }, {
            name: 'Rail',
            data: [0, 53, 256, 153]
        }]
    });
        
    
        
    

    
    //for col
    console.log("tanu " + counts.BiometricCapturedYes)
    const tanu = counts.BiometricCapturedYes
    
        // Mapping the counts object to match the order of categories in the chart
        const dataValues = [
            counts.CurrentARTStatusActive,
            counts.BiometricCapturedNo,
            counts.ValidCaptureYes,
            counts.ValidCaptureNo
        ];
        console.log("datavalues");
        console.log(dataValues);
        




</script>
















