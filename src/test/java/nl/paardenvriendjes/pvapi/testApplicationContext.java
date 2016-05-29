package nl.paardenvriendjes.pvapi;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import nl.paardenvriendjes.pvapi.domain.Trap;
import nl.paardenvriendjes.pvapi.services.TrapDao;




@ContextConfiguration("classpath:application-context.xml")
public class testApplicationContext extends AbstractTransactionalJUnit4SpringContextTests {

	private ApplicationContext ctx;
	@Autowired
	TrapDao trapDao;
	
	@Before
	public void initialize () {
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
//		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//		Session Session = sessionFactory.getCurrentSession();
	}

	
	@Test
	public void testSpringInitialization() throws Exception {
		assertNotNull(ctx);
	}

	@Test
	public void testHibernate() throws Exception {
	Trap trap1 = new Trap (1L); 
	trapDao.saveTrap(trap1);
	
	}
	
	
	}

