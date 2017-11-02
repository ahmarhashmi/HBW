package anh.struts2.annotated;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

/**
 * @author Ahmar Nadeem
 *
 */
@Namespace("/Broker")
@ResultPath(value = "/")
@Results({
    @Result(name = "success", location = "pages/verify_info.jsp"),
    @Result(name = "input", location = "Broker/pages/enter_defense.jsp"),
})
@InterceptorRefs ({
    @InterceptorRef("defaultStack"), 
    @InterceptorRef("prepare")
})
public class CreateNewHearingAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = -5864237156298942117L;
    
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
    
    @Override
    public void prepare() throws Exception {
    }

    @Action(value = "/create_hearing")
    public String execute() {

	return SUCCESS;
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
    @RequiredStringValidator(fieldName="firstName", message="Fist Name is rquired.", trim=true)
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
    @RequiredStringValidator(fieldName="lastName", message="Last Name is rquired.", trim=true)
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
    @RequiredStringValidator(fieldName="address", message="Address is rquired.", trim=true)
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
    @RequiredStringValidator(fieldName="city", message="City is rquired.", trim=true)
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
    @RequiredStringValidator(fieldName="state", message="Select your state.", trim=true)
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
    @RequiredStringValidator(fieldName="zip", message="Zip/Postal Code is rquired.", trim=true)
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
    @RequiredStringValidator(fieldName="email1", message="Email is rquired.", trim=true)
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
    @RequiredStringValidator(fieldName="email2", message="Confirm email is rquired.", trim=true)
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

}
