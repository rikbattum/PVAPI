package nl.paardenvriendjes.pvapi.domain;

import static org.junit.Assert.*;

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

public class CommentValidation   extends AbstractTest {

	@Autowired
	private Validator validator;
	
	final Member member = new Member();
	final Message message = new Message();
	
	
	
	@Before
	public void initialize() {
		
		// organize not null setup
		
		member.setId(1L);
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
	public void testHTMLSanatizeComment() {
		Comment comment = new Comment();
		// mandatory
		comment.setId(1L);
		comment.setMessage(message);
		comment.setMember(member);
		// script attack
		comment.setComment("Have a nice Christmas <script> go to malicious site</script>");
		Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
		assertFalse(violations.isEmpty());
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
		// regular
		comment.setComment("Regular Comment");
		violations = validator.validate(comment);
		assertTrue(violations.isEmpty());
//		// html manipulation
		comment.setComment("<b>I want everything to be Bold</b> or <i> italic </i>");
		violations = validator.validate(comment);
		assertFalse(violations.isEmpty());
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
	}

//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testSanatizeSizeComment() {
//		Message message = new Message();
//		// mandatory
//		message.setId(1L);
//		//script attack
//		message.setMessage("TooShort");
//		message.setPiclink("http://res.cloudinary.com/epona/pictureXYZ.jpg");
//		Set<ConstraintViolation<Message>> violations = validator.validate(message);
//		assertFalse(violations.isEmpty());
//		assertEquals(violations.iterator().next().getMessage(), "size must be between 10 and 150");
//		//regular
//		message.setMessage("TooLong,Vandaag een ritje gemaakt door het garderense bos met esther." + 
//			"We kwamen een wild zwijn tegen. Gelukkig geen ongelukken gebeurd." + 
//			"Het bericht wordt nu wel lang");
//		violations = validator.validate(message);
//		assertEquals(violations.iterator().next().getMessage(), "size must be between 10 and 150");
//	}
//	
//	
	
	
	
	
	
	
	
	
}
