package hbw.controller.hearing.request.action;

import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.LOGIN;
import static com.opensymphony.xwork2.Action.SUCCESS;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
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

import com.fasterxml.jackson.databind.JsonNode;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import hbw.controller.hearing.request.common.CommonUtil;
import hbw.controller.hearing.request.common.Constants;
import hbw.controller.hearing.request.common.FileUtil;
import hbw.controller.hearing.request.common.HBWClient;
import hbw.controller.hearing.request.common.JWTUtil;
import hbw.controller.hearing.request.common.Resource;
import hbw.controller.hearing.request.common.StatesSinglton;
import hbw.controller.hearing.request.model.DeleteSummon;
import hbw.controller.hearing.request.model.Evidence;
import hbw.controller.hearing.request.model.FileValidationRequestDTO;
import hbw.controller.hearing.request.model.States;
import hbw.controller.hearing.request.model.UploadedFile;
import hbw.controller.hearing.request.model.ViolationInfo;

/**
 * @author Ahmar Nadeem
 *
 */
@Namespace("/dispute")
@ResultPath(value = "/")
@Results({ @Result(name = SUCCESS, location = "dispute/ticket/verify_info.jsp"),
	@Result(name = INPUT, location = "dispute/ticket/enter_defense.jsp"),
	@Result(name = LOGIN, location = "dispute/ticket/broker_selection.jsp") })
@InterceptorRefs({ @InterceptorRef("defaultStack"), @InterceptorRef("prepare") })
@Validations
public class CreateNewHearingAction extends ActionSupport implements Preparable {

    private static Logger LOGGER = LoggerFactory.getLogger(CreateNewHearingAction.class);

    private static final long serialVersionUID = -5864237156298942117L;

    private ViolationInfo violationInfo;

    private String defense;
    private String explainWhy;
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

    private boolean violationInSystem;

    private List<UploadedFile> files;

    @Override
    public void prepare() throws Exception {
    }

    /**
     * @return the violationInSystem
     */
    public boolean isViolationInSystem() {
	return violationInSystem;
    }

