/**
 * 
 */
package hbw.rest.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ahmar
 *
 */
@RestController
@RequestMapping("/state")
public class StatesController {

    @RequestMapping(name = "/states", method = RequestMethod.GET)
    ResponseEntity<List<String>> populateStatesList() {
	List<String> states = new ArrayList<String>();
	states.add("New York");
	states.add("New Jersey");
	states.add("New Hampshire");
	states.add("California");
	states.add("Virgin Islands");
	return new ResponseEntity<List<String>>(states, HttpStatus.OK);
    }

}
