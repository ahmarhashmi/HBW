/**
 * 
 */
package hbw.controller.hearing.request.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ahmar
 *
 */
public class ViolationInfo {

    private String vehiclePlate;
    private String vehicleType;
    private String vehicleState;
    private String vehicleMake;

    private String violationNumber;
    private Date issuedOn;
    private String description;
    private String code;
    private String location;

    private BigDecimal fine;
    private BigDecimal penalty;
    private BigDecimal interest;
    private BigDecimal reduction;
    private BigDecimal paid;
    private BigDecimal balanceDue;

    /**
     * @return the vehiclePlate
     */
    public String getVehiclePlate() {
	return vehiclePlate;
    }

    /**
     * @param vehiclePlate
     *            the vehiclePlate to set
     */
    public void setVehiclePlate(String vehiclePlate) {
	this.vehiclePlate = vehiclePlate;
    }

    /**
     * @return the vehicleType
     */
    public String getVehicleType() {
	return vehicleType;
    }

    /**
     * @param vehicleType
     *            the vehicleType to set
     */
    public void setVehicleType(String vehicleType) {
	this.vehicleType = vehicleType;
    }

    /**
     * @return the vehicleState
     */
    public String getVehicleState() {
	return vehicleState;
    }

    /**
     * @param vehicleState
     *            the vehicleState to set
     */
    public void setVehicleState(String vehicleState) {
	this.vehicleState = vehicleState;
    }

    /**
     * @return the vehicleMake
     */
    public String getVehicleMake() {
	return vehicleMake;
    }

    /**
     * @param vehicleMake
     *            the vehicleMake to set
     */
    public void setVehicleMake(String vehicleMake) {
	this.vehicleMake = vehicleMake;
    }

    /**
     * @return the violationNumber
     */
    public String getViolationNumber() {
	return violationNumber;
    }

    /**
     * @param violationNumber
     *            the violationNumber to set
     */
    public void setViolationNumber(String violationNumber) {
	this.violationNumber = violationNumber;
    }

    /**
     * @return the issuedOn
     */
    public String getIssuedOn() {
	if (issuedOn != null) {
	    SimpleDateFormat df = new SimpleDateFormat("mm/dd/yyyy");
	    return df.format(issuedOn);
	}
	return "";
    }

    /**
     * @param issuedOn
     *            the issuedOn to set
     */
    public void setIssuedOn(Date issuedOn) {
	this.issuedOn = issuedOn;
    }

    /**
     * @return the description
     */
    public String getDescription() {
	return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * @return the code
     */
    public String getCode() {
	return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
	this.code = code;
    }

    /**
     * @return the location
     */
    public String getLocation() {
	return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(String location) {
	this.location = location;
    }

    /**
     * @return the fine
     */
    public BigDecimal getFine() {
	if (fine == null) {
	    return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	}
	return fine.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * @param fine
     *            the fine to set
     */
    public void setFine(BigDecimal fine) {
	this.fine = fine;
    }

    /**
     * @return the penalty
     */
    public BigDecimal getPenalty() {
	if (penalty == null) {
	    return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	}
	return penalty.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * @param penalty
     *            the penalty to set
     */
    public void setPenalty(BigDecimal penalty) {
	this.penalty = penalty;
    }

    /**
     * @return the interest
     */
    public BigDecimal getInterest() {
	if (interest == null) {
	    return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	}
	return interest.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * @param interest
     *            the interest to set
     */
    public void setInterest(BigDecimal interest) {
	this.interest = interest;
    }

    /**
     * @return the reduction
     */
    public BigDecimal getReduction() {
	if (reduction == null) {
	    return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	}
	return reduction.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * @param reduction
     *            the reduction to set
     */
    public void setReduction(BigDecimal reduction) {
	this.reduction = reduction;
    }

    /**
     * @return the paid
     */
    public BigDecimal getPaid() {
	if (paid == null) {
	    return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	}
	return paid.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * @param paid
     *            the paid to set
     */
    public void setPaid(BigDecimal paid) {
	this.paid = paid;
    }

    /**
     * @return the balanceDue
     */
    public BigDecimal getBalanceDue() {
	if (balanceDue == null) {
	    return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	}
	return balanceDue.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * @param balanceDue
     *            the balanceDue to set
     */
    public void setBalanceDue(BigDecimal balanceDue) {
	this.balanceDue = balanceDue;
    }

}
