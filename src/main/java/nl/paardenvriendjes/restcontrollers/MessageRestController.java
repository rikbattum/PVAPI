package nl.paardenvriendjes.restcontrollers;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import nl.paardenvriendjes.custom.editors.LineTypeEditor;
import nl.paardenvriendjes.custom.editors.MessageTypeEditor;
import nl.paardenvriendjes.pvapi.daoimpl.MemberDaoImpl;
import nl.paardenvriendjes.pvapi.daoimpl.MessageDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.domain.Message;

@RestController
public class MessageRestController {


	@Autowired
	private MessageDaoImpl messageservice;
	@Autowired
	private MemberDaoImpl memberservice;
	
	static Logger log = Logger.getLogger(MessageDaoImpl.class.getName());
	
	@InitBinder//("EnumEnitBinder")
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, "linetype", new LineTypeEditor());
		binder.registerCustomEditor(String.class, "messagetype", new MessageTypeEditor());
	}
	

	// -------------------Retrieve Single Message--------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/messages/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getMessage(@PathVariable("id") long id) {
		log.debug("Fetching Message with id " + id);
		Message message = messageservice.listOne(id);
		if (message == null) {
			log.debug("Message with id " + id + " not found");
			return new ResponseEntity<Message>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}

	// -------------------Retrieve All Messages Sport--------------------------------------------------------
	
		@CrossOrigin
		@RequestMapping(value = "/messages/sport", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Message>> listAllMessagesSport(@PathVariable("start") int start, @PathVariable("end") int end) {
			List<Message> messagesSport = messageservice.listAllMessagesSport(start, end);
			if (messagesSport.isEmpty()) {
				return new ResponseEntity<List<Message>>(HttpStatus.NO_CONTENT);
				}
			return new ResponseEntity<List<Message>>(messagesSport, HttpStatus.OK);
		}
	
     // -------------------Retrieve All Messages Friends--------------------------------------------------------
	
		@CrossOrigin
		@RequestMapping(value = "/messages/friends", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Message>> listAllMessagesFriends(@PathVariable("start") int start, @PathVariable("end") int end, @PathVariable("memberId") Long memberId) {
			Member member = memberservice.listOne(memberId); 
			List<Message> messagesSport = messageservice.listAllMessagesFriends(start, end, member);
			if (messagesSport.isEmpty()) {
				return new ResponseEntity<List<Message>>(HttpStatus.NO_CONTENT);
				}
			return new ResponseEntity<List<Message>>(messagesSport, HttpStatus.OK);
		}
			
    // -------------------Retrieve All Messages Kids--------------------------------------------------------
	
		@CrossOrigin
		@RequestMapping(value = "/messages/kids", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Message>> listAllMessagesKids(@PathVariable("start") int start, @PathVariable("end") int end) {
			List<Message> messagesSport = messageservice.listAllMessagesKids(start, end);
			if (messagesSport.isEmpty()) {
				return new ResponseEntity<List<Message>>(HttpStatus.NO_CONTENT);
				}
			return new ResponseEntity<List<Message>>(messagesSport, HttpStatus.OK);
		}	
		
	// -------------------Retrieve All Messages All --------------------------------------------------------	
	
		@CrossOrigin
		@RequestMapping(value = "/messages/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Message>> listAllMessagesAll(@PathVariable("start") int start, @PathVariable("end") int end) {
			List<Message> messagesSport = messageservice.listAllMessages(start, end);
			if (messagesSport.isEmpty()) {
				return new ResponseEntity<List<Message>>(HttpStatus.NO_CONTENT);
				}
			return new ResponseEntity<List<Message>>(messagesSport, HttpStatus.OK);
		}		
		
	// -------------------Create a message---------------------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/messages/", method = RequestMethod.POST)
	@PreAuthorize("#message.member.email == authentication.name or hasRole('Admin')")
	public ResponseEntity<Void> createMessage(@RequestBody Message message, UriComponentsBuilder ucBuilder) {
		log.debug("Creating message" + message.getMessage());
		messageservice.save(message);
		// TODO
		// USE GENERIC SERVICE FOR AUTO UPDATE
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/messages/{id}").buildAndExpand(message.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Message--------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/messages/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("#message.member.email == authentication.name or hasRole('Admin')")
	public ResponseEntity<Message> updateMessage(@PathVariable("id") long id, @RequestBody Message message) {
		log.debug("Updating Message" + id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		log.debug("Authis" + auth.getName());
		Message currentMessage = messageservice.listOne(id);
		if (currentMessage == null) {
			System.out.println("Message with id " + id + " not found");
			return new ResponseEntity<Message>(HttpStatus.NOT_FOUND);
		}
		messageservice.edit(message);
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}

	// ------------------- Delete a Message --------------------------------------------------------
	
	@CrossOrigin
	@RequestMapping(value = "/messages/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("#message.member.email == authentication.name or hasRole('Admin')")
	public ResponseEntity<Message> deleteMessage(@PathVariable("id") long id) {
		log.debug("Fetching & Deleting Mesage with id " + id);
		Message message = messageservice.listOne(id);
		if (message == null) {
			System.out.println("Unable to delete Message with id " + id + " not found");
			return new ResponseEntity<Message>(HttpStatus.NOT_FOUND);
		}
		messageservice.remove(message);
		return new ResponseEntity<Message>(HttpStatus.NO_CONTENT);
	}
	
	// ------------------- Count all Messages --------------------------------------------------------
	
			@CrossOrigin
			@RequestMapping(value = "/messages/count", method = RequestMethod.GET)
			public ResponseEntity<Integer> getMessageCount() {
				log.debug("Fetching message count");
				int messageTotal = messageservice.count();
				return new ResponseEntity<Integer>(messageTotal, HttpStatus.OK);
	
			}
	
}


