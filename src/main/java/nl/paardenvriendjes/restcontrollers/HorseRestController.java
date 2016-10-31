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

import nl.paardenvriendjes.custom.editors.GeslachtEditor;
import nl.paardenvriendjes.custom.editors.PaardTypeEditor;
import nl.paardenvriendjes.pvapi.daoimpl.HorseDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Horse;
import nl.paardenvriendjes.pvapi.genericservicelayer.messagecreate.Genericmessageservice;

@RestController
public class HorseRestController {
	
	@Autowired
	private HorseDaoImpl horseservice;
	@Autowired
	private Genericmessageservice genericmessageservice;
	static Logger log = Logger.getLogger(HorseDaoImpl.class.getName());
	
	@InitBinder//("EnumEnitBinder")
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, "paardType", new PaardTypeEditor());
		binder.registerCustomEditor(String.class, "geslacht", new GeslachtEditor());
	}
	
	
	// -------------------Options Call --------------------------------------------------------------
	
	@CrossOrigin
	@RequestMapping(value = "/horses", method = RequestMethod.OPTIONS)
	public ResponseEntity<List<Horse>> optionsCall() {
		ResponseEntity<List<Horse>> ent = new ResponseEntity<List<Horse>> (HttpStatus.NO_CONTENT);
		return ent;
	}

	// -------------------Retrieve All Horses--------------------------------------------------------
	
	@CrossOrigin
	@RequestMapping(value = "/horses", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Horse>> listAllHorses() {
		List<Horse> horses = horseservice.listAll();
		if (horses.isEmpty()) {
			return new ResponseEntity<List<Horse>>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List<Horse>>(horses, HttpStatus.OK);
	}

	// -------------------Retrieve Single Horse--------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/horses/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Horse> getHorse(@PathVariable("id") long id) {
		log.debug("Fetching Horse with id " + id);
		Horse horse = horseservice.listOne(id);
		if (horse == null) {
			log.debug("Horse with id " + id + " not found");
			return new ResponseEntity<Horse>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Horse>(horse, HttpStatus.OK);
	}

	// -------------------Create a horse--------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/horses/", method = RequestMethod.POST)
	public ResponseEntity<Void> createHorse(@RequestBody Horse horse, UriComponentsBuilder ucBuilder) {
		log.debug("Creating horse" + horse.getName());
		
		
		horseservice.save(horse);
		// TODO: 
		//genericmessageservice.newHappyHorseMessage(member, horse);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/horse/{id}").buildAndExpand(horse.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Horse--------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/horses/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Horse> updateHorse(@PathVariable("id") long id, @RequestBody Horse horse) {
		log.debug("Updating Horse " + id);

		Horse currentHorse = horseservice.listOne(id);

		if (currentHorse == null) {
			System.out.println("Horse with id " + id + " not found");
			return new ResponseEntity<Horse>(HttpStatus.NOT_FOUND);
		}

		horseservice.edit(horse);
		return new ResponseEntity<Horse>(horse, HttpStatus.OK);
	}

	// ------------------- Delete a Horse --------------------------------------------------------
	
	@CrossOrigin
	@RequestMapping(value = "/horses/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Horse> deleteHorse(@PathVariable("id") long id) {
		log.debug("Fetching & Deleting Horse with id " + id);

		Horse horse = horseservice.listOne(id);
		if (horse == null) {
			System.out.println("Unable to delete Horse with id " + id + " not found");
			return new ResponseEntity<Horse>(HttpStatus.NOT_FOUND);
		}
		horseservice.remove(horse);
		return new ResponseEntity<Horse>(HttpStatus.NO_CONTENT);
	}
	
	// ------------------- Count all Horses --------------------------------------------------------
	
		@CrossOrigin
		@RequestMapping(value = "/horses/count", method = RequestMethod.GET)
		public ResponseEntity<Integer> getHorseCount() {
			log.debug("Fetching horse count");
			int horseTotal = horseservice.count();
			return new ResponseEntity<Integer>(horseTotal, HttpStatus.OK);
		}
}
