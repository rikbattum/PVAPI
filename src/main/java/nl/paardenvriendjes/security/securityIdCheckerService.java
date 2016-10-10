package nl.paardenvriendjes.security;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.auth0.authentication.result.Authentication;

import nl.paardenvriendjes.pvapi.daoimpl.MessageDaoImpl;

@Component("securityIdCheckerService")
public class securityIdCheckerService {

	static Logger log = Logger.getLogger(MessageDaoImpl.class.getName());

	@Autowired
	private IAuthenticationFacade authenticationFacade;

	public boolean hasPermission(String key) {
       
		
		Authentication authentication = authenticationFacade.getAuthentication();
		String id = authentication.getProfile().getId();
		
		if (id.contentEquals("ssss")) {  
		
		
		return true; 
		}
		else { 
			return false;
		}
    }

}
