package hbw.controller.hearing.request.action;

import hbw.controller.hearing.request.common.CommonUtil;
import hbw.controller.hearing.request.common.Constants;
import hbw.controller.hearing.request.common.FileUtil;
import hbw.controller.hearing.request.common.HBWClient;
import hbw.controller.hearing.request.common.HBWMessages;
import hbw.controller.hearing.request.common.StatesSinglton;
import hbw.controller.hearing.request.model.States;
import hbw.controller.hearing.request.model.ViolationInfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.CookiesAware;
import org.apache.struts2.interceptor.PrincipalAware;
import org.apache.struts2.interceptor.PrincipalProxy;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * @author Ahmar Nadeem
 *
 */
@Namespace("/dispute")
@ResultPath(value = "/")
@Results({ @Result(name = "success", location = "ticket/enter_defense.jsp"),
	@Result(name = "input", location = "ticket/search_violation.jsp") })
@InterceptorRefs({ @InterceptorRef("defaultStack"), @InterceptorRef("prepare") })
@Validations
public class ViolationNumberAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = 2137861793381499464L;

    private static Logger LOGGER = LoggerFactory.getLogger(ViolationNumberAction.class);

    private ViolationInfo violationInfo = new ViolationInfo();

    private boolean violationInSystem;

    public String getState() {
	return "NY";
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
	violationNumber = violationNumber.trim();
	
	try {
	    /**
	     * In case violation is not in system, there's no sense of checking it's
	     * eligibility and populating info
	     */
		if (!HBWClient.isViolationEligibleForHearing(violationNumber)) {	
			String message = HBWClient.getReasonForViolationNotEligibleForHearing(violationNumber);
			if(null != message && message.contains("The check digit is invalid."))
				message = HBWMessages.VIOLATION_SEARCH_INVALID_ENTRY;
			addActionError(message);		
		    //addActionError("This violation has had a prior hearing and cannot be scheduled for another hearing.");			
			return INPUT;
		}
	
		if (!HBWClient.isViolationInSystem(violationNumber)) {
		/** No more needed to restrict the user in case violation is not in system */
		// addActionError("Violation number not found. Check that your violation number is correct and try again.");
		// return INPUT;
		violationInSystem = false;
	    }
	
	    if (violationInSystem) {	
		violationInfo = HBWClient.getViolationInfo(violationNumber);
		if (violationInfo == null) {
		    violationInfo = HBWClient.getViolationInfoByPlateNubmer(violationNumber);
		}
	    } else {
		String vioNumber;
		String checkDigit;
		vioNumber = this.violationNumber.substring(0, 9);
		checkDigit = this.violationNumber.substring(this.violationNumber.length() - 1);
		if (!checkDigit.equals(calculateViolationNumberCheckDigit(vioNumber))) {
		    addActionError(HBWMessages.VIOLATION_SEARCH_INVALID_ENTRY);
		    return INPUT;
		}
	    }
	} catch (Exception e) {
		e.printStackTrace();
	    addActionError(HBWMessages.CREATE_HEARING_GENERIC_ERROR);
	    return INPUT;
	}		
	
	HttpServletRequest request = ServletActionContext.getRequest();	
	HttpSession session = request.getSession(false);

	LOGGER.info("Violation number " + this.violationNumber + " accepted for hearing request.");
			
	session.setAttribute(Constants.VIOLATION_NUMBER, violationNumber);
	session.setAttribute(Constants.VIOLATION_INFO, violationInfo);
	session.setAttribute(Constants.VIOLATION_IN_SYSTEM, violationInSystem);
	
	session.setAttribute(Constants.PAGE_COUNTS, (0));
	
	try {
		FileUtil.deleteTempFolder(request);
	} catch (IOException e) {
		LOGGER.error("Error in sesssion ...");
		e.printStackTrace();
		addActionError(HBWMessages.CREATE_HEARING_GENERIC_ERROR);
		return INPUT;
	}
	
	return SUCCESS;
    }

    /**
     * @author Ahmar Nadeem
     * 
     *         Function to return violation number encoded to base64 thrice.
     * @return
     */
    public String getVioBase64Encoded() {
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession(false);
	return CommonUtil.tripleEncodePlainText((String) session.getAttribute(Constants.VIOLATION_NUMBER));
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
	if (argViolationNumber.length() != 9 /* || !isStringNumeric(argViolationNumber) */) {
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
    public List<States> getStates() {
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