<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<script src="http://www.chartjs.org/samples/latest/utils.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.0/Chart.bundle.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.0/Chart.bundle.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.0/Chart.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.0/Chart.min.js"></script>


<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.5/jspdf.debug.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.5/jspdf.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ILRGraph</title>
<script type="text/javascript">
		function submitThisForm() {
			
			document.getElementById("searchfrm").submit();
			
		}
	</script>
	<script>
	window.onload = function()
	{
		error();
		getGraph();
	}
	function error()
	{var error = "${error}" ;
		if( typeof error != 'undefined' && error)
			{
			alert(error);
			}
	}
	
	</script>
</head>
<body>
	<form:form method="POST" modelAttribute="sb" id="searchfrm"
		action="${pageContext.request.contextPath}/view/ilrgraph.htm">


		<tr>
			<td>Select a location*</td>
			<td><form:select path="locationName">
					<form:option value="" label="Location" />
					<form:options items="${locations}" />
				</form:select></td>
			<td>Select a year*</td>
			<td><form:select path="year">
					<form:option value="" label="Year" />
					<form:options items="${years}" />
				</form:select></td>
			<td>Select a month*</td>
			<td><form:select path="month">
					<form:option value="" label="Month" />
					<form:options items="${months}" />
				</form:select></td>

			<td><input type="button" value="Search" id="searchbtn"
				onclick="submitThisForm();" /></td>

		</tr>

		<br>
		<br>
		<tr>
			<form:errors path="" cssStyle="color: #ff0000"></form:errors>
			<form:errors path="locationName" cssStyle="color: #ff0000"></form:errors>
			<form:errors path="year" cssStyle="color: #ff0000"></form:errors>
			<form:errors path="month" cssStyle="color: #ff0000"></form:errors>
			<c:out value = "${error}"></c:out>
			
		</tr>


	</form:form>

	<div class="row">
		<div class=" col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<center><h2>ILR Monitoring Graph ${loc.name} ${monName} ${yr}</h2></center>
				
					<center><font color="red"><h2 id="demo"></h2></font></center>
				</div>
				
				<div class="panel-body">
					<canvas id="canvas"></canvas>
					<script>
					function getGraph()
					{
						var location = "${loc}";
						var month = "${mon}";
						var year = "${yr}";
						console.log("hereee");
						if((typeof location != 'undefined' && location) &&
								(typeof month != 'undefined' && month) && (typeof year != 'undefined' && year))
						{
							console.log("script");
							 $.ajax({
                         		 type: "GET",
                         		 crossDomain: "FALSE",
                         		 cache: false,
                          	     url: "${pageContext.request.contextPath}/ws/ilr/view/${loc.locationId}/${mon}/${yr}",
                         	 success: function (myresp) {
                         		 //	console.log("Chal gayaa :D");
                         	         data3 = (myresp);
                         	         console.log(data3);
                         	         console.log(data3[0].openingTemprature)
                         	         var openingTemp = [];
                         	         var closingTemp = [];
                         	         var days = [];
                         	         
                         	           	 for(var i = 0 ; i < data3.length ; i++)
                         	        	 {
                         	        		 
                         	        			 openingTemp.push(data3[i].openingTemprature);
                              	        	     closingTemp.push(data3[i].closingTemprature);
                              	        	     days.push(data3[i].day);	
                              	        	     //console.log(data3[i].openingTemprature);
                          	        	 }
                         	      console.log(openingTemp);
                         	      console.log(closingTemp);
                         	        var config = {
                         	            type: 'line',
                         	            data: {
                         	            	
                         	            	  
                         	            		labels: days
                         	            		
                         	            	,
                         	                    datasets: [{
                         	                    
                         	                    	label: "Opening Temperature",
                             	                    backgroundColor: window.chartColors.red,
                             	                    borderColor: window.chartColors.red,
                             	                    data: 
                             	                    	openingTemp
                             	                   
                         	                    
                         	                    ,
                         	                   fill: false,
                         	                },
                         	                
                         	                {
                         	                	label: "Closing Temperature",
                         	                    backgroundColor: window.chartColors.blue,
                         	                    borderColor: window.chartColors.blue,
                         	                    data: 
                         	                    	closingTemp
                         	                    ,
                         	                    fill: false,
                         	                }]
                         	            },
                         	            options: {
                         	                responsive: true,
                         	                tooltips: {
                         	                    mode: 'index',
                         	                    intersect: false,
                         	                },
                         	                hover: {
                         	                    mode: 'nearest',
                         	                    intersect: true
                         	                },
                         	                scales: {
                         	                    xAxes: [{
                         	                        display: true,
                         	                        scaleLabel: {
                         	                            display: true,
                         	                            labelString: 'Days'
                         	                        }
                         	                    }],
                         	                    yAxes: [{
                         	                        display: true,
                         	                        scaleLabel: {
                         	                            display: true,
                         	                            labelString: 'Temperature'
                         	                        }
                         	                    }]
                         	                }
                         	            }
                         	        };
                         	       
                         	            var ctx = document.getElementById("canvas").getContext("2d");
                         	            window.myLine = new Chart(ctx, config);
                         	      

                         	        document.getElementById('randomizeData').addEventListener('click', function() {
                         	            config.data.datasets.forEach(function(dataset) {
                         	                dataset.data = dataset.data.map(function() {
                         	                    return randomScalingFactor();
                         	                });

                         	            });

                         	            window.myLine.update();
                         	        });

                         	        var colorNames = Object.keys(window.chartColors);
                         	        document.getElementById('addDataset').addEventListener('click', function() {
                         	            var colorName = colorNames[config.data.datasets.length % colorNames.length];
                         	            var newColor = window.chartColors[colorName];
                         	            var newDataset = {
                         	                label: 'Dataset ' + config.data.datasets.length,
                         	                backgroundColor: newColor,
                         	                borderColor: newColor,
                         	                data: [],
                         	                fill: false
                         	            };

                         	            for (var index = 0; index < config.data.labels.length; ++index) {
                         	                newDataset.data.push(randomScalingFactor());
                         	            }

                         	            config.data.datasets.push(newDataset);
                         	            window.myLine.update();
                         	        });

                         	        document.getElementById('addData').addEventListener('click', function() {
                         	            if (config.data.datasets.length > 0) {
                         	                var month = MONTHS[config.data.labels.length % MONTHS.length];
                         	                config.data.labels.push(month);

                         	                config.data.datasets.forEach(function(dataset) {
                         	                    dataset.data.push(randomScalingFactor());
                         	                });

                         	                window.myLine.update();
                         	            }
                         	        });

                         	        document.getElementById('removeDataset').addEventListener('click', function() {
                         	            config.data.datasets.splice(0, 1);
                         	            window.myLine.update();
                         	        });

                         	        document.getElementById('removeData').addEventListener('click', function() {
                         	            config.data.labels.splice(-1, 1); // remove the label first

                         	            config.data.datasets.forEach(function(dataset, datasetIndex) {
                         	                dataset.data.pop();
                         	            });

                         	            window.myLine.update();
                         	        });
							                               	 }
                         	 		});
						}
						else
							{
							document.getElementById("demo").innerHTML = "Select all filters to render graph";
							}
						
					}
                                 	
							    </script>
				</div>
			</div>
		</div>
	</div>
</body>
</html>