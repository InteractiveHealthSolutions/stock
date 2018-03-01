<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type='text/javascript'
	src='${pageContext.request.contextPath}/dwr/interface/DWRLocationService.js'></script>
<script type='text/javascript' src='${pageContext.request.contextPath}	/dwr/engine.js'></script>
	

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ILRMonitoringChart</title>
<script type="text/javascript">
	
</script>
<script>
	var checkB = 1;
	function redirect() {

		window.top.location.href = "${pageContext.request.contextPath}/start/mainpage.htm";

	}
	function submitThisForm() {
		checkB = 0;
		$('#vaccinationcenter').attr('required',true);
		$('#filterDatefrom').attr('required',true);
		document.getElementById("searchfrm").submit();

	}
	window.onload = function() {
		error();
		getTable();
	}
	function error() {
		var error = "${error}";
		if (typeof error != 'undefined' && error) {
			alert(error);
		}
	}
	window.onbeforeunload = function(event) {

		if (checkB == 1) {
			return "Are u sure u want to navigate away";
		}
	};
</script>
</head>
<body>
<center>
<form class="searchpalette" id="searchfrm" name="searchfrm" method="post" 
action = ${pageContext.request.contextPath}/view/ilrtable.htm>
<%@ include file="stock_location_selector.jsp" %>

Date 
        <input id="filterDatefrom" name="filterDatefrom" bind-value = "${filterDatefrom}" class="calendarbox" value="${filterDatefrom}" size="11" required/> 
		<input id="filterDateto" name="filterDateto" bind-value="${filterDateto}" class="calendarbox" value="${filterDateto}" size="11" required/>
		<a class="clearDate" onclick="clearFilterDate();">X</a>
		<script type="text/javascript">
	 
		function clearFilterDate(){	
			$('input[id^="filterDate"]').val("");
		}
		
		$(function() {
			$('input[id^="filterDate"]').datepicker({
		    	duration: '',
		        constrainInput: false,
		        maxDate: "+0d",
		        dateFormat: 'dd-mm-yy',
		     });
			
		});
		</script>
		<input type="submit" value="Search" id="searchbtn"
				 />
</form>
</center>
	<div class="row">
		<div class=" col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<center>
						<h2>Temperature Monitoring Chart ${loc.name} ${monName} ${yr}</h2>
					</center>

					<center>
						<font color="red"><h2 id="demo"></h2></font>
					</center>
				</div>
				<center>
					<div id="showData"></div>
				</center>
				<div class="panel-body">
					<canvas id="canvas"></canvas>
					<script>
						function getTable() {
							var location = "${loc}";
							var date = "${filterDatefrom}";
							console.log("hereee");
							if((typeof location != 'undefined' && location) &&
									(typeof date != 'undefined' && date)) {
								console.log("script");
								$
										.ajax({
											type : "GET",
											crossDomain : "FALSE",
											cache : false,
											url : "${pageContext.request.contextPath}/ilrws/view.htm?province=${province}&division=${division}&city=${city}&town=${town}&uc=${uc}&vaccinationcenter=${vaccinationcenter}&filterDateto=${filterDateto}&filterDatefrom=${filterDatefrom}",
											success : function(myresp) {
												//	console.log("Chal gayaa :D");
												data = (myresp);
												console.log(data);
												var col = [];
												var col1 = [];

												for ( var key in data[0]) {
													if (key == "dateToday") {
														if (col.indexOf(key) === -1) {
															col.push("Date");
															col1.push(key);
														}
													}
													if (key == "morningTime") {
														if (col.indexOf(key) === -1) {
															col.push("Time in");
															col1.push(key);
														}
													}
													if (key == "dayendTime") {
														if (col.indexOf(key) === -1) {
															col
																	.push("Time out");
															col1.push(key);
														}
													}
													if (key == "openingTemprature") {
														if (col.indexOf(key) === -1) {
															col
																	.push("Temprature (Time in)");
															col1.push(key);
														}
													}
													if (key == "closingTemprature") {
														if (col.indexOf(key) === -1) {
															col
																	.push("Temprature (Time out)");
															col1.push(key);
														}
													}

												}
												console.log(col1);
												console.log(col);
												var temp = col[2];
												col[2] = col[3];
												col[3] = temp;
												console.log(col);
												temp = col1[2];
												col1[2] = col1[3];
												col1[3] = temp;
												console.log(col1);
												var table = document
														.createElement("table");
												table.style.border = '0.5px solid black';
												table.style.align = 'center';
												var tr = table.insertRow(-1); // TABLE ROW.

												for (var i = 0; i < col.length; i++) {
													var th = document
															.createElement("th");
													th.style.padding = '15px';// TABLE HEADER.
													th.style.border = '0.5px solid black';
													th.innerHTML = col[i];
													tr.appendChild(th);
												}

												for (var i = 0; i < data.length; i++) {

													tr = table.insertRow(-1);

													for (var j = 0; j < col1.length; j++) {
														var tabCell = tr
																.insertCell(-1);
														tabCell.style.border = '0.5px solid black';
														tabCell.style.textAlign = 'center';
														if ((col1[j] == "openingTemprature" || col1[j] == "closingTemprature")
																&& data[i][col1[j]] > 8) {
															tabCell.style.backgroundColor = 'Tomato';
														}
														if ((col1[j] == "openingTemprature" || col1[j] == "closingTemprature")
																&& data[i][col1[j]] < 2) {
															tabCell.style.backgroundColor = 'Aquamarine';
														}
														if ((col1[j] == "openingTemprature" || col1[j] == "closingTemprature")
																&& data[i][col1[j]] == null) {
															tabCell.style.backgroundColor = 'Gold';
														}
														//  var tabCell =  document.createElement("td");
														tabCell.innerHTML = data[i][col1[j]];
													}
												}

												var divContainer = document
														.getElementById("showData");
												divContainer.innerHTML = "";
												divContainer.appendChild(table);

											}
										});
							} else {
								document.getElementById("demo").innerHTML = "Select all filters to render chart";
							}

						}
					</script>
				</div>
			</div>
		</div>
	</div>
</body>
</html>