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
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/dropzone-struts2.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/enter_defense.js"></script>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/dropzone.css" />

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/header.css">
<%-- <script src="https://rawgit.com/enyo/dropzone/master/dist/dropzone.js"></script>
<link rel="stylesheet" href="https://rawgit.com/enyo/dropzone/master/dist/dropzone.css"> --%>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<script type="text/javascript">
	function showHideLoadingDiv(isShow) {
		var loadingDiv = document.getElementById("loadingDiv");
		alert(loadingDiv.style.display);
		if (isShow) {
			loadingDiv.style.display = "block";
			//$('#mainForm').submit();
		} else {
			loadingDiv.style.display = "none";
		}
		alert(loadingDiv.style.display);
	}

	$(document)
			.submit(
					function(event) {
						var isValid = true;

						//$(':input[type="submit"]').prop('disabled', true);
						// perform validations
						if (!$('#certify')[0].checked) {
							$('#notCertified').css("color", "red");
							isValid = false;
						} else {
							$('#notCertified').css("color", "black");
						}
						if (!$('#firstName').val()) {
							$('#firstNameMsg').css("display", "block");
							isValid = false;
						} else {
							$('#firstNameMsg').css("display", "none");
						}
						if (!$('#lastName').val()) {
							$('#lastNameMsg').css("display", "block");
							isValid = false;
						} else {
							$('#lastNameMsg').css("display", "none");
						}
						if (!$('#address').val()) {
							$('#addressMsg').css("display", "block");
							isValid = false;
						} else {
							$('#addressMsg').css("display", "none");
						}
						if (!$('#city').val()) {
							$('#cityMsg').css("display", "block");
							isValid = false;
						} else {
							$('#cityMsg').css("display", "none");
						}
						if ($('#state').val() == 1) {
							$('#stateMsg').css("display", "block");
							isValid = false;
						} else {
							$('#stateMsg').css("display", "none");
						}
						if (!$('#zip').val()) {
							$('#zipMsg').css("display", "block");
							isValid = false;
						} else {
							$('#zipMsg').css("display", "none");
						}
						if (!$('#email1').val()) {
							$('#email1Msg').css("display", "block");
							isValid = false;
						} else {
							$('#email1Msg').css("display", "none");
						}
						if (!$('#email2').val()) {
							$('#email2Msg').css("display", "block");
							isValid = false;
						} else {
							$('#email2Msg').css("display", "none");
						}
						if ($('#email1').val() != $('#email2').val()) {
							$('#emailMatchMsg').css("display", "block");
							isValid = false;
						} else {
							$('#emailMatchMsg').css("display", "none");
						}
						if ($('#affirm').is(":visible")
								&& !$('#affirm')[0].checked) {
							$('#affirmMsg').css("color", "red");
							isValid = false;
						} else {
							$('#affirmMsg').css("color", "black");
						}

						if (!isValid) {
							//$("#submitBtn").removeAttr('disabled');
							//$(':input[type="submit"]').prop('disabled', false);
							$("#submitBtn").css("display", "block");
							$("#submitBtnDisabled").css("display", "none");
							return false;
						}

						/** Checking virus scan after all the validations are successful so that 
						 * user has not to wait for virus scan on every submit
						 */
						if (!$('#affirm').is(":visible")) {
							//loadingDiv.style.display = "block";
							var infectedFiles = isAllUploadedFilesClean();
							if (infectedFiles.length > 0) {
								if (confirm("("
										+ infectedFiles.toString()
										+ ") Files are infected and deleted from the server. Do you want to update more?")) {
									//showHideLoadingDiv(false);
									//$("#submitBtn").removeAttr('disabled');
									/* $("#submitBtn").css("display", "block");
									$("#submitBtnDisabled").css("display",
											"none");
									$(':input[type="submit"]').prop('disabled',
											false); */
									return false;
								}
							}
						}
						$(window).unbind('beforeunload');
					});

	$(window).on('beforeunload', function(event) {
		return "";
	});
