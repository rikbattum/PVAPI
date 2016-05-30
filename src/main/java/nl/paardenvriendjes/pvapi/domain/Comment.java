package nl.paardenvriendjes.pvapi.domain;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

public class Comment {

	
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Message messageid; 
	
	
}
