<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>ILRMonitoringChart</title>
<script type="text/javascript">
	
</script>
<script>
	var chackB = 1;
	function redirect() {

		window.top.location.href = "${pageContext.request.contextPath}/start/mainpage.htm";

	}
	function submitThisForm() {
		checkB = 0;
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
		<form:form method="POST" modelAttribute="sb" id="searchfrm"
			action="${pageContext.request.contextPath}/view/ilrtable.htm">


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
				<c:out value="${error}"></c:out>

			</tr>


		</form:form>
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
							var month = "${mon}";
							var year = "${yr}";
							console.log("hereee");
							if ((typeof location != 'undefined' && location)
									&& (typeof month != 'undefined' && month)
									&& (typeof year != 'undefined' && year)) {
								console.log("script");
								$
										.ajax({
											type : "GET",
											crossDomain : "FALSE",
											cache : false,
											url : "${pageContext.request.contextPath}/ilrws/view/${loc.locationId}/${mon}/${yr}.htm",
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