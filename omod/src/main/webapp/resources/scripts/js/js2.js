var myChart = Highcharts.chart('container',{
    chart: {
    type:'line'
    },
    series: [
    {
    name: 'Cases',
    color: 'green',
    data: [
    {name: 'wk1', x:0, y:187760},
    {name: 'wk2', x:1, y:287760},
    {name: 'wk3', x:2, y:427760}
    ]
    },{
    name: 'Deaths',
    color:'black',
    data: [
    {name: 'wk1', x:0, y:177760},
    {name: 'wk2', x:1, y:25760},
    {name: 'w3', x:2, y:420760}
    ]
    }]
    });