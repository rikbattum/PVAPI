package nl.paardenvriendjes.pvapi.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.abstracttest.AbstractTest;

public class MessageCommentValidation   extends AbstractTest {

	@Autowired
	private Validator validator;
	
	final Member member = new Member();
	final Message message = new Message();
	
	
	
	@Before
	public void initialize() {
		
		// organize not null setup
		
		member.setVoornaam("Peddy");
		member.setAchternaam("Horsy");
		member.setGeboortedatum(new Date(12 - 6 - 1979));
		member.setEmail("peddy.horsey@mailinator.com");
		member.setProfileimage("http://res.cloudinary.com/epona/pictureXYZ.jpg");
		
		Message message = new Message();
		message.setId(1L);
		message.setMessage("Have a nice Christmas");
		message.setPiclink("http://res.cloudinary.com/epona/pictureXYZ.jpg");
		message.setMember(member);
		
	}

	// Test Sanitization and Validations
	
	@Test
	@Transactional
	@Rollback(true)
	public void testRegularCaseToPass() {
		MessageComment messageComment = new MessageComment();
		// mandatory
		messageComment.setId(1L);
		messageComment.setMessage(message);
		messageComment.setMember(member);
		// script attack
		messageComment.setComment("Have a nice Christmas");
		Set<ConstraintViolation<MessageComment>> violations = validator.validate(messageComment);
		assertTrue(violations.isEmpty());	
		}
		
	@Test
	@Transactional
	@Rollback(true)
	public void testNotNullComment() {
		MessageComment messageComment = new MessageComment();
		// mandatory
		messageComment.setId(1L);
		messageComment.setMember(member);
		Set<ConstraintViolation<MessageComment>> violations = validator.validate(messageComment);
		assertFalse(violations.isEmpty());
		assertEquals(violations.iterator().next().getMessage(), "may not be null");
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testNotNullId() {
		MessageComment messageComment = new MessageComment();
		// mandatory
		messageComment.setMember(member);
		messageComment.setComment("Have a nice Christmas");
		Set<ConstraintViolation<MessageComment>> violations = validator.validate(messageComment);
		assertFalse(violations.isEmpty());
		assertEquals(violations.iterator().next().getMessage(), "may not be null");
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testHTMLSanatizeComment() {
		MessageComment messageComment = new MessageComment();
		// mandatory
		messageComment.setId(1L);
		messageComment.setMessage(message);
		messageComment.setMember(member);
		// script attack
		messageComment.setComment("Have a nice Christmas <script> go to malicious site</script>");
		Set<ConstraintViolation<MessageComment>> violations = validator.validate(messageComment);
		assertFalse(violations.isEmpty());		
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
		// regular
		messageComment.setComment("Regular Comment");
		violations = validator.validate(messageComment);
		assertTrue(violations.isEmpty());
		// html manipulation
		messageComment.setComment("<b>I want everything to be Bold</b> or <i> italic </i>");
		violations = validator.validate(messageComment);
		assertFalse(violations.isEmpty());
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
		// Check for special characters to be possible
		messageComment.setComment("I am in need of special characters: !@#$%^&*(){}[]/\":");
		violations = validator.validate(messageComment);
		assertTrue(violations.isEmpty());	
	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSanatizeSizeComment() {
			MessageComment messageComment = new MessageComment();
		// mandatory
		messageComment.setId(1L);
		messageComment.setMessage(message);
		messageComment.setMember(member);
		// Too short
		messageComment.setComment("TooShort");
		Set<ConstraintViolation<MessageComment>> violations = validator.validate(messageComment);
		assertFalse(violations.isEmpty());	
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "size must be between 10 and 150");
		// TooLong
		messageComment.setComment("TooLong,Vandaag een ritje gemaakt door het garderense bos met esther."+ 
			 "We kwamen een wild zwijn tegen. Gelukkig geen ongelukken gebeurd."
			+ "Het bericht wordt nu wel lang");
		assertFalse(violations.isEmpty());		
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "size must be between 10 and 150");
	}
}