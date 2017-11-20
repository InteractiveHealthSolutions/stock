<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Monthly Stats Update</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<style type="text/css">
table {
	border-collapse: collapse;
	margin: 0px auto;
	float: none;
}
table, th, td {
	border: 0.5px solid black;
}

th, td {
	padding: 10px;
	text-align: left;
}
</style>
</head>

<body>
	<script>
		function check() {
			var loc = document.getElementById("locId").value;
			
			if(loc == null || loc == "")
			{
			  alert("Please select a location");
				return;
			}
			 
			
			submitThisForm();
		}
		function submitThisForm() {
			document.getElementById("reqform").submit();
		}
	</script>
	<center>
		<h2>Requirement Update Form</h2>
	</center>
	<br>
	<br>
	<center>
	<form:form method="POST" modelAttribute="reqList"
		id="reqform"
		action="${pageContext.request.contextPath}/add/updatereq.htm">
		
	
		<tr>
		<td>Select location *</td>
					<td><select id = "locId" name="location" required>
							<option value="" label="Please Select" />
							<c:forEach var="row" items="${cLoc}">
									<option>${row}</option>
								</c:forEach>
						</select></td> 
						</tr>
						<br>
						<br>
				<table>	
				<th>Item</th>
				<th>Quantity *</th>
				<th>Comments *</th>
		<c:forEach items="${item}" var = "items" varStatus = "status">
		<tr>
	<td>${items} </td>
		<td><input name="quantity[${status.index}]" />
		<form:errors path = "quantity[${status.index}]" cssStyle="color: #ff0000"> </form:errors>
		</td>
		
		<td><input name="comments[${status.index}]" />
		<form:errors path = "comments[${status.index}]" cssStyle="color: #ff0000"> </form:errors>
		
		</td>
		</tr>
		
		</c:forEach>
		
</table>
<br>
<br>
<input type="button" id="submitBtn" value="Submit Request"
				onclick="check();" />
	</form:form>
</center>
</body>
</html>