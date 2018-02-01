/**
 * 
 */
package hbw.controller.hearing.request.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * @author Ahmar Hashmi
 *
 */
@Namespace("/dispute")
@ResultPath(value = "/")
public class HomeAction extends ActionSupport {

    private static final long serialVersionUID = 5535224131489929020L;
    private static Logger LOGGER = LoggerFactory.getLogger(HomeAction.class);

    /**
     * The main action listener of this class.
     */
    @Action(value = "/home", results = { @Result(name = "success", location = "ticket/broker_selection.jsp") })
    public String execute() {
	LOGGER.info("Redirecting to the home screen.");
	ServletActionContext.getRequest().getSession().invalidate();

	ServletContext context = ServletActionContext.getServletContext();
	RequestDispatcher dispatcher = context.getRequestDispatcher("/index.jsp");
	try {
	    dispatcher.forward(ServletActionContext.getRequest(), ServletActionContext.getResponse());
	} catch (ServletException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}

	return SUCCESS;
    }
}
