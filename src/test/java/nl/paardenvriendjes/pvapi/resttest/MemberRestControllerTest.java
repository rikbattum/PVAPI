package nl.paardenvriendjes.pvapi.resttest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.hibernate.configuration.HibernateConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT, classes= HibernateConfiguration.class)

public class MemberRestControllerTest {
	  
	@Autowired
	    private TestRestTemplate restTemplate;

	@Test
	@Transactional
	    public void welcomeTest() {
	        String body = this.restTemplate.getForObject("/welcome", String.class);
	      
	       assertEquals(body, "Welcome to PVAPI");
	}

@Test
@Transactional
    public void rightsTest() {
        String body = this.restTemplate.getForObject("/", String.class);
      
       assertEquals(body, "Welcome to PVAPI");
    }

}



