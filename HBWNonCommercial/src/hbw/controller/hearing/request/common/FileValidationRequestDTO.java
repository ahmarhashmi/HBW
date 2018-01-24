/**
 * 
 */
package hbw.controller.hearing.request.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Ahmar Nadeem
 *
 */
public class FileValidationRequestDTO implements Serializable {

    private static final long serialVersionUID = 2534164016525275007L;

    /** The violation number that's alternatively called as summon number */
    @JsonProperty("summonsNumber")
    private String summonsNumber;

    /** The JWT created using */
    @JsonProperty("token")
    private String token;

    /** The list of files to be scanned and converted to tiff */
    @JsonProperty("files")
    private List<Evidence> files;

    /** Date and time of the request */
    @JsonProperty("submittedDate")
    private Date submittedDate;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "FileValidationRequestDTO [summonsNumber=" + summonsNumber + ", token=" + token + ", files=" + files
		+ ", submittedDate=" + submittedDate + "]";
    }

    /**
     * @return the summonsNumber
     */
    public String getSummonsNumber() {
	return summonsNumber;
    }

    /**
     * @param summonsNumber
     *            the summonsNumber to set
     */
    public void setSummonsNumber(String summonsNumber) {
	this.summonsNumber = summonsNumber;
    }

    /**
     * @return the token
     */
    public String getToken() {
	return token;
    }

    /**
     * @param token
     *            the token to set
     */
    public void setToken(String token) {
	this.token = token;
    }

    /**
     * @return the files
     */
    public List<Evidence> getFiles() {
	return files;
    }

    /**
     * @param files
     *            the files to set
     */
    public void setFiles(List<Evidence> files) {
	this.files = files;
    }

    /**
     * @return the submittedDate
     */
    public Date getSubmittedDate() {
	return submittedDate;
    }

    /**
     * @param submittedDate
     *            the submittedDate to set
     */
    public void setSubmittedDate(Date submittedDate) {
	this.submittedDate = submittedDate;
    }

}
