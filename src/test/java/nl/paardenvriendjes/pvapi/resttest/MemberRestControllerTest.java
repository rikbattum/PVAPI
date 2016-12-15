package nl.paardenvriendjes.pvapi.resttest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;   
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import nl.paardenvriendjes.application.Application;
import nl.paardenvriendjes.pvapi.abstracttest.AbstractControllerTest;
import nl.paardenvriendjes.pvapi.daoimpl.MemberDaoImpl;
import nl.paardenvriendjes.pvapi.daotest.MemberDaoImplTest;
import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.restcontrollers.MemberRestController;
//
//@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
//@RunWith(SpringRunner.class)
import nl.paardenvriendjes.testutil.TestUtilDataSetup;

@Transactional
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

		when(memberService.listAll()).thenReturn(null);

		 ResultActions s = mvc.perform(get("/members/"));
		//.andExpect(jsonPath("$.content", hasSize(1)));
	
		log.info("should list members " + s.andReturn().getResponse().getContentAsString());
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
