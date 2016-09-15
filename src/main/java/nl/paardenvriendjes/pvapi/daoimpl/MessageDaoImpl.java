package nl.paardenvriendjes.pvapi.daoimpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.domain.Message;
import nl.paardenvriendjes.pvapi.service.AbstractDaoService;

@Repository
@Transactional

@SuppressWarnings("unchecked")
public class MessageDaoImpl extends AbstractDaoService<Message> {

	static Logger log = Logger.getLogger(MessageDaoImpl.class.getName());
	public int pageSize = 8;

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
		// set message type selection
		criteria.add(Restrictions.eq("message.lineType", "LineType.Sport"));
		//get 4 week period
		criteria.add(Restrictions.between("insertDate", getTimeLineLapse (), new Date()));
		// set pages 
		criteria.setFirstResult(start);
		criteria.setMaxResults(pageSize);
		// arrange sort on date; 
		criteria.addOrder(Order.desc("insertDate"));
		List <Message> messageListPageX =  criteria.list();
		log.debug("got List: " + Message.class.toString());
		return messageListPageX;
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

	// convenience method to retrieve 3 weeks before Sys-date;
	public Date getTimeLineLapse () { 
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -28);
		Date dateBefore21Days = cal.getTime();
		return dateBefore21Days;
	}

}
