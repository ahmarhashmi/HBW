package anh.struts2.annotated;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.jboss.util.file.Files;

import com.opensymphony.xwork2.ActionSupport;

@Deprecated
@Namespace("/upload")
@ResultPath(value = "/upload/")
public class FileUploadAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private List<File> uploads = new ArrayList<File>();
    private List<String> uploadsFileName = new ArrayList<String>();
    private List<String> uploadsContentType = new ArrayList<String>();

    private File upload;
    private String desc = "";

    @Action(value = "/multiUploadFile", results = { @Result(name = "success", location = "success.jsp"),
	    @Result(name = "input", location = "error.jsp") }, interceptorRefs = {
		    @InterceptorRef(params = { "allowedTypes",
			    "image/pjpeg,image/jpeg,image/jpg,image/tiff,image/bmp,application/pdf", "maximumSize",
			    "209715200" }, value = "fileUpload"),
		    @InterceptorRef("defaultStack"), @InterceptorRef("validation") })
    public String multiUploadFile() {
	
	
	HttpServletRequest request = ServletActionContext.getRequest();
	doTheStuff(request);
	
//	request.get
	if (uploads.size() > Constants.MAX_NUMBER_OF_EVIDENCES) {
	    throw new RuntimeException("Maximum of " + Constants.MAX_NUMBER_OF_EVIDENCES + " files can be uploaded.");
	}
	int i = 0;
	for (File file : uploads) {
	    if (file.getParent().length() > Constants.MAX_TOTAL_SIZE_OF_EVIDENCE) {
		throw new RuntimeException("Total size of files exceeds allowed "
			+ Constants.MAX_TOTAL_SIZE_OF_EVIDENCE / (1024 * 1024) + "MB limit.");
	    }
	    writeFileToDisk(file, i);
	    i++;
	}
	return SUCCESS;
    }
    
    private void doTheStuff(HttpServletRequest request) {
	if (!ServletFileUpload.isMultipartContent(request)) {
	    throw new IllegalArgumentException(
		    "Request is not multipart, please 'multipart/form-data' enctype for your form.");
	}

	ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
//	PrintWriter writer = response.getWriter();

	System.out.println(new File(request.getServletContext().getRealPath("/") + "images/"));
	try {
	    List<FileItem> items = uploadHandler.parseRequest(request);

	    for (FileItem item : items) {
		if (!item.isFormField()) {
		    File file = new File(request.getServletContext().getRealPath("/") + "images/", item.getName());
		    item.write(file);

		    System.out.println("uploaded");
		}
	    }
	} catch (FileUploadException e) {
	    throw new RuntimeException(e);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	} finally {
//	    writer.close();
	}
    }

    private void writeFileToDisk(File file, int i) {
	try {
//	    FileInputStream fis = new FileInputStream(file);
	    String fileName = uploadsFileName.size() > 0 ? uploadsFileName.get(i) : file.getName();
	    String contentType = uploadsContentType.size() > 0 ? uploadsContentType.get(i) : "multipart/formdata";
	    System.out.println(contentType);
	    
	    File destination = new File("D:/cp/" + fileName);
//	    FileOutputStream fos = new FileOutputStream("D:/cp/" + fileName);
	    
	    Files.copy(file, destination);
	    
//	    byte[] b = new byte[1024];
//	    while (fis.read(b) != -1) {
//		fos.write(b);
//	    }
//	    desc += fileName + ", ";
//	    fos.close();
//	    fis.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private void addToZip() throws IOException {
	FileOutputStream zipFile = new FileOutputStream("D:/cp/zipTest.zip");
	ZipOutputStream zos = new ZipOutputStream(zipFile);

	byte[] buffer = new byte[1024];

	File srcFile = new File("");
	FileInputStream fis = new FileInputStream(srcFile);

	// begin writing a new ZIP entry, positions the stream to the start of
	// the entry data

	zos.putNextEntry(new ZipEntry(srcFile.getName()));

	int length;
	while ((length = fis.read(buffer)) > 0) {
	    zos.write(buffer, 0, length);
	}
	zos.closeEntry();
	fis.close();
    }

    public List<File> getUploads() {
	return uploads;
    }

    public void setUploads(List<File> uploads) {
	this.uploads = uploads;
    }

    public List<String> getUploadsFileName() {
	return uploadsFileName;
    }

    public void setUploadsFileName(List<String> uploadsFileName) {
	this.uploadsFileName = uploadsFileName;
    }

    public List<String> getUploadsContentType() {
	return uploadsContentType;
    }

    public void setUploadsContentType(List<String> uploadsContentType) {
	this.uploadsContentType = uploadsContentType;
    }

    public String getDesc() {
	return desc;
    }

    public void setDesc(String desc) {
	this.desc = desc;
    }

    /**
     * @return the upload
     */
    public File getUpload() {
	return upload;
    }

    /**
     * @param upload
     *            the upload to set
     */
    public void setUpload(File upload) {
	this.upload = upload;
    }
}
