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

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<script type="text/javascript">
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
</script>
</head>
<body
	onload="enableDisableSearchButton(document.getElementById('violationNumber'))">

<jsp:include page="header.jsp" />
<div class="content-holder">
<div class="container">
<div class="row">
<div class="col-sm-9 col-sm-offset-3">
<h1>Dispute a Ticket</h1>
<h3>Enter Violation Number</h3>
<p>Please enter the parking or camera violation number that appears
on your ticket or notice of liability (NOL). On the next page, you will
be able to submit your defense and supporting evidence.</p>

<div class="form-group row "><s:form method="post"
	action="searchViolation" namespace="/dispute" theme="simple"
	onsubmit="return validate();">
	<div class="col-xs-3" style="width: 175px"><s:textfield
		type="tel" id="violationNumber" autocorrect="off" style="width:150px"
		name="violationNumber" label="violationNumber" required="true"
		maxlength="11" minlength="10" class="form-control"
		onchange="enableDisableSearchButton(this);"
		onkeyup="enableDisableSearchButton(this);" /> <!--          placeholder="XXXXXXXXXX" -->
	</div>

	<div><s:submit id="searchBtn" class="btn btn-primary"
		value="Search" /></div>
	<div class="container" style="color: red"><s:if
		test="hasActionErrors()">
		<div class="errors"><s:actionerror /></div>
	</s:if></div>
	<div class="container" id="errorDiv" style="display: none;"><output
		style="color: red;" id="errorMessage"></output></div>
</s:form></div>
<a href="#Content"><b>What is my violation number?</b></a>
<hr>
<h4>Requesting a Hearing</h4>
<p>To avoid penalties, request a hearing to dispute your violation
within 30 days of your violation or notice of liability date. After you
submit the form and any supporting evidence, a judge will review your
case.</p>
<p>If your violation is in judgment, you may request a hearing up to
one year after the judgment date— but if the judge finds you guilty, you
may be required to pay all outstanding penalties.</p>
<hr>
<h4 id="Content">What is my violation number?</h4>
<p>Your violation or notice number is a 10-digit number that appears
on your ticket or notice of liability (NOL).</p>
<p>View an example:</p>
<ul>
	<li><a href="javascript:openTicketPopUp()">Parking Ticket</a></li>
	<li><a href="javascript:openRedlightTicketPopup()">Red Light
	Violation (NOL)</a></li>
	<li><a href="javascript:openBusLaneCameraViolationPopup()">Bus
	Lane Camera Violation (NOL)</a></li>
	<li><a href="javascript:openSpeedCameraViolationPopup()">Speed
	Camera Violation (NOL)</a></li>
</ul>
<hr>
</div>
</div>
</div>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>