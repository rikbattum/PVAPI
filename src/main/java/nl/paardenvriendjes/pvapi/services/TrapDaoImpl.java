package nl.paardenvriendjes.pvapi.services;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import nl.paardenvriendjes.pvapi.domain.Trap;

@Component
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class TrapDaoImpl implements TrapDao {

private SessionFactory sessionFactory;
	
	@Transactional(readOnly = true)
	public List<Trap> listTrappen() {
		Session session = sessionFactory.getCurrentSession();
		List <Trap> trappen = session.createQuery("from Trap").list();
		return trappen;
	}

	public void saveTrap(Trap trap) {
		Session session = sessionFactory.getCurrentSession();
		session.save(trap);
	}

	public void removeTrap(long id) {
		// TODO Auto-generated method stub
		
	}
}
