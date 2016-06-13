package nl.paardenvriendjes.pvapi;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.hibernate.configuration.HibernateConfiguration;
import nl.paardenvriendjes.pvapi.daoimpl.memberDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Member;


@ContextConfiguration(classes=HibernateConfiguration.class)

public class TestMessageCreator extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private memberDaoImpl memberService;
	@Autowired
	private TestUtil testUtil;
	
	@Before
	public void initialize() {

	}

	@After
	public void after() throws Throwable {
		
	}
	
	@Transactional
    @Rollback(true)
	@Test 
	public void testDataMemberCreationCorrectDBTable2() throws Exception {
		
		testUtil.setMembers();
	
		List<Member> memberList = memberService.listMembers();
		Member x = memberList.get(0);
		
		assertThat (memberList.size(), Is.is(8));
		
	}

	
}
