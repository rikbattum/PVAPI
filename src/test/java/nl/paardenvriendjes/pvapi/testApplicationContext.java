package nl.paardenvriendjes.pvapi;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;


@ContextConfiguration("classpath:application-context.xml")
public class testApplicationContext extends AbstractTransactionalJUnit4SpringContextTests {
	
	private EntityManager em;

	@Before
	public void initialize () {
	String persistenceUnit = "pu";
	EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
	em = emf.createEntityManager();
	}

	
	@Test
	public void testJPA() throws Exception {
		assertNotNull(em);
	}
	
	@Test 
	public void testNotNull() { 
		assertNotNull(null);
	}
		
	}

