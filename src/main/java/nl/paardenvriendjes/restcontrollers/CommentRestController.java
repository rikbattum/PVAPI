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

import nl.paardenvriendjes.pvapi.daoimpl.CommentDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Comment;

@RestController
public class CommentRestController {

	@Autowired
	private CommentDaoImpl commentservice ;

	static Logger log = Logger.getLogger(CommentDaoImpl.class.getName());
	
	
	// -------------------Options Call --------------------------------------------------------------
	
		@CrossOrigin
		@RequestMapping(value = "/comments", method = RequestMethod.OPTIONS)
		public ResponseEntity<List<Comment>> optionsCall() {
			ResponseEntity<List<Comment>> ent = new ResponseEntity<List<Comment>> (HttpStatus.NO_CONTENT);
			return ent;
		}

	// -------------------Retrieve All Comments--------------------------------------------------------
		
		@CrossOrigin
		@RequestMapping(value = "/comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Comment>> listAllComments() {
			List<Comment> comments = commentservice.listAll();
			if (comments.isEmpty()) {
				return new ResponseEntity<List<Comment>>(HttpStatus.NO_CONTENT);
				}
			return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
		}

	// -------------------Retrieve Single Comment--------------------------------------------------------

		@CrossOrigin
		@RequestMapping(value = "/comments/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Comment> getComment(@PathVariable("id") long id) {
			log.debug("Fetching Comment with id " + id);
			Comment comment = commentservice.listOne(id);
			if (comment == null) {
				log.debug("Comment with id " + id + " not found");
				return new ResponseEntity<Comment>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Comment>(comment, HttpStatus.OK);
		}

		// -------------------Create a Comment--------------------------------------------------------

		@CrossOrigin
		@RequestMapping(value = "/comments/", method = RequestMethod.POST)
		public ResponseEntity<Void> createComment(@RequestBody Comment comment, UriComponentsBuilder ucBuilder) {
			log.debug("Creating comment" + comment.getComment());

			commentservice.save(comment);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/comments/{id}").buildAndExpand(comment.getId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		}

		// ------------------- Update a Comment--------------------------------------------------------

		@CrossOrigin
		@RequestMapping(value = "/comments/{id}", method = RequestMethod.PUT)
		public ResponseEntity<Comment> updateComment(@PathVariable("id") long id, @RequestBody Comment comment) {
			log.debug("Updating Comment " + id);

			Comment currentComment = commentservice.listOne(id);

			if (currentComment == null) {
				System.out.println("Comment with id " + id + " not found");
				return new ResponseEntity<Comment>(HttpStatus.NOT_FOUND);
			}

			commentservice.edit(currentComment);
			return new ResponseEntity<Comment>(currentComment, HttpStatus.OK);
		}

		// ------------------- Delete a Comment --------------------------------------------------------
		
		@CrossOrigin
		@RequestMapping(value = "/comments/{id}", method = RequestMethod.DELETE)
		public ResponseEntity<Comment> deleteComment(@PathVariable("id") long id) {
			log.debug("Fetching & Deleting Comment with id " + id);

			Comment comment = commentservice.listOne(id);
			if (comment == null) {
				System.out.println("Unable to delete comment with id " + id + " not found");
				return new ResponseEntity<Comment>(HttpStatus.NOT_FOUND);
			}
			commentservice.remove(comment);
			return new ResponseEntity<Comment>(HttpStatus.NO_CONTENT);
		}
		
		// ------------------- Count all Comments --------------------------------------------------------
		
			@CrossOrigin
			@RequestMapping(value = "/comments/count", method = RequestMethod.GET)
			public ResponseEntity<Integer> getCommentCount() {
				log.debug("Fetching comment count");
				int commentTotal = commentservice.count();
				return new ResponseEntity<Integer>(commentTotal, HttpStatus.OK);
			}
	}