package hbw.controller.hearing.request.action;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.example.nycservmobileapp.Address;
import org.example.nycservmobileapp.CreateNewHearingRequest;
import org.example.nycservmobileapp.CreateNewHearingResult;
import org.example.nycservmobileapp.NYCServMobileApp;
import org.example.nycservmobileapp.Name;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfReader;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.log4j2.Log4j2LoggerFactory;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import hbw.controller.hearing.request.common.Constants;
import hbw.controller.hearing.request.common.FileUtil;
import hbw.controller.hearing.request.common.HBWClient;
import hbw.controller.hearing.request.common.Resource;
import hbw.controller.hearing.request.common.StatesSinglton;
import hbw.controller.hearing.request.common.UploadedFiles;
import hbw.controller.hearing.request.common.ViolationInfo;
import hbw.controller.hearing.request.common.ZipUtil;

/**
 * @author Ahmar Nadeem
 *
 */
@Namespace("/dispute")
@ResultPath(value = "/")
@Results({ @Result(name = "success", location = "dispute/ticket/verify_info.jsp"),
	@Result(name = "input", location = "dispute/ticket/enter_defense.jsp"), })
@InterceptorRefs({ @InterceptorRef("defaultStack"),
	@InterceptorRef("prepare")/* , @InterceptorRef("ajaxValidation") */ })
@Validations
public class CreateNewHearingAction extends ActionSupport implements Preparable {

    private static Logger LOGGER = Log4j2LoggerFactory.getLogger(CreateNewHearingAction.class);

    private static final long serialVersionUID = -5864237156298942117L;

    private ViolationInfo violationInfo;

    private String defense;
    private String firstName;
    private String middleName;
    private String lastName;
    private String address;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String email1;
    private String email2;
    private boolean certify;
    private boolean affirm;

    private String violationNumber;

    private List<UploadedFiles> files;

    @Override
    public void prepare() throws Exception {
    }

