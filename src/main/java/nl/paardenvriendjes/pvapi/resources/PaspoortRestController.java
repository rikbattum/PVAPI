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

import nl.paardenvriendjes.pvapi.dao.PaspoortDaoImpl;
import nl.paardenvriendjes.pvapi.data.Paspoort;

@RestController
@Slf4j
public class PaspoortRestController {


	@Autowired
	private PaspoortDaoImpl paspoortservice;

	
	// -------------------Options Call --------------------------------------------------------------
	
	@CrossOrigin
	@RequestMapping(value = "/paspoorts", method = RequestMethod.OPTIONS)
	public ResponseEntity<List<Paspoort>> optionsCall() {
		ResponseEntity<List<Paspoort>> ent = new ResponseEntity<List<Paspoort>> (HttpStatus.NO_CONTENT);
		return ent;
	}

	// -------------------Retrieve All Paspoorts--------------------------------------------------------
	
	@CrossOrigin
	@RequestMapping(value = "/paspoorts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Paspoort>> listAllPaspoorts() {
		List<Paspoort> paspoorts = paspoortservice.listAll();
		if (paspoorts.isEmpty()) {
			return new ResponseEntity<List<Paspoort>>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List<Paspoort>>(paspoorts, HttpStatus.OK);
	}

	// -------------------Retrieve Single Paspoort--------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/paspoorts/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Paspoort> getPaspoort(@PathVariable("id") long id) {
		log.debug("Fetching Paspoort with id " + id);
		Paspoort paspoort = paspoortservice.listOne(id);
		if (paspoort == null) {
			log.debug("Paspoort with id " + id + " not found");
			return new ResponseEntity<Paspoort>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Paspoort>(paspoort, HttpStatus.OK);
	}

	// -------------------Create a paspoort--------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/paspoorts/", method = RequestMethod.POST)
	public ResponseEntity<Void> createPaspoort(@RequestBody Paspoort paspoort, UriComponentsBuilder ucBuilder) {
		log.debug("Creating paspoort" + paspoort.getPaspoortName());

		paspoortservice.save(paspoort);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/paspoort/{id}").buildAndExpand(paspoort.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Paspoort--------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/paspoorts/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Paspoort> updatePaspoort(@PathVariable("id") long id, @RequestBody Paspoort paspoort) {
		log.debug("Updating Paspoort " + id);

		Paspoort currentPaspoort = paspoortservice.listOne(id);

		if (currentPaspoort == null) {
			System.out.println("Paspoort with id " + id + " not found");
			return new ResponseEntity<Paspoort>(HttpStatus.NOT_FOUND);
		}

		paspoortservice.edit(paspoort);
		return new ResponseEntity<Paspoort>(paspoort, HttpStatus.OK);
	}

	// ------------------- Delete a Paspoort --------------------------------------------------------
	
	@CrossOrigin
	@RequestMapping(value = "/paspoorts/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Paspoort> deletePaspoort(@PathVariable("id") long id) {
		log.debug("Fetching & Deleting Paspoort with id " + id);

		Paspoort paspoort = paspoortservice.listOne(id);
		if (paspoort == null) {
			System.out.println("Unable to delete Paspoort with id " + id + " not found");
			return new ResponseEntity<Paspoort>(HttpStatus.NOT_FOUND);
		}
		paspoortservice.remove(paspoort);
		return new ResponseEntity<Paspoort>(HttpStatus.NO_CONTENT);
	}
	
	// ------------------- Count all Paspoorts --------------------------------------------------------
	
		@CrossOrigin
		@RequestMapping(value = "/paspoorts/count", method = RequestMethod.GET)
		public ResponseEntity<Integer> getPaspoortCount() {
			log.debug("Fetching paspoort count");
			int paspoortTotal = paspoortservice.count();
			return new ResponseEntity<Integer>(paspoortTotal, HttpStatus.OK);
		}
}