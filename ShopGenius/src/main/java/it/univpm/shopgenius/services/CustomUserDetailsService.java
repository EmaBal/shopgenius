package it.univpm.shopgenius.services;

import it.univpm.shopgenius.model.entities.User;

public interface CustomUserDetailsService {
	
	public User findById(String username);
	
	public User create(String email, String password);
	
	public void delete(String email);
	
	public User update(User user);
}
