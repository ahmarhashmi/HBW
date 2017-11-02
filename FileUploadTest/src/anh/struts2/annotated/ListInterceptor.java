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
public class ListInterceptor implements Interceptor {

    private static final long serialVersionUID = 3089606473977430682L;

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.interceptor.Interceptor#destroy()
     */
    @Override
    public void destroy() {
	// TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.interceptor.Interceptor#init()
     */
    @Override
    public void init() {
	// TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.interceptor.Interceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
     */
    @Override
    public String intercept(ActionInvocation arg0) throws Exception {
	System.out.println(arg0);
	return null;
    }

}
