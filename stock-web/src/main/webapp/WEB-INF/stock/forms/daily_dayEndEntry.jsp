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
		<h2>Update Daily Stats for ${date}</h2>
	</center>
	<form:form method="POST" modelAttribute="deb"
		id="dailydayendentryform"
		action="${pageContext.request.contextPath}/vaccinator/dailydayendentry/${loc}.htm">
		<center>
		<table>
		<tr>
	<td>	Enter the closing temperature of ILR*</td><td> <input name="closingTemprature" />
		</td></tr>
		<tr>
					<td>ILR Status *</td>
					<td><form:select path="status">
							<form:option value="" label="Please Select" />
							<form:options items="${array}" />
						</form:select></td> 
						<td><form:errors path="status" cssStyle="color: #ff0000" /></td>

				</tr></table>	
		<br> <br>
		<table>	
		
		
				<th>Item</th>
				<th>Quantity used *</th>
				<th>Quantity wasted * </th>
		<c:forEach items="${item}" var = "items" varStatus = "status">
		<tr>
	<td>${items} </td>
		<td><input name="usedQuantityCount[${status.index}]" />
		<form:errors path = "usedQuantityCount[${status.index}]" cssStyle="color: #ff0000"> </form:errors>
		</td>
		
		<td><input type = "text" name="wastedQuantityCount[${status.index}]" />
		<form:errors path = "wastedQuantityCount[${status.index}]" cssStyle="color: #ff0000"> </form:errors>
		
		</td>
		</tr>
		
		</c:forEach>
		
</table>
		</center>
		<center>
			<input type="button" id="submitBtn" value="Submit Data"
				onclick="submitThisForm();" />
		</center>

	</form:form>

</body>
</html>