package nl.paardenvriendjes.pvapi.daotest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import nl.paardenvriendjes.pvapi.daoimpl.MemberDaoImpl;
import nl.paardenvriendjes.pvapi.daoimpl.MessageDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.domain.Message;

@ContextConfiguration(classes = HibernateConfiguration.class)

public class MessageDaoImplTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private MessageDaoImpl messageService;
	@Autowired
	private MemberDaoImpl memberService;
	@Autowired
	private TestUtil testUtil;

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

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
		Message message = new Message();
		message.setMessage("fantastisch weer vandaag");
		message.setPiclink("www.nu.nl");
		message.setMember(testMember);
		message.setInsertDate();
		// Add a test message to a member and set member to message
		testMember.addOrUpdateMessage(message);
		memberService.save(testMember);

		// Assert
		assertThat(memberList.size(), Is.is(8));
		List<Message> messageList = messageService.listAll();
		assertThat(messageList.size(), Is.is(1));
		assertNotNull(testMember.getMessages());
		assertNotNull(testMember.getMessages().get(0));
		assertThat(testMember.getMessages().get(0).getMessage(), Is.is("fantastisch weer vandaag"));
		assertThat(simpleDateFormat.format(testMember.getMessages().get(0).getInsertDate()),  Is.is(simpleDateFormat.format(new Date())));
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
	public void testCascadeSaveMesages() throws Exception {

		testUtil.setMembers();
		testUtil.runMessagesPost();
		List<Member> memberList = memberService.listAll();
		Member testMember = memberList.get(0);
		// // add an extra message
		testMember.addOrUpdateMessage(new Message());
		memberService.save(testMember);
		List<Message> messages = messageService.listAll();
		assertThat(messages.size(), Is.is(23));
	}

	@Transactional
	@Rollback(true)
	@Test
	public void testCascadeDeleteMesages() throws Exception {
		testUtil.setMembers();
		List<Member> memberList = memberService.listAll();
		Member testMember = memberList.get(0);
		// Add new message to be sure there is one
		testMember.addOrUpdateMessage(new Message());
		memberService.save(testMember);
		// assert for added message
		List<Message> messagesList = messageService.listAll();
		assertThat(messagesList.size(), Is.is(1));
		// remove message
		Message MessageToBeRemoved = testMember.getMessages().get(0);
		testMember.removeMessage(MessageToBeRemoved);
		Long toBeRemovedId = MessageToBeRemoved.getId();	
		messageService.remove(toBeRemovedId);
		memberService.save(testMember);
		List<Message> messagesListNew = messageService.listAll();
		assertThat(messagesListNew.size(), Is.is(0));
	}

	@Transactional
	@Rollback(true)
	@Test
	public void testCascadeEditMesages() throws Exception {
		testUtil.setMembers();
		List<Member> memberList = memberService.listAll();
		Member testMember = memberList.get(0);

		// add a extra message
		Message message = new Message();
		message.setMember(testMember);
		message.setMessage("fantastisch weer vandaag");
		message.setInsertDate();
		testMember.addOrUpdateMessage(message);
		memberService.save(testMember);		
		Message messageToChanged = testMember.getMessages().get(0);
		Long messageId = messageToChanged.getId();

		// assert first message text
		assertThat(messageToChanged.getMessage(), Is.is("fantastisch weer vandaag"));
		assertThat (simpleDateFormat.format(messageToChanged.getInsertDate()), Is.is(simpleDateFormat.format(new Date())));
		// assert second message test
		messageToChanged.setMessage("vandaag springen afgelast ivm sneeuw");
		memberService.save(testMember);

		Message updatedMessage = messageService.listOne(messageId);
		assertThat(updatedMessage.getMessage(), Is.is("vandaag springen afgelast ivm sneeuw"));
		assertThat(updatedMessage.getId(), Is.is(239L));
		assertNotNull(updatedMessage.getInsertDate());
		assertThat(simpleDateFormat.format(updatedMessage.getInsertDate()), Is.is(simpleDateFormat.format(new Date())));
	}
}
