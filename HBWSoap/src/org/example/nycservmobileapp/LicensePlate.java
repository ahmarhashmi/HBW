
package org.example.nycservmobileapp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LicensePlate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LicensePlate"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="plateNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="plateState" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="plateType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="plateMake" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LicensePlate", propOrder = {
    "plateNumber",
    "plateState",
    "plateType",
    "plateMake"
})
public class LicensePlate {

    @XmlElement(required = true)
    protected String plateNumber;
    @XmlElement(required = true)
    protected String plateState;
    @XmlElement(required = true)
    protected String plateType;
    @XmlElement(required = true)
    protected String plateMake;

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
     * Gets the value of the plateMake property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlateMake() {
        return plateMake;
    }

    /**
     * Sets the value of the plateMake property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlateMake(String value) {
        this.plateMake = value;
    }

}
