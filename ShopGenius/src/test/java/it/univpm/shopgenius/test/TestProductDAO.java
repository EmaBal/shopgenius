package it.univpm.shopgenius.test;

import static org.assertj.core.api.Assertions.fail;
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

import it.univpm.shopgenius.model.dao.ProductDAO;
import it.univpm.shopgenius.model.entities.Product;

class TestProductDAO {


	private AnnotationConfigApplicationContext ctx;
	private ProductDAO productDAO;
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
		
		productDAO = ctx.getBean(ProductDAO.class);
		
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

		productDAO.setSession(s);

		assertEquals(s, productDAO.getSession()); // s.equals(productDAO.getSession());
		assertSame(s, productDAO.getSession()); // s == productDAO.getSession();
		assertTrue(productDAO.getSession().getTransaction().isActive());

		s.getTransaction().commit();

		assertFalse(productDAO.getSession().getTransaction().isActive());

	}
	
	@Test
	void testAutoCreationOfSession() {
		/**
		 * Check that the dao opens a new session if no session is passed
		 * (NB the returned session has transaction disabled by default)
		 * 		 
		 */
		
		Session s = productDAO.getSession();
		assertNotNull(s);
		assertFalse(s.getTransaction().isActive());
	}
	
	@Test
	void testCreateProductDuplicateNames() {
		
		Session s = sf.openSession();

		productDAO.setSession(s);

		
		s.beginTransaction();
		Product newProduct1 = productDAO.create("product", 1, 1, "A1", null);
		s.getTransaction().commit();
		try {
			Product newProduct2 = productDAO.create(newProduct1.getName(), 2, 2, "A2", null);
			fail("Shoudn't be able to create 2 products with same name");
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	void testNoProductsAtBeginning() {

		Session s = sf.openSession();

		productDAO.setSession(s);

		
		s.beginTransaction();
		List<Product> allProducts = productDAO.getProducts();
		s.getTransaction().commit();
		
		assertEquals(allProducts.size(), 0);
	}
	
	@Test
	void testAllCreatedAreFound() {
		/**
		 * Generate n products, find all of them
		 */
		int n = 10;

		Session s = sf.openSession();

		productDAO.setSession(s);

		for (int i = 0; i < n; i++) {
			
			s.beginTransaction();
			productDAO.create("product" + i, (float)i, i, "A" + i, null);
			s.getTransaction().commit();
			s.beginTransaction();
			List<Product> allUsers = productDAO.getProducts();
			s.getTransaction().commit();
			
			assertEquals(allUsers.size(), i + 1);
		}
	}

	@Test
	void testProductMustHaveAName() {
		
		Session s = sf.openSession();
		
		assertThrows(Exception.class, () ->  { productDAO.create("", 1, 1, "A2", null); } );
	}
	
	@Test
	void testProductMustHaveAZeroOrMorePrice() {

		Session s = sf.openSession();
		
		assertThrows(Exception.class, () ->  { productDAO.create("prod", -2, 1, "A2", null); } );
	}
	
	@Test
	void testProductMustHaveAZeroOrMoreQuantity() {

		Session s = sf.openSession();
		
		assertThrows(Exception.class, () ->  { productDAO.create("prod", 1, -2, "A2", null); } );
	}
	
	@Test
	void testProductMustHaveALocationDetail() {

		Session s = sf.openSession();
		
		assertThrows(Exception.class, () ->  { productDAO.create("prod", 1, 1, "", null); } );
	}
	
	@Test
	void testAllCreatedAreFoundById() {

		int n = 10;

		Session s = sf.openSession();

		productDAO.setSession(s);

		for (int i = 0; i < n; i++) {
			
			s.beginTransaction();
			Product inserted = productDAO.create("prod" + i, i, i, "A" + i, null);
			s.getTransaction().commit();
			s.beginTransaction();
			Product found = productDAO.getProductById(inserted.getId());
			s.getTransaction().commit();
			
			assertSame(inserted, found);
		}
	}
	
	@Test
	void testProductNotFoundById() {
		
		Session s = sf.openSession();

		productDAO.setSession(s);
		
		s.beginTransaction();
		Product inserted = productDAO.create("prod", 1, 1, "A1", null);
		s.getTransaction().commit();
		s.beginTransaction();
		Product found = productDAO.getProductById(inserted.getId() + 10);
		s.getTransaction().commit();
		
		assertNull(found);
	}
	
	@Test
	void testProductIsCreatedAndDeleted() {
		Session s = sf.openSession();

		productDAO.setSession(s);
		
		// 1. create a product
		s.beginTransaction();

		assertEquals(0, productDAO.getProducts().size());
		
		Product inserted = productDAO.create("prod", 1, 1, "A1", null);
		
		s.getTransaction().commit();
		
		// 2. delete the product
		s.beginTransaction();
		
		assertEquals(1, productDAO.getProducts().size());
		
		productDAO.delete(inserted);
		
		s.getTransaction().commit();
		
		// 3. check no more products
		assertEquals(0, productDAO.getProducts().size());
	}
	
	@Test
	void testDeleteNonExistingProductDoesNotCauseError() {

		Session s = sf.openSession();

		productDAO.setSession(s);
		
		s.beginTransaction();
		Product fake = new Product();
		fake.setId(100);
		s.getTransaction().commit();
		
		assertNull(productDAO.getProductById(fake.getId()));

		try {
			
			s.beginTransaction();
			productDAO.delete(fake);
			s.getTransaction().commit();
			
			assertTrue(true);
		} catch (Exception e) {
			fail("Unexpected exception when deleting fake product");
		}
	}
}
