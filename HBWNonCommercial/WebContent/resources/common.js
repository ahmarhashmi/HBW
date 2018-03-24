"use strict";

function startAfresh() {
	var contextPath = window.location.pathname.substring(0,
			window.location.pathname.indexOf("/", 2));
	$.get(contextPath + "/FileUploadServlet?reset=true", function(data) {
	});
	window.location = 'ticket/broker_selection.jsp';
}

function openPopUpWindow(URL, name, width, height, loc, scroll, tool, resize) {
	var windowprops = "location=" + loc + ",scrollbars=" + scroll
			+ ",menubars=yes,toolbar=" + tool + ",resizable=" + resize
			+ ",resizable=0" + ",left=" + 25 + ",top = " + 25 + ",width="
			+ width + ",height=" + height;
	var popup = window.open(URL, name, windowprops);
	popup.focus();
} // end of the function for the pop-up window

function enableDisableSearchButton(obj) {
	var val = obj.value.trim();
	if (val.length >= 10) {
		document.getElementById('searchBtn').disabled = false;
	}
	if (obj.value.length < 10) {
		document.getElementById('searchBtn').disabled = true;
	}
}

function validate() {
	var violation = document.getElementById("violationNumber");
	var val = violation.value.trim();
	if (val.length < 10) {
		document.getElementById("errorMessage").innerHTML = "Violation Number cannot be empty or less than 10 characters.";
		return false;
	}
	if (val.length >= 10) {
		var isValid = "";
		if (val.length == 10) {
			isValid = /^\d+$/.test(val);
			if (!isValid) {
				violation.focus(true);
				document.getElementById("errorMessage").innerHTML = "A 10-digit Violation Number can only contain numbers.";
				document.getElementById("errorDiv").style.display = "block";
				return false;
			}
		} else {
			isValid = /^(\d{9}-)?\d{1}$/.test(val);
			if (!isValid) {
				violation.focus(true);
				document.getElementById("errorMessage").innerHTML = "An 11-digit Violation Number can only contain numbers with a hyphen before last number.";
				document.getElementById("errorDiv").style.display = "block";
				return false;
			}
		}
	}
	return true;
}

function openTicketPopUp()
{
	openPopUpWindow('../sample/violationSample.html','TicketPopUp','720','720','no','yes','no','yes');
}

function openRedlightTicketPopup()
{
	openPopUpWindow('../sample/redLightViolationSample.html','RedLightTicketPopUp','700','640','no','yes','no','no');
}

function openBusLaneCameraViolationPopup()
{
	openPopUpWindow('../sample/busLaneCameraViolationSample.html','ViolationPopUp','700','640','no','no','no','no');
}

function openSpeedCameraViolationPopup()
{
	openPopUpWindow('../sample/speedCameraViolationSample.html','SpeedCameraViolationPopUp','700','640','no','no','no','no');
}
