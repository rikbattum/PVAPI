package nl.paardenvriendjes.pvapi.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import nl.paardenvriendjes.pvapi.daoimpl.MessageDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Member;

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

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public List<T> listAll() {
		List<T> list = getCurrentSession().createQuery("from " + entityClass.getName()).list();
		log.debug("got List: " + entityClass.toString());
		return list;
	}

	public T listOne(Long id) {
		T objectLoaded = (T) getCurrentSession().load(entityClass, id);
		log.debug("got One: " + entityClass.toString());
		return objectLoaded;
	}

	public void save(T entity) {
		getCurrentSession().persist(entity);
		log.debug("saved One: " + entityClass.toString());
	}

	public void edit(T entity) {
		getCurrentSession().merge(entity);
		log.debug("edit: " + entityClass.toString());
	}

	public void remove(Long id) {
		try {
			T ObjectToBeRemoved = (T) getCurrentSession().load(entityClass, id);
			getCurrentSession().delete(ObjectToBeRemoved);
			log.debug("deleted " + entityClass.toString());
		} catch (Exception e) {
			log.error("Object to be deleted not found " + entityClass.toString());
		}
	}

	public List<T> listRange(int[] range) {
		Query query = getCurrentSession().createQuery("from " + entityClass.getName());	
		query.setMaxResults((range[1] - range[0] + 1));
		query.setFirstResult(range[0]);
		List<T> list = query.list(); 
		return list; 
	}
	
	public int count() {
		Query query = getCurrentSession().createQuery("from " + entityClass.getName());
		int count = (int)query.list().size();
		return count;
	}
}
