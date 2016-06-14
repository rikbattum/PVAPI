package nl.paardenvriendjes.pvapi;

import static org.junit.Assert.assertThat;

import java.io.FileReader;
import java.text.SimpleDateFormat;
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
import nl.paardenvriendjes.pvapi.daoimpl.memberDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.services.MemberDaoService;

@ContextConfiguration(classes = HibernateConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMemberCreation {

	@Autowired
	private memberDaoImpl memberService;

	@Autowired
	private TestUtil testUtil;

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
		List<Member> memberList = memberService.listMembers();

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
		List<Member> memberList = memberService.listMembers();
		Member x = memberList.get(0);

		// Assert

		assertThat(x.getPassword(), Is.is("Superdaddy79"));
		assertThat(x.getAchternaam(), Is.is("van Battum"));
		assertThat(x.getVoornaam(), Is.is("Rik"));
		assertThat(x.getGeboortedatum().toString(), Is.is("Fri Jan 12 00:06:00 CET 1979"));
		assertThat(x.getCreatedon().toString(), Is.is("Thu Jan 22 00:01:00 CET 2015"));
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
		List<Member> memberList = memberService.listMembers();

		// Assert
		assertThat(memberList.size(), Is.is(80));
	}

	@Test
    @Transactional
    @Rollback(true)
	public void testMemberDeletion () throws Exception {

		// Arrange
		testUtil.setMembers();
		List<Member> memberList = memberService.listMembers();
		
		//Assert 
		assertThat(memberList.size(), Is.is(8));
		
		//Act
		Member y = memberList.get(0);
		Long idToBeRemoved = y.getId();
		memberService.removeMember(idToBeRemoved);
		List<Member> memberListAgain = memberService.listMembers();
		
		//Assert 
		assertThat(memberListAgain.size(), Is.is(7));
		
	}

}
