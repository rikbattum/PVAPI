package nl.paardenvriendjes.pvapi.authorization;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import nl.paardenvriendjes.application.HibernateConfiguration;
import nl.paardenvriendjes.pvapi.daoimpl.MemberDaoImpl;
import nl.paardenvriendjes.pvapi.daoimpl.MessageDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.domain.Message;
import nl.paardenvriendjes.testutil.Auth0Util;
import nl.paardenvriendjes.testutil.TestUtilDataSetup;
import nl.paardenvriendjes.testutil.TestUtilHeaderRequestInterceptor;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = HibernateConfiguration.class)

public class AuthorizationLoggedInUser {

	static Logger log = Logger.getLogger(AuthorizationLoggedInUser.class.getName());

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private MemberDaoImpl memberService;

	@Autowired
	private TestUtilDataSetup testUtilDataSetup;

	@Autowired
	private MessageDaoImpl messageService;

	@Autowired
	SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	private Auth0Util auth0= new Auth0Util();

	@Before
	public void initializeLogin() {
	}

	@After
	// logout all users
	public void logoutUser() {

		restTemplate.getRestTemplate().getInterceptors().clear();
		Auth0Util logout = new Auth0Util();
		try {
			logout.logout();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	@Transactional
	@Rollback(true)
	public void welcomeTest() {
		String id_token;

		try {
			id_token = auth0.login("userpv@mailinator.com", "user123");
			List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
			interceptors.add(new TestUtilHeaderRequestInterceptor(HttpHeaders.AUTHORIZATION, "Bearer " + id_token));
			restTemplate.getRestTemplate().setInterceptors(interceptors);
		} catch (JSONException | URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.getMessage();
		}
		String body = restTemplate.getForObject("/welcome", String.class);
		assertEquals(body, "Welcome to PVAPI, no login");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void rightsTest() {
		String id_token;

		try {
			id_token = auth0.login("userpv@mailinator.com", "user123");
			List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
			interceptors.add(new TestUtilHeaderRequestInterceptor(HttpHeaders.AUTHORIZATION, "Bearer " + id_token));
			restTemplate.getRestTemplate().setInterceptors(interceptors);
		} catch (JSONException | URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.getMessage();
		}

		ResponseEntity<String> body = restTemplate.getForEntity("/adminsafewelcome", String.class);
		assertEquals(body.getStatusCode(), HttpStatus.FORBIDDEN);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void rightsTestUserIdSpecific() {
		String id_token;

		try {
			id_token = auth0.login("userpv@mailinator.com", "user123");
			List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
			interceptors.add(new TestUtilHeaderRequestInterceptor(HttpHeaders.AUTHORIZATION, "Bearer " + id_token));
			restTemplate.getRestTemplate().setInterceptors(interceptors);
		} catch (JSONException | URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.getMessage();
		}

		String body = restTemplate.getForObject("/usersafewelcome", String.class);
		assertEquals(body, "Welcome to PVAPI, you are logged in :)!");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testNotAbleToChangeMessageOfSomeBodyElse() {
		testUtilDataSetup.setMembers();
		testUtilDataSetup.runMessagesPost();
		List<Message> messages = messageService.listAll();
		Message messageToBeEdited = messages.get(0);
		log.debug("EditMessage: " + messageToBeEdited);
		messageToBeEdited.setMessage("this is an edited message");
		// make httpentity
		HttpEntity<Message> requestUpdate = new HttpEntity<>(messageToBeEdited);
		// create PUT exchange
		ResponseEntity<Message> response = restTemplate.exchange("/messages/" + messageToBeEdited.getId(),
				HttpMethod.PUT, requestUpdate, Message.class);
		// assert not permitted
		assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testAbleToAddAndChangeOwnMessage() { 
		testUtilDataSetup.setMembers();
		testUtilDataSetup.runMessagesPost();
		
		Member validMember = (Member) memberService.findMemberByLastName("Battum").get(0);
		Message messageToBeAdded = new Message();
		messageToBeAdded.setMessage("message being added by controller");
		messageToBeAdded.setMember(validMember);
		// make httpentity
		HttpEntity<Message> request = new HttpEntity<>(messageToBeAdded);
		//login
		try {
			String id_token = auth0.login("userpv@mailinator.com", "user123");
			List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
			interceptors.add(new TestUtilHeaderRequestInterceptor(HttpHeaders.AUTHORIZATION, "Bearer " + id_token));
			restTemplate.getRestTemplate().setInterceptors(interceptors);
		} catch (JSONException | URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		// PART 1 ADD MESSAGE
		
		// create POST exchange
		ResponseEntity<Message> response = restTemplate.exchange("/messages/", HttpMethod.POST, request,
				Message.class);
		
		// assert permitted
		assertEquals(response.getStatusCode(), HttpStatus.CREATED);

		// PART 2 EDIT MESSAGE
//		List<Message> messages = messageService.listAll();
//		Message createdMessage = messages.get(0);
//		Long createdId = createdMessage.getId();
//		createdMessage.setMessage("edition being sent to Controller");

//		// make httpentity
//		HttpEntity<Message> requestUpdate = new HttpEntity<>(createdMessage);

		// create PUT exchange
//		ResponseEntity<Message> newresponse = restTemplate.exchange("/messages/" + createdId, HttpMethod.PUT,
//				requestUpdate, Message.class);

		// assert permitted
		// assertEquals(newresponse.getStatusCode(), HttpStatus.OK);
		// Find message again and assert Message to have been changed
//	}
//		finally {
//			restTemplate.delete("/members/1") ;
//		}
//	}	
	}
}
