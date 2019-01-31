package nl.paardenvriendjes.pvapi.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.dao.abstractdao.AbstractDaoService;
import nl.paardenvriendjes.pvapi.data.EventComment;

@Repository
@Transactional
@Slf4j
public class EventCommentDaoImpl extends AbstractDaoService<EventComment> {

	public EventCommentDaoImpl() {
		super(EventComment.class);
	}

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
