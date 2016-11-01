package nl.paardenvriendjes.testutil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class Auth0Util {
	
	static Logger log = Logger.getLogger(Auth0Util.class.getName());
	
	public String user_id;
	
	public String [] login(String username, String password) throws URISyntaxException, ClientProtocolException,
			IOException, JSONException {
		URIBuilder ub = new URIBuilder(
				"https://pvapp.eu.auth0.com/oauth/ro");
		// ub.setParameter("", "");
		URI uri = ub.build();
		String clientId = "sPcuHXFrQvNcxMv4iYvA9JoF1VhlqyLh";
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("client_id", "sPcuHXFrQvNcxMv4iYvA9JoF1VhlqyLh"));
		urlParameters.add(new BasicNameValuePair("username", username));
		urlParameters.add(new BasicNameValuePair("password", password));
		urlParameters.add(new BasicNameValuePair("connection", "Username-Password-Authentication"));
		urlParameters.add(new BasicNameValuePair("grant_type", "password"));
		urlParameters.add(new BasicNameValuePair("redirect_uri", "http://localhost:59923/adminsafewelcome")); //makes no difference?
		urlParameters.add(new BasicNameValuePair("scope", "openid profile"));
		urlParameters.add(new BasicNameValuePair("device", "openid"));

		HttpUriRequest request = org.apache.http.client.methods.RequestBuilder.post().setUri(uri)
				.setHeader(HttpHeaders.ACCEPT, " text/plain")
				.setEntity(new UrlEncodedFormEntity(urlParameters)).build();
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(request);
		int status = response.getStatusLine().getStatusCode();
		if(status != 200) {
			throw new ClientProtocolException(
					"Unexpected response status: " + status);
		}
		HttpEntity entity = response.getEntity();
		if(entity==null) {
			throw new ClientProtocolException(
					"Response has no body");
		}
		String body = EntityUtils.toString(entity);
		
		JSONObject jwt = new JSONObject(body);
		String access_token = (String) jwt.get("access_token");
		String id_token = (String) jwt.get("id_token");	

        //Mimick Resource Server
    	//Verify access_token 
    	ub = new URIBuilder(
    			"https://pvapp.eu.auth0.com/userinfo");
    	uri = ub.build();
    	request = RequestBuilder.get().setUri(uri).setHeader(HttpHeaders.AUTHORIZATION, "Bearer "+access_token).build();
    	response = client.execute(request);
    	status = response.getStatusLine().getStatusCode();
    	if(status != 200) {
    		throw new ClientProtocolException(
    				"Unexpected response status: " + status);
    	}
    	entity = response.getEntity();
    	if(entity==null) {
    		throw new ClientProtocolException(
    				"Response has no body");
    	}
    	body = EntityUtils.toString(entity);
    	verifyTokenReponse(body, clientId);
    	
    	//verify id_token
    	ub = new URIBuilder(
    			"https://pvapp.eu.auth0.com/tokeninfo");
    	uri = ub.build();
    	
    	urlParameters = new ArrayList<NameValuePair>();
    	urlParameters.add(new BasicNameValuePair("id_token", id_token));
    	request = RequestBuilder.post().setUri(uri)
    			.setEntity(new UrlEncodedFormEntity(urlParameters)).build();
    	response = client.execute(request);
    	status = response.getStatusLine().getStatusCode();
    	if(status != 200) {
    		throw new ClientProtocolException(
    				"Unexpected response status: " + status);
    	}
    	entity = response.getEntity();
    	if(entity==null) {
    		throw new ClientProtocolException(
    				"Response has no body");
    	}
    	body = EntityUtils.toString(entity);
    	user_id = verifyTokenReponse(body, clientId);
   
    	String [] bothstrings = {id_token, user_id};
    	return bothstrings;
    }

    private String verifyTokenReponse(String body, String clientId) throws JSONException, IllegalStateException {
    	//Do some elementary checks
    	log.debug("clientidis: " + clientId);
    	JSONObject jwt = new JSONObject(body);
    	log.debug("jwtnu" + jwt);
    	String clientID = (String) jwt.get("clientID");
     	log.debug("clientid2is: " + clientID);
     	log.debug("clientid2is: ");
    	if(!clientId.equals(clientID)) {
    		throw new IllegalStateException("Unexpected clientID");
    	}
//    	boolean email_verified = (Boolean) jwt.get("email_verified");
//    	if(!email_verified) {
//    		throw new IllegalStateException("email_verified must be true");
//    	}
    	String email = (String) jwt.get("email");
    	if(!IsSet(email)) {
    		throw new IllegalStateException("email must be set");
    	}
    	user_id = (String) jwt.get("user_id");
    	if(!IsSet(user_id)) {
    		throw new IllegalStateException("user_id must be set");
    	}
    	return user_id;
    }

    private static boolean IsSet(String s)
    {
    	return s!=null&&s.trim().length()>0;
    }

    public void logout() throws URISyntaxException, ClientProtocolException,
	IOException, JSONException {
    	URIBuilder ub = new URIBuilder(
				"https://pvapp.eu.auth0.com/v2/logout?returnTo=https://www.google.com");
		// ub.setParameter("", "");
		URI uri = ub.build();
		HttpUriRequest request = org.apache.http.client.methods.RequestBuilder.get().setUri(uri).build();
		HttpClient client = HttpClientBuilder.create().build();
		client.execute(request);	
    }
    
    public void deleteUser(String user_id) throws URISyntaxException, ClientProtocolException,
	IOException, JSONException { 	
    	
    	
    	// specific bearer token for management API
    	String bearertoken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJzUGN1SFhGclF2TmN4TXY0aVl2QTlKb0YxVmhscXlMaCIsInNjb3BlcyI6eyJ1c2VycyI6eyJhY3Rpb25zIjpbImRlbGV0ZSIsInJlYWQiLCJjcmVhdGUiLCJ1cGRhdGUiXX19LCJpYXQiOjE0NzgwMjk0ODIsImp0aSI6ImU5Y2Q4OTI3NGJhMzJhZTZmMWQ1ZTQyZTU0N2Q0ZGZmIn0.8yOCl1DPjA696TbK9PQxwxpra6lKEj4QnIp1fA78NeI";
    	String correctUserIdFortmat = user_id.substring(6);
    	
    	URIBuilder ub = new URIBuilder(
				"https://pvapp.eu.auth0.com/api/v2/users/" + correctUserIdFortmat);
		// ub.setParameter("", "");
		URI uri = ub.build();
		HttpUriRequest request = org.apache.http.client.methods.RequestBuilder.delete().setUri(uri).setHeader(HttpHeaders.AUTHORIZATION, bearertoken).build();
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(request);
		if (response.getStatusLine().getStatusCode()== 201) { 
			log.debug("user with id "+ user_id + " succesfully deleted");
		}
    }
	}	

