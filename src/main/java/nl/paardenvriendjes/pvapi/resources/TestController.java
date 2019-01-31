package nl.paardenvriendjes.pvapi.resources;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.paardenvriendjes.pvapi.dao.MessageDaoImpl;

@RestController
@Slf4j
public class TestController extends BaseController{
	
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
	public String authenticatedWelcome() {
		// Welcome page, non-rest
		return "Welcome to PVAPI, you are logged in :)!";
	}
	
	@CrossOrigin
	@RequestMapping(value = "/authenticateduserrole", method = RequestMethod.GET)
	public String authenticatedAndAuthorizedWelcome() {
		// Welcome page, non-rest
		return "Welcome to PVAPI, you are logged in and have the correct Role:)!";
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


