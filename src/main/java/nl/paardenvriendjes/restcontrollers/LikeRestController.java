package nl.paardenvriendjes.restcontrollers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import nl.paardenvriendjes.custom.editors.LikeTypeEditor;
import nl.paardenvriendjes.pvapi.daoimpl.LikeDaoImpl;
import nl.paardenvriendjes.pvapi.domain.MessageLike;

@RestController
public class LikeRestController {

	@Autowired
	private LikeDaoImpl likeservice;

	static Logger log = Logger.getLogger(LikeDaoImpl.class.getName());
	
	@InitBinder//("EnumEnitBinder")
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, "liketype", new LikeTypeEditor());
	}
	
	// -------------------Options Call --------------------------------------------------------------
	
	@CrossOrigin
	@RequestMapping(value = "/likes", method = RequestMethod.OPTIONS)
	public ResponseEntity<List<MessageLike>> optionsCall() {
		ResponseEntity<List<MessageLike>> ent = new ResponseEntity<List<MessageLike>> (HttpStatus.NO_CONTENT);
		return ent;
	}

	// -------------------Retrieve All Likes--------------------------------------------------------
	
	@CrossOrigin
	@RequestMapping(value = "/likes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MessageLike>> listAllLikes() {
		List<MessageLike> messageLike = likeservice.listAll();
		if (messageLike.isEmpty()) {
			return new ResponseEntity<List<MessageLike>>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List<MessageLike>>(messageLike, HttpStatus.OK);
	}

	// -------------------Retrieve Single Like--------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/likes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageLike> getLike(@PathVariable("id") long id) {
		log.debug("Fetching Like with id " + id);
		MessageLike messageLike = likeservice.listOne(id);
		if (messageLike == null) {
			log.debug("Like with id " + id + " not found");
			return new ResponseEntity<MessageLike>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<MessageLike>(messageLike, HttpStatus.OK);
	}

	// -------------------Create a like--------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/likes/", method = RequestMethod.POST)
	public ResponseEntity<Void> createLike(@RequestBody MessageLike messageLike, UriComponentsBuilder ucBuilder) {
		log.debug("Creating like" + messageLike.getLiketype());

		likeservice.save(messageLike);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/like/{id}").buildAndExpand(messageLike.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Like--------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/likes/{id}", method = RequestMethod.PUT)
	public ResponseEntity<MessageLike> updateLike(@PathVariable("id") long id, @RequestBody MessageLike messageLike) {
		log.debug("Updating Like " + id);

		MessageLike currentLike = likeservice.listOne(id);

		if (currentLike == null) {
			System.out.println("Like with id " + id + " not found");
			return new ResponseEntity<MessageLike>(HttpStatus.NOT_FOUND);
		}

		likeservice.edit(messageLike);
		return new ResponseEntity<MessageLike>(messageLike, HttpStatus.OK);
	}

	// ------------------- Delete a Like --------------------------------------------------------
	
	@CrossOrigin
	@RequestMapping(value = "/likes/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<MessageLike> deleteLike(@PathVariable("id") long id) {
		log.debug("Fetching & Deleting Like with id " + id);

		MessageLike messageLike = likeservice.listOne(id);
		if (messageLike == null) {
			System.out.println("Unable to delete Like with id " + id + " not found");
			return new ResponseEntity<MessageLike>(HttpStatus.NOT_FOUND);
		}
		likeservice.remove(messageLike);
		return new ResponseEntity<MessageLike>(HttpStatus.NO_CONTENT);
	}
	
	// ------------------- Count all Likes --------------------------------------------------------
	
		@CrossOrigin
		@RequestMapping(value = "/likes/count", method = RequestMethod.GET)
		public ResponseEntity<Integer> getLikeCount() {
			log.debug("Fetching like count");
			int likeTotal = likeservice.count();
			return new ResponseEntity<Integer>(likeTotal, HttpStatus.OK);
		}
}