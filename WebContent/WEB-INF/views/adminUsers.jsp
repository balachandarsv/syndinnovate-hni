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
		<li><a
				href="${pageContext.request.contextPath}/admin/addUser">Add
					User</a></li>
		</ul>
	</aside>

	<div class="accounts column is-two-thirds">

		<%
			if (AppUtils.getLoginedUser(session).getRoles().get(0).equals(SecurityConfig.ROLE_USER_NORMAL)
					|| AppUtils.getLoginedUser(session).getRoles().get(0).equals(SecurityConfig.ROLE_USER_HNI)) {
		%>
		<table class="table is-striped is-bordered">
			<thead>
				<tr>
					<th>Account Number</th>
					<th>User Id</th>
					<th>Balance</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>12345678890</td>
					<td>281294</td>
					<td>Rs. 23,21,124.84</td>
				</tr>
			</tbody>
		</table>

		<div class="photo">

			<img
				src="<%="/Face/uploads" + File.separator + AppUtils.getLoginedUser(session).getUserName() + ".jpg"%>"
				width="300px" height="300px">

		</div>

		<%
			} else {
		%>
		<script>
			$(document).ready(function() {
				$.ajax({
					type: "POST",
					url : "${pageContext.request.contextPath}/admin/users",
					success : function(result) {
						var result = JSON.parse(result);
						console.log(result);
						var content = "";
						for (var i = 0; i < result.length; i++) {
							content+="<tr><td>"+result[i].username+"</td><td>"+result[i].role+"</td><td><figure class='image is-128x128'><img src='/Face/uploads/"+result[i].photo+"'></figure></td></tr>"
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
					<th><abbr title="Role">Role</abbr></th>
					<th><abbr title="Photo">Photo</abbr></th>
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
