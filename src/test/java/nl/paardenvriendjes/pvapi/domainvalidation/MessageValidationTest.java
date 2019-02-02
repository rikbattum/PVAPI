package nl.paardenvriendjes.pvapi.domainvalidation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import nl.paardenvriendjes.pvapi.data.Member;
import nl.paardenvriendjes.pvapi.data.Message;

public class MessageValidationTest extends AbstractTest {

	@Autowired
	private Validator validator;

	final Member member = new Member();
	
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
		Message message = new Message();
		message.setId(1L);
		message.setMessage("Have a nice Christmas");
		message.setPiclink("http://res.cloudinary.com/epona/pictureXYZ.jpg");
		message.setMember(member);
		Set<ConstraintViolation<Message>> violations = validator.validate(message);
		assertTrue(violations.isEmpty());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testMandatoryId() {
		Message message = new Message();
		message.setMessage("Have a nice Christmas");
		message.setPiclink("http://res.cloudinary.com/epona/pictureXYZ.jpg");
		message.setMember(member);
		Set<ConstraintViolation<Message>> violations = validator.validate(message);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "must not be null");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testMandatoryMessage() {
		Message message = new Message();
		message.setId(1L);
		message.setPiclink("http://res.cloudinary.com/epona/pictureXYZ.jpg");
		message.setMember(member);
		Set<ConstraintViolation<Message>> violations = validator.validate(message);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "must not be null");
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testMandatoryMember() {
		Message message = new Message();
		// mandatory
		message.setId(1L);
		// script attack
		message.setMessage("Have a nice Christmas");
		message.setPiclink("http://res.cloudinary.com/epona/pictureXYZ.jpg");
		Set<ConstraintViolation<Message>> violations = validator.validate(message);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "must not be null");
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testHTMLSanatizeMessage() {
		Message message = new Message();
		// mandatory
		message.setId(1L);
		// script attack
		message.setMessage("Have a nice Christmas <script> go to malicious site</script>");
		message.setPiclink("http://res.cloudinary.com/epona/pictureXYZ.jpg");
		message.setMember(member);
		Set<ConstraintViolation<Message>> violations = validator.validate(message);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
		// regular
		message.setMessage("Regular Message");
		violations = validator.validate(message);
		assertTrue(violations.isEmpty());
		// html manipulation
		message.setMessage("<b>I want everything to be Bold</b> or <i> italic </i>");
		violations = validator.validate(message);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSizeMessage() {
		Message message = new Message();
		// mandatory
		message.setId(1L);
		// script attack
		message.setMessage("TooShort");
		message.setPiclink("http://res.cloudinary.com/epona/pictureXYZ.jpg");
		message.setMember(member);
		Set<ConstraintViolation<Message>> violations = validator.validate(message);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "size must be between 10 and 150");
		// regular
		message.setMessage("TooLong,Vandaag een ritje gemaakt door het garderense bos met esther."
				+ "We kwamen een wild zwijn tegen. Gelukkig geen ongelukken gebeurd."
				+ "Het bericht wordt nu wel lang");
		violations = validator.validate(message);
		assertEquals(violations.iterator().next().getMessage(), "size must be between 10 and 150");
	}
	

	@Test
	@Transactional
	@Rollback(true)
	public void testValidUrlPatternPicUrl() {
		Message message = new Message();
		// mandatory
		message.setId(1L);
		message.setMessage("Have a nice Christmas");
		message.setPiclink("http://res.cloudinary.com/epona/pictureXYZ.jpg");
		message.setMember(member);
		// regular case
		Set<ConstraintViolation<Message>> violations = validator.validate(message);
		assertTrue(violations.isEmpty());
		// switch to different match point
		message.setPicLinkSecond("https://xxxxx.res.cloudinary.com/epona/pictureXYZ.jpg");
		violations = validator.validate(message);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "must match \"^http://res.cloudinary.com/epona/.*\"");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSanitizeHtmlPicUrl() {
		Message message = new Message();
		// mandatory
		message.setId(1L);
		message.setMessage("Have a nice Christmas");
		message.setPiclink("http://res.cloudinary.com/epona/q<script>x</script>pictureXYZ.jpg");
		message.setMember(member);
		Set<ConstraintViolation<Message>> violations = validator.validate(message);
		violations = validator.validate(message);
		assertFalse(violations.isEmpty());
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
		message.setPicLinkSecond("http://res.cloudinary.com/epona/<script>x</script>pictureXYZ.jpg");
		message.setPicLinkThird("http://res.cloudinary.com/epona/<script>x</script>pictureXYZ.jpg");
		// caught all 3 violations on piclinks
		violations = validator.validate(message);
		assertFalse(violations.isEmpty());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSizePicUrl() {
		Message message = new Message();
		// mandatory
		message.setId(1L);
		message.setMessage("Have a nice Christmas");
		message.setPiclink("http://res.cloudinary.com/epona/pictureXYZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ.jpg");
		message.setMember(member);
		Set<ConstraintViolation<Message>> violations = validator.validate(message);
		violations = validator.validate(message);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "size must be between 0 and 100");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testHTMLSanatizeLocation() {
		Message message = new Message();
		// mandatory
		message.setId(1L);
		message.setMessage("Have a nice Christmas");
		message.setPiclink("http://res.cloudinary.com/epona/pictureXYZ.jpg");
		message.setMember(member);
		// script attack
		message.setMessageLocation("<i> Nice Location </i>");
		Set<ConstraintViolation<Message>> violations = validator.validate(message);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSizeLocation() {
		Message message = new Message();
		// mandatory
		message.setId(1L);
		message.setMessage("Have a nice Christmas");
		message.setPiclink("http://res.cloudinary.com/epona/pictureXYZ.jpg");
		message.setMessageLocation("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
		Set<ConstraintViolation<Message>> violations = validator.validate(message);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "must not be null");
	}	
}
