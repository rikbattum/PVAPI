package nl.paardenvriendjes.pvapi.authorization;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.application.HibernateConfiguration;
import nl.paardenvriendjes.testutil.TestUtilHeaderRequestInterceptor;
import nl.paardenvriendjes.testutil.TestUtilLogin;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = HibernateConfiguration.class)

public class AuthorizationLoggedInAdmin {

	@Autowired
    private TestRestTemplate restTemplate;
	
	@Before
	public void initializeLogin() { 
	
	TestUtilLogin login = new TestUtilLogin();
	String id_token;
	
	try {
		id_token = login.logon("rikbattum@hotmail.com", "admin");
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
	@Rollback(true)
	public void welcomeTest() {
		String body = restTemplate.getForObject("/welcome", String.class);
		assertEquals(body, "Welcome to PVAPI, no login");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void rightsTest() {

		String body = restTemplate.getForObject("/adminsafewelcome", String.class);
		assertEquals(body, "Welcome to PVAPI, you are logged in :)!");
	}
	
	@Test
	@Transactional	
	@Rollback(true)
	public void rightsTestUserIdSpecific() {

		ResponseEntity<String> body = restTemplate.getForEntity("/usersafewelcome", String.class);
		assertEquals(body.getStatusCode(), HttpStatus.FORBIDDEN);
	}
}


