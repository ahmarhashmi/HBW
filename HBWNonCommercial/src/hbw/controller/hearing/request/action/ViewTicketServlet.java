package hbw.controller.hearing.request.action;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import hbw.controller.hearing.request.common.CommonUtil;
import hbw.controller.hearing.request.common.ViolationImageUtil;

/**
 * Servlet implementation class ViewTicketServlet
 */
public class ViewTicketServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static Logger LOGGER = LoggerFactory.getLogger(FileUploadServlet.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewTicketServlet() {
	super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	if (!CommonUtil.isSessionActive(request)) {
	    LOGGER.error("Session has been timed out. Navigating to the home page.");
	    /**
	     * DANGER: Do not change the response string, Else Front end string will also be
	     * required to be changed.
	     */
	    response.getWriter().append("timedout");
//	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Session timed out.");
	    return;
	}
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	String vioNum = (String) request.getParameter("VIOLATION_NUMBER");
	String title = "";
	String locationName = "";

	OutputStream outStream = response.getOutputStream();
	try {
	    ViolationImageUtil.writeVioImagePdfToStream(vioNum, title, locationName, outStream);
	} catch (DocumentException e) {
	    e.printStackTrace();
	}
    }

}
