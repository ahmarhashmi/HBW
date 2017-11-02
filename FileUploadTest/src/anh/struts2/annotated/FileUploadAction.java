package anh.struts2.annotated;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

import com.opensymphony.xwork2.ActionSupport;

@Namespace("/upload")
@ResultPath(value = "/upload/")
public class FileUploadAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private List<File> uploads = new ArrayList<File>();
	private List<String> uploadsFileName = new ArrayList<String>();
	private List<String> uploadsContentType = new ArrayList<String>();
	private String desc = "";

	@Action(value = "/multiUploadFile", results = {
			@Result(name = "success", location = "success.jsp"),
			@Result(name = "input", location = "error.jsp") }, interceptorRefs = {
			@InterceptorRef(params = {
					"allowedTypes",
					"image/pjpeg,image/jpeg,image/jpg,image/tiff,image/bmp,application/pdf",
					"maximumSize", "20971520" }, value = "fileUpload"),
			@InterceptorRef("defaultStack"), @InterceptorRef("validation") })
	public String multiUploadFile() {
		if (uploads.size() > Constants.MAX_NUMBER_OF_EVIDENCES) {
			throw new RuntimeException("Maximum of "
					+ Constants.MAX_NUMBER_OF_EVIDENCES
					+ " files can be uploaded.");
		}
		int i = 0;
		for (File file : uploads) {
			if (file.getParent().length() > Constants.MAX_TOTAL_SIZE_OF_EVIDENCE) {
				throw new RuntimeException(
						"Total size of files exceeds allowed "
								+ Constants.MAX_TOTAL_SIZE_OF_EVIDENCE
								/ (1024 * 1024) + "MB limit.");
			}
			try {
				FileInputStream fis = new FileInputStream(file);
				String fileName = uploadsFileName.get(i);
				String contentType = uploadsContentType.get(i);
				System.out.println(contentType);
				FileOutputStream fos = new FileOutputStream("D:/cp/" + fileName);
				byte[] b = new byte[1024];
				while (fis.read(b) != -1) {
					fos.write(b);
				}
				desc += fileName + ", ";
				fos.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			i++;
		}
		return SUCCESS;
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
}
