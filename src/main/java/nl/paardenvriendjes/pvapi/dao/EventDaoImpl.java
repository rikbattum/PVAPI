package nl.paardenvriendjes.pvapi.dao;

import lombok.extern.slf4j.Slf4j;
import nl.paardenvriendjes.pvapi.dao.abstractdao.AbstractDaoService;
import nl.paardenvriendjes.pvapi.data.Event;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Slf4j
public class EventDaoImpl extends AbstractDaoService<Event> {

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
    public Event save(Event event) {
        event.setActive(true);
        event.setCreatedOnDate();
        getCurrentSession().persist(event);
        log.debug("saved One: " + event.toString());
        // updateMessagesWithEvent(event);
        return event;
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
