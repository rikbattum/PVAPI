package nl.paardenvriendjes.pvapi.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.dao.abstractdao.AbstractDaoService;
import nl.paardenvriendjes.pvapi.data.Member;
import nl.paardenvriendjes.pvapi.data.Message;
import nl.paardenvriendjes.pvapi.data.enums.LineType;

@Repository
@Transactional
@Slf4j
@SuppressWarnings("unchecked")
public class MessageDaoImpl extends AbstractDaoService<Message> {

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
	public Message save(Message message) {
		message.setInsertDate(new Date());
		getCurrentSession().persist(message);
		log.debug("saved One: " + message.toString());
				return message;
				
	}

	@Override
	public Message edit(Message message) {		
		message.setInsertDate(new Date());
		getCurrentSession().update(message);
		log.debug("edit: " + message.toString());
		return message;
	}
	
	@Override
	public void remove(Message message) {
		try { 
			getCurrentSession().delete(message);	
			log.debug("deleted " + Message.class.toString());
		} catch (Exception e) {
			log.error("Object to be deleted not found " + Message.class.toString());
		}
	}

	public List<Message> listAllMessagesSport(int start, int end) {

		Criteria criteria = getCurrentSession().createCriteria(Message.class);
		// set message type selection
		criteria.add(Restrictions.eq("lineType", LineType.SPORT));
		// get 3 week period
		criteria.add(Restrictions.between("insertDate", getTimeLineLapse(21), new Date()));
		// set pages
		criteria.setFirstResult(start);
		criteria.setMaxResults(pageSize);
		// cacheble query
		// criteria.setCacheable(true);
		// arrange sort on date;
		criteria.addOrder(Order.desc("insertDate"));
		List<Message> messageListPageX = criteria.list();
		log.debug("messageis" + messageListPageX.get(0).getMessage());
		log.debug("got List: " + Message.class.toString());
		return messageListPageX;
	}

	public List<Message> listAllMessages(int start, int end) {

		Criteria criteria = getCurrentSession().createCriteria(Message.class);
		// set message type selection
		criteria.add(Restrictions.eq("lineType", LineType.GENERAL));
		// get 2 week period
		criteria.add(Restrictions.between("insertDate", getTimeLineLapse(14), new Date()));
		// set pages
		criteria.setFirstResult(start);
		criteria.setMaxResults(pageSize);
		// cacheble query
		// criteria.setCacheable(true);
		// arrange sort on date;
		criteria.addOrder(Order.desc("insertDate"));
		List<Message> messageListPageX = criteria.list();
		log.debug("got List: " + Message.class.toString());
		return messageListPageX;
	}

	public List<Message> listAllMessagesKids(int start, int end) {

		Criteria criteria = getCurrentSession().createCriteria(Message.class);
		// set message type selection
		criteria.add(Restrictions.eq("lineType", LineType.KIDS));
		// get 3 week period
		criteria.add(Restrictions.between("insertDate", getTimeLineLapse(21), new Date()));
		// set pages
		criteria.setFirstResult(start);
		criteria.setMaxResults(pageSize);
		// cacheble query
		// criteria.setCacheable(true);
		// arrange sort on date;
		criteria.addOrder(Order.desc("insertDate"));
		List<Message> messageListPageX = criteria.list();
		log.debug("got List: " + Message.class.toString());
		return messageListPageX;
	}

	public List<Message> listAllMessagesFriends(int start, int end, Member x) {

		Criteria criteria = getCurrentSession().createCriteria(Message.class);
		// get 3 week period
		criteria.add(Restrictions.between("insertDate", getTimeLineLapse(21), new Date()));
		// get messages of friends only
		criteria.add(Restrictions.in("member", x.getVrienden()));
		// set pages
		criteria.setFirstResult(start);
		criteria.setMaxResults(pageSize);
		// cacheble query
		// criteria.setCacheable(true);
		// arrange sort on date;
		criteria.addOrder(Order.desc("insertDate"));
		List<Message> messageListPageX = criteria.list();
		log.debug("got List: " + Message.class.toString());
		return messageListPageX;
	}

	// convenience method to retrieve days before Sys-date;
	public Date getTimeLineLapse(int amountOfDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -amountOfDays);
		Date dateBeforeXDays = cal.getTime();
		return dateBeforeXDays;
	}
}
