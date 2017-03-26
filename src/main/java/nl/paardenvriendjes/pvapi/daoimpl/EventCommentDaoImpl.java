package nl.paardenvriendjes.pvapi.daoimpl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.daoimpl.abstractdao.AbstractDaoService;
import nl.paardenvriendjes.pvapi.domain.EventComment;

@Repository
@Transactional

public class EventCommentDaoImpl extends AbstractDaoService<EventComment> {

	public EventCommentDaoImpl() {
		super(EventComment.class);
	}

	static Logger log = Logger.getLogger(MessageDaoImpl.class.getName());

	@Autowired
	SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public EventComment save(EventComment EventComment) {
		EventComment.setInsertDate();
		getCurrentSession().persist(EventComment);
		log.debug("saved One: " + EventComment.toString());
		return EventComment;
	}

	@Override
	public EventComment edit(EventComment EventComment) {
		EventComment.setInsertDate();
		getCurrentSession().update(EventComment);
		log.debug("edit: " + EventComment.toString());
		return EventComment;
	}

}
