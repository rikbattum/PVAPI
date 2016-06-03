package nl.paardenvriendjes.pvapi.daoimpl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;

import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.domain.Trap;

@Component
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class memberDaoImpl {

	@Autowired
	private SessionFactory sessionFactory;
		
		@Transactional(readOnly = true)
		public List<Member> listMembers() {
			Session session = sessionFactory.getCurrentSession();
			List <Member> members= session.createQuery("from Member").list();
			return members;
		}

		public void saveMember(Member member) {
			Session session = sessionFactory.getCurrentSession();
			session.save(member);
		}

		public void removeTrap(long id) {
			// TODO Auto-generated method stub
			
		}
	}

	
	
	
	