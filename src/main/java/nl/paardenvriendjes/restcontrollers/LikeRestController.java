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
import org.springframework.web.util.UriComponentsBuilder;

import nl.paardenvriendjes.pvapi.daoimpl.LikeDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Like;

public class LikeRestController {

	@Autowired
	private LikeDaoImpl likeservice;

	static Logger log = Logger.getLogger(LikeDaoImpl.class.getName());

	@CrossOrigin
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome() {//Welcome page, non-rest
        return "Welcome to PVAPI";
    }	
	
	
	// -------------------Options Call --------------------------------------------------------------
	
	@CrossOrigin
	@RequestMapping(value = "/likes", method = RequestMethod.OPTIONS)
	public ResponseEntity<List<Like>> optionsCall() {
		ResponseEntity<List<Like>> ent = new ResponseEntity<List<Like>> (HttpStatus.NO_CONTENT);
		return ent;
	}

	// -------------------Retrieve All Likes--------------------------------------------------------
	
	@CrossOrigin
	@RequestMapping(value = "/likes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Like>> listAllLikes() {
		List<Like> likes = likeservice.listAll();
		if (likes.isEmpty()) {
			return new ResponseEntity<List<Like>>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List<Like>>(likes, HttpStatus.OK);
	}

	// -------------------Retrieve Single Like--------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/likes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Like> getLike(@PathVariable("id") long id) {
		log.debug("Fetching Like with id " + id);
		Like like = likeservice.listOne(id);
		if (like == null) {
			log.debug("Like with id " + id + " not found");
			return new ResponseEntity<Like>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Like>(like, HttpStatus.OK);
	}

	// -------------------Create a like--------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/likes/", method = RequestMethod.POST)
	public ResponseEntity<Void> createLike(@RequestBody Like like, UriComponentsBuilder ucBuilder) {
		log.debug("Creating like" + like.getLiketype());

		likeservice.save(like);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/like/{id}").buildAndExpand(like.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Like--------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/likes/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Like> updateLike(@PathVariable("id") long id, @RequestBody Like like) {
		log.debug("Updating Like " + id);

		Like currentLike = likeservice.listOne(id);

		if (currentLike == null) {
			System.out.println("Like with id " + id + " not found");
			return new ResponseEntity<Like>(HttpStatus.NOT_FOUND);
		}

		likeservice.edit(like);
		return new ResponseEntity<Like>(like, HttpStatus.OK);
	}

	// ------------------- Delete a Like --------------------------------------------------------
	
	@CrossOrigin
	@RequestMapping(value = "/likes/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Like> deleteLike(@PathVariable("id") long id) {
		log.debug("Fetching & Deleting Like with id " + id);

		Like like = likeservice.listOne(id);
		if (like == null) {
			System.out.println("Unable to delete Like with id " + id + " not found");
			return new ResponseEntity<Like>(HttpStatus.NOT_FOUND);
		}
		likeservice.remove(id);
		return new ResponseEntity<Like>(HttpStatus.NO_CONTENT);
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