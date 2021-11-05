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

import it.univpm.shopgenius.model.dao.UserDAO;
import it.univpm.shopgenius.model.entities.User;

class TestUserDAO {


	private AnnotationConfigApplicationContext ctx;
	private UserDAO userDAO;
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
		
		userDAO = ctx.getBean(UserDAO.class);
		
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

		userDAO.setSession(s);

		assertEquals(s, userDAO.getSession()); // s.equals(userDAO.getSession());
		assertSame(s, userDAO.getSession()); // s == userDAO.getSession();
		assertTrue(userDAO.getSession().getTransaction().isActive());

		s.getTransaction().commit();

		assertFalse(userDAO.getSession().getTransaction().isActive());

	}
	
	@Test
	void testAutoCreationOfSession() {
		/**
		 * Check that the dao opens a new session if no session is passed
		 * (NB the returned session has transaction disabled by default)
		 * 		 
		 */
		
		Session s = userDAO.getSession();
		assertNotNull(s);
		assertFalse(s.getTransaction().isActive());
	}

	@Test
	void testCreateUserDuplicateNames() {
		/**
		 * We test that it is possible to create two users with same name and surname
		 */
		
		Session s = sf.openSession();

		userDAO.setSession(s);

		s.beginTransaction();
		
		User newUser = userDAO.create("firstname", "lastname", "email", "password", true);

		s.getTransaction().commit();
		
		try {
			s.beginTransaction();
			User newUser2 = userDAO.create(newUser.getFirstName(), newUser.getLastName(), "newemail", "newpassword", true);
			s.getTransaction().commit();
			assertTrue(true);
		} catch (Exception e) {
			fail("Unexpected exception creating singer with duplicate name: " + e.getMessage());
		}
	}
	
	@Test
	void testNoUsersAtBeginning() {
		/**
		 * Check that there are no users when the application loads
		 */

		Session s = sf.openSession();

		userDAO.setSession(s);

		s.beginTransaction();
		List<User> allUsers = userDAO.getUsers();
		s.getTransaction().commit();
		
		assertEquals(allUsers.size(), 0);
	}
	
	@Test
	void testAllCreatedAreFound() {
		/**
		 * Generate n users, find all of them
		 */
		int n = 10;

		Session s = sf.openSession();

		userDAO.setSession(s);

		
		
		for (int i = 0; i < n; i++) {
			s.beginTransaction();
			userDAO.create("firstname" + i, "lastname" + i, "email" + i, "password" + i, true);
			s.getTransaction().commit();
			s.beginTransaction();
			List<User> allSingers = userDAO.getUsers();
			s.getTransaction().commit();
			
			assertEquals(allSingers.size(), i + 1);
			
		}
	}

	@Test
	void testUserMustHaveAFirstName() {
		/**
		 * A user cannot have empty first name
		 */
		Session s = sf.openSession();
		
		assertThrows(Exception.class, () ->  { userDAO.create("", "lastname", "email", "password", true); } );
	}
	
	@Test
	void testUserMustHaveALasttName() {
		/**
		 * A user cannot have empty last name 
		 */
		Session s = sf.openSession();
		
		assertThrows(Exception.class, () ->  { userDAO.create("firstname", "", "email", "password", true); } );
	}
	
	@Test
	void testUserMustHaveAnEmail() {
		/**
		 * A user cannot have empty email address
		 */
		Session s = sf.openSession();
		
		assertThrows(Exception.class, () ->  { userDAO.create("firstname", "lastname", "", "password", true); } );
	}
	
	@Test
	void testUserMustHaveAPassword() {
		/**
		 * A user cannot have empty password
		 */
		Session s = sf.openSession();
		
		assertThrows(Exception.class, () ->  { userDAO.create("firsname", "lastname", "email", "", true); } );
	}
	
	@Test
	void testAllCreatedAreFoundById() {
		/**
		 * Generate n users, find all of them by ID, check the found user is the same instance that was 
		 * returned by the userDAO.create(...) method
		 */
		int n = 10;

		Session s = sf.openSession();

		userDAO.setSession(s);

		
		
		for (int i = 0; i < n; i++) {
			s.beginTransaction();
			User inserted = userDAO.create("firstname" + i, "lastname" + i, "email" + i, "password" + i, true);

			s.getTransaction().commit();
			s.beginTransaction();
			
			User found = userDAO.getUser(inserted.getId());
			
			s.getTransaction().commit();
			
			assertSame(inserted, found);
		}
	}
	
	@Test
	void testUserNotFoundById() {
		Session s = sf.openSession();

		userDAO.setSession(s);

		
		s.beginTransaction();
		User inserted = userDAO.create("firsname", "lastname", "email", "password", true);
		s.getTransaction().commit();
		s.beginTransaction();
		User found = userDAO.getUser(inserted.getId() + 10);
		s.getTransaction().commit();
		
		assertNull(found);
	}
	
	@Test
	void testUserIsUpdatedCorrectlyWithMerging() {
		Session s = sf.openSession();

		userDAO.setSession(s);

		
		s.beginTransaction();
		User inserted = userDAO.create("firsname", "lastname", "email", "password", true);
		s.getTransaction().commit();
		s.beginTransaction();
		
		User updated = new User();
		updated.setId(inserted.getId());
		updated.setFirstName("firstname1");
		updated.setLastName("lastname1");
		updated.setEmail("email1");
		updated.setPassword("password1");
		
		updated = userDAO.update(updated);
		s.getTransaction().commit();
		s.beginTransaction();
		User found = userDAO.getUser(inserted.getId());
		s.getTransaction().commit();
		
		assertSame(inserted, updated);
		assertSame(updated, found);
		assertSame(found, inserted);
	}
	
	@Test
	void testUserIsUpdatedCorrectlyWithoutMerging() {
		Session s = sf.openSession();

		userDAO.setSession(s);
		
		s.beginTransaction();
		User inserted = userDAO.create("firsname", "lastname", "email", "password", true);
		s.getTransaction().commit();
		s.beginTransaction();
		
		User updated = new User();
		updated.setId(inserted.getId());
		updated.setFirstName("firstname1");
		updated.setLastName("lastname1");
		updated.setEmail("email1");
		updated.setPassword("password1");
		
		userDAO.update(updated);
		s.getTransaction().commit();
		s.beginTransaction();
		User found = userDAO.getUser(inserted.getId());
		s.getTransaction().commit();
		
		assertNotNull(found);
		assertEquals(found, inserted);
		assertSame(found, inserted);
		
		assertNotSame(found, updated);
		
		assertEquals(found.getFirstName(), updated.getFirstName());
		assertEquals(found.getLastName(), updated.getLastName());
		assertEquals(found.getId(), updated.getId());
		assertEquals(found.getEmail(), updated.getEmail());
		assertEquals(found.getPassword(), updated.getPassword());
	}
	
	@Test
	void testUserIsCreatedAndDeleted() {
		Session s = sf.openSession();

		userDAO.setSession(s);
		
		// 1. create a user
		s.beginTransaction();

		assertEquals(0, userDAO.getUsers().size());
		
		User inserted = userDAO.create("firsname", "lastname", "email", "password", true);
		
		s.getTransaction().commit();
		
		// 2. delete the user
		s.beginTransaction();
		
		assertEquals(1, userDAO.getUsers().size());
		
		userDAO.deleteUser(inserted.getId());
		
		s.getTransaction().commit();
		
		// 3. check no more users
		assertEquals(0, userDAO.getUsers().size());
	}
	
	@Test
	void testDeleteNonExistingUserDoesNotCauseError() {
		/**
		 * A user that does not exist can be deleted without begin noticed to the callee
		 */
		Session s = sf.openSession();

		userDAO.setSession(s);
		
		s.beginTransaction();
		User fake = new User();
		fake.setId(100);
		s.getTransaction().commit();
		
		assertNull(userDAO.getUser(fake.getId()));
		
		try {
			
			s.beginTransaction();
			userDAO.deleteUser(fake.getId());
			s.getTransaction().commit();
			
			assertTrue(true);
		} catch (Exception e) {
			fail("Unexpected exception when deleting fake user");
		}
	}
}
