/**
 * 
 */
package hbw.controller.hearing.request.common;

import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

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
}
