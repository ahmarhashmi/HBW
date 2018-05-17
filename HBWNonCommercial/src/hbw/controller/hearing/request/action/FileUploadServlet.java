package hbw.controller.hearing.request.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import hbw.controller.hearing.request.common.CommonUtil;
import hbw.controller.hearing.request.common.Constants;
import hbw.controller.hearing.request.common.FileUtil;
import hbw.controller.hearing.request.common.HBWMessages;
import hbw.controller.hearing.request.common.Resource;

/**
 * @author Ahmar Nadeem
 * 
 *         The servlet to handle evidence upload related actions like upload
 *         file, view file, delete file from server etc.
 */
public class FileUploadServlet extends HttpServlet {

    private static Logger LOGGER = LoggerFactory.getLogger(FileUploadServlet.class);

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
    
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0);
    
	String requestedFileName = request.getParameter("file");
	if (requestedFileName != null) {
	    doGetFile(request, response, requestedFileName.replaceAll("\\s+", ""));
	    return;
	} else {
	    String deleteFile = request.getParameter("delete");
	    if (deleteFile != null) {
		try {
		    doDeleteFile(request, response, deleteFile.replaceAll("\\s+", ""));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		populateTotalUploadInfo(request, response);
		return;
	    }
	}
	if (request.getParameter("reset") != null) {
	    FileUtil.deleteTempFolder(request);
//	    request.getSession(false).invalidate();
	    return;
	}
	if (request.getParameter("uploadInfo") != null) {
	    populateTotalUploadInfo(request, response);
	    return;
	}

    }

    /**
     * @author Ahmar Hashmi
     * @param request
     * @param response
     * @throws IOException
     */
    private void populateTotalUploadInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
	HttpSession session = request.getSession(false);
	Integer count = (Integer) session.getAttribute(Constants.PAGE_COUNTS);
	LOGGER.info("TOTAL Page uploaded so far: " + count);
	Double filesTotalSize = (double) (FileUtils
		.sizeOf(FileUtil.validateAndGetEvidenceUploadPath(request, "FileUploadServlet_doGetFile")));
	LOGGER.info("TOTAL uploaded files size in bytes: " + filesTotalSize);

	// Double size = Double.MIN_VALUE;
	DecimalFormat df = new DecimalFormat("#0.00");
	if (count == 0) {
	    return;
	} else {
	    response.getWriter().append("<b>Total</b> ").append(count == null ? "0" : count.toString())
		    .append(" pages, ").append(df.format(filesTotalSize / 1024 / 1024)).append(" MB");
	}

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
    	
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0);
    
	File file = null;	
	//LOGGER.info(" -- Session in servlet is :::::::::: " + request.getSession());
	if (!CommonUtil.isSessionActive(request)) {
		
	    LOGGER.error("Session has been timed out. Navigating to the home page.");	    
	    /**
	     * CAUTION: Do not change the response string, Else Front end string will also
	     * be required to be changed.
	     */
	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, HBWMessages.CREATE_HEARING_GENERIC_ERROR);
	    return;
	}

	LOGGER.info("Handling request from: " + request.getRemoteAddr());

	if (!ServletFileUpload.isMultipartContent(request)) {
	    throw new IllegalArgumentException(
		    "Request is not multipart, please 'multipart/form-data' enctype for your form.");
	}

	File evidencePath = FileUtil.validateAndGetEvidenceUploadPath(request, "FileUploadServlet_doPost");

	ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
	try {
	    List<FileItem> items = uploadHandler.parseRequest(request);

	    for (FileItem item : items) {
		if (!item.isFormField()) {
		    int pageCount = 1;
		    		    
		    StringBuilder totalCountReachedMessage = new StringBuilder(HBWMessages.FILE_UPLOAD_SERVLET_MAX_PAGES);

		    StringBuilder totalSizeReachedMessage = new StringBuilder(HBWMessages.FILE_UPLOAD_SERVLET_MAX_SIZE);

		    if (getExistingCount(request) + pageCount > Integer
					.parseInt(Resource.MAX_NUMBER_OF_EVIDENCES.getValue())) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						totalCountReachedMessage.toString());
				// file should be removed
				item.delete();
				return;
			}
			if (FileUtils.sizeOf(evidencePath) + item.getSize() > Long
					.parseLong(Resource.MAX_TOTAL_SIZE_OF_EVIDENCE.getValue())) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						totalSizeReachedMessage.toString());
				// file should be removed
				item.delete();
				return;
			}
			file = new File(evidencePath, item.getName().replaceAll("\\s+", ""));
			if (item.getSize() > Long.parseLong(Resource.MAX_TOTAL_SIZE_OF_EVIDENCE.getValue())) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,HBWMessages.FILE_UPLOAD_SERVLET_TOTAL_FILE_SIZE);
				return;
			}
			if (file.exists()) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, HBWMessages.FILE_UPLOAD_SERVLET_FILE_ALREADY_EXIST);
				return;
			}

		    boolean fileWrittenToTheDrive = false;

		    /**
		     * Validate if the file type is a valid image or pdf type.
		     */
		    // String mimeType = new MimetypesFileTypeMap().getContentType(file);
		    // change because of wrong mime type for pdf and bmp application/octate-stream

		    //LOGGER.info("(item.getContentType ::::::::::::: " + item.getContentType());

		    String mimeType = item.getContentType();
		    String[] mime = mimeType.split("/");

		   // LOGGER.info("(mime[0]::::::::::::: " + mime[0]);
		   // LOGGER.info("(mime[1]::::::::::::: " + mime[1]);

		    if (mime[0].equals("image") && !(mime[1].toLowerCase().equals("tiff"))) {
			BufferedImage bi = ImageIO.read(item.getInputStream());
			if (bi == null) {
			    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, HBWMessages.FILE_UPLOAD_SERVLET_FILE_IS_DAMAGED);
			    return;
			}
		    } else if (mime[0].equals("application") && mime[1].toLowerCase().equals("pdf")) {
			item.write(file);
			fileWrittenToTheDrive = true;
			try {
			    pageCount = FileUtil.getPageCountOfPDF(file);

			    //LOGGER.info(" Total pages so far = " + (getExistingCount(request) + pageCount));

			    if (getExistingCount(request) + pageCount > Integer
				    .parseInt(Resource.MAX_NUMBER_OF_EVIDENCES.getValue())) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					totalCountReachedMessage.toString());

				// removing file so that we are not uploading it
				if (null != file)
				    file.delete();
				return;
			    }
			} catch (Exception e) {
			    if (null != file)
				file.delete();
			    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, HBWMessages.FILE_UPLOAD_SERVLET_INVALID_PDF);			   
			    return;
			}

		    } else if (mime[0].equals("image") && (mime[1].toLowerCase().equals("tiff"))) {
			try {
			    pageCount = FileUtil.getPageCountOfTiff(item.get());

			   // LOGGER.info(pageCount + " == Total page count for tiff == "
				  //  + (getExistingCount(request) + pageCount));
			    if (getExistingCount(request) + pageCount > Integer
				    .parseInt(Resource.MAX_NUMBER_OF_EVIDENCES.getValue())) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					totalCountReachedMessage.toString());
				return;
			    }
			} catch (Exception e) {
			    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, HBWMessages.FILE_UPLOAD_SERVLET_INVALID_TIFF);				    
			    return;
			}
		    } else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, HBWMessages.FILE_UPLOAD_SERVLET_SUPPORTED_FILE);			
			return;
		    }

		    if (!fileWrittenToTheDrive) {
			item.write(file);
		    }

		    String fileExt = FilenameUtils.getExtension(file.getName());

		    // if (getServletContext().getMimeType(file.getName()).contains("gif")

		    if (fileExt.toLowerCase().equals("gif") && FileUtil.isAnimatedGIF(file)) {
			file.delete();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, HBWMessages.FILE_UPLOAD_SERVLET_INVALID_GIF);				
			return;
		    }

		    increaseDecreaseCountOfPages(request, pageCount, true);
		    pageCount = 1;

		    LOGGER.info(file.getPath() + "  File uploaded SUCCESSFULLY");
		}
	    }
	} catch (Exception e) {
	    if (null != file)
		file.delete();
	    LOGGER.error("File not uploaded. Please try again.");
	    e.printStackTrace();
	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, HBWMessages.CREATE_HEARING_GENERIC_ERROR);
	}
    }

    /**
     * @author Ahmar Hashmi
     * 
     *         Increases or decreases the count to the page count stored in the
     *         session based on the flag
     * 
     * @param request
     * @param pageCount
     * @param increase
     */
    private void increaseDecreaseCountOfPages(HttpServletRequest request, int pageCount, boolean increase) {
	int existingCount = getExistingCount(request);
	if (increase) {
	    request.getSession(false).setAttribute(Constants.PAGE_COUNTS, (existingCount + pageCount));
	} else {
	    request.getSession(false).setAttribute(Constants.PAGE_COUNTS, (existingCount - pageCount));
	}
    }

    /**
     * @author Ahmar Hashmi
     * 
     *         Gets the existing count session object and returns the count. Returns
     *         zero if object is not previously stored.
     * 
     * @param request
     * @return
     */
    private int getExistingCount(HttpServletRequest request) {
	Integer existingCount = (Integer) request.getSession(false).getAttribute(Constants.PAGE_COUNTS);
	return existingCount == null ? 0 : existingCount;
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
    	
	File folder = FileUtil.validateAndGetEvidenceUploadPath(request, "FileUploadServlet_doGetFile");
	FileInputStream in = null;
    OutputStream out = null;
	// Get the absolute path of the image
	ServletContext sc = getServletContext();
	String fileName = folder.getPath() + File.separator + requestedFileName;

	// Get the MIME type of the image
	String mimeType = sc.getMimeType(fileName.toLowerCase());
	if (mimeType == null) {
	    sc.log("Could not get MIME type of " + fileName);
	    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    return;
	}

	LOGGER.info("File " + fileName + " mime type is:" + mimeType);

	// Set content type
	response.setContentType(mimeType);

	// Set content size
	File file = new File(fileName);
	response.setContentLength((int) file.length());
	try {
	    // Open the file and output streams
	     in = new FileInputStream(file);
	     out = response.getOutputStream();

	    // Copy the contents of the file to the output stream
	    byte[] buf = new byte[1024];
	    int count = 0;
	    while ((count = in.read(buf)) >= 0) {
		out.write(buf, 0, count);
	    }
	    //in.close();
	    //out.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	finally
	{
		 if(null!=out)
		    {
		    	try {
		    		out.flush();
					out.close();
				} catch (IOException e1) {				
					e1.printStackTrace();
				}
		    }
		 
		if(null!=in) {
		    	try {		    		
					in.close();
				} catch (IOException e1) {				
					e1.printStackTrace();
				}
		    }
		   
	}
    }

    /**
     * This function is responsible for deleting the file from the server.
     * 
     * @param request
     * @param response
     * @param deleteFile
     * @throws Exception
     */
    private void doDeleteFile(HttpServletRequest request, HttpServletResponse response, String deleteFile)
	    throws Exception {
	File folder = FileUtil.validateAndGetEvidenceUploadPath(request, "FileUploadServlet_doDeleteFile");
	File file = new File(folder.getPath() + File.separator + deleteFile);
	int pageCount = 1;
	if (FileUtil.getFileExtension(deleteFile).equals("tiff")
		|| FileUtil.getFileExtension(deleteFile).equals("tif")) {

	    pageCount = FileUtil.getPageCountOfTiff(FileUtils.readFileToByteArray(file));
	} else if (FileUtil.getFileExtension(deleteFile).equals("pdf")) {
	    pageCount = FileUtil.getPageCountOfPDF(file);
	}
	
	try{
	file.delete();
	LOGGER.info("DELETED file:" + file.getPath());
	    } catch (Exception e) {
			 LOGGER.error("File is not deleted" + e);	   
		 }
	increaseDecreaseCountOfPages(request, pageCount, false);
	LOGGER.info("file:" + file.getPath() + " >>> Deleted successfully.");
    }

}