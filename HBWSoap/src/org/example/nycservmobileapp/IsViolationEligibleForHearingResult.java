
package org.example.nycservmobileapp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for isViolationEligibleForHearingResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="isViolationEligibleForHearingResult"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="feedBack" type="{http://www.example.org/NYCServMobileApp/}FeedBack"/&gt;
 *         &lt;element name="violationEligibleForHearing" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="violationNotEligibleForHearingReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="inJudgment" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "isViolationEligibleForHearingResult", propOrder = {
    "feedBack",
    "violationEligibleForHearing",
    "violationNotEligibleForHearingReason",
    "inJudgment"
})
public class IsViolationEligibleForHearingResult {

    @XmlElement(required = true)
    protected FeedBack feedBack;
    protected boolean violationEligibleForHearing;
    protected String violationNotEligibleForHearingReason;
    protected Boolean inJudgment;

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
     * Gets the value of the violationEligibleForHearing property.
     * 
     */
    public boolean isViolationEligibleForHearing() {
        return violationEligibleForHearing;
    }

    /**
     * Sets the value of the violationEligibleForHearing property.
     * 
     */
    public void setViolationEligibleForHearing(boolean value) {
        this.violationEligibleForHearing = value;
    }

    /**
     * Gets the value of the violationNotEligibleForHearingReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViolationNotEligibleForHearingReason() {
        return violationNotEligibleForHearingReason;
    }

    /**
     * Sets the value of the violationNotEligibleForHearingReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViolationNotEligibleForHearingReason(String value) {
        this.violationNotEligibleForHearingReason = value;
    }

    /**
     * Gets the value of the inJudgment property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isInJudgment() {
        return inJudgment;
    }

    /**
     * Sets the value of the inJudgment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInJudgment(Boolean value) {
        this.inJudgment = value;
    }

}
