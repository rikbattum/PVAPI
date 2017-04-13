package nl.paardenvriendjes.restcontrollers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import nl.paardenvriendjes.custom.editors.GeslachtEditor;
import nl.paardenvriendjes.custom.editors.LocationTypeEditor;
import nl.paardenvriendjes.custom.editors.SportLevelEditor;
import nl.paardenvriendjes.custom.editors.VervoerEditor;
import nl.paardenvriendjes.exceptionsanderrors.Auth0CreationException;
import nl.paardenvriendjes.pvapi.daoimpl.MemberDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.genericservicelayer.messagecreate.Genericmessageservice;

@RestController
public class MemberRestController extends BaseController {

	@Autowired
	private MemberDaoImpl memberservice;
	@Autowired
	private Genericmessageservice genericmessageservice;

	static Logger log = Logger.getLogger(MemberDaoImpl.class.getName());

	@InitBinder // ("EnumEnitBinder")
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, "place", new LocationTypeEditor());
		binder.registerCustomEditor(String.class, "sporttype", new SportLevelEditor());
		binder.registerCustomEditor(String.class, "vervoer", new VervoerEditor());
		binder.registerCustomEditor(String.class, "geslacht", new GeslachtEditor());
	}

	// -------------------Options Call
	// --------------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/members", method = RequestMethod.OPTIONS)
	public ResponseEntity<List<Member>> optionsCall() {
		ResponseEntity<List<Member>> ent = new ResponseEntity<List<Member>>(HttpStatus.NO_CONTENT);
		return ent;
	}

	// -------------------Retrieve All
	// Members--------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/members", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Member>> listAllUsers() {
		List<Member> members = memberservice.listAll();
		if (members.isEmpty()) {
			return new ResponseEntity<List<Member>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Member>>(members, HttpStatus.OK);
	}

	// -------------------Retrieve Single
	// Member--------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/members/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Member> getUser(@PathVariable("id") long id) {
		log.debug("Fetching User with id " + id);
		Member member = memberservice.listOne(id);
		if (member == null) {
			log.debug("User with id " + id + " not found");
			return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Member>(member, HttpStatus.OK);
	}

	// -------------------Create a
	// member--------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "members/signup", method = RequestMethod.POST)

	public ResponseEntity<Void> signupMember(@RequestBody Member member, UriComponentsBuilder ucBuilder) {

		log.debug("Creating member " + member.getEmail());
		// Save member
		memberservice.save(member);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(member.getId()).toUri());

		// TODO First arrange rights setup for this
		//genericmessageservice.newMemberHappyMessage(member);

		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		
	}

	// ------------------- Update a
	// Member--------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/members/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Member> updateMember(@PathVariable("id") String id, @RequestBody Member member) {
		log.debug("Updating User " + member.getId());
		log.debug("Fetching & Deleting User with id " + id);
