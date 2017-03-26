package nl.paardenvriendjes.pvapi.daoimpl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.daoimpl.abstractdao.AbstractDaoService;
import nl.paardenvriendjes.pvapi.domain.Paspoort;

@Repository
@Transactional

public class PaspoortDaoImpl extends AbstractDaoService<Paspoort> {

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
	public Paspoort save(Paspoort paspoort) {
		paspoort.setCreatedOn();
		paspoort.setActive(true);
		getCurrentSession().persist(paspoort);
		log.debug("saved One: " + paspoort.toString());
		return paspoort;
	}

	@Override
	public void remove(Paspoort paspoortToBeRemoved) {
		try {
			paspoortToBeRemoved.setActive(false);
			paspoortToBeRemoved.setDeactivatedDate();
			getCurrentSession().saveOrUpdate(paspoortToBeRemoved);
			log.debug("Deactivated Paspoort " + paspoortToBeRemoved.toString());
		} catch (Exception e) {
			log.error("Paspoort to be deactivated not successfull for id: " + paspoortToBeRemoved.getId());
		}
	}

	public void reactivate(Long id) {
		try {
			Paspoort paspoortToBeReactivated = (Paspoort) getCurrentSession().load(Paspoort.class, id);
			paspoortToBeReactivated.setActive(true);
			getCurrentSession().saveOrUpdate(paspoortToBeReactivated);
			log.debug("Reactivated Paspoort" + paspoortToBeReactivated.toString());
		} catch (Exception e) {
			log.error("Paspoort to be reactivated not successfull for id: " + id);
		}
	}

}
