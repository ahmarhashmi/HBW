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

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/dropzone.css" />

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

<%-- <script src="https://rawgit.com/enyo/dropzone/master/dist/dropzone.js"></script>
<link rel="stylesheet" href="https://rawgit.com/enyo/dropzone/master/dist/dropzone.css"> --%>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<script type="text/javascript">
	$(document).submit(function(event){
	    $(window).unbind('beforeunload');
	});

	$(window).on('beforeunload', function(event) {
			return "asdf";
	});

</script>
</head>
<body>
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
					<h3>Enter Defense & Evidence</h3>
					<p>Use this form to plead “Not Guilty” to your violation and
						request a hearing. After you submit the form and any evidence you
						have, a judge will review the case. The judge's decision will be
						emailed in about 75 days.</p>
					<hr>
					<h4>Violation</h4>
					<div class="row">
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
						<div class="col-sm-8">
							<table class="table-responsive table table-striped">
								<thead>
									<tr>
										<th class="bg-primary" colspan="2">Violation Details <small>(as
												of Wednesday, Sep 6, 2017 03:15 PM)</small>
										</th>
									</tr>
								</thead>
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
									<td>Code:</td>
									<td><s:property value="violationInfo.code" /></td>
								</tr>
								<tr>
									<td>Location:</td>
									<td><s:property value="violationInfo.location" /></td>
								</tr>
							</table>
						</div>
					</div>
					<div class="clearfix gap"></div>
					<div class="row">
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

					</div>



					<p>If you need a copy of a Notice of Liability, you need to
						contact us; NOL copies are not available online.</p>
					<hr>
					<h4>Enter Defense</h4>
					<p>Explain why you believe the violation should be dismissed.</p>
					<div class="form-group">
						<s:textarea name="defense" maxlength="32700" class="form-control"
							rows="10" cols="30" style="width: 500px; height:150px"
							labelposition="top" />

					</div>
					<hr>
					<h4>Upload Evidence</h4>
					<p>If you have evidence you want the judge to consider, upload
						it below. Evidence can include, but is not limited to: testimony,
						photographs, repair bills, police reports, DMV or insurance
						company records, and towing bills.</p>
					<p>This is the only opportunity you will have to upload
						evidence for your hearing. Please make sure that all the evidence
						you wish to present has been uploaded before you submit your
						request.</p>
					<p>Acceptable file formats are: PDF, JPEG/JPG, TIFF, BMP, and
						non-animated GIFs.</p>
					<p>
						The total limit for uploaded evidence is 20MB and the maximum page
						count is 50 pages. If your evidence exceeds either the file size
						or the page count, do not ask for a hearing online. Submit your
						hearing request and evidence by <a href="">mail</a> or in <a
							href="">person</a> at a Finance Business Center.
					</p>
					<%-- <form id="file-Upload-Form" enctype="multipart/form-data"
						action="<%=request.getContextPath()%>/FileUploadServlet"
						class="dropzone" method="POST"></form> --%>
					<s:form id="file-Upload-Form" enctype="multipart/form-data"
						action="/FileUploadServlet" method="POST">
						<div class="dropzone" id="my-dropzone" name="mainFileUploader">
						</div>
					</s:form>
					<br>
					<script>
					Dropzone.options.myDropzone = {
							maxFilesize : 20, // MB
							acceptedFiles : "image/pjpeg,image/jpeg,image/jpg,image/tiff,image/bmp,application/pdf",
							addRemoveLinks : true,
				            url: "<%=request.getContextPath()%>/FileUploadServlet",
							autoProcessQueue : false,
							uploadMultiple : true,
							parallelUploads : 100,
							maxFiles : 100,

							init : function() {

								var submitButton = document
										.querySelector("#submit-all");
								var wrapperThis = this;

								/* submitButton.addEventListener("click", function () {
								    wrapperThis.processQueue();
								}); */

								this
										.on(
												"addedfile",
												function(file) {

													// Create the remove button
													var removeButton = Dropzone
															.createElement("<button class='btn btn-lg dark'>Remove File</button>");

													// Listen to the click event
													removeButton
															.addEventListener(
																	"click",
																	function(e) {
																		// Make sure the button click doesn't submit the form:
																		e
																				.preventDefault();
																		e
																				.stopPropagation();

																		// Remove the file preview.
																		wrapperThis
																				.removeFile(file);
																		// If you want to the delete the file on the server as well,
																		// you can do the AJAX request here.
																	});

													// Add the button to the file preview element.
													file.previewElement
															.appendChild(removeButton);
												});

								this.on('sendingmultiple', function(data, xhr,
										formData) {
									formData.append("Username", $("#Username")
											.val());
								});
							}
						};
						/* Dropzone.prototype.defaultOptions.dictDefaultMessage = "";
						Dropzone.options.fileUploadForm = {
							maxFilesize : 20, // MB
							acceptedFiles : "image/pjpeg,image/jpeg,image/jpg,image/tiff,image/bmp,application/pdf",
							addRemoveLinks : true */
						//init: function() {
						//this.on("addedfile", function(file) {
						//document.getElementById("affirmCheckBox").style.display =  "none";
						// Create the remove button
						/* var removeButton = Dropzone.createElement("<button>Remove file</button>");	        

						// Capture the Dropzone instance as closure.
						var _this = this;

						// Listen to the click event
						removeButton.addEventListener("click", function(e) {
						  // Make sure the button click doesn't submit the form:
						  e.preventDefault();
						  e.stopPropagation();

						  // Remove the file preview.
						  _this.removeFile(file);
						  // If you want to the delete the file on the server as well,
						  // you can do the AJAX request here.
						});

						// Add the button to the file preview element.
						file.previewElement.appendChild(removeButton); */
						//});
						//}
						//}
					</script>
					<style type="text/css">
