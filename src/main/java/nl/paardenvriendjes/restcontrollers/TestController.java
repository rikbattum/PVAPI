package nl.paardenvriendjes.restcontrollers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	
	// Basic no login test
	
	@CrossOrigin
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome() {// Welcome page, non-rest
		return "Welcome to PVAPI, no login";
	}

	// Basic login test
	
//	@CrossOrigin
	@RequestMapping(value = "/safewelcome", method = RequestMethod.GET)
	public String safewelcome() {// Welcome page, non-rest
		return "Welcome to PVAPI, you are logged in :)!";
	}
	
	
	
}
