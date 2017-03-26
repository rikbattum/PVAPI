package nl.paardenvriendjes.restcontrollers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import nl.paardenvriendjes.pvapi.daoimpl.MessageCommentDaoImpl;
import nl.paardenvriendjes.pvapi.domain.MessageComment;

@RestController
public class MessageCommentRestController {

	@Autowired
	private MessageCommentDaoImpl commentservice ;

	static Logger log = Logger.getLogger(MessageCommentDaoImpl.class.getName());
	
	
	// -------------------Options Call --------------------------------------------------------------
	
		@CrossOrigin
		@RequestMapping(value = "/messagecomments", method = RequestMethod.OPTIONS)
		public ResponseEntity<List<MessageComment>> optionsCall() {
			ResponseEntity<List<MessageComment>> ent = new ResponseEntity<List<MessageComment>> (HttpStatus.NO_CONTENT);
			return ent;
		}

	// -------------------Retrieve All Comments--------------------------------------------------------
		
		@CrossOrigin
		@RequestMapping(value = "/messagecomments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<MessageComment>> listAllComments() {
			List<MessageComment> messageComments = commentservice.listAll();
			if (messageComments.isEmpty()) {
				return new ResponseEntity<List<MessageComment>>(HttpStatus.NO_CONTENT);
				}
			return new ResponseEntity<List<MessageComment>>(messageComments, HttpStatus.OK);
		}

	// -------------------Retrieve Single Comment--------------------------------------------------------

		@CrossOrigin
		@RequestMapping(value = "/messagecomments/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<MessageComment> getComment(@PathVariable("id") long id) {
			log.debug("Fetching Comment with id " + id);
			MessageComment messageComment = commentservice.listOne(id);
			if (messageComment == null) {
				log.debug("Comment with id " + id + " not found");
				return new ResponseEntity<MessageComment>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<MessageComment>(messageComment, HttpStatus.OK);
		}

		// -------------------Create a Comment--------------------------------------------------------

		@CrossOrigin
		@RequestMapping(value = "/messagecomments/", method = RequestMethod.POST)
		public ResponseEntity<Void> createComment(@RequestBody MessageComment messageComment, UriComponentsBuilder ucBuilder) {
			log.debug("Creating comment" + messageComment.getComment());

			commentservice.save(messageComment);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/messagecomments/{id}").buildAndExpand(messageComment.getId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		}

		// ------------------- Update a Comment--------------------------------------------------------

		@CrossOrigin
		@RequestMapping(value = "/messagecomments/{id}", method = RequestMethod.PUT)
		public ResponseEntity<MessageComment> updateComment(@PathVariable("id") long id, @RequestBody MessageComment messageComment) {
			log.debug("Updating Comment " + id);

			MessageComment currentComment = commentservice.listOne(id);

			if (currentComment == null) {
				System.out.println("Comment with id " + id + " not found");
				return new ResponseEntity<MessageComment>(HttpStatus.NOT_FOUND);
			}

			commentservice.edit(currentComment);
			return new ResponseEntity<MessageComment>(currentComment, HttpStatus.OK);
		}

		// ------------------- Delete a Comment --------------------------------------------------------
		
		@CrossOrigin
		@RequestMapping(value = "/messagecomments/{id}", method = RequestMethod.DELETE)
		public ResponseEntity<MessageComment> deleteComment(@PathVariable("id") long id) {
			log.debug("Fetching & Deleting Comment with id " + id);

			MessageComment messageComment = commentservice.listOne(id);
			if (messageComment == null) {
				System.out.println("Unable to delete comment with id " + id + " not found");
				return new ResponseEntity<MessageComment>(HttpStatus.NOT_FOUND);
			}
			commentservice.remove(messageComment);
			return new ResponseEntity<MessageComment>(HttpStatus.NO_CONTENT);
		}
		
		// ------------------- Count all Comments --------------------------------------------------------
		
			@CrossOrigin
			@RequestMapping(value = "/messagecomments/count", method = RequestMethod.GET)
			public ResponseEntity<Integer> getCommentCount() {
				log.debug("Fetching comment count");
				int commentTotal = commentservice.count();
				return new ResponseEntity<Integer>(commentTotal, HttpStatus.OK);
			}
	}