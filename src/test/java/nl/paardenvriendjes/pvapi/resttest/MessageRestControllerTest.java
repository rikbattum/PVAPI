package nl.paardenvriendjes.pvapi.resttest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.http.HttpHeaders;
import org.json.JSONException;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.annotation.Rollback;

import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.domain.Message;
import nl.paardenvriendjes.testutil.TestUtilHeaderRequestInterceptor;

public class MessageRestControllerTest {

	
	
	
	
	
	
//	
//
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testNotAbleToChangeMessageOfSomeBodyElse() {
//		testUtilDataSetup.setMembers();
//		testUtilDataSetup.runMessagesPost();
//		List<Message> messages = messageService.listAll();
//		Message messageToBeEdited = messages.get(0);
//		log.debug("EditMessage: " + messageToBeEdited);
//		messageToBeEdited.setMessage("this is an edited message");
//		// make httpentity
//		HttpEntity<Message> requestUpdate = new HttpEntity<>(messageToBeEdited);
//		// create PUT exchange
//		ResponseEntity<Message> response = restTemplate.exchange("/messages/" + messageToBeEdited.getId(),
//				HttpMethod.PUT, requestUpdate, Message.class);
//		// assert not permitted
//		assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
//	}
//
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testAbleToAddAndChangeOwnMessage() { 
//		testUtilDataSetup.setMembers();
//		testUtilDataSetup.runMessagesPost();
//		
//		Member validMember = (Member) memberService.findMemberByLastName("Battum").get(0);
//		Message messageToBeAdded = new Message();
//		messageToBeAdded.setMessage("message being added by controller");
//		messageToBeAdded.setMember(validMember);
//		// make httpentity
//		HttpEntity<Message> request = new HttpEntity<>(messageToBeAdded);
//		//login
//		try {
//			String id_token = auth0.login("userpv@mailinator.com", "user123");
//			List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
//			interceptors.add(new TestUtilHeaderRequestInterceptor(HttpHeaders.AUTHORIZATION, "Bearer " + id_token));
//			restTemplate.getRestTemplate().setInterceptors(interceptors);
//		} catch (JSONException | URISyntaxException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	
	
	
	
	
}
