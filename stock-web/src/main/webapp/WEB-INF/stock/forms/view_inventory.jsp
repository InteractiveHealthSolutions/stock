
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/template/include.jsp"%><style>
ul {
	list-style: none;
}

.bullet-list-round {
	padding: 0;
	margin: 0 0 24px 12px;
	line-height: 1.28571em;
	margin-left: 14px;
}

.bullet-list-round li {
	margin-top: 6px;
	margin-bottom: 6px;
	background-size: 13px;
	background-repeat: no-repeat;
	padding-left: 20px;
}

li.folder {
	
}

a {
	color: #000000;
	cursor: pointer;
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
}

table, th, td {
	border: 1px solid black;
}
</style>
<script
	src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script type="text/javascript">
	//Execute this after the site is loaded.
	$(function() {
		
		$('li > ul').each(function(i) {
			// Find this list's parent list item.
			var parentLi = $(this).parent('li');


			var subUl = $(this).remove();
			parentLi.wrapInner('<a/>').find('a').click(function() {
				// Make the anchor toggle the leaf display.
				subUl.toggle();
			});
			parentLi.append(subUl);
		});

		// Hide all lists except the outermost.
		$('ul ul').hide();
	});
</script>
<center>
<h2>Inventory Statistics</h2>
</center>
<c:forEach items="${yearlyStats}" var="year">

	<ul class="bullet-list-round">
		<tr>
			<li>Statistics for year ${year}<br>
		</tr>
		<ul class="bullet-list-round">
			<table>
				<li>
				<th>Month</th>
				<th>Item</th>
				<th>Initial Balance</th>
				<th>Prev month Balance</th>
				<th>Current Balance</th>
                <th>Location</th>
				<c:forEach items="${monthlyStats}" var="month">
					<c:set var="y" value="${year}" />
					<c:set var="m" value="${month.key}" />
					<c:choose>
						<c:when test="${y eq m}">
							<c:forEach items="${month.value}" var="item" varStatus="loop">
								<tr>
									<td>${item.month}</td>
									<td>${item.item.name}</td>
									<td>${item.currentMonthContainers}</td>
									<td>${item.prevMonthBalance}</td>
									<td>${item.totalContainers}</td>
									<td>${item.consumerLocation.name}</td>
								</tr>
							</c:forEach>
						</c:when>
					</c:choose>

				</c:forEach>

				</li>
			</table>

		</ul>
		</li>
	</ul>

</c:forEach>
