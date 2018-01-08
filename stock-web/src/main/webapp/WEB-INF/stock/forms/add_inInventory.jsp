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
<title>Add in inventory</title>

</head>
<body>
	<script>
		function submitThisForm() {
			document.getElementById("inventoryentryfrm").submit();
		}
	</script>
	<center>
		<h2>Add In Inventory Form</h2>
	</center>
	<br>
	<form:form method="POST" modelAttribute="inventory"
		id="inventoryentryfrm"
		action="${pageContext.request.contextPath}/add/inventory.htm">

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
			 	
					<td>Transfer from location:</td>
					<td><form:select path="parentLocation">
							<form:option value="" label="Please Select" />
							<form:options items="${locations}" />
						</form:select></td>
					<td><form:errors path="parentLocation"
							cssStyle="color: #ff0000" /></td>

				</tr>
				<tr>
					<td>Transfer to location:</td>
					<td><form:select path="consumerLocation">
							<form:option value="" label="Please Select" />
							<form:options items="${locations}" />
						</form:select></td>
					<td><form:errors path="consumerLocation"
							cssStyle="color: #ff0000" /></td>

				</tr>
 
				<tr>

					<td><form:label path="inventoryInitialVialsCount">Initial Count</form:label></td>
					<td><form:input path="inventoryInitialVialsCount" /></td>
					<td><form:errors cssStyle="color: #ff0000;" /></td>
				</tr>

			</table>
			<input type="button" id="submitBtn" value="Submit Data"
				onclick="submitThisForm();" />

		</center>

	</form:form>

</body>
</html>