    /**
     * The main action listener of this class.
     */
    @Action(value = "/create_hearing")
    public String execute() {
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession();
	violationInfo = (ViolationInfo) session.getAttribute(Constants.VIOLATION_INFO);
	files = new ArrayList<UploadedFiles>();
	File uploadedFiles = FileUtil.validateAndGetEvidenceUploadPath(request);
	if (uploadedFiles.exists()) {
	    UploadedFiles uploadedFile;
	    for (File file : uploadedFiles.listFiles()) {
		uploadedFile = new UploadedFiles();
		uploadedFile.setFileName(file.getName());
		uploadedFile.setFileSize(file.length()/1024);
		uploadedFile.setPageCount(1);

		String mimeType = new MimetypesFileTypeMap().getContentType(file);
		if (mimeType.equals(Constants.PDF) || mimeType.equals("application/octet-stream")) {
		    try {
			Document document = new Document();
			document.open();
			PdfReader reader;
			reader = new PdfReader(file.getPath());
			uploadedFile.setPageCount(reader.getNumberOfPages());
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		}
		files.add(uploadedFile);
	    }
	}

	LOGGER.info("Handling the create hearing request");
	if (!certify) {
	    addActionError("You must certify.");
	    return INPUT;
	}

	if (!email1.equals(email2)) {
	    addActionError("Emails do not match.");
	    return INPUT;
	}
	LOGGER.info("Handling the create hearing request. All validations successful.");
	try {
	    createNewHearing();
	} catch (MalformedURLException e) {
	    addActionError(e.getLocalizedMessage());
	    return INPUT;
	}
	return processHearingRequest();
    }

    /**
     * 
     * @return
     */
    private String processHearingRequest() {
	HttpServletRequest request = ServletActionContext.getRequest();
	File evidenceLocation = FileUtil.validateAndGetEvidenceUploadPath(request);
	if (evidenceLocation.list().length == 0 && !affirm) {
	    addActionError(
		    "No evidence attached. You must affirm that you are not uploading evidence for the judge to consider.");
	    return INPUT;
	}
	File zip = new File(evidenceLocation.getPath() + File.separator + evidenceLocation.getName() + ".zip");
	try {
	    ZipUtil.zipDirectory(evidenceLocation.getPath(), zip.getPath());
	} catch (IOException e) {
	    addActionError(e.getMessage());
	    return INPUT;
	}

	if (zip.exists()) {
	    // TODO: Do the stuff to call the service for creating hearing
	}

	return SUCCESS;
    }

    /**
     * 
     * @throws MalformedURLException
     */
    public void createNewHearing() throws MalformedURLException {
	NYCServMobileApp port = HBWClient.getConnection();
	LOGGER.debug("Invoking createNewHearing...");
	CreateNewHearingRequest request = new CreateNewHearingRequest();
	request.setCredentials(HBWClient.getCredentials());
	request.setWebServiceChannel(Resource.WS_CHANNEL.getValue());

	/** address object */
	Address address = new Address();
	address.setAddressLine1(this.address);
	address.setAddressLine2(this.address2);
	address.setCity(city);
	address.setStateProvince(state);
	address.setZipCode(zip);
	request.setAddress(address);

	/** name object */
	Name name = new Name();
	name.setFirstName(firstName);
	name.setMiddleName(middleName);
	name.setLastName(lastName);
	request.setName(name);

	request.setDefense(defense);
	request.setEmail(email1);
	request.setViolationNumber(violationNumber);
	request.setEvidenceToBeUploaded(!affirm);
	CreateNewHearingResult response = port.createNewHearing(request);
	LOGGER.debug("createNewHearing.result=" + response);
    }

    /**
     * 
     * @return current date
     */
    public String getRequestDate() {
	return DateFormat.getDateInstance().format(new Date());
    }

    /**
     * @return the states
     */
    public List<String> getStates() {
	return StatesSinglton.getStates();
    }

    public String state() throws Exception {
	return SUCCESS;
    }

    /**
     * @return the defense
     */
    public String getDefense() {
	return defense;
    }

    /**
     * @param defense
     *            the defense to set
     */
    public void setDefense(String defense) {
	this.defense = defense;
    }

    /**
     * @return the firstName
     */
    @RequiredStringValidator(fieldName = "firstName", message = "Fist Name is rquired.", trim = true)
    public String getFirstName() {
	return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    /**
     * @return the middleName
     */
    public String getMiddleName() {
	return middleName;
    }

    /**
     * @param middleName
     *            the middleName to set
     */
    public void setMiddleName(String middleName) {
	this.middleName = middleName;
    }

    /**
     * @return the lastName
     */
    @RequiredStringValidator(fieldName = "lastName", message = "Last Name is rquired.", trim = true)
    public String getLastName() {
	return lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    /**
     * @return the address
     */
    @RequiredStringValidator(fieldName = "address", message = "Address is rquired.", trim = true)
    public String getAddress() {
	return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(String address) {
	this.address = address;
    }

    /**
     * @return the address2
     */
    public String getAddress2() {
	return address2;
    }

    /**
     * @param address2
     *            the address2 to set
     */
    public void setAddress2(String address2) {
	this.address2 = address2;
    }

    /**
     * @return the city
     */
    @RequiredStringValidator(fieldName = "city", message = "City is rquired.", trim = true)
    public String getCity() {
	return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(String city) {
	this.city = city;
    }

    /**
     * @return the state
     */
    @RequiredStringValidator(fieldName = "state", message = "Select your state.", trim = true)
    public String getState() {
	return state;
    }

    /**
     * @param state
     *            the state to set
     */
    public void setState(String state) {
	this.state = state;
    }

    /**
     * @return the zip
     */
    @RequiredStringValidator(fieldName = "zip", message = "Zip/Postal Code is rquired.", trim = true)
    public String getZip() {
	return zip;
    }

    /**
     * @param zip
     *            the zip to set
     */
    public void setZip(String zip) {
	this.zip = zip;
    }

    /**
     * @return the email1
     */
    @RequiredStringValidator(fieldName = "email1", message = "Email is rquired.", trim = true)
    public String getEmail1() {
	return email1;
    }

    /**
     * @param email1
     *            the email1 to set
     */
    public void setEmail1(String email1) {
	this.email1 = email1;
    }

    /**
     * @return the email2
     */
    @RequiredStringValidator(fieldName = "email2", message = "Confirm email is rquired.", trim = true)
    public String getEmail2() {
	return email2;
    }

    /**
     * @param email2
     *            the email2 to set
     */
    public void setEmail2(String email2) {
	this.email2 = email2;
    }

    /**
     * @return the certify
     */
    public boolean isCertify() {
	return certify;
    }

    /**
     * @param certify
     *            the certify to set
     */
    public void setCertify(boolean certify) {
	this.certify = certify;
    }

    /**
     * @return the affirm
     */
    public boolean isAffirm() {
	return affirm;
    }

    /**
     * @param affirm
     *            the affirm to set
     */
    public void setAffirm(boolean affirm) {
	this.affirm = affirm;
    }

    /**
     * @return the violationNumber
     */
    public String getViolationNumber() {
	return violationNumber;
    }

    /**
     * @param violationNumber
     *            the violationNumber to set
     */
    public void setViolationNumber(String violationNumber) {
	this.violationNumber = violationNumber;
    }

    /**
     * @return the violationInfo
     */
    public ViolationInfo getViolationInfo() {
	return violationInfo;
    }

    /**
     * @param violationInfo
     *            the violationInfo to set
     */
    public void setViolationInfo(ViolationInfo violationInfo) {
	this.violationInfo = violationInfo;
    }

    /**
     * @return the files
     */
    public List<UploadedFiles> getFiles() {
	return files;
    }

    /**
     * @param files
     *            the files to set
     */
    public void setFiles(List<UploadedFiles> files) {
	this.files = files;
    }

}
