package nl.paardenvriendjes.pvapi.domainvalidation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.abstracttest.AbstractTest;
import nl.paardenvriendjes.pvapi.daotest.MemberDaoImplTest;
import nl.paardenvriendjes.pvapi.domain.Member;

public class MemberValidationTest extends AbstractTest {

	@Autowired
	private Validator validator;
	
	static Logger log = Logger.getLogger(MemberDaoImplTest.class.getName());
	
	// test Sanitization and Validations

		@Test
		@Transactional
		@Rollback(true)
		public void testHtmlSanitizeOverMij() {
			Member member = new Member();
			// mandatory
			member.setId(10001L);
			member.setVoornaam("Peddy");
			member.setAchternaam("Horsy");
			member.setGeboortedatum(new Date(12-6-1979));
			member.setEmail("peddy.horsey@mailinator.com");
			// not good
			member.setOvermij("I will hack you, <script>alert Click here for hack</script>");
			Set<ConstraintViolation<Member>> violations = validator.validate(member);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
		}
		
		@Test
		@Transactional
		@Rollback(true)
		public void testSizeOverMij() {
			Member member = new Member();
			// mandatory
			member.setId(10001L);
			member.setVoornaam("Peddy");
			member.setAchternaam("Horsy");
			member.setGeboortedatum(new Date(12-6-1979));
			member.setEmail("peddy.horsey@mailinator.com");
			// not good
			member.setOvermij("sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
			Set<ConstraintViolation<Member>> violations = validator.validate(member);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "size must be between 2 and 300");
		}
		
