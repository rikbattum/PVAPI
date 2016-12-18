package nl.paardenvriendjes.pvapi.daoimpl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.daoimpl.abstractdao.AbstractDaoService;
import nl.paardenvriendjes.pvapi.domain.Event;

@Repository
@Transactional

public class EventDaoImpl extends AbstractDaoService<Event>{
	
	static Logger log = Logger.getLogger(MessageDaoImpl.class.getName());
	
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	MessageDaoImpl messageservice;
	
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	
	public EventDaoImpl() {
		super(Event.class);
	}
	
	
	@Override
	public void save(Event event) {
		event.setActive(true);
		event.setCreatedOnDate();
		getCurrentSession().persist(event);
		log.debug("saved One: " + event.toString());
		// updateMessagesWithEvent(event);
	}
	
	@Override
	public void remove(Event eventToBeRemoved) {
		try {
			eventToBeRemoved.setActive(false);
			eventToBeRemoved.setDeactivatedDate();
			getCurrentSession().saveOrUpdate(eventToBeRemoved);
			log.debug("Deactivated Event " + eventToBeRemoved.toString());
		} catch (Exception e) {
			log.error("Event to be deactivated not successfull for id: " + eventToBeRemoved.getId());
		}
	}

	public void reactivate(Long id) { 
	try {
		Event eventToBeReactivated = (Event) getCurrentSession().load(Event.class, id);
		eventToBeReactivated.setActive(true);
		getCurrentSession().saveOrUpdate(eventToBeReactivated);
		log.debug("Reactivated Event" + eventToBeReactivated.toString());
	} catch (Exception e) {
		log.error("Event to be reactivated not successfull for id: " + id);
	}
}	
}
