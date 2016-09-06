package nl.paardenvriendjes.pvapi.daoimpl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.domain.Event;
import nl.paardenvriendjes.pvapi.service.AbstractDaoService;

@Repository
@Transactional

public class EventDaoImpl extends AbstractDaoService<Event>{
	
	static Logger log = Logger.getLogger(MessageDaoImpl.class.getName());
	
	@Autowired
	SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	
	public EventDaoImpl() {
		super(Event.class);
	}
	
	
	@Override
	public void save(Event event) {
		event.setCreatedOnDate();
		getCurrentSession().persist(event);
		log.debug("saved One: " + event.toString());
	}
	
	
	
}
