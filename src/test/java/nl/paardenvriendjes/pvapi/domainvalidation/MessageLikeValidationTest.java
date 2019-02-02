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
import nl.paardenvriendjes.pvapi.data.Message;
import nl.paardenvriendjes.pvapi.data.MessageLike;

public class MessageLikeValidationTest  extends AbstractTest{

	
	@Autowired
	private Validator validator;

	final Message message = new Message();
	
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
	
		MessageLike messagelike = new MessageLike();
		messagelike.setId(1L);
		messagelike.setMessage(message);
		Set<ConstraintViolation<MessageLike>> violations = validator.validate(messagelike);
		assertTrue(violations.isEmpty());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testMandatoryId() {
	
		MessageLike messagelike = new MessageLike();
		messagelike.setMessage(message);
		Set<ConstraintViolation<MessageLike>> violations = validator.validate(messagelike);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "must not be null");
	}
	

	@Test
	@Transactional
	@Rollback(true)
	public void testMandatoryMessage(){
	MessageLike messagelike = new MessageLike();
		messagelike.setId(1L);
		Set<ConstraintViolation<MessageLike>> violations = validator.validate(messagelike);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "must not be null");
	}	
}
