package nl.paardenvriendjes.pvapi.daoimpl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.daoimpl.abstractdao.AbstractDaoService;
import nl.paardenvriendjes.pvapi.domain.Comment;

@Repository
@Transactional

public class CommentDaoImpl extends AbstractDaoService<Comment> {

	public CommentDaoImpl() {
		super(Comment.class);
	}

	static Logger log = Logger.getLogger(MessageDaoImpl.class.getName());

	@Autowired
	SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(Comment comment) {
		comment.setInsertDate();
		getCurrentSession().persist(comment);
		log.debug("saved One: " + comment.toString());
	}

	@Override
	public void edit(Comment comment) {
		comment.setInsertDate();
		getCurrentSession().merge(comment);
		log.debug("edit: " + comment.toString());
	}

}
