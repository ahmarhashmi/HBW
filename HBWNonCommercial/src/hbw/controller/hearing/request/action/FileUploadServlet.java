package hbw.controller.hearing.request.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.itextpdf.text.io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import com.itextpdf.text.pdf.codec.TiffImage;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import hbw.controller.hearing.request.common.CommonUtil;
import hbw.controller.hearing.request.common.Constants;
import hbw.controller.hearing.request.common.FileUtil;
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

	String requestedFileName = request.getParameter("file");
	if (requestedFileName != null) {
	    doGetFile(request, response, requestedFileName);
	} else {
	    String deleteFile = request.getParameter("delete");
	    if (deleteFile != null) {
		try {
		    doDeleteFile(request, response, deleteFile);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		response.getWriter().append("FileDeleted: ").append(deleteFile);
	    }
	}
	if (request.getParameter("reset") != null) {
	    FileUtil.deleteTempFolder(request);
	    request.getSession().invalidate();
	}

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
    File file = null;
    
	if (!CommonUtil.isSessionActive(request)) {
	    LOGGER.error("Session has been timed out. Navigating to the home page.");
	    /**
	     * CAUTION: Do not change the response string, Else Front end string will also
	     * be required to be changed.
	     */
	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Session timed out.");
	    return;
	}

	LOGGER.info("Handling request from: " + request.getRemoteAddr());

	if (!ServletFileUpload.isMultipartContent(request)) {
	    throw new IllegalArgumentException(
		    "Request is not multipart, please 'multipart/form-data' enctype for your form.");
	}

	File evidencePath = FileUtil.validateAndGetEvidenceUploadPath(request);

	ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
	try {
	    List<FileItem> items = uploadHandler.parseRequest(request);
	    
	    for (FileItem item : items) {
		if (!item.isFormField()) {
		    int pageCount = 1;

		    StringBuilder totalCountReachedMessage = new StringBuilder(Resource.MAX_NUMBER_OF_EVIDENCES.getValue()+" pages upload limit will be violated with this file. So")
			    .append(item.getName() + " cannot be uploaded. ")
			    .append("If you want to submit more evidence, please do not request a hearing online. ")
			    .append("Submit your hearing request and evidence by mail or in person.");
		    
		    StringBuilder totalSizeReachedMessage = new StringBuilder((Long.parseLong(Resource.MAX_TOTAL_SIZE_OF_EVIDENCE.getValue())/1024/1024)+
			    " MB upload limit will be voilated with this file. So ")
			    .append(item.getName() + " cannot be uploaded. ")
			    .append("If you want to submit more evidence, please do not request a hearing online. ")
			    .append("Submit your hearing request and evidence by mail or in person.");
		    
		    if (getExistingCount(request) + pageCount > Integer
			    .parseInt(Resource.MAX_NUMBER_OF_EVIDENCES.getValue())
			    || FileUtils.sizeOf(evidencePath) + item.getSize() > Long
				    .parseLong(Resource.MAX_TOTAL_SIZE_OF_EVIDENCE.getValue())) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					totalSizeReachedMessage.toString());
			//file should be removed
			item.delete();
			return;
		    }
		    file = new File(evidencePath, item.getName());
		    if (item.getSize() > Long.parseLong(Resource.MAX_TOTAL_SIZE_OF_EVIDENCE.getValue())) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
				"File cannot be greater than " + Resource.MAX_TOTAL_SIZE_OF_EVIDENCE + "MB");
			return;
		    }
		    if (file.exists()) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
				"A file with the same name was already uploaded. If this is different, rename the file and try again.");
			return;
		    }

		    boolean fileWrittenToTheDrive = false;

		    /**
		     * Validate if the file type is a valid image or pdf type.
		     */
		    //String mimeType = new MimetypesFileTypeMap().getContentType(file);	
		    //change because of wrong mime type for pdf and bmp application/octate-stream
		    
		    LOGGER.info("(item.getContentType ::::::::::::: " + item.getContentType());
		   
		    String mimeType = item.getContentType();
		    String[] mime = mimeType.split("/");
		    
		    LOGGER.info("(mime[0]::::::::::::: " + mime[0]);
		    LOGGER.info("(mime[1]::::::::::::: " + mime[1]);		    
		    
		    if (mime[0].equals("image") && !(mime[1].toLowerCase().equals("tiff"))) {			
		    	BufferedImage bi = ImageIO.read(item.getInputStream());
				if (bi == null) {
				    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					    "File is damaged and cannot be uploaded.");
				    return;
				}		    
		    } else if (mime[0].equals("application") && mime[1].toLowerCase().equals("pdf")) {		    	
			item.write(file);
			fileWrittenToTheDrive = true;						
			try {
			    pageCount = getPageCountOfPDF(file);
			    
			    LOGGER.info(" Total pages so far = " + (getExistingCount(request) + pageCount));
			    
			    if (getExistingCount(request) + pageCount > Integer
				    .parseInt(Resource.MAX_NUMBER_OF_EVIDENCES.getValue())) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					totalCountReachedMessage.toString());
				
				//removing file so that we are not uploading it
				if(null!=file)
				file.delete();
				return;
			    }
			} catch (Exception e) {
				if(null!=file)
			    file.delete();
			    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
				    "File is damaged or not a valid pdf file, hence, cannot be uploaded.");
			    return;
			}
		    			  
		    } else if (mime[0].equals("image") && (mime[1].toLowerCase().equals("tiff"))) {
			try {				
				pageCount = getPageCountOfTiff(item.get());		    
			    
			    LOGGER.info(pageCount  + " == Total page count for tiff == "+ (getExistingCount(request) + pageCount));
			    if (getExistingCount(request) + pageCount > Integer
				    .parseInt(Resource.MAX_NUMBER_OF_EVIDENCES.getValue())) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					totalCountReachedMessage.toString());				
				return;
			    }
			} catch (Exception e) {				
			    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
				    "File is damaged or not a valid tiff file, hence, cannot be uploaded.");
			    return;
			}
		    } else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mime[0]+"/"+mime[1]+"File not supported.");
			return;
		    }

		    if (!fileWrittenToTheDrive) {
			item.write(file);
		    }
		    
		  String fileExt = FilenameUtils.getExtension(file.getName());
		    
		  //if (getServletContext().getMimeType(file.getName()).contains("gif")
		   
		  if (fileExt.toLowerCase().equals("gif") && FileUtil.isAnimatedGIF(file)) {
			file.delete();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
				"Animated gif is not allowed. Only PDF, JPEG/JPG, TIFF, BMP, or non-animated gif can be uploaded.");
			return;
		    }

		    increaseDecreaseCountOfPages(request, pageCount, true);
		    pageCount = 1;
		    
		    LOGGER.info(file.getPath() + "  File uploaded SUCCESSFULLY");
		}
	    }
	} catch (Exception e) {
		if(null!=file)
		file.delete();
	    LOGGER.error("File not uploaded. Please try again.");
	    e.printStackTrace();
	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
	}
    }

    /**
     * @author Ahmar Hashmi
     * 
     *         Reads the provided pdf file and returns the page count.
     *         Throws @Exception if file is not pdf or damaged.
     * 
     * @param file
     * @return
     * @throws Exception
     */
    private int getPageCountOfPDF(File file) throws Exception {
	RandomAccessFile raf = new RandomAccessFile(file, "r");
	RandomAccessFileOrArray pdfFile = new RandomAccessFileOrArray(
		new RandomAccessSourceFactory().createSource(raf));
	PdfReader reader = new PdfReader(pdfFile, new byte[0]);
	int pageCount = reader.getNumberOfPages();
	reader.close();

	return pageCount;
    }

    /**
     * @author Ahmar Hashmi
     * 
     *         Reads the tiff file and returns the number of pages it contains.
     * 
     * @param item
     * @return
     */
    @SuppressWarnings("deprecation")
    private int getPageCountOfTiff(byte[] bytes) {    	
	return TiffImage.getNumberOfPages(new RandomAccessFileOrArray(bytes));
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
	    request.getSession().setAttribute(Constants.PAGE_COUNTS, (existingCount + pageCount));
	} else {
	    request.getSession().setAttribute(Constants.PAGE_COUNTS, (existingCount - pageCount));
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
	Integer existingCount = (Integer) request.getSession().getAttribute(Constants.PAGE_COUNTS);
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

	LOGGER.info("File " + fileName + " mime type is:" + mimeType);

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
     * @throws Exception
     */
    private void doDeleteFile(HttpServletRequest request, HttpServletResponse response, String deleteFile)
	    throws Exception {
	File folder = FileUtil.validateAndGetEvidenceUploadPath(request);
	File file = new File(folder.getPath() + File.separator + deleteFile);
	int pageCount = 1;
	if (FileUtil.getFileExtension(deleteFile).equals("tiff")
		|| FileUtil.getFileExtension(deleteFile).equals("tif")) {

	    pageCount = getPageCountOfTiff(FileUtils.readFileToByteArray(file));
	} else if (FileUtil.getFileExtension(deleteFile).equals("pdf")) {
	    pageCount = getPageCountOfPDF(file);
	}
	LOGGER.info("DELETE file:" + file.getPath());
	file.delete();
	increaseDecreaseCountOfPages(request, pageCount, false);
	LOGGER.info("file:" + file.getPath() + " >>> Deleted successfully.");
    }

}