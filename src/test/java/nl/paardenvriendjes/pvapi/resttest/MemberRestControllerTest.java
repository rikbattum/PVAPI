package nl.paardenvriendjes.pvapi.resttest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.context.embedded.LocalServerPort;
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
	
	@Autowired
	EmbeddedWebApplicationContext server;
	@LocalServerPort
	private int port; 
	
	    @Test
		@Transactional
	    public void exampleTest() {
	        String body = this.restTemplate.getForObject("/", String.class);
	        assert(body).matches("Hello World");
	    }

	}



