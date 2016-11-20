package nl.paardenvriendjes.pvapi.daotest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.paardenvriendjes.pvapi.abstracttest.AbstractTest;
import nl.paardenvriendjes.pvapi.daoimpl.MemberDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Event;
import nl.paardenvriendjes.pvapi.domain.Horse;
import nl.paardenvriendjes.pvapi.domain.Interesse;
import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.domain.Message;
import nl.paardenvriendjes.pvapi.enumerations.Geslacht;
import nl.paardenvriendjes.pvapi.enumerations.LineType;
import nl.paardenvriendjes.pvapi.enumerations.MessageType;
import nl.paardenvriendjes.pvapi.enumerations.OtherSport;
import nl.paardenvriendjes.pvapi.enumerations.Place;
import nl.paardenvriendjes.pvapi.enumerations.SportLevel;
import nl.paardenvriendjes.pvapi.enumerations.Vervoer;
import nl.paardenvriendjes.testutil.TestUtilDataSetup;

public class MemberDaoImplTest extends AbstractTest {

	@Autowired
	private MemberDaoImpl memberService;

	@Autowired
	private TestUtilDataSetup testUtilDataSetup;

	@Autowired
	private Validator validator;

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	static Logger log = Logger.getLogger(MemberDaoImplTest.class.getName());
	ObjectMapper mapper = new ObjectMapper();

	public void initialize() {

	}

