<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<!-- saved from url=(0057)file:///C:/Users/haseeb.rashid/Desktop/HBW/Violation.html -->
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

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="topbar">
		<div class="container">
			<div class="pull-left">
				<ul class="topmenu">
					<li><a><img
							src="${pageContext.request.contextPath}/images/top-logo.png"></a></li>
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
				<img src="${pageContext.request.contextPath}/images/main-logo.png">
			</div>
			<div class="pull-right">
				<ul class="nav nav-pills">
					<li><a
						href="file:///C:/Users/haseeb.rashid/Desktop/HBW/Violation.html#">Italiano
							<i class="fa fa-caret-right"></i>
					</a></li>
					<li class="dropdown"><a
						href="file:///C:/Users/haseeb.rashid/Desktop/HBW/Violation.html#"
						data-toggle="dropdown" class="dropdown-toggle">Translate<strong
							class="caret"></strong></a>
						<ul class="dropdown-menu">
							<li><a
								href="file:///C:/Users/haseeb.rashid/Desktop/HBW/Violation.html#">Action</a>
							</li>
						</ul></li>
					<li class="dropdown"><a
						href="file:///C:/Users/haseeb.rashid/Desktop/HBW/Violation.html#"
						data-toggle="dropdown" class="dropdown-toggle">Text Size<strong
							class="caret"></strong></a>
						<ul class="dropdown-menu">
							<li><a
								href="file:///C:/Users/haseeb.rashid/Desktop/HBW/Violation.html#">Action</a>
							</li>
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
					<h2>Hearing Request Received</h2>
					<p>
						Your hearing request has been received. A confirmation email
						containing the information you submitted will be sent to your
						email address. Print and save a copy of the email or make a
						screenshot of this page for your records.<br>
						<br> An Administrative Law Judge’s decision will be sent to
						the email address you supplied in about 75 days.<b> Please
							make sure to add NYCServ@finance.nyc.gov to your address book</b> and
						keep the email for your records.<br>
						<br> Your violation number is:<b> <s:property
								value="violationNumber" /></b><br>
						<br> If you have any questions or need assistance, you may
						call or visit your local <a href="Business Center">Business
							Center</a> and provide your violation number.
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
							<td><s:property value="requestDate" />
								<!-- 09/18/2017 02:10PM --></td>
						</tr>
						<tr>
							<td>Name:</td>
							<td><s:property value="firstName" /> <s:property
									value="lastName" /></td>
						</tr>
						<tr>
							<td>Address:</td>
							<td><s:property value="address" /> <s:property
									value="address2" /></td>
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
					<table class="table-responsive table table-striped">
						<thead>
							<tr>
								<th class="bg-primary" colspan="2">Defense Summary</th>

							</tr>
						</thead>
						<tr>
							<td>
								<p>Your statement as to why you believe the violation should
									be dismissed.</p>
								<br>
								<p>
									<s:property value="defense" />
								</p>
							</td>
						</tr>
					</table>
					<table width="100%">
						<tr>

							<th>Uploaded Files</th>

						</tr>
						<br>

						<tr>

							<th>File Name</th>


							<th>Page Count</th>


							<th>File Size</th>

						</tr>
						<br>
						<tr>
							<td>03947156.jpg</td>
							<td>1</td>
							<td>500.00 KB</td>
						</tr>
						<br>
						<tr>
							<td>ansfgxnbfsxfvhaqw.jpg</td>
							<td>1</td>
							<td>250.00 KB</td>
						</tr>
						<br>
						<tr>
							<td>9073684.jpg</td>
							<td>1</td>
							<td>200.00 KB</td>
						</tr>
					</table>
					<br>

					<div>

						<button class="btn btn-primary" onclick="window.print();">Print</button>
						&nbsp;&nbsp;&nbsp; 
						<a href="nyc.gov/finance">Return to Home Page</a>
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
					<a href="https://appsto.re/us/FreAhb.i" target="_blank"> <img
						src="${pageContext.request.contextPath}/images/appstore.png" />
					</a> <a href="https://goo.gl/46fVdW" target="_blank"> <img
						src="${pageContext.request.contextPath}/images/playstore.png" />
					</a>

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
					City of New York. 2016 All Rights Reserved,<br>Nyc is a
					TradeMark and service mark of the City<br>of New York<br>
					<a
						href="file:///C:/Users/haseeb.rashid/Desktop/HBW/Violation.html#">Privacy
						pollicy</a>. <a
						href="file:///C:/Users/haseeb.rashid/Desktop/HBW/Violation.html#">Terms
						of Use</a>.
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