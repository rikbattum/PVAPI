package nl.paardenvriendjes.pvapi.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.dao.abstractdao.AbstractDaoService;
import nl.paardenvriendjes.pvapi.data.MessageComment;

@Repository
@Transactional

public class MessageCommentDaoImpl extends AbstractDaoService<MessageComment> {

	public MessageCommentDaoImpl() {
		super(MessageComment.class);
	}

	static Logger log = Logger.getLogger(MessageDaoImpl.class.getName());

	@Autowired
	SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public MessageComment save(MessageComment messageComment) {
		messageComment.setInsertDate();
		getCurrentSession().persist(messageComment);
		log.debug("saved One: " + messageComment.toString());
		return messageComment;
	}

	@Override
	public MessageComment edit(MessageComment messageComment) {
		messageComment.setInsertDate();
		getCurrentSession().merge(messageComment);
		log.debug("edit: " + messageComment.toString());
		return messageComment;
	}

}
