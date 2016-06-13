package nl.paardenvriendjes.pvapi.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import nl.paardenvriendjes.pvapi.domain.Trap;
import nl.paardenvriendjes.pvapi.services.TrapDao;

@Component
@Transactional()
public class TrapDaoImpl implements TrapDao {

@Autowired
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