<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/dropzone-struts2.js"></script>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/dropzone.css" />

<script type="text/javascript">
	function validate() {
		return false;
	}
</script>
</head>
<body>
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

	<s:form method="post" action="create_hearing" namespace="/Broker">
		<table>
			<tr>
				<td>
					<h3>Enter Defense</h3>
					<s:actionerror/> 
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
				<td>
					<%-- <s:select label="State/Province" value="state" name="state" labelposition="top"
						headerValue="--- Please Select ---" headerKey="1" 
						list="#{'newYork':'New York','california':'California'}" /> --%> <s:select
						label="State/Province" value="state" name="state"
						labelposition="top" headerValue="--- Please Select ---"
						headerKey="1" list="states" />
				</td>
			</tr>

			<tr>
				<td><s:textfield name="zip" label="ZIP/Postal Code"
						labelposition="top" maxlength="10" requiredLabel="true" requiredPosition="top" /></td>
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
	<s:form action="/multiUploadFile" method="post" class="dropzone"
		id="my-awesome-dropzone" namespace="/upload">

	</s:form>
		<s:checkbox name="affirm" fieldValue="true" id="affirm"
			label="I affirm"></s:checkbox>
</body>
</html>
