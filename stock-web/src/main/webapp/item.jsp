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
</head>
<body>

	<div id = "container">
		<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
			data-target="#myModal">Open Modal</button>

		<!-- Modal -->
		<div id="myModal" class="modal fade" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<a href="${pageContext.request.contextPath}/start/additem.htm"><button type="button" class="close" data-dismiss="modal" onclick = "">&times;</button></a>
						<h4 class="modal-title">Modal Header</h4>
					</div>
					<div class="modal-body">
						<form method = "POST"  action="${pageContext.request.contextPath}/add/item.htm">
						<input type = "text"/>
						<select name="type">
							<option value="" label="Please Select" />
							<option>${type}</option>
						</select>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>

			</div>
		</div>
</div>
</body>
</html>