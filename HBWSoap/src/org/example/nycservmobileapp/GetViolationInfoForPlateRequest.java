
package org.example.nycservmobileapp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getViolationInfoForPlateRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getViolationInfoForPlateRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="credentials" type="{http://www.example.org/NYCServMobileApp/}Credentials"/&gt;
 *         &lt;element name="webServiceChannel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="plateNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="plateState" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="plateType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SearchUnsatisfiedViolationsOnly" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="searchForCollaterals" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getViolationInfoForPlateRequest", propOrder = {
    "credentials",
    "webServiceChannel",
    "plateNumber",
    "plateState",
    "plateType",
    "searchUnsatisfiedViolationsOnly",
    "searchForCollaterals"
})
public class GetViolationInfoForPlateRequest {

    @XmlElement(required = true)
    protected Credentials credentials;
    @XmlElement(required = true)
    protected String webServiceChannel;
    @XmlElement(required = true)
    protected String plateNumber;
    @XmlElement(required = true)
    protected String plateState;
    protected String plateType;
    @XmlElement(name = "SearchUnsatisfiedViolationsOnly")
    protected boolean searchUnsatisfiedViolationsOnly;
    protected boolean searchForCollaterals;

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
     * Gets the value of the plateNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlateNumber() {
        return plateNumber;
    }

    /**
     * Sets the value of the plateNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlateNumber(String value) {
        this.plateNumber = value;
    }

    /**
     * Gets the value of the plateState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlateState() {
        return plateState;
    }

    /**
     * Sets the value of the plateState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlateState(String value) {
        this.plateState = value;
    }

    /**
     * Gets the value of the plateType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlateType() {
        return plateType;
    }

    /**
     * Sets the value of the plateType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlateType(String value) {
        this.plateType = value;
    }

    /**
     * Gets the value of the searchUnsatisfiedViolationsOnly property.
     * 
     */
    public boolean isSearchUnsatisfiedViolationsOnly() {
        return searchUnsatisfiedViolationsOnly;
    }

    /**
     * Sets the value of the searchUnsatisfiedViolationsOnly property.
     * 
     */
    public void setSearchUnsatisfiedViolationsOnly(boolean value) {
        this.searchUnsatisfiedViolationsOnly = value;
    }

    /**
     * Gets the value of the searchForCollaterals property.
     * 
     */
    public boolean isSearchForCollaterals() {
        return searchForCollaterals;
    }

    /**
     * Sets the value of the searchForCollaterals property.
     * 
     */
    public void setSearchForCollaterals(boolean value) {
        this.searchForCollaterals = value;
    }

}
