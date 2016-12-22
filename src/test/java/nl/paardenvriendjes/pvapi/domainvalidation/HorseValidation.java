package nl.paardenvriendjes.pvapi.domainvalidation;

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
import nl.paardenvriendjes.pvapi.domain.Horse;
import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.domain.Message;
import nl.paardenvriendjes.pvapi.domain.Paspoort;

public class HorseValidation extends AbstractTest {

	@Autowired
	private Validator validator;

	final Member member = new Member();

	@Before
	public void initialize() {
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
		horse.setPaspoort(new Paspoort());
		Set<ConstraintViolation<Horse>> violations = validator.validate(horse);
		assertTrue(violations.isEmpty());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testMandatoryId() {
		Horse horse = new Horse();
		horse.setName("Kirane");
		horse.setMember(member);
		horse.setPaspoort(new Paspoort());
		Set<ConstraintViolation<Horse>> violations = validator.validate(horse);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may not be null");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testMandatoryMember() {
		Horse horse = new Horse();
		// mandatory
		horse.setId(1L);
		horse.setName("Kirane");
		horse.setPaspoort(new Paspoort());
		Set<ConstraintViolation<Horse>> violations = validator.validate(horse);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may not be null");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testMandatoryName() {
		Horse horse = new Horse();
		// mandatory
		horse.setId(1L);
		horse.setMember(member);
		horse.setPaspoort(new Paspoort());
		Set<ConstraintViolation<Horse>> violations = validator.validate(horse);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may not be null");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testMandatoryPaspoort() {
		Horse horse = new Horse();
		// mandatory
		horse.setId(1L);
		horse.setMember(member);
		horse.setPaspoort(new Paspoort());
		Set<ConstraintViolation<Horse>> violations = validator.validate(horse);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may not be null");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testUrlPatternProfilePicHorse() {
		Horse horse = new Horse();
		// mandatory
		horse.setId(1L);
		horse.setName("Kirane");
		horse.setMember(member);
		horse.setPaspoort(new Paspoort());
		// regular case
		horse.setHorseimage1("http://res.cloudinary.com/epona/pictureXYZ.jpg");
		Set<ConstraintViolation<Horse>> violations = validator.validate(horse);
		assertTrue(violations.isEmpty());
		// switch to different match point
		horse.setHorseimage1("https://xxxxx.res.cloudinary.com/epona/pictureXYZ.jpg");
		violations = validator.validate(horse);
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
	public void testUrlSanitizeProfilePicHorse() {
		Horse horse = new Horse();
		// mandatory
		horse.setId(1L);
		horse.setName("Kirane");
		horse.setMember(member);
		horse.setPaspoort(new Paspoort());
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
	public void testSizeUrlProfilePicHorse() {
		Horse horse = new Horse();
		// mandatory
		horse.setId(1L);
		horse.setName("Kirane");
		horse.setMember(member);
		horse.setPaspoort(new Paspoort());
		// regular case
		horse.setHorseimage1(
				"http://res.cloudinary.com/epona/pictureXYZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
		Set<ConstraintViolation<Horse>> violations = validator.validate(horse);
		assertFalse(violations.isEmpty());
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "size must be between 0 and 100");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testHTMLSanitizationOfStringInput() {
		Horse horse = new Horse();
		// mandatory
		horse.setId(1L);
		horse.setName("Kirane");
		horse.setMember(member);
		horse.setPaspoort(new Paspoort());
		Set<ConstraintViolation<Horse>> violations = validator.validate(horse);
		assertTrue(violations.isEmpty());
		horse.setName("<script>alert</script>");
		violations = validator.validate(horse);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
		horse.setName("Kirane");
		horse.setAfstamming("<b> Vet is alles moooier </b>");
		violations = validator.validate(horse);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
		horse.setAfstamming("Amigo X Horsine");
		horse.setOvermijnpaard("<script>alert malicious mail <script>");
		violations = validator.validate(horse);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSizeOfStringInput() {
		Horse horse = new Horse();
		// mandatory
		horse.setId(1L);
		horse.setName("Kiraneasdsadasdasdasdasdasdasdasdas");
		horse.setMember(member);
		horse.setPaspoort(new Paspoort());
		Set<ConstraintViolation<Horse>> violations = validator.validate(horse);
		violations = validator.validate(horse);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "size must be between 2 and 30");
		horse.setName("Kirane");
		horse.setAfstamming("KiraneXDirkususXDoradeDinatoDressuurdateo");
		violations = validator.validate(horse);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "size must be between 2 and 40");
		horse.setAfstamming("KiraneXDirkus");
		horse.setOvermijnpaard(
				"lalallalalallalallalallallalallalalallalallalallallalallalalallalallalallallalallalalallalallalallallalallalalallalallalallallalallalalallalallalallallala");
		violations = validator.validate(horse);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "size must be between 2 and 150");
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testMaxStokmaat() {
		Horse horse = new Horse();
		// mandatory
		horse.setId(1L);
		horse.setName("Kirane");
		horse.setMember(member);
		horse.setPaspoort(new Paspoort());
		horse.setStokmaat(300);
		Set<ConstraintViolation<Horse>> violations = validator.validate(horse);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "must be less than or equal to 200");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testMaxWaarde() {
		Horse horse = new Horse();
		// mandatory
		horse.setId(1L);
		horse.setName("Kirane");
		horse.setMember(member);
		horse.setPaspoort(new Paspoort());
		horse.setWaarde(6000000);
		Set<ConstraintViolation<Horse>> violations = validator.validate(horse);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "must be less than or equal to 5000000");
	}
	

	@Test
	@Transactional
	@Rollback(true)
	public void testGeboorteDatum() {
		Horse horse = new Horse();
		// mandatory
		horse.setId(1L);
		horse.setName("Kirane");
		horse.setMember(member);
		horse.setPaspoort(new Paspoort());
		horse.setGeboortedatum(new Date(2025, 06, 23, 00, 00));
		Set<ConstraintViolation<Horse>> violations = validator.validate(horse);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "must be in the past");
	}
}