package nl.paardenvriendjes.pvapi.domainvalidation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import nl.paardenvriendjes.pvapi.abstracttest.AbstractTest;
import nl.paardenvriendjes.pvapi.domain.Event;
import nl.paardenvriendjes.pvapi.domain.EventComment;
import nl.paardenvriendjes.pvapi.domain.Member;

public class EventCommentValidationTest extends AbstractTest {

	@Autowired
	private Validator validator;

	final Member member = new Member();
	final Event event = new Event();

	@Before
	public void initialize() {
	}

	// Test Sanitization and Validations

	@Test
	@Transactional
	@Rollback(true)
	public void testRegularCaseToPass() {
		EventComment eventComment = new EventComment();
		// mandatory
		eventComment.setId(1L);
		eventComment.setEvent(event);
		eventComment.setMember(member);
		eventComment.setComment("Have a nice Christmas");
		Set<ConstraintViolation<EventComment>> violations = validator.validate(eventComment);
		assertTrue(violations.isEmpty());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testMandatoryComment() {
		EventComment eventComment = new EventComment();
		// mandatory
		eventComment.setId(1L);
		eventComment.setEvent(event);
		eventComment.setMember(member);
		Set<ConstraintViolation<EventComment>> violations = validator.validate(eventComment);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may not be null");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testMandatoryId() {
		EventComment eventComment = new EventComment();
		// mandatory
		eventComment.setEvent(event);
		eventComment.setMember(member);
		eventComment.setComment("Have a nice Christmas");
		Set<ConstraintViolation<EventComment>> violations = validator.validate(eventComment);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may not be null");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testMandatoryMessage() {
		EventComment eventComment = new EventComment();
		// mandatory
		eventComment.setId(1L);
		eventComment.setEvent(event);
		eventComment.setMember(member);
		Set<ConstraintViolation<EventComment>> violations = validator.validate(eventComment);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may not be null");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testHTMLSanatizeComment() {
		EventComment eventComment = new EventComment();
		// mandatory
		eventComment.setId(1L);
		eventComment.setEvent(event);
		eventComment.setMember(member);
		// script attack
		eventComment.setComment(("Have a nice Christmas <script> go to malicious site</script>"));
		Set<ConstraintViolation<EventComment>> violations = validator.validate(eventComment);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
		// regular
		eventComment.setComment("Regular Comment");
		violations = validator.validate(eventComment);
		assertTrue(violations.isEmpty());
		// html manipulation
		eventComment.setComment("<b>I want everything to be Bold</b> or <i> italic </i>");
		violations = validator.validate(eventComment);
		assertFalse(violations.isEmpty());
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
		// Check for special characters to be possible
		eventComment.setComment("I am in need of special characters: !@#$%^&*(){}[]/\":");
		violations = validator.validate(eventComment);
		assertTrue(violations.isEmpty());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSanatizeSizeComment() {
		EventComment eventComment = new EventComment();
		// mandatory
		eventComment.setId(1L);
		eventComment.setEvent(event);
		eventComment.setMember(member);
		// Too short
		eventComment.setComment("TooShort");
		Set<ConstraintViolation<EventComment>> violations = validator.validate(eventComment);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "size must be between 10 and 150");
		// TooLong
		eventComment.setComment("TooLong,Vandaag een ritje gemaakt door het garderense bos met esther."
				+ "We kwamen een wild zwijn tegen. Gelukkig geen ongelukken gebeurd."
				+ "Het bericht wordt nu wel lang");
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "size must be between 10 and 150");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testURLPatternPicLink() {
		EventComment eventComment = new EventComment();
		// mandatory
		eventComment.setId(1L);
		eventComment.setEvent(event);
		eventComment.setMember(member);
		eventComment.setComment("Have a nice Christmas");
		eventComment.setPiclink("https://xxxxx.res.cloudinary.com/epona/pictureXYZ.jpg");
		Set<ConstraintViolation<EventComment>> violations = validator.validate(eventComment);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "must match \"^http://res.cloudinary.com/epona/.*\"");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testHTMLSanitizePicLink() {
		EventComment eventComment = new EventComment();
		// mandatory
		eventComment.setId(1L);
		eventComment.setEvent(event);
		eventComment.setMember(member);
		eventComment.setComment("Have a nice Christmas");
		eventComment.setPiclink("http://res.cloudinary.com/epona/pictureXYZ<script>alert 123</script>.jpg");
		Set<ConstraintViolation<EventComment>> violations = validator.validate(eventComment);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSizePicLink() {
		EventComment eventComment = new EventComment();
		// mandatory
		eventComment.setId(1L);
		eventComment.setEvent(event);
		eventComment.setMember(member);
		eventComment.setComment("Have a nice Christmas");
		eventComment.setPiclink(
				"http://res.cloudinary.com/epona/pictureXYZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
		Set<ConstraintViolation<EventComment>> violations = validator.validate(eventComment);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "size must be between 0 and 100");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testHTMLSanatizeCommentLocation() {
		EventComment eventComment = new EventComment();
		// mandatory
		eventComment.setId(1L);
		eventComment.setEvent(event);
		eventComment.setMember(member);
		eventComment.setComment("Have a nice Christmas");
		eventComment.setCommmentLocation("<a href= \"malicious attack\">");
		Set<ConstraintViolation<EventComment>> violations = validator.validate(eventComment);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void tesSizeCommentLocation() {
		EventComment eventComment = new EventComment();
		// mandatory
		eventComment.setId(1L);
		eventComment.setEvent(event);
		eventComment.setMember(member);
		eventComment.setComment("Have a nice Christmas");
		eventComment.setCommmentLocation(
				"DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
		Set<ConstraintViolation<EventComment>> violations = validator.validate(eventComment);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "size must be between 0 and 150");
	}
}
