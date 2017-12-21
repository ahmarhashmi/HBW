
package org.example.nycservmobileapp;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getViolationInfoForPlateResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getViolationInfoForPlateResult"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="feedBack" type="{http://www.example.org/NYCServMobileApp/}FeedBack"/&gt;
 *         &lt;element name="parkingViolations" type="{http://www.example.org/NYCServMobileApp/}ParkingViolation" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getViolationInfoForPlateResult", propOrder = {
    "feedBack",
    "parkingViolations"
})
public class GetViolationInfoForPlateResult {

    @XmlElement(required = true)
    protected FeedBack feedBack;
    protected List<ParkingViolation> parkingViolations;

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
     * Gets the value of the parkingViolations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parkingViolations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParkingViolations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParkingViolation }
     * 
     * 
     */
    public List<ParkingViolation> getParkingViolations() {
        if (parkingViolations == null) {
            parkingViolations = new ArrayList<ParkingViolation>();
        }
        return this.parkingViolations;
    }

}
