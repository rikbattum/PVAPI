package nl.paardenvriendjes.pvapi;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

import javax.persistence.EnumType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.paardenvriendjes.enumerations.MessageType;
import nl.paardenvriendjes.pvapi.daoimpl.MemberDaoImpl;
import nl.paardenvriendjes.pvapi.daoimpl.MessageDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.domain.Message;

@Component
public class TestUtil {

	@Autowired
	private MemberDaoImpl memberService;
	@Autowired
	private MessageDaoImpl messageService;


	public void setMembers() {

		// Arrange members

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

	public void runMessagesPost() {

		// prepare message list
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
		Object obj;
		
		//		set members
				setMembers();
		List<Member> memberList = memberService.listMembers();
		
		
		try {
			// get individual member reference
		
			JSONParser parser = new JSONParser();
			obj = parser.parse(new FileReader("src/test/resources/messages.json"));
			JSONArray messages = (JSONArray) obj;
			System.out.println("Succes getting messages file!");
			
			
						
			for (Object message : messages) {
				JSONObject messageX = (JSONObject) message;
				System.out.println(messageX);
				Message m = new Message();
				System.out.println("testRik");
				// create randomizer for members out of testdata	
				Random randomGenerator = new Random(); 
				int index = randomGenerator.nextInt(memberList.size());
				
				// get random member for message
				Member tempMember = memberList.get(index);	
				m.setMember(tempMember);
//				m.setType((MessageType) messageX.get("type"));
				m.setMessage((String) messageX.get("message"));
				m.setPiclink((String) messageX.get("piclink"));
				m.setPicLinkSecond((String) messageX.get("picLinkSecond"));
				m.setPicLinkThird((String) messageX.get("picLinkThird"));	
				tempMember.getMessages().add(m);
				memberService.updateMember(tempMember);
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			System.out.println("could not create messages");
		}
	}
}
