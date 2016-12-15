package nl.paardenvriendjes.pvapi.authorization;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.abstracttest.AbstractControllerTest;
import nl.paardenvriendjes.restcontrollers.TestController;

public class AuthenticationTest extends AbstractControllerTest{

//    @InjectMocks
//    private TestController testController;
//	
	@Before
	public void initializeLogin() {  
	// Prepare the Spring MVC Mock components for standalone testing
	setUp();
	
	// Initialize Mockito annotated components
	MockitoAnnotations.initMocks(this);
					
	//  use this for real login without Mock user
			
	//	Auth0Util login = new Auth0Util();
	//	String id_token;
	//	
	//	try {
	//		id_token =  login.login("rikbattum@hotmail.com", "admin"); 
	//		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
	//		interceptors.add(new TestUtilHeaderRequestInterceptor(HttpHeaders.AUTHORIZATION, "Bearer " + id_token));
	//		restTemplate.getRestTemplate().setInterceptors(interceptors);
	//	} catch (JSONException | URISyntaxException | IOException e) {
	//		// TODO Auto-generated catch block
	//		e.printStackTrace();
	//		e.getMessage();
	//	}
	}

	@After
	// logout all users
	public void logoutUser () { 
	//  use this for real logout without Mock user
	//		
	//		restTemplate.getRestTemplate().getInterceptors().clear();
	//		Auth0Util logout = new Auth0Util();
	//		try {
	//			logout.logout();
	//		} catch (ClientProtocolException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		} catch (JSONException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		} catch (URISyntaxException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		} catch (IOException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	}	
	
	@Test
	@Transactional
	@Rollback(true)
	public void welcomeTestNotAuthenticated() throws Exception {
	mvc.perform(get("/welcome/")).andExpect(status().isOk());
	}

	@Test
	@Transactional
	@Rollback(true)
	@WithMockUser(username = "random@mailinator.com", roles={"ADMIN"})
	public void welcomeTestAuthenticatedCorrectRole() throws Exception {
	mvc.perform(get("/adminsafewelcome/")).andExpect(status().isOk());
	}
	
	@Test
	@Transactional	
	@Rollback(true)
	@WithMockUser(username = "random@mailinator.com", roles={"WRONGROLE"})
	public void welcomeTestAuthenticatedNotCorrectRole() throws Exception {
	mvc.perform(get("/fakerolesafewelcome")).andExpect(status().isInternalServerError());
	}
}


