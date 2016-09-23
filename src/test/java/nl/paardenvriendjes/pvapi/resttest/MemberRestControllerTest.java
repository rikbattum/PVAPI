//package nl.paardenvriendjes.pvapi.resttest;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.TestRestTemplate;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import nl.paardenvriendjes.pvapi.TestApplication;
//import nl.paardenvriendjes.pvapi.abstracttest.AbstractTest;
//
//@ContextConfiguration(classes = TestApplication.class)
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
//
//public class MemberRestControllerTest extends AbstractTest {
//	  
//	@Autowired
//	    private TestRestTemplate restTemplate;
//
//	    @Test
//		@Transactional
//	    public void exampleTest() {
//	        String body = this.restTemplate.getForObject("/", String.class);
//	        assert(body).matches("Hello World");
//	    }
//
//	}
//


