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

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
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
	function toggleCheckBox(obj) {

		if (obj.id == "broker" && obj.checked == true ) {
			document.getElementById("notABroker").checked = false;
			document.getElementById("continueButton").disabled = false;
		} else if (obj.id == "notABroker" && obj.checked == true ){
			document.getElementById("broker").checked = false;
			document.getElementById("continueButton").disabled = false;
		}
		
		if (document.getElementById("broker").checked == false && document.getElementById("notABroker").checked == false ){
			document.getElementById("continueButton").disabled = true;
		}
		
	}
	
	  $(document).ready(function(){
	    $('#google_translate_element').bind('DOMNodeInserted', function(event) {
	      $('.goog-te-menu-value span:first').html('Translate');
	      $('.goog-te-menu-frame.skiptranslate').load(function(){
	        setTimeout(function(){
	          $('.goog-te-menu-frame.skiptranslate').contents().find('.goog-te-menu2-item-selected .text').html('Translate');    
	        }, 100);
	      });
	    });
	  });
	</script>
	<script type="text/javascript" src="http://translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>

	<script type="text/javascript">
	function googleTranslateElementInit() {
	  new google.translate.TranslateElement({pageLanguage: 'en', layout: google.translate.TranslateElement.InlineLayout.SIMPLE}, 'google_translate_element');
	}
	</script>
