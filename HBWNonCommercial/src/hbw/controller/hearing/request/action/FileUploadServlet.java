package hbw.controller.hearing.request.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.log4j2.Log4j2LoggerFactory;

import hbw.controller.hearing.request.common.FileUtil;
import hbw.controller.hearing.request.common.Resource;

/**
 * @author Ahmar Nadeem
 * 
 *         Servlet implementation class FileUploadServlet for handling the drag
 *         and drop file upload.
 */
public class FileUploadServlet extends HttpServlet {

    Logger LOGGER = Log4j2LoggerFactory.getLogger(FileUploadServlet.class);

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadServlet() {
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

	LOGGER.info("Handling request from: {}", request.getRemoteAddr());

	if (!ServletFileUpload.isMultipartContent(request)) {
	    throw new IllegalArgumentException(
		    "Request is not multipart, please 'multipart/form-data' enctype for your form.");
	}

	File evidencePath = FileUtil.validateAndGetEvidenceUploadPath(request);

	ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
	PrintWriter writer = response.getWriter();

	LOGGER.info(new File(request.getServletContext().getRealPath("/") + "images/").toString());
	try {
	    List<FileItem> items = uploadHandler.parseRequest(request);

	    for (FileItem item : items) {
		if (!item.isFormField()) {
		    if (evidencePath.listFiles().length == Integer.parseInt(Resource.MAX_NUMBER_OF_EVIDENCES.getValue())
			    || FileUtils.sizeOf(evidencePath) >= Long
				    .parseLong(Resource.MAX_TOTAL_SIZE_OF_EVIDENCE.getValue())) {
			throw new RuntimeException("Upload limit reached. File(s) cannot be uploaded. If\r\n"
				+ "you want to submit more evidence, please do not\r\n"
				+ "request a hearing online. Submit your hearing request\r\n"
				+ "and evidence by mail or in person.");
		    }
		    File file = new File(evidencePath, item.getName());
		    if (file.length() > Long.parseLong(Resource.MAX_TOTAL_SIZE_OF_EVIDENCE.getValue())) {
			throw new RuntimeException(
				"File cannot be greater than " + Resource.MAX_TOTAL_SIZE_OF_EVIDENCE + "MB");
		    }
		    item.write(file);

		    LOGGER.info("{} File uploaded SUCCESSFULLY", file.toString());
		}
	    }
	} catch (FileUploadException e) {
	    LOGGER.error("File Upload failed due to an error: {}", e.getMessage());
	    throw new RuntimeException(e);
	} catch (Exception e) {
	    LOGGER.error("File Upload failed due to an error: {}", e.getMessage());
	    throw new RuntimeException(e);
	} finally {
	    writer.close();
	}
    }
}
