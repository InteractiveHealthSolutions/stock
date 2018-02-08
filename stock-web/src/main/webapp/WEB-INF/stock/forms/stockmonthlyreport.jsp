<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script type='text/javascript'
	src='/stock-web/dwr/interface/DWRLocationService.js'></script>
<script type='text/javascript' src='/stock-web/dwr/engine.js'></script>
<script type="text/javascript">
window.onload = function()
{
	var district = "${city}";
	var division = "${division}";
	var province = "${province}";
	var centerId = "${vaccinationcenter}";
	var UC = "${uc}";
	var town = "${town}";
	var tDate = "${filterDateto}";
	var fDate = "${filterDatefrom}";
	console.log("province "+province);
	console.log("Division "+division);
	console.log("District "+district);
	if((typeof province != null && province != "" && province) ||
			(typeof district != null && district != "" && district)
			||
			(typeof  division != null && division != "" && division)
			||
			(typeof centerId != null && centerId != "" && centerId)
			||
			(typeof UC != null && UC != "" && UC)
			||
			(typeof town != null && town != "" && town)
			||
			(typeof tDate != null && tDate != "" && tDate)
		    ||
			(typeof fDate != null && fDate != "" && fDate)){
		getTable();	
}
}
		function submitThisForm() {
			
			document.getElementById("searchfrm").submit();
			
		}
	</script>
<head>
</head>
<form class="searchpalette" id="searchfrm" name="searchfrm" method="post" 
action = ${pageContext.request.contextPath}/report/monthlyreport.htm>
<%@ include file="stock_location_selector.jsp" %>

Date 
        <input id="filterDatefrom" name="filterDatefrom" bind-value = "${filterDatefrom}" class="calendarbox" value="${filterDatefrom}" size="11"/> 
		<input id="filterDateto" name="filterDateto" bind-value="${filterDateto}" class="calendarbox" value="${filterDateto}" size="11"/>
		<a class="clearDate" onclick="clearFilterDate();">X</a>
		<script type="text/javascript">
	 
		function clearFilterDate(){	
			$('input[id^="filterDate"]').val("");
		}
		
		$(function() {
			$('input[id^="filterDate"]').datepicker({
		    	duration: '',
		        constrainInput: false,
		        maxDate: "+0d",
		        dateFormat: 'dd-mm-yy',
		     });
		});
		</script>
		<input type="submit" value="Search" id="searchbtn"
				 />
</form>
		<div class="table-responsive">
			<table id="stockTable" class="table table-bordered ">
			
				<tr style="text-align:center;">
					<th>Product</th>
					<th>Opening Balance</th>
					<th>Received</th>
					<th>Consumed Doses</th>
					<th>Closing Balance</th>
					<th>Unusable/Wastage</th>
				</tr>
						
			</table>
		</div>
<script>
function getTable()
{
	console.log("here");
	var locId = "";
	var district = "${city}";
	var division = "${division}";
	
	var province = "${province}";
	var centerId = "${vaccinationcenter}";
	var UC = "${uc}";
	var town = "${town}";
	var tDate = "${filterDateto}";
	var fDate = "${filterDatefrom}";
	
	 $.ajax({
	        url: "${pageContext.request.contextPath}/stock-monthly-report.htm?province=${province}&division=${division}&city=${city}&town=${town}&uc=${uc}&filterDateto=${filterDateto}&filterDatefrom=${filterDatefrom}", 
	        type: "GET",    
	        dataType:"json",   
	        success: function (response) 
	        {
	          //todo
	          console.log("success");
	          console.log(response);
	          
	          var trHTML = '';
	          $.each(response, function (key,value) {
	             trHTML += 
	                '<tr><td>' + value.item + 
	                '</td><td>' + value.openingBalance + 
	                '</td><td>' + value.received + 
	                '</td><td>' + value.usedcontainers + 
	                '</td><td>' + value.closingBalance + 
	                '</td><td>' + value.wastedContainers + 
	                 
	                '</td></tr>'; 
	          });

	            $('#stockTable').append(trHTML);
	          
	        }   
	    });
	}

</script>
</html>