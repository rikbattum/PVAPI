package nl.paardenvriendjes.pvapi.daoimpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import nl.paardenvriendjes.pvapi.domain.Member;

@Component
@Transactional

public class memberDaoImpl {

	// logging

	static Logger log = Logger.getLogger(memberDaoImpl.class.getName());

	// session Management

	@Autowired
	SessionFactory sessionFactory;

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	// CRUD
	// List all Members

	@Transactional(readOnly = true)
	public List<Member> listMembers() {
		List<Member> members = getCurrentSession().createQuery("from Member").list();
		log.debug("got List of all Members");
		return members;
	}

	// Load Member by ID

	public Member loadMember(Long id) {
		Member memberLoaded = (Member) getCurrentSession().load(Member.class, id);
		return memberLoaded;
	}

	// Save Member
	
	public void saveMember(Member member) {
		getCurrentSession().save(member);
		log.debug("saved Member");
	}
	
	// Update Member
	
	public void updateMember(Member member) {
		getCurrentSession().persist(member);
		log.debug("updated Member");
	}

	// Remove Member by ID
	
	public void removeMember(long id) {
		try { 
		Member memberToBeRemoved = (Member) getCurrentSession().load(Member.class, id);
		getCurrentSession().delete(memberToBeRemoved);
		log.debug("deleted Member id " + id);
		}
		catch (Exception e) { 
			log.error("Member to be deleted not found");
		} 
			
	
	}
}
