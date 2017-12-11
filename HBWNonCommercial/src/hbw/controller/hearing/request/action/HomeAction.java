/**
 * 
 */
package hbw.controller.hearing.request.action;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.log4j2.Log4j2LoggerFactory;

/**
 * @author Ahmar
 *
 */
@Namespace("/Broker")
@ResultPath(value = "/")
public class HomeAction extends ActionSupport {

    private static final long serialVersionUID = 5535224131489929020L;
    Logger LOGGER = Log4j2LoggerFactory.getLogger(HomeAction.class);

    /**
     * The main action listener of this class.
     */
    @Action(value = "/home", results = { @Result(name = "success", location = "pages/broker_selection.jsp") })
    public String execute() {
	LOGGER.info("Redirecting to the home screen.");
	ServletActionContext.getRequest().getSession().invalidate();
	return SUCCESS;
    }
}
