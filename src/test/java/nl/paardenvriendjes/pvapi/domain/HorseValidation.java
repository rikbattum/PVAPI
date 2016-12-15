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

public class HorseValidation   extends AbstractTest {

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
	}

	// Test Sanitization and Validations
	
	@Test
	@Transactional
	@Rollback(true)
	public void testRegularCaseToPass() {
		Horse horse = new Horse();
		// mandatory
		horse.setId(1L);
		horse.setName("Kirane");
		horse.setMember(member);
		Set<ConstraintViolation<Horse>> violations = validator.validate(horse);
		assertTrue(violations.isEmpty());	
		}
		
	@Test
	@Transactional
	@Rollback(true)
	public void testMaxSizeId() {
		Horse horse = new Horse();
		// mandatory
		horse.setId(10000000L);
		horse.setName("Kirane");
		horse.setMember(member);
		Set<ConstraintViolation<Horse>> violations = validator.validate(horse);
		assertFalse(violations.isEmpty());		
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "must be less than or equal to 9999999");
		}
		
	@Test
	@Transactional
	@Rollback(true)
	public void testNotNullMember() {
		Horse horse = new Horse();
		// mandatory
		horse.setId(1L);
		horse.setName("Kirane");
		Set<ConstraintViolation<Horse>> violations = validator.validate(horse);
		assertFalse(violations.isEmpty());		
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may not be null");
	}
		
		@Test
		@Transactional
		@Rollback(true)
		public void testValidProfilePicHorseUrlPattern() {
			Horse horse = new Horse();
			// mandatory
			horse.setId(1L);
			horse.setName("Kirane");
			horse.setMember(member);
			// regular case
			horse.setHorseimage1("http://res.cloudinary.com/epona/pictureXYZ.jpg");
			Set<ConstraintViolation<Horse>> violations = validator.validate(horse);
			assertTrue(violations.isEmpty());
			// switch to different match point
			horse.setHorseimage1("https://xxxxx.res.cloudinary.com/epona/pictureXYZ.jpg");
			violations = validator.validate(horse);
			assertFalse(violations.isEmpty());
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "must match \"^http://res.cloudinary.com/epona/.*\"");
			horse.setHorseimage2("https://xxxxx.res.cloudinary.com/epona/pictureXYZ.jpg");
			horse.setHorseimage3("https://xxxxx.res.cloudinary.com/epona/pictureXYZ.jpg");
			violations = validator.validate(horse);
			assertEquals(violations.size(), 3);
		}

	
		@Test
		@Transactional
		@Rollback(true)
		public void testValidProfilePicHorseUrlSanitize() {
			Horse horse = new Horse();
			// mandatory
			horse.setId(1L);
			horse.setName("Kirane");
			horse.setMember(member);
			// regular case
			horse.setHorseimage1("http://res.cloudinary.com/epona/pictureXYZ<script>alert 123</script>.jpg");
			Set<ConstraintViolation<Horse>> violations = validator.validate(horse);
			assertFalse(violations.isEmpty());
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
		}
		
	@Test
	@Transactional
	@Rollback(true)
	public void testStringInputSanitization() {
		Horse horse = new Horse();
		// mandatory
		horse.setId(1L);
		horse.setName("Kirane");
		horse.setMember(member);
		Set<ConstraintViolation<Horse>> violations = validator.validate(horse);
		assertTrue(violations.isEmpty());	
		horse.setName("<script>alert</script>");
		violations = validator.validate(horse);
		assertFalse(violations.isEmpty());
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
		horse.setName("Kirane");
		horse.setAfstamming("<b> Vet is alles moooier </b>"); 
		violations = validator.validate(horse);
		assertFalse(violations.isEmpty());
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
		horse.setAfstamming("Amigo X Horsine"); 
		horse.setOvermijnpaard("<script>alert malicious mail <script>");
		violations = validator.validate(horse);
		assertFalse(violations.isEmpty());
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
		}	
		
		
	@Test
	@Transactional
	@Rollback(true)
	public void testStringSizeSanitization() {
		Horse horse = new Horse();
		// mandatory
		horse.setId(1L);
		horse.setName("Kiraneasdsadasdasdasdasdasdasdasdas");
		horse.setMember(member);
		Set<ConstraintViolation<Horse>> violations = validator.validate(horse);
		violations = validator.validate(horse);
		assertFalse(violations.isEmpty());
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "size must be between 2 and 30");
		horse.setName("Kirane");
		horse.setAfstamming("KiraneXDirkususXDoradeDinatoDressuurdateo");
		violations = validator.validate(horse);
		assertFalse(violations.isEmpty());
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "size must be between 2 and 40");
		horse.setAfstamming("KiraneXDirkus");
		horse.setOvermijnpaard("lalallalalallalallalallallalallalalallalallalallallalallalalallalallalallallalallalalallalallalallallalallalalallalallalallallalallalalallalallalallallala");
		violations = validator.validate(horse);
		assertFalse(violations.isEmpty());
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "size must be between 2 and 150");
}		
}