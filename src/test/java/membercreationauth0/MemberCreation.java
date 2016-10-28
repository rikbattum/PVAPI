package membercreationauth0;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.application.HibernateConfiguration;
import nl.paardenvriendjes.pvapi.authorization.AuthorizationLoggedInUser;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = HibernateConfiguration.class)

public class MemberCreation {

	@Autowired
	private TestRestTemplate restTemplate;

	static Logger log = Logger.getLogger(AuthorizationLoggedInUser.class.getName());

	// THIS TEST IS AN INCIDENTAL TEST AND SETUP TEST.
	// WILL ONLY BE RUN WHEN BUILDING AND INTEGRATING WITH AUTH0. 
	// NEEDS CLEANEDUP USER IN AUTH) EVERY TIME
	
	@Ignore
	@Test
	@Transactional
	@Rollback(true)
	public void RegisterMember()
			throws URISyntaxException, ClientProtocolException, IOException, JSONException {
	
			// set basis signup data as json input in body
			String jsoninput = "{\"client_id\": \"sPcuHXFrQvNcxMv4iYvA9JoF1VhlqyLh\", \"email\": \"test123@hotmail.com\", \"password\": \"asdasdDs@156\",\"connection\":  \"Username-Password-Authentication\" }"; 
			StringEntity params = new StringEntity(jsoninput);
			params.setContentType("application/json");
			log.debug("params2" + params);
			
			// build uri and request
			URIBuilder ub = new URIBuilder("https://pvapp.eu.auth0.com/dbconnections/signup");
			URI uri = ub.build();
			HttpUriRequest request = org.apache.http.client.methods.RequestBuilder.post().setUri(uri).setEntity(params)
					.build();
			log.debug("request" + request);
			HttpClient client = HttpClientBuilder.create().build();		
			
			HttpResponse response = client.execute(request);
			
			assertEquals(response.getStatusLine().getStatusCode(), 200);

	}
}
