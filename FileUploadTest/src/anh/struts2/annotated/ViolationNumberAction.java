package anh.struts2.annotated;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

/**
 * @author Ahmar Nadeem
 *
 */
@Namespace("/Broker")
@ResultPath(value = "/")
@Results({ @Result(name = "success", location = "pages/enter_defense.jsp"),
	// @Result(name = "success", location = "pages/upload_jquery.jsp"),
	@Result(name = "input", location = "pages/search_violation.jsp"), })
@InterceptorRefs({ @InterceptorRef("defaultStack") })
public class ViolationNumberAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = 2137861793381499464L;

    private ViolationInfo violationInfo = new ViolationInfo();

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
	// call the web service and populate the violation info based on the violation
	// number provided by the user.
	violationInfo = populateViolationInfo(violationNumber);
	return SUCCESS;
    }

    // simple validation
    public void validate() {
	if (Boolean.TRUE) {
	    addActionMessage("You are valid user!");
	} else {
	    addActionError("I don't know you, dont try to hack me!");
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
	// TODO call the restful webservice and populate the violation info
	return info;
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
