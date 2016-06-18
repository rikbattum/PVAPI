package nl.paardenvriendjes.pvapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.core.Is;
import org.hibernate.validator.internal.util.logging.Messages;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.hibernate.configuration.HibernateConfiguration;
import nl.paardenvriendjes.pvapi.daoimpl.MemberDaoImpl;
import nl.paardenvriendjes.pvapi.daoimpl.MessageDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.domain.Message;

@ContextConfiguration(classes = HibernateConfiguration.class)

public class TestMessageCreator extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private MessageDaoImpl messageService;
	@Autowired
	private MemberDaoImpl memberService;
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
	public void testMemberAndMessageCreationGeneral() throws Exception {

		// Arrange
		testUtil.setMembers();
		List<Member> memberList = memberService.listMembers();

		// Act
		Member testMember = memberList.get(0);
		Message message = new Message ();
		message.setMember(testMember);
		message.setMessage("fantastisch weer vandaag");
		message.setPiclink("www.nu.nl");
		messageService.saveMessage(message);
					
		// Assert
		assertThat(memberList.size(), Is.is(8));
		List<Messages> messageList = messageService.listMessages();
		assertThat(messageList.size(), Is.is(1));
		assertNull(testMember.getMessages());
		assertNotNull(testMember.getMessages());
	}

}
