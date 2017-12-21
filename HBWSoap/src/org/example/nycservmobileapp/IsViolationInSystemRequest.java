
package org.example.nycservmobileapp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for isViolationInSystemRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="isViolationInSystemRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="credentials" type="{http://www.example.org/NYCServMobileApp/}Credentials"/&gt;
 *         &lt;element name="webServiceChannel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="violationNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "isViolationInSystemRequest", propOrder = {
    "credentials",
    "webServiceChannel",
    "violationNumber"
})
public class IsViolationInSystemRequest {

    @XmlElement(required = true)
    protected Credentials credentials;
    @XmlElement(required = true)
    protected String webServiceChannel;
    @XmlElement(required = true)
    protected String violationNumber;

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

}
