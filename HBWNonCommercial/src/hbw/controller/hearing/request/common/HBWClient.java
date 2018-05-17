package hbw.controller.hearing.request.common;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;

import org.example.nycservmobileapp.Credentials;
import org.example.nycservmobileapp.GetViolationInfoForPlateRequest;
import org.example.nycservmobileapp.GetViolationInfoForPlateResult;
import org.example.nycservmobileapp.GetViolationInfoRequest;
import org.example.nycservmobileapp.GetViolationInfoResult;
import org.example.nycservmobileapp.IsViolationEligibleForHearingRequest;
import org.example.nycservmobileapp.IsViolationEligibleForHearingResult;
import org.example.nycservmobileapp.IsViolationInSystemRequest;
import org.example.nycservmobileapp.IsViolationInSystemResult;
import org.example.nycservmobileapp.Location;
import org.example.nycservmobileapp.NYCServMobileApp;
import org.example.nycservmobileapp.NYCServMobileApp_Service;
import org.example.nycservmobileapp.ParkingViolation;
import org.example.nycservmobileapp.Vehicle;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import hbw.controller.hearing.request.model.ViolationInfo;

/**
 * This class was generated by Apache CXF 3.0.6 2017-12-18T20:20:23.990-05:00
 * Generated source version: 3.0.6
 * 
 */
public final class HBWClient {

	private static Logger LOGGER = LoggerFactory.getLogger(HBWClient.class);

	private static final QName SERVICE_NAME = new QName(
			"http://www.example.org/NYCServMobileApp/", "NYCServMobileApp");

	private HBWClient() {
	}

	/**
	 * 
	 * @param violationNumber
	 * @return
	 * @throws MalformedURLException
	 */
	public static boolean isViolationInSystem(String violationNumber)
			throws MalformedURLException {
		NYCServMobileApp port = getConnection();

		LOGGER.debug("Invoking isViolationInSystem...");
		IsViolationInSystemRequest request = new IsViolationInSystemRequest();
		request.setCredentials(getCredentials());
		request.setViolationNumber(violationNumber);
		request.setWebServiceChannel(Resource.WS_CHANNEL.getValue());

		IsViolationInSystemResult response = port.isViolationInSystem(request);
		LOGGER.info("isViolationInSystem.result= "
				+ String.valueOf(response.isViolationInSystem()));

		return response.isViolationInSystem();
	}

	/**
	 * 
	 * @param violationNumber
	 * @return
	 * @throws MalformedURLException
	 */
	public static boolean isViolationEligibleForHearing(String violationNumber)
			throws MalformedURLException {
		NYCServMobileApp port = getConnection();

		LOGGER.info("Invoking isViolationEligibleForHearing...");
		IsViolationEligibleForHearingRequest request = new IsViolationEligibleForHearingRequest();
		request.setCredentials(getCredentials());
		request.setViolationNumber(violationNumber);
		request.setWebServiceChannel(Resource.WS_CHANNEL.getValue());

		IsViolationEligibleForHearingResult response = port
				.isViolationEligibleForHearing(request);
		LOGGER.info("isViolationEligibleForHearing.result="
				+ response.isViolationEligibleForHearing());

		return response.isViolationEligibleForHearing();
	}

	/**
	 * 
	 * @param violationNumber
	 * @return
	 * @throws MalformedURLException
	 */
	public static String getReasonForViolationNotEligibleForHearing(
			String violationNumber) throws MalformedURLException {
		NYCServMobileApp port = getConnection();

		LOGGER
				.info("Invoking getReasonCodeForViolationNotEligibleForHearing...");
		IsViolationEligibleForHearingRequest request = new IsViolationEligibleForHearingRequest();
		request.setCredentials(getCredentials());
		request.setViolationNumber(violationNumber);
		request.setWebServiceChannel(Resource.WS_CHANNEL.getValue());

		IsViolationEligibleForHearingResult response = port
				.isViolationEligibleForHearing(request);
		LOGGER.info("isViolationNotEligibleForHearing.result="
				+ response.getViolationNotEligibleForHearingReason());

		return response.getViolationNotEligibleForHearingReason();
	}

