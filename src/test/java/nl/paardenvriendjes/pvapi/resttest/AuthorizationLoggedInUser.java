package nl.paardenvriendjes.pvapi.resttest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.application.HibernateConfiguration;
import nl.paardenvriendjes.pvapi.daoimpl.HorseDaoImpl;
import nl.paardenvriendjes.pvapi.daoimpl.MemberDaoImpl;
import nl.paardenvriendjes.pvapi.daoimpl.MessageDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Message;
import nl.paardenvriendjes.testutil.TestUtilDataSetup;
import nl.paardenvriendjes.testutil.TestUtilHeaderRequestInterceptor;
import nl.paardenvriendjes.testutil.TestUtilLogin;


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
	
	
	@Before
	public void initializeLogin() { 
	
	TestUtilLogin login = new TestUtilLogin();
	String id_token;
	
	try {
		id_token = login.logon("userpv@mailinator.com", "user123");
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new TestUtilHeaderRequestInterceptor(HttpHeaders.AUTHORIZATION, "Bearer " + id_token));
		restTemplate.getRestTemplate().setInterceptors(interceptors);
	} catch (JSONException | URISyntaxException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		e.getMessage();
	}
	}
	
	@After
	// logout all users
	public void logoutUser () { 
		
		restTemplate.getRestTemplate().getInterceptors().clear();
		TestUtilLogin logout = new TestUtilLogin();
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
	public void welcomeTest() {
		String body = restTemplate.getForObject("/welcome", String.class);
		assertEquals(body, "Welcome to PVAPI, no login");
	}

	@Test
	@Transactional
	public void rightsTest() {

		ResponseEntity<String>  body = restTemplate.getForEntity("/adminsafewelcome", String.class);
		assertEquals(body.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	@Transactional
	public void rightsTestUserIdSpecific() {
		

		String body = restTemplate.getForObject("/usersafewelcome", String.class);
		assertEquals(body, "Welcome to PVAPI, you are logged in :)!");
	}
	
	@Test
	@Transactional
	public void testNotAbleToChangeMessageOfSomeBodyElse() {
	testUtilDataSetup.setMembers();
	testUtilDataSetup.runMessagesPost();
	List <Message> messages = messageService.listAll();
	Message messageToBeEdited = messages.get(0);
	log.debug("EditMessage: " + messageToBeEdited);
	messageToBeEdited.setMessage("this is an edited message");
	// make httpentity
	HttpEntity<Message> requestUpdate = new HttpEntity<>(messageToBeEdited);
	// create PUT exchange
	ResponseEntity<Message> response = restTemplate.exchange("/messages/" + messageToBeEdited.getId(), HttpMethod.PUT, requestUpdate, Message.class);
	//assert  not permitted
	assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
	} 
	
	@Test
	@Transactional
	public void testAbleADDandChangeOwnedMessages(){
	
	// PART 1: ADD MESSAGE
	Message messageToBeAdded= new Message();
	messageToBeAdded.setMessage("message being added by controller"); 
	// make httpentity
	HttpEntity<Message> requestAdd = new HttpEntity<>(messageToBeAdded);
	// create POST exchange
	ResponseEntity<Message> response = restTemplate.exchange("/messages/", HttpMethod.POST, requestAdd, Message.class);
	//assert  not permitted
	assertEquals(response.getStatusCode(), HttpStatus.CREATED);
	
	//PART2 EDIT MESSAGE
	List <Message> messages = messageService.listAll();
	Message createdMessage = messages.get(0);
	Long createdId = createdMessage.getId();
	createdMessage.setMessage("edition being sent to Controller");
	
	// make httpentity
	HttpEntity<Message> requestUpdate = new HttpEntity<>(createdMessage);
	
	// create PUT exchange
	ResponseEntity<Message> newresponse = restTemplate.exchange("/messages/" + messageToBeAdded.getId(), HttpMethod.PUT, requestUpdate, Message.class);

	//assert  permitted
	assertEquals(newresponse.getStatusCode(), HttpStatus.OK);
	// Find message again and assert Message to have been changed
	
	
	
	} 
	
	
	
}
