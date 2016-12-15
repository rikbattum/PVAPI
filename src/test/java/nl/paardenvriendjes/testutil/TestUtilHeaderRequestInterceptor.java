package nl.paardenvriendjes.testutil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

public class TestUtilHeaderRequestInterceptor implements ClientHttpRequestInterceptor {

        private final String headerName;

        private final String headerValue;
        List <MediaType> mediatypes = new ArrayList <MediaType>();
        

        public TestUtilHeaderRequestInterceptor(String headerName, String headerValue) {
            this.headerName = headerName;
            this.headerValue = headerValue;
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        	mediatypes.add(MediaType.APPLICATION_JSON);
        	HttpRequest wrapper = new HttpRequestWrapper(request);
            wrapper.getHeaders().set(headerName, headerValue);
            wrapper.getHeaders().set("Cache-Control", "no-cache");
            wrapper.getHeaders().setAccept(mediatypes);
            return execution.execute(wrapper, body);
        }
    }