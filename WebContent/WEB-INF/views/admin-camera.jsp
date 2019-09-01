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
			<li><a
				href="${pageContext.request.contextPath}/admin/addUser">Add
					User</a></li>
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
											type : "POST",
											url : "${pageContext.request.contextPath}/admin/camera",
											success : function(result) {
												var result = JSON.parse(result);
												console.log(result);
												var content = "";
												for (var i = 0; i < result.length; i++) {
													content += "<tr><td>"
															+ result[i].id
															+ "</td><td>"
															+ result[i].url
															+ "</td><td>"
															+ "<div class='field'>";
													if (result[i].status == "OFF") {
														content += "<input id='s"+result[i].id+"' type='checkbox' name='camera' class='camera switch'><label for='s"+result[i].id+"'>"
																+ result[i].status
																+ "</label></div>";
													} else {
														content += "<input id='s"+result[i].id+"' type='checkbox' name='camera' class='camera switch' checked='checked'><label for='s"+result[i].id+"'>"
																+ result[i].status
																+ "</label></div>";
													}
													content += "</td></tr>";
												}
												$("#tbody").html(content);
												$(".camera")
														.on(
																'change',
																function(e) {

																	var status = 'OFF';
																	var id = $(
																			this)
																			.attr(
																					"id");
																	if ($(this)
																			.is(
																					':checked')) {
																		status = 'ON';
																		
																			
																		
																	}
																	$
																			.ajax({
																				type : "POST",
																				data : {
																					'id' : id,
																					'val' : status
																				},
																				url : "${pageContext.request.contextPath}/admin/tcamera",
																				success : function(
																						result) {
																					$(
																							"label[for="
																									+ id
																									+ "]")
																							.html(
																									status);
																				}
																			});

																});
											}
										});

							});
		</script>
		

		<table class="table">
			<thead>
				<tr>
					<th><abbr title="CameraId">Id</abbr></th>
					<th>URL</th>
					<th>Status</th>
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
