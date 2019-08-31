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
			<%
				if (AppUtils.getLoginedUser(session).getRoles().get(0).equals(SecurityConfig.ROLE_USER_NORMAL)
						|| AppUtils.getLoginedUser(session).getRoles().get(0).equals(SecurityConfig.ROLE_USER_HNI)) {
			%>
			<li><a href="#">Account Details</a></li>
			<li><a href="#">Fund Transfer</a></li>
			<li><a href="#">Open Deposit Account</a></li>
		
		<%
			} else {
		%>
		<li><a href="${pageContext.request.contextPath}/admin/addcamera">Add
					Camera</a></li>
		<li><a href="${pageContext.request.contextPath}/admin/addblacklist">Add Blacklist</a></li>
		<%
			}
		%>
		</ul>
	</aside>

	<div class="accounts column">

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
					url : "${pageContext.request.contextPath}/admin/home",
					success : function(result) {
						var result = JSON.parse(result);
						$("#users-count").html(result.hni);
						$("#normal-count").html(result.normal);
						$("#req-count").html(result.bookings);
						$("#blacklist-count").html(result.blacklists);
					}
				});
			});
		</script>
		<div class="tile">
			<div class="tile tilex is-5">
				<h4>Total HNI Users</h4>
				<h5 id="users-count"></h5>
			</div>
			<div class="tile tilex is-5">
				<h4>Total Normal Users</h4>
				<h5 id="normal-count"></h5>
			</div>
		</div>
		<div class="tile">
			<div class="tile tilex is-5">
				<h4>Online Requests</h4>
				<h5 id="req-count"></h5>
			</div>
			<div class="tile tilex is-5">
				<h4>Blacklists</h4>
				<h5 id="blacklist-count"></h5>
			</div>
		</div>
		<%
			}
		%>
	</div>

</section>