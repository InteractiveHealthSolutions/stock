<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Daily Morning Update</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body>
	<script type="text/javascript">
		function submitThisForm() {
			document.getElementById("dailyentrymorningform").submit();
		}
	</script>

	<center>
		<h2>Daily Morning Update Form</h2>
	</center>
	<form:form method="POST" modelAttribute="morningentry"
		id="dailyentrymorningform"
		action="${pageContext.request.contextPath}/vaccinator/dailymorningentry/${user}/${loc}.htm">
		<center>
			<table cellspacing=10>
				<tr>
					<td><form:label path="openingTemprature">Enter the opening temperature</form:label></td>
					<td><form:input path="openingTemprature" /></td>
					<td><form:errors path="openingTemprature"
							cssStyle="color: #ff0000;" /></td>
				</tr>
				<tr>
					<td>ILR Status *</td>
					<td><form:select path="morningILRStatus">
							<form:option value="" label="Please Select" />
							<form:options items="${array}" />
						</form:select></td>
					<td><form:errors path="morningILRStatus" cssStyle="color: #ff0000" /></td>
				</tr>
				<tr>
					<td>Temprature Monitor Status *</td>
					<td><form:select path="morningILRStatus">
							<form:option value="" label="Please Select" />
							<form:options items="${array}" />
						</form:select></td>
					<td><form:errors path="morningILRStatus" cssStyle="color: #ff0000" /></td>
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