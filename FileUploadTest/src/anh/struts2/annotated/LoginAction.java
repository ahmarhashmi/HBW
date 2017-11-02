/**
 * 
 */
package anh.struts2.annotated;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Ahmar
 *
 */
@Namespace("/User")
@ResultPath(value="/")
@Result(name="success",location="pages/broker_selection.jsp")
public class LoginAction extends ActionSupport {

}
