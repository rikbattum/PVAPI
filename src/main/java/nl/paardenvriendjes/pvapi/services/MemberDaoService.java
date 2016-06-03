package nl.paardenvriendjes.pvapi.services;

import java.util.List;

import nl.paardenvriendjes.pvapi.domain.Member;

public class MemberDaoService {

	
	
	public interface MemberDao{

		
		public List <Member> listMembers();
		
		public void saveMember(Member member);

		public void removeTrap(Member member);
		
		
		
		
	}
	
	
	
	
	
	
}
