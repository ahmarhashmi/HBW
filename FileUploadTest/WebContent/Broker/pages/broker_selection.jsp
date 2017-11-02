<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<script type="text/javascript">
	function toggleCheckBox(obj) {

		if (obj.id == "broker") {
			document.getElementById("notABroker").checked = false;
		} else {
			document.getElementById("broker").checked = false;
		}
	}
</script>
</head>
<body>
	<h1>Dispute a Ticket</h1>
	<h2>
		Are you a broker
		<h2>
			<p>You may use this website if you are not a Broker requesting a
				hearing for a commercially-plated vehicle</p>

			<s:form method="post" action="broker_decision" namespace="/Broker">
				<s:checkbox name="broker" fieldValue="false" label="I AM A BROKER"
					id="broker" onchange="toggleCheckBox(this);"></s:checkbox>
				<s:checkbox name="notABroker" fieldValue="true" id="notABroker"
					onchange="toggleCheckBox(this);" label="I AM NOT A BROKER"></s:checkbox>
				<s:submit value="Continue" name="submit" />
			</s:form>
</body>
</html>
