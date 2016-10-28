package nl.paardenvriendjes.restcontrollers.testcontrollers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

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
	
