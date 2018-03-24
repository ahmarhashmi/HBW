<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Dispute a Ticket</title>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/jquery-1.12.4.min.js"></script>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/header.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/common.js"></script>
<script>
	$(document)
			.ready(
					function() {
						$('#google_translate_element')
								.bind(
										'DOMNodeInserted',
										function(event) {
											//$('.goog-te-menu-value span:first').html('Translate');
											$(
													'.goog-te-menu-frame.skiptranslate')
													.load(
															function() {
																setTimeout(
																		function() {
																			$(
																					'.goog-te-menu-frame.skiptranslate')
																					.contents()
																					.find(
																							'.goog-te-menu2-item-selected .text')
																					.html(
																							'Translate');
																		}, 100);
															});
										});
					});
</script>
<script type="text/javascript"
	src="http://translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>

<script type="text/javascript">
	function googleTranslateElementInit() {
		new google.translate.TranslateElement({
			pageLanguage : 'en',
			layout : google.translate.TranslateElement.InlineLayout.SIMPLE
		}, 'google_translate_element');
	}

	function showPrintPrview() {
		// Chrome 1+
		var isChrome = !!window.chrome && !!window.chrome.webstore;
		if (!!window.chrome && !!window.chrome.webstore) {
			window.print();
		} else {
			var divToPrint = document.getElementById('containerDiv');
			var newWin = window.open();
			newWin.document.write(divToPrint.innerHTML);
			newWin.document.close();
			newWin.focus();
			newWin.print();
			newWin.close();
		}
	}
</script>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="content-holder">

		<div class="container" id="containerDiv">
			<div class="row">
				<div class="col-sm-9 col-sm-offset-3">
					<h1>Dispute a Ticket</h1>
					<h2>Hearing Request Received</h2>
					<p>
						Your hearing request has been received. A confirmation email
						containing the information you submitted will be sent to your
						email address. Print and save a copy of the email or make a
						screenshot of this page for your records.<br> <br> An
						Administrative Law Judge’s decision will be sent to the email
						address you supplied in about 75 days.<b> Please make sure to
							add NYCServ@finance.nyc.gov to your address book</b> and keep the
						email for your records.<br> <br> Your violation number
						is:<b> <s:property value="violationNumber" /></b><br> <br>
						If you have any questions or need assistance, you may call or
						visit your local <a
							href="javascript:openPopUpWindow('http://www1.nyc.gov/site/finance/about/contact-us-by-visit.page','FAQPopUp','800','850','no','yes','yes','yes');">Department
							of Finance Business Center</a> and provide your violation number.
					</p>

					<h3>Hearing Request Summary</h3>
					<table class="table-responsive table table-striped">
						<thead>
							<tr>
								<th class="bg-primary" colspan="2">General</th>


							</tr>
						</thead>
						<tr>
							<td>Date and time of hearing request:</td>
							<td><s:property value="requestDate" /> <!-- 09/18/2017 02:10PM --></td>
						</tr>
						<tr>
							<td>Name:</td>
							<td><s:property value="firstName" /> <s:property value="middleName" /> 
							 <s:property value="lastName" /></td>
						</tr>
						<tr>
							<td>Address:</td>
							<td><s:property value="address" /> <s:property
									value="address2" /> <s:property value="city" /> <s:property
									value="state" /> <s:property value="zip" /></td>
						</tr>
						<tr>
							<td>Email Address:</td>
							<td><s:property value="email1" /></td>
						</tr>
					</table>
					<table class="table-responsive table table-striped">
						<thead>
							<tr>
								<th class="bg-primary" colspan="2">Violation Details</th>
							</tr>
						</thead>
						<tr>
							<td>Plate:</td>
							<td><s:property value="violationInfo.vehiclePlate" /></td>
						</tr>
						<tr>
							<td>Violation:</td>
							<td><s:property value="violationNumber" /></td>
						</tr>

						<tr>
							<td>Issued on:</td>
							<td><s:property value="violationInfo.issuedOn" /></td>
						</tr>
						<tr>
							<td>Description:</td>
							<td><s:property value="violationInfo.description" /></td>
						</tr>
						<tr>
							<td>Amount Due:</td>
							<td>$<s:property value="violationInfo.balanceDue" />
							</td>
						</tr>
					</table>
					<s:set name="webViolationInSystem" value="violationInSystem" />
					<s:set name="webViolationInJudgment"
						value="violationInfo.violationStatusInJudgment" />
					<table class="table-responsive table table-striped">
						<thead>
							<tr>
								<th class="bg-primary" colspan="2">Defense Summary</th>

							</tr>
						</thead>
						<s:if test="%{#webViolationInSystem and #webViolationInJudgment}">
							<tr>
								<td>
									<p>
										<b>Explain why you not previously responded to this
											request.</b>
									</p> <br>
									<p>
										<s:property value="explainWhy" />
									</p>
								</td>
							</tr>
						</s:if>
						<tr>
							<td>
								<p><b>Your statement as to why you believe the violation should
									be dismissed.</b></p> <br>
								<p>
									<s:property value="defense" />
								</p>
							</td>
						</tr>
					</table>
					<s:if test="%{!affirm}">
						<table width="100%">
							<tr>
								<th>Uploaded Files</th>
							</tr>

							<tr>
								<th>File Name</th>
								<th>Page Count</th>
								<th>File Size</th>
							</tr>
							<s:iterator value="files">
								<tr>
									<td><s:property value="fileName" /></td>
									<td align="center"><s:property value="pageCount" /></td>
									<td align="center"><s:property value="fileSize" />&nbsp;KB</td>
								</tr>
							</s:iterator>
						</table>
					</s:if>
					<br>

					<div class="final_stage">
						<button class="btn btn-primary" onclick="showPrintPrview();">Print</button>
						&nbsp;&nbsp;&nbsp; <input class="btn btn-link" type="button"
							value="Return to Home Page" onclick="startAfresh();">
					</div>

					<h3>Download the NYC Parking Ticket Pay or Dispute App</h3>
					<p>“NYC Parking Ticket Pay or Dispute” is New York City’s
						official mobile app to securely pay or dispute parking and camera
						violations. You will be able to do the following:</p>
					<ul>
						<li>Pay tickets by credit card, through your bank account or
							Apple Pay.</li>
						<li>Dispute a ticket immediately using your device’s camera
							to upload evidence.</li>
						<li>Search ticket(s) using violation or license plate
							numbers.</li>
						<li>Save tickets you have previously searched for.</li>
						<li>Save license plate and billing information on your
							device.</li>
						<li>View payment history by ticket or license plate numbers.</li>
					</ul>
					<div class="clearfix gap"></div>
					<a class="hideprint" href="https://appsto.re/us/FreAhb.i"
						target="_blank"> <img
						src="${pageContext.request.contextPath}/images/appstore.png" />
					</a> <a class="hideprint" href="https://goo.gl/46fVdW" target="_blank">
						<img src="${pageContext.request.contextPath}/images/playstore.png" />
					</a>

				</div>
			</div>
		</div>

	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>