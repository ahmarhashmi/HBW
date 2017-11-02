/**
 * 
 */
package anh.struts2.annotated;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Ahmar
 *
 */
@Namespace("/User")
@ResultPath(value = "/")
public class WelcomeUserAction extends ActionSupport {

    private String username = "";

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    @Action(value = "Welcome", results = { @Result(name = "success", location = "pages/broker_selection.jsp") })
    public String execute() {

	return SUCCESS;
    }
}
