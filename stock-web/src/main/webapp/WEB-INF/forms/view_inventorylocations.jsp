
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
	<h2>All Locations Inventory Statistics for the month of ${mon}</h2>
</center>
<c:forEach items="${loc}" var="location">

	<ul class="bullet-list-round">

		<li>Statistics for ${location.name}<br>

			<ul class="bullet-list-round">
				<table>

					<th>Item</th>
					<th>Initial Balance (Quantity)</th>
					<th>Prev month Balance (Quantity)</th>
					<th>Wasted (Quantity)</th>
					<th>Used (Quantity)</th>
					<th>Current Balance (Quantity)</th>
					<c:forEach items="${map}" var="map">
						<c:set var="y" value="${location.name}" />
						<c:set var="m" value="${map.key.name}" />
						<c:choose>
							<c:when test="${y eq m}">
								<c:forEach items="${map.value}" var="item">
									<tr>
										<td>${item.key.name}</td>
										<c:forEach items=" ${item.value}" var="inMap">
											<td>${inMap}</td>
										</c:forEach>
									</tr>
								</c:forEach>
							</c:when>
						</c:choose>

					</c:forEach>
		</table>

			</ul>
		</li>
	</ul>

</c:forEach>
