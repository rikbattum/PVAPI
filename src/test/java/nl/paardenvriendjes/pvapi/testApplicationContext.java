package nl.paardenvriendjes.pvapi;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import nl.paardenvriendjes.pvapi.daoimpl.memberDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Member;

@ContextConfiguration("classpath:application-context.xml")
public class testApplicationContext extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private memberDaoImpl memberService;
	
	@SuppressWarnings("deprecation")
	@Before
	public void initialize() {

		Object obj;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");

		// Set 8 Members
		
		try {
			JSONParser parser = new JSONParser();
			obj = parser.parse(new FileReader("src/test/resources/members.json"));
			JSONArray members = (JSONArray) obj;
			System.out.println("Succes getting member file!");
			for (Object createmember : members) {
				JSONObject memberX = (JSONObject) createmember;
				System.out.println(memberX);
				Member m = new Member();
				m.setAchternaam((String) memberX.get("achternaam"));
				m.setVoornaam((String) memberX.get("voornaam"));
				m.setUsername((String) memberX.get("username"));
				m.setGeboortedatum(dateFormat.parse((String)memberX.get("geboortedatum")));
				m.setCreatedon(dateFormat.parse((String)memberX.get("createdon")));
				m.setEmail((String) memberX.get("email"));
				m.setOvermij((String) memberX.get("overmij"));
				m.setPassword((String) memberX.get("password"));
				m.setPlaatsnaam((String) memberX.get("plaatsnaam"));
				m.setProfileimage((String) memberX.get("profileimage"));
				memberService.saveMember(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("could not create members");
		}
	}

	@Test
	public void testIfMemberAreCreated() throws Exception {
		
	

	}
}
