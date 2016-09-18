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

import nl.paardenvriendjes.pvapi.domain.Horse;
import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.service.AbstractDaoService;

@Repository
@Transactional
public class HorseDaoImpl extends AbstractDaoService<Horse> {

	static Logger log = Logger.getLogger(MessageDaoImpl.class.getName());
	
	@Autowired
	SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	
	public HorseDaoImpl() {
		super(Horse.class);
	}	
	
	public List<Horse> findHorseByName(String name) {
		
		Criteria criteria = getCurrentSession().createCriteria(Horse.class);
		if (name.length()<3) {
			List<Horse> temp = new ArrayList <Horse>();
			return temp; 
		}
		else {
			// query for first name 
			criteria.add(Restrictions.ilike("naam", name, MatchMode.ANYWHERE));
			criteria.add(Restrictions. eq("active", true));
			criteria.setFirstResult(0);
			criteria.setMaxResults(20);
			// arrange sort on date; 
			criteria.addOrder(Order.desc("lastname"));
			List <Horse> foundHorses =  criteria.list();
			return foundHorses;
		}
	}
	
	public List<Member> findHorseByLocation(String location) {
		Criteria criteria = getCurrentSession().createCriteria(Member.class);
		if (location.length()<3) {
			
			List<Member> temp = new ArrayList <Member>();
			return temp; 
		}
		criteria.add(Restrictions.ilike("plaatsnaam", location, MatchMode.ANYWHERE));
		criteria.add(Restrictions. eq("active", true));
		criteria.setFirstResult(0);
		criteria.setMaxResults(20);
		// arrange sort on lastname; 
		criteria.addOrder(Order.desc("lastname"));
		List <Member> foundMembers =  criteria.list();
		return foundMembers;
	}	
	
//	public List<Member> findMemberBySportLevel(SportLevel sportlevel) {
//		Criteria criteria = getCurrentSession().createCriteria(Member.class);
//		if (location.length()<3) {
//			
//			List<Member> temp = new ArrayList <Member>();
//			return temp; 
//		}
//		criteria.add(Restrictions.ilike("plaatsnaam", location, MatchMode.ANYWHERE));
//		criteria.add(Restrictions. eq("active", true));
//		criteria.setFirstResult(0);
//		criteria.setMaxResults(20);
//		// arrange sort on lastname; 
//		criteria.addOrder(Order.desc("lastname"));
//		List <Member> foundMembers =  criteria.list();
//		return foundMembers;
//	}	
	
	
	
	
	
	
	
	
}
