package nl.paardenvriendjes.pvapi.resources;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
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

import nl.paardenvriendjes.pvapi.dao.EventCommentDaoImpl;
import nl.paardenvriendjes.pvapi.data.EventComment;

@RestController
@Slf4j
public class EventCommentRestController {

	@Autowired
	private EventCommentDaoImpl commentservice ;
	
	
	// -------------------Options Call --------------------------------------------------------------
	
		@CrossOrigin
		@RequestMapping(value = "/eventcomments", method = RequestMethod.OPTIONS)
		public ResponseEntity<List<EventComment>> optionsCall() {
			ResponseEntity<List<EventComment>> ent = new ResponseEntity<List<EventComment>> (HttpStatus.NO_CONTENT);
			return ent;
		}

	// -------------------Retrieve All Comments--------------------------------------------------------
		
		@CrossOrigin
		@RequestMapping(value = "/eventcomments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<EventComment>> listAllComments() {
			List<EventComment> EventComments = commentservice.listAll();
			if (EventComments.isEmpty()) {
				return new ResponseEntity<List<EventComment>>(HttpStatus.NO_CONTENT);
				}
			return new ResponseEntity<List<EventComment>>(EventComments, HttpStatus.OK);
		}

	// -------------------Retrieve Single Comment--------------------------------------------------------

		@CrossOrigin
		@RequestMapping(value = "/eventcomments/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<EventComment> getComment(@PathVariable("id") long id) {
			log.debug("Fetching Comment with id " + id);
			EventComment EventComment = commentservice.listOne(id);
			if (EventComment == null) {
				log.debug("Comment with id " + id + " not found");
				return new ResponseEntity<EventComment>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<EventComment>(EventComment, HttpStatus.OK);
		}

		// -------------------Create a Comment--------------------------------------------------------

		@CrossOrigin
		@RequestMapping(value = "/eventcomments/", method = RequestMethod.POST)
		public ResponseEntity<Void> createComment(@RequestBody EventComment EventComment, UriComponentsBuilder ucBuilder) {
			log.debug("Creating comment" + EventComment.getComment());

			commentservice.save(EventComment);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/comments/{id}").buildAndExpand(EventComment.getId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		}

		// ------------------- Update a Comment--------------------------------------------------------

		@CrossOrigin
		@RequestMapping(value = "/eventcomments/{id}", method = RequestMethod.PUT)
		public ResponseEntity<EventComment> updateComment(@PathVariable("id") long id, @RequestBody EventComment EventComment) {
			log.debug("Updating Comment " + id);

			EventComment currentComment = commentservice.listOne(id);

			if (currentComment == null) {
				System.out.println("Comment with id " + id + " not found");
				return new ResponseEntity<EventComment>(HttpStatus.NOT_FOUND);
			}

			commentservice.edit(currentComment);
			return new ResponseEntity<EventComment>(currentComment, HttpStatus.OK);
		}

		// ------------------- Delete a Comment --------------------------------------------------------
		
		@CrossOrigin
		@RequestMapping(value = "/eventcomments/{id}", method = RequestMethod.DELETE)
		public ResponseEntity<EventComment> deleteComment(@PathVariable("id") long id) {
			log.debug("Fetching & Deleting Comment with id " + id);

			EventComment EventComment = commentservice.listOne(id);
			if (EventComment == null) {
				System.out.println("Unable to delete comment with id " + id + " not found");
				return new ResponseEntity<EventComment>(HttpStatus.NOT_FOUND);
			}
			commentservice.remove(EventComment);
			return new ResponseEntity<EventComment>(HttpStatus.NO_CONTENT);
		}
		
		// ------------------- Count all Comments --------------------------------------------------------
		
			@CrossOrigin
			@RequestMapping(value = "eventcomments/count", method = RequestMethod.GET)
			public ResponseEntity<Integer> getCommentCount() {
				log.debug("Fetching comment count");
				int commentTotal = commentservice.count();
				return new ResponseEntity<Integer>(commentTotal, HttpStatus.OK);
			}
	}