/**
 * 
 */
package anh.struts2.annotated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author Ahmar
 *
 */
public class StatesSinglton {

    private static List<String> states = new ArrayList<String>();

    static {
	states.addAll(populateStates());
	// states.add("New York");
	// states.add("New Jersey");
	// states.add("New Hampshire");
	// states.add("California");
	// states.add("Virgin Islands");
    }

    private StatesSinglton() {

    }

    public static List<String> getStates() {
	return states;
    }

    @SuppressWarnings("unchecked")
    private static List<String> populateStates() {
	
	
	List<String> states = new ArrayList<String>();
	states.add("New York");
	states.add("New Jersey");
	states.add("New Hampshire");
	states.add("California");
	states.add("Virgin Islands");
	
	if( Boolean.TRUE )
	    return states;
	
	String jsonResponse = "";
	try {
	    URL url = new URL("http://localhost:8080/violation/");
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Accept", "application/json");

	    if (conn.getResponseCode() != 200) {
		throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
	    }

	    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

	    String output;
	    System.out.println("Output from Server .... \n");
	    while ((output = br.readLine()) != null) {
//		System.out.println(output);
		jsonResponse += output;
	    }

	    conn.disconnect();

	} catch (MalformedURLException e) {
	    throw new RuntimeException(e);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
	
	ObjectMapper parser = new ObjectMapper();
	List<String> result = null;
	try {
	    result = parser.readValue(jsonResponse, List.class);
	} catch (JsonParseException e) {
	    e.printStackTrace();
	} catch (JsonMappingException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return result;
    }
    

//    @PostConstruct
//    protected void init() {
//	Client client = ClientBuilder.newClient();
//        //example query params: ?q=Turku&cnt=10&mode=json&units=metric
//        WebTarget target = client.target(
//                "http://api.openweathermap.org/data/2.5/forecast/daily")
////        	.queryParam("cnt", "10")
//                .queryParam("mode", "json")
////                .queryParam("units", "metric")
////                .queryParam("appid", apikey)
//                ;
//        
//        return target.queryParam("q", place)
//                .request(MediaType.APPLICATION_JSON)
//                .get(List.class);
//    }

}
