<!DOCTYPE html>
<%@page import="com.theaiclub.auth.SecurityConfig"%>
<%@page import="com.theaiclub.auth.AppUtils"%>
<%@page import="com.theaiclub.auth.UserAccount"%>
<html>
<head>
<meta charset="UTF-8">
<title>SYND-Innovate | Syndicate Bank Hackathon</title>
<link rel="stylesheet" type="text/css" href="/Face/assets/css/bulma.css">
<link rel="stylesheet" type="text/css"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="/Face/assets/css/bulma-calendar.min.css">
<link rel="stylesheet" type="text/css"
	href="/Face/assets/css/bulma-switch.min.css">
<link rel="stylesheet" type="text/css" href="/Face/assets/css/style.css">
<script type="text/javascript" src="/Face/assets/js/jquery-2.2.4.min.js"></script>
<script type="text/javascript" src="/Face/assets/js/script.js"></script>
<script type="text/javascript" src="/Face/assets/js/bulma-calendar.js"></script>
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800"
	rel="stylesheet">

<%
	UserAccount account = AppUtils.getLoginedUser(session);
%>
<%
	if (account != null) {
		if (account.getRoles().get(0).equals(SecurityConfig.ROLE_ADMIN)) {
%>
<script type="text/javascript">
	var client = new WebSocketClient('ws', '127.0.0.1', 8080, '/Face/streams');
	client.connect();
</script>
<%
	}
	}
%>

</head>
<body>
	<header>
		<div class="header_top">

			<ul class="header_menu columns">
				<div class="column">
					<li class="clock" id="clock" onload="showTime()"></li>
				</div>
				<div class="column">
					<li><a href="${pageContext.request.contextPath}/idea">Idea</a></li>
					<li><a href="https://github.com/balachandarsv/syndinnovate-hni">Presentation</a></li>
					<li><a href="https://www.youtube.com/watch?v=HFVtKIxehH0">Product Video</a></li>
					<li><a href="https://github.com/balachandarsv/syndinnovate-hni">Code</a></li>
				</div>
			</ul>
		</div>
		<div class="middle_header columns">
			<div class="is-one-quarter column">
				<img src="/Face/assets/images/logo.png">
			</div>
			<div class="column title">
				<h4>Artificial Intelligence powered HNI and top 100 customer
					detection</h4>

				<%
					if (account != null) {
						if (account.getRoles().get(0).equals(SecurityConfig.ROLE_ADMIN)) {
				%><ul class="title_menu header_menu align-left fs16">
					<li><a href="${pageContext.request.contextPath}/admin/home">Home</a></li>
					<li><a href="${pageContext.request.contextPath}/admin/users">Users</a></li>
					<li><a href="${pageContext.request.contextPath}/admin/camera">Camera</a></li>
					<li><a href="${pageContext.request.contextPath}/admin/request">Requests</a></li>
					<li><a href="${pageContext.request.contextPath}/admin/history">Predictions
							History</a></li>
					<li><a
						href="${pageContext.request.contextPath}/admin/blacklist">Blacklist</a></li>
					<li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
				</ul>
				<%
					} else if (account.getRoles().get(0).equals(SecurityConfig.ROLE_USER_NORMAL)
								|| account.getRoles().get(0).equals(SecurityConfig.ROLE_USER_HNI)) {
				%><ul class="title_menu header_menu align-left fs16">
					<li><a href="${pageContext.request.contextPath}/user/home">Home</a></li>
					<%
						if (account.getRoles().get(0).equals(SecurityConfig.ROLE_USER_HNI)) {
					%>
					<li><a href="${pageContext.request.contextPath}/user/book"><span
							class="blink">Online Request</span></a></li>
					<%
						}
					%>
					<li><a href="${pageContext.request.contextPath}/user/kyc">Complete
							your KYC</a></li>
					<li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
				</ul>
				<%
					}
					}
				%>

			</div>
		</div>
		<div class="header-bottom clear"></div>
		<div class="notify">
		</div>
	</header>