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
		Comment comment = new Comment();
		// mandatory
		comment.setId(1L);
		comment.setMessage(message);
		comment.setMember(member);
		// script attack
		comment.setComment("Have a nice Christmas");
		Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
		assertTrue(violations.isEmpty());	
		}

		
	@Test
	@Transactional
	@Rollback(true)
	public void testMaxSizeId() {
		Comment comment = new Comment();
		// mandatory
		comment.setId(10000000L);
		comment.setMessage(message);
		comment.setMember(member);
		// script attack
		comment.setComment("Have a nice Christmas");
		Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
		assertFalse(violations.isEmpty());		
		assertEquals(violations.iterator().next().getMessage(), "must be less than or equal to 9999999");
		}
		
	@Test
	@Transactional
	@Rollback(true)
	public void testNotNullComment() {
		Comment comment = new Comment();
		// mandatory
		comment.setId(1L);
		comment.setMember(member);
		Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
		assertFalse(violations.isEmpty());
		assertEquals(violations.iterator().next().getMessage(), "may not be null");
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testNotNullId() {
		Comment comment = new Comment();
		// mandatory
		comment.setMember(member);
		comment.setComment("Have a nice Christmas");
		Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
		assertFalse(violations.isEmpty());
		assertEquals(violations.iterator().next().getMessage(), "may not be null");
	}
	
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
		// html manipulation
		comment.setComment("<b>I want everything to be Bold</b> or <i> italic </i>");
		violations = validator.validate(comment);
		assertFalse(violations.isEmpty());
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
		// Check for special characters to be possible
		comment.setComment("I am in need of special characters: !@#$%^&*(){}[]/\":");
		violations = validator.validate(comment);
		assertTrue(violations.isEmpty());	
	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSanatizeSizeComment() {
			Comment comment = new Comment();
		// mandatory
		comment.setId(1L);
		comment.setMessage(message);
		comment.setMember(member);
		// Too short
		comment.setComment("TooShort");
		Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
		assertFalse(violations.isEmpty());	
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "size must be between 10 and 150");
		// TooLong
		comment.setComment("TooLong,Vandaag een ritje gemaakt door het garderense bos met esther."+ 
			 "We kwamen een wild zwijn tegen. Gelukkig geen ongelukken gebeurd."
			+ "Het bericht wordt nu wel lang");
		assertFalse(violations.isEmpty());		
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "size must be between 10 and 150");
	}
}