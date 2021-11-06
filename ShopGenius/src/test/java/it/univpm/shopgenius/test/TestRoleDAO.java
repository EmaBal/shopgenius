package it.univpm.shopgenius.test;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.univpm.shopgenius.model.dao.RoleDAO;
import it.univpm.shopgenius.model.entities.Role;

class TestRoleDAO {


	private AnnotationConfigApplicationContext ctx;
	private RoleDAO roleDAO;
	private SessionFactory sf;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		System.out.println("Prepare the test case environment");

		ctx = new AnnotationConfigApplicationContext(AppContextTest.class);
		
		roleDAO = ctx.getBean(RoleDAO.class);
		
		sf = ctx.getBean("sessionFactory", SessionFactory.class);
	}

	@AfterEach
	void tearDown() throws Exception {
		System.out.println("Clean-up the test case environment");
		
		ctx.close();
	}

	@Test
	void testBeginCommitTransaction() {
		/**
		 * Check that the dao stores the session for later use, allowing to share the
		 * session
		 */

		Session s = sf.openSession();

		assertTrue(s.isOpen());

		s.beginTransaction();

		roleDAO.setSession(s);

		assertEquals(s, roleDAO.getSession()); // s.equals(roleDAO.getSession());
		assertSame(s, roleDAO.getSession()); // s == roleDAO.getSession();
		assertTrue(roleDAO.getSession().getTransaction().isActive());

		s.getTransaction().commit();

		assertFalse(roleDAO.getSession().getTransaction().isActive());

	}
	
	@Test
	void testAutoCreationOfSession() {
		/**
		 * Check that the dao opens a new session if no session is passed
		 * (NB the returned session has transaction disabled by default)
		 * 		 
		 */
		
		Session s = roleDAO.getSession();
		assertNotNull(s);
		assertFalse(s.getTransaction().isActive());
	}

	@Test
	void testRoleMustHaveAName() {
		/**
		 * A role cannot have empty first name
		 */
		Session s = sf.openSession();
		
		assertThrows(Exception.class, () ->  { roleDAO.create(""); } );
	}
	
	
	@Test
	void testAllCreatedAreFoundByName() {

		int n = 10;

		Session s = sf.openSession();

		roleDAO.setSession(s);

		for (int i = 0; i < n; i++) {
			
			s.beginTransaction();
			Role inserted = roleDAO.create("role" + i);
			s.getTransaction().commit();
			s.beginTransaction();
			Role found = roleDAO.getRole(inserted.getName());
			s.getTransaction().commit();
			
			assertSame(inserted, found);
		}
	}
	
	@Test
	void testUserNotFoundByName() {
		
		Session s = sf.openSession();

		roleDAO.setSession(s);
		
		s.beginTransaction();
		Role inserted = roleDAO.create("role");
		s.getTransaction().commit();
		s.beginTransaction();
		try {
			Role found = roleDAO.getRole("role1");
			fail("Shudn't be able to find non existing role");
		} catch (Exception e) {
			assertTrue(true);
		}
		s.getTransaction().commit();
	}
	
	@Test
	void testRoleIsCreatedAndDeleted() {
		
		Session s = sf.openSession();

		roleDAO.setSession(s);
		
		// 1. create a role
		s.beginTransaction();
		
		Role inserted = roleDAO.create("role");
		
		s.getTransaction().commit();
		
		// 2. delete the role
		s.beginTransaction();
		
		assertEquals(inserted, roleDAO.getRole(inserted.getName()));
		
		roleDAO.delete(inserted);
		
		s.getTransaction().commit();
		
		// 3. check no more roles
		s.beginTransaction();
		try {
			roleDAO.getRole(inserted.getName());
			fail("Shoudnt be able to find deleted role");
		} catch (Exception e) {
			assertTrue(true);
		}
		s.getTransaction().commit();
	}
}
