package nl.paardenvriendjes.pvapi;

import static org.junit.Assert.assertThat;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hamcrest.core.Is;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.hibernate.configuration.HibernateConfiguration;
import nl.paardenvriendjes.pvapi.daoimpl.MemberDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.services.AbstractDaoService;

@ContextConfiguration(classes = HibernateConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMemberCreation {

	@Autowired
	private MemberDaoImpl memberService;

	@Autowired
	private TestUtil testUtil;

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public void initialize() {

	}

	@After
	public void after() throws Throwable {

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testMemberCreationObjectEntries() throws Exception {

		// Arrange
		testUtil.setMembers();

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
		testUtil.setMembers();

		// Act
		List<Member> memberList = memberService.listAll();
		Member x = memberList.get(0);

		// Assert

		assertThat(x.getPassword(), Is.is("Superdaddy79"));
		assertThat(x.getAchternaam(), Is.is("van Battum"));
		assertThat(x.getVoornaam(), Is.is("Rik"));
		assertThat(x.getGeboortedatum().toString(), Is.is("Fri Jan 12 00:06:00 CET 1979"));
		assertThat(simpleDateFormat.format(x.getCreatedon()), Is.is(simpleDateFormat.format(new Date())));
		assertThat(x.getPlaatsnaam(), Is.is("Hilversum"));
		assertThat(x.getProfileimage(), Is.is("www.image.com/rik"));
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
		testUtil.setMembers();
		testUtil.setMembers();
		testUtil.setMembers();
		testUtil.setMembers();
		testUtil.setMembers();
		testUtil.setMembers();
		testUtil.setMembers();
		testUtil.setMembers();
		testUtil.setMembers();
		testUtil.setMembers();

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
		testUtil.setMembers();
		List<Member> memberList = memberService.listAll();

		// Assert
		assertThat(memberList.size(), Is.is(8));

		// Act
		Member member = memberList.get(0);
		Long idToBeRemoved = member.getId();
		memberService.remove(idToBeRemoved);
		List<Member> memberListAgain = memberService.listAll();

		// Assert
		assertThat(memberListAgain.size(), Is.is(7));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testMemberUpdate() throws Exception {

		// Arrange
		testUtil.setMembers();
		List<Member> memberList = memberService.listAll();

		// Assert
		assertThat(memberList.size(), Is.is(8));

		// Act
		Member member = memberList.get(0);
		member.setEmail("test@nu.nl");
		member.setPlaatsnaam("emmeloord");
		Long id = member.getId();
		memberService.edit(member);

		List<Member> memberListAgain = memberService.listAll();

		// Assert
		assertThat(memberListAgain.size(), Is.is(8));
		Member memberUpdated = memberService.listOne(id);
		assertThat(memberUpdated.getPlaatsnaam(), Is.is("emmeloord"));
		assertThat(memberUpdated.getEmail(), Is.is("test@nu.nl"));
		assertThat(memberUpdated.getId(), Is.is(id));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testCreatedOnValueSetInBackend() throws Exception {

		// Arrange
		testUtil.setMembers();
		List<Member> memberList = memberService.listAll();

		// Act
		Member member = memberList.get(0);
		
		// Assert
		assertThat(simpleDateFormat.format(member.getCreatedon()), Is.is(simpleDateFormat.format(new Date())));

	}
}
