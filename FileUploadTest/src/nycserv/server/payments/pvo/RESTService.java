/**
 * 
 */
package nycserv.server.payments.pvo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.persistence.criteria.Root;

/**
 * @author Ahmar
 *
 */
public class RESTService {

    private static final String apiKey = "0ae09eed4f3c024451ads12d1gsgsg1sg";// change this with yours
    private static final String baseBaiduUrl = "http://apis.baidu.com/heweather/weather/free?city=";// The constant url
												    // should be
												    // stored in config
												    // file

    public static Root getWeatherInfo(String cityName) {
	String jsonResult = getWeatherJsonString(cityName);
	Root weatherInfoObject = null;//toEntity(jsonResult);
	return weatherInfoObject;
    }

    // //Covert json string to class object
    // private static Root toEntity(String jsonString)
    // {
    // try{
    // Gson gson = new GsonBuilder().create();
    // Root weatherInfo = gson.fromJson(jsonString, Root.class);
    // return weatherInfo;
    // }
    // catch(JsonSyntaxException ex)
    // {
    // ex.printStackTrace();
    // return null;
    // }
    // }

    // Get the weather of the specific city
    private static String getWeatherJsonString(String cityName) throws RuntimeException {

	// define a variable to store the weather api url and set beijing as it's
	// default value
	String baiduUrl = baseBaiduUrl + "beijing"; // default value hard-coded 'beijing'
						    // should be stored in config file

	try {
	    if (cityName != null && cityName != "")
		baiduUrl = baseBaiduUrl + URLEncoder.encode(cityName, "utf-8");
	} catch (UnsupportedEncodingException e1) {
	    e1.printStackTrace();
	}

	StringBuilder strBuf = new StringBuilder();

	HttpURLConnection conn = null;
	BufferedReader reader = null;
	try {
	    // Declare the connection to weather api url
	    URL url = new URL(baiduUrl);
	    conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Accept", "application/json");
	    conn.setRequestProperty("apikey", apiKey);

	    if (conn.getResponseCode() != 200) {
		throw new RuntimeException("HTTP GET Request Failed with Error code : " + conn.getResponseCode());
	    }

	    // Read the content from the defined connection
	    // Using IO Stream with Buffer raise highly the efficiency of IO
	    reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
	    String output = null;
	    while ((output = reader.readLine()) != null)
		strBuf.append(output);
	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (reader != null) {
		try {
		    reader.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	    if (conn != null) {
		conn.disconnect();
	    }
	}

	return strBuf.toString();
    }
}
