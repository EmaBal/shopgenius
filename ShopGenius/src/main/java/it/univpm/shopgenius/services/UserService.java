package it.univpm.shopgenius.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import it.univpm.shopgenius.model.entities.User;

public interface UserService {

	public UserDetails loadUserByUsername(String email);
	
    public List <User> getUsers();

    public void saveUser(User user);

    public User getUser(int id);

    public void deleteUser(int id);

	public User findById(String email);

	public User create(String first_name, String last_name, String email, String password, boolean isEnabled);

	public User update(User user);
	
	public User findUserByEmail(String email);

	public void addRole(User user, String roleName);

	public void removeRole(User user, String roleName);

}
