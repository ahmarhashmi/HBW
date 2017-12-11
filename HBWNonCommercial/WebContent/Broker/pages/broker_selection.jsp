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

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
</head>
<body onload="document.getElementById('continueButton').disabled = true;">
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
	<div class="content-holder">

		<div class="container">
			<div class="row">
				<div class="col-sm-9 col-sm-offset-3">
					<h1>Dispute a Ticket</h1>
					<!-- <h3>Are you a Broker?</h3>
					<p>You may use this website if you are not a Broker requesting
						a hearing for a commercially-plated vehicle. Select the "I AM NOT"
						checkbox below and continue on to request a hearing.</p>
					<h4>What is a Broker?</h4>
					<p>A Broker means a person, who is not the owner or operator of
						the summonsed vehicle, who requests a hearing three or more times
						in a six month period, on behalf of another person or ﬁrm, and is
						not an employee of that person or ﬁrm.</p>
					<h4>Request a hearing for a commercially-plated vehicle</h4>
					<p>Brokers should use one of the applications speciﬁcally set
						up to accommodate commercial customers. Select the "I AM" checkbox
						below and you will be redirected to the commercial use page on the
						Finance website.</p> -->
					<h4>Select one:</h4>
					<s:form method="post" action="broker_decision" namespace="/Broker"
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
					<div class="footerlogoholder">
						<img
							src="${pageContext.request.contextPath}/images/footer-logo.png" />
					</div>
					City of New York. 2016 All Rights Reserved, Nyc is a TradeMark and
					service mark of the City of New York<br /> <a href="#">Privacy
						pollicy</a>. <a href="#">Terms of Use</a>.
				</div>
			</div>
		</div>
	</footer>

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