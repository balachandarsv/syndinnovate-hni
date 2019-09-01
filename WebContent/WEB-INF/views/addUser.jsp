<%@page import="com.theaiclub.auth.AppUtils"%>
<%@page import="com.theaiclub.auth.UserAccount"%>
<jsp:include page="header.jsp"></jsp:include>
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

	<div class="accounts column">
		<form id="myform" class="field" method="post"
			action="${pageContext.request.contextPath}/admin/addUser">
			<%
				if (request.getAttribute("errorMessage") != null) {
			%>
			<p class="error">${errorMessage}</p>
			<%
				request.setAttribute("errorMessage", null);
				}
			%>
			<div class="field">
				<label class="label">Username</label>
				<div class="control">
					<input class="input" type="text" placeholder="username" name="username">
				</div>
			</div>
			
			<div class="field">
				<label class="label">Password</label>
				<div class="control">
					<input class="input" type="password" placeholder="password" name="password">
				</div>
			</div>

			<div class="field">
				<label class="label">Role</label>
				<div class="control">
					<div class="select">
						<select name="role">
							<option value="ADMIN">Admin</option>
							<option value="NORMAL_USER">Normal Customer</option>
							<option value="HNI_USER">HNI Customer</option>
						</select>
					</div>
				</div>
			</div>

			<div class="field">
				<label class="label">Email</label>
				<div class="control">
					<input class="input" type="email" placeholder="email" name="email">
				</div>
			</div>
			
			<div class="field">
				<label class="label">Mobile</label>
				<div class="control">
					<input class="input" type="text" placeholder="mobile" name="mobile">
				</div>
			</div>

			<div class="field is-grouped">
				<div class="control">
					<button class="button is-link">Submit</button>
				</div>
				
			</div>


		</form>
	</div>

</section>
<jsp:include page="footer.jsp"></jsp:include>