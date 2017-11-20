<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Inventory</title>
<script>
function submitThisForm()
{
	document.getElementById("searchfrm").submit();
}
</script>
<style>
table {
    border-collapse: collapse;
}

table, th, td {
	border: 0.5px solid black;
}
th , td{
padding: 15px;
    text-align: left;
    }
</style>
<center><h2>Search Inventory</h2></center>
</head>
<body>
<center>
<form:form method = "POST" modelAttribute = "search"  id = "searchfrm" 
action="${pageContext.request.contextPath}/view/searchinventory.htm">

<tr>
<td> Select a location</td>
<td><form:select path="locationName">
<form:option value = "" label = "Location"/>
<form:options items = "${locations}" />
</form:select></td>
<td>Select a year</td>
<td>
<form:select path="year">
<form:option value="" label="Year" />
<form:options items="${years}"/>
</form:select>
</td>
<td>Select a month</td>
<td><form:select path="month">
<form:option value="" label = "Month" />
<form:options items="${months}"/>
</form:select></td>
<td><input type = "button" value="Search" id= "searchbtn" onclick = "submitThisForm();"/></td>
</tr>
<br><br>
<tr>
<form:errors path = "" cssStyle="color: #ff0000" ></form:errors>${error}
</tr>


</form:form>
<br>
<table>
<th>Item</th>
<th>Initial Containers</th>
<th>Prev Month Balance</th>
<th>Total Balance</th>
<th>Month</th>
<th>Year</th>
<th>Location</th>
<c:forEach items = "${inventory}" var = "list">
<tr>
<td>${list.item.name}</td>
<td>${list.currentMonthContainers}</td>
<td>${list.prevMonthBalance}</td>
<td>${list.totalContainers}</td>
<td>${list.month}</td>
<td>${list.year}</td>
<td>${list.consumerLocation.name}</td>
</tr>
</c:forEach>

</table>
</center>
</body>
</html>