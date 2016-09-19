package nl.paardenvriendjes.pvapi.resttest;


@ContextConfiguration(classes = TestApplication.java.class)
@RunWith(SpringJUnit4ClassRunner.class)


public class MemberRestControllerTest {

    private MockMvc mockMvc;

  @Test
	@Transactional
	@Rollback(false)
	public void testMemberCreation throws Exception {
  Object member = 	{}   ;
	  
	
        mockMvc.perform(post("/member"))

 
}







}
