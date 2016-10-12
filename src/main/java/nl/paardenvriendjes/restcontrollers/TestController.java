package nl.paardenvriendjes.restcontrollers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import nl.paardenvriendjes.pvapi.daoimpl.MemberDaoImpl;
import nl.paardenvriendjes.pvapi.daoimpl.MessageDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.domain.Message;

@RestController
public class TestController {
	
	@Autowired
	private MessageDaoImpl messageservice;
	@Autowired
	private MemberDaoImpl memberservice;
	static Logger log = Logger.getLogger(MessageDaoImpl.class.getName());
	
	// Basic no login test
	
	@CrossOrigin
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome() {
		// Welcome page, non-rest
		return "Welcome to PVAPI, no login";
	}

	// Basic login test
	
	@CrossOrigin
	@RequestMapping(value = "/adminsafewelcome", method = RequestMethod.GET)
	public String safewelcomeAdmin() {
		// Welcome page, non-rest
		return "Welcome to PVAPI, you are logged in :)!";
	}
	
	@CrossOrigin
	@RequestMapping(value = "/usersafewelcome", method = RequestMethod.GET)
	public String safewelcomeForSpecificUser() {
		// Welcome page, non-rest
		return "Welcome to PVAPI, you are logged in :)!";
	}
	
	
//	@CrossOrigin
//	@RequestMapping(value = "/safeuseraction", method = RequestMethod.POST)
//	
//		public ResponseEntity<Void> safeUserMessage(@RequestBody Message message, UriComponentsBuilder ucBuilder) {
//			log.debug("Creating user safe message"); 
//			HttpHeaders headers = new HttpHeaders();
//			headers.setLocation(ucBuilder.path("/messages/{id}").buildAndExpand(message.getId()).toUri());
//			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
//	}
}
