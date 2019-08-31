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
			action="${pageContext.request.contextPath}/user/bookslot">

			<div class="field">
				<label class="label">Issue</label>
				<div class="control">
					<input class="input" type="text" placeholder="Issue Title" name="title">
				</div>
			</div>

			<div class="field">
				<label class="label">Category</label>
				<div class="control">
					<div class="select">
						<select name="category">
							<option value="Savings Account">Savings Account</option>
							<option value="Current Account">Current Account</option>
							<option value="Pension Account">Pension Account</option>
							<option value="Fund Transfer">Fund Transfer</option>
							<option value="Loan">Loan</option>
							<option value="Locker">Locker</option>
							<option value="Grievance Addressal">Grievance Addressal</option>
							<option value="Branch Related">Branch Related</option>
						</select>
					</div>
				</div>
			</div>

			<div class="field">
				<label class="label">Description</label>
				<div class="control">
					<textarea class="textarea" placeholder="Textarea" name="desc"></textarea>
				</div>
			</div>

			<div class="field">
				<label class="label">Select Date & Time of Visit</label>
				<div class="control">
					<input type="datetime-local" id="date" name="date">
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