/**
 * 
 */
package hbw.controller.hearing.request.common;

import hbw.controller.hearing.request.model.DeleteSummon;
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
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

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

	String proxyIp = Resource.PROXY_IP.getValue();
	String port = Resource.PROXY_PORT.getValue();

	HttpURLConnection conn;
	if (null != proxyIp && proxyIp.trim().length() > 3) {
	    proxyIp = Resource.PROXY_IP.getValue().trim();
	    port = Resource.PROXY_PORT.getValue().trim();
	    LOGGER.info("Using proxy IP:" + proxyIp + ":" + port);
	    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIp, Integer.parseInt(port)));
	    conn = (HttpURLConnection) url.openConnection(proxy);
	} else {
	    LOGGER.info("No proxy has been set. Opening the connection at " + url);
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
	    //LOGGER.info("Request json for files validation is :" + payload);

	    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    StringBuffer jsonString = new StringBuffer();
	    String line;
	    while ((line = br.readLine()) != null) {
		jsonString.append(line);
	    }
	    LOGGER.info(dto.getSummonsNumber() + " CreateHearing response from the VGD server is :" + jsonString.toString());
	    br.close();
	    conn.disconnect();
	    return jsonString.toString();
	} catch (MalformedURLException e) {
	    throw new RuntimeException(e);
	} catch (IOException e) {
		e.printStackTrace();
	    //throw new RuntimeException("Server is busy, please try again later.");
	    throw new RuntimeException(e);
	}
   }
    
    public static String deleteSummon(final DeleteSummon ds) {
    	try {
    		//LOGGER.info("deleteSummon :" + Resource.VANGAURD_DELETE_SUMMON_URL.getValue());
    		 
    	    HttpURLConnection conn = getConnection(Resource.VANGAURD_DELETE_SUMMON_URL.getValue(), Constants.POST);
    	    conn.setDoInput(true);
    	    conn.setDoOutput(true);
    	    OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), Constants.UTF8);

    	    String payload = createJsonString(ds);
    	    writer.write(payload);
    	    writer.close();
    	    //LOGGER.info("Request json for files validation is :" + payload);

    	    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    	    StringBuffer jsonString = new StringBuffer();
    	    String line;
    	    while ((line = br.readLine()) != null) {
    		jsonString.append(line);
    	    }
    	    LOGGER.info(ds.getSummonsNumber() +  " deleteSummon response from the server is :" + jsonString.toString());
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
 }