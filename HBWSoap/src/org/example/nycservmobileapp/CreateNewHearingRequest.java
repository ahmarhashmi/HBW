
package org.example.nycservmobileapp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createNewHearingRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createNewHearingRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="credentials" type="{http://www.example.org/NYCServMobileApp/}Credentials"/&gt;
 *         &lt;element name="webServiceChannel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="violationNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="name" type="{http://www.example.org/NYCServMobileApp/}Name"/&gt;
 *         &lt;element name="address" type="{http://www.example.org/NYCServMobileApp/}Address"/&gt;
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="defense" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="inJudgment" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="mtvjDefense" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="evidenceToBeUploaded" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="mobilePhoneNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createNewHearingRequest", propOrder = {
    "credentials",
    "webServiceChannel",
    "violationNumber",
    "name",
    "address",
    "email",
    "defense",
    "inJudgment",
    "mtvjDefense",
    "evidenceToBeUploaded",
    "mobilePhoneNumber"
})
public class CreateNewHearingRequest {

    @XmlElement(required = true)
    protected Credentials credentials;
    @XmlElement(required = true)
    protected String webServiceChannel;
    @XmlElement(required = true)
    protected String violationNumber;
    @XmlElement(required = true)
    protected Name name;
    @XmlElement(required = true)
    protected Address address;
    @XmlElement(required = true)
    protected String email;
    @XmlElement(required = true)
    protected String defense;
    protected boolean inJudgment;
    protected String mtvjDefense;
    protected boolean evidenceToBeUploaded;
    protected String mobilePhoneNumber;

    /**
     * Gets the value of the credentials property.
     * 
     * @return
     *     possible object is
     *     {@link Credentials }
     *     
     */
    public Credentials getCredentials() {
        return credentials;
    }

    /**
     * Sets the value of the credentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link Credentials }
     *     
     */
    public void setCredentials(Credentials value) {
        this.credentials = value;
    }

    /**
     * Gets the value of the webServiceChannel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebServiceChannel() {
        return webServiceChannel;
    }

    /**
     * Sets the value of the webServiceChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebServiceChannel(String value) {
        this.webServiceChannel = value;
    }

    /**
     * Gets the value of the violationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViolationNumber() {
        return violationNumber;
    }

    /**
     * Sets the value of the violationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViolationNumber(String value) {
        this.violationNumber = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link Name }
     *     
     */
    public Name getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link Name }
     *     
     */
    public void setName(Name value) {
        this.name = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setAddress(Address value) {
        this.address = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the defense property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefense() {
        return defense;
    }

    /**
     * Sets the value of the defense property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefense(String value) {
        this.defense = value;
    }

    /**
     * Gets the value of the inJudgment property.
     * 
     */
    public boolean isInJudgment() {
        return inJudgment;
    }

    /**
     * Sets the value of the inJudgment property.
     * 
     */
    public void setInJudgment(boolean value) {
        this.inJudgment = value;
    }

    /**
     * Gets the value of the mtvjDefense property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMtvjDefense() {
        return mtvjDefense;
    }

    /**
     * Sets the value of the mtvjDefense property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMtvjDefense(String value) {
        this.mtvjDefense = value;
    }

    /**
     * Gets the value of the evidenceToBeUploaded property.
     * 
     */
    public boolean isEvidenceToBeUploaded() {
        return evidenceToBeUploaded;
    }

    /**
     * Sets the value of the evidenceToBeUploaded property.
     * 
     */
    public void setEvidenceToBeUploaded(boolean value) {
        this.evidenceToBeUploaded = value;
    }

    /**
     * Gets the value of the mobilePhoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    /**
     * Sets the value of the mobilePhoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobilePhoneNumber(String value) {
        this.mobilePhoneNumber = value;
    }

}
