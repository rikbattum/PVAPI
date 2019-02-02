package nl.paardenvriendjes.pvapi.domainvalidation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.abstracttest.AbstractTest;
import nl.paardenvriendjes.pvapi.data.Member;
import nl.paardenvriendjes.pvapi.data.Message;
import nl.paardenvriendjes.pvapi.data.MessageComment;

public class MessageCommentValidationTest   extends AbstractTest {

	@Autowired
	private Validator validator;
	
	final Member member = new Member();
	final Message message = new Message();
	
	
	
	@Before
	public void initialize() {
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
		messageComment.setComment("Have a nice Christmas");
		Set<ConstraintViolation<MessageComment>> violations = validator.validate(messageComment);
		assertTrue(violations.isEmpty());	
		}
		
	@Test
	@Transactional
	@Rollback(true)
	public void testMandatoryComment() {
		MessageComment messageComment = new MessageComment();
		// mandatory
		messageComment.setId(1L);
		messageComment.setMessage(message);
		messageComment.setMember(member);
		Set<ConstraintViolation<MessageComment>> violations = validator.validate(messageComment);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "must not be null");
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testMandatoryId() {
		MessageComment messageComment = new MessageComment();
		// mandatory
		messageComment.setMember(member);
		messageComment.setComment("Have a nice Christmas");
		messageComment.setMessage(message);
		Set<ConstraintViolation<MessageComment>> violations = validator.validate(messageComment);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "must not be null");
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testMandatoryMessage() {
		MessageComment messageComment = new MessageComment();
		// mandatory
		messageComment.setId(1L);
		messageComment.setMember(member);
		messageComment.setComment("Have a nice Christmas");
		Set<ConstraintViolation<MessageComment>> violations = validator.validate(messageComment);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "must not be null");
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
		assertEquals(violations.size(), 1);		
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
		// regular
		messageComment.setComment("Regular Comment");
		violations = validator.validate(messageComment);
		assertTrue(violations.isEmpty());
		// html manipulation
		messageComment.setComment("<b>I want everything to be Bold</b> or <i> italic </i>");
		violations = validator.validate(messageComment);
		assertEquals(violations.size(), 1);
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
	
	@Test
	@Transactional
	@Rollback(true)
	public void testURLPatternPicLink() {
		MessageComment messageComment = new MessageComment();
		// mandatory
		messageComment.setId(1L);
		messageComment.setMember(member);
		messageComment.setMessage(message);
		messageComment.setComment("Have a nice Christmas");
		messageComment.setPiclink("https://xxxxx.res.cloudinary.com/epona/pictureXYZ.jpg");
		Set<ConstraintViolation<MessageComment>> violations = validator.validate(messageComment);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "must match \"^http://res.cloudinary.com/epona/.*\"");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testHTMLSanitizePicLink() {
		MessageComment messageComment = new MessageComment();
		// mandatory
		messageComment.setId(1L);
		messageComment.setMember(member);
		messageComment.setMessage(message);
		messageComment.setComment("Have a nice Christmas");
		messageComment.setPiclink("http://res.cloudinary.com/epona/pictureXYZ<script>alert 123</script>.jpg");
		Set<ConstraintViolation<MessageComment>> violations = validator.validate(messageComment);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSizePicLink() {
		MessageComment messageComment = new MessageComment();
		// mandatory
		messageComment.setId(1L);
		messageComment.setMember(member);
		messageComment.setMessage(message);
		messageComment.setComment("Have a nice Christmas");
		messageComment.setPiclink("http://res.cloudinary.com/epona/pictureXYZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
		Set<ConstraintViolation<MessageComment>> violations = validator.validate(messageComment);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "size must be between 0 and 100");
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testHTMLSanatizeCommentLocation() {
		MessageComment messageComment = new MessageComment();
		// mandatory
		messageComment.setId(1L);
		messageComment.setMember(member);
		messageComment.setMessage(message);
		messageComment.setComment("Have a nice Christmas");
		messageComment.setCommmentLocation("<a href= \"malicious attack\">");
		Set<ConstraintViolation<MessageComment>> violations = validator.validate(messageComment);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void tesSizeCommentLocation() {
		MessageComment messageComment = new MessageComment();
		// mandatory
		messageComment.setId(1L);
		messageComment.setMember(member);
		messageComment.setMessage(message);
		messageComment.setComment("Have a nice Christmas");
		messageComment.setCommmentLocation("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
		Set<ConstraintViolation<MessageComment>> violations = validator.validate(messageComment);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "size must be between 0 and 150");
	}
}