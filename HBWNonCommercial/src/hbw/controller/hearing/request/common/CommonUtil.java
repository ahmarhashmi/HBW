/**
 * 
 */
package hbw.controller.hearing.request.common;

import hbw.controller.hearing.request.model.DeleteSummon;
import hbw.controller.hearing.request.model.FileValidationRequestDTO;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.CookiesAware;
import org.apache.struts2.interceptor.PrincipalAware;
import org.apache.struts2.interceptor.PrincipalProxy;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Ahmar Hashmi
 * 
 *         A common utility class that contains different utility functions to
 *         be used across application.
 */
public final class CommonUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * @author Ahmar Nadeem
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

    /**
     * @author Ahmar Nadeem
     * 
     *         A utility function to get http connection for rest calls.
     * 
     * @param URL
     * @param method
     * @return
     * @throws IOException
     */
    public static HttpsURLConnection getConnection(final String URL, final String method) throws IOException {
	URL url = new URL(URL);

	String proxyIp = Resource.PROXY_IP.getValue();
	String port = Resource.PROXY_PORT.getValue();

	HttpsURLConnection conn;
	if (null != proxyIp && proxyIp.trim().length() > 3) {
	    proxyIp = Resource.PROXY_IP.getValue().trim();
	    port = Resource.PROXY_PORT.getValue().trim();
	    LOGGER.info("Using proxy IP:" + proxyIp + ":" + port);
	    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIp, Integer.parseInt(port)));
	    conn = (HttpsURLConnection) url.openConnection(proxy);
	} else {
	    LOGGER.info("No proxy has been set. Opening the connection at " + url);
	    conn = (HttpsURLConnection) url.openConnection();
	}
	conn.setRequestMethod(method);
	conn.setRequestProperty("Accept", "application/json");
	conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

	/*
	 * conn.setRequestProperty("Connection", "Keep-Alive");
	 * conn.setRequestProperty("Keep-Alive", "header");
	 */

	return conn;
    }

    /**
     * @author Ahmar Nadeem
     * 
     *         It scans the files and converts to tiff
     * 
     * @param dto
     * @return
     */
    public static String scanAndConvertFilesToTiff(final FileValidationRequestDTO dto) {

	HttpsURLConnection conn = null;
	OutputStreamWriter writer = null;
	BufferedReader br = null;

	try {
	    conn = getConnection(Resource.VANGAURD_VIRUS_SCAN_URL.getValue(), Constants.POST);

	    conn.setConnectTimeout(0);
	    conn.setReadTimeout(0);

	    // LOGGER.info(" conn.getReadTimeout() == " + conn.getReadTimeout()
	    // + " -- " + conn.getConnectTimeout());

	    conn.setDoInput(true);
	    conn.setDoOutput(true);

	    conn.connect();

	    writer = new OutputStreamWriter(conn.getOutputStream(), Constants.UTF8);

	    String payload = createJsonString(dto);
	    writer.write(payload);

	    writer.close();

	    // FileUtils.writeStringToFile(new File("C:\\payload.txt"),payload);
	    LOGGER.info(
		    "Request made to VGD for summon number " + dto.getSummonsNumber() + " TOKEN: " + dto.getToken());

	    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    StringBuffer jsonString = new StringBuffer();
	    String line;
	    while ((line = br.readLine()) != null) {
		jsonString.append(line);
	    }
	    LOGGER.info(dto.getSummonsNumber() + " CreateHearing response from the VGD server is :"
		    + jsonString.toString());

	    br.close();
	    conn.disconnect();

	    return jsonString.toString();
	} catch (MalformedURLException e) {
	    throw new RuntimeException(e);
	} catch (IOException e) {
	    e.printStackTrace();
	    // throw new
	    // RuntimeException("Server is busy, please try again later.");
	    throw new RuntimeException(e);
	} finally {
	    try {
		if (null != writer)
		    writer.close();
		if (null != br)
		    br.close();
		if (null != conn) {
		    conn.disconnect();
		    conn = null;
		}
	    } catch (Exception e) {
		LOGGER.error("Exception in Finally");
	    }
	}
    }

    public static String deleteSummon(final DeleteSummon ds) {
	try {
	    // LOGGER.info("deleteSummon :" +
	    // Resource.VANGAURD_DELETE_SUMMON_URL.getValue());

	    HttpsURLConnection conn = getConnection(Resource.VANGAURD_DELETE_SUMMON_URL.getValue(), Constants.POST);
	    conn.setDoInput(true);
	    conn.setDoOutput(true);
	    OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), Constants.UTF8);

	    String payload = createJsonString(ds);
	    writer.write(payload);
	    writer.close();
	    // LOGGER.info("Request json for files validation is :" + payload);

	    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    StringBuffer jsonString = new StringBuffer();
	    String line;
	    while ((line = br.readLine()) != null) {
		jsonString.append(line);
	    }
	    LOGGER.info(ds.getSummonsNumber() + " deleteSummon response from the server is :" + jsonString.toString());
	    br.close();
	    conn.disconnect();
	    return jsonString.toString();
	} catch (MalformedURLException e) {
	    throw new RuntimeException(e);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    /**
     * @author Ahmar Nadeem
     * 
     *         validates the session and returns true if session is active, false
     *         otherwise.
     * 
     * @param request
     * @return
     */
    public static boolean isSessionActive(HttpServletRequest requesta) {
	HttpSession session = requesta.getSession(false);

	LOGGER.info("Checking Orignal  session = " + session.getId());

	if (null == session || null == session.getAttribute(Constants.VIOLATION_NUMBER)) {
	    return false;
	}
	return true;
    }

    /**
     * @author Ahmar Nadeem
     * 
     *         Utility to triple encode the provided plain text.
     * 
     * @param plainText
     * @return
     */
    public static String tripleEncodePlainText(String plainText) {
	String encoded = new String();
	try {
	    encoded = DatatypeConverter.printBase64Binary(plainText.getBytes(Constants.UTF8));
	    encoded = new String(DatatypeConverter.printBase64Binary(encoded.getBytes(Constants.UTF8)));
	    encoded = new String(DatatypeConverter.printBase64Binary(encoded.getBytes(Constants.UTF8)));
	} catch (Exception e) {
	    LOGGER.error(e.toString());
	}
	return encoded;
    }

    /**
     * @author Ahmar Nadeem
     * 
     *         Utility to triple decode the provided encoded text.
     * 
     * @param encodedText
     * @return
     */
    public static String tripleDecodePlainText(String encodedText) {
	String decoded = new String();
	try {
	    decoded = new String(DatatypeConverter.parseBase64Binary(encodedText));
	    decoded = new String(DatatypeConverter.parseBase64Binary(decoded));
	    decoded = new String(DatatypeConverter.parseBase64Binary(decoded));
	} catch (Exception e) {
	    LOGGER.error(e.toString());
	}
	return decoded;
    }

    /**
     * @param ds
     * @return
     * @throws JsonProcessingException
     */
    private static String createJsonString(DeleteSummon ds) throws JsonProcessingException {
	return mapper.writeValueAsString(ds);
    }

    /**
     * @param dto
     * @return
     * @throws JsonProcessingException
     */
    private static String createJsonString(FileValidationRequestDTO dto) throws JsonProcessingException {
	return mapper.writeValueAsString(dto);
    }

    /**
     * @param json
     * @return
     * @throws IOException
     */
    public static JsonNode parseJsonStringToObject(String json) throws IOException {
	return mapper.readValue(json, JsonNode.class);
    }

    public static String encryptTextToAES(String key, String initVector, String value) {
	try {
	    IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	    SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	    cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

	    byte[] encrypted = cipher.doFinal(value.getBytes());
	    // LOGGER.info("encrypted string: "+ Base64.encodeBase64String(encrypted));

	    return Base64.encodeBase64String(encrypted);
	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return null;
    }

    public static String decryptTextFromAES(String key, String initVector, String encrypted) {
	try {
	    IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	    SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	    cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

	    byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

	    return new String(original);
	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return null;
    }

    public static String convertSpecialChars(String param) {
	char singleQ1 = 0x2018; // Microsoft Office "Smart" open single quote
				// (int value - 8016)
	char singleQ2 = 0x2019; // Microsoft Office "Smart" close single quote
				// (int value - 8017)
	char doubleQ1 = 0x201C; // Microsoft Office "Smart" open double quote
				// (int value - 8220)
	char doubleQ2 = 0x201D; // Microsoft Office "Smart" close double quote
				// (int value - 8221)
	char singleQ = 0x0027; // ASCII single quote ' (int value - 39)
	char doubleQ = 0x0022; // ASCII double quote " (int value - 34)
	StringBuffer sb = new StringBuffer();
	for (int i = 0; i < param.length(); i++) {
	    char testChar = param.charAt(i);
	    // ** Test for single Smart Quote and convert to ASCII single quote
	    if (testChar == singleQ1 || testChar == singleQ2) {
		testChar = singleQ;
	    }
	    // ** Test for double Smart Quote and convert to ASCII double quote
	    else if (testChar == doubleQ1 || testChar == doubleQ2) {
		testChar = doubleQ;
	    }
	    sb.append(testChar);
	}
	// System.out.println("sb = " + sb.toString() );
	return sb.toString();
    }

    public static void main(String[] args) {

	String key = "HBWNonCommercial"; // 128 bit key
	String initVector = "AbcdefghijkLMNOP"; // 16 bytes IV

	// LOGGER.info(decrypt(key, initVector, encrypt(key, initVector, "vgrdTest")));
	// LOGGER.info(decrypt(key, initVector, encrypt(key, initVector,
	// "YHvkA$36PkLbQ#G")));

	/*
	 * FileValidationRequestDTO dto = new FileValidationRequestDTO(); List<Evidence>
	 * files = new ArrayList<Evidence>();
	 * 
	 * File f1 = new File(
	 * "C:\\Users\\EWS\\Desktop\\HBW\\testFiles\\20cee281-9b53-4927-acba-2da74dbab053-Copy.pdf"
	 * ); File f2 = new File(
	 * "C:\\Users\\EWS\\Desktop\\HBW\\testFiles\\20cee281-9b53-4927-acba-2da74dbab053.pdf"
	 * ); File f3 = new
	 * File("C:\\Users\\EWS\\Desktop\\HBW\\testFiles\\DI7A0103.gif"); File f4 = new
	 * File("C:\\Users\\EWS\\Desktop\\HBW\\testFiles\\DI7A0103X.gif"); String
	 * summonNumber = "1179114478"; try { Evidence evidence = new Evidence();
	 * evidence.setName(f1.getName());
	 * evidence.setExtension(FileUtil.getFileExtension(f1.getName())); evidence
	 * .setContent(FileUtil.encodeFileToBase64Binary(f1)); files.add(evidence);
	 * 
	 * evidence = new Evidence(); evidence.setName(f2.getName());
	 * evidence.setExtension(FileUtil.getFileExtension(f2.getName())); evidence
	 * .setContent(FileUtil.encodeFileToBase64Binary(f2)); files.add(evidence);
	 * 
	 * evidence = new Evidence(); evidence.setName(f3.getName());
	 * evidence.setExtension(FileUtil.getFileExtension(f3.getName())); evidence
	 * .setContent(FileUtil.encodeFileToBase64Binary(f3)); files.add(evidence);
	 * 
	 * evidence = new Evidence(); evidence.setName(f4.getName());
	 * evidence.setExtension(FileUtil.getFileExtension(f4.getName())); evidence
	 * .setContent(FileUtil.encodeFileToBase64Binary(f4)); files.add(evidence); }
	 * catch (IOException e) { LOGGER.error("Exception "+e.getMessage()); }
	 * dto.setFiles(files ); dto.setSummonsNumber(summonNumber);
	 * dto.setToken(JWTUtil.createJWT(summonNumber)); try {
	 * scanAndConvertFilesToTiff(dto ); }catch( Exception e ) {
	 * LOGGER.error("Exception "+e.getMessage()); e.printStackTrace(); }
	 */

    }
}