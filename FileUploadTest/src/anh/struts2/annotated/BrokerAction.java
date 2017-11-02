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
    public String execute() {

	if (notABroker) {
	    return SUCCESS;
	} else {
	    return NONE;
	}
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
