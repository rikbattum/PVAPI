package nl.paardenvriendjes.pvapi.authenticationandauthorization;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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

import nl.paardenvriendjes.application.HibernateConfiguration;
import nl.paardenvriendjes.testutil.Auth0Util;
import nl.paardenvriendjes.testutil.TestUtilHeaderRequestInterceptor;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = HibernateConfiguration.class)

// Integration Test with Auth0 Only run now and then
@Ignore
public class Auth0AuthorizationLoggedInUserTest{

	static Logger log = Logger.getLogger(Auth0AuthorizationLoggedInUserTest.class.getName());

	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	SessionFactory sessionFactory;
	
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
	public void welcomeTestNoLogin() {
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
	public void rightsTestLoggedIn() {
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

		ResponseEntity<String> body = restTemplate.getForEntity("/authenticateduserrole", String.class);
		assertEquals(body.getStatusCode(), HttpStatus.OK);
		assertEquals(body.getBody() , "Welcome to PVAPI, you are logged in and have the correct Role:)!");
	
	}
}
