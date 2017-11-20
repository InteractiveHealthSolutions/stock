<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="/WEB-INF/template/include.jsp"%>
    <%@page import="com.ihs.stock.api.model.Item"%>
    <%@page import="com.ihs.stock.api.model.MonthlyStats"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Item list</title>
<style>
table, th, td {
    border: 1px solid black;
}

</style>
<center><h2>Items List</h2></center>
</head>
<body>
<center>


<table>
<tr>
<th>Item Name</th><th>Item Type</th><th>Quantity in a container</th><th>Expiry (after opening)</th><th>Expiry Unit</th>
<th>Brand</th><th>Manufacturer</th>
</tr>
			<c:forEach var="listValue" items="${list}">
			<tr>
				<td>${listValue.name}</td>
				<td>${listValue.itemType.itemType}</td>
				<td>${listValue.enclosedQuantity}</td>
				<td>${listValue.expiryAfterOpening}</td>
				<td>${listValue.expiryUnit}</td>
				<td>${listValue.brand}</td>
				<td>${listValue.manufacturer}</td>
				</tr>
			</c:forEach>
		
</table>
</center>
</body>
</html>