package it.univpm.shopgenius.test;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.univpm.shopgenius.model.dao.ProductTypeDAO;
import it.univpm.shopgenius.model.entities.Product;
import it.univpm.shopgenius.model.entities.ProductType;

class TestProductTypeDAO {


	private AnnotationConfigApplicationContext ctx;
	private ProductTypeDAO productTypeDAO;
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
		
		productTypeDAO = ctx.getBean(ProductTypeDAO.class);
		
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

		productTypeDAO.setSession(s);

		assertEquals(s, productTypeDAO.getSession()); // s.equals(productTypeDAO.getSession());
		assertSame(s, productTypeDAO.getSession()); // s == productTypeDAO.getSession();
		assertTrue(productTypeDAO.getSession().getTransaction().isActive());

		s.getTransaction().commit();

		assertFalse(productTypeDAO.getSession().getTransaction().isActive());

	}
	
	@Test
	void testAutoCreationOfSession() {
		/**
		 * Check that the dao opens a new session if no session is passed
		 * (NB the returned session has transaction disabled by default)
		 * 		 
		 */
		
		Session s = productTypeDAO.getSession();
		assertNotNull(s);
		assertFalse(s.getTransaction().isActive());
	}
	
	@Test
	void testNoProductsAtBeginning() {
		/**
		 * Check that there are no users when the application loads
		 */

		Session s = sf.openSession();

		productTypeDAO.setSession(s);

		
		s.beginTransaction();
		List<ProductType> allProductTypes = productTypeDAO.getTypes();
		s.getTransaction().commit();
		
		assertEquals(allProductTypes.size(), 0);
	}
	
	@Test
	void testAllCreatedAreFound() {
		/**
		 * Generate n users, find all of them
		 */
		int n = 10;

		Session s = sf.openSession();

		productTypeDAO.setSession(s);

		for (int i = 0; i < n; i++) {
			
			s.beginTransaction();
			productTypeDAO.create("ptype" + i);
			s.getTransaction().commit();
			s.beginTransaction();
			List<ProductType> allProductTypes = productTypeDAO.getTypes();
			s.getTransaction().commit();
			
			assertEquals(allProductTypes.size(), i + 1);
		}
	}

	@Test
	void testProductMustHaveAName() {
		/**
		 * A user cannot have empty first name
		 */
		Session s = sf.openSession();
		
		assertThrows(Exception.class, () ->  { productTypeDAO.create(""); } );
	}
	
	@Test
	void testAllCreatedAreFoundByName() {
		/**
		 * Generate n users, find all of them by ID, check the found user is the same instance that was 
		 * returned by the productTypeDAO.create(...) method
		 */
		int n = 10;

		Session s = sf.openSession();

		productTypeDAO.setSession(s);

		for (int i = 0; i < n; i++) {
			
			s.beginTransaction();
			ProductType inserted = productTypeDAO.create("ptype" + i);
			s.getTransaction().commit();
			s.beginTransaction();
			ProductType found = productTypeDAO.getProductTypeFromName(inserted.getTypeName());
			s.getTransaction().commit();
			
			assertSame(inserted, found);
		}
	}
	
	@Test
	void testProductTypeNotFoundByName() {
		Session s = sf.openSession();

		productTypeDAO.setSession(s);
		
		s.beginTransaction();
		ProductType inserted = productTypeDAO.create("ptype");
		s.getTransaction().commit();
		s.beginTransaction();
		try {
			ProductType found = productTypeDAO.getProductTypeFromName("ptype1");
			fail("Shoudn't be able to find non existing product type");
		} catch (Exception e) {
			assertTrue(true);
		}
		s.getTransaction().commit();
	}
	
	@Test
	void testProductTypeIsCreatedAndDeleted() {
		Session s = sf.openSession();

		productTypeDAO.setSession(s);
		
		// 1. create a user
		s.beginTransaction();

		assertEquals(0, productTypeDAO.getTypes().size());
		
		ProductType inserted = productTypeDAO.create("ptype");
		
		s.getTransaction().commit();
		
		// 2. delete the user
		s.beginTransaction();
		
		assertEquals(1, productTypeDAO.getTypes().size());
		
		productTypeDAO.delete(inserted);
		
		s.getTransaction().commit();
		
		// 3. check no more users
		assertEquals(0, productTypeDAO.getTypes().size());
	}
}
