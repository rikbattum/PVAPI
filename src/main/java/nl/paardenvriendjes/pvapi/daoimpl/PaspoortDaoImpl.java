package nl.paardenvriendjes.pvapi.daoimpl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.domain.Paspoort;
import nl.paardenvriendjes.pvapi.service.AbstractDaoService;

@Repository
@Transactional

public class PaspoortDaoImpl extends AbstractDaoService<Paspoort>{
	
	
	static Logger log = Logger.getLogger(MessageDaoImpl.class.getName());
	
	@Autowired
	SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public PaspoortDaoImpl() {
		super(Paspoort.class);
	}
	
	@Override
	public void save(Paspoort paspoort) {
		paspoort.setCreatedOn();
		getCurrentSession().persist(paspoort);
		log.debug("saved One: " + paspoort.toString());
	}
	
}
