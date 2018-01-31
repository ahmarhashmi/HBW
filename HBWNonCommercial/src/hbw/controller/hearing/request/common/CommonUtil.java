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
import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Ahmar
 *
 */
public final class CommonUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

    private static ObjectMapper mapper = new ObjectMapper();

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

    public static String scanAndConvertFilesToTiff(FileValidationRequestDTO dto) {
	// String jsonResponse = "";
	try {
	    URL url = new URL(Resource.VANGAURD_VIRUS_SCAN_URL.getValue());
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
	    LOGGER.info("Request json for files validation is :{}", payload);

	    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    StringBuffer jsonString = new StringBuffer();
	    String line;
	    while ((line = br.readLine()) != null) {
		jsonString.append(line);
	    }
	    LOGGER.info("Response from the server is :{}", jsonString.toString());
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
