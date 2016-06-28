package nl.paardenvriendjes.pvapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.core.Is;
import org.hibernate.SessionFactory;
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
	public void testMemberAndCASCADEMessageCreationGeneral() throws Exception {

		// Arrange
		testUtil.setMembers();
		List<Member> memberList = memberService.listAll();

		// Act
		Member testMember = memberList.get(0);
		Message message = new Message ();
		message.setMember(testMember);
		message.setMessage("fantastisch weer vandaag");
		message.setPiclink("www.nu.nl");
		//Add a test message to a member
		testMember.getMessages().add(message);
		// message should be persisted cascaded by member 
		memberService.save(testMember);
					
		// Assert
		assertThat(memberList.size(), Is.is(8));
		List<Message> messageList = messageService.listAll();
		assertThat(messageList.size(), Is.is(1));
		assertNotNull(testMember.getMessages());
		assertNotNull(testMember.getMessages().get(0));
		assertThat(testMember.getMessages().get(0).getMessage(), Is.is("fantastisch weer vandaag"));
	}
	
	@Transactional
	@Rollback(true)
	@Test
	public void testMultipleMesages() throws Exception {
	
		
		testUtil.runMessagesPost();
		
		List<Message> messages = messageService.listAll();
		assertThat(messages.size(), Is.is(22));
	}
	
	@Transactional
	@Rollback(true)
	@Test
	public void testCascadeDeleteMesages() throws Exception {
		
		testUtil.setMembers();
		testUtil.runMessagesPost();
		List<Member> memberList = memberService.listAll();
		Member testMember = memberList.get(0);
		// add a message to be sure there is one
		testMember.getMessages().add(new Message());
		memberService.save(testMember);
		List<Message> messages = messageService.listAll();
		assertThat(messages.size(), Is.is(23)); 
		
		Message MessageToBeRemoved =testMember.getMessages().get(0);
		Long toBeRemovedId= MessageToBeRemoved.getId();
		messageService.remove(toBeRemovedId);
		testMember.getMessages().remove(MessageToBeRemoved);
		memberService.save(testMember);
		
		List <Message> messagesListNew =  messageService.listAll();
		System.out.println("findme");
		System.out.println(messagesListNew);
		assertThat(messagesListNew.size(), Is.is(22));
		
	}
}
