/**
 * JavaScript for processing data and drawing graph on home page
 */

		//var parseDate = d3.time.format("%Y-%m-%d").parse;
       	var simpleData = {};
        simpleData.records = new Array();
        
        // function to extract date and cost from raw data 
        for (var i = 0; i < records.length; i++) {
        	var tempRecords = records[i];
        	
        	simpleData.records.push({
        		"date" : tempRecords.recordDate.substring(5, tempRecords.recordDate.length),
        		"spending" : tempRecords.cost
        	});
        }
        //console.log(simpleData);
        
        // function to groupby and sum json data
        var data = simpleData.records.reduce(function(res, obj) {
            if (!(obj.date in res))
                res.__array.push(res[obj.date] = obj);
            else {
                res[obj.date].spending += obj.spending;
            }
            return res;
        }, {__array:[]}).__array
        
        //console.log(data);
       	
        //function to sort data by dates
        function sortFunction(a,b){  
            var dateA = new Date(a.date).getTime();
            var dateB = new Date(b.date).getTime();
            return dateA > dateB ? 1 : -1;  
        }; 
        
        /* function to create array of past 7 days */
        //function to format date to yyyy-mm-DD
        function formatDate(date){
            var dd = date.getDate();
            var mm = date.getMonth()+1;
            var yyyy = date.getFullYear();
            if(dd<10) {dd='0'+dd}
            if(mm<10) {mm='0'+mm}
            date = mm +'-' + dd;
            return date
        }
        
        //function to add missing date and spending into data
        function Last7Days (data) {
            if (data.length < 1) {
                for (var i=6; i>-1; i--) {
                var d = new Date();
                d.setDate(d.getDate() - i);
                data.push( {date:formatDate(d), spending:0} );  
                }
            }
            else {
                for (var i=6; i>-1; i--) {
                    var d = new Date();
                    d.setDate(d.getDate() - i);
                    d = formatDate(d);
                    if (!dayExistsIn(data,d)) {
                        data.push({date:d,spending:0});   
                    }
                }
            }
            return data;
         }
        
         //function to check if a date already exists in data
         function dayExistsIn(data, d) {
             var result = false; 
             for (var i=0; i<data.length; i++) {
                     if (data[i].date === d) {
                         result = true;
                     }
                 }
             return result;
         }
        
         Last7Days(data);
         data.sort(sortFunction);
         console.log(data);
		
         // set the dimensions and margins of the graph
         var margin = {top: 25, right: 10, bottom: 10, left: 15},
             width = +d3.select('#chart').style('width').slice(0, -2)*0.9  - margin.left - margin.right,
             height = 291 - margin.top - margin.bottom;

         // set the ranges
         var xBar = d3.scaleBand().range([0, width]).paddingInner(0.5).paddingOuter(0.25);
         var yBar = d3.scaleLinear().range([height, 0]);

         // append the svg obgect to the body of the page
         // appends a 'group' element to 'svg'
         // moves the 'group' element to the top left margin
         var svg = d3.select("#chart").append("svg")
            		 .attr("width", width + margin.left + margin.right*3)
		             .attr("height", height + margin.top + margin.bottom*3)
		             .append("g")
		             .attr("transform", "translate(" + margin.left*4 + "," + margin.top + ")");

         // Scale the range of the data
         xBar.domain(data.map(function(d) { return d.date; }));
         yBar.domain([0, d3.max(data, function(d) { return d.spending; })]).nice();

         var rect = svg.selectAll("rect")
              			.data(data)

         rect.enter().append("rect")
            		 .merge(rect)
	              	 .attr("class", "bar")
	              	 .style("stroke", "none")
	              	 .style("fill", "steelblue")
	              	 .attr("x", function(d){ return xBar(d.date); })
	              	 .attr("width", function(d){ return xBar.bandwidth(); })
	              	 .attr("y", function(d){ return yBar(d.spending); })
	              	 .attr("height", function(d){ return height - yBar(d.spending); });
	              	 
	          
          // Add the X Axis
          svg.append("g")
              .attr("transform", "translate(0," + height + ")")
              .call(d3.axisBottom(xBar))
          	  
          // Add the Y0 Axis
          svg.append("g")
              .attr("class", "axisSteelBlue")
              .call(d3.axisLeft(yBar))
              .append("text")
                          .attr("x", -margin.left*3.5)
                          .attr("y", -15)
                          .attr("dy", "0.32em")
                          .attr("fill", "#000")
                          //.attr("font-weight", "bold")
                          .attr("text-anchor", "start")
                          .attr("font-family", "sans-serif")
                          .style("font-size","14px")
                          .style("fill",'#9f0000')
                          .text("Total Spending");
          
          // text size
          svg.selectAll("text").style("font-size","13px");
          
          // add label on top bar
          svg.selectAll(".text")        
          .data(data)
          .enter()
          .append("text")
          .attr("class","label")
          .attr("x", function(d){ return xBar(d.date); })
          .attr("y", function(d){ return yBar(d.spending); })
          .attr("dy", "1em")
          .attr("dx", "0.2em")
          .style("fill","white")
          .style("font-size", "0.75em")
          .text(function(d) { 
        	  if(d.spending>0) return "$"+d.spending; });      