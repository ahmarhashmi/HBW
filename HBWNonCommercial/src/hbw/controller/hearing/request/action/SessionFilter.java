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

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * Servlet implementation class SessionFilter
 */
public class SessionFilter implements Filter {

	private static Logger LOGGER = LoggerFactory.getLogger(SessionFilter.class);
	private String mode = "DEV";

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		/*
		 res.addHeader("X-FRAME-OPTIONS", mode );
		 res.addHeader("X-Content-Type-OPTIONS", "nosniff" );
		 res.addHeader("X-XSS-Protection", "1; mode=block" ); 
		 res.addHeader("Vary", "*" ); 
		 res.addHeader("Expires", "-1" ); 
		 res.addHeader("Pragma", "no-cache" );
		 res.addHeader("Cache-control", "no-cache, no-store,max-age=0, must-revalidate" );
		 */
		 /*
		  Cookie[] cookies = ((HttpServletRequest) request).getCookies();
	        if (cookies != null)
	            for (int i = 0; i < cookies.length; i++) {
	                cookies[i].setValue("HBW");
	                cookies[i].setPath("/");
	                cookies[i].setMaxAge(2000);	                
	                cookies[i].setSecure(true);              
	                res.addCookie(cookies[i]);
	            }
	      	 */
		 //res.setHeader("SET-COOKIE", "JSESSIONID=" + req.getSession().getId() + ";Path=/ ;SECURE ;HttpOnly");
		 //res.setHeader("SET-COOKIE", "JSESSIONID=" + req.getSession().getId() + ";HttpOnly");
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