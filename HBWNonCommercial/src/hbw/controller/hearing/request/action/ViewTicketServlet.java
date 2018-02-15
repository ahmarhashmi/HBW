package hbw.controller.hearing.request.action;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;

import hbw.controller.hearing.request.common.ViolationImageUtil;

/**
 * Servlet implementation class ViewTicketServlet
 */
public class ViewTicketServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
	response.getWriter().append("Served at: ").append(request.getContextPath());
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
