
package org.example.nycservmobileapp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for isViolationInSystemResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="isViolationInSystemResult"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="feedBack" type="{http://www.example.org/NYCServMobileApp/}FeedBack"/&gt;
 *         &lt;element name="violationInSystem" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "isViolationInSystemResult", propOrder = {
    "feedBack",
    "violationInSystem"
})
public class IsViolationInSystemResult {

    @XmlElement(required = true)
    protected FeedBack feedBack;
    protected boolean violationInSystem;

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
     * Gets the value of the violationInSystem property.
     * 
     */
    public boolean isViolationInSystem() {
        return violationInSystem;
    }

    /**
     * Sets the value of the violationInSystem property.
     * 
     */
    public void setViolationInSystem(boolean value) {
        this.violationInSystem = value;
    }

}
