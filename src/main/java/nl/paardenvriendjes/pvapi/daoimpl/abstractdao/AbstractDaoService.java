package nl.paardenvriendjes.pvapi.daoimpl.abstractdao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.daoimpl.MessageDaoImpl;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public abstract class AbstractDaoService<T> {

	// use generic to determine entity class
	private Class<T> entityClass;

	public AbstractDaoService(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	// logging

	static Logger log = Logger.getLogger(MessageDaoImpl.class.getName());

	// session Management

	@Autowired
	SessionFactory sessionFactory;

	@PersistenceContext
	private EntityManager entityManager;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
	public List<T> listAll() {
		List<T> list = getCurrentSession().createQuery("from " + entityClass.getName()).list();
		log.debug("got List: " + entityClass.toString());
		return list;
	}

	@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
	public T listOne(Long id) {
		T objectLoaded = (T) getCurrentSession().get(entityClass, id);
		log.debug("got One: " + entityClass.toString());
		return objectLoaded;
	}

	public T save(T entity) {
		getCurrentSession().persist(entity);
		log.debug("saved One: " + entityClass.toString());
		return entity;
	}

	@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
	public T edit(T entity) {
		getCurrentSession().update(entity);
		log.debug("edit: " + entityClass.toString());
		return entity;
	}

	@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
	public void remove(T entity) {
		try {
			getCurrentSession().delete(entity);
			log.debug("deleted " + entityClass.toString());
		} catch (Exception e) {
			log.error("Object to be deleted not found " + entityClass.toString());
		}
	}

	@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
	public List<T> listRange(int[] range) {
		Query query = getCurrentSession().createQuery("from " + entityClass.getName());
		query.setMaxResults((range[1] - range[0] + 1));
		query.setFirstResult(range[0]);
		List<T> list = query.list();
		return list;
	}

	@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
	public List<T> listOutOfQueryId(Long[] arrayID) {
		Query query = getCurrentSession().createQuery("from " + entityClass.getName() + " Where id IN (:arrayID)");
		query.setParameterList("arrayID", arrayID);
		List<T> list = query.list();
		return list;
	}

	@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
	public int count() {
		Query query = getCurrentSession().createQuery("from " + entityClass.getName());
		int count = (int) query.list().size();
		return count;
	}
}
