package it.univpm.shopgenius.model.dao;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import it.univpm.shopgenius.model.entities.Role;
import it.univpm.shopgenius.model.entities.User;

public interface UserDAO {

    public List <User> getUsers();

    public void saveUser(User user);

    public User getUser(int id);

    public void deleteUser(int id);

	User findUserByUsername(String email);

	User create(String first_name, String last_name, String email, String password, boolean isEnabled);

	User update(User user);

	String encryptPassword(String password);

	void setPasswordEncoder(PasswordEncoder passwordEncoder);

	PasswordEncoder getpasswordEncoder();

	public User findUserByEmail(String email);

	public void addRole(User user, Role role);
}
