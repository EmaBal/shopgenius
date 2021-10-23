package it.univpm.shopgenius.model.dao;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import it.univpm.shopgenius.model.entities.User;

public interface UserDAO {

    public List <User> getUsers();

    public void saveUser(User user);

    public User getUser(int id);

    public void deleteUser(int id);

	User findUserByUsername(String email);

	User create(String email, String password);

	User update(User user);

	String encryptPassword(String password);

	void setPasswordEncoder(PasswordEncoder passwordEncoder);

	PasswordEncoder getpasswordEncoder();
}