/* *****CHAMP DROPZONE***** */
.dropzone {
	border: 1px dashed #cec7c7;
	/*min-height: 360px;*/
	height: 165px;
	width: 700px;
	-webkit-border-radius: 3px;
	border-radius: 3px;
	background: #f3f3f3;
	background-image:
		url("${pageContext.request.contextPath}/images/drag.png");
	background-repeat: no-repeat;
	padding: 0px;
	margin: 0px;
}
</style>

					<p>You must check the box below if you are not uploading
						evidence. By checking this box, you agree to the following:</p>
					<s:form method="post" action="create_hearing" namespace="/Broker"
						theme="simple" id="mainForm">
						<p>
						<div class="dottedborderdiv"></div>
						<div class="checkbox" id="affirmCheckBox">
							<label> <input type="checkbox"> I affirm that I
								am not uploading evidence for the judge to consider. I
								understand that this is the only opportunity I will have to
								upload evidence for my hearing.
							</label>
						</div>
						</p>
						<h4>Enter Contact Information</h4>
						<p>Enter your name and the address where the hearing decision
							should be mailed. A hearing request confirmation will be sent to
							your email address.</p>
						<div class="form-group row">
							<div class="form-group">
								<label>First Name</label>
								<s:textfield name="firstName" label="First Name"
									class="form-control" labelposition="top" maxlength="30" />
							</div>
							<div class="form-group">
								<label>Middle Initial (Optional)</label>
								<s:textfield name="middleName" label="Middle Initial"
									class="form-control" labelposition="top" maxlength="1" />
							</div>
							<div class="form-group">
								<label>Last Name</label>
								<s:textfield name="lastName" label="Last Name"
									class="form-control" labelposition="top" maxlength="30" />
							</div>

							<div class="form-group">
								<label>Address</label>
								<s:textfield name="address" label="Address" class="form-control"
									labelposition="top" maxlength="50" />
							</div>
							<div class="form-group">
								<label>Address Line 2 (Optional)</label>
								<s:textfield name="address2" maxlength="50" class="form-control"
									label="Address Line 2 (optional)" labelposition="top" />
							</div>
							<div class="form-group">
								<label>City</label>
								<s:textfield name="city" label="City" labelposition="top"
									class="form-control" maxlength="50" />
							</div>
							<div class="form-group">
								<label>State/Province</label>
								<%-- <s:select label="State/Province" value="state" name="state"
									class="form-control" labelposition="top"
									headerValue="--- Please Select ---" headerKey="1"
									list="#{'newYork':'New York','california':'California'}" /> --%>
								<s:select label="State/Province" value="state" name="state"
									class="form-control" labelposition="top"
									headerValue="--- Please Select ---" headerKey="1" list="states" />
							</div>
							<div class="form-group">
								<label>ZIP/Postal Code</label>
								<s:textfield name="zip" label="ZIP/Postal Code"
									class="form-control" labelposition="top" maxlength="10"
									requiredLabel="true" requiredPosition="top" />
							</div>
							<div class="form-group">
								<label>Email Address</label>
								<s:textfield name="email1" label="Email Address"
									class="form-control" labelposition="top" maxlength="50"
									type="email" />
							</div>
							<div class="form-group">
								<label>Confirm Email Address</label>
								<s:textfield name="email2" label="Confirm Email Address"
									class="form-control" labelposition="top" maxlength="50"
									type="email" />
							</div>


							<p>You must check the box below to submit your hearing
								request. By checking this box, you agree to the following:</p>
							<div class="checkbox">
								<label> <input type="checkbox">I certify that I
									am the person named above or the authorized agent of such a
									person and I am duty authorized to make this affirmation. I
									also affirm that all statements made and information submitted
									herein are true and accurate to the best of my knowledge and
									any attachments are true and correct copies of originals. I
									understand that any willful false statements made herein may
									subject me to the penalties prescribed in the Penal Law.
								</label>

							</div>
						</div>

						<p>Once you submit your request, your hearing will be
							scheduled.</p>
						<div class="form-group">
							<s:submit value="Submit Request" class="btn btn-primary " />
							<a class="btn btn-link " href="#" onclick="location.reload();" >Cancel Request</a>
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
				class="fa fa-paper-plane"></i> Subscribe </a>
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
				</div>
			</div>
		</div>
	</footer>

	<!-- Include all compiled plugins (below), or include individual files as needed -->

	<!-- Latest compiled and minified JavaScript -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
		integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
		crossorigin="anonymous">
		
	</script>
	</ body>
