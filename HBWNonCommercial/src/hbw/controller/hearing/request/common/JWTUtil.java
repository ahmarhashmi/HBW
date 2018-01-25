package hbw.controller.hearing.request.common;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import sun.misc.BASE64Encoder;

/**
 * 
 * @author Ahmar Nadeem
 *
 */
public class JWTUtil {

    final static String algorithm = "alg";
    final static String algorithmValue = "HS256";
    final static String tokenType = "typ";
    final static String tokenTypeValue = "JWT";

    /**
     * Utility function to create a JWT using violation/summon number
     * 
     * @param violationNumber
     * @return
     */
    public static String createJWT(String violationNumber) {

	// The JWT signature algorithm we will be using to sign the token
	SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	Map<String, Object> headerMap = new HashMap<String, Object>();
	headerMap.put(algorithm, algorithmValue);
	headerMap.put(tokenType, tokenTypeValue);

	// We will sign our JWT with our ApiKey secret
	String keyBytes = new BASE64Encoder().encode(Constants.JWT_SECRET_KEY.getBytes());
	byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(keyBytes);
	Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

	Map<String, Object> claim = new HashMap<String, Object>();
	claim.put(Constants.SUMMON, violationNumber);
	// Let's set the JWT Claims
	JwtBuilder builder = Jwts.builder().setHeaderParam(tokenType, tokenTypeValue).setClaims(claim)
		.signWith(signatureAlgorithm, signingKey);

	// Builds the JWT and serializes it to a compact, URL-safe string
	return builder.compact();
    }

    // Sample method to validate and read the JWT
    public static void parseJWT(String jwt) {

	// This line will throw an exception if it is not a signed JWS (as expected)
	Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(Constants.JWT_SECRET_KEY))
		.parseClaimsJws(jwt).getBody();
	System.out.println("ID: " + claims.getId());
	System.out.println("Subject: " + claims.getSubject());
	System.out.println("Issuer: " + claims.getIssuer());
	System.out.println("Expiration: " + claims.getExpiration());
    }
}
