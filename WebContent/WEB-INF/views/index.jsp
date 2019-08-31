
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
<jsp:include page="header.jsp"></jsp:include>
<div class="form">
	<h4>Login</h4>




	<form class="login-form" method="POST"
		action="${pageContext.request.contextPath}/login">
		<input type="hidden" name="redirectId" value="${redirectId}" /> <input
			type="text" name="userName" placeholder="Username" /> <input
			type="password" name="password" placeholder="Password" />

		<button type="submit">Login</button>
	</form>
	<%
		if (request.getAttribute("errorMessage") != null) {
	%>
	<p class="error">${errorMessage}</p>
	<%
		request.setAttribute("errorMessage", null);
		}
	%>
</div>
<jsp:include page="footer.jsp"></jsp:include>