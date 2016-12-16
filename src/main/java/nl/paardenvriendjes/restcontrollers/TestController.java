package nl.paardenvriendjes.restcontrollers;

import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.paardenvriendjes.pvapi.daoimpl.MessageDaoImpl;

@RestController
public class TestController extends BaseController{
	
	static Logger log = Logger.getLogger(MessageDaoImpl.class.getName());
	
	// Basic no login test
	
	//@CrossOrigin
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome() {
		// Welcome page, non-rest
		return "Welcome to PVAPI, no login";
	}

	// Basic login test
	
	@CrossOrigin
	@RequestMapping(value = "/authenticatedwelcome", method = RequestMethod.GET)
	public String safewelcomeAdmin() {
		// Welcome page, non-rest
		return "Welcome to PVAPI, you are logged in :)!";
	}
	
	@CrossOrigin
	@RequestMapping(value = "/ping")
	public String ping() {
		return "All good. You DO NOT need to be authenticated to call /ping";
	}

	@CrossOrigin
	@RequestMapping(value = "/secured/ping")
	public String securedPing() {
		return "All good. You DO need to be authenticated to call /secured/ping";
	}	
}


