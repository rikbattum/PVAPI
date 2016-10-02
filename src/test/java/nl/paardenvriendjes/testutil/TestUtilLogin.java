package nl.paardenvriendjes.testutil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class TestUtilLogin {
	
	static Logger log = Logger.getLogger(TestUtilLogin.class.getName());
	
	
	public String logon() throws URISyntaxException, ClientProtocolException,
			IOException, JSONException {
		URIBuilder ub = new URIBuilder(
				"https://pvapp.eu.auth0.com/oauth/ro");
		// ub.setParameter("", "");
		URI uri = ub.build();

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("client_id", "sPcuHXFrQvNcxMv4iYvA9JoF1VhlqyLh"));
		urlParameters.add(new BasicNameValuePair("username", "rikbattum@hotmail.com"));
		urlParameters.add(new BasicNameValuePair("password", "admin"));
		urlParameters.add(new BasicNameValuePair("connection", "Username-Password-Authentication"));
		urlParameters.add(new BasicNameValuePair("grant_type", "password"));
		//urlParameters.add(new BasicNameValuePair("redirect_uri", "http://localhost:8080/SessionTest2/callback")); //makes no difference
		urlParameters.add(new BasicNameValuePair("scope", "openid"));
		urlParameters.add(new BasicNameValuePair("device", "openid"));

		HttpUriRequest request = org.apache.http.client.methods.RequestBuilder.post().setUri(uri)
				.setHeader(HttpHeaders.ACCEPT, "application/json")
				.setEntity(new UrlEncodedFormEntity(urlParameters)).build();
		BasicResponseHandler rh = new BasicResponseHandler();
		HttpClient client = HttpClientBuilder.create().build();
		String response = client.execute(request, rh);
		
		log.debug(response);
		JSONObject jwt = new JSONObject(response);
		log.debug("id_token is "+jwt.get("id_token"));
		log.debug("access_token is "+jwt.get("access_token"));
		log.debug("token_type is "+jwt.get("token_type"));
		String access_token = (String) jwt.get("access_token");
		String id_token = (String) jwt.get("id_token");

        return id_token;
        		
	}	
}
