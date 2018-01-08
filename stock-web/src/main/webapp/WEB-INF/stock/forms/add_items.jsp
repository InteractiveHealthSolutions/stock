<%@page import="java.util.HashMap"%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Item</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script>
function redirect() {
	 
	window.top.location.href = "${pageContext.request.contextPath}/mainpage.htm";
	  
	}
	function submitAddItemForm() {
		document.getElementById("itementryfrm").submit();
	}
	function submitAddAttributeTypeForm()
	{
		document.getElementById("itemattrtypeentryfrm").submit();
	}
	
	$(document).on("click", ".add-attr", function () {
	     var itemId = $(this).data('id');
	     
	     $(".modal-body #itemId").val(itemId);
	    
	     $('#addItemAttributeModal').modal('show');
	});
	function validateForm() {
	    var x = document.forms["itementryfrm"]["enclosedQuantity"].value;
	    if (isNaN(x) || x < 1 || x > 10) {
	        alert("Quantity should be a number");
	        return false;
	    } else {
	        return true;
	    }
	}
</script>
<style>
table {
	border-collapse: collapse;
	margin: 0px auto;
	float: none;
}

.main {
	border-radius: 5px;
	width: 50%;
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

.center-block {
	width: 250px;
	padding: 10px;
	background-color: #eceadc;
	color: #ec8007
}
</style>
</head>
<body>
	<center>
		<h2>Item Page</h2>
	</center>
	<table class="main">
		<tr>
			<th>Item Name</th>
		
			<th>Quantity in a container</th>
			<th>Expiry (after opening)</th>
			<th>Expiry Unit</th>
			<th>Brand</th>
			<th>Manufacturer</th>
			<th>Add attribute</th>
		</tr>
		<c:forEach var="listValue" items="${list}">
			<tr>
				<td>${listValue.name}</td>
				
				<td>${listValue.enclosedQuantity}</td>
				<td>${listValue.expiryAfterOpening}</td>
				<td>${listValue.expiryUnit}</td>
				<td>${listValue.brand}</td>
				<td>${listValue.manufacturer}</td>
				<td><button type="button"
						class="add-attr btn btn-default transparent" data-toggle="modal"
						data-id="${listValue.name}">Add Attributes</button></td>
			</tr>
		</c:forEach>

	</table>
	<br>

	<center>
		<div class="container">

			<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
				data-target="#addItemModal">Add item</button>
			<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
				data-target="#viewItemModal">View all items</button>
			<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
				data-target="#addItemAttributeTypeModal">Add item Attribute
				Type</button>
			<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
				data-target="#viewAttrModal">View all Attribute Types</button>

		</div>

	</center>
	<!-- Modal -->
	<div id="addItemModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" onclick="">&times;</button>
					<h4 class="modal-title">Add item</h4>
				</div>
				<form method="POST" id="nodeForm" modelAttribute="item"
					id="itementryfrm" onsubmit="return validateForm()"
					action="${pageContext.request.contextPath}/add/item.htm">
					<div class="modal-body">

						<div class="form-group">
							<label class="form-control-label">Enter Name *</label> <input
								class="form-control" type="text" name="name" required />
						</div>
						<div class="form-group">
							<label class="form-control-label">Select Type *</label> <select
								class="form-control" name="type" required>
								<option value="" label="Please Select" />
								<c:forEach var="row" items="${type}">
									<option>${row}</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<label class="form-control-label">Barcode *</label> <input
								class="form-control" name="barcode" required />
						</div>
						<div class="form-group">
							<label class="form-control-label">Description</label> <input
								class="form-control" name="description" />
						</div>
						<div class="form-group">
							<label class="form-control-label">Quantity in a container
								*</label> <input type = "number" class="form-control" name="enclosedQuantity" required />

						</div>
						<div class="form-group">
							<label class="form-control-label">Best Before (after
								opening) *</label> <input type = "number" class="form-control"
								name="expirationDurationAfterOpening" required />
						</div>
						<div class="form-group">
							<label class="form-control-label">Select Expiry Unit</label> <select
								class="form-control" name="expiryUnit" required>
								<option value="" label="Please select" />
								<c:forEach var="row" items="${expiryUnit}" >
									<option>${row}</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<label class="form-control-label">Brand</label> <input
								class="form-control" name="brand" />
						</div>
						<div class="form-group">
							<label class="form-control-label">Manufacturer</label> <input
								class="form-control" name="manufacturer" />
						</div>



					</div>

					<div class="modal-footer">
						<input type="submit" title="Add item" value="Add item"
							onclick="submitAddItemForm()" class="btn btn-default transparent">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>

		</div>
	</div>
	
	<!-- Modal -->
	<div id="viewItemModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" onclick="">&times;</button>
					<h4 class="modal-title">Available Items</h4>
				</div>

				<div class="modal-body">

					<table>
						<tr>
							<th>Item Name</th>
						
							<th>Quantity in a container</th>
							<th>Expiry (after opening)</th>
							<th>Expiry Unit</th>
							<th>Brand</th>
							<th>Manufacturer</th>
						</tr>
						<c:forEach var="listValue" items="${list}">
							<tr>
								<td>${listValue.name}</td>
								
								<td>${listValue.enclosedQuantity}</td>
								<td>${listValue.expiryAfterOpening}</td>
								<td>${listValue.expiryUnit}</td>
								<td>${listValue.brand}</td>
								<td>${listValue.manufacturer}</td>
							</tr>
						</c:forEach>

					</table>

				</div>
				<div class="modal-footer">

					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>

			</div>

		</div>
	</div>


	<!-- Modal -->
	<div id="addItemAttributeTypeModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" onclick="">&times;</button>
					<h4 class="modal-title">Add Attribute Type</h4>
				</div>
				<form method="POST" id="nodeForm" modelAttribute="attributeType"
					id="itemattrtypeentryfrm"
					action="${pageContext.request.contextPath}/add/attributetype.htm">
					<div class="modal-body">

						<div class="form-group">
							<label class="form-control-label">Enter Name *</label> <input
								class="form-control" type="text" name="attributeName" required />
						</div>
						<div class="form-group">
							<label class="form-control-label">Enter display name *</label> <input
								class="form-control" name="displayName" required />
						</div>
						<div class="form-group">
							<label class="form-control-label">Description</label> <input
								class="form-control" name="description" />
						</div>
						<div class="form-group">
							<label class="form-control-label">Category *</label> <input
								class="form-control" name="category" required />
						</div>
					</div>

					<div class="modal-footer">
						<input type="submit" title="Add item attribute type"
							value="Add item" onclick="submitAddAttributeTypeForm()"
							class="btn btn-default transparent">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>

		</div>
	</div>


	<!-- Modal -->
	<div id="viewAttrModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" onclick="">&times;</button>
					<h4 class="modal-title">Available Attribute Types</h4>
				</div>

				<div class="modal-body">

					<table>
						<tr>
							<th>Name</th>
							<th>Display Name</th>
							<th>Description</th>
							<th>Category</th>

						</tr>
						<c:forEach var="listValue" items="${attrlist}">
							<tr>
								<td>${listValue.attributeName}</td>
								<td>${listValue.displayName}</td>
								<td>${listValue.description}</td>
								<td>${listValue.category}</td>

							</tr>
						</c:forEach>

					</table>

				</div>
				<div class="modal-footer">

					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>

			</div>

		</div>
	</div>
	<div id="addItemAttributeModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" onclick="">&times;</button>
					<h4 class="modal-title">Add item Attribute</h4>
				</div>
				<form:form method="POST" modelAttribute="attrBean"
					id="itemattrentryfrm"
					action="${pageContext.request.contextPath}/add/itemattr.htm">
					<div class="modal-body">


						<div class="form-group">
							<label class="form-control-label">Item Name</label> <input
								class="form-control" type="text" name="itemName" id="itemId"
								readOnly="readOnly" />
						</div>


						<div class="form-group">
							<label class="form-control-label">Select Attribute</label> <select
								class="form-control" name="atype" required>
								<option value="" label="Please Select" />
								<c:forEach var="row" items="${attrlist}">
									<option>${row.displayName}</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<label class="form-control-label">Attribute Value</label> <input
								class="form-control" name="value" required />
						</div>
					</div>

					<div class="modal-footer">
						<input type="submit" title="Add attributes" value="Add Attribute"
							class="btn btn-default transparent">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form:form>
			</div>

		</div>
	</div>

</body>
</html>