    /**
     * The main action listener of this class.
     */
    @Action(value = "/create_hearing")
    public String execute() {
    
    boolean noHearing = false;
    	
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession(false);
	if (!CommonUtil.isSessionActive(request)) {
	    return LOGIN;
	}
	violationInSystem = (Boolean) session.getAttribute(Constants.VIOLATION_IN_SYSTEM);
	violationInfo = (ViolationInfo) session.getAttribute(Constants.VIOLATION_INFO);
	violationNumber = (String) session.getAttribute(Constants.VIOLATION_NUMBER);
	
	LOGGER.info("REQUEST RECEIVED for Violation No. "+violationNumber + " -- SessionId = "+ session.getId());
			
	files = new ArrayList<UploadedFile>();
	File uploadedFiles = FileUtil.validateAndGetEvidenceUploadPath(request, "create_hearing_execute");
	List<Evidence> evidences = new ArrayList<Evidence>();
	if (uploadedFiles.exists()) {
	    UploadedFile uploadedFile;
	    int totalPages = 0;
	    for (File file : uploadedFiles.listFiles()) {
		uploadedFile = new UploadedFile();
		uploadedFile.setFileName(file.getName());
		uploadedFile.setFileSize(FileUtils.sizeOf(file) / 1024);

		if (FileUtil.getFileExtension(file.getName()).equals("tiff")
			|| FileUtil.getFileExtension(file.getName()).equals("tif")) {

		    try {
			uploadedFile.setPageCount(FileUtil.getPageCountOfTiff(FileUtils.readFileToByteArray(file)));
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		} else if (FileUtil.getFileExtension(file.getName()).equals("pdf")) {
		    try {
			uploadedFile.setPageCount(FileUtil.getPageCountOfPDF(file));
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		} else {
		    uploadedFile.setPageCount(1);
		}
		files.add(uploadedFile);
		totalPages += uploadedFile.getPageCount();

		try {
		    Evidence evidence = new Evidence();
		    evidence.setName(file.getName());
		    evidence.setExtension(FileUtil.getFileExtension(file.getName()));
		    evidence.setContent(FileUtil.encodeFileToBase64Binary(file));
		    evidences.add(evidence);
		} catch (IOException e) {
		    addActionError(e.getLocalizedMessage());
		    return INPUT;
		}
	    }
	    UploadedFile total = new UploadedFile();
	    total.setFileName("TOTAL");
	    total.setPageCount(totalPages);
	    total.setFileSize(FileUtils.sizeOf(uploadedFiles) / 1024);
	    files.add(total);
	}

	LOGGER.info(violationNumber +" : Handling the create hearing request");
	
	if (!certify) {
	    addActionError("You must certify.");
	    return INPUT;
	}

	if (!email1.equals(email2)) {
	    addActionError("Emails do not match.");
	    return INPUT;
	}

	if (evidences.size() > 0) {
	    FileValidationRequestDTO dto = new FileValidationRequestDTO();
	    dto.setFiles(evidences);
	    dto.setSubmittedDate(new Date());
	    dto.setSummonsNumber(violationNumber);
	    dto.setToken(JWTUtil.createJWT(violationNumber));
	    // dto.setSourceLocation("DisputeATicket");

	    try {
		String response = CommonUtil.scanAndConvertFilesToTiff(dto);
		JsonNode resNode = CommonUtil.parseJsonStringToObject(response);
		if (null != resNode.get(0)) {
		    if (resNode.get(0).get(Constants.RETURN_CODE) != null
			    && resNode.get(0).get(Constants.RETURN_CODE).asText().equals(Constants.RETURN_CODE_ERROR)) {	
		    	
		    	noHearing = true;
		    	
			    if (resNode.get(0).get("ErrorCode").asText().equals("106")) {
				addActionError("Total file size is greater then allowed limit.");
			    } else if (resNode.get(0).get("ErrorCode").asText().equals("101")) {
				addActionError("Duplicate Summon found. Cannot be uploaded.");
			    } else {
				addActionError("Some Processing Error occur, try again later.");
			    }
			    return INPUT;
			}		    
		}
		else if (resNode.get(Constants.RETURN_CODE) != null
			&& resNode.get(Constants.RETURN_CODE).asText().equals(Constants.RETURN_CODE_ERROR)) {
			
			noHearing = true;
			
		    LOGGER.info("Vanguard return code is " + resNode.get(Constants.RETURN_CODE).asText());
		    JsonNode payload = resNode.get("Payload");
		    boolean isError = false;
		    for (JsonNode payloadItem : payload) {
			if (payloadItem.get("ErrorCode") != null
				&& payloadItem.get("ErrorCode").asText().equals("101")) {
			    addActionError("Duplicate Summon found. Cannot be uploaded.");
			    isError = true;
			} else if (payloadItem.get("ErrorCode") != null
				&& payloadItem.get("ErrorCode").asText().equals("105")) {
			    addActionError("File name " + payloadItem.get("FileName")
				    + " is corrupt or malicious and identified as infected. Cannot be uploaded.");
			    isError = true;
			} else if (payloadItem.get("ErrorCode") != null
				&& payloadItem.get("ErrorCode").asText().equals("107")) {
			    addActionError("File name " + payloadItem.get("FileName")
				    + " is not a valid file name. Cannot be uploaded.");
			    isError = true;
			} else if (payloadItem.get("ErrorCode") != null
				&& payloadItem.get("ErrorCode").asText().equals("106")) {
			    addActionError("File name " + payloadItem.get("FileName")
				    + " is not a valid file size. Cannot be uploaded.");
			    isError = true;
			} else if (payloadItem.get("ErrorCode") != null
				&& payloadItem.get("ErrorCode").asText().equals("112")) {
			    addActionError("File name " + payloadItem.get("FileName")
				    + " is duplicate file. Cannot be uploaded.");
			    isError = true;
			}
		    }
		    if (!isError) {
			addActionError(resNode.get("ErrorCode").asText()
				+ ": Unexpected response from the server. Please try again later.");
		    }
		    return INPUT;
		}
	    } catch (Exception e) {
		e.printStackTrace();
		
		
		try{
		
		if (e.getMessage().contains("Connection timed out:")) {
			    addActionError("Server not responding. Please try again later.");
			    return INPUT;
		}
		//new code in case of exception delete the recently create request 
		if(e.getMessage().contains("HTTP response code: 503") || e.getMessage().contains("HTTP response code: 403")){
				LOGGER.error("Going to delete Summon from VGD server.");
				deleteVGDSummonsNumber(violationNumber);
				addActionError("Server is busy. Please try again later.");
			    return INPUT;
			}
		}catch(Exception exp){
			LOGGER.error("Exception in VGD delete  === " + exp.getMessage());			
		}
				
		addActionError(e.getLocalizedMessage());
		return INPUT;
	    }
	}
	try {
	    LOGGER.info("Handling the create hearing request. All validations successful.");
	    //LOGGER.info("State has been selected to be:" + state);
	    
	    if(!noHearing) {
	    	createNewHearing();
	    	FileUtil.deleteTempFolder(request);
	    }
	} catch (MalformedURLException e) {
	    e.printStackTrace();
		addActionError("Server is busy, please try later.");
	    
	    return INPUT;
	} catch (IOException e) {
	    LOGGER.error("An error occurred in deleting the temp directory: " + e.getStackTrace());
	    return INPUT;
	} catch (Exception e) {
		deleteVGDSummonsNumber(violationNumber);
	    e.printStackTrace();
	    if (e.getMessage() != null && e.getMessage().toLowerCase().contains("invalid defense")) {
		addActionError(
			"Something wrong with the defense you entered. There might be some unauthorized characters in it. Please check and try again.");
	    } else {		
		addActionError("Error while create hearing, please try later");
	    }
	    return INPUT;
	}
	return SUCCESS;
    }
    
    public void deleteVGDSummonsNumber(String violationNumber)
    {
    
    	DeleteSummon ds = new DeleteSummon();	
	    ds.setSummonsNumber(violationNumber);
	    ds.setToken(JWTUtil.createJWT(violationNumber));	    
	    
	    String response = CommonUtil.deleteSummon(ds);;
		JsonNode resNode;
		try {
			resNode = CommonUtil.parseJsonStringToObject(response);
			if (null != resNode.get(0)) {
				//need to code here
				LOGGER.info(resNode.get(0).get("ReturnCode")+"");
			}
		} catch (IOException e) {			
			e.printStackTrace();
		}		
    }

    /**
     * 
     * @throws MalformedURLException
     */
    public void createNewHearing() throws MalformedURLException {
	NYCServMobileApp port = HBWClient.getConnection();
	LOGGER.info(violationNumber + " :Invoking createNewHearing...");
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
	request.setMtvjDefense(explainWhy);
	CreateNewHearingResult response = port.createNewHearing(request);
	if (!response.getFeedBack().isSuccess()) {
	    throw new RuntimeException(response.getFeedBack().getFailureReason());

	}
	else
	{
		LOGGER.info(violationNumber + " : createNewHearing.result from DOF isNewHearingCreated() = " + response.isNewHearingCreated() + " --getFeedBack().isSuccess()--  "+ response.getFeedBack().isSuccess());
	}
    }

    /**
     * 
     * @return current date
     */
    public String getRequestDate() {
	return DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT).format(new Date());
    }

    /**
     * @return the states
     */
    public List<States> getStates() {
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
    @RequiredStringValidator(fieldName = "firstName", message = "Fist Name is required.", trim = true)
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
    @RequiredStringValidator(fieldName = "lastName", message = "Last Name is required.", trim = true)
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
    @RequiredStringValidator(fieldName = "address", message = "Address is required.", trim = true)
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
    @RequiredStringValidator(fieldName = "city", message = "City is required.", trim = true)
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
    @RequiredStringValidator(fieldName = "zip", message = "Zip/Postal Code is required.", trim = true)
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
    @RequiredStringValidator(fieldName = "email1", message = "Email is required.", trim = true)
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
    @RequiredStringValidator(fieldName = "email2", message = "Confirm email is required.", trim = true)
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
    public List<UploadedFile> getFiles() {
	return files;
    }

    /**
     * @param files
     *            the files to set
     */
    public void setFiles(List<UploadedFile> files) {
	this.files = files;
    }

    /**
     * @return the explainWhy
     */
    public String getExplainWhy() {
	return explainWhy;
    }

    /**
     * @param explainWhy
     *            the explainWhy to set
     */
    public void setExplainWhy(String explainWhy) {
	this.explainWhy = explainWhy;
    }

}
