package nl.paardenvriendjes.pvapi;

import java.io.FileReader;
import java.text.SimpleDateFormat;

import javax.persistence.EnumType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.paardenvriendjes.enumerations.MessageType;
import nl.paardenvriendjes.pvapi.daoimpl.memberDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.domain.Message;

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

	public void setMessages() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
		Object obj;

		try {
			JSONParser parser = new JSONParser();
			obj = parser.parse(new FileReader("src/test/resources/messages.json"));
			JSONArray messages = (JSONArray) obj;
			System.out.println("Succes getting messages file!");

			// "message": "Esther heeft zich aangemeld",
			// "type": "NEWPROFILE",
			// "insertDate": "22-05-2015",
			// "piclink": "www.piclink.nl/4",
			// "picLinkSecond": " ",
			// "picLinkThird": " "

			for (Object message : messages) {
				JSONObject messageX = (JSONObject) message;
				Message m = new Message();
				m.setMessage((String) messageX.get("message"));
				m.setType((MessageType) messageX.get("type"));
				m.setPiclink((String) messageX.get("piclink"));
				m.setPicLinkSecond((String) messageX.get("picLinkSecond"));
				m.setPicLinkThird((String) messageX.get("picLinkThird"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			System.out.println("could not create messages");
		}
	}
}
