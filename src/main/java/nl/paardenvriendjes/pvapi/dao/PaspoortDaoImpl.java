package nl.paardenvriendjes.pvapi.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.dao.abstractdao.AbstractDaoService;
import nl.paardenvriendjes.pvapi.data.Paspoort;

import java.util.Date;

@Repository
@Transactional
@Slf4j
public class PaspoortDaoImpl extends AbstractDaoService<Paspoort> {

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
			paspoortToBeRemoved.setDeactivatedDate(new Date());
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
