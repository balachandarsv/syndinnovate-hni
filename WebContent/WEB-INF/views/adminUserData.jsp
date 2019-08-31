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
		<li><a href="${pageContext.request.contextPath}/admin/addblacklist">Add Blacklist</a></li>
		</ul>
	</aside>

	<div class="accounts column is-two-thirds">

		<%
			if (AppUtils.getLoginedUser(session).getRoles().get(0).equals(SecurityConfig.ROLE_USER_NORMAL)
					|| AppUtils.getLoginedUser(session).getRoles().get(0).equals(SecurityConfig.ROLE_USER_HNI)) {
		%>
		

		<%
			} else {
		%>
		<script>
			$(document).ready(function() {
				var user = "<%= request.getParameter("user") %>";
				$.ajax({
					type: "POST",
					url : "${pageContext.request.contextPath}/admin/userdata",
					data: {user:user},
					success : function(result) {
						var result = JSON.parse(result);
						console.log(result);
						var content = "";
						content+="<tr><td>"+result.user.username+"</td><td>"+result.user.role+"</td><td>"+result.user.email+"</td><td>"+result.user.mobile+"</td><td><figure class='image is-128x128'><img src='/Face/uploads/"+result.user.photo+"'></figure></td></tr>"
						
						$("#tbody").html(content);
						var requests = result.requests;
						var content2 = "";
						for (var i = 0; i < requests.length; i++) {
							content2 += "<tr><td>"
									+ requests[i].category
									+ "</td><td>"
									+ requests[i].title
									+ "</td><td>"
									+ requests[i].description
									+ "</td><td>"+requests[i].datetime+"</td></tr>"
						}
						$("#tbody2").html(content2);
					}
				});
			});
		</script>
		<h4>User Details</h4>
		<hr>
		<table class="table">
			<thead>
				<tr>
					<th><abbr title="Username">User</abbr></th>
					<th><abbr title="Role">Role</abbr></th>
					<th><abbr title="Email">Email</abbr></th>
					<th><abbr title="Mobile">Mobile</abbr></th>
					<th><abbr title="Photo">Photo</abbr></th>
				</tr>
			</thead>
			<tbody id="tbody">
				
			</tbody>
		</table>
		<h4>Request Details</h4>
		<hr>
		<table class="table">
			<thead>
				<tr>
					<th><abbr title="Category">Category</abbr></th>
					<th><abbr title="Title">Title</abbr></th>
					<th><abbr title="Description">Description</abbr></th>
					<th><abbr title="DateTime">Date & Time</abbr></th>
				</tr>
			</thead>
			<tbody id="tbody2">
				
			</tbody>
		</table>
		<%
			}
		%>
	</div>

</section>

<jsp:include page="footer.jsp"></jsp:include>