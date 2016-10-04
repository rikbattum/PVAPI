package nl.paardenvriendjes.pvapi.daotest;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.abstracttest.AbstractTest;
import nl.paardenvriendjes.pvapi.daoimpl.CommentDaoImpl;
import nl.paardenvriendjes.pvapi.daoimpl.MemberDaoImpl;
import nl.paardenvriendjes.pvapi.daoimpl.MessageDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Comment;
import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.domain.Message;
import nl.paardenvriendjes.testutil.TestUtilDataSetup;

@DirtiesContext
public class CommentDaoImplTest extends AbstractTest{
	
	@Autowired
	private MessageDaoImpl messageService;
	@Autowired
	private MemberDaoImpl memberService;
	@Autowired
	private CommentDaoImpl commentService;
	@Autowired
	private TestUtilDataSetup testUtilDataSetup;

	@After
	public void after() throws Throwable {

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testCommentCascadeSave() {

		// Arrange
		testUtilDataSetup.runMessagesPost();
		Message messageOne = messageService.listAll().get(0);
		Member memberOne = messageOne.getMember();

		// Act
		Comment commentOne = new Comment();
		commentOne.setComment("leuke update!");
		commentOne.setMember(memberOne);
		commentOne.setInsertDate();
		commentOne.setMessage(messageOne);
		messageOne.getCommentlist().add(commentOne);
		memberService.save(memberOne);

		// Assert
		List<Comment> commentList = commentService.listAll();
		assertThat(commentList.size(), Is.is(1));
		assertThat(commentList.get(0).getComment(), Is.is("leuke update!"));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testCommentCascadeEdit() {

		// Arrange
		testUtilDataSetup.runMessagesPost();
		Message messageOne = messageService.listAll().get(0);
		Member memberOne = messageOne.getMember();
		Comment commentOne = new Comment();
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
	public void testCommentCascadeDelete() {

		// Arrange
				testUtilDataSetup.runMessagesPost();
				Message messageOne = messageService.listAll().get(0);
				Member memberOne = messageOne.getMember();
	
		// Act
				Comment commentOne = new Comment();
				commentOne.setComment("leuke update!");
				commentOne.setMember(memberOne);
				commentOne.setInsertDate();
				commentOne.setMessage(messageOne);
				messageOne.getCommentlist().add(commentOne);
				memberService.save(memberOne);
				List<Comment> commentList = commentService.listAll();
				assertThat(commentList.size(), Is.is(1));
				
				int messageCounter = memberOne.getMessages().size();
				
		// Assert
				memberOne.getMessages().remove(messageOne);
				messageService.remove(messageOne.getId());
				memberService.edit(memberOne);
				
				List<Comment> commentList2 = commentService.listAll();
				assertThat(commentList2.size(), Is.is(0));
				Member memberTwo = memberService.listOne(memberOne.getId());
				assertThat(memberTwo.getMessages().size(), Is.is(messageCounter-1));
				
}
}