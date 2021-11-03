package it.univpm.shopgenius.test.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.univpm.shopgenius.controller.ProductController;
import it.univpm.shopgenius.test.config.AppContextTest;

class TestProductController {

	private AnnotationConfigApplicationContext ctx;
	private SessionFactory sf;
	private ProductController productController;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
//		System.out.println("Prepare the test case environment");
//
//		ctx = new AnnotationConfigApplicationContext(AppContextTest.class);
//		
//		productController = ctx.getBean(ProductController.class);
//		
//		sf = ctx.getBean("sessionFactory", SessionFactory.class);
	}

	@AfterEach
	void tearDown() throws Exception {
		System.out.println("Clean-up the test case environment");
		
//		ctx.close();
	}
	
	@Test
	void testSearchSuccess() {
		String result = productController.searchProduct("Coca-cola", null, null);
		assertNotNull(result);
		assertEquals(result,"redirect:/product");
	}

}