</head>
<body
	onload="enableDisableSearchButton(document.getElementById('violationNumber'))">
	
	<%--
	<div class="topbar">
		<div class="container">
			<div class="pull-left">
				<ul class="topmenu">
					<li><a><img
							src="${pageContext.request.contextPath}/images/top-logo.png" /></a></li>
					<li><a>Department of Finance</a></li>
				</ul>
			</div>
			<div class="pull-right">
				<ul class="topmenu">
					<li><a>311</a></li>
					<li><a>Search all NYC.giv websites</a></li>
				</ul>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
	<div class="main-menu">
		<div class="container">
			<div class="logo">
				<img src="${pageContext.request.contextPath}/images/main-logo.png" />
			</div>
			<div class="pull-right">
				<ul class="nav nav-pills">
					<li><a href="#">Italiano <i class="fa fa-caret-right"></i></a></li>
					<li class="dropdown"><a href="#" data-toggle="dropdown"
						class="dropdown-toggle">Translate<strong class="caret"></strong></a>
						<ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
						</ul></li>
					<li class="dropdown"><a href="#" data-toggle="dropdown"
						class="dropdown-toggle">Text Size<strong class="caret"></strong></a>
						<ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
						</ul></li>
				</ul>
				<div class="clearfix"></div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
	
	--%>
	
	<jsp:include page="header.jsp"></jsp:include>
	<div class="content-holder">

		<div class="container">
			<div class="row">
				<div class="col-sm-9 col-sm-offset-3">
					<h1>Dispute a Ticket</h1>
					<!--<p>Use the form on the next page to plead "Not Guilty" to your
						violation and request a hearing. After you submit the form and any
						evidence you have, a judge will review the case.</p>-->
					<h3>Enter Violation Number</h3>
					<p>Please enter the parking or camera violation number that
						appears on your ticket or notice of liability (NOL). On the next
						page, you will be able to submit your defense and supporting
						evidence.</p>

					<div class="form-group row ">
						<s:form method="post" action="searchViolation"
							namespace="/dispute" theme="simple" onsubmit="return validate();">
							<div class="col-xs-3">
								<s:textfield type="tel" id="violationNumber" autocorrect="off"
									name="violationNumber" label="violationNumber" required="true"
									maxlength="11" minlength="10" class="form-control"
									placeholder="XXXXXXXXXX"
									onchange="enableDisableSearchButton(this);"
									onkeyup="enableDisableSearchButton(this);" />
							</div>

							<div>
								<s:submit id="searchBtn" class="btn btn-primary" value="Search" />
							</div>
							<div class="container" style="color: red">
								<s:if test="hasActionErrors()">
									<div class="errors">
										<s:actionerror />
									</div>
								</s:if>
							</div>
							<div class="container" id="errorDiv" style="display: none;">
								<output style="color: red;" id="errorMessage"></output>
							</div>
						</s:form>

					</div>
					<a href="#Content"><b>What is my violation number?</b></a>
					<hr>
					<h4>Requesting a Hearing</h4>
					<p>To avoid penalties, request a hearing to dispute your
						violation within 30 days of your violation or notice of liability
						date. After you submit the form and any supporting evidence, a
						judge will review your case.</p>
					<p>If your violation is in judgment, you may request a hearing
						up to one year after the judgment date— but if the judge finds you
						guilty, you may be required to pay all outstanding penalties.</p>
					<!-- <h4>When to Request a Hearing</h4>
					<p>To avoid penalties, request a parking violation hearing
						within 30 days of the violation date and request a Camera
						violation hearing within 30 days of the Notice of Liability (NOL)
						date.</p>
					<br>
					<p>Hearings can be requested up to one year after the judgment
						date. But if the judge finds you guilty, you may also have to pay
						the penalties.</p>
					<h4>Evidence</h4>
					<p>If you have evidence you want the judge to consider
						(photographs, documents, etc.) you will be allowed to submit it
						with your online hearing request form.</p>
					<p>All evidence must be submitted at this time. There will be
						no other opportunity to submit evidence.</p>
					<p>Acceptable file formats are: PDF, JPEG/JPG, TIFF, BMP, and
						non-animated GIFs.</p>
					<p>
						We cannot accept more than 20MB of evidence into our system. If
						your evidence files are larger than 20MB do not ask for a hearing
						online. Submit your hearing request and evidence by <a href=""><b>mail</b></a>
						or in <a href=""><b>person</b></a> at a Finance Business Center.
					</p>
					<h4>After You Request a Hearing</h4>
					<p>When you submit this hearing request, you will get an email
						confirmation. The judge's decision will be emailed in about 75
						days. Please make sure to add NYCServ@finance.nyc.gov to your
						address book.</p>-->
					<hr>
					<h4 id="Content">What is my violation number?</h4>
					<p>Your violation or notice number is a 10-digit number that
						appears on your ticket or notice of liability (NOL).</p>
					<p>View an example:</p>
					<ul>
						<li><a href="javascript:openTicketPopUp()">Parking Ticket</a></li>
						<li><a href="javascript:openRedlightTicketPopup()">Red Light Violation (NOL)</a></li>
						<li><a href="javascript:openBusLaneCameraViolationPopup()">Bus Lane Camera Violation (NOL)</a></li>
						<li><a href="javascript:openSpeedCameraViolationPopup()">Speed Camera Violation (NOL)</a></li>
					</ul>
					<hr>
					<%-- <h3>Download the NYC Parking Ticket Pay or Dispute App</h3>
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
					<a href="https://appsto.re/us/FreAhb.i" target="_blank"> <img
						src="${pageContext.request.contextPath}/images/appstore.png" />
					</a> <a href="https://goo.gl/46fVdW" target="_blank"> <img
						src="${pageContext.request.contextPath}/images/playstore.png" />
					</a>

				</div>
			</div>
		</div>

	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	<%--
	<footer>
		<div class="votensubscripbeholder">
			<a class="buttonssubcriptnvote"><i class="fa fa-check-square"></i>
				Register to Vote </a> <a class="buttonssubcriptnvote"><i
				class="fa fa-paper-plane"></i> Subscripe </a>
		</div>
		<div class="container">
			<div class="row">
				<div class="col-sm-3">
					<a>Drectory of City Agencies</a> <a>Notify NYC</a> <a>NYC
						Mobile Apps</a>
				</div>
				<div class="col-sm-3">
					<a>Contact NYC Government</a> <a>CityStore</a> <a>Maps</a>
				</div>
				<div class="col-sm-3">
					<a>City Employees</a> <a>Stay Connected</a> <a>Resident Toolkit</a>
				</div>
				<div class="col-sm-3">
					City of New York. 2016 All Rights Reserved,<br />Nyc is a
					TradeMark and service mark of the City<br />of New York<br /> <a
						href="#">Privacy pollicy</a>. <a href="#">Terms of Use</a>.
				 --%>
				</div>
			</div>
		</div>
	
	<jsp:include page="footer.jsp"></jsp:include>

	--%>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->

	<!-- Latest compiled and minified JavaScript -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
		integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
		crossorigin="anonymous"></script>
</body>
</html>