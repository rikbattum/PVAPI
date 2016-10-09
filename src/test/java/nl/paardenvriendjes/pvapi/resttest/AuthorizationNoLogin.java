package nl.paardenvriendjes.pvapi.resttest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.application.HibernateConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = HibernateConfiguration.class)

public class AuthorizationNoLogin {

	@Autowired
    private TestRestTemplate restTemplate;

	@Before
	public void doSomething () {

	}
	
	
	@Test
	@Transactional
	public void welcomeTest() {
		String body = restTemplate.getForObject("/welcome", String.class);
		assertEquals(body, "Welcome to PVAPI, no login");
	}

	@Test
	@Transactional
	public void rightsTestAdmin() {

		ResponseEntity<String> response =  restTemplate.getForEntity("/adminsafewelcome", String.class);
		assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	@Transactional
	public void rightsTestUserIdSpecific() {

		ResponseEntity<String> response =  restTemplate.getForEntity("/usersafewelcome", String.class);
		assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	
}
