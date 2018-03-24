package hbw.controller.hearing.request.action;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * Servlet implementation class SessionFilter
 */
public class SessionFilter implements Filter {

	private static Logger LOGGER = LoggerFactory.getLogger(SessionFilter.class);
	private String mode = "DEV";

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filterChain) throws IOException, ServletException {

		String jSessionId = null;

		try {
			HttpSession session = ((HttpServletRequest) req).getSession(false);
			if (session == null) {
				session = ((HttpServletRequest) req).getSession(true);
				//LOGGER.info("Session IS not existing. Taking New Session = "
						//+ session.getId());
			}

			Cookie[] cookies = ((HttpServletRequest) req).getCookies();			
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals("JSESSIONID")) {
						jSessionId = cookies[i].getValue();
						cookies[i].setValue(null);
						cookies[i].setMaxAge(0);
						((HttpServletResponse) res).addCookie(cookies[i]);
					}

				}
			}

			if (jSessionId != null) {
				Cookie c = new Cookie("JSESSIONID", jSessionId);				
				if (req instanceof HttpServletRequest) {										
					String URL = ((HttpServletRequest) req).getRequestURL()
							.toString().toUpperCase();
					if (URL.startsWith("HTTPS")) {
						c.setPath("/");								
						c.setSecure(true);
					}				
				}
				((HttpServletResponse) res).addCookie(c);
			}			

		} catch (Exception e) {
			LOGGER.error("Error in Session Filter");
			e.printStackTrace();
		}
		filterChain.doFilter(req, res);
	}

	public void destroy() {
	}

	public void init(FilterConfig filterConfig) {
		String configMode = filterConfig.getInitParameter("mode");
		if (configMode != null) {
			mode = configMode;
		}
	}

}