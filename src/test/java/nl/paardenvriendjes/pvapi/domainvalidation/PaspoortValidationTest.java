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
import nl.paardenvriendjes.pvapi.data.Horse;
import nl.paardenvriendjes.pvapi.data.Paspoort;

public class PaspoortValidationTest extends AbstractTest {

	@Autowired
	private Validator validator;

	final Horse horse = new Horse();

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
		Paspoort paspoort = new Paspoort();
		paspoort.setId(1L);
		paspoort.setHorse(horse);
		paspoort.setPaspoortName("paspoortName");
		Set<ConstraintViolation<Paspoort>> violations = validator.validate(paspoort);
		assertTrue(violations.isEmpty());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testMandatoryId() {
		Paspoort paspoort = new Paspoort();
		paspoort.setHorse(horse);
		paspoort.setPaspoortName("paspoortName");
		Set<ConstraintViolation<Paspoort>> violations = validator.validate(paspoort);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may not be null");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testMandatoryPaspoortName() {
		Paspoort paspoort = new Paspoort();
		paspoort.setId(1L);
		paspoort.setHorse(horse);
		Set<ConstraintViolation<Paspoort>> violations = validator.validate(paspoort);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may not be null");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testMandatoryHorse() {
		Paspoort paspoort = new Paspoort();
		paspoort.setId(1L);
		paspoort.setPaspoortName("paspoortName");
		Set<ConstraintViolation<Paspoort>> violations = validator.validate(paspoort);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may not be null");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testHTMLSanitizePaspoortName() {
		Paspoort paspoort = new Paspoort();
		paspoort.setId(1L);
		paspoort.setHorse(horse);
		paspoort.setPaspoortName("paspoor<br>");
		Set<ConstraintViolation<Paspoort>> violations = validator.validate(paspoort);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSizePaspoortName() {
		Paspoort paspoort = new Paspoort();
		paspoort.setId(1L);
		paspoort.setHorse(horse);
		paspoort.setPaspoortName("paspoortNameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		Set<ConstraintViolation<Paspoort>> violations = validator.validate(paspoort);
		assertEquals(violations.size(), 1);
		assertEquals(violations.iterator().next().getMessage(), "size must be between 2 and 20");
	}

}
