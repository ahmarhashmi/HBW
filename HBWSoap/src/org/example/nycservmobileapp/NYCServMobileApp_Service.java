package org.example.nycservmobileapp;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.0.6
 * 2017-12-18T21:50:24.108-05:00
 * Generated source version: 3.0.6
 * 
 */
@WebServiceClient(name = "NYCServMobileApp", 
                  wsdlLocation = "hbw.wsdl",
                  targetNamespace = "http://www.example.org/NYCServMobileApp/") 
public class NYCServMobileApp_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.example.org/NYCServMobileApp/", "NYCServMobileApp");
    public final static QName NYCServMobileAppSOAP = new QName("http://www.example.org/NYCServMobileApp/", "NYCServMobileAppSOAP");
    static {
        URL url = NYCServMobileApp_Service.class.getResource("hbw.wsdl");
        if (url == null) {
            url = NYCServMobileApp_Service.class.getClassLoader().getResource("hbw.wsdl");
        } 
        if (url == null) {
            java.util.logging.Logger.getLogger(NYCServMobileApp_Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "hbw.wsdl");
        }       
        WSDL_LOCATION = url;
    }

    public NYCServMobileApp_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public NYCServMobileApp_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public NYCServMobileApp_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public NYCServMobileApp_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public NYCServMobileApp_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public NYCServMobileApp_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName);
    }    

    /**
     *
     * @return
     *     returns NYCServMobileApp
     */
    @WebEndpoint(name = "NYCServMobileAppSOAP")
    public NYCServMobileApp getNYCServMobileAppSOAP() {
        return super.getPort(NYCServMobileAppSOAP, NYCServMobileApp.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns NYCServMobileApp
     */
    @WebEndpoint(name = "NYCServMobileAppSOAP")
    public NYCServMobileApp getNYCServMobileAppSOAP(WebServiceFeature... features) {
        return super.getPort(NYCServMobileAppSOAP, NYCServMobileApp.class, features);
    }

}
