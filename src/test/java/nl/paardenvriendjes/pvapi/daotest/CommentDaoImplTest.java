package nl.paardenvriendjes.pvapi.daotest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.abstracttest.AbstractTest;
import nl.paardenvriendjes.pvapi.dao.MemberDaoImpl;
import nl.paardenvriendjes.pvapi.dao.MessageCommentDaoImpl;
import nl.paardenvriendjes.pvapi.dao.MessageDaoImpl;
import nl.paardenvriendjes.pvapi.data.Member;
import nl.paardenvriendjes.pvapi.data.Message;
import nl.paardenvriendjes.pvapi.data.MessageComment;
import nl.paardenvriendjes.testutil.TestUtilDataSetup;

@DirtiesContext
public class CommentDaoImplTest extends AbstractTest {

	@Autowired
	private MessageDaoImpl messageService;
	@Autowired
	private MemberDaoImpl memberService;
	@Autowired
	private MessageCommentDaoImpl commentService;
	@Autowired
	private TestUtilDataSetup testUtilDataSetup;

	@After
	public void after() throws Throwable {

	}

	@Test
	@Transactional
	@Rollback(true)
	@WithMockUser(username = "userpv@mailinator.com", authorities={"USER"})
	public void testCommentCascadeSave() {

		// Arrange
		testUtilDataSetup.setMembers();
		testUtilDataSetup.runMessagesPost();
		Message messageOne = messageService.listAll().get(0);
		Member memberOne = messageOne.getMember();

		// Act
		MessageComment commentOne = new MessageComment();
		commentOne.setComment("leuke update!");
		commentOne.setMember(memberOne);
		commentOne.setMessage(messageOne);
		messageOne.getCommentlist().add(commentOne);
		memberService.edit(memberOne);
		

		// Assert
		List<MessageComment> commentList = commentService.listAll();
		Message messageWithComment = messageService.listAll().get(0);
		assertThat(commentList.size(), Is.is(1));
		assertThat(commentList.get(0).getComment(), Is.is("leuke update!"));
		assertEquals(messageWithComment.getCommentlist().size(), 1);
		assertThat(messageWithComment.getCommentlist().get(0).getComment(),  Is.is("leuke update!"));
		
	}

	@Test
	@Transactional
	@Rollback(true)
	@WithMockUser(username = "userpv@mailinator.com", authorities={"USER"})
	public void testCommentCascadeEdit() {

		// Arrange
		testUtilDataSetup.setMembers();
		testUtilDataSetup.runMessagesPost();
		Message messageOne = messageService.listAll().get(0);
		Member memberOne = messageOne.getMember();
		MessageComment commentOne = new MessageComment();
		commentOne.setComment("leuke update!");
		commentOne.setMember(memberOne);
		commentOne.setInsertDate();
		commentOne.setMessage(messageOne);
		messageOne.getCommentlist().add(commentOne);
		memberService.save(memberOne);

		Message messageTwo = messageService.listAll().get(0);
		assertThat(messageTwo.getCommentlist().get(0).getComment(), Is.is("leuke update!"));
		messageTwo.getCommentlist().get(0).setComment("Update");
		memberService.save(memberOne);
		assertThat(messageTwo.getCommentlist().get(0).getComment(), Is.is("Update"));
	}

	@Test
	@Transactional
	@Rollback(true)
	@WithMockUser(username = "userpv@mailinator.com", authorities={"USER"})
	public void testCommentCascadeDelete() {

		// Arrange
		testUtilDataSetup.setMembers();
		testUtilDataSetup.runMessagesPost();
		Message messageOne = messageService.listAll().get(0);
		Member memberOne = messageOne.getMember();

		// Act
		MessageComment commentOne = new MessageComment();
		commentOne.setComment("leuke update!");
		commentOne.setMember(memberOne);
		commentOne.setInsertDate();
		commentOne.setMessage(messageOne);
		messageOne.getCommentlist().add(commentOne);
		memberService.edit(memberOne);
		List<MessageComment> commentList = commentService.listAll();
		assertThat(commentList.size(), Is.is(1));

		int messageCounter = memberOne.getMessages().size();
		assertEquals(memberOne.getMessages().get(0).getCommentlist().size(), 1);
		
		// Assert
		memberOne.getMessages().get(0).getCommentlist().remove(0);
		memberService.edit(memberOne);

		List<MessageComment> commentList2 = commentService.listAll();
		assertThat(commentList2.size(), Is.is(0));
		Member memberTwo = memberService.listOne(memberOne.getId());
		assertThat(memberTwo.getMessages().size(), Is.is(messageCounter));
		assertEquals(memberTwo.getMessages().get(0).getCommentlist().size(), 0);
		
		
	}

}