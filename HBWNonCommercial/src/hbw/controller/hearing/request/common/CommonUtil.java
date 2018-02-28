/**
 * 
 */
package hbw.controller.hearing.request.common;

import hbw.controller.hearing.request.model.FileValidationRequestDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

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
    public static HttpURLConnection getConnection(final String URL, final String method) throws IOException {
	URL url = new URL(URL);	
	
	/*
	Properties sysProperties = System.getProperties();
	
	String proxyHost = "bcpxy.nycnet";
	//String proxyHost = "10.141.22.15";	
	String proxyPort = "8080";
	String proxySet = "true";

	sysProperties.put("http.proxyHost", proxyHost);
	sysProperties.put("http.proxyPort", proxyPort);
	sysProperties.put("proxySet", proxySet);
	*/
	
	String proxyIp = Resource.PROXY_IP.getValue();
	String port = Resource.PROXY_PORT.getValue();
	
	HttpURLConnection conn;
	if(proxyIp != null ){
		LOGGER.info("Using proxy IP:"+proxyIp+":"+port);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIp,Integer.parseInt(port)));	
		conn = (HttpURLConnection) url.openConnection(proxy);
	} else{
		LOGGER.info("No proxy has been set. Opening the connection at "+url);
		conn = (HttpURLConnection) url.openConnection();
	}
	conn.setRequestMethod(method);
	conn.setRequestProperty("Accept", "application/json");
	conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	
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
	try {		
    	
	    HttpURLConnection conn = getConnection(Resource.VANGAURD_VIRUS_SCAN_URL.getValue(), Constants.POST);	    
	    conn.setDoInput(true);
	    conn.setDoOutput(true);
	    OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), Constants.UTF8);

	    String payload = createJsonString(dto);
	    writer.write(payload);
	    writer.close();
	    LOGGER.info("Request json for files validation is :" + payload);

	    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    StringBuffer jsonString = new StringBuffer();
	    String line;
	    while ((line = br.readLine()) != null) {
		jsonString.append(line);
	    }
	    LOGGER.info("Response from the server is :" + jsonString.toString());
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
    public static boolean isSessionActive(HttpServletRequest request) {
	HttpSession session = request.getSession();
	if (session == null || session.getAttribute(Constants.VIOLATION_NUMBER) == null) {
	    return false;
	}
	return true;
    }

    /**
     * @author Ahmar Nadeem
     * 
     *         Utility to triple encode the provided violation number for view
     *         ticket URL.
     * 
     * @param violationNumber
     * @return
     */
    public static String tripleEncodeViolationNumber(String violationNumber) {
	String encoded = new String();
	try {
	    encoded = DatatypeConverter.printBase64Binary(violationNumber.getBytes(Constants.UTF8));
	    encoded = new String(DatatypeConverter.printBase64Binary(encoded.getBytes(Constants.UTF8)));
	    encoded = new String(DatatypeConverter.printBase64Binary(encoded.getBytes(Constants.UTF8)));
	} catch (Exception e) {
	    LOGGER.error(e.toString());
	}
	return encoded;
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
}
