package nl.paardenvriendjes.pvapi.resttest;

public class MessageRestControllerTest {

	
	
	
	
	
	
//	
//
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testNotAbleToChangeMessageOfSomeBodyElse() {
//		testUtilDataSetup.setMembers();
//		testUtilDataSetup.runMessagesPost();
//		List<Message> messages = messageService.listAll();
//		Message messageToBeEdited = messages.get(0);
//		log.debug("EditMessage: " + messageToBeEdited);
//		messageToBeEdited.setMessage("this is an edited message");
//		// make httpentity
//		HttpEntity<Message> requestUpdate = new HttpEntity<>(messageToBeEdited);
//		// create PUT exchange
//		ResponseEntity<Message> response = restTemplate.exchange("/messages/" + messageToBeEdited.getId(),
//				HttpMethod.PUT, requestUpdate, Message.class);
//		// assert not permitted
//		assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
//	}
//
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testAbleToAddAndChangeOwnMessage() { 
//		testUtilDataSetup.setMembers();
//		testUtilDataSetup.runMessagesPost();
//		
//		Member validMember = (Member) memberService.findMemberByLastName("Battum").get(0);
//		Message messageToBeAdded = new Message();
//		messageToBeAdded.setMessage("message being added by controller");
//		messageToBeAdded.setMember(validMember);
//		// make httpentity
//		HttpEntity<Message> request = new HttpEntity<>(messageToBeAdded);
//		//login
//		try {
//			String id_token = auth0.login("userpv@mailinator.com", "user123");
//			List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
//			interceptors.add(new TestUtilHeaderRequestInterceptor(HttpHeaders.AUTHORIZATION, "Bearer " + id_token));
//			restTemplate.getRestTemplate().setInterceptors(interceptors);
//		} catch (JSONException | URISyntaxException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	
	
	
	
	
}
