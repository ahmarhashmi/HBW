package hbw.controller.hearing.request.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
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

	String requestedFileName = request.getParameter("file");
	if (requestedFileName != null) {
	    doGetFile(request, response, requestedFileName);
	} else {
	    String deleteFile = request.getParameter("delete");
	    if (deleteFile != null) {
		doDeleteFile(request, response, deleteFile);
		response.getWriter().append("FileDeleted: ").append(deleteFile);
	    }
	}
	if (request.getParameter("reset") != null) {
	    request.getSession().invalidate();
	}

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
			writer.write("File cannot be greater than " + Resource.MAX_TOTAL_SIZE_OF_EVIDENCE + "MB");
			response.setStatus(500);
			return;
		    }
		    if (file.exists()) {
			writer.write("File with the same name already exists/uploaded.");
			response.setStatus(500);
			return;
		    }
		    
		    item.write(file);
		    if (getServletContext().getMimeType(file.getName()).contains("gif") && FileUtil.isAnimatedGIF(file)) {
			file.delete();
			writer.write("Animated giffs are not allowed to upload");
			response.setStatus(500);
			return;
		    }

		    LOGGER.info(file.getPath() + "{} File uploaded SUCCESSFULLY", file.getPath());
		}
	    }
	} catch (Exception e) {
	    LOGGER.error("File Upload failed due to an error: {}", e.getMessage());
	    e.printStackTrace();
	    writer.write(e.getLocalizedMessage());
	} finally {
	    writer.close();
	}
    }

    /**
     * This function is responsible for getting the file and writing to the
     * response.
     * 
     * @param request
     * @param response
     * @param requestedFileName
     */
    private void doGetFile(HttpServletRequest request, HttpServletResponse response, String requestedFileName) {
	File folder = FileUtil.validateAndGetEvidenceUploadPath(request);
	// Get the absolute path of the image
	ServletContext sc = getServletContext();
	String fileName = folder.getPath() + File.separator + requestedFileName;

	// Get the MIME type of the image
	String mimeType = sc.getMimeType(fileName);
	if (mimeType == null) {
	    sc.log("Could not get MIME type of " + fileName);
	    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    return;
	}

	LOGGER.info("File {} mime type is:{}", fileName, mimeType);

	// Set content type
	response.setContentType(mimeType);

	// Set content size
	File file = new File(fileName);
	response.setContentLength((int) file.length());
	try {
	    // Open the file and output streams
	    FileInputStream in = new FileInputStream(file);
	    OutputStream out = response.getOutputStream();

	    // Copy the contents of the file to the output stream
	    byte[] buf = new byte[1024];
	    int count = 0;
	    while ((count = in.read(buf)) >= 0) {
		out.write(buf, 0, count);
	    }
	    in.close();
	    out.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    /**
     * This function is responsible for deleting the file from the server.
     * 
     * @param request
     * @param response
     * @param deleteFile
     */
    private void doDeleteFile(HttpServletRequest request, HttpServletResponse response, String deleteFile) {
	File folder = FileUtil.validateAndGetEvidenceUploadPath(request);
	File fileName = new File(folder.getPath() + File.separator + deleteFile);
	LOGGER.info("DELETE file:{}", fileName.getPath());
	fileName.delete();
	LOGGER.info("file:{} >>> Deleted successfully.", fileName.getPath());
    }

}
