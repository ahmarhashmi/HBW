/**
 * 
 */
package hbw.controller.hearing.request.common;

import hbw.controller.hearing.request.model.States;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ahmar Hashmi
 *
 */
public class StatesSinglton {

    private static List<States> states = populateStates();

    private StatesSinglton() {

    }

    public static List<States> getStates() {
	return states;
    }

    private static List<States> populateStates() {

	List<States> states = new ArrayList<States>();
	states.add(new States("NL","Newfoundland and Labrador"));
	states.add(new States("PE","Prince Edward Island"));
	states.add(new States("NS","Nova Scotia"));
	states.add(new States("NB","New Brunswick"));
	states.add(new States("QC","Quebec"));
	states.add(new States("ON","Ontario"));
	states.add(new States("MB","Manitoba"));
	states.add(new States("SK","Saskatchewan"));
	states.add(new States("AB","Alberta"));
	states.add(new States("BC","British Columbia"));
	states.add(new States("YT","Yukon"));
	states.add(new States("NT","Northwest Territories"));
	states.add(new States("NU","Nunavut"));
	states.add(new States("AL","Alabama"));
	states.add(new States("AK","Alaska"));
	states.add(new States("AZ","Arizona"));
	states.add(new States("AR","Arkansas"));
	states.add(new States("CA","California"));
	states.add(new States("CO","Colorado"));
	states.add(new States("CT","Connecticut"));
	states.add(new States("DE","Delaware"));
	states.add(new States("FL","Florida"));
	states.add(new States("GA","Georgia"));
	states.add(new States("HI","Hawaii"));
	states.add(new States("ID","Idaho"));
	states.add(new States("IL","Illinois"));
	states.add(new States("IN","Indiana"));
	states.add(new States("IA","Iowa"));
	states.add(new States("KS","Kansas"));
	states.add(new States("KY","Kentucky"));
	states.add(new States("LA","Louisiana"));
	states.add(new States("ME","Maine"));
	states.add(new States("MD","Maryland"));
	states.add(new States("MA","Massachusetts"));
	states.add(new States("MI","Michigan"));
	states.add(new States("MN","Minnesota"));
	states.add(new States("MS","Mississippi"));
	states.add(new States("MO","Missouri"));
	states.add(new States("MT","Montana"));
	states.add(new States("NE","Nebraska"));
	states.add(new States("NV","Nevada"));
	states.add(new States("NH","New Hampshire"));
	states.add(new States("NJ","New Jersey"));
	states.add(new States("NM","New Mexico"));
	states.add(new States("NY","New York"));
	states.add(new States("NC","North Carolina"));
	states.add(new States("ND","North Dakota"));
	states.add(new States("OH","Ohio"));
	states.add(new States("OK","Oklahoma"));
	states.add(new States("OR","Oregon"));
	states.add(new States("PA","Pennsylvania"));
	states.add(new States("RI","Rhode Island"));
	states.add(new States("SC","South Carolina"));
	states.add(new States("SD","South Dakota"));
	states.add(new States("TN","Tennessee"));
	states.add(new States("TX","Texas"));
	states.add(new States("UT","Utah"));
	states.add(new States("VT","Vermont"));
	states.add(new States("VA","Virginia"));
	states.add(new States("WA","Washington"));
	states.add(new States("WV","West Virginia"));
	states.add(new States("WI","Wisconsin"));
	states.add(new States("WY","Wyoming"));
	// Commonwealth/Territory:
	states.add(new States("AS","American Samoa"));
	states.add(new States("DC","District of Columbia"));
	states.add(new States("FM","Federated States of Micronesia"));
	states.add(new States("GU","Guam"));
	states.add(new States("MH","Marshall Islands"));
	states.add(new States("MP","Northern Mariana Islands"));
	states.add(new States("PW","Palau"));
	states.add(new States("PR","Puerto Rico"));
	states.add(new States("VI","Virgin Islands"));

	return states;
    }

}
