
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<html>
<head>


<script>
	function redirect() {
		 
		window.top.location.href = "${pageContext.request.contextPath}/s_home.htm";
		  
		}
	var checkB = 1;
		function check() {
			var loc = document.getElementById("locId").value;
			
			if(loc == null || loc == "")
			{
			  alert("Please select a location");
				return;
			}
			
			
			submitThisForm();
		}
		function submitThisForm() {
			checkB=0;
			document.getElementById("reqform").submit();
		}
	
	
window.onbeforeunload = function(event) {
	 document.getElementById("reqform").reset();
	
 if(checkB== 1)
	 {
    return "Are u sure u want to navigate away";
	 }
};

</script>
</head>

<body>
<center><h2>Requisition Form for Vaccination Centers under town ${town}</h2></center>
	<center>
		<form:form method="POST" modelAttribute="reqList" id="reqform"
			action="${pageContext.request.contextPath}/add/updatereq/${user}/${id}.htm">
			<table>
				<tr>
					<td>Select location *</td>
					<td><select id="locId" name="location">
							<option value="" label="Please Select" />
							<c:forEach var="row" items="${cLoc}">
								<option>${row}</option>
							</c:forEach>
					</select></td>
				</tr>
				<br>
				<br>
			</table>
			<table>
				<th>Item</th>
				<th>Quantity *</th>
				<th>Comments</th>
				<c:forEach items="${item}" var="items" varStatus="status">
					<tr>
						<td>${items}</td>
						<td><input name="quantity[${status.index}]" /> <form:errors
								path="quantity[${status.index}]" cssStyle="color: #ff0000">
							</form:errors></td>

						<td><input type="text" name="comments[${status.index}]" /> <form:errors
								path="comments[${status.index}]" cssStyle="color: #ff0000">
							</form:errors></td>
					</tr>

				</c:forEach>

			</table>
			<br>
			<br>
			<input type="button" id="submitBtn" value="Submit Request"
				onclick="check();" />
			<input onclick="redirect();" type="button" value="Cancel" />
		</form:form>
	</center>
</body>
</html>
