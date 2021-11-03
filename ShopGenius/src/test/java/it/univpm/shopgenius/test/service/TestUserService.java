package it.univpm.shopgenius.test.service;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import it.univpm.shopgenius.model.dao.UserDAO;
import it.univpm.shopgenius.model.entities.User;
import it.univpm.shopgenius.services.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class TestUserService {
	
	@MockBean
	private UserDAO userDAO;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testUserCreation() {
		User user = new User();
		Mockito.when(userDAO.create("asd", "asd", "asd", "asd", true)).thenReturn(user);
		try {
			user = userService.create("", "lastname", "email", "password", true);
			assertTrue(true);
		} catch (Exception e) {
			fail("Unexpected exception creating user: " + e.getMessage());
		}
	}
}