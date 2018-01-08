<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="/WEB-INF/template/include.jsp"%>
    <%@page import="com.ihs.stock.api.model.Item"%>
    <%@page import="com.ihs.stock.api.model.MonthlyStats"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Vaccinator Monthly Balance</title>
<style>
table, th, td {
    border: 1px solid black;
}

</style>
</head>
<body>
<center>
Balance for the month of ${month} ${year}

<table>
<tr>
<th>Item Name</th><th>Initial Vials Count</th><th>Balance Vials Count</th><th>Total Vials Count</th>
</tr>
			<c:forEach var="listValue" items="${list}">
			<tr>
				<td>${listValue.item.name}</td>
				<td>${listValue.initialContainers}</td>
				<td>${listValue.balanceContainers}</td>
				<td>${listValue.totalContainers}</td>
				</tr>
			</c:forEach>
		
</table>
</center>
</body>
</html>