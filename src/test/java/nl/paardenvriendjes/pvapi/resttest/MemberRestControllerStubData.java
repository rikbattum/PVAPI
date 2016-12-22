package nl.paardenvriendjes.pvapi.resttest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import nl.paardenvriendjes.pvapi.domain.Member;

@Component
public class MemberRestControllerStubData {

	public static List<Member> getMemberListStubData() {
		List<Member> list = new ArrayList<>();
		list.add(getMemberOneStubData());
		list.add(getMemberTwoStubData());
		return list;
	}

	private static Member getMemberOneStubData() {
		Member member = new Member();
		member.setId(10001L);
		member.setVoornaam("Peddy");
		member.setAchternaam("Horsy");
		member.setEmail("peddy.horsey@mailinator.com");
		member.setGeboortedatum(new Date(2025, 06, 23, 00, 00));
		return member;
	}

	private static Member getMemberTwoStubData() {
		Member member = new Member();
		member.setId(10002L);
		member.setVoornaam("Anny");
		member.setAchternaam("Ponny");
		member.setEmail("anny.ponny@mailinator.com");
		member.setGeboortedatum(new Date(2025, 06, 23, 00, 00));
		return member;
	}

	public static List getEmptyMemberList() {
		List<Member> emptymemberList = new ArrayList<Member>();
		return emptymemberList;
	}

}
