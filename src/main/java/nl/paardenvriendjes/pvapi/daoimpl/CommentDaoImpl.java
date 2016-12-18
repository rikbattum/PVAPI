package nl.paardenvriendjes.pvapi.daoimpl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.daoimpl.abstractdao.AbstractDaoService;
import nl.paardenvriendjes.pvapi.domain.MessageComment;

@Repository
@Transactional

public class CommentDaoImpl extends AbstractDaoService<MessageComment> {

	public CommentDaoImpl() {
		super(MessageComment.class);
	}

	static Logger log = Logger.getLogger(MessageDaoImpl.class.getName());

	@Autowired
	SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(MessageComment messageComment) {
		messageComment.setInsertDate();
		getCurrentSession().persist(messageComment);
		log.debug("saved One: " + messageComment.toString());
	}

	@Override
	public void edit(MessageComment messageComment) {
		messageComment.setInsertDate();
		getCurrentSession().merge(messageComment);
		log.debug("edit: " + messageComment.toString());
	}

}
