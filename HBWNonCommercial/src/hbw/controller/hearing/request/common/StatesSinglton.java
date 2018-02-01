/**
 * 
 */
package hbw.controller.hearing.request.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ahmar Hashmi
 *
 */
public class StatesSinglton {

    private static List<String> states = populateStates();

    private StatesSinglton() {

    }

    public static List<String> getStates() {
	return states;
    }

    private static List<String> populateStates() {

	List<String> states = new ArrayList<String>();
	states.add("Newfoundland and Labrador");
	states.add("Prince Edward Island");
	states.add("Nova Scotia");
	states.add("New Brunswick");
	states.add("Quebec");
	states.add("Ontario");
	states.add("Manitoba");
	states.add("Saskatchewan");
	states.add("Alberta");
	states.add("British Columbia");
	states.add("Yukon");
	states.add("Northwest Territories");
	states.add("Nunavut");
	states.add("Alabama");
	states.add("Alaska");
	states.add("Arizona");
	states.add("Arkansas");
	states.add("California");
	states.add("Colorado");
	states.add("Connecticut");
	states.add("Delaware");
	states.add("Florida");
	states.add("Georgia");
	states.add("Hawaii");
	states.add("Idaho");
	states.add("Illinois");
	states.add("Indiana");
	states.add("Iowa");
	states.add("Kansas");
	states.add("Kentucky");
	states.add("Louisiana");
	states.add("Maine");
	states.add("Maryland");
	states.add("Massachusetts");
	states.add("Michigan");
	states.add("Minnesota");
	states.add("Mississippi");
	states.add("Missouri");
	states.add("Montana");
	states.add("Nebraska");
	states.add("Nevada");
	states.add("New Hampshire");
	states.add("New Jersey");
	states.add("New Mexico");
	states.add("New York");
	states.add("North Carolina");
	states.add("North Dakota");
	states.add("Ohio");
	states.add("Oklahoma");
	states.add("Oregon");
	states.add("Pennsylvania");
	states.add("Rhode Island");
	states.add("South Carolina");
	states.add("South Dakota");
	states.add("Tennessee");
	states.add("Texas");
	states.add("Utah");
	states.add("Vermont");
	states.add("Virginia");
	states.add("Washington");
	states.add("West Virginia");
	states.add("Wisconsin");
	states.add("Wyoming");
	// Commonwealth/Territory:
	states.add("American Samoa");
	states.add("District of Columbia");
	states.add("Federated States of Micronesia");
	states.add("Guam");
	states.add("Marshall Islands");
	states.add("Northern Mariana Islands");
	states.add("Palau");
	states.add("Puerto Rico");
	states.add("Virgin Islands");

	return states;
    }

}
