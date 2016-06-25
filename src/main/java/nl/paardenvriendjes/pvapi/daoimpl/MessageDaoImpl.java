package nl.paardenvriendjes.pvapi.daoimpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.domain.Message;

@Component
@Transactional

public class MessageDaoImpl {

	// logging

	static Logger log = Logger.getLogger(MessageDaoImpl.class.getName());

	// session Management

	@Autowired
	SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	// CRUD
	// List all Messages

	@Transactional(readOnly = true)
	public List<Message> listMessages() {
		List<Message> messages = getCurrentSession().createQuery("from Message").list();
		log.debug("got List of all Messages");
		return messages;
	}

	// Load Member by ID

	public Message loadMessage(Long id) {
		Message messageLoaded = (Message) getCurrentSession().load(Message.class, id);
		return messageLoaded;
	}

	// Save Message
	
	public void save(Message message) {	
		getCurrentSession().persist(message);
		log.debug("saved or updated Message");
	}
	
	// Update Message
	
	public void updateMessage(Message message) {
		getCurrentSession().persist(message);
		log.debug("updated Message");
	}

	// Remove Message by ID
	
	public void removeMessage(long id) {
		try { 
		Message messageToBeRemoved = (Message) getCurrentSession().load(Message.class, id);
		getCurrentSession().delete(messageToBeRemoved);
		
		log.debug("deleted Message id " + id);
		}
		catch (Exception e) { 
			log.error("Message to be deleted not found");
		} 
	}
}
