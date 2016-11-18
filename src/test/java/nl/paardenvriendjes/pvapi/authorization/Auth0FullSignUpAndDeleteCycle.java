package nl.paardenvriendjes.pvapi.authorization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import nl.paardenvriendjes.application.HibernateConfiguration;
import nl.paardenvriendjes.pvapi.daoimpl.MemberDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.testutil.Auth0Util;
import nl.paardenvriendjes.testutil.TestUtilHeaderRequestInterceptor;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = HibernateConfiguration.class)
// ingore untill delete issue is fixed

public class Auth0FullSignUpAndDeleteCycle {

	@Autowired
    private TestRestTemplate restTemplate;
	@Autowired
	private MemberDaoImpl memberservicefullcycle; 
	
	static Logger log = Logger.getLogger(AuthorizationLoggedInUser.class.getName());
	
	

	@Test
	@Transactional
	@Rollback(true)
	public void A_testSignUpOfMember()	{
		
		// Part 0 add member and get ID for test
		Member member = new Member();
		member.setEmail("userpvjunit@mailinator.com");
		member.setPassword("3213hjxcS");
		HttpEntity<Member> requestAdd = new HttpEntity<>(member);
		ResponseEntity<Member> response = restTemplate.exchange("/members/signup", HttpMethod.POST, requestAdd,
				Member.class);
		assertEquals(response.getStatusCode(), HttpStatus.CREATED);
	} 
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void B_testLoginOfMember() throws ClientProtocolException, JSONException, URISyntaxException, IOException	{
			Auth0Util login = new Auth0Util();
			String id_token = login.login("userpvjunit@mailinator.com", "3213hjxcS");
			List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
			interceptors.add(new TestUtilHeaderRequestInterceptor(HttpHeaders.AUTHORIZATION, "Bearer " + id_token));
			restTemplate.getRestTemplate().setInterceptors(interceptors);	
			
	assertTrue(StringUtils.countOccurrencesOf(id_token, ".") == 2);		
	}		
	

	@Test
	@Transactional
	@Rollback(true)
	public void C_testDeletionOfMember() throws ClientProtocolException, JSONException, URISyntaxException, IOException{
		//create member again
		Member member = new Member();
		member.setEmail("userpvjunit@mailinator.com");
		member.setPassword("3213hjxcS");
		HttpEntity<Member> requestAdd = new HttpEntity<>(member);
		ResponseEntity<Member> response = restTemplate.exchange("/members/", HttpMethod.PUT, requestAdd,
				Member.class);
		assertEquals(response.getStatusCode(), 200);		
		
		//login
		Auth0Util login = new Auth0Util();
		String id_token = login.login("userpvjunit@mailinator.com", "3213hjxcS");
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new TestUtilHeaderRequestInterceptor(HttpHeaders.AUTHORIZATION, "Bearer " + id_token));
		restTemplate.getRestTemplate().setInterceptors(interceptors);	
		//delete
		restTemplate.delete("/members/1");
	}
}
