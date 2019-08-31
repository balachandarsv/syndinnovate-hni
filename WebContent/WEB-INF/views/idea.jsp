<jsp:include page="header.jsp"></jsp:include>
<section>
	<div class="main-container">
		<div class="article-title">Developing automated system for
			Identification & Triggering alert when a High Net Worth customer
			visits the Branch and/or using our Digital Platform like
			Internet/Mobile Banking etc.</div>
		<h4>Introduction</h4>
		<p>The term High net worth individual is used to address
			individuals holding high valued financial asset. Addressing high net
			worth customer’s needs on a priority basis is something that every
			bank strives to do. The aim is to implement a software/solution
			powered by Artificial Intelligence to detect the presence of a high
			net worth customer/top 100 customer, notifying their presence to the
			respected branch head/designated customer service associate at the
			branch and easing out the needs of the branch employee and the
			customer.</p>
		<h4>Implementation plan</h4>
		<p>The aim is to develop a system to identify HNI and top 100
			customers when they enter the branch premises. For this, the power of
			Artificial Intelligence (AI) is harnessed. As per govt norms, since
			each branch holds KYC records which includes the photo of the
			customer, a neural network powered facial recognition system is used.
			This AI powered facial recognition system (which works for even low
			resolution photos of 5KB onwards) will be capable of identifying a
			person given his/her photo. Most of the banks today are CCTV powered.
			The entrance CCTV camera footage stream will be fed to the server
			which processes the video using various video processing techniques.
			From the video stream , the photo of the person entering is captured
			which is fed to the face recognition system. The facial recognition
			system identifies the person and verifies if the person is a HNI
			customer/top 100 customer against the list of HNI customers/top 100
			customers. If they turn out to be either of them, an alert is
			triggered to the designated person at the branch. This whole pipeline
			is done in real time. Also, a provisional tab is introduced in the
			Internet-banking portal where the customer can schedule an
			appointment with the designated branch. The customer can enter the
			purpose of the visit in the portal. The system will trigger an alert
			to the branch head/designated associate 20 minutes prior to the
			scheduled slot along with details filled in by the customer for
			better addressal of customer needs . Putting these two solutions
			together will ensure that identification and serving of HNI & top 100
			customers are done with minimum/no delay.</p>

		<h4>Tech & Domain involved</h4>
		<p>Artificial Intelligence (Neural networks), Java, Javascript,
			Python</p>
	</div>
</section>
<jsp:include page="footer.jsp"></jsp:include>