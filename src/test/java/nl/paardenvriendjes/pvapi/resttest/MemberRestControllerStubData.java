package nl.paardenvriendjes.pvapi.resttest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import nl.paardenvriendjes.pvapi.data.Member;
import nl.paardenvriendjes.pvapi.data.enums.Geslacht;
import nl.paardenvriendjes.pvapi.data.enums.Place;
import nl.paardenvriendjes.pvapi.data.enums.SportLevel;
import nl.paardenvriendjes.pvapi.data.enums.Vervoer;

@Component
public class MemberRestControllerStubData {

	public static List<Member> getMemberListStubData() {
		List<Member> list = new ArrayList<>();
		list.add(getMemberOneStubData());
		list.add(getMemberTwoStubData());
		return list;
	}

	public static Member getMemberOneStubData() {
		Member member = new Member();
		member.setId(10001L);
		member.setVoornaam("Peddy");
		member.setAchternaam("Horsy");
		member.setEmail("peddy.horsey@mailinator.com");
		member.setGeboortedatum(new Date(2025, 06, 23, 00, 00));
		return member;
	}

	public static Member getMemberTwoStubData() {
		Member member = new Member();
		member.setId(10002L);
		member.setVoornaam("Anny");
		member.setAchternaam("Ponny");
		member.setEmail("anny.ponny@mailinator.com");
		member.setGeboortedatum(new Date(2025, 06, 23, 00, 00));
		return member;
	}

	public static List<Member> getEmptyMemberList() {
		List<Member> emptymemberList = new ArrayList<Member>();
		return emptymemberList;
	}

	public static Member memberOneFullAdd() {
		Member member = new Member();
		member.setVoornaam("Peddy");
		member.setAchternaam("Horsy");
		member.setUsername("theHorseBoy");
		member.setGeboortedatum(new Date(12 - 6 - 1979));
		member.setEmail("peddy.horsey@mailinator.com");
		member.setAuth0user_id("auth0|582dff439ca666c66c968b412345678");
		member.setGeslacht(Geslacht.M);
		member.setGeboortedatum(new Date(12 - 06 - 1979));
		member.setOvermij("Ik ben leuk");
		member.setPlace(Place.AALDEN);
		member.setProfileimage("http://res.cloudinary.com/epona/pictureXYZ");
		member.setSportLevel(SportLevel.TOP);
		member.setVervoer(Vervoer.GEENVERVOER);
		member.setPassword("2312321hjsdasH");
		return member;
	}

	public static Member memberOneFullAdded() {

		Member memberadded = new Member();
		memberadded.setId(1L);
		memberadded.setVoornaam("Peddy");
		memberadded.setAchternaam("Horsy");
		memberadded.setUsername("theHorseBoy");
		memberadded.setGeboortedatum(new Date(12 - 6 - 1979));
		memberadded.setEmail("peddy.horsey@mailinator.com");
		memberadded.setAuth0user_id("auth0|582dff439ca666c66c968b412345678");
		memberadded.setGeslacht(Geslacht.M);
		memberadded.setGeboortedatum(new Date(12 - 06 - 1979));
		memberadded.setOvermij("Ik ben leuk");
		memberadded.setPlace(Place.AALDEN);
		memberadded.setProfileimage("http://res.cloudinary.com/epona/pictureXYZ");
		memberadded.setSportLevel(SportLevel.TOP);
		memberadded.setVervoer(Vervoer.GEENVERVOER);
		memberadded.setPassword("2312321hjsdasH");
		return memberadded;
	}
}
