package nl.paardenvriendjes.pvapi.daoimpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.domain.Message;
import nl.paardenvriendjes.pvapi.service.AbstractDaoService;

@Repository
@Transactional

public class MessageDaoImpl extends AbstractDaoService<Message> {

	static Logger log = Logger.getLogger(MessageDaoImpl.class.getName());

	@Autowired
	SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public MessageDaoImpl() {
		super(Message.class);
	}

	@Override
	public void save(Message message) {
		message.setInsertDate();
		getCurrentSession().persist(message);
		log.debug("saved One: " + message.toString());
	}

	@Override
	public void edit(Message message) {
		message.setInsertDate();
		getCurrentSession().merge(message);
		log.debug("edit: " + message.toString());
	}

	public List<Message> listAllMessagesSport(int start, int end) {

		Criteria criteria = getCurrentSession().createCriteria(Message.class);
		criteria.add(Restrictions.eq("message.lineType", "LineType.Sport"));
		criteria.setFirstResult(start);
		criteria.setMaxResults(pagegeSize);
		// arrange sort on date; 
		criteria.addOrder(Order.desc("insertDate"));
		List<Message> messageListPageX = criteria.list();
		return messageListPageX;
		log.debug("got List: " + Message.class.toString());

		// List<Message> list = getCurrentSession().createQuery("from " + Message.class).list();
		

	}

	public List<Message> listAllMessages(int start, int end) {

		List<Message> list = getCurrentSession().createQuery("from " + Message.class).list();
		log.debug("got List: " + Message.class.toString());
		return list;

	}

	public List<Message> listAllMessagesKids(int start, int end) {

		List<Message> list = getCurrentSession().createQuery("from " + Message.class).list();
		log.debug("got List: " + Message.class.toString());
		return list;

	}

	public List<Message> listAllMessagesFriends(int start, int end) {

		List<Message> list = getCurrentSession().createQuery("from " + Message.class).list();
		log.debug("got List: " + Message.class.toString());
		return list;

	}

}
