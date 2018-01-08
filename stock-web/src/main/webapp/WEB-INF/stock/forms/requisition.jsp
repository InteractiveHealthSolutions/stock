<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Requisition Approval</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
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
<script>
	
</script>
</head>
<body>
	<center>

		<h2>Request for Town ${locId.name}</h2>
		<c:forEach items="${req}" var="r" varStatus="status">
			 UC : ${r.key}
		<table cellspacing=10>
				<th>Requisition Date</th>
				<th>Vaccination Center</th>
				<th>Item Name</th>
				<th>Required Quantity</th>
				<th>Comment</th>
				<th>Approval Status</th>

				<c:forEach items="${r.value}" var="temp">
					<tr>
						<td>${temp.dateCreated}</td>
						<c:forEach items="${vacCenter}" var="vc">
							<c:set var="loc" value="${temp.requisitionLocation}"></c:set>
							<c:set var="vct" value="${vc.locationId}"></c:set>
							<c:choose>

								<c:when test="${loc eq vct}">
									<td>${vct.name}</td>
								</c:when>
							</c:choose>
						</c:forEach>
						<c:forEach items="items" var="i">
							<c:set var="iid" value="${temp.item}"></c:set>
							<c:set var="it" value="${i.itemId}"></c:set>
							<c:choose>
								<c:when test="${iid eq it}">
									<td>${it.name}</td>
								</c:when>
							</c:choose>
						</c:forEach>

						<td>${temp.quantity}</td>
						<td>${temp.comments}</td>
						<c:set var="y" value="${temp.approvalStatus}" />
						<c:set var="m" value="Pending" />
						<c:choose>
							<c:when test="${y eq m}">
								<td style="background-color: #ff0000;">${temp.approvalStatus}</td>
							</c:when>
							<c:otherwise>
								<td style="background-color: #ffff00;">${temp.approvalStatus}</td>

							</c:otherwise>
						</c:choose>



					</tr>
				</c:forEach>


			</table>
		</c:forEach>

	</center>
	<br>
	<br>


	<table>

	</table>

</body>
</html>