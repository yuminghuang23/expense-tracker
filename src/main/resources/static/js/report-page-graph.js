/**
 * function to draw graphs on report page
 */
/***************** BUBBLE CHART *****************/
        function DrawBubbleChart(allRecordsByCat, color) {
		    var diameter = +d3.select('#bubble').style('width').slice(0, -2)*0.5;
		    
		 	// Define the div for the tooltip
	        var div = d3.select("div#bubble").append("div")	
	                .attr("class", "tooltip")				
	                .style("opacity", 0);
	        
	        var bubble = d3.pack(allRecordsByCat)
	            .size([diameter, diameter])
	            .padding(1.5);
	
	        var svg = d3.select("div#bubble")
	            .append("svg")
	            .attr("width", diameter)
	            .attr("height", diameter)
	            .attr("class", "bubble");
	        
	        var nodes = d3.hierarchy({children: allRecordsByCat})
	            .sum(function(d) { return d.cost; });
	        
	        var node = svg.selectAll(".node")
	            .data(bubble(nodes).descendants())
	            .enter()
	            .filter(function(d){
	                return  !d.children
	            })
	            .append("g")
	            .attr("class", "node")
	            .attr("transform", function(d) {
	                return "translate(" + d.x + "," + d.y + ")";
	            });
	        
	        node.append("circle")
		        .attr("r", function(d) {
		            return d.r;
		        })
		        .style("fill", function(d,i) {
		            return color(i);
		        })
	        	.on("mouseover", function(d) {		
	                div.transition()		
	                    .duration(20)		
	                    .style("opacity", .9);		
	                div.html(d.data.category + " $" + d.data.cost)	
	                    .style("left", (d3.event.pageX) + "px")		
	                    .style("top", (d3.event.pageY) + "px");	
	            })					
	            .on("mouseout", function(d) {		
	                div.transition()		
	                    .duration(50)		
	                    .style("opacity", 0); });
	
		    node.append("text")
		        .attr("dy", ".2em")
		        .style("text-anchor", "middle")
		        .text(function(d) {
		            return d.data.category.substring(0, d.r / 3);
		        })
		        .attr("font-family", "sans-serif")
		        .attr("font-size", function(d){
		            return d.r/3.5;
		        })
		        .attr("fill", "white");
		
		    node.append("text")
		        .attr("dy", "1.3em")
		        .style("text-anchor", "middle")
		        .text(function(d) {
		            return "$" + d.data.cost;
		        })
		        .attr("font-family",   "sans-serif")
		        .attr("font-size", function(d){
		            return d.r/3;
		        })
		        .attr("fill", "white");
		
		    d3.select(self.frameElement)
		        .style("height", diameter + "px");
        }
   
     	// ***************** STACKED BAR CHART ***************** //
        function DrawStackBar(allRecordsByCatDate, color, enumList) {
        	// set up width, height, margin
            var width = +d3.select('#bar').style('width').slice(0, -2)*0.8;
            var height = 500 ;
                
            var margin = {top: 30, right: 20, bottom: 30, left: 40};
                
            // Define the div for the tooltip
            var div = d3.select("body").append("div")	
                    .attr("class", "tooltip")				
                    .style("opacity", 0);
                
            // define svg to draw bar
            var svgStackBar = d3.select("div#bar").append("svg").
                            attr('width', width)
                            .attr('height', height)
                            .append("g")
                            .attr("transform", "translate(" + margin.left*2 + "," + margin.top*2 + ")")
                
            svgStackBar.append("g").attr("transform", "translate(" + margin.left + "," + margin.top + ")");
                
            width = width - margin.left - margin.right;
            height = height - margin.top - margin.bottom;
                
            // define x and y axis range 
            var x = d3.scaleBand()
                            .rangeRound([0, width])
                            .paddingInner(0.05)
                            .align(0.1);
            var y = d3.scaleLinear()
                            .rangeRound([height, 0]);
                
            var keys = enumList;
                
            // draw the axis
            x.domain(allRecordsByCatDate.map(function(d,i) { return i; })); // uses index to map to x-axis
                
            y.domain([0, d3.max(allRecordsByCatDate, function(d) { return d.cost; })]).nice();
                          color.domain(keys);
                
            // process data for stacked bar
            var stackBars = svgStackBar.selectAll(".bar") 
                    .data(d3.stack().keys(keys)(allRecordsByCatDate));
                
            // draw stacked bar
            stackBars
                    .enter().append("g")
                        .attr("fill", function(d) { return color(d.key); })
                    .selectAll("rect")
                        .data(function(d) { return d; })
                    .enter().append("rect")
                        .attr("x", function(d,i) { return x(i); }) // corresponds to the mapped index values 
                        .attr("y", function(d) { return y(d[1]); }) // plots the bars according to cost
                        .attr("height", function(d) { return y(d[0]) - y(d[1]); })
                        .attr("width", x.bandwidth())
                        .on("mouseover", function(d) {		// for hover div displaying pointed data
                            div.transition()		
                                .duration(20)		
                                .style("opacity", .9);
                            div.html(getKeyByValue(d.data,Math.round((d[1]-d[0])*100)/100) + ": $" + Math.round((d[1]-d[0])*100)/100 + 
                                "/$" + d.data.cost)	
                                .style("left", (d3.event.pageX) + "px")		
                                .style("top", (d3.event.pageY) + "px");	
                        })
                        .on("mouseout", function(d) {		
                            div.transition()		
                                .duration(50)		
                                .style("opacity", 0); });     
                
            // axis setup
            svgStackBar.append("g")
                          .attr("class", "axis")
                          .attr("transform", "translate(" + 0 + "," + height + ")")
                          .call(d3.axisBottom(x).tickFormat(function(d,i) { return i+1; }))
                       .append("text")
                          .attr("x", width/2)
                          .attr("y", -margin.top + margin.top * 2)
                          .attr("dy", "0.32em")
                          .attr("fill", "#000")
                          //.attr("font-weight", "bold")
                          .attr("text-anchor", "start")
                          .attr("font-family", "sans-serif")
                          .style("font-size","14px")
                          .style("fill",'#9f0000')
                          .text("Date");

            svgStackBar.append("g")
                          .attr("class", "axis")
                          //.attr("transform", "translate(0 ", "0)")
                          .call(d3.axisLeft(y).ticks(null, "s"))
                       .append("text")
                          .attr("x", -margin.left)
                          .attr("y", -margin.top+10)
                          .attr("dy", "0.32em")
                          .attr("fill", "#000")
                          //.attr("font-weight", "bold")
                          .attr("text-anchor", "start")
                          .attr("font-family", "sans-serif")
                          .style("font-size","14px")
                          .style("fill",'#9f0000')
                          .text("Spending");
                
            // legend setup
            var legend = svgStackBar.append("g")
                              .attr("font-family", "sans-serif")
                              .attr("font-size", 10)
                              .attr("text-anchor", "end")
                            .selectAll("g")
                            .data(keys.slice().reverse())
                            .enter().append("g")
                              .attr("transform", function(d, i) { return "translate(0," + i * 20 + ")"; });

            legend.append("rect")
                              .attr("x", width + margin.right*4)
                              .attr("width", 19)
                              .attr("height", 19)
                              .attr("fill", color);

            legend.append("text")
                              .attr("x", width + margin.right*4 - 5)
                              .attr("y", 9.5)
                              .attr("dy", "0.32em")
                              .text(function(d) { return d; });

            // function to find json key using value
            function getKeyByValue(object, value) {
                return Object.keys(object).find(key => object[key] === value);
            }
        }
	    
	    /***************** BAR BY CATEGORY CHART *****************/
        // function for drawing the bar chart 
        function DrawBarByCategory(category, data) {
            // dynamic variables
            var key = category;
	
            /* graph */
            // define width height 
            var width = +d3.select('#barByCategory').style('width').slice(0, -2)*0.8 
            var height = 500;

            // set maxvalue for y axis 
            var maxValue = 400;

            var margin = {
                top: 20,
                left: 20,
                right: 30,
                bottom: 55
            };
            
            // Define the div for the tooltip
            var div = d3.select("body").append("div")	
                .attr("class", "tooltip")				
                .style("opacity", 0);
            
            // set up svg canvas 
            var svgBar = d3.select("#barByCategory")
                .append("svg")
                .attr('width', width)
                .attr('height', height)
                .append("g")
                .attr("transform", "translate(" + margin.left*2 + "," + margin.top*2 + ")")
                //.attr("transform", "translate(" + margin.top + "," + margin.left + ")");
            
            width = width  - margin.left - margin.right;
            height = height - margin.top - margin.bottom;

            // for the axis range and type 
            var yscale = d3.scaleLinear()
                .range([height, 0])
                .domain([0, maxValue]);

            var xscale = d3.scaleBand().
                range([0, width])
                .paddingInner(0.4)
                .paddingOuter(0.4);

            // duration for animation 
            var duration = 1500;

            var xaxis = d3.axisBottom(xscale).tickFormat(function(d,i) { return i+1; });

            var yaxis = d3.axisLeft(yscale);
            
            // set up axis 
            svgBar.append('g')
                .attr('transform', 'translate(0, ' + (height) + ')')
                .attr('class', 'axis x');

            svgBar.append('g')
                .attr('class', 'axis y');
            
            xscale.domain(data.map(function(d) { return d.date; }));

            yscale.domain([0, d3.max(data, function(d) { return d[key]; })]).nice();

            // set up bars
            var bars = svgBar.selectAll(".bar")
                .data(data);
            
            // start to draw bars
            bars
                .enter()
                .append('rect')
                .attr('class', 'bar')
                .attr("fill", "	#FFA07A")
                .attr('width', xscale.bandwidth())
                .attr('height', 0)
                .attr('y', height)
                .on("mouseover", function(d) {		
                    div.transition()		
                        .duration(20)		
                        .style("opacity", .9);		
                    div.html("on date: " + d.date + " $" + d[key])	
                        .style("left", (d3.event.pageX) + "px")		
                        .style("top", (d3.event.pageY) + "px");	
                })					
                .on("mouseout", function(d) {		
                    div.transition()		
                        .duration(50)		
                        .style("opacity", 0); })
                .merge(bars)
                .transition()
                .duration(duration)
                .attr("height", function(d, i) {
                    return height - yscale(d[key]);
                })
                .attr("y", function(d, i) {
                    return yscale(d[key]);
                })
                .attr("width", xscale.bandwidth())
                .attr("x", function(d, i) {
                    return xscale(d.date);
                })

            // remove not used data
            bars
                .exit()
                .transition()
                .duration(duration)
                .attr('height', 0)
                .attr('y', height)
                .remove();

             // labels above bars
            var labels = svgBar.selectAll('.label')
                .data(data);

            var new_labels = labels
                .enter()
                .append('text')
                .attr('class', 'label')
                .attr('opacity', 0)
                .attr('y', height)
                .attr('fill', 'black')
                .attr('text-anchor', 'middle')

            new_labels.merge(labels)
                .transition()
                .duration(duration)
                .attr('opacity', 1)
                .attr('x', function(d, i) {
                    return xscale(d.date) + xscale.bandwidth() / 2;
                })
                .attr('y', function(d) {
                    return yscale(d[key]);
                })
                .text(function(d) {
                    return d.count;
                });

            // animation for labels
            labels
                .exit()
                .transition()
                .duration(duration)
                .attr('y', height)
                .attr('opacity', 0)
                .remove();

            // draw axis and animation  
            svgBar.select('.x.axis')
                .transition()
                .duration(duration)
                .call(xaxis);

            svgBar.select('.y.axis')
                .transition()
                .duration(duration)
                .call(yaxis);

            // y axis title
            svgBar.append("text")
                      //.attr("transform", "rotate(-90)")
                      .attr("y", -30)
                      .attr("x",5)
                      .attr("dy", "1em")
                      .style("fill",'#9f0000')
                      .style("text-anchor", "middle")
                      .style("font-family",'"Lato", sans-serif')
                      .style("font-size","14px")
                      .text("Spending");  

            // x-axis label
            svgBar.append("text")             
                      .attr("y", height*1.05)
                      .attr("x", width/2)
                      .attr("dy", "1em")
                      .style("fill",'#9f0000')
                      .style("text-anchor", "middle")
                      .style("font-family",'"Lato", sans-serif')
                      .style("font-size","14px")
                      .text("Date");  
            // legend
            const colors = ["#FFA07A"];
            // set a two length array for number of legends
            var lengedlenth = [key];
           
            var legend = svgBar.append("g")
                .attr("class", "legend")
                .attr("transform", "translate(" +  width + ", " + -height*0.6 + ")");

            legend.selectAll("rect")
                .data(lengedlenth)
                .enter()
                .append("rect")
                .attr("x", 0)
                .attr("y", function(d, i){
                    return (i * 15)+(height/1.5);
                })
                .attr("width", 12)
                .attr("height", 12)
                .attr("fill", function(d, i){
                    return colors[i];
                });
           
             legend.selectAll("text")
                  .data(lengedlenth)
                  .enter()
                  .append("text")
                  .text(function(d){
                     return d;
                  })
                  .attr("x", 15)
                  .attr("y", function(d, i){
                    return (i * 15)+(height/1.48);
                  })
                  .attr("text-anchor", "start")
                  .attr("alignment-baseline", "hanging")
                  .style("font-size",(width * 0.001) + "em")
                  .style("font-family",'"Lato", sans-serif');

        }
	    
        /***************** LINE CHART *****************/
        function DrawLineChart(data) {
            // convert monthYear to d3 date format
        var parser = d3.timeParse("%m-%Y");  
        
        // define width height 
        var width = +d3.select('#lineChart').style('width').slice(0, -2)*0.8 ;
        var height = 500;
        
        var margin = {
                top: 20,
                left: 20,
                right: 30,
                bottom: 55
        };
        
        // Define the div for the tooltip
        var div = d3.select("#lineChart").append("div")	
            .attr("class", "tooltip")				
            .style("opacity", 0);
        
        // set up svg canvas 
        var svgLine = d3.select("#lineChart")
            .append("svg")
            .attr('width', width)
            .attr('height', height)
            .append("g")
            .attr("transform", "translate(" + margin.left*2 + "," + margin.top*2 + ")")
        
        width = width - margin.left - margin.right;
        height = height - margin.top - margin.bottom;
        
        // set the ranges
        var x = d3.scaleTime().range([0, width]);
        var y = d3.scaleLinear().range([height, 0]);
        
        // define line
        var valueline = d3.line()
            .x(function(d) { return x(parser(d.monthYear))}) 
            .y(function(d) { return y(d.monthlySum)})
        
        // sclae range of data
        x.domain([parser(data[0].monthYear), parser(data[data.length-1].monthYear)]);
        //x.domain(d3.extent(data, function(d) { return parser(d.monthYear) }));
        //x.domain(data.map(function(d) { console.log(d.monthYear); return d.monehtYear; }));
        y.domain([0, d3.max(data, function(d) { return d.monthlySum })]);
        
        // add valueline path
        svgLine.append("path")
            .datum(data)
            .attr("class", "line")
            .attr("d", valueline);
        
        // add dot for each data point
        svgLine.selectAll(".dot")
            .data(data)
            .enter().append("circle")
            .attr("class","dot")
            .attr("cx", function(d,i) { return x(parser(d.monthYear)) })
            .attr("cy", function(d) { return y(d.monthlySum) })
            .attr("r", 5)
            .on("mouseover", function(d) {	// for hover	
                    div.transition()		
                        .duration(20)		
                        .style("opacity", .9);		
                    div.html("$" + (Math.round(d.monthlySum * 100)/100).toFixed(2))	
                        .style("left", (d3.event.pageX) + "px")		
                        .style("top", (d3.event.pageY) + "px");	
                })					
                .on("mouseout", function(d) {		
                    div.transition()		
                        .duration(50)		
                        .style("opacity", 0); });
        
        // add axis
        svgLine.append("g")
                .attr("transform", "translate(0," + height + ")")
                .call(d3.axisBottom(x).ticks(d3.timeMonth, 1).tickFormat(d3.timeFormat("%b-%Y")));

        svgLine.append("g")
                .call(d3.axisLeft(y));  
        
        // axis label
        // y axis title
        svgLine.append("text")
                    //.attr("transform", "rotate(-90)")
                .attr("y", -30)
                .attr("x",5)
                .attr("dy", "1em")
                .style("fill",'#9f0000')
                .style("text-anchor", "middle")
                .style("font-family",'"Lato", sans-serif')
                .style("font-size","14px")
                .text("Total Spending");  

        // x-axis label
        svgLine.append("text")             
                .attr("y", height*1.06)
                .attr("x", width/2)
                .attr("dy", "1em")
                .style("fill",'#9f0000')
                .style("text-anchor", "middle")
                .style("font-family",'"Lato", sans-serif')
                .style("font-size","14px")
                .text("Month and Year");  
        }