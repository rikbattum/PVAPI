package nl.paardenvriendjes.pvapi.resttest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.http.HttpHeaders;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import nl.paardenvriendjes.application.HibernateConfiguration;
import nl.paardenvriendjes.testutil.TestUtilHeaderRequestInterceptor;
import nl.paardenvriendjes.testutil.TestUtilLogin;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = HibernateConfiguration.class)

public class MemberRestControllerTest {
	
	@Autowired
    private TestRestTemplate restTemplate;



	
	@Test
	@Transactional
	@Rollback(true)
	public void test () { 
		assertEquals(true, true);
	}
	

}
