package nl.paardenvriendjes.pvapi.daotest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hamcrest.core.Is;
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

@ContextConfiguration(classes = HibernateConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MemberDaoImplTest {

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
	public void testMemberCreationExactNumberOfEntries() throws Exception {

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
		assertThat(simpleDateFormat.format(x.getCreatedonDate()), Is.is(simpleDateFormat.format(new Date())));
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
		assertThat(simpleDateFormat.format(member.getCreatedonDate()), Is.is(simpleDateFormat.format(new Date())));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testListMembersWithRange() throws Exception {
		
	testUtil.setMembers();
	
	int range [] = new int [] {0,4};
	List<Member> memberList = memberService.listRange(range); 
	
	assertThat(memberList.size(), Is.is(5));
	assertThat(memberList.get(0).getAchternaam(), Is.is("van Battum"));
	assertThat(memberList.get(4).getAchternaam(), Is.is("de Boer"));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCountOfMembersList() throws Exception {	
	testUtil.setMembers();
	int count = memberService.count();
	
	assertEquals(count, 8);
	
	testUtil.setMembers();
	int secondCount = memberService.count();
	
	assertEquals(secondCount, 16);
	}	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testQueryMembersOnID() throws Exception {	
	testUtil.setMembers();
	List<Member> memberList = memberService.listAll();
	Long idOne = (Long) memberList.get(3).getId();
	Long idTwo = (Long) memberList.get(5).getId();
	Long idArray [] = new Long [] {idOne, idTwo};
	List <Member> memberSelection = memberService.listOutOfQueryId(idArray);
	assertEquals(memberSelection.size(), 2);
	assertEquals(memberSelection.get(0).getId(), idOne);
	assertEquals(memberSelection.get(1).getId(), idTwo);
	}	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testQueryMembersByFirstName() throws Exception {	
	testUtil.setMembers();
	List<Member> memberList = memberService.findMemberByFirstName("dennis");
	assertEquals(memberList.size(), 1);
	assertEquals(memberList.get(0).getVoornaam(), "Dennis");
	List<Member> memberList2 = memberService.findMemberByFirstName("dENnis");
	assertEquals(memberList.size(), 1);
	assertEquals(memberList.get(0).getVoornaam(), "Dennis");;
	}	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testQueryMembersByFirstAndLastName() throws Exception {	
	testUtil.setMembers();
	List<Member> memberList = memberService.findMemberByFirstAndLastName("Ellise", "vermeendt");
	assertEquals(memberList.size(), 1);
	assertEquals(memberList.get(0).getVoornaam(), "Ellis");
	assertEquals(memberList.get(0).getVoornaam(), "Vermeend");
	List<Member> memberList2 = memberService.findMemberByFirstAndLastName("Ellis", "vermeent");
	assertEquals(memberList.size(), 0);
	}	
	
	
	
	
}