		@Test
		@Transactional
		@Rollback(true)
		public void testMandatoryId() {
			Member member = new Member();
			// mandatory
			member.setVoornaam("Peddy");
			member.setAchternaam("Horsy");
			member.setGeboortedatum(new Date(12 - 6 - 1979));
			member.setEmail("peddy.horsey@mailinator.com");
			Set<ConstraintViolation<Member>> violations = validator.validate(member);
			//missing Id
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "may not be null");
		}	

		@Test
		@Transactional
		@Rollback(true)
		public void testHtmlSanitizeNames() {
			Member member = new Member();
			// mandatory
			member.setId(10001L);
			member.setVoornaam("<i>Peddy</i>");
			member.setAchternaam("<p>Horsy</p>");
			member.setGeboortedatum(new Date(12 - 6 - 1979));
			member.setEmail("peddy.horsey@mailinator.com");
			Set<ConstraintViolation<Member>> violations = validator.validate(member);
			assertEquals(violations.size(), 2);
			assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
		}

		@Test
		@Transactional
		@Rollback(true)
		public void testSizeNames() {
			Member member = new Member();
			// mandatory
			member.setVoornaam("Peddy");
			member.setAchternaam("Horsy");
			member.setGeboortedatum(new Date(12 - 6 - 1979));
			member.setId(10001L);
			// not good, no @
			member.setEmail("peddy.horsey@mail.com");
			Set<ConstraintViolation<Member>> violations = validator.validate(member);
			assertTrue(violations.isEmpty());

			// voornaam
			member.setVoornaam("Peddssssssssssyxxxxxx");
			violations = validator.validate(member);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "size must be between 2 and 20");

			// achternaam
			member.setVoornaam("Rik");
			member.setAchternaam("Horsyyyyyyyyyyyyyyyxxxxxx");
			violations = validator.validate(member);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "size must be between 2 and 20");

			// usernamen
			member.setAchternaam("Horsy");
			member.setUsername("B");
			violations = validator.validate(member);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "size must be between 2 and 30");
		}
		
		@Test
		@Transactional
		@Rollback(true)
		public void testValidUrlPatternProfilePic() {
			Member member = new Member();
			// mandatory
			member.setId(10001L);
			member.setVoornaam("Peddy");
			member.setAchternaam("Horsy");
			member.setGeboortedatum(new Date(12 - 6 - 1979));
			member.setEmail("peddy.horsey@mailinator.com");
			// regular case
			member.setProfileimage("http://res.cloudinary.com/epona/pictureXYZ.jpg");
			Set<ConstraintViolation<Member>> violations = validator.validate(member);
			assertTrue(violations.isEmpty());
			// switch to different match point
			member.setProfileimage("https://xxxxx.res.cloudinary.com/epona/pictureXYZ.jpg");
			violations = validator.validate(member);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "must match \"^http://res.cloudinary.com/epona/.*\"");
		}

		@Test
		@Transactional
		@Rollback(true)
		public void testSanitizeHtmlProfilePicUrl() {
			Member member = new Member();
			// mandatory
			member.setId(10001L);
			member.setVoornaam("Peddy");
			member.setAchternaam("Horsy");
			member.setGeboortedatum(new Date(12 - 6 - 1979));
			member.setEmail("peddy.horsey@mailinator.com");
			// not good in imagelink
			member.setProfileimage("http://res.cloudinary.com/epona/pictureXYZ<script>alert 123</script>.jpg");
			Set<ConstraintViolation<Member>> violations = validator.validate(member);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "may have unsafe html content");
		}
		
		@Test
		@Transactional
		@Rollback(true)
		public void testSizeProfilePicUrl() {
			Member member = new Member();
			// mandatory
			member.setId(10001L);
			member.setVoornaam("Peddy");
			member.setAchternaam("Horsy");
			member.setGeboortedatum(new Date(12 - 6 - 1979));
			member.setEmail("peddy.horsey@mailinator.com");
			// not good in imagelink
			member.setProfileimage("http://res.cloudinary.com/epona/pictureXYZxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			Set<ConstraintViolation<Member>> violations = validator.validate(member);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "size must be between 0 and 100");
		}
		
		@Test
		@Transactional
		@Rollback(true)
		public void testValidPasswordSize() {
			Member member = new Member();
			// mandatory
			member.setId(10001L);
			member.setVoornaam("Peddy");
			member.setAchternaam("Horsy");
			member.setGeboortedatum(new Date(12 - 6 - 1979));
			member.setEmail("peddy.horsey@mailinator.com");
			// not good in passwordsize
			member.setPassword("12345678910lkjsdgrmstkcgdefdglerasrfdsfl");
			Set<ConstraintViolation<Member>> violations = validator.validate(member);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "size must be between 0 and 35");
		}
				
		@Test
		@Transactional
		@Rollback(true)
		public void testValidEmailAdres() {
			Member member = new Member();
			// mandatory
			member.setId(10001L);
			member.setVoornaam("Peddy");
			member.setAchternaam("Horsy");
			member.setGeboortedatum(new Date(12 - 6 - 1979));
			// not good, no @
			member.setEmail("peddy.horseymailator");
			Set<ConstraintViolation<Member>> violations = validator.validate(member);
			assertFalse(violations.isEmpty());
			// contains Please provide a valid email address & not a well-formed
			// email address in random order
			assertEquals(violations.size(), 2);

			// not good, no .
			member.setEmail("peddy.horseymailator@kpn");
			violations = validator.validate(member);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "Please provide a valid email address");

			// not good, size too long
			member.setEmail("peddsssssssssssssssssssssssssssssssssssssssssorseymailator@kpn.nl");
			violations = validator.validate(member);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "size must be between 7 and 60");

			// test regular email 1
			member.setEmail("peddy.horseymailator@kpn.nl");
			violations = validator.validate(member);
			assertTrue(violations.isEmpty());

			// test regular email 2
			member.setEmail("peddy.horseymailato198786@mail.com");
			violations = validator.validate(member);
			assertTrue(violations.isEmpty());
		}
		
		@Test
		@Transactional
		@Rollback(true)
		public void testMandatoryEmailAttribute() {
			Member member = new Member();
			member.setId(10001L);
			member.setAuth0user_id("auth0|582dff439c968b412345678");
			member.setVoornaam("Peddy");
			member.setAchternaam("Horsy");
			member.setGeboortedatum(new Date(12 - 6 - 1979));
			Set<ConstraintViolation<Member>> violations = validator.validate(member);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "may not be null");
		}

		@Test
		@Transactional
		@Rollback(true)
		public void testGeboorteDatum() {
			Member member = new Member();
			member.setId(10001L);
			member.setVoornaam("Peddy");
			member.setAchternaam("Horsy");
			member.setEmail("peddy.horsey@mailinator.com");
			member.setGeboortedatum(new Date(2025, 06, 23, 00, 00));
			log.debug(member.getGeboortedatum());
			Set<ConstraintViolation<Member>> violations = validator.validate(member);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "must be in the past");
		}

		@Test
		@Transactional
		@Rollback(true)
		public void testMaxLengthAuth0Id() {
			Member member = new Member();
			member.setId(10001L);
			member.setAuth0user_id("auth0|582dff439ca666c66c968b412345678");
			member.setVoornaam("Peddy");
			member.setAchternaam("Horsy");
			member.setEmail("peddy.horsey@mailinator.com");
			member.setGeboortedatum(new Date(12 - 6 - 1979));
			Set<ConstraintViolation<Member>> violations = validator.validate(member);
			assertEquals(violations.size(), 1);
			assertEquals(violations.iterator().next().getMessage(), "size must be between 20 and 35");
		}
}
