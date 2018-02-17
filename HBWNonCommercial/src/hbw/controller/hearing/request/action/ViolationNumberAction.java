package hbw.controller.hearing.request.action;

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

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import hbw.controller.hearing.request.common.Constants;
import hbw.controller.hearing.request.common.HBWClient;
import hbw.controller.hearing.request.common.StatesSinglton;
import hbw.controller.hearing.request.model.ViolationInfo;

/**
 * @author Ahmar Nadeem
 *
 */
@Namespace("/dispute")
@ResultPath(value = "/")
@Results({ @Result(name = "success", location = "ticket/enter_defense.jsp"),
	@Result(name = "input", location = "ticket/search_violation.jsp"), })
@InterceptorRefs({ @InterceptorRef("defaultStack"), @InterceptorRef("prepare") })
@Validations
public class ViolationNumberAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = 2137861793381499464L;

    private static Logger LOGGER = LoggerFactory.getLogger(ViolationNumberAction.class);

    private ViolationInfo violationInfo = new ViolationInfo();

    private boolean violationInSystem;

    public String getState() {
	return "New York";
    }

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
	/** Set the default value to true */
	violationInSystem = true;
	try {
	    if (!HBWClient.isViolationInSystem(violationNumber)) {
		/** No more needed to restrict the user in case violation is not in system */
		// addActionError("Violation does not exist in the system.");
		// return INPUT;
		violationInSystem = false;
	    }
	    /**
	     * In case violation is not in system, there's no sense of checking it's
	     * eligibility and populating info
	     */
	    if (violationInSystem) {
		if (!HBWClient.isViolationEligibleForHearing(violationNumber)) {
		    addActionError("Violation is not eligible for hearing.");
		    return INPUT;
		}
		violationInfo = HBWClient.getViolationInfo(violationNumber);
		if (violationInfo == null) {
		    violationInfo = HBWClient.getViolationInfoByPlateNubmer(violationNumber);
		}
	    }
	} catch (Exception e) {
	    addActionError(
		    "We are having trouble connecting to your system. We are aware of the issue and actively working on it. Please try again later.");
	    return INPUT;
	}

	LOGGER.info("Violation number " + this.violationNumber + " accepted for hearing request.");
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession();
	session.setAttribute(Constants.VIOLATION_NUMBER, violationNumber);
	session.setAttribute(Constants.VIOLATION_INFO, violationInfo);
	session.setAttribute(Constants.VIOLATION_IN_SYSTEM, violationInfo);
	return SUCCESS;
    }
    
    /**
     * Calculates the check digit for a nine digit violation number. If the
     * violation number is invalid, returns "X" Creation date: (8/28/00 3:47:32 PM)
     * 
     * @return java.lang.String - valid check digit "0" - "9" or "X" if invalid
     * @param argViolationNumber
     *            java.lang.String first nine digits of violation number
     */
    @Deprecated
    public static String calculateViolationNumberCheckDigit(String argViolationNumber) {
	int checkDigit = 0;
	// remove lead and trail blanks
	if (argViolationNumber.length() != 9 /*|| !isStringNumeric(argViolationNumber)*/) {
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

    /**
     * @return the violationInSystem
     */
    public boolean isViolationInSystem() {
	return violationInSystem;
    }

    /**
     * @param violationInSystem
     *            the violationInSystem to set
     */
    public void setViolationInSystem(boolean violationInSystem) {
	this.violationInSystem = violationInSystem;
    }

}
