package anh.struts2.annotated;

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

/**
 * @author Ahmar Nadeem
 * 
 *         Servlet implementation class FileUploadServlet for handling the drag
 *         and drop file upload.
 */
public class FileUploadServlet extends HttpServlet {
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
	
	System.out.println(request.getRemoteAddr());
	
	if (!ServletFileUpload.isMultipartContent(request)) {
	    throw new IllegalArgumentException(
		    "Request is not multipart, please 'multipart/form-data' enctype for your form.");
	}

	ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
	PrintWriter writer = response.getWriter();

	System.out.println(new File(request.getServletContext().getRealPath("/") + "images/"));
	try {
	    List<FileItem> items = uploadHandler.parseRequest(request);

	    for (FileItem item : items) {
		if (!item.isFormField()) {
		    File file = new File("D:/cp/", item.getName());
		    item.write(file);

		    System.out.println("uploaded");
		}
	    }
	} catch (FileUploadException e) {
	    throw new RuntimeException(e);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	} finally {
	    writer.close();
	}
    }
}