</html>


<!-- 
	// fileUploadForm is the configuration for the element that has an id attribute
	  // with the value my-dropzone (or fileUploadForm)
	  Dropzone.options.fileUploadForm = {
			  maxFilesize: 20, // MB
			  acceptedFiles: "image/pjpeg,image/jpeg,image/jpg,image/tiff,image/bmp,application/pdf",
			  addRemoveLinks: true
	   /*  init: function() {
	      this.on("addedfile", function(file) {
	        // Create the remove button
	        var removeButton = Dropzone.createElement("<button>Remove file</button>");	        

	        // Capture the Dropzone instance as closure.
	        var _this = this;

	        // Listen to the click event
	        removeButton.addEventListener("click", function(e) {
	          // Make sure the button click doesn't submit the form:
	          e.preventDefault();
	          e.stopPropagation();

	          // Remove the file preview.
	          _this.removeFile(file);
	          // If you want to the delete the file on the server as well,
	          // you can do the AJAX request here.
	        });

	        // Add the button to the file preview element.
	        file.previewElement.appendChild(removeButton);
	      });
	    } */
	  };
</script>
</head>
<body onload="onLoadUpdateFileElement()">

	<h1>Dispute a Ticket</h1>
	<h2>Enter Defense and Evidence</h2>
	<h3>Violation</h3>
	<p>The violation number you entered is not yet in our system, but
		you may still request a hearing at this time. Please verify that your
		violation number is correct before completing the form below.</p>
	<p>
		<b>Violation:</b>
		<s:property value="violationNumber" />
	</p>

	<s:form method="post" action="create_hearing" namespace="/Broker" theme="simple">
		<table>
			<tr>
				<td>
					<h3>Enter Defense</h3> <s:actionerror />
				</td>
			</tr>
			<tr>
				<td><s:textarea name="defense" maxlength="32700"
						style="width: 500px; height:150px" labelposition="top"></s:textarea></td>
			</tr>
			<tr>
				<td>
					<h3>Enter Contact Information</h3>
				</td>
			</tr>
			<tr>
				<td><s:textfield name="firstName" label="First Name"
						labelposition="top" maxlength="30" /></td>
			</tr>
			<tr>
				<td><s:textfield name="middleName" label="Middle Initial"
						labelposition="top" maxlength="1" /></td>
			</tr>
			<tr>
				<td><s:textfield name="lastName" label="Last Name"
						labelposition="top" maxlength="30" /></td>
			</tr>

			<tr>
				<td><s:textfield name="address" label="Address"
						labelposition="top" maxlength="50" /></td>
			</tr>

			<tr>
				<td><s:textfield name="address2" maxlength="50"
						label="Address Line 2 (optional)" labelposition="top" /></td>
			</tr>

			<tr>
				<td><s:textfield name="city" label="City" labelposition="top"
						maxlength="50" /></td>
			</tr>

			<tr>
				<td><s:select label="State/Province" value="state" name="state"
						labelposition="top" headerValue="--- Please Select ---"
						headerKey="1"
						list="#{'newYork':'New York','california':'California'}" /> <s:select
						label="State/Province" value="state" name="state"
						labelposition="top" headerValue="--- Please Select ---"
						headerKey="1" list="states" /></td>
			</tr>

			<tr>
				<td><s:textfield name="zip" label="ZIP/Postal Code"
						labelposition="top" maxlength="10" requiredLabel="true"
						requiredPosition="top" /></td>
			</tr>

			<tr>
				<td><s:textfield name="email1" label="Email Address"
						labelposition="top" maxlength="50" type="email" /></td>
			</tr>

			<tr>
				<td><s:textfield name="email2" label="Confirm Email Address"
						labelposition="top" maxlength="50" type="email" /></td>
			</tr>
			<tr>
				<td><s:checkbox name="certify" fieldValue="true" id="certify"
						label="I certify"></s:checkbox></td>
			</tr>
			<tr>
				<td><s:submit value="Continue" name="submit"
						onclick="validate()" /></td>
				<td><s:submit value="Cancel" name="submit" /></td>
			</tr>
		</table>
	</s:form>

	<h3>Upload Evidence</h3>

	<form id="file-Upload-Form" enctype="multipart/form-data"
		action="<%=request.getContextPath()%>/FileUploadServlet"
		class="dropzone" method="POST">

	</form>

</body>
<script type="text/javascript">
	function onLoadUpdateFileElement() {
		/* var x = document.getElementsByClassName("dz-hidden-input");
		var element = x[0];
		//alert(x.length);
		if( x.length == 1 ){
			element.name = "uploads";
			element.id = "my-awesome-dropzone_uploads";
			//alert(element.name);
		} */
	}
	//alert(document.getElementByName("uploads"));
</script>
</html>
 -->