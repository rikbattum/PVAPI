package nl.paardenvriendjes.pvapi.daotest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.enumerations.LineType;
import nl.paardenvriendjes.pvapi.abstracttest.AbstractTest;
import nl.paardenvriendjes.pvapi.daoimpl.MemberDaoImpl;
import nl.paardenvriendjes.pvapi.daoimpl.MessageDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.domain.Message;

public class MessageDaoImplTest extends AbstractTest {

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
		assertThat(updatedMessage.getId(), Is.is(317L));
		assertNotNull(updatedMessage.getInsertDate());
		assertThat(simpleDateFormat.format(updatedMessage.getInsertDate()), Is.is(simpleDateFormat.format(new Date())));
	}
	
	@Transactional
	@Rollback(true)
	@Test
	public void testQuerySportMessages() throws Exception {

		testUtil.setMembers();
		testUtil.runMessagesPost();
		Message message1 = messageService.listAll().get(0);
		Message message2= messageService.listAll().get(1);
		Message message3= messageService.listAll().get(2);
		Message message4= messageService.listAll().get(3);
		message1.setLineType(LineType.SPORT);
		message2.setLineType(LineType.SPORT);
		message3.setLineType(LineType.SPORT);
		//Does not fit
		message4.setLineType(LineType.GENERAL);
		message1.InsertDateTest(getTimeLineLapseTEST(15));
		message2.InsertDateTest(getTimeLineLapseTEST(3));
		//does not fit
		message3.InsertDateTest(getTimeLineLapseTEST(26));
		message4.InsertDateTest(getTimeLineLapseTEST(3));
		// no save or edit, because of auto date set in DAO implementation
		List <Message> messages = messageService.listAllMessagesSport(0, 400);
		assertThat(messages.size(), Is.is(2));
	}
	
	@Transactional
	@Rollback(true)
	@Test
	public void testQueryFriendsMessages() throws Exception {

		testUtil.setMembers();
		testUtil.runMessagesPost();
		Message message1 = messageService.listAll().get(0);
		Message message2= messageService.listAll().get(1);
		Message message3= messageService.listAll().get(2);
		Message message4= messageService.listAll().get(3);
		Message message5= messageService.listAll().get(4);
		Member member1 = memberService.listAll().get(0); 
		Member member2 = memberService.listAll().get(1);
		Member member3 = memberService.listAll().get(2);
		Member member4= memberService.listAll().get(3);
		member1.getVrienden().add(member2);
		member1.getVrienden().add(member3);
		//	Don't bother set auto date or testing time criterium here;	
		//Does fit
		message1.setMember(member2);
		message3.setMember(member2);
		//don't fit
		message2.setMember(member3);
		message4.setMember(member4);
		message5.setMember(member1);
		messageService.save(message1);
		messageService.save(message2);
		messageService.save(message3);
		messageService.save(message4);
		messageService.save(message5);
		// Save Member
		memberService.save(member1);
		memberService.save(member2);
		memberService.save(member3);
		memberService.save(member4);
		// Get all friends messages of Member1 
		List <Message> messages = messageService.listAllMessagesFriends(0, 400, member1);
		assertThat(messages.size(), Is.is(3));
		// Check all member are indeed in friends list 
		assertThat (messages.get(0).getMember(), Is.is(member2));
		assertThat (messages.get(1).getMember(), Is.is(member3));
		assertThat (messages.get(2).getMember(), Is.is(member2));
	}
	
	// Utility functions
	
	public Date getTimeLineLapseTEST (int amountOfDays) { 
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -amountOfDays);
		Date dateBeforeXDays = cal.getTime();
		return dateBeforeXDays;
	}
}