//		log.debug(SecurityContextHolder.getContext().getAuthentication().getName());
//		log.debug(SecurityContextHolder.getContext().getAuthentication().getCredentials());
//		log.debug(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//		log.debug(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());

		Member currentMember = memberservice.listOne(member.getId());

		if (currentMember == null) {
			System.out.println("User with id " + id + " not found");
			return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
		}

		memberservice.edit(member);
		return new ResponseEntity<Member>(member, HttpStatus.OK);
	}

	// ------------------- Delete a Member
	// --------------------------------------------------------
	
	@RequestMapping(value = "/members/{id}", method = RequestMethod.DELETE)
	@CrossOrigin
	public ResponseEntity<Member> deleteUser(@PathVariable("id") Long id)
			throws URISyntaxException, IOException, Auth0CreationException {
		log.debug("Fetching & Deleting User with id " + id);

		// Remove member in PVAPI
		Member member = memberservice.listOne(id);
		if (member == null || member.getId() == null || member.getAuth0user_id() == null) {
			System.out.println("Unable to delete Member with id " + id + " not found");
			return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
		}
		memberservice.remove(member);
		
		return new ResponseEntity<Member>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Count all Members
	// --------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/members/count", method = RequestMethod.GET)
	public ResponseEntity<Integer> getUserCount() {
		log.debug("Fetching user count");
		int memberTotal = memberservice.count();
		return new ResponseEntity<Integer>(memberTotal, HttpStatus.OK);
	}

	// ------------------- Find Member by firstname
	// -------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/members/find/firstname", method = RequestMethod.GET)
	public ResponseEntity<List<Member>> findMemberByFirstName(@PathVariable("firstname") String firstname) {
		log.debug("Fetching member by firstname");
		List<Member> members = memberservice.findMemberByFirstName(firstname);
		if (members.isEmpty()) {
			return new ResponseEntity<List<Member>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Member>>(members, HttpStatus.OK);
	}

	// ------------------- Find Member by lastname
	// -------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/members/find/lastname", method = RequestMethod.GET)
	public ResponseEntity<List<Member>> findMemberByLasttName(@PathVariable("lastname") String lastname) {
		log.debug("Fetching member by lastname");
		List<Member> members = memberservice.findMemberByLastName(lastname);
		if (members.isEmpty()) {
			return new ResponseEntity<List<Member>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Member>>(members, HttpStatus.OK);
	}

	// ------------------- Find Member by fullname
	// -------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/members/find/fullname", method = RequestMethod.GET)
	public ResponseEntity<List<Member>> findMemberByFullName(@PathVariable("firstname") String firstname,
			@PathVariable("lastName") String lastname) {
		log.debug("Fetching member by FullName");
		List<Member> members = memberservice.findMemberByFirstAndLastName(firstname, lastname);
		if (members.isEmpty()) {
			return new ResponseEntity<List<Member>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Member>>(members, HttpStatus.OK);
	}

	// ------------------- Find Member by location
	// -------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/members/find/location", method = RequestMethod.GET)
	public ResponseEntity<List<Member>> findMemberByLocation(@PathVariable("location") String location) {
		log.debug("Fetching member by location");
		List<Member> members = memberservice.findMemberByLocation(location);
		if (members.isEmpty()) {
			return new ResponseEntity<List<Member>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Member>>(members, HttpStatus.OK);
	}

	// ------------------- Find Member by interesse
	// -------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/members/find/interesse", method = RequestMethod.GET)
	public ResponseEntity<List<Member>> findMemberByInteresse(@PathVariable("interesse") String interesse) {
		log.debug("Fetching member by interesse");
		List<Member> members = memberservice.findMemberByInteresse(interesse);
		if (members.isEmpty()) {
			return new ResponseEntity<List<Member>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Member>>(members, HttpStatus.OK);
	}

	// ------------------- Find Member by sporttype
	// -------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/members/find/sporttype", method = RequestMethod.GET)
	public ResponseEntity<List<Member>> findMemberBySportType(@PathVariable("sporttype") String sporttype) {
		log.debug("Fetching member by sporttype");
		List<Member> members = memberservice.findMemberBySportType(sporttype);
		if (members.isEmpty()) {
			return new ResponseEntity<List<Member>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Member>>(members, HttpStatus.OK);
	}

	// Friend functions

	@CrossOrigin
	@RequestMapping(value = "/members/friend/addfriend/", method = RequestMethod.PUT)
	@PreAuthorize("@memberservice.listOne(#id).getId() == authentication.name or hasRole('Admin')")
	public ResponseEntity<Member> addFriend(@PathVariable("id") long id,
			@PathVariable("toBeFollowedFriendId") long toBeFollowedFriendId) {
		log.debug("Adding friend with id " + toBeFollowedFriendId + " to memberId " + id);
		Member member = memberservice.listOne(id);
		Member friend = memberservice.listOne(toBeFollowedFriendId);
		if (member == null || friend == null) {
			log.debug("Unable to add member or friend when null id of " + id + " or friend id of: "
					+ toBeFollowedFriendId);
			return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
		}
		memberservice.addFriend(member, friend);
		return new ResponseEntity<Member>(member, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/members/friend/removefriend/", method = RequestMethod.PUT)
	@PreAuthorize("@memberservice.listOne(#id).getId() == authentication.name or hasRole('Admin')")
	public ResponseEntity<Member> removeFriend(@PathVariable("id") long id,
			@PathVariable("toBeRemovedFriendId") long toBeRemovedFriendId) {
		log.debug("Removing friend with id " + toBeRemovedFriendId + " from memberId " + id);
		Member member = memberservice.listOne(id);
		Member friend = memberservice.listOne(toBeRemovedFriendId);
		if (member == null || friend == null) {
			log.debug("Unable to remove member or friend when null id of " + id + " or friend id of: "
					+ toBeRemovedFriendId);
			return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
		}
		memberservice.removeFriend(member, friend);
		return new ResponseEntity<Member>(member, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/members/friend/addblock/", method = RequestMethod.PUT)
	@PreAuthorize("@memberservice.listOne(#id).getId() == authentication.name or hasRole('Admin')")
	public ResponseEntity<Member> addBlock(@PathVariable("id") long id,
			@PathVariable("toBeAddedBlockId") long toBeAddedBlockId) {
		log.debug("Adding block for  friend with id " + toBeAddedBlockId + " for memberId " + id);
		Member member = memberservice.listOne(id);
		Member block = memberservice.listOne(toBeAddedBlockId);
		if (member == null || block == null) {
			log.debug(
					"Unable to block member or friend when null id of " + id + " or block id of: " + toBeAddedBlockId);
			return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
		}
		memberservice.addBlock(member, block);
		return new ResponseEntity<Member>(member, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/members/friend/removeblock/", method = RequestMethod.PUT)
	@PreAuthorize("@memberservice.listOne(#id).getId() == authentication.name or hasRole('Admin')")
	public ResponseEntity<Member> removeBlock(@PathVariable("id") long id,
			@PathVariable("toBeRemovedBlockId") long toBeRemovedBlockId) {
		log.debug("Removing block for friend with id " + toBeRemovedBlockId + " for memberId " + id);
		Member member = memberservice.listOne(id);
		Member block = memberservice.listOne(toBeRemovedBlockId);
		if (member == null || block == null) {
			log.debug("Unable to unlock member or friend when null id of " + id + " or block id of: "
					+ toBeRemovedBlockId);
			return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
		}
		memberservice.removeBlock(member, block);
		return new ResponseEntity<Member>(member, HttpStatus.OK);
	}
}
