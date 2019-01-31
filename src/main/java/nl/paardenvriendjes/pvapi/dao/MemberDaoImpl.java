package nl.paardenvriendjes.pvapi.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import nl.paardenvriendjes.pvapi.data.enums.Place;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.custom.editors.LocationTypeEditor;
import nl.paardenvriendjes.pvapi.dao.abstractdao.AbstractDaoService;
import nl.paardenvriendjes.pvapi.data.Member;

@Repository (value="memberservice")
@Transactional
@SuppressWarnings("unchecked")
@Slf4j
public class MemberDaoImpl extends AbstractDaoService<Member> {

	@Autowired
	SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public MemberDaoImpl() {
		super(Member.class);
	}

	@Override
	// free for all?	
	public Member save(Member member) {
		member.setCreatedonDate();
		member.setActive(true);
		getCurrentSession().persist(member);
		log.debug("saved One: " + member.getEmail());
		return member; 
	}
	
	@Override
	@PreAuthorize("#member.email == authentication.name and hasAnyAuthority('USER','ADMIN')")
	public Member edit(Member member) {
		getCurrentSession().update(member);
		log.debug("edit: " + member.getEmail());
		return member;
	}
	
	@Override
	@PreAuthorize("#member.email == authentication.name and hasAnyAuthority('USER','ADMIN')")
	public void remove(Member member) {
		try {
			member.setActive(false);
			member.setDeactivatedDate();
			getCurrentSession().update(member);
			log.debug("Deactivated Member" + member.getEmail());
		} catch (Exception e) {
			log.error("Member to be deactivated not successfull for id: " + member.getId());
		}
	}
	
	@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
	public List<Member> listOutOfQueryId(String[] arrayID) {		
		Query query = getCurrentSession().createQuery("from " + Member.class.getName() + " Where id IN (:arrayID)");	
		query.setParameterList("arrayID", arrayID);
		List<Member> list = query.list(); 
		return list; 
	}

	@PreAuthorize("member.email == authentication.name and hasAnyAuthority('USER','ADMIN')")
	public void reactivate(String email) { 
	try {
		Member memberToBeReactivated = (Member) getCurrentSession().load(Member.class, email);
		memberToBeReactivated.setActive(true);
		getCurrentSession().merge(memberToBeReactivated);
		log.debug("Reactivated Member" + memberToBeReactivated.getEmail());
	} catch (Exception e) {
		log.error("Member to be reactivated not successfull for id: " + email);
	}
}

	// find functions
	@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
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

	@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
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

	@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
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

	@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
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

	@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
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
	
	@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
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
	@PreAuthorize("member.email == authentication.name and hasAnyAuthority('USER','ADMIN')")
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

	@PreAuthorize("member.email == authentication.name and hasAnyAuthority('USER','ADMIN')")
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
	
	@PreAuthorize("member.email == authentication.name and hasAnyAuthority('USER','ADMIN')")
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
	
	@PreAuthorize("member.email == authentication.name and hasAnyAuthority('USER','ADMIN')")
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