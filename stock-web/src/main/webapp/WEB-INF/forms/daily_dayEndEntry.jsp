<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Day End Update</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body>
	<script>
		function submitThisForm() {
			document.getElementById("dailydayendentryform").submit();
		}
	</script>
	<center>
		<h2>Update Daily Stats</h2>
	</center>
	<form:form method="POST" modelAttribute="dayendentry"
		id="dailydayendentryform"
		action="${pageContext.request.contextPath}/vaccinator/dailydayendentry.htm">
		<center>
			<table cellspacing=10>
				<tr>
					<td>Please select an item:</td>
					<td><form:select path="itemName">
							<form:option value="" label="Please Select" />
							<form:options items="${items}" />
						</form:select></td>
					<td><form:errors cssStyle="color: #ff0000" /></td>
				</tr>
				<tr>
					<td><form:label path="closingTemprature">Enter the closing temprature</form:label></td>
					<td><form:input path="closingTemprature" /></td>
					<td><form:errors cssStyle="color: #ff0000;" /></td>
				</tr>
				<tr>
					<td><form:label path="usedQuantityCount">Enter the number of used doses</form:label></td>
					<td><form:input path="usedQuantityCount" /></td>
					<td><form:errors cssStyle="color: #ff0000;" /></td>
				</tr>
				<tr>
					<td><form:label path="wastedQuantityCount">Enter the number of wasted doses</form:label></td>
					<td><form:input path="wastedQuantityCount" /></td>
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