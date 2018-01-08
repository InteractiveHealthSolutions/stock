<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="/WEB-INF/template/include.jsp"%>
    <%@page import="com.ihs.stock.api.model.Item"%>
    <%@page import="com.ihs.stock.api.model.MonthlyStats"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Daily Stats</title>
<style>
table, th, td {
    border: 1px solid black;
}

</style>
<center><h2>Statistics for ${date}</h2></center>
</head>
<body>
<center>


<table>
<tr>
<th>Item Name</th><th>Quantity Wasted</th><th>Quantity Used</th><th>Containers Wasted</th><th>Containers Used</th>

</tr>
			<c:forEach var="listValue" items="${dailyStats}">
			<tr>
				<td>${listValue.item.name}</td>
				<td>${listValue.wastedQuantity}</td>
				<td>${listValue.usedQuantity}</td>
				<td>${listValue.wastedContainers}</td>
				<td>${listValue.usedContainers}</td>
			</tr>
			</c:forEach>
		
</table>
</center>
</body>
</html>