/**
 * 
 */
package hbw.controller.hearing.request.common;

import java.util.Properties;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.log4j2.Log4j2LoggerFactory;

/**
 * @author Ahmar Nadeem
 *
 */
public enum Resource {

    EVIDENCE_UPLOAD_LOCATION,
    MAX_NUMBER_OF_EVIDENCES,
    MAX_TOTAL_SIZE_OF_EVIDENCE,
    WSDL_URI,
    USER_ID,
    PASSWORD, 
    WS_CHANNEL,
    

    ;

    private static final String PATH = "config.properties";

    private static final Logger logger = Log4j2LoggerFactory.getLogger(Resource.class);

    private static Properties properties;

    private String value;

    private void init() {
	if (properties == null) {
	    properties = new Properties();
	    try {
		properties.load(Resource.class.getClassLoader().getResourceAsStream(PATH));
	    } catch (Exception e) {
		logger.error("Unable to load " + PATH + " file from classpath.", e);
		System.exit(1);
	    }
	}
	value = (String) properties.get(this.toString());
    }

    public String getValue() {
	if (value == null) {
	    init();
	}
	return value;
    }

}