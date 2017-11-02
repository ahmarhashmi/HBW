<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<body>
<h1>Dispute a Ticket</h1>
<h2>Enter Violation Number<h2>

<s:form method="post" action="searchViolation" namespace="/Broker">
	<s:textfield name="violationNumber" label="violationNumber" maxlength="10"/>
	<s:submit value="Search" name="submit" />
</s:form>
</body>
</html>
