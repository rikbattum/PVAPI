package nl.paardenvriendjes.pvapi.daotest;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.paardenvriendjes.pvapi.daoimpl.MemberDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Interesse;
import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.domain.Message;

@Component
public class TestUtil {

	@Autowired
	private MemberDaoImpl memberService;


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
				m.setEmail((String) memberX.get("email"));
				m.setOvermij((String) memberX.get("overmij"));
				m.setPassword((String) memberX.get("password"));
				m.setPlaatsnaam((String) memberX.get("plaatsnaam"));
				m.setProfileimage((String) memberX.get("profileimage"));
				memberService.save(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			System.out.println("could not create members");
		}
	}

	public void runMessagesPost() {

		// prepare message list
		Object obj;
		
		//		set members
				setMembers();
		List<Member> memberList = memberService.listAll();
		
		
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
				memberService.edit(tempMember);
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			System.out.println("could not create messages");
		}
	}
}
