
package org.example.nycservmobileapp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Vehicle complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Vehicle"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="vehicleMake" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="vehicleModel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="vehicleColor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="vehiclePlate" type="{http://www.example.org/NYCServMobileApp/}LicensePlate"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Vehicle", propOrder = {
    "vehicleMake",
    "vehicleModel",
    "vehicleColor",
    "vehiclePlate"
})
public class Vehicle {

    @XmlElement(required = true)
    protected String vehicleMake;
    @XmlElement(required = true)
    protected String vehicleModel;
    @XmlElement(required = true)
    protected String vehicleColor;
    @XmlElement(required = true)
    protected LicensePlate vehiclePlate;

    /**
     * Gets the value of the vehicleMake property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleMake() {
        return vehicleMake;
    }

    /**
     * Sets the value of the vehicleMake property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleMake(String value) {
        this.vehicleMake = value;
    }

    /**
     * Gets the value of the vehicleModel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleModel() {
        return vehicleModel;
    }

    /**
     * Sets the value of the vehicleModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleModel(String value) {
        this.vehicleModel = value;
    }

    /**
     * Gets the value of the vehicleColor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleColor() {
        return vehicleColor;
    }

    /**
     * Sets the value of the vehicleColor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleColor(String value) {
        this.vehicleColor = value;
    }

    /**
     * Gets the value of the vehiclePlate property.
     * 
     * @return
     *     possible object is
     *     {@link LicensePlate }
     *     
     */
    public LicensePlate getVehiclePlate() {
        return vehiclePlate;
    }

    /**
     * Sets the value of the vehiclePlate property.
     * 
     * @param value
     *     allowed object is
     *     {@link LicensePlate }
     *     
     */
    public void setVehiclePlate(LicensePlate value) {
        this.vehiclePlate = value;
    }

}
