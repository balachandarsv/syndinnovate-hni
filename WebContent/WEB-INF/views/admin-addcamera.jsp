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
			action="${pageContext.request.contextPath}/admin/addcamera">

			<div class="field">
				<label class="label">URL</label>
				<div class="control">
					<input class="input" type="text" placeholder="Camera URL"
						name="url">
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