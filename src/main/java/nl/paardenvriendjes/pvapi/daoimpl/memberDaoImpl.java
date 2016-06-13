package nl.paardenvriendjes.pvapi.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.domain.Member;

@Component
@Transactional

public class memberDaoImpl {

	@Autowired
	SessionFactory sessionFactory;

	protected Session getCurrentSession() {
	return sessionFactory.getCurrentSession();
	}


	@Transactional(readOnly = true)
	public List<Member> listMembers() {
		List<Member> members = getCurrentSession().createQuery("from Member").list();
		return members;
	}

	public void saveMember(Member member) {
		getCurrentSession().save(member);
	}
	
	public void removeTrap(long id) {
		// TODO Auto-generated method stub

	}
}
