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
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
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
	try {
	    if (!HBWClient.isViolationInSystem(violationNumber)) {
		addActionError("Violation does not exist in the system.");
		return INPUT;
	    }
	    if (!HBWClient.isViolationEligibleForHearing(violationNumber)) {
		addActionError("Violation is not eligible for hearing.");
		return INPUT;
	    }
	    violationInfo = HBWClient.getViolationInfo(violationNumber);
	    if (violationInfo == null) {
		violationInfo = HBWClient.getViolationInfoByPlateNubmer(violationNumber);
	    }
	} catch (Exception e) {
	    addActionError(
		    "We are having trouble connecting to your system. We are aware of the issue and actively working on it. Please try again later.");
	    return INPUT;
	}

	LOGGER.info("Violation number "+this.violationNumber+" accepted for hearing request.");
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession();
	session.setAttribute(Constants.VIOLATION_NUMBER, violationNumber);
	session.setAttribute(Constants.VIOLATION_INFO, violationInfo);
	return SUCCESS;
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