	@After
	public void after() throws Throwable {

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testMemberCreationExactNumberOfEntries() throws Exception {

		// Arrange
		testUtilDataSetup.setMembers();

		// Act
		List<Member> memberList = memberService.listAll();

		// Assert
		assertThat(memberList.size(), Is.is(8));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testMemberCreationIfDataIsCorrect() throws Exception {

		// Arrange
		testUtilDataSetup.setMembers();

		// Act
		List<Member> memberList = memberService.listAll();
		Member x = memberList.get(0);

		// Assert

		assertThat(x.getPassword(), Is.is("Superdaddy79"));
		assertThat(x.getAchternaam(), Is.is("van Battum"));
		assertThat(x.getVoornaam(), Is.is("Rik"));
		assertThat(x.getGeboortedatum().toString(), Is.is("Fri Jan 12 00:06:00 CET 1979"));
		assertThat(simpleDateFormat.format(x.getCreatedonDate()), Is.is(simpleDateFormat.format(new Date())));
		assertThat(x.getPlace().toString(), Is.is("AALDEN"));
		assertThat(x.getProfileimage(), Is.is("http://res.cloudinary.com/epona/pictureXYZ.jpg"));
		assertThat(x.getOvermij(), Is.is("ik ben een paardenliefhebber"));
		assertThat(x.getUsername(), Is.is("rikbattum"));

		assertThat(memberList.size(), Is.is(8));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testMemberCreationMultibleMemberSameData() throws Exception {

		// Arrange
		// 10 times 8 members
		testUtilDataSetup.setMembers();
		testUtilDataSetup.setMembers();
		testUtilDataSetup.setMembers();
		testUtilDataSetup.setMembers();
		testUtilDataSetup.setMembers();
		testUtilDataSetup.setMembers();
		testUtilDataSetup.setMembers();
		testUtilDataSetup.setMembers();
		testUtilDataSetup.setMembers();
		testUtilDataSetup.setMembers();

		// Act
		List<Member> memberList = memberService.listAll();

		// Assert
		assertThat(memberList.size(), Is.is(80));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testMemberDeletion() throws Exception {

		// Arrange
		testUtilDataSetup.setMembers();
		List<Member> memberList = memberService.listAll();

		// Assert
		assertThat(memberList.size(), Is.is(8));

		// Act
		Member member = memberList.get(0);
		memberService.remove(member);
		List<Member> memberListAgain = memberService.listAll();

		// Assert
		assertThat(memberListAgain.size(), Is.is(8));
		assertFalse(memberListAgain.get(7).getActive());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testMemberUpdate() throws Exception {

		// Arrange
		testUtilDataSetup.setMembers();
		List<Member> memberList = memberService.listAll();

		// Assert
		assertThat(memberList.size(), Is.is(8));

		// Act
		Member member = memberList.get(0);
		member.setEmail("test@nu.nl");
		member.setPlace(Place.EMMELOORD);
		Long id = member.getId();
		memberService.edit(member);

		List<Member> memberListAgain = memberService.listAll();

		// Assert
		assertThat(memberListAgain.size(), Is.is(8));
		Member memberUpdated = memberService.listOne(id);
		assertThat(memberUpdated.getPlace().toString(), Is.is("EMMELOORD"));
		assertThat(memberUpdated.getEmail(), Is.is("test@nu.nl"));
		assertThat(memberUpdated.getId(), Is.is(id));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testCreatedOnValueSetInBackend() throws Exception {

		// Arrange
		testUtilDataSetup.setMembers();
		List<Member> memberList = memberService.listAll();

		// Act
		Member member = memberList.get(0);

		// Assert
		assertThat(simpleDateFormat.format(member.getCreatedonDate()), Is.is(simpleDateFormat.format(new Date())));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testListMembersWithRange() throws Exception {

		testUtilDataSetup.setMembers();

		int range[] = new int[] { 0, 4 };
		List<Member> memberList = memberService.listRange(range);

		assertThat(memberList.size(), Is.is(5));
		assertThat(memberList.get(0).getAchternaam(), Is.is("van Battum"));
		assertThat(memberList.get(4).getAchternaam(), Is.is("Vermeendt"));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testCountOfMembersList() throws Exception {
		testUtilDataSetup.setMembers();
		int count = memberService.count();

		assertEquals(count, 8);

		testUtilDataSetup.setMembers();
		int secondCount = memberService.count();

		assertEquals(secondCount, 16);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testQueryMembersOnID() throws Exception {
		testUtilDataSetup.setMembers();
		List<Member> memberList = memberService.listAll();
		Long idOne = (Long) memberList.get(3).getId();
		Long idTwo = (Long) memberList.get(5).getId();
		Long idArray[] = new Long[] { idOne, idTwo };
		List<Member> memberSelection = memberService.listOutOfQueryId(idArray);
		assertEquals(memberSelection.size(), 2);
		assertEquals(memberSelection.get(0).getId(), idOne);
		assertEquals(memberSelection.get(1).getId(), idTwo);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testQueryMembersByFirstName() throws Exception {
		testUtilDataSetup.setMembers();
		List<Member> memberList = memberService.findMemberByFirstName("dennis");
		assertEquals(memberList.size(), 2);
		assertEquals(memberList.get(0).getVoornaam(), "SuzyDennis");
		;
		assertEquals(memberList.get(1).getVoornaam(), "Dennis");
		List<Member> memberList2 = memberService.findMemberByFirstName("dENnis");
		assertEquals(memberList2.size(), 2);
		assertEquals(memberList2.get(0).getVoornaam(), "SuzyDennis");
		assertEquals(memberList2.get(1).getVoornaam(), "Dennis");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testQueryMembersByFirstAndLastName() throws Exception {
		testUtilDataSetup.setMembers();
		List<Member> memberList = memberService.findMemberByFirstAndLastName("Ellis", "Vermeend");
		assertEquals(memberList.size(), 2);
		assertEquals(memberList.get(0).getVoornaam(), "2Ellise");
		assertEquals(memberList.get(0).getAchternaam(), "Vermeendt");
		assertEquals(memberList.get(1).getVoornaam(), "Ellis");
		assertEquals(memberList.get(1).getAchternaam(), "Vermeend");
		List<Member> memberList2 = memberService.findMemberByFirstAndLastName("Ellis", "vermeent");
		assertEquals(memberList2.size(), 0);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testQueryMembersByLocation() throws Exception {
		testUtilDataSetup.setMembers();
		List<Member> memberList = memberService.listAll();
		Member memberx = memberList.get(0);
		memberx.setPlace(Place.DESTEEG);
		List<Member> memberListLocation = memberService.findMemberByLocation("DE STEEG");
		assertEquals(memberListLocation.size(), 1);
		assertEquals(memberListLocation.get(0).getPlace().getValue(), "DE STEEG");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testQueryMembersByInteresses() throws Exception {
		testUtilDataSetup.setMembers();
		List<Member> memberList0 = memberService.listAll();
		Member x = memberList0.get(7);
		x.setInteresse(new Interesse());
		x.getInteresse().setLesgeven(true);
		memberService.edit(x);
		log.debug("extraneus" + mapper.writeValueAsString(x));

		List<Member> memberList = memberService.findMemberByInteresse("lesgeven");
		assertEquals(memberList.size(), 1);
		assertEquals(memberList.get(0).getInteresse().getLesgeven(), true);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testQueryMembersBySportTypes() throws Exception {
		testUtilDataSetup.setMembers();
		List<Member> memberList0 = memberService.listAll();
		Member x = memberList0.get(5);
		x.getSports().put("Mennen", "Recreatief");
		memberService.edit(x);

		List<Member> memberList = memberService.findMemberBySportType("Mennen");
		assertEquals(memberList.size(), 1);
		assertEquals(memberList.get(0).getSports().get("Mennen"), "Recreatief");
		assertEquals(memberList.get(0).getSports().get("Rodeo"), null);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSetFullMember() throws Exception {
		Member fullmember = new Member();
		fullmember.setAchternaam("Janssen");
		fullmember.setVoornaam("Ronnie");
		fullmember.setGeboortedatum(new Date());
		fullmember.setEmail("ron.janssen@freemail.com");
		fullmember.getInteresse().setLesgeven(true);
		fullmember.getInteresse().setManageexploitatie(true);
		fullmember.getInteresse().setPaardenshow(false);
		fullmember.getInteresse().setStalbeheer(true);
		fullmember.getOtherSports().add(OtherSport.DENKSPORT);
		fullmember.getOtherSports().add(OtherSport.FIETSEN);
		fullmember.getOtherSports().add(OtherSport.GOLF);
		fullmember.setOvermij("ik vind buitenrijden te gek");
		fullmember.setPassword("1234");
		fullmember.setSportLevel(SportLevel.L2);
		fullmember.setPlace(Place.BAAK);
		fullmember.setProfileimage("http://res.cloudinary.com/epona/pictureXYZ.jpg");
		fullmember.setUsername("the Penguin");
		fullmember.setGeslacht(Geslacht.M);
		fullmember.setVervoer(Vervoer.TRAILER);
		fullmember.getSports().put("mennen", "L2");
		fullmember.getSports().put("springen", "L2");
		Message message = new Message();
		message.setMessage("fantastische dag");
		message.setPiclink("http://res.cloudinary.com/epona/pictureXYZ");
		message.setMember(fullmember);
		message.setLineType(LineType.SPORT);
		message.setMessageType(MessageType.MESSAGE);
		fullmember.getMessages().add(message);
		Member vriend = new Member();
		vriend.setUsername("Ted");
		vriend.setVoornaam("Allan");
		vriend.setEmail("vriend@yahoo.com");
		vriend.setGeboortedatum(new Date());
		vriend.setAchternaam("Pichard");
		Member blokkade = new Member();
		blokkade.setUsername("Det");
		blokkade.setVoornaam("Nalla");
		blokkade.setEmail("blokkade@yahoo.com");
		blokkade.setGeboortedatum(new Date());
		blokkade.setAchternaam("Drahcip");
		fullmember.getVrienden().add(blokkade);
		Event event = new Event();
		event.setEventDate(new Date());
		event.setEventName("spring open");
		event.setMessage("open event for all");
		fullmember.getEvents().add(new Event());
		memberService.save(fullmember);
		memberService.save(vriend);
		memberService.save(blokkade);
		Horse horse = new Horse();
		horse.setAfstamming("Vertigo X Regilio");
		horse.setHorseimage1("http://res.cloudinary.com/epona/pictureXYZ");
		horse.setName("Amalia");
		horse.setGeslacht(Geslacht.F);
		fullmember.addOrUpdateHorse(horse);
		memberService.edit(fullmember);
		log.debug("This is the json of full_member");
		log.debug(mapper.writeValueAsString(fullmember));
		List<Member> memberList = memberService.findMemberByFirstAndLastName("Ronnie", "Janssen");
		assertEquals(memberList.size(), 1);
	}
}
