<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="com.ihs.stock.api.model.Inventory"%>
<%@page import="com.ihs.stock.api.model.Item"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Monthly Stats Update</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<script type="text/javascript">
	$(function() {
		$('#idDateField').datepicker();
		$('#idDateField1').datepicker();
	});
</script>
<body>
	<script>
		function submitThisForm() {
			document.getElementById("monthlystatsform").submit();
		}
	</script>
	<center>
		<h2>Monthly Update Form</h2>
	</center>
	<form:form method="POST" modelAttribute="monthlyUpdate"
		id="monthlystatsform"
		action="${pageContext.request.contextPath}/vaccinator/monthlystatsupdate.htm">
		<center>
			<table cellspacing=10>
				<tr>
					<td>Please select an item:</td>
					<td><form:select path="itemName">
							<form:option value="" label="Please Select" />
							<form:options items="${items}" />
						</form:select></td>
					<td><form:errors path="itemName" cssStyle="color: #ff0000" /></td>
				</tr>
				<tr>
					<td><form:label path="noOfVials">Enter Required Containers</form:label></td>
					<td><form:input path="noOfVials" /></td>
					<td><form:errors cssStyle="color: #ff0000;" /></td>
				</tr>
				<tr>
					<td><form:label path="expiryDate">Enter expiry date</form:label></td>
					<td><form:input id="idDateField" path="expiryDate" /></td>
					<td><form:errors cssStyle="color: #ff0000;" /></td>
				</tr>
				<tr>
					<td><form:label path="manufactureDate">Enter manufacture date</form:label></td>
					<td><form:input id="idDateField1" path="manufactureDate" /></td>
					<td><form:errors cssStyle="color: #ff0000;" /></td>
				</tr>
			</table>
		</center>
		<center>
			<input type="button" id="submitBtn" value="Submit Data"
				onclick="submitThisForm();" />
		</center>

	</form:form>

</body>
</html>