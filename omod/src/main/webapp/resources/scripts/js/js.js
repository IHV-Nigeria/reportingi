var myChart = Highcharts.chart('container',{
    chart: {
    type:'line'
    },
    series: [{
    name: 'Data Set',
    data: [2,40,71,3,11,51,4,71,31]
    },
    {
    name: 'Data Set',
    data: [1,2,3,1,1,2]
    }]
    });