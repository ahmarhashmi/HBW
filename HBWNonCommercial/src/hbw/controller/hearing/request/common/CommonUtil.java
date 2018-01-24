/**
 * 
 */
package hbw.controller.hearing.request.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Ahmar
 *
 */
public final class CommonUtil {

    /**
     * 
     * @param cal
     * @return
     */
    public static Date convertGeorgeonDateToDate(XMLGregorianCalendar cal) {
	try {
	    XMLGregorianCalendar xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal.toString());
	    return xmlCalendar.toGregorianCalendar().getTime();
	} catch (DatatypeConfigurationException e) {
	    return null;
	}
    }

    public static void validateAndConvertFilesToTiff(FileValidationRequestDTO dto) {
	// String jsonResponse = "";
	try {
	    URL url = new URL("https://hbwmobileuat.vgdinbox.net/process.php");
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("POST");
	    conn.setDoInput(true);
	    conn.setDoOutput(true);
	    conn.setRequestProperty("Accept", "application/json");
	    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	    OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");

	    String payload = createJsonString(dto);
	    writer.write(payload);
	    writer.close();
	    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    StringBuffer jsonString = new StringBuffer();
	    String line;
	    while ((line = br.readLine()) != null) {
		jsonString.append(line);
	    }
	    br.close();
	    conn.disconnect();

	    // if (conn.getResponseCode() != 200) {
	    // throw new RuntimeException("Failed : HTTP error code : " +
	    // conn.getResponseCode());
	    // }
	    //
	    // BufferedReader br = new BufferedReader(new
	    // InputStreamReader((conn.getInputStream())));
	    //
	    // String output;
	    // System.out.println("Output from Server .... \n");
	    // while ((output = br.readLine()) != null) {
	    // jsonResponse += output;
	    // }
	    //
	    // conn.disconnect();

	} catch (MalformedURLException e) {
	    throw new RuntimeException(e);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private static String createJsonString(FileValidationRequestDTO dto) throws JsonProcessingException {
	ObjectMapper mapper = new ObjectMapper();
	return mapper.writeValueAsString(dto);
    }

    /**
     * Utility function to create a JWT using violation/summon number
     * 
     * @param violationNumber
     * @return
     */
    public static String createJWT(String violationNumber) {

	// The JWT signature algorithm we will be using to sign the token
	SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	// We will sign our JWT with our ApiKey secret
	byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Constants.JWT_SECRET_KEY);
	Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

	Map<String, Object> claim = new HashMap<String, Object>();
	claim.put(Constants.SUMMON, violationNumber);
	// Let's set the JWT Claims
	JwtBuilder builder = Jwts.builder().addClaims(claim).signWith(signatureAlgorithm, signingKey);

	// Builds the JWT and serializes it to a compact, URL-safe string
	return builder.compact();
    }

    // Sample method to validate and read the JWT
    public static void parseJWT(String jwt) {

	// This line will throw an exception if it is not a signed JWS (as expected)
	Claims claims = Jwts.parser()
		.setSigningKey(DatatypeConverter.parseBase64Binary("TheNewYorkCityDepartmentOfFinance"))
		.parseClaimsJws(jwt).getBody();
	System.out.println("ID: " + claims.getId());
	System.out.println("Subject: " + claims.getSubject());
	System.out.println("Issuer: " + claims.getIssuer());
	System.out.println("Expiration: " + claims.getExpiration());
    }
}
