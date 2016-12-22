package nl.paardenvriendjes.pvapi.domainvalidation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.abstracttest.AbstractTest;
import nl.paardenvriendjes.pvapi.domain.Event;
import nl.paardenvriendjes.pvapi.domain.EventLike;

public class EventLikeValidation extends AbstractTest{

	@Autowired
	private Validator validator;

	final Event event = new Event();
	
	@Before
	public void initialize() {
	}

	@After
	public void after() throws Throwable {
	}

	// Test Sanitization and Validations

	@Test
	@Transactional
	@Rollback(true)
	public void testRegularCaseToPass() {
	
		EventLike eventlike = new EventLike();
		eventlike.setId(1L);
		eventlike.setEvent(event);
		Set<ConstraintViolation<EventLike>> violations = validator.validate(eventlike);
		assertTrue(violations.isEmpty());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testMandatoryId() {
	
		EventLike eventlike = new EventLike();
		eventlike.setEvent(event);
		Set<ConstraintViolation<EventLike>> violations = validator.validate(eventlike);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may not be null");
	}
	

	@Test
	@Transactional
	@Rollback(true)
	public void testMandatoryEvent(){
	EventLike eventlike = new EventLike();
		eventlike.setId(1L);
		Set<ConstraintViolation<EventLike>> violations = validator.validate(eventlike);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may not be null");
	}	
	
}
