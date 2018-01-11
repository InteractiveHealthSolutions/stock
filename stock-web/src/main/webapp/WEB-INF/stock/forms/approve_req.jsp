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
	<script type="text/javascript">
		function submitThisForm() {
			
			document.getElementById("approvereq").submit();
		}
	</script>

	<form:form method="POST" modelAttribute="urb" id="approvereq"
		action="${pageContext.request.contextPath}/add/approvereq/${user}/${locId.locationId}.htm">
		<center>

			<h2>
				Approve Request for ${locId.name}

				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
					type="button" id="submitBtn" value="Approve"
					onclick="submitThisForm();" />
			</h2>


			<!--<c:choose>
				<c:when test="${fn:length(req) gt 0}">

					<input type="radio" value="1" name="select" onclick="selectAll()" />Approve
					<input type="radio" name="select" value="0"
						onclick="selectAll(this)" />
					<br>
					<br>
					<br>
				</c:when>

			</c:choose>-->


			<table cellspacing=10>
				<th>Item Name</th>
				<th>Required Quantity</th>
				<th>Comment</th>
				<th>Current Status</th>

				<th>Approval Status</th>
				<c:forEach items="${req}" var="req" varStatus="status">
					<tr>
						<td>${req.item}</td>
						<td>${req.quantity}</td>
						<td>${req.comments}</td>
						<c:set var="y" value="${req.approvalStatus}" />
						<c:set var="m" value="Pending" />
						<c:choose>
							<c:when test="${y eq m}">
								<td style="background-color: #ff0000;">${req.approvalStatus}</td>
								<td><input type="radio" name="check[${status.index}]"
									value="approve" />Approve <input type="radio"
									name="check[${status.index}]" value="unapprove" />UnApprove <input
									type="radio" name="check[${status.index}]" value="donothing"
									checked />None</td>
							</c:when>
							<c:otherwise>
								<td style="background-color: #ffff00;">${req.approvalStatus}</td>
								<td>Request Rejected</td>

							</c:otherwise>
						</c:choose>



					</tr>
				</c:forEach>
				<c:forEach items="${reqAll}" var="list">
					<tr>
						<c:set var="y" value="${list.approvalStatus}" />
						<c:set var="m" value="Approved" />
						<c:choose>
							<c:when test="${y eq m}">
								<td>${list.item}</td>
								<td>${list.quantity}</td>
								<td>${list.comments}</td>
								<td>${list.approvalStatus}</td>
								<td>${list.approvalStatus}</td>
							</c:when>
						</c:choose>
				</c:forEach>

			</table>

		</center>
		<br>
		<br>


	</form:form>

	<table>

	</table>

</body>
</html>