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
@Results({
    @Result(name = "success", location = "pages/enter_defense.jsp"),
    @Result(name = "input", location = "pages/search_violation.jsp"),
})
@InterceptorRefs ({
    @InterceptorRef("defaultStack")
})
public class ViolationNumberAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = 2137861793381499464L;

    private String violationNumber;
//    private static List<String> states;
    
    @Override
    public void prepare() throws Exception {
    }

    @Action(value = "searchViolation")
    public String execute() {
	
	return SUCCESS;
    }
    
    public List<String> getStates(){
	return StatesSinglton.getStates();
    }

    /**
     * @return the violationNumber
     */
    @RequiredStringValidator(fieldName="violationNumber", message="Violation Number is rquired.", trim=true)
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
     * @return the states
     */
//    public List<String> getStates() {
//        return states;
//    }

}
