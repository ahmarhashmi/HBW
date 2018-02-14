<%--
/**
 *
 * This JSP is used to display the Parking Violation Ticket Image
 *
 ******************************************************************************
 *                     M O D I F I C A T I O N   L O G                        *
 *                                                                            *
 *                                                                            *
 *      DATE        RELEASE #   AUTHOR      DESCRIPTION                       *
 *      ----        ---------   ------      -----------                       *
 *                                                                            *
 ******************************************************************************
 *
 * @author  Scott Nelson
 * @version 1.0
 */
--%>


<%@ page
	import="hbw.controller.hearing.request.common.ViolationImageUtil"%>

<jsp:useBean id="supportBean"
	class="hbw.controller.hearing.request.common.ViolationImageUtil"
	scope="request" />

<%
    /* response.setContentType("application/pdf");
			String vioNum = supportBean.getHttpRequest().getParameter("searchID");
			String title = supportBean.getHttpRequest().getParameter("title");
			String locationName = supportBean.getHttpRequest().getParameter("locationName");
				 vioNum passed in as param, use it CQ #19964
				// we triple encode to obfuscate to the prying user
				BASE64Decoder decoder = new BASE64Decoder();
				vioNum = URLDecoder.decode(vioNum, "UTF-8"); // first decode from URL encoded
				String decoded = new String(decoder.decodeBuffer(vioNum)); // once
				decoded = new String(decoder.decodeBuffer(decoded)); // twice
				vioNum = new String(decoder.decodeBuffer(decoded)); // third time

				supportBean.writeVioImagePdfToStream(vioNum, title, locationName, response.getOutputStream());
				// the following two lines are required to avoid IllegalStateException due tyo accessing the getOutputStream (JSP 2.0) SEN -10-02-2006
				out.clear();
				out = pageContext.pushBody(); */
%>
