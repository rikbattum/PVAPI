package nl.paardenvriendjes.pvapi.domainvalidation;

import static org.junit.Assert.*;

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
import nl.paardenvriendjes.pvapi.domain.Message;
import nl.paardenvriendjes.pvapi.domain.Paspoort;

public class EventValidation extends AbstractTest{

		@Autowired
		private Validator validator;

		final Paspoort paspoort = new Paspoort();

		@Before
		public void initialize() {
		}

		@After
		public void after() throws Throwable {
		}


		@Test
		@Transactional
		@Rollback(true)
		public void testRegularCaseToPass() {
			Event event = new Event();
			event.setId(1L);
			event.setEventName("buitenrit");
			event.setPaspoort(paspoort);
			Set<ConstraintViolation<Event>> violations = validator.validate(event);
			assertTrue(violations.isEmpty());
		}
		
		
		@Test
		@Transactional
		@Rollback(true)
		public void testMandatoryId() {
			Event event = new Event();
			event.setEventName("buitenrit");
			event.setPaspoort(paspoort);
			Set<ConstraintViolation<Event>> violations = validator.validate(event);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "may not be null");
		}
		
		@Test
		@Transactional
		@Rollback(true)
		public void testMandatoryPaspoort() {
			Event event = new Event();
			event.setEventName("buitenrit");
			event.setId(1L);
			Set<ConstraintViolation<Event>> violations = validator.validate(event);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "may not be null");
		}
		
		@Test
		@Transactional
		@Rollback(true)
		public void testEventName() {
			Event event = new Event();
			event.setId(1L);
			event.setPaspoort(paspoort);
			Set<ConstraintViolation<Event>> violations = validator.validate(event);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "may not be null");
		}
		
		@Test
		@Transactional
		@Rollback(true)
		public void testHTMLSanitizeEventName() {
			Event event = new Event();
			event.setId(1L);
			event.setPaspoort(paspoort);
			event.setEventName("buitenrit<script>");
			Set<ConstraintViolation<Event>> violations = validator.validate(event);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
		}
		
		@Test
		@Transactional
		@Rollback(true)
		public void testSizeEventName() {
			Event event = new Event();
			event.setId(1L);
			event.setPaspoort(paspoort);
			event.setEventName("buitenritzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
			Set<ConstraintViolation<Event>> violations = validator.validate(event);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "size must be between 2 and 25");
		}
		
		@Test
		@Transactional
		@Rollback(true)
		public void testHTMLSanitizeEventMessage() {
			Event event = new Event();
			event.setId(1L);
			event.setPaspoort(paspoort);
			event.setEventName("buitenrit");
			event.setMessage("test <i> test");
			Set<ConstraintViolation<Event>> violations = validator.validate(event);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
		}
		
		@Test
		@Transactional
		@Rollback(true)
		public void testSizeEventMessage() {
			Event event = new Event();
			event.setId(1L);
			event.setPaspoort(paspoort);
			event.setEventName("buitenrit");
			event.setMessage("test test test test test test test test test test test test");
			Set<ConstraintViolation<Event>> violations = validator.validate(event);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "size must be between 2 and 20");
		}
		
		@Test
		@Transactional
		@Rollback(true)
		public void testHTMLSanitizeEventLocation() {
			Event event = new Event();
			event.setId(1L);
			event.setPaspoort(paspoort);
			event.setEventName("buitenrit");
			event.setEventLocation("test <i> test");
			Set<ConstraintViolation<Event>> violations = validator.validate(event);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
		}
		
		@Test
		@Transactional
		@Rollback(true)
		public void testSizeEventLocation() {
			Event event = new Event();
			event.setId(1L);
			event.setPaspoort(paspoort);
			event.setEventName("buitenrit");
			event.setEventLocation("test test test test test test test test test test test test test");
			Set<ConstraintViolation<Event>> violations = validator.validate(event);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "size must be between 0 and 40");
		}
			
		@Test
		@Transactional
		@Rollback(true)
		public void testMaxSizeScoreAndRanking() {
			Event event = new Event();
			event.setId(1L);
			event.setPaspoort(paspoort);
			event.setEventName("buitenrit");
			event.setRanking(100000);
			Set<ConstraintViolation<Event>> violations = validator.validate(event);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "must be less than or equal to 1000");
			event.setRanking(1);
			event.setScore(109023);
			violations = validator.validate(event);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "must be less than or equal to 1000");
			}
		
		@Test
		@Transactional
		@Rollback(true)
		public void testValidUrlPatternPicUrl() {
			Event event = new Event();
			event.setId(1L);
			event.setPaspoort(paspoort);
			event.setEventName("buitenrit");
			event.setPiclink("https://xxxxx.res.cloudinary.com/epona/pictureXYZ.jpg");
			Set<ConstraintViolation<Event>> violations = validator.validate(event);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "must match \"^http://res.cloudinary.com/epona/.*\"");
		}
		
		@Test
		@Transactional
		@Rollback(true)
		public void testSanitizeHtmlPicUrl() {
			Event event = new Event();
			event.setId(1L);
			event.setPaspoort(paspoort);
			event.setEventName("buitenrit");
			event.setPiclink("http://res.cloudinary.com/epona/q<script>x</script>pictureXYZ.jpg");
			Set<ConstraintViolation<Event>> violations = validator.validate(event);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
		}
		
		@Test
		@Transactional
		@Rollback(true)
		public void testSizePicUrl() {
			Event event = new Event();
			event.setId(1L);
			event.setPaspoort(paspoort);
			event.setEventName("buitenrit");
			event.setPiclink("http://res.cloudinary.com/epona/pictureXYZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ.jpg");
			Set<ConstraintViolation<Event>> violations = validator.validate(event);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "size must be between 0 and 100");
		}
}
