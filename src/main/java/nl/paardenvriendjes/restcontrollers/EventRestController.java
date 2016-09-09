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
import nl.paardenvriendjes.pvapi.daoimpl.EventDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Event;


public class EventRestController {


	@Autowired
	private EventDaoImpl eventservice;

	static Logger log = Logger.getLogger(EventDaoImpl.class.getName());

	
	
// -------------------Options Call --------------------------------------------------------------

@CrossOrigin
@RequestMapping(value = "/events", method = RequestMethod.OPTIONS)
public ResponseEntity<List<Event>> optionsCall() {
	ResponseEntity<List<Event>> ent = new ResponseEntity<List<Event>> (HttpStatus.NO_CONTENT);
	return ent;
}

// -------------------Retrieve All events--------------------------------------------------------

@CrossOrigin
@RequestMapping(value = "/events", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<List<Event>> listAllEvents() {
	List<Event> events = eventservice.listAll();
	if (events.isEmpty()) {
		return new ResponseEntity<List<Event>>(HttpStatus.NO_CONTENT);
		}
	return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
}

// -------------------Retrieve Single Event--------------------------------------------------------

@CrossOrigin
@RequestMapping(value = "/events/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Event> getEvent(@PathVariable("id") long id) {
	log.debug("Fetching Event with id " + id);
	Event event = eventservice.listOne(id);
	if (event == null) {
		log.debug("Event with id " + id + " not found");
		return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
	}
	return new ResponseEntity<Event>(event, HttpStatus.OK);
}

// -------------------Create a Event--------------------------------------------------------

@CrossOrigin
@RequestMapping(value = "/events/", method = RequestMethod.POST)
public ResponseEntity<Void> createEvent(@RequestBody Event event, UriComponentsBuilder ucBuilder) {
	log.debug("Creating " + event.getEventName());

	eventservice.save(event);

	HttpHeaders headers = new HttpHeaders();
	headers.setLocation(ucBuilder.path("/ventt/{id}").buildAndExpand(event.getId()).toUri());
	return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
}

// ------------------- Update an Event--------------------------------------------------------

@CrossOrigin
@RequestMapping(value = "/events/{id}", method = RequestMethod.PUT)
public ResponseEntity<Event> updateEvent(@PathVariable("id") long id, @RequestBody Event event) {
	log.debug("Updating ventt " + id);

	Event currentevent = eventservice.listOne(id);

	if (currentevent == null) {
		System.out.println("Event with id " + id + " not found");
		return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
	}

	eventservice.edit(currentevent);
	return new ResponseEntity<Event>(currentevent, HttpStatus.OK);
}

// ------------------- Delete a Event --------------------------------------------------------

@CrossOrigin
@RequestMapping(value = "/events/{id}", method = RequestMethod.DELETE)
public ResponseEntity<Event> deleteEvent(@PathVariable("id") long id) {
	log.debug("Fetching & Deleting Event with id " + id);

	Event event = eventservice.listOne(id);
	if (event == null) {
		System.out.println("Unable to delete Event with id " + id + " not found");
		return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
	}
	eventservice.remove(id);
	return new ResponseEntity<Event>(HttpStatus.NO_CONTENT);
}

// ------------------- Count all events --------------------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/events/count", method = RequestMethod.GET)
	public ResponseEntity<Integer> getEventCount() {
		log.debug("Fetching event count");
		int eventTotal = eventservice.count();
		return new ResponseEntity<Integer>(eventTotal, HttpStatus.OK);
	}
}