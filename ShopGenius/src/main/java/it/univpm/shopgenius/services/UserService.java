package it.univpm.shopgenius.services;

import java.util.List;

import it.univpm.shopgenius.model.entities.User;

public interface UserService {

    public List <User> getUsers();

    public void saveUser(User user);

    public User getUser(int id);

    public void deleteUser(int id);

	User findById(String email);

	User create(String email, String password);

	User update(User user);

}
