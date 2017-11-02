<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/dropzone.js"></script>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/dropzone.css" />
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
		<s:property value="violationNumber"/>
	</p>

	<s:form method="post" action="create_hearing" valuespace="/Broker">
		<table>
			<tr>
				<td>
					<h3>Enter Defense</h3>
				</td>
			</tr>
			<tr>
				<td><s:property value="defense" /></td>
			</tr>
			<tr>
				<td><s:property value="firstName" /></td>
			</tr>
			<tr>
				<td><s:property value="middleName" /></td>
			</tr>
			<tr>
				<td><s:property value="lastName" /></td>
			</tr>

			<tr>
				<td><s:property value="address" /></td>
			</tr>

			<tr>
				<td><s:property value="address2" /></td>
			</tr>

			<tr>
				<td><s:property value="city" /></td>
			</tr>

			<tr>
				<td><s:property value="state" /></td>
			</tr>

			<tr>
				<td><s:property value="zip" /></td>
			</tr>

			<tr>
				<td><s:property value="email1" /></td>
			</tr>

			<tr>
				<td><s:property value="email2" /></td>
			</tr>
			<tr>
				<td><s:submit value="Print" name="submit" /></td>
			</tr>
		</table>
	</s:form>

</body>
</html>
