
package org.example.nycservmobileapp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getViolationInfoResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getViolationInfoResult"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="feedBack" type="{http://www.example.org/NYCServMobileApp/}FeedBack"/&gt;
 *         &lt;element name="violation" type="{http://www.example.org/NYCServMobileApp/}ParkingViolation" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getViolationInfoResult", propOrder = {
    "feedBack",
    "violation"
})
public class GetViolationInfoResult {

    @XmlElement(required = true)
    protected FeedBack feedBack;
    protected ParkingViolation violation;

    /**
     * Gets the value of the feedBack property.
     * 
     * @return
     *     possible object is
     *     {@link FeedBack }
     *     
     */
    public FeedBack getFeedBack() {
        return feedBack;
    }

    /**
     * Sets the value of the feedBack property.
     * 
     * @param value
     *     allowed object is
     *     {@link FeedBack }
     *     
     */
    public void setFeedBack(FeedBack value) {
        this.feedBack = value;
    }

    /**
     * Gets the value of the violation property.
     * 
     * @return
     *     possible object is
     *     {@link ParkingViolation }
     *     
     */
    public ParkingViolation getViolation() {
        return violation;
    }

    /**
     * Sets the value of the violation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParkingViolation }
     *     
     */
    public void setViolation(ParkingViolation value) {
        this.violation = value;
    }

}
