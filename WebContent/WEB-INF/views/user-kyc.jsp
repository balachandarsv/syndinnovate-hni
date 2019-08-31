<%@page import="com.theaiclub.auth.AppUtils"%>
<%@page import="com.theaiclub.auth.UserAccount"%>
<jsp:include page="header.jsp"></jsp:include>
<section class="columns">
	<aside class="sidebar column is-one-fifth">
		Welcome,
		<%=AppUtils.getLoginedUser(session).getUserName()%>
		<hr>
		<ul>
			<li><a href="#">Account Details</a></li>
			<li><a href="#">Fund Transfer</a></li>
			<li><a href="#">Open Deposit Account</a></li>
		</ul>
	</aside>

	<div class="accounts column">
		<form id="myform" class="field" method="post"
			action="${pageContext.request.contextPath}/user/addkyc"
			enctype="multipart/form-data">
			<%
				if (request.getAttribute("errorMessage") != null) {
			%>
			<p class="error">${errorMessage}</p>
			<%
				request.setAttribute("errorMessage", null);
				}
			%>
			<div class="control">
				<input class="input" type="hidden"
					value="<%=AppUtils.getLoginedUser(session).getUserName()%>"
					name="username" id="name">
			</div>
			<label class="label">Password</label>
			<div class="control">
				<input class="input" type="Password" placeholder="Password"
					name="password" id="password">
			</div>
			<label class="label">Upload Photo</label>
		
			<div class="field">
				<div class="file is-centered is-boxed is-success has-name">
					<label class="file-label"> <input class="file-input" id="file-input"
						type="file" name="file"> <span class="file-cta">
							<span class="file-icon"> <i class="fa fa-upload"></i>
						</span> <span class="file-label"> Upload File</span>
					</span> <span class="file-name" id="file-name">  </span>
					</label>
				</div>
			</div>
			<div class="field is-grouped">
				<div class="control">
					<input type="submit" class="button is-success is-medium"
						value="Upload">
				</div>

			</div>



		</form>
	</div>
</section>
<jsp:include page="footer.jsp"></jsp:include>