package nl.paardenvriendjes.pvapi.resttest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.abstracttest.AbstractControllerTest;
import nl.paardenvriendjes.pvapi.dao.MemberDaoImpl;
import nl.paardenvriendjes.pvapi.daotest.MemberDaoImplTest;
import nl.paardenvriendjes.pvapi.resources.MemberRestController;

@Transactional
@Slf4j
// no security
public class MemberRestControllerTest extends AbstractControllerTest {

	@Mock
	private MemberDaoImpl memberService;

	@InjectMocks
	private MemberRestController memberRestController;

	@Before
	public void setupTest() {
		// Prepare the Spring MVC Mock components for standalone testing
		setUp(memberRestController);
		// Initialize Mockito annotated components
		MockitoAnnotations.initMocks(this);
	}

	// Member listing
	
	@Test
	public void shouldListMembers() throws Exception {

		when(memberService.listAll()).thenReturn(MemberRestControllerStubData.getMemberListStubData());

		ResultActions s = mvc.perform(get("/members/"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].voornaam", is("Peddy")))
				.andExpect(jsonPath("$[1].voornaam", is("Anny")));
		
		verify(memberService, times(1)).listAll();
		verifyNoMoreInteractions(memberService);

		log.info("should list members " + s.andReturn().getResponse().getContentAsString());
	}

	@Test
	public void shouldListMembersNoContent() throws Exception {    
		when(memberService.listAll()).thenReturn(MemberRestControllerStubData.getEmptyMemberList());
		ResultActions s = mvc.perform(get("/members/")).andExpect(content().string(""));
		log.info("should list members " + s.andReturn().getResponse().getContentAsString());
		
		verify(memberService, times(1)).listAll();
		verifyNoMoreInteractions(memberService);
	}
	
	// Member by Id
	
	@Test
	public void shouldListMemberById() throws Exception {

		when(memberService.listOne(1L)).thenReturn(MemberRestControllerStubData.getMemberOneStubData());

		ResultActions s = mvc.perform(get("/members/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.voornaam", is("Peddy")));
		
		verify(memberService, times(1)).listOne(1L);
		verifyNoMoreInteractions(memberService);

		log.info("should list member by Id " + s.andReturn().getResponse().getContentAsString());
	}
	
	@Test
	public void shouldReturnNotFoundWhenWrongMemberById() throws Exception {

		ResultActions s = mvc.perform(get("/members/666"))
				.andExpect(status().isNotFound());

		log.info("should list member by Id " + s.andReturn().getResponse().getContentAsString());
	}

	
	// really registers a member
	
//	@Test
//	public void shouldRegisterMember() throws Exception {
//		
//	    when(memberService.save(any(Member.class))).thenReturn(MemberRestControllerStubData.memberOneFullAdded());
//	    
//		ResultActions s = mvc.perform(get("/members/666"))
//				.andExpect(status().isNotFound());
//
//		
//	       mvc.perform(post("/members/signup")
//	                .contentType("configuration/json")
//	                .content(convertObjectToJsonBytes(MemberRestControllerStubData.memberOneFullAdd()))
//	        )
//	                .andExpect(status().isCreated())
//	                .andExpect(content().contentType("configuration/json"))
//	                .andExpect(jsonPath("$.id", is(1)))
//	                .andExpect(jsonPath("$.Voornaam", is("Peddy")))
//	                .andExpect(jsonPath("$.Achternaam", is("Horsy")))
//	       			.andExpect(jsonPath("$.Geslacht", is("M")));
//		log.info("should list member by Id " + s.andReturn().getResponse().getContentAsString());
//	}
	


}
