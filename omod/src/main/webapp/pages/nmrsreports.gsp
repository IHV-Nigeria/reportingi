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
  RecaptureCounts: {}
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
                        counts.RecaptureCounts[null]++;
                    } else {
                        counts.RecaptureCounts[null] = 1;
                    }
                }
            }
            console.log("log here");
            console.log(counts.BiometricCapturedYes);
            console.log("log here again");
            console.log(counts);
            const dataValues = [
                counts.CurrentARTStatusActive,
                counts.BiometricCapturedNo,
                counts.ValidCaptureYes,
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



            // Modify the series data with dynamically supplied values
            const chart = Highcharts.chart('containercol', {
                chart: {
                    type: 'column'
                },
                title: {
                    text: 'PBS Capture',
                    align: 'center'
                },
                xAxis: {
                    categories: ['TX_Curr', 'No Baseline PBS', 'Valid Capture', 'Invalid Capture'],
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
                    valueSuffix: ' (1000 MT)'
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: [
                    {
                        name: 'PBS Capture',
                        data: dataValues
                    }
                ]
            });


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
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        },
        yAxis: {
            title: {
                text: 'Retention in Treatment'
            }
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: false
            }
        },
        series: [{
            name: 'TX_Curr',
            data: [16.0, 18.2, 23.1, 27.9, 32.2, 36.4, 39.8, 38.4, 35.5, 29.2,
                22.0, 17.8]
        }, {
            name: 'IIT',
            data: [-2.9, -3.6, -0.6, 4.8, 10.2, 14.5, 17.6, 16.5, 12.0, 6.5,
                2.0, -0.9]
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
                    enabled: true,
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
    
    
    //for stacked
        Highcharts.chart('containersta', {
        chart: {
            type: 'column'
        },
        title: {
            text: 'Further Analytics',
            align: 'left'
        },
        subtitle: {
            text: 'Source: <a href="https://www.ssb.no/transport-og-reiseliv/landtransport/statistikk/innenlandsk-transport">SSB</a>',
            align: 'left'
        },
        xAxis: {
            categories: ['2019', '2020', '2021']
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
            data: [434, 290, 307]
        }, {
            name: 'Rail',
            data: [272, 153, 156]
        }, {
            name: 'Air',
            data: [13, 7, 8]
        }, {
            name: 'Sea',
            data: [55, 35, 41]
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












