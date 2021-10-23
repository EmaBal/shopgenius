package it.univpm.shopgenius.model.dao;

import org.hibernate.Session;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.univpm.shopgenius.model.entities.User;


public interface UserDetailsDao {
	Session getSession();
	public void setSession(Session session);

	
	User findUserByUsername(String email);
		
	User create(String email, String password);
	
	User update(User user);
	
	void delete(User user);

	public String encryptPassword(String password);
	
	void setPasswordEncoder(PasswordEncoder passwordEncoder);
	
	PasswordEncoder getpasswordEncoder();
}
