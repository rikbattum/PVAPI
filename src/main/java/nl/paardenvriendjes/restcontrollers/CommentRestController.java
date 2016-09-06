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
import nl.paardenvriendjes.pvapi.domain.Member;;

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

		// -------------------Retrieve All Members--------------------------------------------------------
		
//		@CrossOrigin
//		@RequestMapping(value = "/members", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//		public ResponseEntity<List<Member>> listAllUsers() {
//			List<Member> members = memberservice.listAll();
//			if (members.isEmpty()) {
//				return new ResponseEntity<List<Member>>(HttpStatus.NO_CONTENT);
//				}
//			return new ResponseEntity<List<Member>>(members, HttpStatus.OK);
//		}
//
//		// -------------------Retrieve Single Member--------------------------------------------------------
//
//		@CrossOrigin
//		@RequestMapping(value = "/members/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//		public ResponseEntity<Member> getUser(@PathVariable("id") long id) {
//			log.debug("Fetching User with id " + id);
//			Member member = memberservice.listOne(id);
//			if (member == null) {
//				log.debug("User with id " + id + " not found");
//				return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
//			}
//			return new ResponseEntity<Member>(member, HttpStatus.OK);
//		}
//
//		// -------------------Create a member--------------------------------------------------------
//
//		@CrossOrigin
//		@RequestMapping(value = "/members/", method = RequestMethod.POST)
//		public ResponseEntity<Void> createMember(@RequestBody Member member, UriComponentsBuilder ucBuilder) {
//			log.debug("Creating member" + member.getUsername());
//
//			memberservice.save(member);
//
//			HttpHeaders headers = new HttpHeaders();
//			headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(member.getId()).toUri());
//			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
//		}
//
//		// ------------------- Update a Member--------------------------------------------------------
//
//		@CrossOrigin
//		@RequestMapping(value = "/members/{id}", method = RequestMethod.PUT)
//		public ResponseEntity<Member> updateMember(@PathVariable("id") long id, @RequestBody Member member) {
//			log.debug("Updating User " + id);
//
//			Member currentMember = memberservice.listOne(id);
//
//			if (currentMember == null) {
//				System.out.println("User with id " + id + " not found");
//				return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
//			}
//
//			memberservice.edit(member);
//			return new ResponseEntity<Member>(member, HttpStatus.OK);
//		}
//
//		// ------------------- Delete a Member --------------------------------------------------------
//		
//		@CrossOrigin
//		@RequestMapping(value = "/members/{id}", method = RequestMethod.DELETE)
//		public ResponseEntity<Member> deleteUser(@PathVariable("id") long id) {
//			log.debug("Fetching & Deleting User with id " + id);
//
//			Member member = memberservice.listOne(id);
//			if (member == null) {
//				System.out.println("Unable to delete Member with id " + id + " not found");
//				return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
//			}
//			memberservice.remove(id);
//			return new ResponseEntity<Member>(HttpStatus.NO_CONTENT);
//		}
//	}

	
	
	
	
	
	
}
