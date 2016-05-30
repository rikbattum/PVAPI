package nl.paardenvriendjes.pvapi.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import nl.paardenvriendjes.enumerations.MessageType;

@Entity
public class Message {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String message;
	private MessageType type;
	private Date insertDate; 
	@OneToMany(mappedBy="id")
	private Member member;
	private String piclink; 
	private String picLinkSecond;
	private String picLinkThird;
	@OneToMany (mappedBy="message_id")
	private List <Comment> commentlist;
	@OneToMany (mappedBy="id")
	private List <Like> likelist;
	
	
	
	
	
}
