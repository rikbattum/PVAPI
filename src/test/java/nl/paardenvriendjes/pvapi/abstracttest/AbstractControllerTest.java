package nl.paardenvriendjes.pvapi.abstracttest;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude;

import nl.paardenvriendjes.application.Application;
import nl.paardenvriendjes.application.HibernateConfiguration;
import nl.paardenvriendjes.application.security.AppSecurityConfig;
import nl.paardenvriendjes.restcontrollers.BaseController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Application.class, HibernateConfiguration.class, AppSecurityConfig.class})
@WebAppConfiguration
@Ignore
public class AbstractControllerTest {

	protected MockMvc mvc;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	/**
	 * Prepares the test class for execution of web tests. Builds a MockMvc
	 * instance. Call this method from the concrete JUnit test class in the
	 * <code>@Before</code> setup method.
	 */
	protected void setUp() {
		mvc = 
				MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}

	/**
	 * Prepares the test class for execution of web tests. Builds a MockMvc
	 * instance using standalone configuration facilitating the injection of
	 * Mockito resources into the controller class.
	 *
	 * @param controller
	 *            A controller object to be tested.
	 */
	protected void setUp(BaseController controller) {
		mvc = MockMvcBuilders.standaloneSetup(controller)
				.build();
	}

	/**
	 * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
	 * 
	 * @param obj
	 *            The Object to map.
	 * @return A String of JSON.
	 * @throws JsonProcessingException
	 *             Thrown if an error occurs while mapping.
	 */
	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}

	/**
	 * Maps a String of JSON into an instance of a Class of type T. Uses a
	 * Jackson ObjectMapper.
	 * 
	 * @param json
	 *            A String of JSON.
	 * @param clazz
	 *            A Class of type T. The mapper will attempt to convert the JSON
	 *            into an Object of this Class type.
	 * @return An Object of type T.
	 * @throws JsonParseException
	 *             Thrown if an error occurs while mapping.
	 * @throws JsonMappingException
	 *             Thrown if an error occurs while mapping.
	 * @throws IOException
	 *             Thrown if an error occurs while mapping.
	 */
	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, clazz);
	}
	
	// converts object to JSON bytes
	
	 protected static byte[] convertObjectToJsonBytes(Object object) throws IOException {
	        ObjectMapper mapper = new ObjectMapper();
	        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	        return mapper.writeValueAsBytes(object);
	    }
}