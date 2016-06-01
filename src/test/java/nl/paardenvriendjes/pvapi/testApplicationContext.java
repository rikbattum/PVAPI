package nl.paardenvriendjes.pvapi;

import java.io.FileReader;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.format.Printer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


@ContextConfiguration("classpath:application-context.xml")
public class testApplicationContext extends AbstractTransactionalJUnit4SpringContextTests {

	private ApplicationContext ctx;
	
	@Before
	public void initialize () {
 
		
	}
		
	@Test
	public void testMemberCreation () throws Exception {
	
		Object obj; 
		JSONObject member = null;
		
		try {
			JSONParser parser = new JSONParser(); 
            obj = parser.parse(new FileReader(
                    "src/test/resources/members1.json"));         
        } catch (Exception e) {
            e.printStackTrace();
        }
		String name = (String) member.get("voornaam");
		assertThat(name,  is("constant string"));
	}	
	}

