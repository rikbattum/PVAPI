package nl.paardenvriendjes.application.security;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.auth0.authentication.result.Authentication;


@Component
	public class AuthenticationFacade implements IAuthenticationFacade {
	 
	    @Override
	    public Authentication getAuthentication() {
	        return (Authentication) SecurityContextHolder.getContext().getAuthentication();
	    }
}

// use as:Authentication authentication = authenticationFacade.getAuthentication();
// String id = authentication.getProfile().getId(); name = new Authentication authentication = authenticationFacade.getAuthentication();
// String id = authentication.getProfile().getId();();
