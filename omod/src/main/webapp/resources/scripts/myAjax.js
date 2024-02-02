/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


function leapYear(year)
{
    return (year % 100 === 0) ? (year % 400 === 0) : (year % 4 === 0);
}

function myAjax(data, cohortAjaxUrl)
{
    let myPromise = new Promise(function(resolve, reject) {
    // "Producing Code" (May take some time)

      jq = jQuery;
   
        jq.getJSON(cohortAjaxUrl,
            
        data, function(data){
                resolve(data)
            }, function(xhr, status, error){
                console.log(error);
                reject(error)
         })
         
    });
    
    return myPromise;
          
}

function getDateRangeForQuartercy(quarter, year)
{
    var startDate = "";
    var endDate = "";
    if(quarter == 1)
    {
         startDate = year+"-01-01";
        endDate = year+"-03-31";
    }
    else if(quarter == 2)
    {
        
         startDate = year+"-04-01";
        endDate = year+"-06-30";
    }
    else if(quarter == 3)
    {
         startDate = year+"-07-01";
        endDate = year+"-09-30";
    }
    else if(quarter == 4)
    {
         startDate = year+"-10-01";
        endDate = year+"-12-31";
    }
    
    console.log(year);
    return [startDate, endDate];
}

function getDateRangeForQuarter(quarter, year)
{
    var startDate = "";
    var endDate = "";
    if(quarter == 1)
    {
        year = year -1;
        startDate = year+"-10-01";
        endDate = year+"-12-31";
    }else if(quarter == 2)
    {
         startDate = year+"-01-01";
        endDate = year+"-03-31";
    }
    else if(quarter == 3)
    {
        
         startDate = year+"-04-01";
        endDate = year+"-06-30";
    }
    else if(quarter == 4)
    {
         startDate = year+"-07-01";
        endDate = year+"-09-30";
    }
    
    console.log(year);
    return [startDate, endDate];
}

function getAge(dateString) {
    var today = new Date();
    var birthDate = new Date(dateString);
    var age = today.getFullYear() - birthDate.getFullYear();
    var m = today.getMonth() - birthDate.getMonth();
    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }
    return age;
}


// this function is manually formatted. It's a quick fix due to time constraint. Don't run date function on the output of this function
function manuallyFormattedDate (inputDate) {
  var fullDate = new Date(inputDate);
console.log(fullDate);
var twoDigitMonth = fullDate.getMonth();
  twoDigitMonth++;
if (twoDigitMonth < 10)
    twoDigitMonth = "0" + twoDigitMonth;
  console.log(twoDigitMonth);
  //in javascript, adding empty string to number makes it a string so that ".length" can be used on it
var twoDigitDate = fullDate.getDate() + "";
if (twoDigitDate.length == 1)
    twoDigitDate = "0" + twoDigitDate;
var currentDate = fullDate.getFullYear() + "-" + twoDigitMonth + "-" + twoDigitDate; 
return currentDate;
}