</script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#submitBtn").attr("disabled", true);
						$("#email2").on("change", function(){
							if( $(this).val() != '' ){
								if ($('#email1').val() != $(this).val()) {
									$('#emailMatchMsg').css("display", "block");
									isValid = false;
								} else {
									$('#emailMatchMsg').css("display", "none");
								}
							}
						});
						$("#affirm").bind('change', enableDisableSubmitButton);
						$("#firstName").bind('blur', enableDisableSubmitButton);
						$("#lastName").bind('blur', enableDisableSubmitButton);
						$("#address").bind('blur', enableDisableSubmitButton);
						$("#city").bind('blur', enableDisableSubmitButton);
						$("#state").bind('blur', enableDisableSubmitButton);
						$("#zip").bind('blur', enableDisableSubmitButton);
						$("#email1").bind('blur', enableDisableSubmitButton);
						$("#email2").bind('blur', enableDisableSubmitButton);
						$("#certify").bind('change', enableDisableSubmitButton);
						
						$('#google_translate_element')
								.bind(
										'DOMNodeInserted',
										function(event) {
											$('.goog-te-menu-value span:first')
													.html('Translate');
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
<body onload="setViolationNumber();">
	<jsp:include page="header.jsp" />
	<div class="content-holder">

		<div class="container">
			<div class="row">
				<div class="col-sm-9 col-sm-offset-3">
					<h1>Dispute a Ticket</h1>
					<h3>Enter Defense & Evidence</h3>
					<p>Use this form to plead “Not Guilty” to your violation and
						request a hearing. After you submit the form and any evidence you
						have, a judge will review the case. The judge's decision will be
						emailed in about 75 days.</p>
					<hr>
					<h4>Violation</h4>
					<div class="row">
						<s:if test="violationInfo.isVehicleInfoInSystem">
							<div class="col-sm-4">
								<table class="table-responsive table table-striped">
									<thead>
										<tr>
											<th class="bg-primary" colspan="2">Vehicle</th>
										</tr>
									</thead>
									<tr>
										<td>Plate:</td>
										<td><s:property value="violationInfo.vehiclePlate" /></td>
									</tr>
									<tr>
										<td>Type:</td>
										<td><s:property value="violationInfo.vehicleType" /></td>
									</tr>
									<tr>
										<td>State:</td>
										<td><s:property value="violationInfo.vehicleState" /></td>
									</tr>
									<tr>
										<td>Make:</td>
										<td><s:property value="violationInfo.vehicleMake" /></td>
									</tr>
								</table>
							</div>
						</s:if>
						<s:if test="violationInfo.isViolationDetailInSystem">
							<div class="col-sm-8">
								<table class="table-responsive table table-striped">
									<thead>
										<tr>
											<th class="bg-primary" colspan="2">Violation Details <small>(as
													of <s:property value="violationInfo.asOf" />)
											</small> <!-- Wednesday, Sep 6, 2017 03:15 PM -->
											</th>
										</tr>
									</thead>
									<tr>
										<td>Violation:</td>
										<td><span id="violationNumber"><s:property
													value="violationNumber" /></span></td>
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
										<td>Code:</td>
										<td><s:property value="violationInfo.code" /></td>
									</tr>
									<tr>
										<td>Location:</td>
										<td><s:property value="violationInfo.location" /></td>
									</tr>
								</table>
							</div>
						</s:if>
					</div>
					<div class="clearfix gap"></div>
					<div class="row">
						<s:if test="violationInfo.isFeesTableInSystem">
							<div class="col-sm-4">
								<table class="table-responsive table table-striped">
									<thead>
										<tr>
											<th class="bg-primary" colspan="2">Fees</th>
										</tr>
									</thead>
									<tr>
										<td>Fine:</td>
										<td>$<s:property value="violationInfo.fine" /></td>
									</tr>
									<tr>
										<td>Penalty:</td>
										<td><s:property value="violationInfo.penalty" /></td>
									</tr>
									<tr>
										<td>Interest:</td>
										<td><s:property value="violationInfo.interest" /></td>
									</tr>
									<tr>
										<td>Reduction:</td>
										<td><s:property value="violationInfo.reduction" /></td>
									</tr>
									<tr>
										<td>Paid:</td>
										<td><s:property value="violationInfo.paid" /></td>
									</tr>
									<tr class="total">
										<td>Balance Due:</td>
										<td>$<s:property value="violationInfo.balanceDue" /></td>
									</tr>
								</table>
							</div>
						</s:if>
					</div>

					<div class="gap"></div>
					<hr>
					<h4>Enter Defense</h4>
					<p>Explain why you believe the violation should be dismissed.</p>
					<div class="form-group">
						<textarea class="form-control" rows="10" cols="30"
							maxlength="32700" onkeyup="setDefenseValue(this);"></textarea>
						<span id="maxLengthReached" style="color: red; display: none;">Maximum
							length reached. If you want to write more, please do not request
							a hearing online. Submit your hearing request and evidence by
							mail or in person.</span>
					</div>
					<hr>
					<h4>Upload Evidence</h4>
					<p>Use the button below to upload evidence for the judge’s
						consideration. Evidence can include, but is not limited to,
						testimony, photographs, repair bills, police reports, DMV or
						insurance company records, and towing bills. This will be your
						only opportunity to submit evidence.</p>
					<p>Acceptable file formats are: PDF, JPEG/JPG, TIFF, BMP, and
						non-animated GIFs.</p>
					<p>
						The total limit for uploaded evidence is 20MB and the maximum page
						count is 50 pages. If your evidence exceeds either the file size
						or the page count, do not ask for a hearing online. Submit your
						hearing request and evidence by <a href="#">mail</a> or <a
							href="#">in person</a> at a <a href="#">Department of Finance
							Business Center.</a>
					</p>
					<form id="file-upload-form" enctype="multipart/form-data"
						action="<%=request.getContextPath()%>/FileUploadServlet"
						class="dropzone" method="POST"></form>
					<br>


					<p id="affirmCheckBoxPrompt">You must check the box below if
						you are not uploading evidence. By checking this box, you agree to
						the following:</p>
					<s:form method="post" action="create_hearing" namespace="/dispute"
						theme="simple" id="mainForm">

						<s:textfield id="violationHidden" name="violationNumber"
							style="display:none" />
						<div class="dottedborderdiv"></div>
						<div class="checkbox" id="affirmCheckBox">
							<label id="affirmMsg"> <s:checkbox id="affirm"
									name="affirm" /> I affirm that I am not uploading evidence for
								the judge to consider. I understand that this is the only
								opportunity I will have to upload evidence for my hearing.
							</label>
						</div>
						<h4>Enter Contact Information</h4>
						<p>Enter your name and the address to which the hearing
							decision should be mailed. A hearing request confirmation will be
							sent to your email address.</p>

						<s:if test="hasActionErrors()">
							<div class="errors" style="color: red;">
								<s:actionerror />
							</div>
						</s:if>
						<div class="form-group">
							<div style="display: none;">
								<s:textarea id="defenseHidden" name="defense" maxlength="32700"
									class="form-control" rows="10" cols="30"
									style="width: 500px; height:150px" labelposition="top" />
							</div>
							<div class="form-group">
								<label>First Name</label>
								<s:textfield name="firstName" id="firstName" label="First Name"
									class="form-control" labelposition="top" maxlength="30" />
								<span id="firstNameMsg" style="color: red; display: none;">First
									Name is required.</span>
							</div>
							<div class="form-group">
								<label>Middle Initial (Optional)</label>
								<s:textfield name="middleName" label="Middle Initial"
									class="form-control" style="max-width:50px;"
									labelposition="top" maxlength="1" />
							</div>
							<div class="form-group">
								<label>Last Name</label>
								<s:textfield name="lastName" id="lastName" label="Last Name"
									class="form-control" labelposition="top" maxlength="30" />
								<span id="lastNameMsg" style="color: red; display: none;">Last
									Name is required.</span>
							</div>

							<div class="form-group">
								<label>Address</label>
								<s:textfield name="address" id="address" label="Address"
									class="form-control" labelposition="top" maxlength="50" />
								<span id="addressMsg" style="color: red; display: none;">Address
									is required.</span>
							</div>
							<div class="form-group">
								<label>Address Line 2 (Optional)</label>
								<s:textfield name="address2" maxlength="50" class="form-control"
									label="Address Line 2 (optional)" labelposition="top" />
							</div>
							<div class="form-group">
								<label>City</label>
								<s:textfield name="city" id="city" label="City"
									labelposition="top" class="form-control" maxlength="50" />
								<span id="cityMsg" style="color: red; display: none;">City
									is required.</span>
							</div>
							<div class="form-group">
								<label>State/Province</label>
								<s:select label="State/Province" value="state" name="state"
									id="state" name="state" class="form-control"
									style="max-width:250px;" labelposition="top"
									headerValue="--- Please Select ---" headerKey="1" list="states" />
								<span id="stateMsg" style="color: red; display: none;">Please
									select the State/Province.</span>
							</div>
							<div class="form-group">
								<label>ZIP/Postal Code</label>
								<s:textfield name="zip" id="zip" type="tel" label="ZIP/Postal Code"
									class="form-control" style="max-width:250px;"
									labelposition="top" maxlength="10" requiredLabel="true"
									requiredPosition="top" />
								<span id="zipMsg" style="color: red; display: none;">Zip/Postal
									Code is required.</span>
							</div>
							<div class="form-group">
								<label>Email Address</label>
								<s:textfield name="email1" id="email1" label="Email Address"
									class="form-control" labelposition="top" maxlength="50"
									type="email" />
								<span id="email1Msg" style="color: red; display: none;">Email
									is required.</span>
							</div>
							<div class="form-group">
								<label>Confirm Email Address</label>
								<s:textfield name="email2" id="email2"
									label="Confirm Email Address" class="form-control"
									labelposition="top" maxlength="50" type="email" />
								<span id="email2Msg" style="color: red; display: none;">Please
									confirm your email.</span> <span id="emailMatchMsg"
									style="color: red; display: none;">Does not match email address above.</span>
							</div>


							<p id="notCertified">You must check the box below to submit
								your hearing request. By checking this box, you agree to the
								following:</p>
							<div class="checkbox">
								<label> <s:checkbox id="certify" name="certify" />I
									certify that I am the person named above or the authorized
									agent of such person and I am duly authorized to make this
									affirmation. I also affirm that all statements made and
									information submitted herein are true and accurate to the best
									of my knowledge and any attachments are true and correct copies
									of the originals. I understand that any willful false
									statements made herein may subject me to the penalties
									prescribed in the Penal Law.
								</label>

							</div>
						</div>

						<p>Once you submit your request, your hearing will be
							scheduled.</p>
						<div class="form-group">
							<s:submit value="Submit Request" class="btn btn-primary "
								id="submitBtn" />
							<a class="btn btn-link " href="#" onclick="cancelRequest();">Cancel
								Request</a>
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
		crossorigin="anonymous">
		
	</script>

	<!--  -->
	<!-- Dialog box to show the uploaded content to the user -->
	<div id="dialog" title="Basic dialog"
		style="z-index: 10000; display: none">
		<img id="image" src="">
		<div style="width: 100%; height: 100%">
			<iframe id="frame" width="100%" height="100%"></iframe>
		</div>
	</div>

	<div id="loadingDiv" style="display: none;">
		<div class="backdrop"></div>
		<div class="loading_image">
			<img src="${pageContext.request.contextPath}/images/giphy.gif">
			<p>Scanning uploaded documents...</p>
		</div>
	</div>
</body>
</html>
