
package org.example.nycservmobileapp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="in" type="{http://www.example.org/NYCServMobileApp/}getViolationInfoForPlateRequest"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "in"
})
@XmlRootElement(name = "getViolationInfoForPlate")
public class GetViolationInfoForPlate {

    @XmlElement(required = true)
    protected GetViolationInfoForPlateRequest in;

    /**
     * Gets the value of the in property.
     * 
     * @return
     *     possible object is
     *     {@link GetViolationInfoForPlateRequest }
     *     
     */
    public GetViolationInfoForPlateRequest getIn() {
        return in;
    }

    /**
     * Sets the value of the in property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetViolationInfoForPlateRequest }
     *     
     */
    public void setIn(GetViolationInfoForPlateRequest value) {
        this.in = value;
    }

}
