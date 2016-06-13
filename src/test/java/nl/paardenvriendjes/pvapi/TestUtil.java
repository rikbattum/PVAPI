package nl.paardenvriendjes.pvapi;

import java.io.FileReader;
import java.text.SimpleDateFormat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.paardenvriendjes.pvapi.daoimpl.memberDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Member;

@Component
public class TestUtil {

	@Autowired
	private memberDaoImpl memberService;
	
	
	public void setMembers() {

		// Arrange customers 
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
		Object obj;

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
				m.setGeboortedatum(dateFormat.parse((String) memberX.get("geboortedatum")));
				m.setCreatedon(dateFormat.parse((String) memberX.get("createdon")));
				m.setEmail((String) memberX.get("email"));
				m.setOvermij((String) memberX.get("overmij"));
				m.setPassword((String) memberX.get("password"));
				m.setPlaatsnaam((String) memberX.get("plaatsnaam"));
				m.setProfileimage((String) memberX.get("profileimage"));
				memberService.saveMember(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			System.out.println("could not create members");
		}
	}
	
}
