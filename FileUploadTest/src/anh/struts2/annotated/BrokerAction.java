/**
 * 
 */
package anh.struts2.annotated;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Ahmar Nadeem
 * 
 */
@Namespace("/Broker")
@ResultPath(value = "/")
public class BrokerAction extends ActionSupport {

    private static final long serialVersionUID = -9077943167249674484L;

    private boolean broker;
    private boolean notABroker;

    @Action(value = "broker_decision", results = { @Result(name = "success", location = "pages/search_violation.jsp") })
    public String execute() throws Exception {

	if (notABroker) {
	    return SUCCESS;
	}
	if (broker) {
	    /**
	     * In case the user is a broker, then we need to redirect them to the external
	     * URL.
	     */
	    HttpServletResponse response = ServletActionContext.getResponse();
	    response.sendRedirect("http://www1.nyc.gov/site/finance/vehicles/");
	    return super.execute();
	}
	return NONE;
    }

    /**
     * @return the broker
     */
    public boolean isBroker() {
	return broker;
    }

    /**
     * @param broker
     *            the broker to set
     */
    public void setBroker(boolean broker) {
	this.broker = broker;
    }

    /**
     * @return the notABroker
     */
    public boolean isNotABroker() {
	return notABroker;
    }

    /**
     * @param notABroker
     *            the notABroker to set
     */
    public void setNotABroker(boolean notABroker) {
	this.notABroker = notABroker;
    }

}
