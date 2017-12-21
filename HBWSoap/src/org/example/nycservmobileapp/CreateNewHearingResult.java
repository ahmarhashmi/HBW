
package org.example.nycservmobileapp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createNewHearingResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createNewHearingResult"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="feedBack" type="{http://www.example.org/NYCServMobileApp/}FeedBack"/&gt;
 *         &lt;element name="newHearingCreated" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createNewHearingResult", propOrder = {
    "feedBack",
    "newHearingCreated"
})
public class CreateNewHearingResult {

    @XmlElement(required = true)
    protected FeedBack feedBack;
    protected boolean newHearingCreated;

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
     * Gets the value of the newHearingCreated property.
     * 
     */
    public boolean isNewHearingCreated() {
        return newHearingCreated;
    }

    /**
     * Sets the value of the newHearingCreated property.
     * 
     */
    public void setNewHearingCreated(boolean value) {
        this.newHearingCreated = value;
    }

}
