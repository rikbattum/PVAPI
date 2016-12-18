package nl.paardenvriendjes.pvapi.resttest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.abstracttest.AbstractControllerTest;
import nl.paardenvriendjes.pvapi.daoimpl.MemberDaoImpl;
import nl.paardenvriendjes.pvapi.daotest.MemberDaoImplTest;
import nl.paardenvriendjes.restcontrollers.MemberRestController;
//
//@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
//@RunWith(SpringRunner.class)
import nl.paardenvriendjes.testutil.TestUtilDataSetup;

@Transactional
// no security
public class MemberRestControllerTest extends AbstractControllerTest {

    @Mock
    private MemberDaoImpl memberService;
	
    @InjectMocks
    private MemberRestController memberRestController;
	
    @Autowired
	private TestUtilDataSetup testUtilDataSetup;
    
    @Autowired
    private MemberRestControllerStubData stubdata; 
    
    static Logger log = Logger.getLogger(MemberDaoImplTest.class.getName());
    
	@Before
	public void setupTest() { 
        // Prepare the Spring MVC Mock components for standalone testing
		setUp(memberRestController);
		// Initialize Mockito annotated components
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldListMembers() throws Exception {

		when(memberService.listAll()).thenReturn(stubdata.getMemberListStubData());

		 ResultActions s = mvc.perform(get("/members/"))
		.andExpect(status().isOk());
		//.andExpect(jsonPath("$.content", hasSize(1)));
	
		log.info("should list members " + s.andReturn().getResponse().getContentAsString());
	}
	
	@Test
	public void shoulListMembersNoContent() throws Exception {

		when(memberService.listAll()).thenReturn(MemberRestControllerStubData.getMemberListStubData());

		 ResultActions s = mvc.perform(get("/members/"))
		.andExpect(jsonPath("$", hasSize(2)));
	
		log.info("should list members " + s.andReturn().getResponse().getContentAsString());
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
