/**
 * 
 */
package hbw.controller.hearing.request.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.log4j2.Log4j2LoggerFactory;

/**
 * @author Ahmar Nadeem
 * 
 */
@Namespace("/Broker")
@ResultPath(value = "/")
public class BrokerAction extends ActionSupport {

    Logger LOGGER = Log4j2LoggerFactory.getLogger(BrokerAction.class);
    
    private static final long serialVersionUID = -9077943167249674484L;

    private boolean broker;
    private boolean notABroker;

    @Action(value = "broker_decision", results = { @Result(name = "success", location = "pages/search_violation.jsp") })
    public String execute() throws Exception {

	if (notABroker) {
	    LOGGER.info("The user is not a broker. Navigating to the search a violation page.");
	    return SUCCESS;
	}
	if (broker) {
	    LOGGER.info("The user is a broker. Navigating to the broker area.");
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
