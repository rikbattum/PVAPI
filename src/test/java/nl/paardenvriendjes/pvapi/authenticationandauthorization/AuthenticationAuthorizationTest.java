package nl.paardenvriendjes.pvapi.authenticationandauthorization;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.abstracttest.AbstractControllerTest;

public class AuthenticationAuthorizationTest extends AbstractControllerTest {

	@Before
	public void initializeTest() {
		// Prepare the Spring MVC Mock components for testing
		setUp();
	}

	@After
	// logout all users
	public void afterTest() {

	}

	// test authentication
	
	@Test
	@Transactional
	@Rollback(true)
	public void welcomeTestAuthenticationNotNeeded() throws Exception {
		mvc.perform(get("/welcome"))
		.andExpect(status().isOk())
		.andExpect(content().string("Welcome to PVAPI, no login"));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void welcomeTestAuthenticatedNotAuthenticated() throws Exception {
		mvc.perform(get("/authenticatedwelcome"))
		.andExpect(status().isForbidden());
	}

	@Test
	@Transactional
	@Rollback(true)
	@WithMockUser(username = "random@mailinator.com", authorities = {"ADMIN"})
	public void welcomeTestAuthenticated() throws Exception {
		mvc.perform(get("/authenticatedwelcome"))
		.andExpect(status().isOk())
		.andExpect(content().string("Welcome to PVAPI, you are logged in :)!"));
	}

	// test authorization
	
	@Test
	@Transactional
	@Rollback(true)
	public void rightsTestLoggedInUser() throws Exception {
		mvc.perform(get("/authenticateduserrole"))
		.andExpect(status().isForbidden());
	}

	@Test
	@Transactional
	@Rollback(true)
	@WithMockUser(username = "random@mailinator.com", authorities = {"RANDOM"})
	public void rightsTestWrongAuthorization() throws Exception {
		mvc.perform(get("/authenticateduserrole"))
		.andExpect(status().isForbidden());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	@WithMockUser(username = "random@mailinator.com", authorities = {"USER"})
	public void rightsTestGoodAuthorization() throws Exception {
		mvc.perform(get("/authenticateduserrole"))
		.andExpect(status().isOk())
		.andExpect(content().string("Welcome to PVAPI, you are logged in and have the correct Role:)!"));
	}
}

// use this for real login without Mock user

// Auth0Util login = new Auth0Util();
// String id_token;
//
// try {
// id_token = login.login("rikbattum@hotmail.com", "admin");
// List<ClientHttpRequestInterceptor> interceptors = new
// ArrayList<ClientHttpRequestInterceptor>();
// interceptors.add(new
// TestUtilHeaderRequestInterceptor(HttpHeaders.AUTHORIZATION, "Bearer " +
// id_token));
// restTemplate.getRestTemplate().setInterceptors(interceptors);
// } catch (JSONException | URISyntaxException | IOException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// e.getMessage();
// }

// use this for real logout without Mock user
//
// restTemplate.getRestTemplate().getInterceptors().clear();
// Auth0Util logout = new Auth0Util();
// try {
// logout.logout();
// } catch (ClientProtocolException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// } catch (JSONException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// } catch (URISyntaxException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// } catch (IOException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
