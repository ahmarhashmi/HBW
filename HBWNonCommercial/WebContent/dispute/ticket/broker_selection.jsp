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
<%-- <script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script> --%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/jquery-1.12.4.min.js"></script>

<script type="text/javascript">
	function toggleCheckBox(obj) {

		if (obj.id == "broker" && obj.checked == true) {
			document.getElementById("notABroker").checked = false;
			document.getElementById("continueButton").disabled = false;
		} else if (obj.id == "notABroker" && obj.checked == true) {
			document.getElementById("broker").checked = false;
			document.getElementById("continueButton").disabled = false;
		}
		disableButton();
	}

	function disableButton() {
		if (document.getElementById("broker").checked == false
				&& document.getElementById("notABroker").checked == false) {
			document.getElementById("continueButton").disabled = true;
		}
	}

	$(document)
			.ready(
					function() {
						disableButton();
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

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body
	onload="document.getElementById('continueButton').disabled = true;">
	<jsp:include page="header.jsp" />
	<div class="content-holder">

		<div class="container">
			<div class="row">
				<div class="col-sm-9 col-sm-offset-3">
					<h1>Dispute a Ticket</h1>
					<h4>Select one:</h4>
					<s:form method="post" action="broker_decision" namespace="/dispute"
						theme="simple">
						<div class="checkbox">
							<label> <s:checkbox name="notABroker" id="notABroker"
									onchange="toggleCheckBox(this);">
								</s:checkbox> I own or operate the ticketed vehicle or am legally authorized
								by the vehicle’s owner to dispute the ticket.
							</label>
						</div>
						<div class="checkbox">
							<label> <s:checkbox name="broker" id="broker"
									onchange="toggleCheckBox(this);">
								</s:checkbox> I am a broker disputing a ticket on behalf of a client.
								(Brokers will be redirected to another page to dispute their
								tickets.)
							</label>
						</div>
						<div class="clearfix gap"></div>
						<div class="form-group">
							<s:submit class="btn btn-primary themebluebutton"
								value="Continue" id="continueButton"></s:submit>
						</div>
					</s:form>
				</div>
			</div>
		</div>

	</div>

	<jsp:include page="footer.jsp" />

	<!-- Latest compiled and minified JavaScript -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
		integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
		crossorigin="anonymous"></script>
</body>
</html>