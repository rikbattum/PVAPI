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
import nl.paardenvriendjes.pvapi.domain.Likes;

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
	public ResponseEntity<List<Likes>> optionsCall() {
		ResponseEntity<List<Likes>> ent = new ResponseEntity<List<Likes>> (HttpStatus.NO_CONTENT);
		return ent;
	}

	// -------------------Retrieve All Likes--------------------------------------------------------
	
	@CrossOrigin
	@RequestMapping(value = "/likes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Likes>> listAllLikes() {
		List<Likes> likes = likeservice.listAll();
		if (likes.isEmpty()) {
			return new ResponseEntity<List<Likes>>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List<Likes>>(likes, HttpStatus.OK);
	}

	// -------------------Retrieve Single Like--------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/likes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Likes> getLike(@PathVariable("id") long id) {
		log.debug("Fetching Like with id " + id);
		Likes likes = likeservice.listOne(id);
		if (likes == null) {
			log.debug("Like with id " + id + " not found");
			return new ResponseEntity<Likes>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Likes>(likes, HttpStatus.OK);
	}

	// -------------------Create a like--------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/likes/", method = RequestMethod.POST)
	public ResponseEntity<Void> createLike(@RequestBody Likes likes, UriComponentsBuilder ucBuilder) {
		log.debug("Creating like" + likes.getLiketype());

		likeservice.save(likes);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/like/{id}").buildAndExpand(likes.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Like--------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/likes/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Likes> updateLike(@PathVariable("id") long id, @RequestBody Likes likes) {
		log.debug("Updating Like " + id);

		Likes currentLike = likeservice.listOne(id);

		if (currentLike == null) {
			System.out.println("Like with id " + id + " not found");
			return new ResponseEntity<Likes>(HttpStatus.NOT_FOUND);
		}

		likeservice.edit(likes);
		return new ResponseEntity<Likes>(likes, HttpStatus.OK);
	}

	// ------------------- Delete a Like --------------------------------------------------------
	
	@CrossOrigin
	@RequestMapping(value = "/likes/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Likes> deleteLike(@PathVariable("id") long id) {
		log.debug("Fetching & Deleting Like with id " + id);

		Likes likes = likeservice.listOne(id);
		if (likes == null) {
			System.out.println("Unable to delete Like with id " + id + " not found");
			return new ResponseEntity<Likes>(HttpStatus.NOT_FOUND);
		}
		likeservice.remove(likes);
		return new ResponseEntity<Likes>(HttpStatus.NO_CONTENT);
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