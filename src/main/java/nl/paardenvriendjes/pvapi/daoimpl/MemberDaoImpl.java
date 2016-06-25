package nl.paardenvriendjes.pvapi.daoimpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.services.AbstractDaoService;

@Component
@Transactional

public class MemberDaoImpl extends AbstractDaoService<Member> {

	public MemberDaoImpl() {
		super(Member.class);
	}

	// logging

	static Logger log = Logger.getLogger(MemberDaoImpl.class.getName());

	// session Management

	@Autowired
	SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	// CRUD
	// List all Members

	@Transactional(readOnly = true)
	public List<Member> listAll() {
		return super.listAll();
	}

	// Load Member by ID

	public Member listOne(Long id) {
		return super.listOne(id);
	}

	// Save Member

	public void save(Member member) {
		super.save(member);
	}

	// Update Member

	public void edit(Member member) {
		super.edit(member);
	}

	// Remove Member by ID

	public void remove(long id) {
		super.remove(id);
	}
}
