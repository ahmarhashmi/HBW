/**
 * 
 */
package anh.struts2.annotated;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * @author Ahmar
 *
 */
public class MyInterceptor implements Interceptor {

    private static final long serialVersionUID = 1671290672208835483L;

    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.xwork2.interceptor.Interceptor#destroy()
     */
    @Override
    public void destroy() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.xwork2.interceptor.Interceptor#init()
     */
    @Override
    public void init() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.opensymphony.xwork2.interceptor.Interceptor#intercept(com.opensymphony.
     * xwork2.ActionInvocation)
     */
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
	return actionInvocation.invoke();
    }

}
