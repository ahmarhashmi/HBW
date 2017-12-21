
package org.example.nycservmobileapp;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ParkingViolation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ParkingViolation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="violationNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="violationDescription" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="violationStatusDescription" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="violationStatusInJudgment" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="issuedToVehicle" type="{http://www.example.org/NYCServMobileApp/}Vehicle"/&gt;
 *         &lt;element name="amountDue" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="issueDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="issueTime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="amountFine" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="amountPenalty" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="amountInterest" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="amountReduction" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="amountPaid" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="violationCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="violationLocation" type="{http://www.example.org/NYCServMobileApp/}Location"/&gt;
 *         &lt;element name="inHearingLongMessage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="pastDueMessage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lesseeMessage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParkingViolation", propOrder = {
    "violationNumber",
    "violationDescription",
    "violationStatusDescription",
    "violationStatusInJudgment",
    "issuedToVehicle",
    "amountDue",
    "issueDate",
    "issueTime",
    "amountFine",
    "amountPenalty",
    "amountInterest",
    "amountReduction",
    "amountPaid",
    "violationCode",
    "violationLocation",
    "inHearingLongMessage",
    "pastDueMessage",
    "lesseeMessage"
})
public class ParkingViolation {

    @XmlElement(required = true)
    protected String violationNumber;
    @XmlElement(required = true)
    protected String violationDescription;
    @XmlElement(required = true)
    protected String violationStatusDescription;
    protected boolean violationStatusInJudgment;
    @XmlElement(required = true)
    protected Vehicle issuedToVehicle;
    @XmlElement(required = true)
    protected BigDecimal amountDue;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar issueDate;
    @XmlElement(required = true)
    protected String issueTime;
    @XmlElement(required = true)
    protected BigDecimal amountFine;
    @XmlElement(required = true)
    protected BigDecimal amountPenalty;
    @XmlElement(required = true)
    protected BigDecimal amountInterest;
    @XmlElement(required = true)
    protected BigDecimal amountReduction;
    @XmlElement(required = true)
    protected BigDecimal amountPaid;
    @XmlElement(required = true)
    protected String violationCode;
    @XmlElement(required = true)
    protected Location violationLocation;
    @XmlElement(required = true)
    protected String inHearingLongMessage;
    @XmlElement(required = true)
    protected String pastDueMessage;
    @XmlElement(required = true)
    protected String lesseeMessage;

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

    /**
     * Gets the value of the violationDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViolationDescription() {
        return violationDescription;
    }

    /**
     * Sets the value of the violationDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViolationDescription(String value) {
        this.violationDescription = value;
    }

    /**
     * Gets the value of the violationStatusDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViolationStatusDescription() {
        return violationStatusDescription;
    }

    /**
     * Sets the value of the violationStatusDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViolationStatusDescription(String value) {
        this.violationStatusDescription = value;
    }

    /**
     * Gets the value of the violationStatusInJudgment property.
     * 
     */
    public boolean isViolationStatusInJudgment() {
        return violationStatusInJudgment;
    }

    /**
     * Sets the value of the violationStatusInJudgment property.
     * 
     */
    public void setViolationStatusInJudgment(boolean value) {
        this.violationStatusInJudgment = value;
    }

    /**
     * Gets the value of the issuedToVehicle property.
     * 
     * @return
     *     possible object is
     *     {@link Vehicle }
     *     
     */
    public Vehicle getIssuedToVehicle() {
        return issuedToVehicle;
    }

    /**
     * Sets the value of the issuedToVehicle property.
     * 
     * @param value
     *     allowed object is
     *     {@link Vehicle }
     *     
     */
    public void setIssuedToVehicle(Vehicle value) {
        this.issuedToVehicle = value;
    }

    /**
     * Gets the value of the amountDue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmountDue() {
        return amountDue;
    }

    /**
     * Sets the value of the amountDue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmountDue(BigDecimal value) {
        this.amountDue = value;
    }

    /**
     * Gets the value of the issueDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getIssueDate() {
        return issueDate;
    }

    /**
     * Sets the value of the issueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setIssueDate(XMLGregorianCalendar value) {
        this.issueDate = value;
    }

    /**
     * Gets the value of the issueTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssueTime() {
        return issueTime;
    }

    /**
     * Sets the value of the issueTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssueTime(String value) {
        this.issueTime = value;
    }

    /**
     * Gets the value of the amountFine property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmountFine() {
        return amountFine;
    }

    /**
     * Sets the value of the amountFine property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmountFine(BigDecimal value) {
        this.amountFine = value;
    }

    /**
     * Gets the value of the amountPenalty property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmountPenalty() {
        return amountPenalty;
    }

    /**
     * Sets the value of the amountPenalty property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmountPenalty(BigDecimal value) {
        this.amountPenalty = value;
    }

    /**
     * Gets the value of the amountInterest property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmountInterest() {
        return amountInterest;
    }

    /**
     * Sets the value of the amountInterest property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmountInterest(BigDecimal value) {
        this.amountInterest = value;
    }

    /**
     * Gets the value of the amountReduction property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmountReduction() {
        return amountReduction;
    }

    /**
     * Sets the value of the amountReduction property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmountReduction(BigDecimal value) {
        this.amountReduction = value;
    }

    /**
     * Gets the value of the amountPaid property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    /**
     * Sets the value of the amountPaid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmountPaid(BigDecimal value) {
        this.amountPaid = value;
    }

    /**
     * Gets the value of the violationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViolationCode() {
        return violationCode;
    }

    /**
     * Sets the value of the violationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViolationCode(String value) {
        this.violationCode = value;
    }

    /**
     * Gets the value of the violationLocation property.
     * 
     * @return
     *     possible object is
     *     {@link Location }
     *     
     */
    public Location getViolationLocation() {
        return violationLocation;
    }

    /**
     * Sets the value of the violationLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Location }
     *     
     */
    public void setViolationLocation(Location value) {
        this.violationLocation = value;
    }

    /**
     * Gets the value of the inHearingLongMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInHearingLongMessage() {
        return inHearingLongMessage;
    }

    /**
     * Sets the value of the inHearingLongMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInHearingLongMessage(String value) {
        this.inHearingLongMessage = value;
    }

    /**
     * Gets the value of the pastDueMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPastDueMessage() {
        return pastDueMessage;
    }

    /**
     * Sets the value of the pastDueMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPastDueMessage(String value) {
        this.pastDueMessage = value;
    }

    /**
     * Gets the value of the lesseeMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLesseeMessage() {
        return lesseeMessage;
    }

    /**
     * Sets the value of the lesseeMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLesseeMessage(String value) {
        this.lesseeMessage = value;
    }

}
