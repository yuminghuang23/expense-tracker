/**
 * Functions for processing data on Report page
 */

		//function to sort data by dates
        function sortFunction(a,b){  
            var dateA = new Date(a.date).getTime();
            var dateB = new Date(b.date).getTime();
            return dateA > dateB ? 1 : -1;  
        }; 
    	
     	// function to sort based on month-year
        function sortFunctionForLineData(a,b){ 
            var parser = d3.timeParse("%m-%Y");
            var dateA = parser(a.monthYear);
            var dateB = parser(b.monthYear);
            console.log(dateA,dateB)
            return dateA > dateB ? 1 : -1;  
        }; 
        
        // function to format date 
        function formatDate(date){
            var dd = date.getDate();
            var mm = date.getMonth()+1;
            var yyyy = date.getFullYear();
            if(dd<10) {dd='0'+dd}
            if(mm<10) {mm='0'+mm}
            date = yyyy + '-' + mm +'-' + dd;
            return date
        }
                
        /* get all dates from previous month */
        //function to get all dates between two dates
        function getAllDates(start, end) {
            var arr = new Array();
            
            while (start <= end) {
                arr.push(formatDate(start));
                start.setDate(start.getDate() + 1);
            }
            return arr;
        }
        
        /* final json */
        function finalJson(data, dateArr) {
        	
            var jsonFinal = new Array();
            for (var i = 0; i < dateArr.length; i++) {
                var jsonEnum = {};
                var cost = 0;
                jsonEnum["date"] = dateArr[i].substring(5,dateArr.length); // insert date 
                for (var j = 0; j < data.length; j++) { 
                   if (dateArr[i] === data[j].recordDate) { // if date match, insert category and cost
                       jsonEnum[data[j].category] = data[j].cost;
                       cost += data[j].cost;
                   }
                }

                for (var k = 0; k < enumList.length; k++) {
                    if (!jsonEnum.hasOwnProperty(enumList[k])) { // insert the rest of category with 0
                        jsonEnum[enumList[k]] = 0;
                    }
                }
                jsonEnum["cost"] = cost;
                jsonFinal.push(jsonEnum);
            }
            return jsonFinal;
        }