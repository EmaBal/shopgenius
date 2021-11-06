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

import it.univpm.shopgenius.model.dao.FavoriteDAO;
import it.univpm.shopgenius.model.entities.Product;
import it.univpm.shopgenius.model.entities.User;

class TestFavoriteDAO {

	private AnnotationConfigApplicationContext ctx;
	private FavoriteDAO favoriteDAO;
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
		
		favoriteDAO = ctx.getBean(FavoriteDAO.class);
		
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

		favoriteDAO.setSession(s);

		assertEquals(s, favoriteDAO.getSession()); // s.equals(favoriteDAO.getSession());
		assertSame(s, favoriteDAO.getSession()); // s == favoriteDAO.getSession();
		assertTrue(favoriteDAO.getSession().getTransaction().isActive());

		s.getTransaction().commit();

		assertFalse(favoriteDAO.getSession().getTransaction().isActive());

	}
	
	@Test
	void testAutoCreationOfSession() {
		/**
		 * Check that the dao opens a new session if no session is passed
		 * (NB the returned session has transaction disabled by default)
		 * 		 
		 */
		
		Session s = favoriteDAO.getSession();
		assertNotNull(s);
		assertFalse(s.getTransaction().isActive());
	}

	@Test
	void testAddProductToUser() {
		
		Session s = sf.openSession();

		favoriteDAO.setSession(s);

		
		s.beginTransaction();
		User user = new User();
		user.setFirstName("firstname");
		user.setLastName("lastname");
		user.setEmail("email");
		user.setPassword("password");
		user.setEnabled(true);
		
		Product product = new Product();
		product.setName("prod");
		product.setPrice(1);
		product.setQuantity(1);
		product.setLocationDetail("A1");

		try {
			favoriteDAO.add(user,product);
			s.getTransaction().commit();
			assertTrue(true);
		} catch (Exception e) {
			fail("Unexpected exception adding favorite product to user: " + e.getMessage());
		}
		
		assertEquals(user.getProducts().size(), 1);
		assertTrue(user.getProducts().contains(product));
	}
	
	@Test
	void testRemoveProductFromUser() {
		
		Session s = sf.openSession();

		favoriteDAO.setSession(s);

		s.beginTransaction();
		User user = new User();
		user.setFirstName("firstname");
		user.setLastName("lastname");
		user.setEmail("email");
		user.setPassword("password");
		user.setEnabled(true);
		
		Product product = new Product();
		product.setName("prod");
		product.setPrice(1);
		product.setQuantity(1);
		product.setLocationDetail("A1");
		
		favoriteDAO.add(user,product);

		try {
			favoriteDAO.delete(user,product);
			s.getTransaction().commit();
			assertTrue(true);
		} catch (Exception e) {
			fail("Unexpected exception removing favorite product from user: " + e.getMessage());
		}
		
		assertEquals(user.getProducts().size(), 0);
		assertFalse(user.getProducts().contains(product));
	}
	
	@Test
	void testRemoveNonExistingFavoriteDoesntCauseError() {
		
		Session s = sf.openSession();

		favoriteDAO.setSession(s);
		
		
		s.beginTransaction();
		User user = new User();
		user.setFirstName("firstname");
		user.setLastName("lastname");
		user.setEmail("email");
		user.setPassword("password");
		user.setEnabled(true);
		
		Product product = new Product();
		product.setName("prod");
		product.setPrice(1);
		product.setQuantity(1);
		product.setLocationDetail("A1");

		try {
			favoriteDAO.delete(user,product);
			s.getTransaction().commit();
			
			assertTrue(true);
		} catch (Exception e) {
			fail("Unexpected exception removing non exising favorite: " + e.getMessage());
		}
	}
	
	@Test
	void testAddProductToUserTwiceDoesntCauseError() {

		Session s = sf.openSession();
		
		favoriteDAO.setSession(s);

		
		s.beginTransaction();
		User user = new User();
		user.setFirstName("firstname");
		user.setLastName("lastname");
		user.setEmail("email");
		user.setPassword("password");
		user.setEnabled(true);
		
		Product product = new Product();
		product.setName("prod");
		product.setPrice(1);
		product.setQuantity(1);
		product.setLocationDetail("A1");
		
		favoriteDAO.add(user,product);

		s.getTransaction().commit();
		s.beginTransaction();
		
		try {
			favoriteDAO.add(user,product);
			s.getTransaction().commit();
			assertTrue(true);
		} catch (Exception e) {
			fail("Unexpected exception adding favorite product to user twice: " + e.getMessage());
		}
		
		assertEquals(user.getProducts().size(), 1);
		assertTrue(user.getProducts().contains(product));
	}
}
