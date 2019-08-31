<jsp:include page="header.jsp"></jsp:include>

<%@page import="com.theaiclub.auth.SecurityConfig"%>
<%@page import="java.io.File"%>
<%@page import="com.theaiclub.auth.AppUtils"%>
<%@page import="com.theaiclub.auth.UserAccount"%>
<section class="columns">
	<aside class="sidebar column is-one-fifth">
		Welcome,
		<%=AppUtils.getLoginedUser(session).getUserName()%>
		<hr>
		<ul>
			<li><a href="${pageContext.request.contextPath}/admin/addcamera">Add
					Camera</a></li>
			<li><a
				href="${pageContext.request.contextPath}/admin/addblacklist">Add
					Blacklist</a></li>
		</ul>
	</aside>

	<div class="accounts column is-two-thirds">

		<%
			if (AppUtils.getLoginedUser(session).getRoles().get(0).equals(SecurityConfig.ROLE_ADMIN)) {
		%>
		<script>
			$(document)
					.ready(
							function() {
								$
										.ajax({
											type: "POST",
											url : "${pageContext.request.contextPath}/admin/request",
											success : function(result) {
												var result = JSON.parse(result);
												console.log(result);
												var content = "";
												for (var i = 0; i < result.length; i++) {
													content += "<tr><td>"
															+ result[i].username
															+ "</td><td>"
															+ result[i].category
															+ "</td><td>"
															+ result[i].title
															+ "</td><td>"
															+ result[i].description
															+ "</td><td>"+result[i].datetime+"</td></tr>"
												}
												$("#tbody").html(content);
											}
										});
							});
		</script>

		<table class="table">
			<thead>
				<tr>
					<th><abbr title="Username">User</abbr></th>
					<th><abbr title="Category">Category</abbr></th>
					<th><abbr title="Title">Title</abbr></th>
					<th><abbr title="Description">Description</abbr></th>
					<th><abbr title="DateTime">Date & Time</abbr></th>
				</tr>
			</thead>
			<tbody id="tbody">

			</tbody>
		</table>
		<%
			}
		%>
	</div>

</section>

<jsp:include page="footer.jsp"></jsp:include>