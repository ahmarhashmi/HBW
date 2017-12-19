package hbw.controller.hearing.request.action;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.web.client.RestTemplate;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.log4j2.Log4j2LoggerFactory;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import hbw.controller.hearing.request.common.Constants;
import hbw.controller.hearing.request.common.Resource;
import hbw.controller.hearing.request.common.StatesSinglton;
import hbw.controller.hearing.request.common.ViolationInfo;

/**
 * @author Ahmar Nadeem
 *
 */
@Namespace("/dispute")
@ResultPath(value = "/")
@Results({ @Result(name = "success", location = "ticket/enter_defense.jsp"),
	// @Result(name = "success", location = "pages/upload_jquery.jsp"),
	@Result(name = "input", location = "ticket/search_violation.jsp"), })
@InterceptorRefs({ @InterceptorRef("defaultStack"), @InterceptorRef("prepare") })
@Validations
public class ViolationNumberAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = 2137861793381499464L;

    Logger LOGGER = Log4j2LoggerFactory.getLogger(ViolationNumberAction.class);

    private ViolationInfo violationInfo = new ViolationInfo();

    private RestTemplate rest = new RestTemplate();

    private String violationNumber;

    @Override
    public void prepare() throws Exception {
    }

    /**
     * The function that is called when user enters the violation number and submits
     * the form
     */
    @Action(value = "searchViolation")
    public String execute() {

	if (!isViolationInSystem()) {
	    addActionError("Violation does not exist in the system.");
	    return INPUT;
	}

	// call the web service and populate the violation info based on the violation
	// number provided by the user.

	violationInfo = populateViolationInfo(violationNumber);

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession();
	session.setAttribute(Constants.VIOLATION_NUMBER, violationNumber);
	return SUCCESS;
    }

    /**
     * 
     * @return
     */
    private boolean isViolationInSystem() {
	/** Check if the violation exists in the system or not */
	URI isViolationInSystemURL = null;
	try {
	    isViolationInSystemURL = new URI(Resource.IS_VIOLATION_IN_SYSTEM_URL.getValue() + violationNumber);
	    if (!rest.getForObject(isViolationInSystemURL, Boolean.class)) {
		return false;
	    }
	} catch (URISyntaxException e) {
	    e.printStackTrace();
	    return false;
	}
    }

    /**
     * This function is responsible for calling the web-service to populate
     * violation information.
     * 
     * @param violationNumber2
     * @return
     */
    private ViolationInfo populateViolationInfo(String violationNumber) {
	ViolationInfo info = new ViolationInfo();

	info.setBalanceDue(BigDecimal.TEN);
	info.setCode("code");
	info.setDescription("This is a computer generated ticket");
	info.setFine(BigDecimal.valueOf(3));
	info.setInterest(BigDecimal.valueOf(1.5));
	info.setIssuedOn(new Date());
	info.setLocation("New York");
	info.setPaid(BigDecimal.valueOf(6));
	info.setPenalty(BigDecimal.ZERO);
	info.setReduction(BigDecimal.ZERO);
	info.setVehicleMake("Honda");
	info.setVehiclePlate("LV-17-1234");
	info.setVehicleState("New York");
	info.setVehicleType("Motor Car");
	// TODO call the restful webservice and populate the violation info
	return info;
    }

    /**
     * Calculates the check digit for a nine digit violation number. If the
     * violation number is invalid, returns "X" Creation date: (8/28/00 3:47:32 PM)
     * 
     * @return java.lang.String - valid check digit "0" - "9" or "X" if invalid
     * @param argViolationNumber
     *            java.lang.String first nine digits of violation number
     */
    public static String calculateViolationNumberCheckDigit(String argViolationNumber) {
	int checkDigit = 0;
	// remove lead and trail blanks
	if (argViolationNumber.length() != 9 || !validateNumericString(argViolationNumber)) {
	    return "X";
	}

	// Now that result is all numeric do the check digit alorithm
	for (int digit = 0; digit < argViolationNumber.length(); digit++) {
	    checkDigit += ((10 - digit) * (new Integer(argViolationNumber.substring(digit, digit + 1)).intValue()));
	}
	checkDigit = checkDigit % 11; // mod eleven
	if (checkDigit == 10) {
	    checkDigit = 0; // a check digit (mod 11) of 10 counts as zero
	}
	return "" + checkDigit;
    }

    /**
     * Makes sure if the provided string contains only numeric
     * 
     * @param argViolationNumber
     * @return
     */
    private static boolean validateNumericString(String argViolationNumber) {
	try {
	    Long.parseLong(argViolationNumber);
	    return true;
	} catch (NumberFormatException e) {
	    return false;
	}
    }

    /**
     * Populate the states from singleton
     * 
     * @return
     */
    public List<String> getStates() {
	return StatesSinglton.getStates();
    }

    /**
     * @return the violationNumber
     */
    @RequiredStringValidator(fieldName = "violationNumber", message = "Violation Number is rquired.", trim = true)
    public String getViolationNumber() {
	return violationNumber;
    }

    /**
     * @param violationNumber
     *            the violationNumber to set
     */
    public void setViolationNumber(String violationNumber) {
	this.violationNumber = violationNumber;
    }

    /**
     * @return the violationInfo
     */
    public ViolationInfo getViolationInfo() {
	return violationInfo;
    }

    /**
     * @param violationInfo
     *            the violationInfo to set
     */
    public void setViolationInfo(ViolationInfo violationInfo) {
	this.violationInfo = violationInfo;
    }

}
