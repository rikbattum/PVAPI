package nl.paardenvriendjes.pvapi.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.service.AbstractDaoService;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class MemberDaoImpl extends AbstractDaoService<Member> {

	static Logger log = Logger.getLogger(MessageDaoImpl.class.getName());
	
	@Autowired
	SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public MemberDaoImpl() {
		super(Member.class);
	}

	
	@Override
	public void save(Member member) {
		member.setCreatedonDate();
		member.setActive(true);
		getCurrentSession().persist(member);
		log.debug("saved One: " + member.toString());
	}
	
	public List<Member> findMemberByFirstName(String firstname) {
		Criteria criteria = getCurrentSession().createCriteria(Member.class);
		if (firstname.length()<3) {
			List<Member> temp = new ArrayList <Member>();
			return temp; 
		}
		else {
		// query for first name 
		criteria.add(Restrictions.ilike("voornaam", firstname, MatchMode.ANYWHERE));
		criteria.add(Restrictions. eq("active", true));
		criteria.setFirstResult(0);
		criteria.setMaxResults(20);
		// arrange sort on date; 
		criteria.addOrder(Order.desc("voornaam"));
		List <Member> foundMembers =  criteria.list();
		return foundMembers;
		}
	}
	
	public List<Member> findMemberByLastName(String lastname) {
		Criteria criteria = getCurrentSession().createCriteria(Member.class);
		if (lastname.length()<3) {
			List<Member> temp = new ArrayList <Member>();
			return temp; 
		}
		else {
		// query for first name 
		criteria.add(Restrictions.ilike("achternaam", lastname, MatchMode.ANYWHERE));
		criteria.add(Restrictions. eq("active", true));
		criteria.setFirstResult(0);
		criteria.setMaxResults(20);
		// arrange sort on date; 
		criteria.addOrder(Order.desc("lastname"));
		List <Member> foundMembers =  criteria.list();
		return foundMembers;
		}
	}
	
	
	
	
	public List<Member> findMemberByFirstAndLastName(String firstName, String lastName) {
	
		Criteria criteria = getCurrentSession().createCriteria(Member.class);
		if (lastName.length()<3) {
			List<Member> temp = new ArrayList <Member>();
			return temp; 
		}
		
		List <Member> foundMembers =  criteria.list();
		return foundMembers;
	}
	
	public List<Member> findMemberLocation() {
		
		
	}
	
	
    
	
}