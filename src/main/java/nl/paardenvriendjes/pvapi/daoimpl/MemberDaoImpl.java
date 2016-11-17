package nl.paardenvriendjes.pvapi.daoimpl;

import java.util.ArrayList;
import java.util.Arrays;
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

import nl.paardenvriendjes.custom.editors.LocationTypeEditor;
import nl.paardenvriendjes.pvapi.daoimpl.abstractdao.AbstractDaoService;
import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.enumerations.Place;

@Repository (value="memberservice")
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
	
	@Override
	public void remove(Member memberToBeRemoved) {
		try {
			memberToBeRemoved.setActive(false);
			memberToBeRemoved.setDeactivatedDate();
			getCurrentSession().merge(memberToBeRemoved);
			log.debug("Deactivated Horse " + memberToBeRemoved.toString());
		} catch (Exception e) {
			log.error("Member to be deactivated not successfull for id: " + memberToBeRemoved.getId());
		}
	}

	public void reactivate(Long id) { 
	try {
		Member memberToBeReactivated = (Member) getCurrentSession().load(Member.class, id);
		memberToBeReactivated.setActive(true);
		getCurrentSession().merge(memberToBeReactivated);
		log.debug("Reactivated Member" + memberToBeReactivated.toString());
	} catch (Exception e) {
		log.error("Member to be reactivated not successfull for id: " + id);
	}
}

	// find functions
	
	public List<Member> findMemberByFirstName(String firstname) {
		Criteria criteria = getCurrentSession().createCriteria(Member.class);
		if (firstname.length() < 3) {
			List<Member> temp = new ArrayList<Member>();
			return temp;
		} else {
			// query for first name
			criteria.add(Restrictions.ilike("voornaam", firstname, MatchMode.ANYWHERE));
			criteria.add(Restrictions.eq("active", true));
			criteria.setFirstResult(0);
			criteria.setMaxResults(20);
			// arrange sort on date;
			criteria.addOrder(Order.desc("voornaam"));
			List<Member> foundMembers = criteria.list();
			return foundMembers;
		}
	}

	public List<Member> findMemberByLastName(String lastname) {
		Criteria criteria = getCurrentSession().createCriteria(Member.class);
		if (lastname.length() < 3) {
			List<Member> temp = new ArrayList<Member>();
			return temp;
		} else {
			// query for first name
			criteria.add(Restrictions.ilike("achternaam", lastname, MatchMode.ANYWHERE));
			criteria.add(Restrictions.eq("active", true));
			criteria.setFirstResult(0);
			criteria.setMaxResults(20);
			// arrange sort on date;
			criteria.addOrder(Order.desc("achternaam"));
			List<Member> foundMembers = criteria.list();
			return foundMembers;
		}
	}

	public List<Member> findMemberByFirstAndLastName(String firstname, String lastname) {

		Criteria criteria = getCurrentSession().createCriteria(Member.class);
		if (lastname.length() < 3) {
			List<Member> temp = new ArrayList<Member>();
			return temp;
		} else {
			// query for first and last name
			criteria.add(Restrictions.ilike("voornaam", firstname, MatchMode.ANYWHERE));
			criteria.add(Restrictions.ilike("achternaam", lastname, MatchMode.ANYWHERE));
			criteria.add(Restrictions.eq("active", true));
			criteria.setFirstResult(0);
			criteria.setMaxResults(20);
			// arrange sort on date;
			criteria.addOrder(Order.desc("achternaam"));
			List<Member> foundMembers = criteria.list();
			return foundMembers;
		}
	}

	public List<Member> findMemberByLocation(String location) {
		if (location.length() < 3) {

			List<Member> temp = new ArrayList<Member>();
			return temp;
		} else {
			LocationTypeEditor editor = new LocationTypeEditor();
			Place place =  editor.returnAsPlace(location);
			Criteria criteria = getCurrentSession().createCriteria(Member.class);
			criteria.add(Restrictions.eq("place", place));
			criteria.add(Restrictions.eq("active", true));
			criteria.setFirstResult(0);
			criteria.setMaxResults(20);
			// arrange sort on lastname;
			criteria.addOrder(Order.desc("achternaam"));
			List<Member> foundMembers = criteria.list();
			return foundMembers;
		}
	}

	public List<Member> findMemberByInteresse(String interesse) {
		Criteria criteria = getCurrentSession().createCriteria(Member.class);
		if (interesse.length()< 2) {

			List<Member> temp = new ArrayList<Member>();
			return temp;
		}
		criteria.add(Restrictions.eq("interesse."+interesse, true));
		criteria.add(Restrictions.eq("active", true));
		criteria.setFirstResult(0);
		criteria.setMaxResults(50);
		// arrange sort on lastname;
		criteria.addOrder(Order.desc("achternaam"));
		List<Member> foundMembers = criteria.list();
		return foundMembers;
	}
	
	public List<Member> findMemberBySportType(String sporttype) {
		Criteria criteria = getCurrentSession().createCriteria(Member.class, "memb");
		if (sporttype.length()< 2) {

			List<Member> temp = new ArrayList<Member>();
			return temp;
		}		
		criteria.createAlias("memb.sports", "sports");
		criteria.add(Restrictions.eq("sports.indices", sporttype));
		criteria.add(Restrictions.eq("active", true));
		criteria.setFirstResult(0);
		criteria.setMaxResults(50);
		// arrange sort on lastname;
		criteria.addOrder(Order.desc("achternaam"));
		List<Member> foundMembers = criteria.list();
		return foundMembers;
	}
	
	// friend functions
	
	public void addFriend (Member member, Member toBeFollowedMember) {
		
		if (Arrays.asList(member.getBlokkades()).contains(toBeFollowedMember)) { 
		 throw new IllegalArgumentException("Sorry this member does not want to be followed");
		}
		else if (Arrays.asList(member.getVrienden()).contains(toBeFollowedMember)) {
		throw new IllegalArgumentException("Sorry this member is already your friend");
		}
		else {
		member.addOrUpdateFriend(toBeFollowedMember);
		getCurrentSession().merge(member);
		log.debug("Added a friend with Id: " + toBeFollowedMember.getId() + " to Member: " + member.getId());
		}
	}

	public void removeFriend (Member member, Member toBeRemovedFriend) throws IllegalArgumentException {
		
		if (!Arrays.asList(member.getVrienden()).contains(toBeRemovedFriend)) { 
		 throw new IllegalArgumentException("Sorry this member is not a Friend");
		}
		else {
		member.removeFriend(toBeRemovedFriend);
		getCurrentSession().merge(member);
		log.debug("Removed a friend with Id: " + toBeRemovedFriend.getId() + " from Member: " + member.getId());
		}
	}
	
	public void addBlock (Member member, Member toBeBlockedMember) throws IllegalArgumentException {
				
		if (Arrays.asList(member.getBlokkades()).contains(toBeBlockedMember)) { 
		 throw new IllegalArgumentException("Sorry this member is already blocked");
		}
		else {
		member.addOrUpdateBlokkade(toBeBlockedMember);
		getCurrentSession().merge(member);;
		log.debug("Added a blocked member with Id: " + toBeBlockedMember.getId() + "to Member: " + member.getId());
		}
	}	
	
	public void removeBlock (Member member, Member toBeUnBlockedMember) throws IllegalArgumentException {
				
		if (!Arrays.asList(member.getBlokkades()).contains(toBeUnBlockedMember)) { 
		throw new IllegalArgumentException("Sorry this member is not blocked, so unblocking not possible");
		}
		
		else if (Arrays.asList(member.getBlokkades()).contains(toBeUnBlockedMember)) { 
		member.removeBlokkade(toBeUnBlockedMember);
		getCurrentSession().merge(member);
		log.debug("Added a vriend with Id: " + toBeUnBlockedMember.getId() + "to Member: " + member.getId());
		}
	}		
	
// Convenience Methods for sportsmap
	
	//public void updateHorseSports(String sporttype, String sportlevel) {
//	
//	for (String type : SportType.values()) {
//	if (type == sporttype) { 
//	SportType selectedSportType = type; 	
//	}
//	}
//	for (SportLevel level : SportLevel.values()) { 
//	if (level == sportlevel) { 
//	SportLevel selectedSportLevel = level; 	
//	}	
//	}
//	if (selectedSportLevel == null) { 
//	throw IllegalArgumentException () ; 	
//	}
//	else { 
//	addSportToMap(selectedSportType, selectedSportLevel);
//	}
//}
//
//public void deleteHorseSports(String sportype, String sportlevel) {
	
//	for (SportType type : SportType.values()) {
//	if (type == sporttype) { 
//	SportType selectedSportType = type; 	
//	}
//	if (selectedSportType == null | selected Level == null) { 
//	throw IllegalArgumentException () ; 	
//	}
//	else { 
//	removeSportFromMap(selectedSportType);
//	}
//}
}