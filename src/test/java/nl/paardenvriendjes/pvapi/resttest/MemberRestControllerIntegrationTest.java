//package nl.paardenvriendjes.pvapi.resttest;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.is;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.List;
//
//import org.apache.log4j.Logger;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.transaction.annotation.Transactional;
//
//import nl.paardenvriendjes.pvapi.abstracttest.AbstractControllerTest;
//import nl.paardenvriendjes.pvapi.daoimpl.MemberDaoImpl;
//import nl.paardenvriendjes.pvapi.daotest.MemberDaoImplTest;
//import nl.paardenvriendjes.pvapi.domain.Member;
//import nl.paardenvriendjes.testutil.TestUtilDataSetup;
//
//
//@Transactional
//public class MemberRestControllerIntegrationTest extends AbstractControllerTest {
//
//	
//    @Autowired
//	private TestUtilDataSetup testUtilDataSetup;
//    
//    @Autowired
//	private MemberDaoImpl memberservice; 
//    
//    static Logger log = Logger.getLogger(MemberDaoImplTest.class.getName());
//
//    private List <Member> memberList; 
//    
//    
//	@Before
//	public void setupTest() { 
//        // Prepare the Spring MVC Mock components for standalone testing
//		setUp();
//		// Initialize data; 
//		testUtilDataSetup.setMembers();		 
//	}
//	
//	@Test
//	@WithMockUser(username = "random@mailinator.com", authorities={"USER"})
//	public void shouldListMembers() throws Exception {
//
//		 ResultActions s = mvc.perform(get("/members/"))
//		.andExpect(status().isOk())
//		.andExpect(content().contentType("application/json;charset=UTF-8"))
//		.andExpect(jsonPath("$", hasSize(8)))
//		.andExpect(jsonPath("$[0].voornaam", is("Rik"))) 
//		.andExpect(jsonPath("$[0].achternaam", is("van Battum")))
//		.andExpect(jsonPath("$[0].username", is("rikbattum")))
//		.andExpect(jsonPath("$[0].email", is("userpv@mailinator.com")))
//		.andExpect(jsonPath("$[0].id", is(9)));
//		
//		log.info("should list members " + s.andReturn().getResponse().getContentAsString());
//	}	
//	
//	
//	@Test ()
//	public void shouldNotListMembersNotUserRole() throws Exception {
//
//		 ResultActions s = mvc.perform(get("/members/"))
//				 .andExpect(status().is4xxClientError());
//	}	
//	
//}