/**
 * 
 */
package hbw.controller.hearing.request.model;

/**
 * @author Ahmar Hashmi
 *
 */
public class UploadedFile {

    private String fileName;
    private int pageCount;
    private long fileSize;

    /**
     * @return the fileName
     */
    public String getFileName() {
	return fileName;
    }

    /**
     * @param fileName
     *            the fileName to set
     */
    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    /**
     * @return the pageCount
     */
    public int getPageCount() {
	return pageCount;
    }

    /**
     * @param pageCount
     *            the pageCount to set
     */
    public void setPageCount(int pageCount) {
	this.pageCount = pageCount;
    }

    /**
     * @return the fileSize
     */
    public long getFileSize() {
	return fileSize;
    }

    /**
     * @param fileSize
     *            the fileSize to set
     */
    public void setFileSize(long fileSize) {
	this.fileSize = fileSize;
    }
}
