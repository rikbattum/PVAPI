package nl.paardenvriendjes.pvapi.authorization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cache;
import javax.persistence.EntityManager;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

	static Logger log = Logger.getLogger(AuthorizationLoggedInUser.class.getName());

	// only run this integration test for auth0 integration
	@Ignore
	@Test
	@Transactional
	@Rollback(true)
	public void A_testSignUpSignInAndDeletionOfMemberAuth0()
			throws ClientProtocolException, JSONException, URISyntaxException, IOException {

		// Part 0 Signup
		Member member = new Member();
		member.setEmail("userpvjunit@mailinator.com");
		member.setVoornaam("Peddy");
		member.setAchternaam("Horsy");
		member.setUsername("PeddyHorsey");
		member.setGeboortedatum(new Date(12 - 6 - 1979));
		member.setPassword("3213hjxcS");
		HttpEntity<Member> requestAdd = new HttpEntity<>(member);
		ResponseEntity<Member> response = restTemplate.exchange("/members/signup", HttpMethod.POST, requestAdd,
				Member.class);
		assertEquals("Signup of Member at Auth0", response.getStatusCode(), HttpStatus.CREATED);

		// Part 1 Login
		Auth0Util login = new Auth0Util();
		String id_token = login.login("userpvjunit@mailinator.com", "3213hjxcS");
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new TestUtilHeaderRequestInterceptor(HttpHeaders.AUTHORIZATION, "Bearer " + id_token));
		restTemplate.getRestTemplate().setInterceptors(interceptors);
		assertTrue("Login at Auth0", StringUtils.countOccurrencesOf(id_token, ".") == 2);

//		// Part 2 Get Member
//		Member [] memberList = restTemplate.getForObject("/members/find/" + member.getVoornaam(), Member[].class);
//		Member foundMember = memberList[0];
//		log.debug(foundMember.getId());

		// Part 3 Delete Member

		HttpEntity<Member> requestDelete = new HttpEntity<>(member);
		ResponseEntity<Member> responsedeletion = restTemplate.exchange("/members/1",
				HttpMethod.DELETE, requestDelete, Member.class);
		assertEquals("Auth0 Delete Member", responsedeletion.getStatusCode(), HttpStatus.NO_CONTENT);
	}
}