	/**
	 * Populates the violation info by calling the soap webservice and parses
	 * the response to the ViolationInfo.
	 * 
	 * @param violationNumber
	 * @return
	 * @throws MalformedURLException
	 */
	public static ViolationInfo getViolationInfo(String violationNumber)
			throws MalformedURLException {
		NYCServMobileApp port = getConnection();
		LOGGER.debug("Invoking getViolationInfo...");
		GetViolationInfoRequest request = new GetViolationInfoRequest();
		request.setCredentials(getCredentials());
		request.setViolationNumber(violationNumber);
		request.setWebServiceChannel(Resource.WS_CHANNEL.getValue());

		GetViolationInfoResult response = port.getViolationInfo(request);
		ParkingViolation violation = response.getViolation();
		if (violation != null) {
			return parseToViolationInfo(violation);
		}
		return null;
	}

	/**
	 * 
	 * @param violationNumber
	 * @return
	 * @throws MalformedURLException
	 */
	public static ViolationInfo getViolationInfoByPlateNubmer(
			String violationNumber) throws MalformedURLException {
		NYCServMobileApp port = getConnection();
		LOGGER.debug("Invoking getViolationInfoForPlate...");
		GetViolationInfoForPlateRequest request = new GetViolationInfoForPlateRequest();
		request.setCredentials(getCredentials());
		request.setPlateNumber(violationNumber);
		request.setWebServiceChannel(Resource.WS_CHANNEL.getValue());

		GetViolationInfoForPlateResult response = port
				.getViolationInfoForPlate(request);
		List<ParkingViolation> violations = response.getParkingViolations();

		// FIXME: Need to make sure how will we sort out which violation is the
		// user
		// going to contest for
		/** DECIDED: We will not consider Vehicle Plate Number */
		return parseToViolationInfo(violations.get(0));
	}

	/**
	 * 
	 * @param violation
	 * @return
	 */
	private static ViolationInfo parseToViolationInfo(ParkingViolation violation) {
		ViolationInfo info = new ViolationInfo();
		info.setViolationNumber(violation.getViolationNumber());
		info.setViolationStatusInJudgment(violation
				.isViolationStatusInJudgment());

		Vehicle veh = violation.getIssuedToVehicle();
		if (veh != null) {
			info.setVehicleType(veh.getVehiclePlate().getPlateType());
			info.setVehicleMake(veh.getVehicleMake());
			info.setVehiclePlate(veh.getVehiclePlate().getPlateNumber());
			info.setVehicleState(veh.getVehiclePlate().getPlateState());
		}

		info.setBalanceDue(violation.getAmountDue());
		info.setPaid(violation.getAmountPaid());
		info.setPenalty(violation.getAmountPenalty());
		info.setReduction(violation.getAmountReduction());
		info.setFine(violation.getAmountFine());
		info.setInterest(violation.getAmountInterest());

		info.setCode(violation.getViolationCode());
		info.setDescription(violation.getViolationDescription());
		info.setIssuedOn(CommonUtil.convertGeorgeonDateToDate(violation
				.getIssueDate()));
		info.setIssueTime(violation.getIssueTime());
		Location location = violation.getViolationLocation();
		if (location != null) {
			info.setLocation(location.getStreetAddress() + ", "
					+ location.getAddressModifier() + ", "
					+ location.getCounty());
		}

		return info;
	}

	/**
	 * Creates the connection by using the wsdl URL configured in the properties
	 * file.
	 * 
	 * @return
	 * @throws MalformedURLException
	 */
	public static NYCServMobileApp getConnection() throws MalformedURLException {
		URL wsdlURL = new URL(Resource.WSDL_URI.getValue());
		NYCServMobileApp_Service ss = new NYCServMobileApp_Service(wsdlURL,
				SERVICE_NAME);
		return ss.getNYCServMobileAppSOAP();
	}

	private static Credentials cred = null;

	/**
	 * 
	 * @return
	 */
	public static Credentials getCredentials() {
		if (null == cred) {
			cred = new Credentials();
			cred.setUserID(Resource.USER_ID.getValue());
			cred.setPassword(CommonUtil.decryptTextFromAES(Constants.AES_KEY,
					Constants.AES_INIT_VECTOR, Resource.PASSWORD.getValue()));
			// cred.setPassword(CommonUtil.tripleDecodePlainText(Resource.PASSWORD.getValue()));
			// LOGGER.info(cred.getUserID()+" -- "+cred.getPassword());
		}
		return cred;
	}

}
